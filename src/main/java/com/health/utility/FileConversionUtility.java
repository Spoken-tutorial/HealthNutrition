package com.health.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class FileConversionUtility {

    private static final Logger logger = LoggerFactory.getLogger(FileConversionUtility.class);

    @Value("${spring.applicationexternalPath.name}")
    private String mediaRoot;

    public String convertDoctoPdf(String inputFilePath, String libreOfficeCommand) {
        String document = "";
        boolean shouldConvert = true;

        Path docPath = Paths.get(mediaRoot, inputFilePath);
        Path pdfDirPath = docPath.getParent();

        try {
            if (docPath == null || pdfDirPath == null || !Files.exists(docPath)) {
                logger.warn("Invalid document or directory path.");
                return "";
            }

            String fileNameWithoutExt = docPath.getFileName().toString().replaceFirst("[.][^.]+$", "");
            Path pdfPath = Paths.get(pdfDirPath.toString(), fileNameWithoutExt + ".pdf");

            if (Files.exists(pdfPath)) {
                if (Files.getLastModifiedTime(pdfPath).toMillis() > Files.getLastModifiedTime(docPath).toMillis()) {
                    shouldConvert = false;
                }
            }

            if (shouldConvert) {
                // Replace placeholders
                String commandStr = libreOfficeCommand.replace("{input}", docPath.toString()).replace("{outdir}",
                        pdfDirPath.toString());

                logger.debug("Resolved command: {}", commandStr);

                List<String> commandTokens = tokenizeCommand(commandStr);

                ProcessBuilder processBuilder = new ProcessBuilder(commandTokens);
                Process process = processBuilder.start();

                // Read standard output
                try (InputStream inputStream = process.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        logger.info("LibreOffice Output: {}", line);
                    }
                }

                // Read error output
                try (InputStream errorStream = process.getErrorStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        logger.warn("LibreOffice Error: {}", line);
                    }
                }

                boolean finished = process.waitFor(CommonData.TIME_UNIT_FOR_WAIT, TimeUnit.SECONDS);
                int exitCode = finished ? process.exitValue() : 1;

                if (!finished) {
                    logger.warn("LibreOffice process did not finish in time. Destroying process...");
                    process.destroy();
                }

                if (exitCode == 0 && Files.exists(pdfPath)) {
                    String absolutePath = pdfPath.toString();
                    int indexToStart = absolutePath.indexOf("Media");
                    document = (indexToStart != -1) ? absolutePath.substring(indexToStart) : absolutePath;
                } else {
                    logger.error("Document conversion failed or timed out: {}", docPath);
                }
            }

        } catch (IOException | InterruptedException e) {
            logger.error("Exception during document conversion", e);
            Thread.currentThread().interrupt(); // preserve interrupt status
        }

        return document;
    }

    /**
     * Tokenizes a command string into individual arguments, respecting quoted
     * segments that may contain spaces.
     */
    private List<String> tokenizeCommand(String commandTemplate) {
        List<String> tokens = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();

        for (char c : commandTemplate.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (current.length() > 0) {
                    tokens.add(current.toString());
                    current.setLength(0);
                }
            } else {
                current.append(c);
            }
        }

        if (current.length() > 0) {
            tokens.add(current.toString());
        }

        return tokens;
    }

    public String generateThumbnailFromPdfAndSave(String pdfFilePath) {
        Path pdfPath = Paths.get(mediaRoot, pdfFilePath);
        Path thumbnailDirPath = pdfPath.getParent();
        String fileNameWithoutExt = pdfPath.getFileName().toString().replaceFirst("[.][^.]+$", "");
        boolean shouldConvert = true;
        String resultPathofThumbnail = "";

        // Check if the PDF path is valid
        if (pdfFilePath.isEmpty() || pdfFilePath.equals("") || pdfPath == null || !Files.exists(pdfPath)) {
            logger.warn("PDF file does not exist: {}", pdfPath);
            return "";
        }
        if (!Files.isReadable(pdfPath)) {
            logger.error("PDF file is not readable (access denied): {}", pdfPath);
            return "";
        }

        // Define thumbnail path
        Path thumbnailPath = Paths.get(thumbnailDirPath.toString(), fileNameWithoutExt + ".png");
        String thumbnailPathStr = thumbnailPath.toString();

        try (PDDocument document = PDDocument.load(pdfPath.toFile())) {
            if (Files.exists(thumbnailPath)) {
                if (Files.getLastModifiedTime(thumbnailPath).toMillis() > Files.getLastModifiedTime(pdfPath)
                        .toMillis()) {
                    shouldConvert = false;
                }
            }

            if (shouldConvert) {
                PDFRenderer pdfRenderer = new PDFRenderer(document);
                BufferedImage image = pdfRenderer.renderImageWithDPI(0, 15); // Render first page at 15 DPI

                if (image.getHeight() > 200) {
                    int newDPI = (int) Math.ceil(15.0 * 200 / image.getHeight());
                    if (newDPI > 2 && newDPI < 15) {
                        image = pdfRenderer.renderImageWithDPI(0, newDPI);
                    }
                }

                // Save thumbnail image
                File outputFile = thumbnailPath.toFile();
                ImageIO.write(image, "png", outputFile);
            }

            int indexToStart = thumbnailPathStr.indexOf("Media");
            resultPathofThumbnail = (indexToStart != -1) ? thumbnailPathStr.substring(indexToStart) : thumbnailPathStr;

            ServiceUtility.convertFilePathToUrl(resultPathofThumbnail);

        } catch (Exception e) {
            logger.error("Error in thumbnail creation for file: {}", pdfPath, e);
        }

        return resultPathofThumbnail;
    }
}
