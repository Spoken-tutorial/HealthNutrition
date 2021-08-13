package com.health.utility;

/**
 * coomon data used in application
 * @author om prakash soni
 * @version 1.0
 *
 */
public class CommonData {
	
	public static final long videoDuration=300000000L;

	public static String superUserRole = "SUPER_USER";

	public static String creationAdminRole = "CREATION_ADMIN";

	public static String traningAdminRole= "TRAINING_ADMIN";

	public static String domainReviewerRole = "DOMAIN_REVIEWER";

	public static String qualityReviewerRole = "QUALITY_REVIEWER";

	public static String adminReviewerRole = "ADMIN_REVIEWER";

	public static String masterTrainerRole = "MASTER_TRAINER";

	public static String contributorRole= "CONTRIBUTOR";
	
	public static String externalContributorRole= "EXTERNAL_CONTRIBUTOR" ;

	public static String Outline_SAVE_SUCCESS_MSG = "Outline Saved Successfully !";

	public static String Keyword_SAVE_SUCCESS_MSG = "Keyword Saved Successfully !";

	public static String Video_SAVE_SUCCESS_MSG = "Video Saved Successfully !";

	public static String Video_STATUS_SUCCESS_MSG = "Video status updated successfully";

	public static String Script_SAVE_SUCCESS_MSG = "Script saved successfully";

	public static String Slide_SAVE_SUCCESS_MSG = "Slide saved successfully";

	public static String PRE_REQUISTIC_SAVE_SUCCESS_MSG = "Pre-Requistic saved successfully";

	public static String Graphics_SAVE_SUCCESS_MSG = "Graphics saved successfully";

	public static String RECORD_SAVE_SUCCESS_MSG = "Record saved successfully.";

	public static String EVENT_CHECK_DATE = "Please check event date";

	public static String EVENT_CHECK_CONTACT = "Contact number should be of 10 digits";

	public static String EVENT_CHECK_EMAIL = "Please check email id";

	public static String RECORD_UPDATE_SUCCESS_MSG = "Record updated successfully!";

	public static String RECORD_EXISTS = "Record Already Exists!";

	public static String RECORD_ERROR= "Error in saving record";

	public static String STATUS_ERROR= "Error in updating status";

	public static String QUESTION_EXIST= "Question Exist";

	public static String JPG_PNG_EXT= "Only .jpg or .png file allowed";

	public static String ROLE_APPROVED_SUCCESS_MSG= "Role approved";

	public static String CONTRIBUTOR_ADDED_SUCCESS_MSG = "Contributor added successfully!";

	public static String CONTRIBUTOR_ASSIGNED_TUTORIAL = "Contributor assigned successfully!";

	public static String CONTRIBUTOR_ERROR = "Contributor already assigned for selected language!";

	public static String CONTRIBUTOR_ERROR_MSG = "Error in assigning contributor!";

	public static String CONTRIBUTOR_TOPIC_ERROR = "Error in assigning topic";

	public static String MASTER_TRAINER_ADDED_SUCCESS_MSG = "Master Trainer added successfully!";

	public static String ADMIN_ADDED_SUCCESS_MSG = "Admin added Successfully!";

	public static String ROLE_ERROR_MSG = "Error in assigning role!";

	public static String DUPLICATE_ROLE_ERROR= "Role request already submitted!";

	public static String QUALITY_ADDED_SUCCESS_MSG = "Request submitted successfully for Quality Reviewer role!";

	public static String ROLE_REQUEST_ERROR = "Error in sending request!";

	public static String DOMAIN_REVIEW_MSG = "Waiting for Domain Review";

	public static String QUALITY_REVIEW_MSG = "Waiting for Quality Review";

	public static String ADMIN_REVIEW_MSG = "Waiting for Admin Review";

	public static String NEED_IMPROVEMENT_MSG = "Need Improvement";

	public static String ADD_CONTENT = "Add Content";

	public static int DOMAIN_STATUS = 2;

	public static int ADMIN_STATUS = 1;

	public static int QUALITY_STATUS = 3;

	public static int IMPROVEMENT_STATUS = 4;

	public static int PUBLISH_STATUS=6;

	public static int WAITING_PUBLISH_STATUS=5;

	public static String WAITING_PUBLISH = "Waiting for Publish";

	public static String PUBLISHED="Published";

	public static String uploadDirectoryQuestion = "Media/Content/Question/";

	public static String uploadDirectoryEvent = "Media/Content/Event/";

	public static String uploadDirectoryCategory = "Media/Content/Category/";

	public static String uploadDirectoryTutorial = "Media/Content/Tutorial/";

	public static String uploadDirectoryTestimonial = "Media/Content/Testimonial/";

	public static String uploadDirectoryConsultant = "Media/Content/Consultant/";

	public static String uploadDirectoryMasterTrainer = "Media/Content/MasterTrainer/";

	public static String uploadDirectoryMasterTrainerFeedback = "Media/Content/MasterTrainerFeedback/";

	public static String uploadUserImage = "Media/Content/ProfileImage/";
	
	public static String uploadPostQuestion = "Media/Content/PostQuestion/";
	
	public static String uploadBrouchure = "Media/Content/Brouchure/";
	
	public static String uploadCarousel = "Media/Content/Carousel/";

	public static String[] tutorialStatus= { ADD_CONTENT, ADMIN_REVIEW_MSG, DOMAIN_REVIEW_MSG, QUALITY_REVIEW_MSG,
			NEED_IMPROVEMENT_MSG, WAITING_PUBLISH,PUBLISHED
	};

	public static String COMMENT_SUCCESS = "Comment Added";

	public static String FAILURE = "Failed";

	public static String SCRIPT = "Script";

	public static String VIDEO = "Video";

	public static String KEYWORD = "Keyword";

	public static String OUTLINE = "Outline";

	public static String PRE_REQUISTIC = "Pre_requistic";

	public static String SLIDE = "Slide";

	public static String CSV_ERROR = "Error in uploading csv file";

	public static String ZIP_ERROR = "Error in uploading zip file";

	public static String NAME_ERROR = "Enter valid event name";

	public static String EVENT_ERROR = "Error in adding event";

	public static String EVENT_SUCCESS= "Event added successfully";

	public static String FEEDBACK_SUCCESS = "Feedback Added Successfully";

	public static String FEEDBACK_ERROR = "Error while Adding";

	public static String PUBLISHED_SUCCESS= "Tutorial published successfully";

	public static String COMMON_PASSWORD= "health";

	public static String MAIL_SEND= "Mail has been send";

	public static String NOT_VALID_EMAIL_ERROR= "Please Enter Correct Email";

	public static String VIDEO_FILE_EXTENSION_ERROR= "Provide MP4 extension File";
	
	public static String VIDEO_CONSENT_FILE_EXTENSION_ERROR= "Provide pdf or jpg/png Image";

	public static String TESTIMONIAL_NOT_ERROR= "Testimonial doesn't exist";
	
	public static String VIDEO_DURATION_ERROR= "Video durastion must be less than 5 min";
	
	public static String ADD_PROFILE_PIC_CONSTRAINT= "Please Add profile pic and user deatils before Requesting Master Trainer Role";
	
	public static String ADMIN_REVIEWER_REQ= "Admin Reviewer role request submitted";
	
	public static final long videoSize=400*1024*1024;
	
	public static final long videoSizeTest=20*1024*1024;
	
	public static final long fileSize=20*1024*1024;
	
	public static final long categoryFileSizeImageFileSize = 1*1024*1024;
	
	




}
