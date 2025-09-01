
package com.health.threadpool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import com.health.model.CourseCatTopicMapping;
import com.health.model.Language;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.model.Tutorial;
import com.health.service.CategoryService;
import com.health.service.ContributorAssignedTutorialService;
import com.health.service.CourseCatTopicService;
import com.health.service.CourseService;
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
    private CourseService courseService;

    @Autowired
    private CourseCatTopicService courseCatTopicService;

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

    private void generateLocalIndexHtml(int courseId, Set<Integer> catIds, Set<Integer> lanIds, String indexPath,
            Path originalPath, Environment env) {
        Map<String, Object> modelAttributes = getHealthTutorialData(courseId, catIds, lanIds, originalPath, env);
        thymeleafService.createHtmlFromTemplate("indexofHealthTutorial", modelAttributes, indexPath, request, response);
    }

    @Async
    private CompletableFuture<String> createZip(int courseId, String courseName, String quality, Set<Integer> catIds,
            Set<Integer> lanIds, Environment env) {
        String zipUrl = "";

        List<Category> catList = new ArrayList<>();
        List<Language> lanList = new ArrayList<>();
        Set<Tutorial> uniqueTutorials = new LinkedHashSet<>();
        StringBuilder tempCatIdFolder = new StringBuilder();
        StringBuilder tempLanIdFolder = new StringBuilder();
        StringBuilder tempcourseIdFolder = new StringBuilder();
        StringBuilder sb = new StringBuilder();
        boolean flag = true;

        List<TopicCategoryMapping> tcmList = new ArrayList<>();
        List<ContributorAssignedTutorial> conList = new ArrayList<>();
        List<Tutorial> tutorials = new ArrayList<>();

        for (int catId : catIds) {
            tempCatIdFolder.append("cat").append(catId).append("_");
            Category cat = catService.findByid(catId);
            catList.add(cat);

            for (int lanId : lanIds) {

                Language lan = lanService.getById(lanId);
                if (flag) {
                    lanList.add(lan);

                    tempLanIdFolder.append("lan").append(lanId).append("_");
                    // if (lanId != 22) {
                    sb.append(lan.getLangName().replace(' ', '_')).append("_");
                    // }

                }
                if (courseId == 0) {
                    tcmList = topicCatMappingService.findAllByCategory(cat);
                    conList = conService.findAllByTopicCatAndLan(tcmList, lan);
                    tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);
                    uniqueTutorials.addAll(tutorials);

                }

            }
            flag = false;

        }
        String parentZipfolder = "";
        if (courseId != 0) {
            List<CourseCatTopicMapping> cctmList = courseCatTopicService
                    .findAllByCourse_CourseIdAndStatusTrue(courseId);

            for (CourseCatTopicMapping temp : cctmList) {
                Category cat = temp.getCat();
                if (cat.isStatus()) {
                    Topic topic = temp.getTopic();
                    TopicCategoryMapping tcm = topicCatMappingService.findAllByCategoryAndTopic(cat, topic);
                    tcmList.add(tcm);
                }

            }

            conList = conService.findByTopicCatLanList(tcmList, lanList);
            logger.info("conList size in create zip : {}", conList.size());
            tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);
            uniqueTutorials.addAll(tutorials);

            tempcourseIdFolder.append("CourseId").append(courseId).append("_");
            parentZipfolder = tempCatIdFolder.toString() + tempLanIdFolder.toString() + tempcourseIdFolder.toString()
                    + quality;
        } else {
            parentZipfolder = tempCatIdFolder.toString() + tempLanIdFolder.toString() + quality;
        }

        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '_') {
            sb.deleteCharAt(sb.length() - 1);
        }
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
                Path destinationDirectory1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryHealthTutorialZipFiles, sdfString, File.separator, rootFolder);

                Path indexHtmlPath = Paths.get(destinationDirectory1.toString(), File.separator, "index.html");

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

                    Path destinationCourse = Paths.get(destinationDirectory1.toString(), catName, langName);
                    Path destinationVideo = destinationCourse.resolve("Tutorials");
                    Path destinationSlide = destinationCourse.resolve("Slides");
                    Path destinationScript = destinationCourse.resolve("Scripts");

                    Path destinationTimedScript = destinationScript.resolve("Timed_Scripts");
                    Path destinationOriginalScript = null;

                    List<Path> foldersToCreate = new ArrayList<>(Arrays.asList(destinationCourse, destinationVideo,
                            destinationSlide, destinationScript, destinationTimedScript));

                    if (langName.equals("English")) {
                        destinationOriginalScript = destinationScript.resolve("Original_Scripts");
                        foldersToCreate.add(destinationOriginalScript);
                    }

                    try {
                        for (Path folder : foldersToCreate) {
                            ServiceUtility.createFolder(folder);
                        }
                    } catch (IOException e) {
                        logger.error("Exception occurred while creating directories: ", e);
                    }

                    try {
                        String resolution = "";
                        if (quality.equals("Medium")) {
                            resolution = "_480p";
                        } else if (quality.equals("Low")) {
                            resolution = "_360p";
                        }

                        Path basePath;
                        Path originalPath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                tutorialPath1);

                        if (resolution == null || resolution.isEmpty()) {
                            basePath = originalPath;
                        } else {
                            String resolutionUrl = ServiceUtility.getVideoResolutionPath(tutorialPath1, resolution);

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
                        }

