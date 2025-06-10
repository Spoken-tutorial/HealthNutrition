
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
import com.health.model.Category;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.model.Tutorial;
import com.health.service.CategoryService;
import com.health.service.ContributorAssignedTutorialService;
import com.health.service.LanguageService;
import com.health.service.TopicCategoryMappingService;
import com.health.service.TutorialService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;

@Service
public class ZipHealthTutorialThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ZipHealthTutorialThreadService.class);

    @Autowired
    private ThymeleafService thymeleafService;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private AjaxController ajaxController;

    @Autowired
    private LanguageService lanService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private CategoryService catService;

    @Autowired
    private TutorialService tutorialService;

    @Autowired
    private ContributorAssignedTutorialService conService;

    @Autowired
    private TopicCategoryMappingService topicCatMappingService;

    private final ConcurrentHashMap<String, String> ZipNames = new ConcurrentHashMap<>();

    private String createZipforHealthTutorial(String originalCategoryName, String langName, String document,
            Environment env) {
        String zipUrl = "";
        try {
            zipUrl = ServiceUtility.createFileWithSubDirectoriesforHealthTutorial(originalCategoryName, langName,
                    document, env);

            String catName = originalCategoryName.replace(' ', '_');
            String rootFolder = catName + "_" + langName;

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

    private void generateLocalIndexHtml(String langName, int catId, String indexPath, Path originalPath,
            Environment env) {
        Map<String, Object> modelAttributes = getHealthTutorialData(langName, catId, originalPath, env);
        thymeleafService.createHtmlFromTemplate("indexofHealthTutorial", modelAttributes, indexPath, request, response);
    }

    @Async
    private CompletableFuture<String> createZip(String originalCategoryName, String langName, Environment env) {
        String zipUrl = "";

        Category category = catService.findBycategoryname(originalCategoryName);
        Language lan = lanService.getByLanName(langName);
        List<TopicCategoryMapping> tcmList = topicCatMappingService.findAllByCategory(category);
        List<ContributorAssignedTutorial> conList = conService.findAllByTopicCatAndLan(tcmList, lan);
        List<Tutorial> tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);

        if (ServiceUtility.IsCategoryAndLanZipExist(originalCategoryName, langName, env)) {
            logger.info("Zip Url Exist");
            zipUrl = ServiceUtility.getCategoryAndLanZipPath(originalCategoryName, langName, env);

        }

        else {

            int countHealthTutorial = tutorials.size();
            if (countHealthTutorial == 0) {
                zipUrl = "Error";
            }

            else {
                String document = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                String sdfString = "health_tutorials-" + sdf.format(new java.util.Date());

                String catName = originalCategoryName.replace(' ', '_');
                String rootFolder = catName + "_" + langName;
                Path destInationDirectory1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryHealthTutorialZipFiles, sdfString, File.separator, rootFolder,
                        File.separator, catName);

                Path indexHtmlPath = Paths.get(destInationDirectory1.toString(), File.separator, "index.html");

                for (Tutorial tempTutorial : tutorials) {

                    ContributorAssignedTutorial con = tempTutorial.getConAssignedTutorial();
                    Topic topic = con.getTopicCatId().getTopic();
                    String originalTopicName = topic.getTopicName();
                    String topicName = originalTopicName.replace(' ', '_');
                    int tutorialId = tempTutorial.getTutorialId();
                    String tutorialIdString = Integer.toString(tutorialId);
                    String slide = "";
                    String original_script = "";
                    String timed_script = "";
                    if (!tempTutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {
                        slide = tempTutorial.getRelatedVideo().getSlide();
                        timed_script = CommonData.uploadDirectoryScriptOdtFileforDownload + tutorialId + ".odt";
                    } else {
                        slide = tempTutorial.getSlide();
                        timed_script = tempTutorial.getTimeScript();
                        original_script = CommonData.uploadDirectoryScriptOdtFileforDownload + tutorialId + ".odt";
                    }

                    String tutorialPath1 = tempTutorial.getVideo();

//                    Path filePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
//                            CommonData.uploadDirectoryScriptOdtFileforDownload, tutorialId + ".odt");
//                    Path destainationPath = destInationDirectoryforTopiccAndLan.resolve(topicName + ".odt");

                    Path destInationDirectoryforTutorial = Paths.get(destInationDirectory1.toString(), File.separator,
                            langName, File.separator, topicName);
                    try {
                        ServiceUtility.createFolder(destInationDirectoryforTutorial);
                    } catch (IOException e) {

                        logger.error("Exception: ", e);
                    }

                    try {

                        Path sourcePathofTutorial = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                tutorialPath1);
                        Path destainationPath1 = destInationDirectoryforTutorial.resolve(topicName + ".mp4");

                        File sourceFile1 = sourcePathofTutorial.toFile();
                        if (sourceFile1.exists()) {

                            copyFileUsingRsync(sourcePathofTutorial, destainationPath1);

                        }

                        Path sourcePathofSlide = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                slide);
                        File sourceofSlide = sourcePathofSlide.toFile();

                        if (sourceofSlide.exists()) {
                            // Extract the file name (e.g., "myslide.zip")
                            String slideFileName = sourcePathofSlide.getFileName().toString();

                            // Create destination path with filename preserved
                            Path destainationSlidePath = destInationDirectoryforTutorial.resolve(slideFileName);

                            // Now copy the file with correct filename
                            copyFileUsingRsync(sourcePathofSlide, destainationSlidePath);
                        }

                        Path sourcePathofTimedScript = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                timed_script);
                        Path destainationTimedScriptPath = null;
                        if (tempTutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {

                            // Get the extension from the source file
                            String sourceFileName = sourcePathofTimedScript.getFileName().toString();
                            String fileExtension = sourceFileName.contains(".")
                                    ? sourceFileName.substring(sourceFileName.lastIndexOf('.'))
                                    : "";

                            // Construct destination path using the same extension
                            destainationTimedScriptPath = destInationDirectoryforTutorial
                                    .resolve("timed_script" + fileExtension);

                        } else {
                            destainationTimedScriptPath = destInationDirectoryforTutorial
                                    .resolve("timed_script" + ".odt");
                        }

                        File sourceofTimedScript = sourcePathofTimedScript.toFile();
                        if (sourceofTimedScript.exists()) {

                            copyFileUsingRsync(sourcePathofTimedScript, destainationTimedScriptPath);

                        }

                        if (!original_script.isEmpty()) {
                            Path sourcePathofOriginalScript = Paths
                                    .get(env.getProperty("spring.applicationexternalPath.name"), original_script);
                            Path destainationPathOfOriginalScript = destInationDirectoryforTutorial
                                    .resolve("orginal_Script" + ".odt");

                            File sourceOriginalScript = sourcePathofOriginalScript.toFile();
                            if (sourceOriginalScript.exists()) {

                                copyFileUsingRsync(sourcePathofOriginalScript, destainationPathOfOriginalScript);

                            }

                        }

                    } catch (Exception e) {

                        logger.error("Exception: ", e);
                    }

                }
                int catId = category.getCategoryId();
                generateLocalIndexHtml(langName, catId, indexHtmlPath.toString(), destInationDirectory1, env);
                Path destInationDirectory2 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryHealthTutorialZipFiles, sdfString, File.separator, rootFolder);
                String temp = destInationDirectory2.toString();
                int indexToStart = temp.indexOf("Media");
                document = temp.substring(indexToStart, temp.length());
                createZipforHealthTutorial(originalCategoryName, langName, document, env);
                zipUrl = ServiceUtility.getCategoryAndLanZipPath(originalCategoryName, langName, env);

            }

        }
        String key = originalCategoryName + "_" + langName;

        ZipNames.put(key, zipUrl);
        return CompletableFuture.completedFuture(zipUrl);
    }

    public String getZipName(String originalCategoryName, String langName, Environment env) {
        String key = originalCategoryName + "_" + langName;
        String zipName = ZipNames.putIfAbsent(key, "");
        if (zipName == null) {
            // Call the Async method
            createZip(originalCategoryName, langName, env);
            return null;
        }
        if (zipName.isEmpty()) {
            // processing already progress
            return null;

        }

        return zipName;
    }

    public void deleteKeyFromZipNamesAndCategoryAndLanZipIfExists(String originalCategoryName, String langName,
            Environment env) {
        String catName = originalCategoryName.replace(' ', '_');

        String zipFileName = catName + "_" + langName + ".zip";
        String key = originalCategoryName + "_" + langName;

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryHealthTutorialZipFiles, zipFileName);

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

    private Map<String, Object> getHealthTutorialData(String langName, int catId, Path originalPath, Environment env) {
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

        modelAttributes.put("language", langName);

        logger.info("LangName:{}", langName);

        Language localLan = lanService.getByLanName(langName);

        logger.info("localLan:{}", localLan);

        List<Topic> topics = new ArrayList<>();
        List<Tutorial> tutorialList = new ArrayList<>();
        if (catId != 0) {
            Category category = catService.findByid(catId);

            Language lan = lanService.getByLanName(langName);
            List<TopicCategoryMapping> tcmList = topicCatMappingService.findAllByCategory(category);
            List<ContributorAssignedTutorial> conList = conService.findAllByTopicCatAndLan(tcmList, lan);
            List<Tutorial> tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);

            for (Tutorial tempTutorial : tutorials) {
                ContributorAssignedTutorial con = tempTutorial.getConAssignedTutorial();
                Topic topic = con.getTopicCatId().getTopic();
                String originalTopicName = topic.getTopicName();
                String topicName = originalTopicName.replace(' ', '_');
                int tutorialId = tempTutorial.getTutorialId();
                // String tutorialIdString = Integer.toString(tutorialId);
                Path sourcePathofTutorial = Paths.get(langName, File.separator, topicName, File.separator,
                        topicName + ".mp4");

                String videoPathofTutorial = sourcePathofTutorial.toString();
                tempTutorial.setIndexVideoPath(videoPathofTutorial);
                tutorialList.add(tempTutorial);
                topics.add(topic);
            }

            tutorialList.sort(Comparator.comparing(
                    tempTutorial -> tempTutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()));
            topics.sort(Comparator.comparing(Topic::getTopicName));

            modelAttributes.put("catName", category.getCatName());

        }

        modelAttributes.put("tutorialList", tutorialList);

        modelAttributes.put("topics", topics);
        modelAttributes.put("languageForHealth", localLan);

        return modelAttributes;
    }

}
