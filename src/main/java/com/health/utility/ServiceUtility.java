package com.health.utility;

import java.io.File;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;

import java.util.regex.Pattern;


import org.springframework.web.multipart.MultipartFile;

/**
 * Utility class
 * @author om Prakash
 * @version 1.0
 *
 */
public class ServiceUtility {
	
	/**
	 * To get current time
	 * @return Timestamp object
	 */
	public static Timestamp getCurrentTime() {								// Current Date
		
		Date date=new Date();
		long t=date.getTime();
		Timestamp st=new Timestamp(t);
		
		return st;
	}
	
	
	/**
	 * return no of days between present date and given date
	 * @param date Timestamp object
	 * @return String 
	 */
	public static String daysDifference(Timestamp date) {						// days Difference Between 2 date(current - given)
		
		Timestamp presentdate=getCurrentTime();
		long difference =Math.abs(date.getTime()-presentdate.getTime());
		long differenceDate=difference/(24*60*60*1000);
		
		
		return Long.toString(differenceDate);
	}
	
	
	/**
	 * to create folder in system
	 * @param path relative path
	 * @return
	 */
	public static boolean createFolder(String path) {					// check for existence of path
		boolean status=false;
		if(!new File(path).exists()) {
			status=new File(path).mkdirs();
		}
		return status;
		
	}
	

	/**
	 * to upload file in system
	 * @param uploadFile Multipart object
	 * @param pathToUpload relative path
	 * @return
	 * @throws Exception not found
	 */
	public static String uploadFile(MultipartFile uploadFile,String pathToUpload) throws Exception{		// uploading file
		String path=null;	
		
			Path fileNameAndPath =Paths.get(pathToUpload, uploadFile.getOriginalFilename());
		
				Files.write(fileNameAndPath, uploadFile.getBytes());
				System.out.println(fileNameAndPath.toString());
				path=fileNameAndPath.toString();
			
			
		
		return path;
	}
	

	
	/**
	 * to upload video file in system
	 * @param file multipart object
	 * @param pathToUpload relative path 
	 * @return
	 * @throws Exception not found
	 */
	public static String uploadVideoFile(MultipartFile file,String pathToUpload) throws Exception{		// uploading file
		String path=null;	
		
			Path fileNameAndPath =Paths.get(pathToUpload, file.getOriginalFilename());
			
				Files.write(fileNameAndPath, file.getBytes());
				System.out.println(fileNameAndPath.toString());
				path=fileNameAndPath.toString();
			
			
		
		return path;
	}
	

	/**
	 * to check whether PDF file or not
	 * @param pdfFile MultipartFile object
	 * @return
	 */
	public static boolean checkFileExtensiononeFilePDF(MultipartFile pdfFile) {				// validate file against PDF extension
		
		
			if(!pdfFile.getOriginalFilename().endsWith(".pdf") && !pdfFile.getOriginalFilename().endsWith(".PDF")) {
				return false;
			}
		
		return true;
	}
	
	/**
	 * to check whether CSV file or not
	 * @param pdfFile MultipartFile object
	 * @return
	 */
	public static boolean checkFileExtensiononeFileCSV(MultipartFile pdfFile) {				// validate file against PDF extension
		
		
		if(!pdfFile.getOriginalFilename().endsWith(".csv") && !pdfFile.getOriginalFilename().endsWith(".CSV")) {
			return false;
		}
	
	return true;
	}
	
	
	