//                        String topicNameForTutorial = "";
//                        if (!resolution.isEmpty()) {
//                            topicNameForTutorial = topicName + resolution;
//                        } else {
//                            topicNameForTutorial = topicName;
//                        }
                        String finaltutorialName = "";
                        Path sourcePath;

                        Path webmSourcePath = replaceExtension(basePath, ".webm");
                        if (webmSourcePath.toFile().exists()) {
                            sourcePath = webmSourcePath;
                            finaltutorialName = topicName + ".webm";
                        } else {
                            sourcePath = basePath; // replaceExtension(basePath, ".mp4");
                            finaltutorialName = topicName + ".mp4";
                        }

                        Path destainationPath1 = destinationVideo.resolve(finaltutorialName);

                        File sourceFile = sourcePath.toFile();
                        logger.info("last assignment of source path :{}", sourcePath.toString());
                        if (sourceFile.exists()) {
                            logger.info(sourcePath.toString());

                            copyFileUsingRsync(sourcePath, destainationPath1);

                        }

                        Path sourcePathofSlide = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                                slide);
                        File sourceofSlide = sourcePathofSlide.toFile();

                        if (sourceofSlide.exists()) {
                            // Extract the file name (e.g., "myslide.zip")
                            String slideFileName = sourcePathofSlide.getFileName().toString();
                            String fileExtension = slideFileName.contains(".")
                                    ? slideFileName.substring(slideFileName.lastIndexOf('.'))
                                    : "";

                            // Create destination path with filename preserved
                            Path destainationSlidePath = destinationSlide.resolve(topicName + fileExtension);

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
                            destainationTimedScriptPath = destinationTimedScript.resolve(topicName + fileExtension);

                        } else {
                            destainationTimedScriptPath = destinationTimedScript.resolve(topicName + ".odt");
                        }

                        File sourceofTimedScript = sourcePathofTimedScript.toFile();
                        if (sourceofTimedScript.exists()) {

                            copyFileUsingRsync(sourcePathofTimedScript, destainationTimedScriptPath);

                        }

                        if (!original_script.isEmpty()) {
                            Path sourcePathofOriginalScript = Paths
                                    .get(env.getProperty("spring.applicationexternalPath.name"), original_script);
                            Path destainationPathOfOriginalScript = destinationOriginalScript
                                    .resolve(topicName + ".odt");

                            File sourceOriginalScript = sourcePathofOriginalScript.toFile();
                            if (sourceOriginalScript.exists()) {

                                copyFileUsingRsync(sourcePathofOriginalScript, destainationPathOfOriginalScript);

                            }

                        }

                    } catch (Exception e) {

                        logger.error("Exception: ", e);
                    }

                }

                generateLocalIndexHtml(courseId, catIds, lanIds, indexHtmlPath.toString(), destinationDirectory1, env);
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

    private boolean deleteParentDir(String parentZipfolder, Environment env) {
        Path zipFilePathDirectory = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryHealthTutorialZipFiles, parentZipfolder);

        File parentDir = zipFilePathDirectory.toFile();

        boolean isDeleted = false;

        if (parentDir.exists()) {
            try {
                deleteFolderRecursively(parentDir.toPath());
                if (!parentDir.exists()) {
                    isDeleted = true; // folder deleted successfully
                }
            } catch (IOException e) {
                logger.error("Failed to delete parent folder recursively: {}", parentDir.getAbsolutePath(), e);
            }
        }
        return isDeleted;
    }

    public String getZipName(int courseId, String courseName, String quality, Set<Integer> catIds, Set<Integer> lanIds,
            Environment env) {

        StringBuilder tempCatIdFolder = new StringBuilder();
        StringBuilder tempLanIdFolder = new StringBuilder();
        StringBuilder tempcourseIdFolder = new StringBuilder();
        StringBuilder sb = new StringBuilder();

        for (int catId : catIds) {
            tempCatIdFolder.append("cat").append(catId).append("_");
        }
        for (int lanId : lanIds) {
            Language lan = lanService.getById(lanId);
            tempLanIdFolder.append("lan").append(lanId).append("_");
            // if (lanId != 22) {
            sb.append(lan.getLangName().replace(' ', '_')).append("_");
            // }

        }

        if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '_') {
            sb.deleteCharAt(sb.length() - 1);
        }
        String newzipNameWithoutExtentions = courseName.replace(' ', '_') + "_" + sb.toString();
        String parentZipfolder = "";
        if (courseId != 0) {
            tempcourseIdFolder.append("CourseId").append(courseId).append("_");
            parentZipfolder = tempCatIdFolder.toString() + tempLanIdFolder.toString() + tempcourseIdFolder.toString()
                    + quality;
        } else {
            parentZipfolder = tempCatIdFolder.toString() + tempLanIdFolder.toString() + quality;
        }

        String key = parentZipfolder;
        String zipName = ZipNames.putIfAbsent(key, "");
        if (zipName == null) {

            createZip(courseId, courseName, quality, catIds, lanIds, env);
            return null;
        } else if (zipName.isEmpty()) {
            // processing already progress
            return null;

        }

        else {

            Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"), zipName);
            String fileName = zipFilePathName.getFileName().toString();
            String newFileName = newzipNameWithoutExtentions + ".zip";
            if (!fileName.equals(newFileName)) {
                boolean isDeleted = deleteParentDir(parentZipfolder, env);

                if (isDeleted) {
                    ZipNames.remove(key);
                    createZip(courseId, courseName, quality, catIds, lanIds, env);

                    logger.info("Parent dir deleted successfully: {} " + parentZipfolder);
                } else {
                    logger.info("Failed to delete the Parent dir: {} " + parentZipfolder);
                }

                return null;
            }

        }

        return zipName;
    }

    public void deleteKeyFromZipNamesAndHealthTutorialZipIfExists(int catId, Optional<Integer> lanId,
            Optional<Integer> courseId, Environment env) {
        // Construct identifiers to match in folder names
        String catIdentifier = "cat" + catId;
        if (lanId == null)
            lanId = Optional.empty();
        if (courseId == null)
            courseId = Optional.empty();

        int lanIdInt = lanId.orElse(0);
        int courseIdInt = courseId.orElse(0);
        String lanIdentifier = "lan" + lanIdInt;
        String courseIdentifier = "CourseId" + courseIdInt;

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
            if ((folderName.contains(catIdentifier) && folderName.contains(lanIdentifier))
                    || (folderName.contains(catIdentifier) && folderName.contains(courseIdentifier))) {
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

    private Map<String, Object> getHealthTutorialData(int courseId, Set<Integer> catIds, Set<Integer> lanIds,
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

        List<Category> catList = new ArrayList<>();
        List<Language> lanList = new ArrayList<>();
        List<Language> finalLanList = new ArrayList<>();
        List<Topic> topics = new ArrayList<>();
        Set<Tutorial> uniqueTutorials = new LinkedHashSet<>();
        Set<Category> uniqueCategories = new LinkedHashSet<>();
        Set<Topic> uniqueTopics = new LinkedHashSet<>();
        Set<Language> uniqueLanguage = new LinkedHashSet<>();
        List<TopicCategoryMapping> tcmList = new ArrayList<>();
        List<ContributorAssignedTutorial> conList = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<Map<String, String>> tutorialJsonList = new ArrayList<>();
        List<Tutorial> tutorials = new ArrayList<>();
        boolean flag = true;

        for (int catId : catIds) {
            Category cat = catService.findByid(catId);
            for (int lanId : lanIds) {
                Language lan = lanService.getById(lanId);
                if (flag) {
                    lanList.add(lan);

                }
                if (courseId == 0) {
                    tcmList = topicCatMappingService.findAllByCategory(cat);
                    conList = conService.findAllByTopicCatAndLan(tcmList, lan);
                    tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);
                    uniqueTutorials.addAll(tutorials);
                }

            }
            flag = false;

        }

        if (courseId != 0) {
            List<CourseCatTopicMapping> cctmList = courseCatTopicService
                    .findAllByCourse_CourseIdAndStatusTrue(courseId);

            for (CourseCatTopicMapping temp : cctmList) {
                Category cat = temp.getCat();
                if (cat.isStatus()) {
                    Topic topic = temp.getTopic();
                    TopicCategoryMapping tcm = topicCatMappingService.findAllByCategoryAndTopic(cat, topic);
                    tcmList.add(tcm);
                }

            }

            conList = conService.findByTopicCatLanList(tcmList, lanList);
            logger.info("conList size in getHealthTutorialData : {}", conList.size());
            tutorials = tutorialService.findAllByconAssignedTutorialAndStatus(conList);
            uniqueTutorials.addAll(tutorials);

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
                    sourcePath = Paths.get(catName, File.separator, langName, File.separator, "Tutorials",
                            File.separator, topicName + ".webm");
                    ;

                } else {
                    sourcePath = Paths.get(catName, File.separator, langName, File.separator, "Tutorials",
                            File.separator, topicName + ".mp4");

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
            finalLanList.addAll(uniqueLanguage);

            tutorialList.sort(Comparator.comparing(
                    tempTutorial -> tempTutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()));

            topics.sort(Comparator.comparing(Topic::getTopicName));

            tutorialList.sort(Comparator.comparing(
                    tempTutorial -> tempTutorial.getConAssignedTutorial().getTopicCatId().getCat().getCatName()));

            Collections.sort(catList, Category.SortByOrderValue);

            tutorialList.sort(
                    Comparator.comparing(tempTutorial -> tempTutorial.getConAssignedTutorial().getLan().getLangName()));

            finalLanList.sort(Comparator.comparing(Language::getLangName));

        }

        modelAttributes.put("tutorialList", tutorialList);
        modelAttributes.put("categories", catList);
        modelAttributes.put("topics", topics);
        modelAttributes.put("languages", finalLanList);
        modelAttributes.put("languageCount", finalLanList.size());
        // getModelData(modelAttributes, catIds, lanIds, 0);
        return modelAttributes;
    }

}
