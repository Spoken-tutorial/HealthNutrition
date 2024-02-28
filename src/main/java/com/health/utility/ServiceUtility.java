package com.health.utility;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.health.model.Tutorial;
import com.health.model.User;
import com.health.repository.UserRepository;

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

    private static String uploadFile(MultipartFile uploadFile, String pathToUpload) throws Exception { // uploading file
        String path = null;

        Path fileNameAndPath = Paths.get(pathToUpload, uploadFile.getOriginalFilename());

        Files.write(fileNameAndPath, uploadFile.getBytes());
        logger.info("File Name Path1: {} ", fileNameAndPath.toString());
        path = fileNameAndPath.toString();

        return path;
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

    /************************
     * Create HTml file from Url for testing
     ******************/
    public static void createHtmlWithoutImagesAndVideos(Tutorial tut, String mediaRoot, String url) {
        try {

            Document doc = Jsoup.connect(url).get();

            Elements imgTags = doc.select("img");
            for (Element img : imgTags) {
                img.remove();
            }

            Elements videoTags = doc.select("video");
            for (Element video : videoTags) {
                video.remove();
            }

            Elements sourceTags = doc.select("source");
            for (Element source : sourceTags) {
                source.remove();
            }

            Path path = Paths.get(mediaRoot, CommonData.uploadDirectoryScriptHtmlFile);

            Files.createDirectories(path);

            Path filePath = Paths.get(mediaRoot, CommonData.uploadDirectoryScriptHtmlFile,
                    tut.getTutorialId() + ".html");

            Files.writeString(filePath, doc.outerHtml());

            String temp = filePath.toString();

            int indexToStart = temp.indexOf("Media");

            String document = temp.substring(indexToStart, temp.length());
            System.out.println(document);

        } catch (IOException e) {
            logger.error("Exception Error", e);
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
                System.out.println("JsonResponse" + jsonResponse);

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree(jsonResponse);
                System.out.println("jsonNode" + jsonNode);

                JsonNode publishedArray = jsonNode.get("published");
                System.out.println("publishedArray" + publishedArray);

                for (int i = 0; i < publishedArray.size(); i++) {
                    listofScriptVersions.add(publishedArray.get(i).asInt());
                }

                httpClient.close();
                Collections.reverse(listofScriptVersions);
                System.out.println("Alok List" + listofScriptVersions);
                return listofScriptVersions;
            } else {
                System.out.println("API request failed with status code: " + statusCode);

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