	/**
	 * to check whether IMAGE file or not
	 * @param temp MultipartFile object
	 * @return
	 */
	public static boolean checkFileExtensionImage(MultipartFile temp) {			// validate file against Image Extension
		
		
			if(!temp.getOriginalFilename().endsWith(".jpg") && !temp.getOriginalFilename().endsWith(".jpeg") && !temp.getOriginalFilename().endsWith(".png")
					&& !temp.getOriginalFilename().endsWith(".JPG") && !temp.getOriginalFilename().endsWith(".JPEG") && !temp.getOriginalFilename().endsWith(".PNG")) {
				
				return false;
			}
		
		return true;
	}
	
	
	/**
	 * to check whether MP4 file or not
	 * @param videoFile MultipartFile object
	 * @return
	 */
	public static boolean checkFileExtensionVideo(MultipartFile videoFile) {			// validate file against Image Extension
		
		
		if(!videoFile.getOriginalFilename().endsWith(".mp4") && !videoFile.getOriginalFilename().endsWith(".mov")
				&& !videoFile.getOriginalFilename().endsWith(".MP4") && !videoFile.getOriginalFilename().endsWith(".MOV")) {
			return false;
		}
		
		return true;
	}
	

	/**
	 * to check whether ZIP file or not
	 * @param temp MultipartFile object
	 * @return
	 */
	public static boolean checkFileExtensionZip(MultipartFile temp) {			// validate file against HTML Extension
		
		
			if(!temp.getOriginalFilename().endsWith(".zip") && !temp.getOriginalFilename().endsWith(".ZIP")) {
				
				return false;
			}
		
		return true;
	}
	
	/**
	 * to check whether size of file is within the limit
	 * @param temp MultipartFile object
	 * @return
	 */
	public static boolean checkVideoSize(MultipartFile temp) {
		
		if(temp.getSize() > CommonData.videoSize) {
			return false;
		}
		return true;
	}
	
	/**
	 * to check whether size of Testimonial video is within the limit
	 * @param temp MultipartFile object
	 * @return
	 */
	public static boolean checkVideoSizeTestimonial(MultipartFile temp) {
		
		if(temp.getSize() > CommonData.videoSizeTest) {
			return false;
		}
		return true;
	}
	
	/**
	 * to check whether size of file is within the limit
	 * @param temp MultipartFile object
	 * @return
	 */
	public static boolean checkScriptSlideProfileQuestion(MultipartFile temp) {
		
		if(temp.getSize() > CommonData.fileSize) {
			return false;
		}
		return true;
	}
	
	/**
	 * to check whether size of file is within the limit
	 * @param temp MultipartFile object
	 * @return
	 */
	public static boolean checkCatAndAllImageFile(MultipartFile temp) {
		
		if(temp.getSize() > CommonData.categoryFileSizeImageFileSize) {
			return false;
		}
		return true;
	}
	
	/**
	 * to get present working path
	 * @return
	 */
	public static String presentDirectory() {
		Path currentRelativePath = Paths.get("");
		String currentpath = currentRelativePath.toAbsolutePath().toString();
		return currentpath;
		
	}
	
	
	/**
	 * to validate email
	 * @param email String object
	 * @return
	 */
	public static boolean checkEmailValidity(String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ 
                            "[a-zA-Z0-9_+&*-]+)*@" + 
                            "(?:[a-zA-Z0-9-]+\\.)+[a-z" + 
                            "A-Z]{2,7}$"; 
                              
        Pattern pat = Pattern.compile(emailRegex); 
        if (email == null) 
            return false; 
        
        return pat.matcher(email).matches(); 
	}
	

	/**
	 * to check numeral value in string
	 * @param input String object
	 * @return
	 */
	public static boolean checkContainNumeralInString(String input) {
		for(int i=0 ; i<input.length();i++) {
			if(input.charAt(i)=='0' || input.charAt(i)=='1' || input.charAt(i)=='2' || input.charAt(i)=='3' ||
					input.charAt(i)=='4' || input.charAt(i)=='5' || input.charAt(i)=='6' || input.charAt(i)=='7' ||
					input.charAt(i)=='8' || input.charAt(i)=='9') {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * to convert String into date Object
	 * @param date String object
	 * @return 
	 * @throws ParseException
	 */

	public static java.sql.Date convertStringToDate(String date) throws ParseException{
		SimpleDateFormat sd1=new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date dateUtil=sd1.parse(date);
		return new java.sql.Date(dateUtil.getTime());
	}
	
	
}
