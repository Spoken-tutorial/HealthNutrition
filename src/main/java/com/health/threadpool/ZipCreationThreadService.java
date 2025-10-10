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
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.PackLanTutorialResource;
import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;
import com.health.model.Topic;
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

    private Path replaceExtension(Path path, String newExtension) {
        String filename = path.getFileName().toString();
        int dotIndex = filename.lastIndexOf(".");
        String newFilename = (dotIndex != -1 ? filename.substring(0, dotIndex) : filename) + newExtension;
        return path.getParent().resolve(newFilename);
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

    private void generateLocalIndexHtml(String langName, int packageId, String indexPath, Path originalPath,
            Environment env) {
        Map<String, Object> modelAttributes = getTrainingModuleData("", langName, packageId, originalPath, env);
        thymeleafService.createHtmlFromTemplate("indexofHstTrainingModule", modelAttributes, indexPath, request,
                response);
    }

    @Async
    private CompletableFuture<String> createZip(String originalPackageName, String langName, Environment env) {
        String zipUrl = "";
        List<WeekTitleVideo> weekTitleList = new ArrayList<>();
        List<VideoResource> videoResourceList = new ArrayList<>();
        List<TutorialWithWeekAndPackage> tutWithWeekAndPacagekList = new ArrayList<>();
        List<PackLanTutorialResource> packLanTutorialResourceList = new ArrayList<>();

        PackageContainer packageContainer = packageContainerService.findByPackageName(originalPackageName);

        PackageLanguage packLan = packLanService.findByPackageContainerAndLan(packageContainer, langName);
        tutWithWeekAndPacagekList = new ArrayList<>(packLan.getTutorialsWithWeekAndPack());
        packLanTutorialResourceList = new ArrayList<>(packLan.getPackLanTutorialResource());

        if (ServiceUtility.IsPackageAndLanZipExist(originalPackageName, langName, env)) {
            logger.info("Zip Url Exist");
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
            int countHealthTutorial = packLanTutorialResourceList.size();
            if (countTutorial == 0 && countHealthTutorial == 0) {
                zipUrl = "Error";
            }

            else {
                String document = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                String sdfString = "training_modules-" + sdf.format(new java.util.Date());

                String packageName = originalPackageName.replace(' ', '_');
                String rootFolder = packageName + "_" + langName;
                Path destInationDirectory1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryTrainingModuleZipFiles, sdfString, File.separator, rootFolder);

                Path indexHtmlPath = Paths.get(destInationDirectory1.toString(), File.separator, "index.html");

                for (WeekTitleVideo temp : weekTitleList) {
                    String weekName = temp.getWeek().getWeekName().replace(' ', '_');

                    // String title = ServiceUtility.sanitizetitle(temp.getTitle());

                    String tutorialPath = temp.getVideoResource().getVideoPath();
                    String thumbnailPath = temp.getVideoResource().getThumbnailPath();
                    String sessionName = temp.getVideoResource().getSessionName();
                    String fileSessionName = sessionName.replace(" ", "_");

                    Path destInationDirectoryforLanAndWeek = Paths.get(destInationDirectory1.toString(), File.separator,
                            langName, File.separator, weekName, File.separator, sessionName);
                    try {
                        ServiceUtility.createFolder(destInationDirectoryforLanAndWeek);
                    } catch (IOException e) {

                        logger.error("Exception: ", e);
                    }

                    try {

                        Path basePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"), tutorialPath);

                        String titleWithExtension;
                        Path sourcePath;

                        Path webmSourcePath = replaceExtension(basePath, ".webm");
                        if (webmSourcePath.toFile().exists()) {
                            sourcePath = webmSourcePath;
                            titleWithExtension = fileSessionName + ".webm";

                        } else {
                            sourcePath = basePath; // replaceExtension(basePath, ".mp4");
                            titleWithExtension = fileSessionName + ".mp4";
                        }
                        logger.info("video file name: {}", titleWithExtension);
                        Path destinationPath = destInationDirectoryforLanAndWeek.resolve(titleWithExtension);
                        File sourceFile = sourcePath.toFile();

                        if (sourceFile.exists()) {

                            copyFileUsingRsync(sourcePath, destinationPath);
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

                logger.info("Checking copied Training Modules video files in the destination folder");
                ServiceUtility.printFilesNameFromPath(destInationDirectory1);

                for (PackLanTutorialResource tempTutorial : packLanTutorialResourceList) {

                    ContributorAssignedTutorial con = tempTutorial.getTutorial().getConAssignedTutorial();
                    Topic topic = con.getTopicCatId().getTopic();
                    String originalTopicName = topic.getTopicName();
                    String topicName = originalTopicName.replace(' ', '_');
                    int tutorialId = tempTutorial.getTutorial().getTutorialId();
                    String tutorialIdString = Integer.toString(tutorialId);

                    String tutorialPath1 = tempTutorial.getTutorial().getVideo();

                    Path destInationDirectoryforTutorial = Paths.get(destInationDirectory1.toString(), File.separator,
                            langName, File.separator, "Health Spoken", topicName, File.separator, tutorialIdString);
                    try {
                        ServiceUtility.createFolder(destInationDirectoryforTutorial);
                    } catch (IOException e) {

                        logger.error("Exception: ", e);
                    }

                    try {

                        Path originalPath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                tutorialPath1);
                        Path basePath;
                        String resolution = "_480p";
                        String resolutionSubtitle = resolution + CommonData.WITH_SUBTITLES;
                        String resolutionUrl = ServiceUtility.getVideoResolutionPath(tutorialPath1, resolution);
                        String resolutionSubtitleUrl = ServiceUtility.getVideoResolutionPath(tutorialPath1,
                                resolutionSubtitle);
                        String originalSubtitleUrl = ServiceUtility.getVideoResolutionPath(tutorialPath1,
                                CommonData.WITH_SUBTITLES);

                        if (resolutionUrl != null) {
                            Path resolutionPath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                    resolutionUrl);

                            if (resolutionPath.toFile().exists()) {
                                basePath = resolutionPath;

                            } else {
                                basePath = originalPath;
                            }
                        } else {
                            basePath = originalPath;
                        }

                        Path resolutionSubtitlePath;
                        Path originalSubtitlePath;

                        if (resolutionSubtitleUrl != null) {
                            resolutionSubtitlePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                    resolutionSubtitleUrl);
                            if (resolutionSubtitlePath.toFile().exists()) {
                                basePath = resolutionSubtitlePath;
                            }
                        }

                        if (originalSubtitleUrl != null && resolutionUrl == null) {
                            originalSubtitlePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                    originalSubtitleUrl);
                            if (originalSubtitlePath.toFile().exists()) {
                                basePath = originalSubtitlePath;
                            }
                        }

                        String topicwithExtention;
                        Path sourcePath;

                        Path webmSourcePath = replaceExtension(basePath, ".webm");
                        if (webmSourcePath.toFile().exists()) {
                            sourcePath = webmSourcePath;
                            topicwithExtention = topicName + ".webm";
                        } else {
                            sourcePath = basePath; // replaceExtension(basePath, ".mp4");
                            topicwithExtention = topicName + ".mp4";
                        }

                        Path destainationPath1 = destInationDirectoryforTutorial.resolve(topicwithExtention);

                        File sourceFile1 = sourcePath.toFile();
                        if (sourceFile1.exists()) {

                            copyFileUsingRsync(sourcePath, destainationPath1);

                        }

                    } catch (Exception e) {

                        logger.error("Exception: ", e);
                    }

                }
                int packageId = packageContainer.getPackageId();
                generateLocalIndexHtml(langName, packageId, indexHtmlPath.toString(), destInationDirectory1, env);
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

    private Map<String, Object> getTrainingModuleData(String weekName, String langName, int packageId,
            Path originalPath, Environment env) {
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
            intWeekId = ServiceUtility.extractInteger(weekName);
        }

        logger.info("Week:{} ", weekName);
        logger.info("LangName:{}", langName);

        Week localWeek = weekService.findByWeekId(intWeekId);
        Language localLan = lanService.getByLanName(langName);

        logger.info("localWeek:{}", localWeek);
        logger.info("localLan:{}", localLan);

        List<WeekTitleVideo> weekTitleVideoList = new ArrayList<>();
        List<PackLanTutorialResource> packLanTutorialResourceList = new ArrayList<>();
        List<Topic> topics = new ArrayList<>();

        if (packageId != 0) {
            PackageContainer packageContainer = packageContainerService.findByPackageId(packageId);
            PackageLanguage packLan = packLanService.findByPackageContainerAndLan(packageContainer, langName);
            List<TutorialWithWeekAndPackage> tutWithWeekAndPacagekList = new ArrayList<>(
                    packLan.getTutorialsWithWeekAndPack());
            if (tutWithWeekAndPacagekList.size() > 0) {
                for (TutorialWithWeekAndPackage temp : tutWithWeekAndPacagekList) {
                    WeekTitleVideo weekTitleVideo = temp.getWeekTitle();

                    String sessionName = weekTitleVideo.getVideoResource().getSessionName();

                    // String title = ServiceUtility.sanitizetitle(weekTitleVideo.getTitle());
                    String fileSessionName = sessionName.replace(" ", "_");

                    String weekNameTemp = weekTitleVideo.getWeek().getWeekName().replace(' ', '_');
                    String tempTutorialPath = weekTitleVideo.getVideoResource().getVideoPath();

                    Path basePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"), tempTutorialPath);

                    Path sourcePath;

                    Path webmSourcePath = replaceExtension(basePath, ".webm");
                    if (webmSourcePath.toFile().exists()) {
                        sourcePath = Paths.get(langName, File.separator, weekNameTemp, File.separator, sessionName,
                                File.separator, fileSessionName + ".webm");
                        ;

                    } else {
                        sourcePath = Paths.get(langName, File.separator, weekNameTemp, File.separator, sessionName,
                                File.separator, fileSessionName + ".mp4");

                    }

                    String videoPath = sourcePath.toString();
                    weekTitleVideo.setIndexVideoPath(videoPath);

                    Path sourcePathforThumbnail = Paths.get(langName, File.separator, weekNameTemp, File.separator,
                            sessionName, File.separator, "thumbnail.jpg");

                    String thumnailPath = sourcePathforThumbnail.toString();
                    String thumbnail = ServiceUtility.convertFilePathToUrl(thumnailPath);
                    weekTitleVideo.setIndexThumbnailPath(thumbnail);

                    weekTitleVideoList.add(weekTitleVideo);

                }

                weekTitleVideoList.sort(Comparator
                        .comparingInt(
                                (WeekTitleVideo wtv) -> ServiceUtility.extractInteger(wtv.getWeek().getWeekName()))
                        .thenComparing(wtv -> wtv.getVideoResource().getLan().getLangName())
                        .thenComparing(
                                Comparator.comparing((WeekTitleVideo wtv) -> wtv.getVideoResource().getSessionName(),
                                        Comparator.nullsLast(Comparator.naturalOrder())))
                        .thenComparing(WeekTitleVideo::getTitle));

            }

            List<PackLanTutorialResource> tempPackLanTutorialResourceList = new ArrayList<>(
                    packLan.getPackLanTutorialResource());
            for (PackLanTutorialResource tempTutorial : tempPackLanTutorialResourceList) {
                ContributorAssignedTutorial con = tempTutorial.getTutorial().getConAssignedTutorial();
                Topic topic = con.getTopicCatId().getTopic();
                String originalTopicName = topic.getTopicName();
                String topicName = originalTopicName.replace(' ', '_');
                int tutorialId = tempTutorial.getTutorial().getTutorialId();
                String tutorialIdString = Integer.toString(tutorialId);
                String tempTutorialPath = tempTutorial.getTutorial().getVideo();
                Path basePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"), tempTutorialPath);

                Path sourcePath;

                Path webmSourcePath = replaceExtension(basePath, ".webm");
                if (webmSourcePath.toFile().exists()) {
                    sourcePath = Paths.get(langName, File.separator, "Health Spoken", File.separator, topicName,
                            File.separator, tutorialIdString, File.separator, topicName + ".webm");
                    ;

                } else {
                    sourcePath = Paths.get(langName, File.separator, "Health Spoken", File.separator, topicName,
                            File.separator, tutorialIdString, File.separator, topicName + ".mp4");

                }

                String videoPathofTutorial = sourcePath.toString();
                tempTutorial.setIndexVideoPath(videoPathofTutorial);
                packLanTutorialResourceList.add(tempTutorial);
                topics.add(topic);
            }

            packLanTutorialResourceList.sort(Comparator.comparing(tempTutorial -> tempTutorial.getTutorial()
                    .getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()));
            topics.sort(Comparator.comparing(Topic::getTopicName));

        } else {
            weekTitleVideoList = weekTitleVideoService.findAll();

        }

        modelAttributes.put("weekTitleVideoList", weekTitleVideoList);
        modelAttributes.put("packLanTutorialResourceList", packLanTutorialResourceList);
        modelAttributes.put("topics", topics);
        modelAttributes.put("languageForHealth", localLan);

        getPackageAndLanguageData(modelAttributes, weekName, langName, packageId);
        return modelAttributes;
    }

}
