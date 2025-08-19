package com.health.utility;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.model.User;
import com.health.repository.UserRepository;
import com.health.threadpool.TimeoutOutputStream;

/**
 * Utility class
 * 
 * @author om Prakash
 * @version 1.0
 *
 */

public class ServiceUtility {

    private static final Logger logger = LoggerFactory.getLogger(ServiceUtility.class);

    /**
     * To get current time
     * 
     * @return Timestamp object
     */

    @Autowired
    private JavaMailSender mailSender;

    public static Timestamp getCurrentTime() { // Current Date

        Date date = new Date();
        long t = date.getTime();
        Timestamp st = new Timestamp(t);

        return st;
    }

    /**
     * return no of days between present date and given date
     * 
     * @param date Timestamp object
     * @return String
     */
    public static String daysDifference(Timestamp date) { // days Difference Between 2 date(current - given)

        Timestamp presentdate = getCurrentTime();
        long difference = Math.abs(date.getTime() - presentdate.getTime());
        long differenceDate = difference / (24 * 60 * 60 * 1000);

        return Long.toString(differenceDate);
    }

    /**
     * to create folder in system
     * 
     * @param path relative path
     * @return
     * @throws IOException
     */
    public static boolean createFolder(String file) throws IOException { // check for existence of path
        boolean status = false;
        Path path = Paths.get(file);
        if (Files.createDirectories(path) != null) {
            status = true;
        }
        return status;

    }

    public static boolean createFolder(Path path) throws IOException { // check for existence of path
        boolean status = false;
        if (Files.exists(path))
            return status;
        if (Files.createDirectories(path) != null) {
            status = true;
        }
        return status;

    }

    /**
     * to upload file in system
     * 
     * @param uploadFile   Multipart object
     * @param pathToUpload relative path
     * @return
     * @throws Exception not found
     */

    public static String sanitizeFilename(String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return fileName;
        }

