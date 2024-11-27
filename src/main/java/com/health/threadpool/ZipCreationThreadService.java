package com.health.threadpool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.fracpete.processoutput4j.output.CollectingProcessOutput;
import com.github.fracpete.rsync4j.RSync;
import com.health.config.ThymeleafService;
import com.health.controller.AjaxController;
import com.health.model.Language;
import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;
import com.health.model.TutorialWithWeekAndPackage;
import com.health.model.VideoResource;
import com.health.model.Week;
import com.health.model.WeekTitleVideo;
import com.health.service.LanguageService;
import com.health.service.PackageContainerService;
import com.health.service.PackageLanguageService;
import com.health.service.WeekService;
import com.health.service.WeekTitleVideoService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;

@Service
public class ZipCreationThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ZipCreationThreadService.class);

    @Autowired
    private PackageLanguageService packLanService;

    @Autowired
    private PackageContainerService packageContainerService;

    @Autowired
    private ThymeleafService thymeleafService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private WeekService weekService;

    @Autowired
    private WeekTitleVideoService weekTitleVideoService;

    @Autowired
    private AjaxController ajaxController;

    @Autowired
    private LanguageService lanService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    private final ConcurrentHashMap<String, String> ZipNames = new ConcurrentHashMap<>();

    private String createZipforPackageAndLan(String originalPackageName, String langName, String document,
            Environment env) {
        String zipUrl = "";
        try {
            zipUrl = ServiceUtility.createFileWithSubDirectoriesforTrainingModule(originalPackageName, langName,
                    document, env);

            String packageName = originalPackageName.replace(' ', '_');
            String rootFolder = packageName + "_" + langName;

            Path oldDirectory = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                    document.replace(rootFolder, ""));

            FileUtils.deleteDirectory(oldDirectory.toFile());

        } catch (IOException e) {
            logger.error("Exception in Zip Creation: ", e);

        }
        return zipUrl;
    }

    private void copyFileUsingRsync(Path source, Path destination) {
        RSync rsync = new RSync().source(source.toString()).destination(destination.toString()).archive(true)
                .delete(true);

        CollectingProcessOutput output;
        try {
            output = rsync.execute();
            logger.info("output", output.getStdOut());
            if (output.getExitCode() > 0) {
                logger.info("output error:{}", output.getStdErr());
            }
        } catch (Exception e) {
            logger.error("Exception", e);
        }

    }

    private void generateLocalIndexHtml(String langName, int packageId, String indexPath, Path originalPath) {
        Map<String, Object> modelAttributes = getTrainingModuleData("", langName, packageId, originalPath);
        thymeleafService.createHtmlFromTemplate("indexofHstTrainingModule", modelAttributes, indexPath, request,
                response);
    }

    @Async
    private CompletableFuture<String> createZip(String originalPackageName, String langName, Environment env) {
        String zipUrl = "";
        List<WeekTitleVideo> weekTitleList = new ArrayList<>();
        List<VideoResource> videoResourceList = new ArrayList<>();
        List<TutorialWithWeekAndPackage> tutWithWeekAndPacagekList = new ArrayList<>();

        PackageContainer packageContainer = packageContainerService.findByPackageName(originalPackageName);

        PackageLanguage packLan = packLanService.findByPackageContainerAndLan(packageContainer, langName);
        tutWithWeekAndPacagekList = new ArrayList<>(packLan.getTutorialsWithWeekAndPack());

        if (ServiceUtility.IsPackageAndLanZipExist(originalPackageName, langName, env)) {
            zipUrl = ServiceUtility.getPackageAndLanZipPath(originalPackageName, langName, env);

        }

        else {

            if (tutWithWeekAndPacagekList.size() > 0) {
                for (TutorialWithWeekAndPackage temp : tutWithWeekAndPacagekList) {
                    weekTitleList.add(temp.getWeekTitle());
                    videoResourceList.add(temp.getWeekTitle().getVideoResource());
                }
            }

            int countTutorial = videoResourceList.size();
            if (countTutorial == 0) {
                zipUrl = "Error";
            }

            else {
                String document = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                String sdfString = "training_modules-" + sdf.format(new java.util.Date());

                String packageName = originalPackageName.replace(' ', '_');
                String rootFolder = packageName + "_" + langName;
                Path destInationDirectory1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryTrainingModuleZipFiles, sdfString, File.separator, rootFolder,
                        File.separator, packageName);

                Path indexHtmlPath = Paths.get(destInationDirectory1.toString(), File.separator, "index.html");

                for (WeekTitleVideo temp : weekTitleList) {
                    String weekName = temp.getWeek().getWeekName().replace(' ', '_');
                    String title = temp.getTitle().replace(' ', '_');

                    String tutorialPath = temp.getVideoResource().getVideoPath();
                    String thumbnailPath = temp.getVideoResource().getThumbnailPath();
                    String weekTitleVideoString = Integer.toString(temp.getWeekTitleVideoId());

                    Path destInationDirectoryforLanAndWeek = Paths.get(destInationDirectory1.toString(), File.separator,
                            langName, File.separator, weekName, File.separator, weekTitleVideoString);
                    try {
                        ServiceUtility.createFolder(destInationDirectoryforLanAndWeek);
                    } catch (IOException e) {

                        logger.error("Exception: ", e);
                    }

                    try {

                        Path sourcePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                tutorialPath);
                        Path destainationPath = destInationDirectoryforLanAndWeek.resolve(title + ".mp4");

                        File sourceFile = sourcePath.toFile();
                        if (sourceFile.exists()) {

                            // FileUtils.copyFile(sourceFile, destainationPath.toFile());
                            copyFileUsingRsync(sourcePath, destainationPath);

                        }

                        Path sourcePathforThumbanil = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                thumbnailPath);
                        Path destainationthumbnailPath = destInationDirectoryforLanAndWeek.resolve("thumbnail.jpg");

                        File sourceFileofThumbnail = sourcePathforThumbanil.toFile();
                        if (sourceFileofThumbnail.exists()) {

                            // FileUtils.copyFile(sourceFile, destainationPath.toFile());
                            copyFileUsingRsync(sourcePathforThumbanil, destainationthumbnailPath);

                        }

                    } catch (Exception e) {

                        logger.error("Exception: ", e);
                    }

                }
                int packageId = packageContainer.getPackageId();
                generateLocalIndexHtml(langName, packageId, indexHtmlPath.toString(), destInationDirectory1);
                Path destInationDirectory2 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryTrainingModuleZipFiles, sdfString, File.separator, rootFolder);
                String temp = destInationDirectory2.toString();
                int indexToStart = temp.indexOf("Media");
                document = temp.substring(indexToStart, temp.length());
                createZipforPackageAndLan(originalPackageName, langName, document, env);
                zipUrl = ServiceUtility.getPackageAndLanZipPath(originalPackageName, langName, env);

            }

        }
        String key = originalPackageName + "_" + langName;

        ZipNames.put(key, zipUrl);
        return CompletableFuture.completedFuture(zipUrl);
    }

    public String getZipName(String originalPackageName, String langName, Environment env) {
        String key = originalPackageName + "_" + langName;
        String zipName = ZipNames.putIfAbsent(key, "");
        if (zipName == null) {
            // Call the Async method
            createZip(originalPackageName, langName, env);
            return null;
        }
        if (zipName.isEmpty()) {
            // processing already progress
            return null;

        }

        return zipName;
    }

    public void deleteKeyFromZipNamesAndPackageAndLanZipIfExists(String originalPackageName, String langName,
            Environment env) {
        String packageName = originalPackageName.replace(' ', '_');

        String zipFileName = packageName + "_" + langName + ".zip";
        String key = originalPackageName + "_" + langName;

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryTrainingModuleZipFiles, zipFileName);

        File file = zipFilePathName.toFile();

        if (file.exists()) {

            boolean isDeleted = file.delete();

            if (isDeleted) {
                ZipNames.remove(key);
                logger.info("Zip File deleted successfully: {} " + zipFileName);
            } else {
                logger.info("Failed to delete the zip file: {} " + zipFileName);
            }
        } else {
            logger.info(" ZipFile does not exist: {} " + zipFileName);
        }
    }

    private void getPackageAndLanguageData(Map<String, Object> modelAttributes, String weekId, String lanId,
            int packageId) {
        List<PackageContainer> packageList = packLanService.findAllDistinctPackageContainers();
        modelAttributes.put("packageList", packageList);
        Optional<Integer> optionalPackageId = Optional.of(packageId);
        ArrayList<Map<String, String>> arlist = ajaxController.getLanguageByWeek(weekId, lanId, optionalPackageId);
        ArrayList<Map<String, String>> arlist1 = ajaxController.getWeekByLanguage(weekId, lanId, optionalPackageId);
        Map<String, String> languages = arlist.get(0);
        Map<String, String> weeks = arlist1.get(0);

        modelAttributes.put("weekList", weeks);
        modelAttributes.put("languages", languages);
        modelAttributes.put("localWeek", weekId);

        modelAttributes.put("localLanguage", lanId);

        modelAttributes.put("languageCount", languages.size());

    }

    private int extractInteger(String str) {
        // Use regular expression to find all digits in the string
        String numberStr = str.replaceAll("\\D+", ""); // \\D+ matches all non-digit characters
        if (!numberStr.isEmpty()) {
            return Integer.parseInt(numberStr);
        } else {
            return 0;
        }
    }

    private Map<String, Object> getTrainingModuleData(String weekName, String langName, int packageId,
            Path originalPath) {
        Map<String, Object> modelAttributes = new HashMap<>();

        try {

            Resource resources_for_zip = resourceLoader.getResource("classpath:/static/resources_for_zip");
            Path sourcePath = Paths.get(resources_for_zip.getURI());

            copyFileUsingRsync(sourcePath, originalPath);

        } catch (Exception e) {
            logger.error("Error copying of resource_for_zip", e);
        }

        boolean zipVariable = false;

        modelAttributes.put("rootFolder", "resources_for_zip");
        modelAttributes.put("zipVariable", zipVariable);
        modelAttributes.put("week", weekName);
        modelAttributes.put("language", langName);

        int intWeekId = 0;
        if (!weekName.equals("")) {
            intWeekId = extractInteger(weekName);
        }

        logger.info("Week:{} ", weekName);
        logger.info("LangName:{}", langName);

        Week localWeek = weekService.findByWeekId(intWeekId);
        Language localLan = lanService.getByLanName(langName);

        logger.info("localWeek:{}", localWeek);
        logger.info("localLan:{}", localLan);

        List<WeekTitleVideo> weekTitleVideoList = new ArrayList<>();

        if (packageId != 0) {
            PackageContainer packageContainer = packageContainerService.findByPackageId(packageId);
            PackageLanguage packLan = packLanService.findByPackageContainerAndLan(packageContainer, langName);
            List<TutorialWithWeekAndPackage> tutWithWeekAndPacagekList = new ArrayList<>(
                    packLan.getTutorialsWithWeekAndPack());
            if (tutWithWeekAndPacagekList.size() > 0) {
                for (TutorialWithWeekAndPackage temp : tutWithWeekAndPacagekList) {
                    WeekTitleVideo weekTitleVideo = temp.getWeekTitle();
                    int weekTitleVideoId = weekTitleVideo.getWeekTitleVideoId();
                    String weekTitleVideoString = Integer.toString(weekTitleVideoId);
                    String title = weekTitleVideo.getTitle().replace(' ', '_');
                    String weekNameTemp = weekTitleVideo.getWeek().getWeekName().replace(' ', '_');

                    Path sourcePath = Paths.get(langName, File.separator, weekNameTemp, File.separator,
                            weekTitleVideoString, File.separator, title + ".mp4");

                    String videoPath = sourcePath.toString();
//                    int indexToStart = videoPath.indexOf(langName);
//                    String document = videoPath.substring(indexToStart, videoPath.length());
                    weekTitleVideo.setIndexVideoPath(videoPath);

                    Path sourcePathforThumbnail = Paths.get(langName, File.separator, weekNameTemp, File.separator,
                            weekTitleVideoString, File.separator, "thumbnail.jpg");

                    String thumnailPath = sourcePathforThumbnail.toString();
//                    int indexToStart1 = thumnailPath.indexOf(langName);
//                    String tempthumbnail = thumnailPath.substring(indexToStart1, thumnailPath.length());
                    String thumbnail = ServiceUtility.convertFilePathToUrl(thumnailPath);
                    weekTitleVideo.setIndexThumbnailPath(thumbnail);

                    weekTitleVideoList.add(weekTitleVideo);

                }

                weekTitleVideoList.sort(
                        Comparator.comparingInt((WeekTitleVideo wtv) -> extractInteger(wtv.getWeek().getWeekName()))
                                .thenComparing(wtv -> wtv.getVideoResource().getLan().getLangName())
                                .thenComparing(WeekTitleVideo::getTitle));

                for (WeekTitleVideo temp : weekTitleVideoList) {
                    logger.info(":{} : {} : {}", temp.getWeek().getWeekName(),
                            temp.getVideoResource().getLan().getLangName(), temp.getTitle());
                }

            }

        } else {
            weekTitleVideoList = weekTitleVideoService.findAll();

        }

        modelAttributes.put("weekTitleVideoList", weekTitleVideoList);

        getPackageAndLanguageData(modelAttributes, weekName, langName, packageId);
        return modelAttributes;
    }

}
