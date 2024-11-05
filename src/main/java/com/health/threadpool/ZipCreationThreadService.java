package com.health.threadpool;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.github.fracpete.processoutput4j.output.CollectingProcessOutput;
import com.github.fracpete.rsync4j.RSync;
import com.health.model.PackageContainer;
import com.health.model.PackageLanguage;
import com.health.model.TutorialWithWeekAndPackage;
import com.health.model.VideoResource;
import com.health.model.WeekTitleVideo;
import com.health.service.PackageContainerService;
import com.health.service.PackageLanguageService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;

@Service
public class ZipCreationThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ZipCreationThreadService.class);

    @Autowired
    private PackageLanguageService packLanService;

    @Autowired
    private PackageContainerService packageContainerService;

    private final ConcurrentHashMap<String, String> ZipNames = new ConcurrentHashMap<>();

    private String createZipforPackageAndLan(String originalPackageName, String langName, String document,
            Environment env) {
        String zipUrl = "";
        try {
            zipUrl = ServiceUtility.createFileWithSubDirectoriesforTrainingModule(originalPackageName, langName,
                    document, env);
            FileUtils.deleteDirectory(Paths.get(document).toFile());

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
                Path destInationDirectory1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                        CommonData.uploadDirectoryTrainingModuleZipFiles, sdfString, File.separator, packageName);

                for (WeekTitleVideo temp : weekTitleList) {
                    String weekName = temp.getWeek().getWeekName().replace(' ', '_');
                    String title = temp.getTitle().replace(' ', '_');

                    String tutorialPath = temp.getVideoResource().getVideoPath();

                    Path destInationDirectoryforLanAndWeek = Paths.get(destInationDirectory1.toString(), File.separator,
                            langName, File.separator, weekName);
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

                    } catch (Exception e) {

                        logger.error("Exception: ", e);
                    }

                }
                String temp = destInationDirectory1.toString();
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

}
