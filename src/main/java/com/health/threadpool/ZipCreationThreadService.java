package com.health.threadpool;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.github.fracpete.processoutput4j.output.CollectingProcessOutput;
import com.github.fracpete.rsync4j.RSync;
import com.health.utility.ServiceUtility;

@Service
public class ZipCreationThreadService {

    private static final Logger logger = LoggerFactory.getLogger(ZipCreationThreadService.class);

    @Async
    public CompletableFuture<String> createZipAsynforPackageAndLan(String originalPackageName, String langName,
            String document, Environment env, Model model) {
        String zipUrl = "";
        try {
            zipUrl = ServiceUtility.createFileWithSubDirectoriesforTrainingModule(originalPackageName, langName,
                    document, env);
            FileUtils.deleteDirectory(Paths.get(document).toFile());
            if (zipUrl.isEmpty()) {
                model.addAttribute("error_msg", "Some Errors Occurred; please try again");
            } else {
                List<String> fileInfoList = ServiceUtility.FileInfoSizeAndCreationDate(zipUrl, env);
                if (fileInfoList != null) {
                    model.addAttribute("zipUrl", zipUrl);
                } else {
                    model.addAttribute("error_msg", "Some errors occurred; please try again");
                }
            }
        } catch (IOException e) {
            logger.error("Exception in Zip Creation: ", e);
            model.addAttribute("error_msg", "Some Errors Occurred; please contact Admin or try again");
        }
        return CompletableFuture.completedFuture(zipUrl);
    }

    public void copyFileUsingRsync(Path source, Path destination) {
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

}
