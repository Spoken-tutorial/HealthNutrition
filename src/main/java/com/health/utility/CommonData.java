package com.health.utility;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * coomon data used in application
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
@Component
public class CommonData {

    @Value("${elasticsearch_url}")
    public String elasticSearch_url;

    public static final long videoDuration = 300000000L;

    public static String superUserRole = "SUPER_USER";

    public static String creationAdminRole = "CREATION_ADMIN";

    public static String traningAdminRole = "TRAINING_ADMIN";

    public static String domainReviewerRole = "DOMAIN_REVIEWER";

    public static String qualityReviewerRole = "QUALITY_REVIEWER";

    public static String adminReviewerRole = "ADMIN_REVIEWER";

    public static String masterTrainerRole = "MASTER_TRAINER";

    public static String contributorRole = "CONTRIBUTOR";

    public static String externalContributorRole = "EXTERNAL_CONTRIBUTOR";

    public static String Outline_SAVE_SUCCESS_MSG = "Outline Saved Successfully !";

    public static String Keyword_SAVE_SUCCESS_MSG = "Keyword Saved Successfully !";

    public static String Video_SAVE_SUCCESS_MSG = "Video Saved Successfully !";

    public static String Video_STATUS_SUCCESS_MSG = "Video status updated successfully";

    public static String Script_SAVE_SUCCESS_MSG = "Script saved successfully";

    public static String SCRIPT_FOR_DOMAIN_REVIEW = "Script submitted for domain review.";

    public static String SCRIPT_FOR_QUALITY_REVIEW = "Script submitted for quality review.";

    public static String SCRIPT_STATUS_UPDATE_SUCCESS = "Script status updated successfully.";

    public static String Slide_SAVE_SUCCESS_MSG = "Slide saved successfully";

    public static String PRE_REQUISTIC_SAVE_SUCCESS_MSG = "Prerequisite saved successfully";

    public static String Graphics_SAVE_SUCCESS_MSG = "Graphics saved successfully";

    public static String RECORD_SAVE_SUCCESS_MSG = "Record saved successfully.";

    public static String EVENT_CHECK_DATE = "Please check event date.";

    public static String EVENT_CHECK_CONTACT = "Contact number should be of 10 digits.";

    public static String EVENT_CHECK_EMAIL = "Please check email id";

    public static String RECORD_UPDATE_SUCCESS_MSG = "Record updated successfully!";

    public static String All_Caches_Clear_MSG = "All caches have been cleared successfully!";

    public static String RECORD_EXISTS = "Record Already Exists!";

    public static String RECORD_ERROR = "Error in saving record";

    public static String STATUS_ERROR = "Error in updating status";

    public static String QUESTION_EXIST = "Question Exist";

    public static String JPG_PNG_EXT = "Only .jpg or .png file allowed";

    public static String ROLE_APPROVED_SUCCESS_MSG = "Role approved";

    public static String CONTRIBUTOR_ADDED_SUCCESS_MSG = "Contributor added successfully!";

    public static String CONTRIBUTOR_ASSIGNED_TUTORIAL = "Contributor assigned successfully!";

    public static String CONTRIBUTOR_ERROR = "Contributor already assigned for selected language!";

    public static String CONTRIBUTOR_ERROR_MSG = "Error in assigning contributor!";

    public static String CONTRIBUTOR_TOPIC_ERROR = "Error in assigning topic";

    public static String MASTER_TRAINER_ADDED_SUCCESS_MSG = "Master Trainer added successfully!";

    public static String ADMIN_ADDED_SUCCESS_MSG = "Admin added Successfully!";

    public static String ROLE_ERROR_MSG = "Error in assigning role!";

    public static String DUPLICATE_ROLE_ERROR = "Role request already submitted!";

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

    public static int PUBLISH_STATUS = 6;

    public static int WAITING_PUBLISH_STATUS = 5;

    public static String WAITING_PUBLISH = "Waiting for Publish";

    public static String PUBLISHED = "Published";

    public static String uploadDirectoryQuestion = "Media/Content/Question/";

    public static String uploadDirectoryEvent = "Media/Content/Event/";

    public static String uploadDirectoryCategory = "Media/Content/Category/";

    public static String uploadDirectoryTutorial = "Media/Content/Tutorial/";

