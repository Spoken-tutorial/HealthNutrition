
package com.health.threadpool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

import com.fasterxml.jackson.databind.ObjectMapper;
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

    private String createZipforHealthTutorial(String parentZipFolderName, String zipNameWithoutExtention,
            String document, Environment env) {
        String zipUrl = "";
        try {
            zipUrl = ServiceUtility.createFileWithSubDirectoriesforHealthTutorial(parentZipFolderName,
                    zipNameWithoutExtention, document, env);

            Path oldDirectory = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                    document.replace(zipNameWithoutExtention, ""));

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

    private void generateLocalIndexHtml(Set<Integer> catIds, Set<Integer> lanIds, String indexPath, Path originalPath,
            Environment env) {
        Map<String, Object> modelAttributes = getHealthTutorialData(catIds, lanIds, originalPath, env);
        thymeleafService.createHtmlFromTemplate("indexofHealthTutorial", modelAttributes, indexPath, request, response);
    }

    @Async
    private CompletableFuture<String> createZip(String courseName, Set<Integer> catIds, Set<Integer> lanIds,
            Environment env) {
        String zipUrl = "";

        List<Category> catList = new ArrayList<>();
        List<Language> lanList = new ArrayList<>();
        Set<Tutorial> uniqueTutorials = new LinkedHashSet<>();
        StringBuilder tempCatIdFolder = new StringBuilder();
        StringBuilder tempLanIdFolder = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        boolean flag = true;

        for (int catId : catIds) {
            tempCatIdFolder.append("cat").append(catId).append("_");
            Category cat = catService.findByid(catId);
            catList.add(cat);

            for (int lanId : lanIds) {

                Language lan = lanService.getById(lanId);
                if (flag) {
                    lanList.add(lan);

                    tempLanIdFolder.append("lan").append(lanId).append("_");
                    if (lanId != 22) {
                        sb.append(lan.getLangName().replace(' ', '_')).append("_");
                    }

                }

                List<TopicCategoryMapping> tcmList = topicCatMappingService.findAllByCategory(cat);
                List<ContributorAssignedTutorial> conList = conService.findAllByTopicCatAndLan(tcmList, lan);
                List<Tutorial> tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);
                uniqueTutorials.addAll(tutorials);

            }
            flag = false;

        }
        String parentZipfolder = tempCatIdFolder.toString() + tempLanIdFolder.toString();
        String zipNameWithoutExtentions = courseName.replace(' ', '_') + "_" + sb.toString();

        if (ServiceUtility.IsCourseNameAndLanZipExist(parentZipfolder, zipNameWithoutExtentions, env)) {
            logger.info("Zip Url Exist");
            zipUrl = ServiceUtility.getCourseNameAndLanZipPath(parentZipfolder, zipNameWithoutExtentions, env);

        }

        else {

            int countHealthTutorial = uniqueTutorials.size();
            if (countHealthTutorial == 0) {
                zipUrl = "Error";
            }

            else {
                String document = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
                String sdfString = "health_tutorials-" + sdf.format(new java.util.Date());

                // String catName = originalCategoryName.replace(' ', '_');
                String rootFolder = zipNameWithoutExtentions;
                Path destInationDirectory1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryHealthTutorialZipFiles, sdfString, File.separator, rootFolder);

                Path indexHtmlPath = Paths.get(destInationDirectory1.toString(), File.separator, "index.html");

                for (Tutorial tempTutorial : uniqueTutorials) {

                    ContributorAssignedTutorial con = tempTutorial.getConAssignedTutorial();
                    Topic topic = con.getTopicCatId().getTopic();
                    String originalTopicName = topic.getTopicName();
                    String topicName = originalTopicName.replace(' ', '_');
                    Category category = con.getTopicCatId().getCat();
                    String catName = category.getCatName().replace(' ', '_');
                    Language lan = con.getLan();
                    String langName = lan.getLangName().replace(' ', '_');
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
                            catName, File.separator, langName, File.separator, topicName);
                    try {
                        ServiceUtility.createFolder(destInationDirectoryforTutorial);
                    } catch (IOException e) {

                        logger.error("Exception: ", e);
                    }

                    try {

                        Path basePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                tutorialPath1);

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

                        File sourceFile = sourcePath.toFile();
                        if (sourceFile.exists()) {

                            copyFileUsingRsync(sourcePath, destainationPath1);

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

                generateLocalIndexHtml(catIds, lanIds, indexHtmlPath.toString(), destInationDirectory1, env);
                Path destInationDirectory2 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryHealthTutorialZipFiles, sdfString, File.separator, rootFolder);
                String temp = destInationDirectory2.toString();
                int indexToStart = temp.indexOf("Media");
                document = temp.substring(indexToStart, temp.length());
                createZipforHealthTutorial(parentZipfolder, zipNameWithoutExtentions, document, env);
                zipUrl = ServiceUtility.getCourseNameAndLanZipPath(parentZipfolder, zipNameWithoutExtentions, env);

            }

        }
        String key = parentZipfolder;

        ZipNames.put(key, zipUrl);
        return CompletableFuture.completedFuture(zipUrl);
    }

    public String getZipName(String courseName, Set<Integer> catIds, Set<Integer> lanIds, Environment env) {

        StringBuilder tempCatIdFolder = new StringBuilder();
        StringBuilder tempLanIdFolder = new StringBuilder();

        for (int catId : catIds) {
            tempCatIdFolder.append("cat").append(catId).append("_");
        }
        for (int lanId : lanIds) {
            Language lan = lanService.getById(lanId);
            tempLanIdFolder.append("lan").append(lanId).append("_");

        }

        String parentZipfolder = tempCatIdFolder.toString() + tempLanIdFolder.toString();
        String key = parentZipfolder;
        String zipName = ZipNames.putIfAbsent(key, "");
        if (zipName == null) {
            // Call the Async method
            createZip(courseName, catIds, lanIds, env);
            return null;
        } else if (zipName.isEmpty()) {
            // processing already progress
            return null;

        }

        else if (!zipName.contains(courseName)) {
            Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"), zipName);

            File file = zipFilePathName.toFile();

            if (file.exists()) {

                boolean isDeleted = file.delete();

                if (isDeleted) {
                    ZipNames.remove(key);
                    createZip(courseName, catIds, lanIds, env);

                    logger.info("Zip File deleted successfully: {} " + zipName);
                } else {
                    logger.info("Failed to delete the zip file: {} " + zipName);
                }
            } else {
                logger.info(" ZipFile does not exist: {} " + zipName);
            }

            return null;
        }

        return zipName;
    }

    public void deleteKeyFromZipNamesAndHealthTutorialZipIfExists(int catId, int lanId, Environment env) {
        // Construct identifiers to match in folder names
        String catIdentifier = "cat" + catId;
        String lanIdentifier = "lan" + lanId;

        // Resolve the main directory path
        Path basePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryHealthTutorialZipFiles);

        File baseDir = basePath.toFile();

        if (!baseDir.exists() || !baseDir.isDirectory()) {
            logger.warn("HealthTutorialZipFiles directory does not exist or is not a directory: {}", basePath);
            return;
        }

        // Loop through all subfolders
        File[] subFolders = baseDir.listFiles(File::isDirectory);
        if (subFolders == null)
            return;

        for (File subFolder : subFolders) {
            String folderName = subFolder.getName();

            // Check if the folder name contains both catId and lanId
            if (folderName.contains(catIdentifier) && folderName.contains(lanIdentifier)) {
                // Delete the folder and its contents recursively
                try {
                    deleteFolderRecursively(subFolder.toPath());
                    logger.info("Deleted folder and zip: {}", folderName);

                    // Remove the key from ZipNames map
                    ZipNames.remove(folderName);
                    logger.info("Removed key from ZipNames: {}", folderName);

                } catch (IOException e) {
                    logger.error("Failed to delete folder: " + folderName, e);
                }
            }
        }
    }

    private void deleteFolderRecursively(Path folderPath) throws IOException {
        Files.walk(folderPath).sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
    }

    private Map<String, Object> getHealthTutorialData(Set<Integer> catIds, Set<Integer> lanIds, Path originalPath,
            Environment env) {
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

        List<Category> catList = new ArrayList<>();
        List<Language> lanList = new ArrayList<>();
        List<Topic> topics = new ArrayList<>();
        Set<Tutorial> uniqueTutorials = new LinkedHashSet<>();
        Set<Category> uniqueCategories = new LinkedHashSet<>();
        Set<Topic> uniqueTopics = new LinkedHashSet<>();
        Set<Language> uniqueLanguage = new LinkedHashSet<>();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> tutorialJsonList = new ArrayList<>();

        for (int catId : catIds) {
            Category cat = catService.findByid(catId);
            for (int lanId : lanIds) {
                Language lan = lanService.getById(lanId);

                List<TopicCategoryMapping> tcmList = topicCatMappingService.findAllByCategory(cat);
                List<ContributorAssignedTutorial> conList = conService.findAllByTopicCatAndLan(tcmList, lan);
                List<Tutorial> tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);
                uniqueTutorials.addAll(tutorials);

            }

        }

        List<Tutorial> tutorialList = new ArrayList<>();

        if (!uniqueTutorials.isEmpty()) {

            for (Tutorial tempTutorial : uniqueTutorials) {
                ContributorAssignedTutorial con = tempTutorial.getConAssignedTutorial();
                TopicCategoryMapping tcm = con.getTopicCatId();
                Category cat = tcm.getCat();
                String catName = cat.getCatName().replace(' ', '_');
                Language lan = con.getLan();
                String langName = lan.getLangName().replace(' ', '_');
                Topic topic = tcm.getTopic();
                String originalTopicName = topic.getTopicName();
                String topicName = originalTopicName.replace(' ', '_');
                int topicOrder = tcm.getOrder();
                int catOrder = cat.getOrder();
                String tempTutorialPath = tempTutorial.getVideo();

                int tutorialId = tempTutorial.getTutorialId();
                // String tutorialIdString = Integer.toString(tutorialId);

                Path basePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"), tempTutorialPath);

                Path sourcePath;

                Path webmSourcePath = replaceExtension(basePath, ".webm");
                if (webmSourcePath.toFile().exists()) {
                    sourcePath = Paths.get(catName, File.separator, langName, File.separator, topicName, File.separator,
                            topicName + ".webm");
                    ;

                } else {
                    sourcePath = Paths.get(catName, File.separator, langName, File.separator, topicName, File.separator,
                            topicName + ".mp4");

                }

                String videoPathofTutorial = sourcePath.toString();
                tempTutorial.setIndexVideoPath(videoPathofTutorial);
                tutorialList.add(tempTutorial);
                uniqueTopics.add(topic);
                uniqueCategories.add(cat);
                uniqueLanguage.add(lan);

                Map<String, String> tutorialData = new HashMap<>();
                tutorialData.put("category", cat.getCatName());
                tutorialData.put("topic", topic.getTopicName());
                tutorialData.put("language", lan.getLangName());
                tutorialData.put("cat_order", String.valueOf(catOrder));
                tutorialData.put("topic_order", String.valueOf(topicOrder));

                tutorialJsonList.add(tutorialData);

            }

            // Convert to JSON string and add to model
            try {
                String tutorialJsonString = mapper.writeValueAsString(tutorialJsonList);
                modelAttributes.put("tutorialJson", tutorialJsonString);
            } catch (Exception e) {
                logger.error("Failed to convert tutorial data to JSON", e);
            }

            catList.addAll(uniqueCategories);
            topics.addAll(uniqueTopics);
            lanList.addAll(uniqueLanguage);

            tutorialList.sort(Comparator.comparing(
                    tempTutorial -> tempTutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()));

            topics.sort(Comparator.comparing(Topic::getTopicName));

            tutorialList.sort(Comparator.comparing(
                    tempTutorial -> tempTutorial.getConAssignedTutorial().getTopicCatId().getCat().getCatName()));

            Collections.sort(catList, Category.SortByOrderValue);

            tutorialList.sort(
                    Comparator.comparing(tempTutorial -> tempTutorial.getConAssignedTutorial().getLan().getLangName()));

            lanList.sort(Comparator.comparing(Language::getLangName));

        }

        modelAttributes.put("tutorialList", tutorialList);
        modelAttributes.put("categories", catList);
        modelAttributes.put("topics", topics);
        modelAttributes.put("languages", lanList);
        modelAttributes.put("languageCount", lanList.size());
        // getModelData(modelAttributes, catIds, lanIds, 0);
        return modelAttributes;
    }

}