        String newFileName = fileName.replace("\u2018\u2018", "\"") // ‘‘
                .replace("\u2019\u2019", "\"") // ’’
                .replace("\u2018", "'") // ‘
                .replace("\u2019", "'") // ’
                .replace("\u2014", "-") // —
                .replace("\u3164", " ") // Invisible Space
                .replace("\u2022", "."); // •
        if (!fileName.equals(newFileName)) {
            logger.info("Replaced fileName: {} New FileName: {}", fileName, newFileName);
        }
        return newFileName;
    }

    public static String sanitizeCourseName(String courseName) {

        String tempCourseName = sanitizeFilename(courseName);

        String sanitized = tempCourseName.replaceAll(CommonData.ILLEGAL_CHARS, "_");

        sanitized = sanitized.trim().replaceAll("^_+|_+$", "");

        // Limit length (Windows has path length limits taking as 100 now)
        int maxLength = 100;
        if (sanitized.length() > maxLength) {
            sanitized = sanitized.substring(0, maxLength);
        }

        return sanitized;
    }

    private static String uploadFile(MultipartFile uploadFile, String pathToUpload) throws Exception { // uploading file
        String path = null;

        Path fileNameAndPath = Paths.get(pathToUpload, uploadFile.getOriginalFilename());

        Files.write(fileNameAndPath, uploadFile.getBytes());
        logger.info("File Name Path1: {} ", fileNameAndPath.toString());
        path = fileNameAndPath.toString();

        return path;
    }

    public static int extractInteger(String str) {

        String numberStr = str.replaceAll("\\D+", ""); // \\D+ matches all non-digit characters
        if (!numberStr.isEmpty()) {
            return Integer.parseInt(numberStr);
        } else {
            return 0;
        }
    }

    public static String getZipfileName(Path zipUrl) {

        return zipUrl.getFileName().toString();
    }

    public static String uploadMediaFile(MultipartFile file, Environment env, String folder) throws Exception {

        String fileName = file.getOriginalFilename();
        String newFileName = sanitizeFilename(fileName);
        MultipartFile renamedFile = new RenamedMultipartFile(file, newFileName);
        createFolder(env.getProperty("spring.applicationexternalPath.name") + folder);
        String pathtoUploadPoster = ServiceUtility.uploadFile(renamedFile,
                env.getProperty("spring.applicationexternalPath.name") + folder);
        int indexToStart = pathtoUploadPoster.indexOf("Media");

        String document = pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
        return document;

    }

    public static String generateImageFromPdfAndSave(String pdfFilePath, Environment env, String outputFolderPath)
            throws IOException {

        String pathName = env.getProperty("spring.applicationexternalPath.name");
        try (PDDocument document = PDDocument.load(new File(pathName + pdfFilePath))) {
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            BufferedImage image = pdfRenderer.renderImageWithDPI(0, 15); // Render the first page with 300 DPI

            // logger.info("Image Width and Height:{} {}", image.getWidth(),
            // image.getHeight());

            if (image.getHeight() > 200) {
                int newDPI = (int) Math.ceil(15.0 * 200 / image.getHeight());
                if (newDPI > 2 && newDPI < 15) {
                    image = pdfRenderer.renderImageWithDPI(0, newDPI);
                    // logger.info("After setting dpi {}, Width and Height of Image: {} {}", newDPI,
                    // image.getWidth(), image.getHeight());
                }
            }

            // Save the image to the output folder
            String fileName = outputFolderPath + "/" + "thumbnail.png";
            File outputFile = new File(pathName, fileName);
            ImageIO.write(image, "png", outputFile);

            // Convert the byte array to a Base64-encoded string
            // String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            return fileName;
        }
    }

    public static List<String> UploadMediaFileAndCreateThumbnail(MultipartFile file, Environment env, String folder)
            throws Exception {

        List<String> documents = new ArrayList<>(2);
        String pdfFile = "";
        String thumbnail = null;

        try {
            pdfFile = uploadMediaFile(file, env, folder);
            documents.add(pdfFile);
            boolean checkPdf = pdfFile.toLowerCase().endsWith(".pdf");

            if (checkPdf == true) {
                try {
                    thumbnail = generateImageFromPdfAndSave(pdfFile, env, folder);

                } catch (Exception e) {
                    logger.error("Exception Generating thumbnail from  :{}", pdfFile, e);
                    // ignore
                }

            }
            documents.add(thumbnail);
            return documents;

        } catch (Exception e) {
            logger.error("Exception Generating uploading file from  :{}", file, e);
            throw e;
        }

    }

    /**
     * to upload video file in system
     * 
     * @param file         multipart object
     * @param pathToUpload relative path
     * @return
     * @throws Exception not found
     */
    private static String uploadVideoFile(MultipartFile file, String pathToUpload) throws Exception { // uploading file
        String path = null;

        Path fileNameAndPath = Paths.get(pathToUpload, file.getOriginalFilename());

        Files.write(fileNameAndPath, file.getBytes());
        logger.info("File Name And Path: {}", fileNameAndPath.toString());
        path = fileNameAndPath.toString();

        return path;
    }

    /**
     * to check whether PDF file or not
     * 
     * @param pdfFile MultipartFile object
     * @return
     */
    public static boolean checkFileExtensiononeFilePDF(MultipartFile pdfFile) { // validate file against PDF extension

        if (!pdfFile.getOriginalFilename().endsWith(".pdf") && !pdfFile.getOriginalFilename().endsWith(".PDF")) {
            return false;
        }

        return true;
    }

    /**
     * to check whether CSV file or not
     * 
     * @param pdfFile MultipartFile object
     * @return
     */
    public static boolean checkFileExtensiononeFileCSV(MultipartFile pdfFile) { // validate file against PDF extension

        if (!pdfFile.getOriginalFilename().endsWith(".csv") && !pdfFile.getOriginalFilename().endsWith(".CSV")) {
            return false;
        }

        return true;
    }

    /**
     * to check whether IMAGE file or not
     * 
     * @param temp MultipartFile object
     * @return
     */
    public static boolean checkFileExtensionImage(MultipartFile temp) { // validate file against Image Extension

        if (!temp.getOriginalFilename().endsWith(".jpg") && !temp.getOriginalFilename().endsWith(".jpeg")
                && !temp.getOriginalFilename().endsWith(".png") && !temp.getOriginalFilename().endsWith(".JPG")
                && !temp.getOriginalFilename().endsWith(".JPEG") && !temp.getOriginalFilename().endsWith(".PNG")) {

            return false;
        }

        return true;
    }

    /**
     * to check whether MP4 file or not
     * 
     * @param videoFile MultipartFile object
     * @return
     */
    public static boolean checkFileExtensionVideo(MultipartFile videoFile) { // validate file against Image Extension

        if (!videoFile.getOriginalFilename().endsWith(".mp4") && !videoFile.getOriginalFilename().endsWith(".mov")
                && !videoFile.getOriginalFilename().endsWith(".MP4")
                && !videoFile.getOriginalFilename().endsWith(".MOV")) {
            return false;
        }

        return true;
    }

    /**
     * to check whether ZIP file or not
     * 
     * @param temp MultipartFile object
     * @return
     */
    public static boolean checkFileExtensionZip(MultipartFile temp) { // validate file against HTML Extension

        if (!temp.getOriginalFilename().endsWith(".zip") && !temp.getOriginalFilename().endsWith(".ZIP")) {

            return false;
        }

        return true;
    }

    /**
     * to check whether size of file is within the limit
     * 
     * @param temp MultipartFile object
     * @return
     */
    public static boolean checkVideoSize(MultipartFile temp) {

        if (temp.getSize() > CommonData.videoSize) {
            return false;
        }
        return true;
    }

    /**
     * to check whether size of Testimonial video is within the limit
     * 
     * @param temp MultipartFile object
     * @return
     */
    public static boolean checkVideoSizeTestimonial(MultipartFile temp) {
        logger.info("Video Size:{}", temp.getSize());
        if (temp.getSize() > CommonData.videoSizeTest) {

            return false;
        }
        return true;
    }

    public static boolean checkVideoSizeSpokenVideo(MultipartFile temp) {
        logger.info("Video Size:{}", temp.getSize());
        if (temp.getSize() > CommonData.videoSizeSpokenVideo) {

            return false;
        }
        return true;
    }

    public static boolean checkVideoSizePromoVideo(MultipartFile temp) {
        logger.info("Video Size: {}", temp.getSize());
        if (temp.getSize() > CommonData.videoSizePromoVideo) {

            return false;
        }
        return true;
    }

    /**
     * to check whether size of file is within the limit
     * 
     * @param temp MultipartFile object
     * @return
     */
    public static boolean checkScriptSlideProfileQuestion(MultipartFile temp) {

        if (temp.getSize() > CommonData.fileSize) {
            return false;
        }
        return true;
    }

    /**
     * to check whether size of file is within the limit
     * 
     * @param temp MultipartFile object
     * @return
     */
    public static boolean checkCatAndAllImageFile(MultipartFile temp) {

        if (temp.getSize() > CommonData.categoryFileSizeImageFileSize) {
            return false;
        }
        return true;
    }

    /***** To add missing html tag in string content *******/
    public static void addMissingHtmlTag(Element element) {
        Elements children = element.children();
        for (int i = 0; i < children.size(); i++) {
            Element child = children.get(i);
            if (child.tagName().equals("p")) {
                Element nextSibling = child.nextElementSibling();
                if (nextSibling != null && !nextSibling.tagName().equals("p")) {
                    child.appendChild(nextSibling);
                    i--; // Re-check the current index as the sibling has moved
                }
            }
            addMissingHtmlTag(child); // Recursive call to add missing html tag
        }
    }

    /**
     * to get present working path
     * 
     * @return
     */
    public static String presentDirectory() {
        Path currentRelativePath = Paths.get("");
        String currentpath = currentRelativePath.toAbsolutePath().toString();
        return currentpath;

    }

    /**
     * Craete Zip File
     * 
     * @throws IOException
     **/

    public static String createZipFile(List<String> fileUrls, Environment env) throws IOException {
        String document = "";
        Path zipFilePathDirectory = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryScriptZipFiles);

        Files.createDirectories(zipFilePathDirectory);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String zipFileName = "scripts-" + sdf.format(new Date()) + ".zip";

        Path zipFilePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryScriptZipFiles, zipFileName);

        try (OutputStream fos = Files.newOutputStream(zipFilePath); ZipOutputStream zos = new ZipOutputStream(fos)) {

            byte[] buffer = new byte[16384];

            for (String fileUrl : fileUrls) {
                Path fileUrlPath = Paths.get(env.getProperty("spring.applicationexternalPath.name"), fileUrl);

                File file = fileUrlPath.toFile();

                if (!file.exists()) {
                    logger.info("Sciptfile not found: {}" + file);
                    continue;
                }

                try (FileInputStream fis = new FileInputStream(file)) {
                    zos.putNextEntry(new ZipEntry(file.getName()));

                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }

                    zos.closeEntry();
                }
            }

            String temp = zipFilePath.toString();
            int indexToStart = temp.indexOf("Media");
            document = temp.substring(indexToStart, temp.length());

        } catch (Exception e) {
            logger.error("Exception Error", e);
        }

        return document;
    }

    /****
     * Create ZipFileWithSubDirectories
     * 
     */

    private static void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));

            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));

            }
            zipOut.closeEntry();
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }

        try (FileInputStream fis = new FileInputStream(fileToZip)) {
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[16384];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
        } catch (Exception e) {
            logger.error("Exception Error", e);
        }

    }

    public static String zipFileWithSubDirectories(String sourceDirurl, Environment env) throws IOException {

        String document = "";
        Path zipFilePathDirectory = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryScriptZipFiles);

        Files.createDirectories(zipFilePathDirectory);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String zipFileName = "scripts-" + sdf.format(new Date()) + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryScriptZipFiles, zipFileName);

        Path sourceDirlName = Paths.get(env.getProperty("spring.applicationexternalPath.name"), sourceDirurl);

        try (OutputStream fos = Files.newOutputStream(zipFilePathName);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            File filetoZip = new File(sourceDirlName.toString());
            zipFile(filetoZip, filetoZip.getName(), zos);

            String temp = zipFilePathName.toString();
            int indexToStart = temp.indexOf("Media");
            document = temp.substring(indexToStart, temp.length());

        } catch (IOException e) {
            logger.error("Exception Error  ", e);
        }

        return document;
    }

    public static String createFileWithSubDirectoriesforTrainingModule(String originalPackageName, String langName,
            String sourceDirurl, Environment env) throws IOException {

        String packageName = originalPackageName.replace(' ', '_');

        String document = "";
        Path zipFilePathDirectory = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryTrainingModuleZipFiles);

        Files.createDirectories(zipFilePathDirectory);

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String zipFileName = packageName + "_" + langName + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryTrainingModuleZipFiles, zipFileName);

        Path sourceDirlName = Paths.get(env.getProperty("spring.applicationexternalPath.name"), sourceDirurl);

        try (OutputStream fos = Files.newOutputStream(zipFilePathName);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            File filetoZip = new File(sourceDirlName.toString());
            zipFile(filetoZip, filetoZip.getName(), zos);

            String temp = zipFilePathName.toString();
            int indexToStart = temp.indexOf("Media");
            document = temp.substring(indexToStart, temp.length());

        } catch (IOException e) {
            logger.error("Exception Error  ", e);
        }

        return document;
    }

    public static boolean IsPackageAndLanZipExist(String originalPackageName, String langName, Environment env) {
        String packageName = originalPackageName.replace(' ', '_');

        String zipFileName = packageName + "_" + langName + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryTrainingModuleZipFiles, zipFileName);

        File file = zipFilePathName.toFile();

        return file.exists();

    }

    public static String getPackageAndLanZipPath(String originalPackageName, String langName, Environment env) {
        String packageName = originalPackageName.replace(' ', '_');

        String zipFileName = packageName + "_" + langName + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryTrainingModuleZipFiles, zipFileName);

        String temp = zipFilePathName.toString();
        int indexToStart = temp.indexOf("Media");
        String document = temp.substring(indexToStart, temp.length());
        return document;

    }

    public static void deletePackageAndLanZipIfExists(String originalPackageName, String langName, Environment env) {
        String packageName = originalPackageName.replace(' ', '_');

        String zipFileName = packageName + "_" + langName + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryTrainingModuleZipFiles, zipFileName);

        File file = zipFilePathName.toFile();

        if (file.exists()) {

            boolean isDeleted = file.delete();

            if (isDeleted) {
                logger.info("Zip File deleted successfully: {} " + zipFileName);
            } else {
                logger.info("Failed to delete the zip file: {} " + zipFileName);
            }
        } else {
            logger.info(" ZipFile does not exist: {} " + zipFileName);
        }
    }

    public static String createFileWithSubDirectoriesforHealthTutorial(String parentFolderName,
            String zipfileNameWithouExtention, String sourceDirurl, Environment env) throws IOException {

        String document = "";
        Path zipFilePathDirectory = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryHealthTutorialZipFiles, parentFolderName);

        Files.createDirectories(zipFilePathDirectory);

        // SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
        String zipFileName = zipfileNameWithouExtention + ".zip";

        Path zipFilePathName = Paths.get(zipFilePathDirectory.toString(), zipFileName);
        if (!zipFilePathName.toFile().exists()) {

        }

        Path sourceDirlName = Paths.get(env.getProperty("spring.applicationexternalPath.name"), sourceDirurl);

        try (OutputStream fos = Files.newOutputStream(zipFilePathName);
                ZipOutputStream zos = new ZipOutputStream(fos)) {
            File filetoZip = new File(sourceDirlName.toString());
            zipFile(filetoZip, filetoZip.getName(), zos);

            String temp = zipFilePathName.toString();
            int indexToStart = temp.indexOf("Media");
            document = temp.substring(indexToStart, temp.length());

        } catch (IOException e) {
            logger.error("Exception Error  ", e);
        }

        return document;
    }

    public static boolean IsCourseNameAndLanZipExist(String parentZipfolder, String zipNameWithoutExtention,
            Environment env) {

        String zipFileName = zipNameWithoutExtention + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryHealthTutorialZipFiles, parentZipfolder, zipFileName);

        File file = zipFilePathName.toFile();

        return file.exists();

    }

    public static String getCourseNameAndLanZipPath(String parentZipfolder, String zipNameWithoutExtention,
            Environment env) {
        // String catName = originalCategoryName.replace(' ', '_');

        String zipFileName = zipNameWithoutExtention + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryHealthTutorialZipFiles, parentZipfolder, zipFileName);

        String temp = zipFilePathName.toString();
        int indexToStart = temp.indexOf("Media");
        String document = temp.substring(indexToStart, temp.length());
        return document;

    }

    public static void deleteCategoryAndLanZipIfExists(String originalCategoryName, String langName, Environment env) {
        String catName = originalCategoryName.replace(' ', '_');

        String zipFileName = catName + "_" + langName + ".zip";

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadDirectoryHealthTutorialZipFiles, zipFileName);

        File file = zipFilePathName.toFile();

        if (file.exists()) {

            boolean isDeleted = file.delete();

            if (isDeleted) {
                logger.info("Zip File deleted successfully: {} " + zipFileName);
            } else {
                logger.info("Failed to delete the zip file: {} " + zipFileName);
            }
        } else {
            logger.info(" ZipFile does not exist: {} " + zipFileName);
        }
    }

    /**
     * File Info
     * 
     * @throws IOException
     **/

    public static List<String> FileInfoSizeAndCreationDate(String fileUrl, Environment env) {

        List<String> fileInfo = new ArrayList<>();
        try {

            Path fileUrlPath = Paths.get(env.getProperty("spring.applicationexternalPath.name"), fileUrl);

            BasicFileAttributes attr = Files.readAttributes(fileUrlPath, BasicFileAttributes.class);

            long fileSize = attr.size();
            FileTime date = attr.lastModifiedTime();
            attr.lastModifiedTime();
            SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
            String dateCreated = df.format(date.toMillis());

            fileInfo.add(Long.toString(fileSize));
            fileInfo.add(dateCreated);

        } catch (IOException e) {
            logger.error("Error reading file attributes: ", e);
        }
        return fileInfo;

    }

    /*
     * Delete Files of N old days
     * 
     */

    public static void deleteFilesOlderThanNDays(int days, Environment env, String dirPath) throws IOException {

        long cutOff = System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000);

        dirPath = env.getProperty("spring.applicationexternalPath.name") + dirPath;

        Files.list(Paths.get(dirPath)).forEach(path -> {

            try {
                if (Files.getLastModifiedTime(path).to(TimeUnit.MILLISECONDS) < cutOff) {
                    Files.delete(path);
                }
            } catch (IOException ix) {
                logger.error("Exception Error", ix);
            }

        });
    }

    /*
     * dowanload Manager
     * 
     */

    public static String downloadManager(String zipUrl, AtomicInteger downloadCount, int downloadLimit,
            long downloadTimeOut, Environment env, HttpServletResponse response) {
        String message = "Please try again after 30 minutes.";
        if (downloadCount.get() == downloadLimit) {

            return message;
        }
        downloadCount.incrementAndGet();
        logger.debug(" Increament downloadCount :{}", downloadCount.get());

        Path zipFilePathName = Paths.get(env.getProperty("spring.applicationexternalPath.name"), zipUrl);
        try (OutputStream os = new TimeoutOutputStream(response.getOutputStream(), downloadTimeOut);
                InputStream is = new BufferedInputStream(new FileInputStream(zipFilePathName.toFile()));) {

            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + ServiceUtility.getZipfileName(zipFilePathName));
            response.setContentLengthLong(Files.size(zipFilePathName));

            byte[] buffer = new byte[16384];
            int length;

            while ((length = is.read(buffer)) > 0) {

                // TimeoutOutputStream tos = new TimeoutOutputStream(os, downloadTimeOut);
                // tos.write(buffer, 0, length);

                os.write(buffer, 0, length);

            }
            os.flush();

        } catch (Exception e) {
            logger.info("Exception:{}", e.getMessage());
        } finally {
            downloadCount.decrementAndGet();
            logger.debug(" Decrement downloadCount :{}", downloadCount.get());
        }

        return null;

    }

    public static double getZipSizeInMB(String zipUrl, Environment env) {
        Path zipFilePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"), zipUrl);
        File zipFile = zipFilePath.toFile();
        double fileSizeInMB = 0;
        if (zipFile.exists() && zipFile.isFile()) {
            long fileSizeInBytes = zipFile.length();
            fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0);
            fileSizeInMB = new BigDecimal(fileSizeInMB).setScale(2, RoundingMode.HALF_UP).doubleValue();
        }
        return fileSizeInMB;

    }

    public static String getVideoResolutionPath(String VideoPath, String resolution) {
        int dotIndex = VideoPath.lastIndexOf('.');

        String base = VideoPath.substring(0, dotIndex);
        String extension = VideoPath.substring(dotIndex);

        String videoResolutionPath = base + resolution + extension;
        return videoResolutionPath;

    }

    /**
     * to validate email
     * 
     * @param email String object
     * @return
     */
    public static boolean checkEmailValidity(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;

        return pat.matcher(email).matches();
    }

    /**
     * to check numeral value in string
     * 
     * @param input String object
     * @return
     */
    public static boolean checkContainNumeralInString(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '0' || input.charAt(i) == '1' || input.charAt(i) == '2' || input.charAt(i) == '3'
                    || input.charAt(i) == '4' || input.charAt(i) == '5' || input.charAt(i) == '6'
                    || input.charAt(i) == '7' || input.charAt(i) == '8' || input.charAt(i) == '9') {
                return false;
            }
        }
        return true;
    }

    /**
     * to convert String into date Object
     * 
     * @param date String object
     * @return
     * @throws ParseException
     */

    public static java.sql.Date convertStringToDate(String date) throws ParseException {
        SimpleDateFormat sd1 = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date dateUtil = sd1.parse(date);
        return new java.sql.Date(dateUtil.getTime());
    }

    public static String convertFilePathToUrl(String thumbnailFile) {
        String result = thumbnailFile.replace("\\", "/");
        return result;
    }

    public void sendVerficationEmail(User user, String siteURL)
            throws UnsupportedEncodingException, MessagingException {
        String subject = "Please Verify Your Registration.";
        String senderName = "Spoken-Tutorial";
        String mailContent = "<p> Dear " + user.getFullName() + ",</p>";
        mailContent += "<p>Please click the Link below to verify your Registration: </p>";

        String verifyURL = siteURL + "/verify?code=" + user.getEmailVerificationCode();
        mailContent += "<h3> <a href=\"" + verifyURL + "\">VERIFY</a> </h3>";

        mailContent += "<p>Thank You </br> The Spoken Tutorials. </p>";

        MimeMessage message = mailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("mansigundre1@gmail.com", senderName);
            helper.setTo(user.getEmail());
            helper.setSubject(subject);
            helper.setText(mailContent, true);

            mailSender.send(message);

        } catch (MessagingException ex) {
            throw new RuntimeException("Error sending mail notification", ex);
        }
    }

    /**
     * 
     * @param emailVerificationCode
     * @return
     */

    public static boolean verify(String emailVerificationCode) {
        User user = UserRepository.findByVerificationCode(emailVerificationCode);

        if (user == null || user.isEnabled()) {
            return false;
        } else {
            UserRepository.enable(user.getId());
            return true;
        }

    }

    @Cacheable(cacheNames = "scriptapi")
    public static List<Integer> getApiVersion(String scripmanager_api, int catId, int TutorialId, int lanId) {

        int x = 1;
        List<Integer> listofScriptVersions = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        sb.append(scripmanager_api);
        sb.append("/");
        sb.append(catId);
        sb.append("/");
        sb.append(TutorialId);
        sb.append("/");
        sb.append(lanId);
        sb.append("/");
        String api_url = sb.toString();

        try {

            CloseableHttpClient httpClient = HttpClients.createDefault();

            HttpGet httpGet = new HttpGet(api_url);

            HttpResponse response = httpClient.execute(httpGet);

            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                String jsonResponse = EntityUtils.toString(response.getEntity());

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);

                JsonNode publishedArray = jsonNode.get("published");

                for (int i = 0; i < publishedArray.size(); i++) {
                    listofScriptVersions.add(publishedArray.get(i).asInt());
                }

                httpClient.close();
                Collections.reverse(listofScriptVersions);

                return listofScriptVersions;
            } else {
                logger.info("API request failed with status code:{}  ", statusCode);

                listofScriptVersions.add(x);
                return listofScriptVersions;
            }

            // Close the HTTP client

        } catch (Exception e) {
            e.printStackTrace();
            listofScriptVersions.add(x);
            return listofScriptVersions;

        }

    }

}