    public static String uploadDirectoryOutline = "Media/Content/Tutorial/Outline/";
    public static String uploadDirectoryScriptHtmlFile = "Media/Content/Tutorial/ScriptHtmlFile/";
    public static String uploadDirectoryScriptHtmlFileforDownload = "Media/Content/Tutorial/ScriptDownloadHtmlFile/";
    public static String uploadDirectoryScriptOdtFileforDownload = "Media/Content/Tutorial/ScriptDownloadOdtFile/";
    public static String uploadDirectoryTimeScriptvttFile = "Media/Content/Tutorial/VttFile/";
    public static String uploadDirectoryScriptJsonFile = "Media/Content/Tutorial/ScriptJsonFile/";
    public static String uploadDirectoryScriptZipFiles = "Media/Content/Tutorial/ScriptZipFiles/";
    public static String uploadDirectoryTrainingModuleZipFiles = "Media/Content/TrainingModuleZipFiles/";
    public static String uploadDirectoryHealthTutorialZipFiles = "Media/Content/HealthTutorialZipFiles/";

    public static String uploadDirectoryTestimonial = "Media/Content/Testimonial/";
    public static String uploadDirectorySource = "Media/Content/Source/";

    public static String uploadDirectoryConsultant = "Media/Content/Consultant/";

    public static String uploadDirectoryMasterTrainer = "Media/Content/MasterTrainer/";

    public static String uploadDirectoryMasterTrainerFeedback = "Media/Content/MasterTrainerFeedback/";

    public static String uploadUserImage = "Media/Content/ProfileImage/";

    public static String uploadPostQuestion = "Media/Content/PostQuestion/";

    public static String uploadBrouchure = "Media/Content/Brochure/";

    public static String uploadPromoVideo = "Media/Content/PromoVideo/";

    public static String uploadVersion = "Media/Content/Version/";

    public static String uploadCarousel = "Media/Content/Carousel/";

    public static String uploadResearchPaper = "Media/Content/ResearchPaper/";
    public static String uploadLiveTutorial = "Media/Content/LiveTutorial/";

    public static String[] tutorialStatus = { ADD_CONTENT, ADMIN_REVIEW_MSG, DOMAIN_REVIEW_MSG, QUALITY_REVIEW_MSG,
            NEED_IMPROVEMENT_MSG, WAITING_PUBLISH, PUBLISHED };

//	public static String COMMENT_SUCCESS = "Comment Added";
    public static String COMMENT_SUCCESS = "Your comment is posted successfully.";

    public static String FAILURE = "Failed";

    public static String SCRIPT = "Script";

    public static String VIDEO = "Video";

    public static String KEYWORD = "Keyword";

    public static String OUTLINE = "Outline";

    public static String PRE_REQUISTIC = "prerequisite";

    public static String SLIDE = "Slide";

    public static String CSV_ERROR = "Error in uploading csv file";

    public static String ZIP_ERROR = "Error in uploading zip file";

    public static String NAME_ERROR = "Enter valid event name";

    public static String EVENT_ERROR = "Error in adding event";

    public static String EVENT_SUCCESS = "Event added successfully";

    public static String FEEDBACK_SUCCESS = "Feedback Added Successfully";

    public static String FEEDBACK_ERROR = "Error while Adding";

    public static String PUBLISHED_SUCCESS = "Tutorial published successfully";

    public static String COMMON_PASSWORD = "health";

    public static String MAIL_SEND = "Mail has been send";

    public static String NOT_VALID_EMAIL_ERROR = "Please Enter Correct Email";

    public static String VIDEO_FILE_EXTENSION_ERROR = "Provide MP4 extension File";

    public static String VIDEO_CONSENT_FILE_EXTENSION_ERROR = "Provide pdf or jpg/png Image";

    public static String TESTIMONIAL_NOT_ERROR = "Testimonial doesn't exist";

    public static String VIDEO_DURATION_ERROR = "Video duration must be less than 5 min";

    public static String ADD_PROFILE_PIC_CONSTRAINT = "Please Add profile pic and user deatils before Requesting Master Trainer Role";

    public static String ADMIN_REVIEWER_REQ = "Admin Reviewer role request submitted";

    public static final long videoSize = 50 * 1024 * 1024;

    public static final long videoSizeTest = 30 * 1024 * 1024;

    public static final long videoSizePromoVideo = 1000 * 1024 * 1024;

    public static final long videoSizeSpokenVideo = 1000 * 1024 * 1024;

    public static final long fileSize = 10 * 1024 * 1024;

    public static final long categoryFileSizeImageFileSize = 10 * 1024 * 1024;

