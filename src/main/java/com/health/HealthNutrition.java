package com.health;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableAsync;

import com.health.threadpool.TaskProcessingService;
import com.health.utility.CommonData;

@ServletComponentScan
@SpringBootApplication
@EnableCaching
@PropertySource("classpath:git.properties")
@EnableAsync
public class HealthNutrition extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer
        implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(HealthNutrition.class);

    @Autowired
    private Environment env;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @Value("${git.commit.id}")
    private String gitCommitId;

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(HealthNutrition.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(HealthNutrition.class, args);

    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting application {}", gitCommitId);

        try {

            taskProcessingService.createOutlineFile();
            taskProcessingService.addAllBrochureToQueue();
            taskProcessingService.addAllResearchPapertoQueue();
            taskProcessingService.addAllTuttorialsToQueue();
            taskProcessingService.intializeQueue();
            taskProcessingService.deleteQueueByApiStatus();
            taskProcessingService.queueProcessor();

        } catch (Exception e) {

            logger.error("Exception: ", e);
        }

        String baseDir = env.getProperty("spring.applicationexternalPath.name");
        new File(baseDir + CommonData.uploadDirectoryCategory).mkdirs();
        new File(baseDir + CommonData.uploadDirectoryQuestion).mkdirs();
        new File(baseDir + CommonData.uploadDirectoryTutorial).mkdirs();
        new File(baseDir + CommonData.uploadUserImage).mkdirs();
        new File(baseDir + CommonData.uploadDirectoryTestimonial).mkdirs();
        new File(baseDir + CommonData.uploadDirectoryMasterTrainer).mkdirs();
        new File(baseDir + CommonData.uploadDirectoryEvent).mkdirs();
        new File(baseDir + CommonData.uploadDirectoryMasterTrainerFeedback).mkdirs();
        new File(baseDir + CommonData.uploadLiveTutorial).mkdirs();

    }

}