    public static final int SCRIPT_MAX_COUNT = 100;
    public static final long SCRIPT_MAX_SIZE_MB = 100 * 1024 * 1024;
    public static final long Zip_DIR_MAX_SIZE_GB = 5 * 1000 * 1024 * 1024;

    public static final String SCRIPT_UPLOAD_ERROR = "Script Not Uploaded";

//public static String SCRIPT_MANAGER_BASE= "http://127.0.0.1:8000/";
    public static String SCRIPT_MANAGER_BASE = "https://scriptmanager.spoken-tutorial.org/";

    public static String SCRIPT_MANAGER_CREATE = "create/healthnutrition/";

    public static String SCRIPT_MANAGER_VIEW = "view/healthnutrition/";

    public static String SM_DEFAULT_PARAM = "1";

    public static String UNPUBLISH_SUCCESS = "Tutorial is unpubished.";

    public static String GENERAL_ERROR_MSG = "Some error occurred. Please contact site admin.";

    public static String CONSULTANT_ERROR = "Consultant doesn't exist";

    public static String DOMAIN_REVIEW_FAIL = "0";

    public static String DOMAIN_REVIEW_SUCCCESS = "1";

    public static String FAILURE_STATUS = "0";

    public static String SUCCESS_STATUS = "1";

    public int HANDLER_DATA = 10000000;

    public static final String STATUS = "status";
    public static final String STATUS_QUEUED = "queued";
    public static final String STATUS_PROCESSING = "processing";
    public static final String STATUS_PENDING = "pending";
    public static final String STATUS_DONE = "done";
    public static final String STATUS_FAILED = "failed";
    public static final String REASON = "reason";
    public static final String STATUS_NOTFOUND = "notFound";
    public static final String PROCESSING_TIME = "processingTime";
    public static final String START_TIME = "startTime";
    public static final String END_TIME = "endTime";
    public static final String CHANGE_TIME = "changeTime";
    public static final String MODIFICATION_TIME = "modificationTime";
    public static final String CREATION_TIME = "creationTime";
    public static final String CURRENT_TIME = "currentTime";
    public static final String DOCUMENT_ID = "documentId";
    public static final String DOCUMENT_TYPE = "documentType";
    public static final String DOCUMENT_TYPE_TUTORIAL_ORIGINAL_SCRIPT = "Tutorial_for_Original_Script";
    public static final String DOCUMENT_TYPE_TUTORIAL_TIME_SCRIPT = "Tutorial_for_Time_Script";
    public static final String DOCUMENT_TYPE_BROCHURE = "Brochure";
    public static final String DOCUMENT_TYPE_VERSION = "Version";
    public static final String DOCUMENT_TYPE_FILES_OF_BROCHURE = "Files_Of_Brochure";
    public static final String DOCUMENT_TYPE_RESEARCHPAPER = "ResearchPaper";

    public static final String DOCUMENT_ID_TUTORIAL_TIMESCRIPT = "Tts";
    public static final String DOCUMENT_ID_TUTORIAL_ORIGINAL_SCRIPT = "Tos";
    public static final String DOCUMENT_ID_BROCHURE = "Bro";
    public static final String DOCUMENT_ID_VERSION = "Ver";
    public static final String DOCUMENT_ID_FILES_OF_BROCHURE = "Files_Of_Bro";
    public static final String DOCUMENT_ID_RESEARCHPAPER = "Rp";
    public static final String LANGUAGE = "language";
    public static final String RANK = "rank";
    public static final String SUCCESS = "success";
    public static final String ADD_DOCUMENT = "addDocument";
    public static final String UPDATE_DOCUMENT = "updateDocument";
    public static final String UPDATE_DOCUMENT_RANK = "updateDocumentRank";
    public static final String DELETE_DOCUMENT = "deleteDocument";
    public static final String QUEUE_ID = "queueId";
    public static final String QUEUE_TIME = "queueTime";
    public static final String RUNNING_DOCUMENT = "runningDocument";
    public static final String SKIPPED_DOCUMENT = "skippedDocument";
    public static final Long NO_TASK_SLEEP_TIME = 30L * 1000;
    public static final Long TASK_SLEEP_TIME = 10L * 1000;
    public static final Long NO_TASK_SLEEP_TIME_FOR_DELETE = 60L * 1000 * 60;
    public static final Long TASK_SLEEP_TIME_FOR_DELETE = 60L * 1000 * 60;

//	public static String SCRIPT_MANAGER_VIEW= "view/healthnutrition";

}
