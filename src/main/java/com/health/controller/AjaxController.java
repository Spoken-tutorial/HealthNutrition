package com.health.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

//import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.health.config.SecurityConfig;
import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Brouchure;
import com.health.model.Carousel;
import com.health.model.Category;
import com.health.model.City;
import com.health.model.Comment;
import com.health.model.Consultant;
import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.District;
import com.health.model.Event;
import com.health.model.FeedbackForm;
import com.health.model.FilesofBrouchure;
import com.health.model.Language;
import com.health.model.LogManegement;
import com.health.model.PathofPromoVideo;
import com.health.model.PromoVideo;
import com.health.model.ResearchPaper;
import com.health.model.State;
import com.health.model.Testimonial;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;
import com.health.model.TrainingTopic;
import com.health.model.Tutorial;
import com.health.model.User;
import com.health.model.Version;
import com.health.service.BrouchureService;
import com.health.service.CarouselService;
import com.health.service.CategoryService;
import com.health.service.CityService;
import com.health.service.CommentService;
import com.health.service.ConsultantService;
import com.health.service.ContributorAssignedMultiUserTutorialService;
import com.health.service.ContributorAssignedTutorialService;
import com.health.service.DistrictService;
import com.health.service.EventService;
import com.health.service.FeedbackService;
import com.health.service.FilesofBrouchureService;
import com.health.service.LanguageService;
import com.health.service.LogMangementService;
import com.health.service.PathofPromoVideoService;
import com.health.service.PromoVideoService;
import com.health.service.ResearchPaperService;
import com.health.service.RoleService;
import com.health.service.StateService;
import com.health.service.TestimonialService;
import com.health.service.TopicCategoryMappingService;
import com.health.service.TopicService;
import com.health.service.TraineeInformationService;
import com.health.service.TrainingInformationService;
import com.health.service.TrainingTopicService;
import com.health.service.TutorialService;
import com.health.service.UserRoleService;
import com.health.service.UserService;
import com.health.service.VersionService;
import com.health.threadpool.TaskProcessingService;
import com.health.utility.CommonData;
import com.health.utility.MailConstructor;
import com.health.utility.SecurityUtility;
import com.health.utility.ServiceUtility;

/**
 * This Controller Class takes website AJAX request and process it accordingly
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
@Controller
public class AjaxController {

    private static Logger logger = LoggerFactory.getLogger(AjaxController.class);

    @Autowired
    private CategoryService catService;

    @Autowired
    private CommonData commonData;

    @Autowired
    private PromoVideoService promoVideoService;

    @Autowired
    private ResearchPaperService researchPaperService;

    @Autowired
    private PathofPromoVideoService pathofPromoVideoService;

    @Autowired
    private VersionService verService;

    @Autowired
    private FilesofBrouchureService filesofBroService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicCategoryMappingService topicCatService;

    @Autowired
    private UserService usrservice;

    @Autowired
    private UserRoleService usrRoleService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CarouselService caroService;

    @Autowired
    private ContributorAssignedTutorialService conService;

    @Autowired
    private ContributorAssignedMultiUserTutorialService conMultiService;

    @Autowired
    private TutorialService tutService;

    @Autowired
    private LanguageService lanService;

    @Autowired
    private Environment env;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @Autowired
    private StateService stateService;

    @Autowired
    private DistrictService disService;

    @Autowired
    private CityService cityServie;

    @Autowired
    private TrainingInformationService trainingInforService;

    @Autowired
    private TraineeInformationService traineeService;

    @Autowired
    private CommentService comService;

    @Autowired
    private LogMangementService logService;

    @Autowired
    private FeedbackService ffService;

    @Autowired
    private TrainingTopicService trainingTopicService;

    @Autowired
    private ConsultantService consultantService;

    @Autowired
    private ConsultantService consultService;

    @Autowired
    private TestimonialService testService;

    @Autowired
    private BrouchureService broService;

    @Autowired
    private LanguageService langService;

    @Autowired
    private EventService eventService;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private JavaMailSender mailSender;

    private static final String SUCCESS_TOKEN = "success";
    private static final String ERROR_TOKEN = "error";
    private static final String TUTORIAL_UPDATE_ERROR = "Some error occcurred in updating tutorial. Please contact site admin.";
    private static final String TUTORIAL_UPDATE_SUCCESS = "Component updated successfully.";
    private static final String TUTORIAL_CREATION_ERROR = "Error in creating tutorial. Please contact site admin.";
    private static final String ZIP_EXTN_ERROR = "File extension must be in ZIP.";
    private static final String SLIDE_SIZE_ERROR = "File Size must be under 20 MB";
    private static final String VIDEO_EXTN_ERROR = "File extension must be in MP4.";
    private static final String VIDEO_SIZE_ERROR = "File Size must be under 50 MB.";
    private static final int NULL_TUTORIAL = 0;
    private static final int ADD_COMPONENT = 0;
    private static final String DOMAIN = "domain";
    private static final String QUALITY = "quality";
    private static final String ADMIN_REVIEWER = "admin-rev";

    private User getUser(Principal principal) {
        if (principal != null) {
            return usrservice.findByUsername(principal.getName());
        }
        return new User();
    }

    // private HashMap<Integer, String> updateResponse(String res, String msg, int
    // comp_status, int tutorial_id) {
    private HashMap<String, String> updateResponse(String res, String msg, int comp_status, int tutorialId, User user) {
        // 0: success/error ; 1:info msg 2:comp status
        HashMap<String, String> temp = new HashMap<>();
        temp.put("response", res);
        temp.put("message", msg);
        temp.put("component_status", CommonData.tutorialStatus[comp_status]);
        temp.put("tutorial_id", String.valueOf(tutorialId));
        String firstName = "";
        String lastName = "";
        logger.info("Variables of updateResponset comp_status : {} tutorialId : {}", comp_status, tutorialId);
        if (user.getFirstName() != "") {
            firstName = user.getFirstName();
            if (firstName.length() > 1) {
                firstName = firstName.substring(0, 1).toUpperCase() + firstName.substring(1);
            } else {
                firstName = firstName.toUpperCase();
            }
        }
        if (user.getLastName() != "") {
            lastName = user.getLastName();
            if (lastName.length() > 1) {
                lastName = lastName.substring(0, 1).toUpperCase() + lastName.substring(1);
            } else {
                lastName = lastName.toUpperCase();
            }
        }
        temp.put("user", firstName + " " + lastName);

        return temp;
    }

    private HashMap<String, String> addOutlineComp(Tutorial tut, String outline, User usr) {
        HashMap<String, String> temp = new HashMap<>();
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.OUTLINE, CommonData.DOMAIN_STATUS, tut.getOutlineStatus(), CommonData.contributorRole, usr,
                tut);
        tut.setOutline(outline);
        try {
            tut.saveOutline(env.getProperty("spring.applicationexternalPath.name"), outline);
        } catch (IOException e) {

            logger.error("Exception: ", e);
        }
        tut.setOutlineStatus(CommonData.DOMAIN_STATUS);
        tut.setOutlineUser(usr);
        try {
            tutService.save(tut);
            temp = updateResponse(SUCCESS_TOKEN, TUTORIAL_UPDATE_SUCCESS, tut.getOutlineStatus(), tut.getTutorialId(),
                    usr);
        } catch (Exception e) {
            temp = updateResponse(ERROR_TOKEN, TUTORIAL_UPDATE_ERROR, tut.getOutlineStatus(), tut.getTutorialId(), usr);
            return temp;
        }
        logService.save(log);
        return temp;

    }

    private HashMap<String, String> addScriptComp(Tutorial tut, User usr) {
        HashMap<String, String> temp = new HashMap<>();
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT,
                CommonData.DOMAIN_STATUS, tut.getScriptStatus(), CommonData.contributorRole, usr, tut);
        tut.setScriptStatus(CommonData.DOMAIN_STATUS);
        tut.setScriptUser(usr);
        try {
            tutService.save(tut);
            temp = updateResponse(SUCCESS_TOKEN, TUTORIAL_UPDATE_SUCCESS, tut.getScriptStatus(), tut.getTutorialId(),
                    usr);
        } catch (Exception e) {
            logger.error("Error in Add Script Comp: {}", tut, e);
            temp = updateResponse(ERROR_TOKEN, TUTORIAL_UPDATE_ERROR, tut.getScriptStatus(), tut.getTutorialId(), usr);
            return temp;
        }
        logService.save(log);
        return temp;

    }

    private String getDocument(Tutorial tut, MultipartFile mediaFile, String comp) {
        try {
            ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")
                    + CommonData.uploadDirectoryTutorial + tut.getTutorialId() + "/" + comp);
        } catch (IOException e) {

            logger.error("Exception: ", e);
        }
        String document = "";
        try {

            String folder = CommonData.uploadDirectoryTutorial + tut.getTutorialId() + "/" + comp;
            document = ServiceUtility.uploadMediaFile(mediaFile, env, folder);
        } catch (Exception e) {
            logger.error("Error in Get Document: {}", e);
        }

        return document;
    }

    private HashMap<String, String> addSlideComp(Tutorial tut, MultipartFile slideFile, User usr) {
        HashMap<String, String> temp = new HashMap<>();
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE,
                CommonData.DOMAIN_STATUS, tut.getSlideStatus(), CommonData.contributorRole, usr, tut);
        try {
            String document = getDocument(tut, slideFile, "Slide");

            if (document != "") {
                tut.setSlide(document);
                tut.setSlideStatus(CommonData.DOMAIN_STATUS);
                tut.setSlideUser(usr);
                tutService.save(tut);
                temp = updateResponse(SUCCESS_TOKEN, TUTORIAL_UPDATE_SUCCESS, tut.getSlideStatus(), tut.getTutorialId(),
                        usr);
                logService.save(log);
            }
        } catch (Exception e) {
            temp = updateResponse(ERROR_TOKEN, TUTORIAL_UPDATE_ERROR, tut.getSlideStatus(), tut.getTutorialId(), usr);
        }

        return temp;
    }

    private HashMap<String, String> addVideoComp(Tutorial tut, MultipartFile videoFile, User usr) {
        HashMap<String, String> temp = new HashMap<>();
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO,
                CommonData.ADMIN_STATUS, tut.getVideoStatus(), CommonData.contributorRole, usr, tut);

        try {
            String document = getDocument(tut, videoFile, "Video");

            if (document != "") {
                tut.setVideo(document);
                tut.setVideoStatus(CommonData.ADMIN_STATUS);
                tut.setVideoUser(usr);
                tutService.save(tut);

                temp = updateResponse(SUCCESS_TOKEN, TUTORIAL_UPDATE_SUCCESS, tut.getVideoStatus(), tut.getTutorialId(),
                        usr);
                logService.save(log);
            }
        } catch (Exception e) {
            logger.error("Error in get addVideo", e);
            temp = updateResponse(ERROR_TOKEN, TUTORIAL_UPDATE_ERROR, tut.getVideoStatus(), tut.getTutorialId(), usr);
        }

        return temp;
    }

    private HashMap<String, String> addKeywordComp(Tutorial tut, String keywordData, User usr) {
        HashMap<String, String> temp = new HashMap<>();
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.KEYWORD, CommonData.DOMAIN_STATUS, tut.getKeywordStatus(), CommonData.contributorRole, usr,
                tut);
        tut.setKeyword(keywordData);
        tut.setKeywordStatus(CommonData.DOMAIN_STATUS);
        tut.setKeywordUser(usr);
        try {
            tutService.save(tut);
            temp = updateResponse(SUCCESS_TOKEN, TUTORIAL_UPDATE_SUCCESS, tut.getKeywordStatus(), tut.getTutorialId(),
                    usr);
        } catch (Exception e) {
            temp = updateResponse(ERROR_TOKEN, TUTORIAL_UPDATE_ERROR, tut.getKeywordStatus(), tut.getTutorialId(), usr);
            return temp;
        }
        logService.save(log);
        return temp;

    }

    private HashMap<String, String> addPreReqComp(Tutorial tut, Tutorial prereq, User usr) {
        HashMap<String, String> temp = new HashMap<>();
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.PRE_REQUISTIC, CommonData.DOMAIN_STATUS, tut.getPreRequisticStatus(),
                CommonData.contributorRole, usr, tut);

        try {
            tut.setPreRequistic(prereq);
            tut.setPreRequisticStatus(CommonData.DOMAIN_STATUS);
            tut.setPreRequiticUser(usr);
            tutService.save(tut);
            temp = updateResponse(SUCCESS_TOKEN, TUTORIAL_UPDATE_SUCCESS, tut.getPreRequisticStatus(),
                    tut.getTutorialId(), usr);
        } catch (Exception e) {
            temp = updateResponse(ERROR_TOKEN, TUTORIAL_UPDATE_ERROR, tut.getPreRequisticStatus(), tut.getTutorialId(),
                    usr);
            return temp;
        }

        logService.save(log);
        return temp;

    }

    private HashMap<String, String> addNullPreReq(Tutorial tut, User usr) {
        HashMap<String, String> temp = new HashMap<>();
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.PRE_REQUISTIC, CommonData.DOMAIN_STATUS, tut.getPreRequisticStatus(),
                CommonData.contributorRole, usr, tut);
        logger.info("Variables of addNullPreReq tutorial : {}", tut);
        tut.setPreRequistic(null);
        tut.setPreRequisticStatus(CommonData.DOMAIN_STATUS);
        tut.setPreRequiticUser(usr);
        try {
            tutService.save(tut);
            temp = updateResponse(SUCCESS_TOKEN, TUTORIAL_UPDATE_SUCCESS, tut.getPreRequisticStatus(),
                    tut.getTutorialId(), usr);
        } catch (Exception e) {
            temp = updateResponse(ERROR_TOKEN, TUTORIAL_UPDATE_ERROR, tut.getPreRequisticStatus(), tut.getTutorialId(),
                    usr);
            return temp;
        }
        logService.save(log);
        return temp;
    }

    private Tutorial createTutorial(String catName, int topicId, String lang, User usr) {
        Category cat = catService.findBycategoryname(catName);
        Topic topic = topicService.findById(topicId);
        TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
        Language lan = lanService.getByLanName(lang);
        ContributorAssignedTutorial conLocal = conService.findByTopicCatAndLanViewPart(localTopicCat, lan);
        Tutorial local = new Tutorial();
        local.setDateAdded(ServiceUtility.getCurrentTime());
        local.setConAssignedTutorial(conLocal);
        local.setTutorialId(tutService.getNewId());
        logger.info("Variables of createTutorial cat: {} topic : {} lan : {}", cat, topic, lan);
        if (!lan.getLangName().equalsIgnoreCase("English")) {
            Language lanEng = lanService.getByLanName("English");
            ContributorAssignedTutorial conLocal1 = conService.findByTopicCatAndLanViewPart(localTopicCat, lanEng);
            local.setRelatedVideo(tutService.findAllByContributorAssignedTutorial(conLocal1).get(0));

            Tutorial preReq = tutService.findAllByContributorAssignedTutorial(conLocal1).get(0).getPreRequistic();

            if (preReq == null) {
                local.setPreRequistic(null);
                local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
            } else {
                ContributorAssignedTutorial conLocal2 = conService
                        .findByTopicCatAndLanViewPart(preReq.getConAssignedTutorial().getTopicCatId(), lan);
                List<Tutorial> t = tutService.findAllByContributorAssignedTutorial(conLocal2);
                if (!t.isEmpty()) {
                    local.setPreRequistic(t.get(0));
                }

                local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
            }
            local.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
            local.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
        }
        try {
            tutService.save(local);
        } catch (Exception e) {
            logger.error("Error in create Tutorial: {}", local, e);
        }
        return local;
    }

    private HashMap<String, String> setResponse(Integer status) {
        HashMap<String, String> temp = new HashMap<String, String>();
        temp.put("response", CommonData.SUCCESS_STATUS);
        temp.put("status", CommonData.tutorialStatus[status]);
        return temp;
    }

    /**
     * make visible/disable brochure object in system
     * 
     * @param id int value
     * @return Boolean value
     */
    @GetMapping("/enableDisableBrouchure")
    public @ResponseBody boolean enableDisableBrouchure(int id) {
        Brouchure bro = broService.findById(id);

        try {
            if (bro.isShowOnHomepage()) {
                bro.setShowOnHomepage(false);
                taskProcessingService.addUpdateDeleteBrochure(bro, CommonData.DELETE_DOCUMENT);
                broService.save(bro);
                return true;

            } else {
                bro.setShowOnHomepage(true);
                taskProcessingService.addUpdateDeleteBrochure(bro, CommonData.ADD_DOCUMENT);
                broService.save(bro);
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Enable Disbale Brochure: {}", bro, e);
            return false;
        }

    }

    @GetMapping("/enableDisablePromoVideo")
    public @ResponseBody boolean enableDisablePromoVideo(int id) {
        PromoVideo pro = promoVideoService.findById(id);

        try {
            if (pro.isShowOnHomepage()) {
                pro.setShowOnHomepage(false);
                promoVideoService.save(pro);
                return true;

            } else {
                pro.setShowOnHomepage(true);
                promoVideoService.save(pro);
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Enable Disbale Promo Video: {}", pro, e);
            return false;
        }

    }

    @GetMapping("/enableDisableResearchPaper")
    public @ResponseBody boolean enableDisableResearchPaper(int id) {
        ResearchPaper res = researchPaperService.findById(id);

        try {
            if (res.isShowOnHomepage()) {

                res.setShowOnHomepage(false);
                taskProcessingService.addUpdateDeleteResearchPaper(res, CommonData.DELETE_DOCUMENT);
                researchPaperService.save(res);
                return true;

            } else {
                res.setShowOnHomepage(true);
                taskProcessingService.addUpdateDeleteResearchPaper(res, CommonData.ADD_DOCUMENT);
                researchPaperService.save(res);
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Enable Disbale Research Paper: {}", res, e);
            return false;
        }

    }

    @GetMapping("/enableDisableEvent")
    public @ResponseBody boolean enableDisableEvent(int id) {
        Event event = eventService.findById(id);

        try {
            if (event.isShowOnHomepage()) {

                event.setShowOnHomepage(false);

                eventService.save(event);
                return true;

            } else {
                event.setShowOnHomepage(true);

                eventService.save(event);
                return true;
            }
        } catch (Exception e) {

            logger.error("Error in Enable Disbale Event: {}", event, e);
            return false;
        }

    }

    @GetMapping("/enableDisableCarousel")
    public @ResponseBody boolean enableDisableCarousel(int id) {
        Carousel cara = caroService.findById(id);

        try {
            if (cara.isShowOnHomepage()) {

                cara.setShowOnHomepage(false);

                caroService.save(cara);
                return true;

            } else {
                cara.setShowOnHomepage(true);

                caroService.save(cara);
                return true;
            }
        } catch (Exception e) {

            logger.error("Error in Enable Disbale Carousel: {}", cara, e);
            return false;
        }

    }

    /*
     * A function to get primary version by checked and unchecked checkbox Author:
     * Alok Kumar
     * 
     */

    @GetMapping("/primaryVersionWithoutOverwrite")
    public @ResponseBody int getPrimaryVersionwithoutOverwrite(int id, int checkedValue) {

        Brouchure bro = broService.findById(id);
        logger.info("Variables of getPrimaryVersionwithoutOverwrite Brochure : {}", bro);
        if (checkedValue == 1)
            return bro.getPrimaryVersion();
        else
            return bro.getPrimaryVersion() + 1;

    }

    /**
     * make visible/disable Consultant object in system
     * 
     * @param id int value
     * @return Boolean value
     */
    @GetMapping("/enableDisableConsultant")
    public @ResponseBody boolean enableDisableConsultant(int id) {
        Consultant con = consultService.findById(id);

        try {
            if (con.isOnHome()) {
                con.setOnHome(false);
                consultService.save(con);
                return true;

            } else {
                con.setOnHome(true);
                consultService.save(con);
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Enable Disbale Consultant: {}", con, e);
            return false;
        }

    }

    /**
     * make visible/disable Testimonial object in system
     * 
     * @param id int value
     * @return Boolean value
     */
    @GetMapping("/enableDisableTestimonial")
    public @ResponseBody boolean enableDisableTestimonial(int id) {
        Testimonial test = testService.findById(id);

        try {
            if (test.isApproved()) {
                test.setApproved(false);
                testService.save(test);
                return true;

            } else {
                test.setApproved(true);
                testService.save(test);
                return true;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Enable Disbale Testimonial: {}", test, e);
            return false;
        }

    }

    /**
     * update trainee object
     * 
     * @param addharNo  String
     * @param age       int
     * @param principal Principal object
     * @param email     String
     * @param gender    String
     * @param name      String
     * @param org       String
     * @param phone     String
     * @param traineeId int
     * @return List of String
     */
    @RequestMapping("/updateTrainee")
    public @ResponseBody List<String> updateUserPassword(@RequestParam(value = "aadhar") String addharNo,
            @RequestParam(value = "age") int age, Principal principal, @RequestParam(value = "email") String email,
            @RequestParam(value = "gender") String gender, @RequestParam(value = "name") String name,
            @RequestParam(value = "org") String org, @RequestParam(value = "phone") String phone,
            @RequestParam(value = "traineeId") int traineeId) {
        List<String> status = new ArrayList<String>();

        // User usr = new User();

        // if (principal != null) {

        // usr = usrservice.findByUsername(principal.getName());
        // }

        long aadharNumber = Long.parseLong(addharNo);
        long phoneNumber = Long.parseLong(phone);

        TraineeInformation trainee = traineeService.findById(traineeId);

        trainee.setAadhar(aadharNumber);
        trainee.setAge(age);
        trainee.setEmail(email);
        trainee.setGender(gender);
        trainee.setName(name);
        trainee.setOrganization(org);
        trainee.setPhone(phoneNumber);

        try {
            traineeService.save(trainee);
            status.add("Success");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Update Trainee: {}", trainee, e);
            status.add("failure");
        }

        return status;

    }

    /**
     * update password of user
     * 
     * @param newPass   string
     * @param oldPass   string
     * @param principal Principal object
     * @return List of String object
     */
    @RequestMapping("/updatePassword")
    public @ResponseBody List<String> updateUserPassword(@RequestParam(value = "password") String newPass,
            @RequestParam(value = "currentPassword") String oldPass, Principal principal) {
        List<String> status = new ArrayList<String>();
        boolean statusPassword = false;

        User usr = getUser(principal);
        if (newPass.length() < 6) {
            status.add("passwordLengthError");
            return status;
        }

        statusPassword = SecurityConfig.passwordEncoder().matches(oldPass, usr.getPassword());

        if (statusPassword) {
            try {
                usr.setPassword(SecurityUtility.passwordEncoder().encode(newPass));
                usrservice.save(usr);
                status.add("Success");
            } catch (Exception e) {
                // TODO: handle exception
                status.add("failure");
            }
        } else {
            status.add("failure");
        }

        return status;

    }

    /**
     * return trainee object based on given training id
     * 
     * @param id int
     * @return list of string
     */

    @RequestMapping("/loadTraineeByTrainingId")
    public @ResponseBody List<TraineeInformation> getTraineeInfoOnTrainingId(@RequestParam(value = "id") int id) {

        TrainingInformation training = trainingInforService.getById(id);
        List<TraineeInformation> traineeList = traineeService.findAllBytraineeInfos(training);
        List<TraineeInformation> traineeListResponse = new ArrayList<TraineeInformation>();

        for (TraineeInformation x : traineeList) {
            TraineeInformation tem = new TraineeInformation(x.getTrainingId(), x.getName(), x.getEmail(), x.getPhone(),
                    x.getAge(), x.getAadhar(), x.getGender(), x.getOrganization(), null);
            traineeListResponse.add(tem);
        }

        return traineeListResponse;

    }

    /**
     * return title name for the training conducted given id
     * 
     * @param id int
     * @return HashMap
     */

    @RequestMapping("/loadTitleNameInMasterTraining")
    public @ResponseBody HashMap<Integer, String> getTopicNameFromMasterTrainer(@RequestParam(value = "id") int id) {

        HashMap<Integer, String> topicName = new HashMap<>();

        Category cat = catService.findByid(id);
        List<TopicCategoryMapping> topicCatList = topicCatService.findAllByCategory(cat);
        Set<TrainingTopic> trainingTopic = trainingTopicService.findByTopicCat(topicCatList);

        for (TrainingTopic x : trainingTopic) {
//			TrainingInformation temp = trainingInforService.getById(x.getTraineeInfos().getTrainingId());
            Event event = eventService.findById(x.getEvent().getEventId());
//			topicName.put(temp.getTrainingId(), temp.getTitleName());
            topicName.put(event.getEventId(), event.getEventName());
        }

        /*
         * for(TrainingInformation x : training) { topicName.put(x.getTrainingId(),
         * x.getTitleName()); }
         */

        return topicName;

    }

    /**
     * return district given state id
     * 
     * @param id int
     * @return HashMap
     */

    @RequestMapping("/loadDistrictByState")
    public @ResponseBody HashMap<Integer, String> getDistrictByState(@RequestParam(value = "id") int id) {

        HashMap<Integer, String> disName = new HashMap<>();

        State state = stateService.findById(id);
        logger.info("Variables of getDistrictByState state : {}", state);

        List<District> districts = disService.findAllByState(state);

        for (District temp : districts) {

            disName.put(temp.getId(), temp.getDistrictName());
        }
        return disName;

    }

    /**
     * return city given district id
     * 
     * @param id int
     * @return HashMap
     */

    @RequestMapping("/loadCityByDistrict")
    public @ResponseBody HashMap<Integer, String> getCityByDistrict(@RequestParam(value = "id") int id) {

        HashMap<Integer, String> cityName = new HashMap<>();

        District district = disService.findById(id);
        logger.info("Variables of getCityByDistrict district : {}", district);

        List<City> cities = cityServie.findAllByDistrict(district);

        for (City temp : cities) {

            cityName.put(temp.getId(), temp.getCityName());
        }
        return cityName;

    }

    /**
     * return topic given category id
     * 
     * @param id int
     * @return HashMap
     */

    @RequestMapping("/loadTopicByCategory")
    public @ResponseBody HashMap<Integer, String> getTopicByCategory(@RequestParam(value = "id") int id) {

        HashMap<Integer, String> topicName = new HashMap<>();

        Category cat = catService.findByid(id);
        logger.info("Variables of getTopicByCategory catId :{} category : {}", id, cat);
        List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat);
        List<ContributorAssignedTutorial> cat_list = conService.findAllByTopicCat(local);
        List<Tutorial> tutorials = tutService.findAllByconAssignedTutorialAndStatus(cat_list);
        // HashSet<Topic> topics = new HashSet<>();
        for (Tutorial t : tutorials) {
            topicName.put(t.getConAssignedTutorial().getTopicCatId().getTopic().getTopicId(),
                    t.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
        }
        return topicName;

    }

    @RequestMapping("/loadTopicByCategoryforCitation")
    public @ResponseBody HashMap<Integer, String> getTopicByCategoryforCitation(@RequestParam(value = "id") int id) {
        HashMap<Integer, String> topicName = new HashMap<>();

        Category cat = catService.findByid(id);
        logger.info("Variables of getTopicByCategory catId :{} category : {}", id, cat);

        List<Tutorial> tutorials = tutService.findAllEnabledEnglishTuorialsWithCitationIsNullByCategory(cat);

        for (Tutorial temp : tutorials) {

            Topic topic = temp.getConAssignedTutorial().getTopicCatId().getTopic();
            topicName.put(topic.getTopicId(), topic.getTopicName());

        }

        return topicName;
    }

    @RequestMapping("/loadPromoVideoByLanguage")
    public @ResponseBody String getPathofPromoVideo(@RequestParam(value = "lanId") int lanId,
            @RequestParam(value = "promoId") int promoId) {

        try {
            Language lan = langService.getById(lanId);
            logger.info("Variables of getPathofPromoVideo lan :{} ", lan);
            PromoVideo promo = promoVideoService.findById(promoId);
            PathofPromoVideo pathofPromoVideo = pathofPromoVideoService.findByLanguageandPromoVideo(lan, promo);

            return pathofPromoVideo.getVideoPath();
        } catch (Exception e) {
            logger.error("Error in get the Path of Promo Video: {} {}", lanId, promoId, e);
            return "";
        }

    }

    @RequestMapping("/loadWebAndPrintFile")
    public @ResponseBody List<String> loadWebAndPrintFile(@RequestParam(value = "lanId") int lanId,
            @RequestParam(value = "verId") int verId) {

        List<String> fileList = new ArrayList<>();
        Language lan = langService.getById(lanId);
        Version ver = verService.findById(verId);

        try {
            FilesofBrouchure filesBro = filesofBroService.findByLanguageandVersion(lan, ver);

            String webFile = filesBro.getWebPath();

            if (webFile != "") {
                fileList.add(webFile);
            }

        } catch (Exception e) {

        }

        return fileList;

    }

    /*
     * Function to load Topic and Language by category Author: Alok Kumar
     */

    @RequestMapping("/loadTopicAndLanguageByCategory")
    @Cacheable(cacheNames = "categories", key = "{#root.methodName, #catId, #topicId, #languageId }")
    public @ResponseBody ArrayList<Map<String, Integer>> getTopicAndLanguageByCategory(
            @RequestParam(value = "catId") int catId, @RequestParam(value = "topicId") int topicId,
            @RequestParam(value = "languageId") int languageId) {

        ArrayList<Map<String, Integer>> arlist = new ArrayList<>();

        Map<String, Integer> topics = new LinkedHashMap<>();
        // HashMap<String,Integer> topics=new LinkedHashMap<>();
        Map<String, Integer> languages = new TreeMap<>();

        Category cat = catId != 0 ? catService.findByid(catId) : null;
        Topic topic = topicId != 0 ? topicService.findById(topicId) : null;
        Language language = languageId != 0 ? langService.getById(languageId) : null;

        List<TopicCategoryMapping> localcat = cat != null ? topicCatService.findAllByCategory(cat)
                : topicCatService.findAll();

        List<ContributorAssignedTutorial> cat_list = language != null
                ? conService.findAllByTopicCatAndLan(localcat, language)
                : conService.findAllByTopicCat(localcat);

        // To find Topics
        List<Tutorial> tutorials = tutService.findAllByconAssignedTutorialAndStatus(cat_list);

        /*
         * for(Tutorial t: tutorials) {
         * 
         * Category c = t.getConAssignedTutorial().getTopicCatId().getCat(); //Topic
         * topic2 = t.getConAssignedTutorial().getTopicCatId().getTopic();
         * TopicCategoryMapping tcp=t.getConAssignedTutorial().getTopicCatId(); Topic
         * topic2= tcp.getTopic(); if(c.isStatus()) { if(topic2.isStatus()) {
         * topics.put( topic2.getTopicName(),topic2.getTopicId()); //topics.put(
         * topic2.getTopicName(),tcp.getOrder());
         * 
         * } }
         * 
         * 
         * 
         * 
         * }
         */

        List<TopicCategoryMapping> tcmList = new ArrayList<>();
        for (Tutorial t : tutorials) {
            Category c = t.getConAssignedTutorial().getTopicCatId().getCat();
            TopicCategoryMapping tcp = t.getConAssignedTutorial().getTopicCatId();
            if (c.isStatus()) {

                if (tcp.getTopic().isStatus()) {
                    tcmList.add(tcp);
                }
            }

        }

        if (cat == null) {
            Collections.sort(tcmList, TopicCategoryMapping.SortByTopicName);
            for (TopicCategoryMapping tcm : tcmList) {
                Topic topic2 = tcm.getTopic();

                topics.put(topic2.getTopicName(), topic2.getTopicId());
            }
        } else {
            Set<TopicCategoryMapping> tcmset = new HashSet<>(tcmList);
            tcmList = new ArrayList<>(tcmset);
            Collections.sort(tcmList, TopicCategoryMapping.SortByOrderValue);

            int counter = 1;
            for (TopicCategoryMapping tcm : tcmList) {
                Topic topic2 = tcm.getTopic();

                topics.put(counter + ". " + topic2.getTopicName(), topic2.getTopicId());

                counter++;
            }
        }

        arlist.add(topics);

        if (topic != null) {
            if (cat != null) {
                localcat.clear();
                localcat.add(topicCatService.findAllByCategoryAndTopic(cat, topic));
            } else {
                localcat = topicCatService.findAllByTopic(topic);
            }
        }

        cat_list = conService.findAllByTopicCat(localcat);

        for (ContributorAssignedTutorial c : cat_list) {
            if (!tutService.findAllByContributorAssignedTutorialEnabled(c).isEmpty()) {
                languages.put(c.getLan().getLangName(), c.getLan().getLanId());
            }
        }

        arlist.add(languages);

        return arlist;

    }

    /*
     * Function to load Category and Language by Topic Author: Alok Kumar
     */

    @RequestMapping("/loadCategoryAndLanguageByTopic")
    @Cacheable(cacheNames = "topics", key = "{#root.methodName, #topicId, #catId, #languageId }")
    public @ResponseBody ArrayList<Map<String, Integer>> getCategoryAndLanguageByTopic(
            @RequestParam(value = "catId") int catId, @RequestParam(value = "topicId") int topicId,
            @RequestParam(value = "languageId") int languageId) {
        ArrayList<Map<String, Integer>> arlist = new ArrayList<>();

        Map<String, Integer> cats = new TreeMap<>();
        Map<String, Integer> languages = new TreeMap<>();

        Category cat = catId != 0 ? catService.findByid(catId) : null;
        Topic topic = topicId != 0 ? topicService.findById(topicId) : null;
        Language language = languageId != 0 ? langService.getById(languageId) : null;

        List<TopicCategoryMapping> localtopic = topic != null ? topicCatService.findAllByTopic(topic)
                : topicCatService.findAll();
        List<ContributorAssignedTutorial> topic_list = language != null
                ? conService.findAllByTopicCatAndLan(localtopic, language)
                : conService.findAllByTopicCat(localtopic);

        // To find category

        List<Tutorial> tutorials = tutService.findAllByconAssignedTutorialAndStatus(topic_list);

        for (Tutorial t : tutorials) {
            Category cat2 = t.getConAssignedTutorial().getTopicCatId().getCat();
            if (cat2.isStatus()) {
                cats.put(cat2.getCatName(), cat2.getCategoryId());
            }

        }

        arlist.add(cats);

        if (cat != null) {
            if (topic != null) {
                localtopic.clear();
                localtopic.add(topicCatService.findAllByCategoryAndTopic(cat, topic));
            } else {
                localtopic = topicCatService.findAllByCategory(cat);
            }
        }

        topic_list = conService.findAllByTopicCat(localtopic);

        // To find Languages
        for (ContributorAssignedTutorial c : topic_list) {
            Language lan = c.getLan();
            String langName = "";
            if (lan != null)
                langName = lan.getLangName();
            if (!languages.containsKey(langName) && langName != "") {
                List<Tutorial> tutlist = tutService.findAllByContributorAssignedTutorialEnabled(c);
                for (Tutorial t1 : tutlist) {
                    Category cat3 = t1.getConAssignedTutorial().getTopicCatId().getCat();
                    if (cat3.isStatus()) {
                        languages.put(langName, lan.getLanId());
                        break;
                    }
                }
            }
        }

        arlist.add(languages);

        return arlist;

    }

    /*
     * Function to load Category and Topic by Language Author: Alok Kumar
     */

    @RequestMapping("/loadCategoryAndTopicByLanguage")
    @Cacheable(cacheNames = "languages", key = "{#root.methodName, #languageId, #catId, #topicId }")
    public @ResponseBody ArrayList<Map<String, Integer>> getCategoryAndTopicByLanguage(
            @RequestParam(value = "catId") int catId, @RequestParam(value = "topicId") int topicId,
            @RequestParam(value = "languageId") int languageId) {

        ArrayList<Map<String, Integer>> arlist = new ArrayList<>();
        Map<String, Integer> cats = new TreeMap<>();
        Map<String, Integer> topics = new LinkedHashMap<>();

        Category cat = catId != 0 ? catService.findByid(catId) : null;
        Topic topic = topicId != 0 ? topicService.findById(topicId) : null;
        Language language = languageId != 0 ? langService.getById(languageId) : null;

        // To find category

        List<TopicCategoryMapping> local = topic != null ? topicCatService.findAllByTopic(topic)
                : topicCatService.findAll();

        List<ContributorAssignedTutorial> lang_list = language != null
                ? conService.findAllByTopicCatAndLan(local, language)
                : conService.findAllByTopicCat(local);

        List<Tutorial> tutorials = tutService.findAllByconAssignedTutorialAndStatus(lang_list);

        for (Tutorial t : tutorials) {
            Category cat2 = t.getConAssignedTutorial().getTopicCatId().getCat();
            if (cat2.isStatus()) {
                cats.put(cat2.getCatName(), cat2.getCategoryId());
            }

        }

        arlist.add(cats);

        // To find Topics

        List<TopicCategoryMapping> local2 = cat != null ? topicCatService.findAllByCategory(cat)
                : topicCatService.findAll();

        List<ContributorAssignedTutorial> lang_list2 = language != null
                ? conService.findAllByTopicCatAndLan(local2, language)
                : conService.findAllByTopicCat(local2);

        List<Tutorial> tutorials2 = tutService.findAllByconAssignedTutorialAndStatus(lang_list2);

        List<TopicCategoryMapping> tcmList = new ArrayList<>();
        for (Tutorial t : tutorials2) {
            Category c = t.getConAssignedTutorial().getTopicCatId().getCat();
            TopicCategoryMapping tcp = t.getConAssignedTutorial().getTopicCatId();
            if (c.isStatus()) {
                if (tcp.getTopic().isStatus()) {
                    tcmList.add(tcp);
                }
            }
        }

        if (cat == null) {
            Collections.sort(tcmList, TopicCategoryMapping.SortByTopicName);
            for (TopicCategoryMapping tcm : tcmList) {
                Topic topic2 = tcm.getTopic();

                topics.put(topic2.getTopicName(), topic2.getTopicId());
            }
        } else {
            Set<TopicCategoryMapping> tcmset = new HashSet<>(tcmList);
            tcmList = new ArrayList<>(tcmset);
            Collections.sort(tcmList, TopicCategoryMapping.SortByOrderValue);

            int counter = 1;
            for (TopicCategoryMapping tcm : tcmList) {
                Topic topic2 = tcm.getTopic();

                topics.put(counter + ". " + topic2.getTopicName(), topic2.getTopicId());

                counter++;
            }
        }
        arlist.add(topics);

        return arlist;

    }

    @RequestMapping("/loadTopicByCategoryInAssignContri")
    public @ResponseBody HashMap<Integer, String> getTopicByCategoryAssignContri(@RequestParam(value = "id") int id) {

        HashMap<Integer, String> topicName = new HashMap<>();

        Category cat = catService.findByid(id);
        logger.info("Variables of etTopicByCategoryAssignContri cat : {}", cat);
        List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat);
        for (TopicCategoryMapping t : local) {
            if (t.getTopic().isStatus()) {
                topicName.put(t.getTopic().getTopicId(), t.getTopic().getTopicName());
            }

        }
        return topicName;

    }

    @RequestMapping("/loadLanguageAndTopicByCategoryResource")
    @Cacheable(cacheNames = "downloadloadbycat", key = "{#root.methodName, #catId}")
    public @ResponseBody ArrayList<HashMap<Integer, String>> getLanguageByCategoryResourse(
            @RequestParam(value = "catId") int catId) {

        ArrayList<HashMap<Integer, String>> arlist = new ArrayList<>();
        HashMap<Integer, String> languagesMap = new HashMap<>();
        HashMap<Integer, String> topicMap = new HashMap<>();

        Category cat = catService.findByid(catId);
        logger.info("Variables of loadLanguageByCategoryResourse cat : {}, catId: {}", cat, catId);
        if (cat != null && cat.isStatus()) {
            List<TopicCategoryMapping> tcm = topicCatService.findAllByCategory(cat);
            List<ContributorAssignedTutorial> conList = conService.findAllByTopicCat(tcm);
            List<Tutorial> tutList = tutService.findAllByconAssignedTutorialAndStatus(conList);

            for (Tutorial tut : tutList) {
                ContributorAssignedTutorial conAssignedTutorial = tut.getConAssignedTutorial();
                Language lan = conAssignedTutorial.getLan();
                Topic topic = conAssignedTutorial.getTopicCatId().getTopic();

                languagesMap.put(lan.getLanId(), lan.getLangName());
                topicMap.put(topic.getTopicId(), topic.getTopicName());

            }

        }
        arlist.add(languagesMap);
        arlist.add(topicMap);

        return arlist;

    }

    @RequestMapping("/loadTutorialCountByTopicAndLanguage")
    public @ResponseBody int getTutorialCountByTopicAndLanguage(@RequestParam(value = "catId") int catId,
            @RequestParam(value = "topicIds[]") Optional<Integer[]> topicIds,
            @RequestParam(value = "lanIds[]") Optional<Integer[]> lanIds) {

        Category cat = catService.findByid(catId);
        logger.info("Variables of getTopicByCategoryAndLanguageResourse cat : {} catId:{}", cat, catId);
        logger.info("Variables of getTopicByCategoryAndLanguageResourse lan : {}", lanIds);
        logger.info("Variables of getTopicByCategoryAndLanguageResourse topic : {}", topicIds);
        int tutorialCount = 0;
        if (cat != null && cat.isStatus()) {

            List<ContributorAssignedTutorial> conList = new ArrayList<>();
            List<Language> languages = new ArrayList<>();
            List<Topic> topics = new ArrayList<>();
            List<Tutorial> tutList = new ArrayList<>();
            List<TopicCategoryMapping> tcmList = new ArrayList<>();

            if ((topicIds != null && topicIds.isPresent() && topicIds.get().length != 0)) {
                for (Integer topicId : topicIds.get()) {
                    Topic topic = topicService.findById(topicId);
                    topics.add(topic);
                    tcmList.add(topicCatService.findAllByCategoryAndTopic(cat, topic));
                }
            }

            if ((lanIds != null && lanIds.isPresent() && lanIds.get().length != 0)) {
                for (Integer lanId : lanIds.get()) {
                    languages.add(lanService.getById(lanId));
                }
            }

            if ((lanIds != null && lanIds.isPresent() && lanIds.get().length != 0)
                    && (topicIds != null && topicIds.isPresent() && topicIds.get().length != 0)) {

                for (Language lan : languages) {

                    List<ContributorAssignedTutorial> conList1 = conService.findAllByTopicCatAndLan(tcmList, lan);
                    if (conList1 != null) {
                        conList.addAll(conList1);

                    }
                }

            } else if ((lanIds != null && lanIds.isPresent() && lanIds.get().length != 0)) {
                List<TopicCategoryMapping> tcm = topicCatService.findAllByCategory(cat);
                for (Language lan : languages) {

                    List<ContributorAssignedTutorial> conList1 = conService.findAllByTopicCatAndLan(tcm, lan);
                    if (conList1 != null) {
                        conList.addAll(conList1);

                    }
                }

            } else if ((topicIds != null && topicIds.isPresent() && topicIds.get().length != 0)) {
                conList = conService.findAllByTopicCat(tcmList);

            }

            tutList = tutService.findAllByconAssignedTutorialAndStatus(conList);

            Set<Tutorial> tutorialSet = new LinkedHashSet<>(tutList);

            tutorialCount = tutorialSet.size();

        }

        return tutorialCount;
    }

//    @RequestMapping("/loadTutorialCountByTopics")
//    public @ResponseBody HashMap<Integer, String> getTutorialCountByTopic(@RequestParam(value = "catId") int catId,
//            @RequestParam(value = "topicIds[]") int[] topicIds) {
//
//        HashMap<Integer, String> tutorialCount = new HashMap<>();
//
//        Category cat = catService.findByid(catId);
//
//        logger.info("Variables of getTopicByCategoryAndLanguageResourse cat : {}", catId);
//
//        if (cat != null & cat.isStatus()) {
//            List<TopicCategoryMapping> tcm = new ArrayList<>();
//            List<ContributorAssignedTutorial> conList = new ArrayList<>();
//
//            if (topicIds != null) {
//                for (int i = 0; i < topicIds.length; i++) {
//                    tcm.add(topicCatService.findAllByCategoryAndTopic(cat, topicService.findById(topicIds[i])));
//
//                }
//            }
//            conList = conService.findAllByTopicCat(tcm);
//            List<Tutorial> tutList = tutService.findAllByconAssignedTutorialAndStatus(conList);
//
//            tutorialCount.put(tutList.size(), Integer.toString(tutList.size()));
//        }
//        return tutorialCount;
//
//    }

    /*
     * A new function by to load topic by Category
     * 
     */

    @RequestMapping("/loadTopicByCategoryInAddTopic")
    public @ResponseBody TreeMap<String, Integer> getTopicByCategoryAddTopic(@RequestParam(value = "id") int id) {

        TreeMap<String, Integer> topicName = new TreeMap<>();

        Category cat = catService.findByid(id);

        List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat);
        for (TopicCategoryMapping t : local) {
            if (t.getTopic().isStatus()) {
                topicName.put(t.getTopic().getTopicName(), t.getOrder());
            }

        }

        return topicName;

    }

    /**
     * return published tutorial
     * 
     * @param id         int value
     * @param tutorialId int
     * @param langName   String
     * @return hashMao
     * 
     */

    @RequestMapping("/loadTopicByCategoryPreRequistic")
    public @ResponseBody HashMap<Integer, String> getTopicByCategoryPreRequistic(@RequestParam(value = "id") String id,
            @RequestParam(value = "tutorialId") int tutorialId, @RequestParam(value = "langName") String langName) {

        HashMap<Integer, String> topicName = new HashMap<>();

        Language lan = lanService.getByLanName(langName);

        Category cat = catService.findBycategoryname(id);

        List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat);

        List<ContributorAssignedTutorial> cons = conService.findAllByTopicCat(local);

        List<ContributorAssignedTutorial> tempCon = new ArrayList<ContributorAssignedTutorial>();

        for (ContributorAssignedTutorial x : cons) {

            if (x.getLan().getLangName().equalsIgnoreCase(lan.getLangName())) {
                tempCon.add(x);
            }
        }

        List<Tutorial> tuts = tutService.findAllByContributorAssignedTutorialList(tempCon);

        if (tutorialId != 0) {
            Tutorial tut = tutService.getById(tutorialId);
            for (Tutorial temp : tuts) {
                if (temp.getTutorialId() != tut.getTutorialId()) {
                    if (temp.isStatus()) {
                        topicName.put(temp.getTutorialId(),
                                temp.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
                    }
                }
            }
        } else {
            for (Tutorial temp : tuts) {
                if (temp.isStatus()) {
                    topicName.put(temp.getTutorialId(),
                            temp.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
                }
            }
        }

        return topicName;
    }

    /**
     * Enable role from approve role tab under super admin role
     * 
     * @param id int
     * @return String object
     */

    @RequestMapping("/enableRoleById")
    public @ResponseBody String enableRoleById(@RequestParam(value = "id") long id) {

        UserRole usrRole = usrRoleService.findById(id);

        if (usrRole != null) {
            try {
                int val = usrRoleService.enableRole(usrRole);
                SimpleMailMessage msg = mailConstructor.approveRole(usrRole.getUser(), usrRole.getRole(),
                        usrRole.getCategory(), usrRole.getLan());

                mailSender.send(msg);

                if (val == 0) {
                    // return error
                }
            } catch (Exception e) {
                // TODO: handle exception

                // return error
            }

        } else {

            // return error
        }

        return CommonData.ROLE_APPROVED_SUCCESS_MSG;
    }

    /**
     * delete role
     * 
     * @param id int
     * @return String
     */

    @RequestMapping("/deleteRole")
    public @ResponseBody String deleteMasterRoleById(@RequestParam(value = "id") long id) {

        UserRole usrRole = usrRoleService.findById(id);

        if (usrRole != null) {
            try {

                if (usrRole.getRole().getRoleId() == 7) {
                    User usr = usrRole.getUser();
                    usr.setOrgRolev(null);
                    usr.setOrganization(null);
                    usr.setAge(0);
                    usr.setUserRoles(null);
                    usrservice.save(usr);
                }

                usrRole.setRole(null);
                usrRole.setUser(null);
                usrRole.setCat(null);
                usrRole.setLan(null);
                usrRole.setRejected(true);
                usrRoleService.save(usrRole);

                SimpleMailMessage msg = mailConstructor.rejectRole(usrRole.getUser(), usrRole.getRole(),
                        usrRole.getCategory(), usrRole.getLan());

                mailSender.send(msg);

            } catch (Exception e) {
                // TODO: handle exception

                // return error
            }

        } else {

            // return error
        }

        return "Role Disaaproved";
    }

    /**
     * return the language for which user has contributor role
     * 
     * @param username string
     * @return list of string
     */

    @RequestMapping("/loadLanguageByUser")
    public @ResponseBody List<String> getLanguageByContributor(@RequestParam(value = "id") String username) {

        List<String> langauges = new ArrayList<String>();

        User usr = usrservice.findByUsername(username);
        Role role = roleService.findByname(CommonData.contributorRole);
        Role role1 = roleService.findByname(CommonData.externalContributorRole);

        List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);
        userRoles.addAll(usrRoleService.findAllByRoleUserStatus(role1, usr, true));
        for (UserRole temp : userRoles) {
            if (temp.getLanguage() != null) {
                langauges.add(temp.getLanguage().getLangName());
            }
        }

        return langauges;
    }

    /**
     * return all the category
     * 
     * @return hashmap
     */

    @RequestMapping("/loadCategory")
    public @ResponseBody HashMap<Integer, String> getCategory() {

        HashMap<Integer, String> categories = new HashMap<>();

        List<Category> categoriesTemp = catService.findAll();

        for (Category cat : categoriesTemp) {
            if (cat.isStatus()) {
                categories.put(cat.getCategoryId(), cat.getCatName());
            }

        }

        return categories;
    }

    /**
     * return topic under category for contributor
     * 
     * @param catName   String
     * @param principal Principal object
     * @return hashmap
     */

    @RequestMapping("/loadTopicByCategoryOnContributorRole")
    public @ResponseBody HashMap<Integer, String> getTopicByCategoryOnContributorRole(
            @RequestParam(value = "id") String catName, Principal principal) {

        HashMap<Integer, String> topicName = new HashMap<>();

        User usr = getUser(principal);

        Category cat = catService.findBycategoryname(catName);
        logger.info("Variables of getTopicByCategoryOnContributorRole usr:{} cat: {}", usr.getUsername(), cat);

        List<TopicCategoryMapping> localTopicCat = topicCatService.findAllByCategory(cat);

        List<ContributorAssignedMultiUserTutorial> conTutorialByUser = conMultiService.getAllByuser(usr);

        for (ContributorAssignedMultiUserTutorial localCon : conTutorialByUser) {

            for (TopicCategoryMapping topicTemp : localTopicCat) {

                ContributorAssignedTutorial conTemp = conService.findById(localCon.getConAssignedTutorial().getId());

                if (conTemp.getTopicCatId().getTopicCategoryId() == topicTemp.getTopicCategoryId()) {

                    topicName.put(topicTemp.getTopic().getTopicId(), topicTemp.getTopic().getTopicName());
                }
            }
        }

        return topicName;

    }

    /**
     * Load langauge for contributor role
     * 
     * @param catName   String
     * @param topicId   int value
     * @param principal Principal object
     * @return Set
     */

    @RequestMapping("/loadLanguageForContributorRoleTutorial")
    public @ResponseBody Set<String> getLanguageByContributorRole(@RequestParam(value = "id") String catName,
            @RequestParam(value = "topicId") int topicId, Principal principal) {

        Set<String> languages = new HashSet<String>();

        User usr = getUser(principal);
        Category cat = catService.findBycategoryname(catName);
        Topic topic = topicService.findById(topicId);
        TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);

        logger.info("Variables of getLanguageByContributorRole usr: {}cat : {} topic : {} localTopicCat : {}",
                usr.getUsername(), cat, topic, localTopicCat);

        List<ContributorAssignedMultiUserTutorial> conTutorialByUser = conMultiService.getAllByuser(usr);

        for (ContributorAssignedMultiUserTutorial temp : conTutorialByUser) {

            ContributorAssignedTutorial conTemp = conService.findById(temp.getConAssignedTutorial().getId());
            if (conTemp.getTopicCatId().getTopicCategoryId() == localTopicCat.getTopicCategoryId()) {
                languages.add(conTemp.getLan().getLangName());
            }
        }

        return languages;
    }

    /***********************************
     * CONTRIBUTOR SIDE
     *********************************************/
    /**
     * return outline given tutorial id
     * 
     * @param tutorialId int
     * @return string
     */

    @RequestMapping("/viewOutline")
    public @ResponseBody String viewOutline(@RequestParam(value = "id") int tutorialId) {

        Tutorial tut = tutService.getById(tutorialId);
        if (tut.getOutline() != null) {
            return tut.getOutline();
        }
        return null;

    }

    /**
     * add outline to the tutorial
     * 
     * @param tutorialId  int
     * @param outlineData string
     * @param catName     string
     * @param topicId     int
     * @param lanId       string
     * @param principal   principal object
     * @return string
     */
    @PostMapping("/addOutline")
    public @ResponseBody HashMap<String, String> addOutline(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "saveOutline") String outlineData,
            @RequestParam(value = "categoryname") String catName, @RequestParam(value = "topicid") int topicId,
            @RequestParam(value = "lanId") String lang, Principal principal) {

        HashMap<String, String> temp = new HashMap<>();

        Category cat = catService.findBycategoryname(catName);

        Topic topic = topicService.findById(topicId);
        TopicCategoryMapping tcm = topicCatService.findAllByCategoryAndTopic(cat, topic);
        Language language = langService.getByLanName(lang);
        ContributorAssignedTutorial cnn = conService.findByTopicCatAndLanViewPart(tcm, language);
        User usr = getUser(principal);
        Tutorial local = null;

        /*
         * List<Tutorial> tut = tutService.findAllByContributorAssignedTutorial(cnn);
         * 
         * 
         * logger.
         * info("Variables of addOutline usr: {} tutorialId : {} topicId : {} catName : {} langName {}"
         * , usr.getUsername(), tutorialId, topicId, catName, lang); Tutorial local =
         * null;
         * 
         * int tutId1 = 0; if (tut.size() > 0) { Tutorial tut1 = tut.get(0); tutId1 =
         * tut1.getTutorialId(); }
         */

        if (tutorialId != 0) {
            Tutorial tut2 = tutService.getById(tutorialId);
            temp = addOutlineComp(tut2, outlineData, usr);
            return temp;
        }

        else {
            local = createTutorial(catName, topicId, lang, usr);
            if (local != null) {
                temp = addOutlineComp(local, outlineData, usr);
            } else {
                temp = updateResponse(ERROR_TOKEN, TUTORIAL_CREATION_ERROR, 0, 0, usr);
            }
        }

        // alok
        /*
         * User usr=getUser(principal); Tutorial local = null; if(tutorialId!=0) {
         * Tutorial tut=tutService.getById(tutorialId); temp =
         * addOutlineComp(tut,outlineData,usr); return temp; }else { local =
         * createTutorial(catName, topicId, lang,usr); if(local!=null) { temp =
         * addOutlineComp(local,outlineData,usr); }else { temp =
         * updateResponse(ERROR_TOKEN, TUTORIAL_CREATION_ERROR, 0,0,usr); } }
         */
        return temp;
    }

    /**
     * view keyword of tutorial given tutorial id
     * 
     * @param tutorialId int
     * @return string
     */

    @RequestMapping("/viewKeyword")
    public @ResponseBody String viewkeyword(@RequestParam(value = "id") int tutorialId) {

        Tutorial tut = tutService.getById(tutorialId);
        if (tut.getKeyword() != null) {
            return tut.getKeyword();
        }
        return null;

    }

    /**
     * add keyword to the tutorial
     * 
     * @param tutorialId  int
     * @param keywordData string
     * @param catName     string
     * @param topicId     int
     * @param lanId       string
     * @param principal   principal object
     * @return string
     */

    @RequestMapping("/addKeyword")
    public @ResponseBody HashMap<String, String> addKeyword(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "savekeyword") String keywordData,
            @RequestParam(value = "categoryname") String catName, @RequestParam(value = "topicid") int topicId,
            @RequestParam(value = "lanId") String lanId, Principal principal) {

        HashMap<String, String> temp = new HashMap<>();
        User usr = getUser(principal);
        Tutorial local = null;
        if (tutorialId != 0) {
            Tutorial tut = tutService.getById(tutorialId);
            temp = addKeywordComp(tut, keywordData, usr);

        } else {
            local = createTutorial(catName, topicId, lanId, usr);
            if (local != null) {
                temp = addKeywordComp(local, keywordData, usr);
            } else {
                temp = updateResponse(ERROR_TOKEN, TUTORIAL_CREATION_ERROR, ADD_COMPONENT, NULL_TUTORIAL, usr);
            }
        }
        return temp;

    }

    /**
     * Add pre requisite component of tutorial when none given
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String object
     */

    @RequestMapping("/addPreRequisticWhenNotRequired")
    public @ResponseBody HashMap<String, String> addPreRequistic(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "categoryname") String catName, @RequestParam(value = "topicid") int topicId,
            @RequestParam(value = "lanId") String lanId, Principal principal) {

        HashMap<String, String> temp = new HashMap<>();
        User usr = getUser(principal);

        Tutorial tut = null;

        if (tutorialId != 0) {
            tut = tutService.getById(tutorialId);
            temp = addNullPreReq(tut, usr);
        } else {
            tut = createTutorial(catName, topicId, lanId, usr);
            if (tut != null) {
                temp = addNullPreReq(tut, usr);
            } else {
                temp = updateResponse(ERROR_TOKEN, TUTORIAL_CREATION_ERROR, 0, 0, usr);
            }
        }
        return temp;
    }

    /**
     * Add pre requisite component of tutorial
     * 
     * @param tutorialId int value
     * @param catName    String
     * @param topicId    int
     * @param lanId      string
     * @param principal  Principal object
     * @return string
     */

    @RequestMapping("/addPreRequistic")
    public @ResponseBody HashMap<String, String> addPreRequistic(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "categoryname") String catName, @RequestParam(value = "topicid") int topicId,
            @RequestParam(value = "lanId") String lanId, @RequestParam(value = "preReqTutId") int preReqTutId,
            Principal principal) {

        HashMap<String, String> temp = new HashMap<>();
        User usr = getUser(principal);

        Tutorial tut = null;
        Tutorial local = null;
        Tutorial preReq = tutService.getById(preReqTutId);
        if (tutorialId != 0) {
            tut = tutService.getById(tutorialId);
            temp = addPreReqComp(tut, preReq, usr);
        } else {
            local = createTutorial(catName, topicId, lanId, usr);
            if (local != null) {
                temp = addPreReqComp(local, preReq, usr);
            } else {
                temp = updateResponse(ERROR_TOKEN, TUTORIAL_CREATION_ERROR, ADD_COMPONENT, NULL_TUTORIAL, usr);
            }
        }
        return temp;
    }

    /**
     * Add video component of tutorial
     * 
     * @param tutorialId int value
     * @param videoFile  MultipartFile
     * @param catName    String
     * @param topicId    int
     * @param lanId      string
     * @param principal  Principal object
     * @return string
     */

    @RequestMapping("/addVideo")
    public @ResponseBody HashMap<String, String> addKeyword(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "videoFileName") MultipartFile videoFile,
            @RequestParam(value = "categoryname") String catName, @RequestParam(value = "topicid") int topicId,
            @RequestParam(value = "lanId") String lanId, Principal principal) {

        HashMap<String, String> temp = new HashMap<>();

        User usr = getUser(principal);

        if (!ServiceUtility.checkFileExtensionVideo(videoFile)) { // throw error on extension
            temp = updateResponse(ERROR_TOKEN, VIDEO_EXTN_ERROR, ADD_COMPONENT, tutorialId, usr);
            return temp;
        }
        if (!ServiceUtility.checkVideoSize(videoFile)) {
            temp = updateResponse(ERROR_TOKEN, VIDEO_SIZE_ERROR, ADD_COMPONENT, tutorialId, usr);
            return temp;
        }
        Tutorial local = null;
        if (tutorialId != 0) {
            Tutorial tut = tutService.getById(tutorialId);
            logger.info("Variable of addKeyWord usr:{} tut: {}", usr.getUsername(), tut);
            temp = addVideoComp(tut, videoFile, usr);
        } else {
            local = createTutorial(catName, topicId, lanId, usr);
            if (local != null) {
                temp = addVideoComp(local, videoFile, usr);
            } else {
                temp = updateResponse(ERROR_TOKEN, TUTORIAL_CREATION_ERROR, ADD_COMPONENT, NULL_TUTORIAL, usr);
            }

        }
        return temp;

    }

    /**
     * Add Slide component of tutorial
     * 
     * @param tutorialId int value
     * @param videoFile  MultipartFile
     * @param catName    String
     * @param topicId    int
     * @param lanId      string
     * @param principal  Principal object
     * @return string
     */

    @RequestMapping("/addSlide")
    public @ResponseBody HashMap<String, String> addSlide(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "uploadsSlideFile") MultipartFile videoFile,
            @RequestParam(value = "categoryname") String catName, @RequestParam(value = "topicid") int topicId,
            @RequestParam(value = "lanId") String lanId, Principal principal) {

        HashMap<String, String> temp = new HashMap<>();
        User usr = getUser(principal);

        if (!ServiceUtility.checkFileExtensionZip(videoFile)) { // throw error on extension
            temp = updateResponse(ERROR_TOKEN, ZIP_EXTN_ERROR, ADD_COMPONENT, tutorialId, usr);
            return temp;
        }
        if (!ServiceUtility.checkScriptSlideProfileQuestion(videoFile)) {
            temp = updateResponse(ERROR_TOKEN, SLIDE_SIZE_ERROR, ADD_COMPONENT, tutorialId, usr);
            return temp;
        }
        Tutorial local = null;
        if (tutorialId != 0) {
            Tutorial tut = tutService.getById(tutorialId);
            logger.info("Variable of  addSlide usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
            temp = addSlideComp(tut, videoFile, usr);
        } else {
            local = createTutorial(catName, topicId, lanId, usr);
            logger.info("Variable of  addSlide usr:{} tutorial : {}", usr.getUsername(), local);
            if (local != null) {
                temp = addSlideComp(local, videoFile, usr);
            } else {
                temp = updateResponse(ERROR_TOKEN, TUTORIAL_CREATION_ERROR, ADD_COMPONENT, NULL_TUTORIAL, usr);
            }
        }
        return temp;

    }

    /**
     * Add script component of tutorial
     * 
     * @param tutorialId int value
     * @param videoFile  MultipartFile
     * @param catName    String
     * @param topicId    int
     * @param lanId      string
     * @param principal  Principal object
     * @return string
     */

    @RequestMapping("/addScript")
    public @ResponseBody HashMap<String, String> addScript(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "categoryname") String catName, @RequestParam(value = "topicid") int topicId,
            @RequestParam(value = "lanId") String lang, Principal principal) {

        HashMap<String, String> temp = new HashMap<>();
        User usr = getUser(principal);

        if (tutorialId != 0) {
            Tutorial tut = tutService.getById(tutorialId);
            logger.info("Variable of  addScript usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
            temp = addScriptComp(tut, usr);
        } else {
            Tutorial tut = createTutorial(catName, topicId, lang, usr);
            logger.info("Variable of  addScript usr:{} tutorial : {}", usr.getUsername(), tut);
            temp = addScriptComp(tut, usr);
        }
        return temp;

    }

    /**
     * accept video component from admin reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */

    @GetMapping("/acceptAdminVideo")
    public @ResponseBody HashMap<String, String> addAdminVideo(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        User usr = getUser(principal);
        logger.info("Variable of addAdminVideo tutorialId : {}", tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO,
                CommonData.DOMAIN_STATUS, tutorial.getVideoStatus(), CommonData.adminReviewerRole, usr, tutorial);
        tutorial.setVideoStatus(CommonData.DOMAIN_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        HashMap<String, String> temp = setResponse(tutorial.getVideoStatus());
        return temp;
    }

    /**
     * accept outline component from domain reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptDomainOutline")
    public @ResponseBody HashMap<String, String> acceptDomainOutline(@RequestParam(value = "id") int tutorialId,
            Principal principal) {

        HashMap<String, String> temp = new HashMap<String, String>();
        User usr = getUser(principal);
        logger.info("Variable of acceptDomainOutline usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.OUTLINE, CommonData.QUALITY_STATUS, tutorial.getOutlineStatus(),
                CommonData.domainReviewerRole, usr, tutorial);
        tutorial.setOutlineStatus(CommonData.QUALITY_STATUS);
        tutService.save(tutorial);
        logService.save(log);
//		return CommonData.Outline_SAVE_SUCCESS_MSG;
        temp.put("response", CommonData.SUCCESS_STATUS);
        temp.put("status", CommonData.tutorialStatus[tutorial.getOutlineStatus()]);
//		ToDO Add user
        // temp.put("user", usrservice.);
        // return CommonData.SUCCESS_STATUS;
        return temp;

    }

    /**
     * accept script component from domain reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptDomainScript")
    public @ResponseBody HashMap<String, String> acceptDomainScript(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        HashMap<String, String> temp = new HashMap<String, String>();
        User usr = getUser(principal);
        logger.info("Variable of acceptDomainScript usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT,
                CommonData.QUALITY_STATUS, tutorial.getScriptStatus(), CommonData.domainReviewerRole, usr, tutorial);

        tutorial.setScriptStatus(CommonData.QUALITY_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        temp.put("response", CommonData.SUCCESS_STATUS);
        temp.put("status", CommonData.tutorialStatus[tutorial.getScriptStatus()]);
//		return CommonData.SCRIPT_FOR_QUALITY_REVIEW;
        return temp;

    }

    /**
     * accept video component from domain reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptDomainVideo")
    public @ResponseBody HashMap<String, String> acceptDomainVideo(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        HashMap<String, String> temp = new HashMap<String, String>();
        User usr = getUser(principal);
        logger.info("Variable of acceptDomainVideo usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO,
                CommonData.QUALITY_STATUS, tutorial.getVideoStatus(), CommonData.domainReviewerRole, usr, tutorial);

        tutorial.setVideoStatus(CommonData.QUALITY_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        temp.put("response", CommonData.SUCCESS_STATUS);
        temp.put("status", CommonData.tutorialStatus[tutorial.getVideoStatus()]);
        // return CommonData.Video_SAVE_SUCCESS_MSG;
        return temp;

    }

    /**
     * accept slide component from domain reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptDomainSlide")
    public @ResponseBody HashMap<String, String> acceptDomainSlide(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        HashMap<String, String> temp = new HashMap<String, String>();
        User usr = getUser(principal);
        logger.info("Variable of acceptDomainSlide usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE,
                CommonData.QUALITY_STATUS, tutorial.getSlideStatus(), CommonData.domainReviewerRole, usr, tutorial);
        tutorial.setSlideStatus(CommonData.QUALITY_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        temp.put("response", CommonData.SUCCESS_STATUS);
        temp.put("status", CommonData.tutorialStatus[tutorial.getSlideStatus()]);
//		return CommonData.Slide_SAVE_SUCCESS_MSG;
        return temp;

    }

    /**
     * accept keyword component from domain reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptDomainKeywords")
    public @ResponseBody HashMap<String, String> acceptDomainKeywords(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        HashMap<String, String> temp = new HashMap<String, String>();
        User usr = getUser(principal);
        logger.info("Variable of acceptDomainKeywords usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.KEYWORD, CommonData.QUALITY_STATUS, tutorial.getKeywordStatus(),
                CommonData.domainReviewerRole, usr, tutorial);
        tutorial.setKeywordStatus(CommonData.QUALITY_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        temp.put("response", CommonData.SUCCESS_STATUS);
        temp.put("status", CommonData.tutorialStatus[tutorial.getKeywordStatus()]);
        return temp;

    }

    /**
     * accept pre requisite component from domain reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptDomainPreRequistic")
    public @ResponseBody HashMap<String, String> acceptDomainPreRequistic(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        HashMap<String, String> temp = new HashMap<String, String>();
        User usr = getUser(principal);
        logger.info("Variable of acceptDomainPreRequistic usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.PRE_REQUISTIC, CommonData.QUALITY_STATUS, tutorial.getPreRequisticStatus(),
                CommonData.domainReviewerRole, usr, tutorial);
        tutorial.setPreRequisticStatus(CommonData.QUALITY_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        temp.put("response", CommonData.SUCCESS_STATUS);
        temp.put("status", CommonData.tutorialStatus[tutorial.getPreRequisticStatus()]);
        // return CommonData.PRE_REQUISTIC_SAVE_SUCCESS_MSG;
        return temp;

    }

    /***********************************
     * END
     ***************************************************************/

    /**********************************
     * operation at Quality USER
     *****************************************/

    /**
     * accept outline component from quality reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptQualityOutline")
    public @ResponseBody HashMap<String, String> acceptQualityOutline(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        User usr = getUser(principal);
        logger.info("Variable of acceptQualityOutline usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.OUTLINE, CommonData.WAITING_PUBLISH_STATUS, tutorial.getOutlineStatus(),
                CommonData.qualityReviewerRole, usr, tutorial);
        tutorial.setOutlineStatus(CommonData.WAITING_PUBLISH_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        HashMap<String, String> temp = setResponse(tutorial.getOutlineStatus());
        return temp;
    }

    /**
     * accept script component from quality reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptQualityScript")
    public @ResponseBody HashMap<String, String> acceptQualityScript(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        User usr = getUser(principal);
        logger.info("Variable of acceptQualityScript usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT,
                CommonData.WAITING_PUBLISH_STATUS, tutorial.getScriptStatus(), CommonData.qualityReviewerRole, usr,
                tutorial);
        tutorial.setScriptStatus(CommonData.WAITING_PUBLISH_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        HashMap<String, String> temp = setResponse(tutorial.getScriptStatus());
        return temp;
    }

    /**
     * accept video component from quality reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptQualityVideo")
    public @ResponseBody HashMap<String, String> acceptQualityVideo(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        User usr = getUser(principal);
        logger.info("Variable of acceptQualityVideo usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO,
                CommonData.WAITING_PUBLISH_STATUS, tutorial.getVideoStatus(), CommonData.qualityReviewerRole, usr,
                tutorial);
        tutorial.setVideoStatus(CommonData.WAITING_PUBLISH_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        HashMap<String, String> temp = setResponse(tutorial.getVideoStatus());
        return temp;
    }

    /**
     * accept slide component from quality reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptQualitySlide")
    public @ResponseBody HashMap<String, String> acceptQualitySlide(@RequestParam(value = "id") int tutorialId,
            Principal principal) {
        User usr = getUser(principal);
        logger.info("Variable of acceptQualitySlide usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE,
                CommonData.WAITING_PUBLISH_STATUS, tutorial.getSlideStatus(), CommonData.qualityReviewerRole, usr,
                tutorial);
        tutorial.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        HashMap<String, String> temp = setResponse(tutorial.getSlideStatus());
        return temp;
    }

    /**
     * accept keyword component from quality reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptQualityKeywords")
    public @ResponseBody HashMap<String, String> acceptQualityKeywords(@RequestParam(value = "id") int tutorialId,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("Variable of acceptQualityKeywords usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        Tutorial tutorial = tutService.getById(tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.KEYWORD, CommonData.WAITING_PUBLISH_STATUS, tutorial.getKeywordStatus(),
                CommonData.qualityReviewerRole, usr, tutorial);
        tutorial.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        HashMap<String, String> temp = setResponse(tutorial.getKeywordStatus());
        return temp;

    }

    /**
     * accept pre requisite component from quality reviewer
     * 
     * @param tutorialId int value
     * @param principal  Principal object
     * @return String
     */
    @RequestMapping("/acceptQualityPreRequistic")
    public @ResponseBody HashMap<String, String> acceptQualityPreRequistic(@RequestParam(value = "id") int tutorialId,
            Principal principal) {

        User usr = getUser(principal);
        Tutorial tutorial = tutService.getById(tutorialId);
        logger.info("Variable of acceptQualityPreRequistic usr:{} tutorialId : {}", usr.getUsername(), tutorialId);
        LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.PRE_REQUISTIC, CommonData.WAITING_PUBLISH_STATUS, tutorial.getPreRequisticStatus(),
                CommonData.qualityReviewerRole, usr, tutorial);
        tutorial.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
        tutService.save(tutorial);
        logService.save(log);
        HashMap<String, String> temp = setResponse(tutorial.getPreRequisticStatus());
        return temp;

    }

    /***********************************
     * END
     ***************************************************************/
    /*******************************
     * COMMENT MODULE UNDER CREATION PART
     ********************************/

    /**
     * records comment made by user given type of component
     * 
     * @param tutorialId int value
     * @param msg        string
     * @param type       string
     * @param role       String
     * @param principal  Principal object
     * @return string
     */
    @RequestMapping("/commentByReviewer")
    public @ResponseBody HashMap<String, String> commentByReviewer(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "msg") String msg, @RequestParam(value = "type") String type,
            @RequestParam(value = "role") String role, Principal principal) {
        HashMap<String, String> temp = new HashMap<String, String>();

        User usr = getUser(principal);

        String roleName = null;
        int statusvalue = 0;
        String typeValue = null;

        if (role.equalsIgnoreCase(QUALITY)) {
            roleName = CommonData.qualityReviewerRole;
        } else if (role.equalsIgnoreCase(DOMAIN)) {
            roleName = CommonData.domainReviewerRole;
        } else if (role.equalsIgnoreCase(ADMIN_REVIEWER)) {
            roleName = CommonData.adminReviewerRole;
        }
        Tutorial tut = tutService.getById(tutorialId);

        Comment com = new Comment();
        com.setComment(msg);
        com.setCommentId(comService.getNewCommendId());
        com.setDateAdded(ServiceUtility.getCurrentTime());
        com.setRoleName(roleName);
        if (type.equalsIgnoreCase(CommonData.SCRIPT)) {
            com.setType(CommonData.SCRIPT);
            statusvalue = tut.getScriptStatus();
            typeValue = CommonData.SCRIPT;

        } else if (type.equalsIgnoreCase(CommonData.KEYWORD)) {
            com.setType(CommonData.KEYWORD);
            statusvalue = tut.getKeywordStatus();
            typeValue = CommonData.KEYWORD;

        } else if (type.equalsIgnoreCase(CommonData.SLIDE)) {
            com.setType(CommonData.SLIDE);
            statusvalue = tut.getSlideStatus();
            typeValue = CommonData.SLIDE;

        } else if (type.equalsIgnoreCase(CommonData.VIDEO)) {
            com.setType(CommonData.VIDEO);
            statusvalue = tut.getVideoStatus();
            typeValue = CommonData.VIDEO;

        } else if (type.equalsIgnoreCase(CommonData.PRE_REQUISTIC)) {
            com.setType(CommonData.PRE_REQUISTIC);
            statusvalue = tut.getPreRequisticStatus();
            typeValue = CommonData.PRE_REQUISTIC;

        } else if (type.equalsIgnoreCase(CommonData.OUTLINE)) {
            com.setType(CommonData.OUTLINE);
            statusvalue = tut.getOutlineStatus();
            typeValue = CommonData.OUTLINE;

        } else {
            temp.put("response", CommonData.FAILURE_STATUS);
            temp.put("status", CommonData.tutorialStatus[statusvalue]);
            return temp;
        }

        com.setUser(usr);
        com.setTutorialInfos(tut);

        try {
            comService.save(com);
            LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), typeValue,
                    CommonData.IMPROVEMENT_STATUS, statusvalue, roleName, usr, tut);
            if (type.equalsIgnoreCase(CommonData.SCRIPT)) {
                tut.setScriptStatus(CommonData.IMPROVEMENT_STATUS);
            } else if (type.equalsIgnoreCase(CommonData.KEYWORD)) {
                tut.setKeywordStatus(CommonData.IMPROVEMENT_STATUS);
            } else if (type.equalsIgnoreCase(CommonData.SLIDE)) {
                tut.setSlideStatus(CommonData.IMPROVEMENT_STATUS);
            } else if (type.equalsIgnoreCase(CommonData.VIDEO)) {
                tut.setVideoStatus(CommonData.IMPROVEMENT_STATUS);
            } else if (type.equalsIgnoreCase(CommonData.PRE_REQUISTIC)) {
                tut.setPreRequisticStatus(CommonData.IMPROVEMENT_STATUS);
            } else if (type.equalsIgnoreCase(CommonData.OUTLINE)) {
                tut.setOutlineStatus(CommonData.IMPROVEMENT_STATUS);
            }

            tutService.save(tut);
            logService.save(log);

            temp.put("response", CommonData.SUCCESS_STATUS);
            temp.put("status", CommonData.tutorialStatus[CommonData.IMPROVEMENT_STATUS]);
            return temp;

        } catch (Exception e) {
            temp.put("response", CommonData.FAILURE_STATUS);
            temp.put("status", CommonData.tutorialStatus[statusvalue]);
            return temp;
        }
    }

    /**
     * records comment made by user under contributor role given type of component
     * 
     * @param tutorialId int value
     * @param msg        string
     * @param type       string
     * @param principal  Principal object
     * @return string
     */
    @RequestMapping("/commentByContributor")
    public @ResponseBody String commentByContributor(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "type") String type, @RequestParam(value = "msg") String msg, Principal principal) {

        User usr = getUser(principal);

        Tutorial tut = tutService.getById(tutorialId);

        Comment com = new Comment();
        com.setComment(msg);
        com.setCommentId(comService.getNewCommendId());
        com.setDateAdded(ServiceUtility.getCurrentTime());
        com.setUser(usr);
        com.setTutorialInfos(tut);
        com.setRoleName(CommonData.contributorRole);

        if (type.equalsIgnoreCase(CommonData.SCRIPT)) {
            com.setType(CommonData.SCRIPT);

        } else if (type.equalsIgnoreCase(CommonData.KEYWORD)) {
            com.setType(CommonData.KEYWORD);

        } else if (type.equalsIgnoreCase(CommonData.SLIDE)) {
            com.setType(CommonData.SLIDE);

        } else if (type.equalsIgnoreCase(CommonData.VIDEO)) {
            com.setType(CommonData.VIDEO);

        } else if (type.equalsIgnoreCase(CommonData.PRE_REQUISTIC)) {
            com.setType(CommonData.PRE_REQUISTIC);

        } else if (type.equalsIgnoreCase(CommonData.OUTLINE)) {
            com.setType(CommonData.OUTLINE);

        }

        try {
            comService.save(com);

            return CommonData.COMMENT_SUCCESS;

        } catch (Exception e) {
            // TODO: handle exception
            return CommonData.FAILURE;
        }

    }

    /**
     * Add user details from homepage to database
     * 
     * @param contactData FeedbackForm object
     * @return list of String
     */
    @PostMapping("/addContactForm")
    public @ResponseBody List<String> addContactData(@Valid @RequestBody FeedbackForm contactData) {
        List<String> status = new ArrayList<String>();

        try {
            FeedbackForm addLocal = new FeedbackForm();
            addLocal.setId(ffService.getNewId());
            addLocal.setDateAdded(ServiceUtility.getCurrentTime());
            addLocal.setEmail(contactData.getEmail());
            addLocal.setMessage(contactData.getMessage());
            addLocal.setName(contactData.getName());

            ffService.save(addLocal);
            SimpleMailMessage msg = mailConstructor.feedbackMailSend(contactData.getName(), contactData.getEmail(),
                    contactData.getMessage());
            mailSender.send(msg);

            status.add("Success");

        } catch (Exception e) {

            status.add("Failure");
            logger.error("Error in add Contact Data : {} {} {}", contactData.getEmail(), contactData.getMessage(),
                    contactData.getName(), e);
        }

        return status;
    }

    /**
     * add profile picture of user
     * 
     * @param uploadPhoto MultipartFile
     * @param principal   Principal object
     * @return string
     * @throws Exception
     */
    @PostMapping("/updateProfilePic")
    public @ResponseBody String updateProfilePic(@RequestParam("profilePicture") MultipartFile uploadPhoto,
            Principal principal) throws Exception {

        String folder = CommonData.uploadUserImage + principal.getName();
        String document = ServiceUtility.uploadMediaFile(uploadPhoto, env, folder);

        User usr = usrservice.findByUsername(principal.getName());
        usr.setProfilePic(document);

        usrservice.save(usr);

        return "ok";
    }

    /**
     * return consultant record
     * 
     * @param consultantId int value
     * @return list of String
     */
    @GetMapping("/getConsultantDetails")
    public @ResponseBody List<String> getConsultantDetailsInfo(@RequestParam(value = "id") int consultantId) {
        Consultant cons = consultantService.findById(consultantId);
        logger.info("Variables of getConsultantDetails consultantId : {}", consultantId);
        User user = cons.getUser();

        List<UserRole> userRoles = usrRoleService
                .findAllByRoleUserStatus(roleService.findByname(CommonData.domainReviewerRole), user, true);
        List<String> cat_lang = new ArrayList<>();
        if (userRoles.size() > 0) {
            for (UserRole u : userRoles) {
                if (u.getRole().getRoleId() == 4) {

                    cat_lang.add(u.getCat().getCatName() + " - " + u.getLan().getLangName());

                }
            }
        }
        return cat_lang;

    }

    /**
     * returns all the language available in the database
     * 
     * @return list of string
     */
    @RequestMapping("/loadLanguages")
    public @ResponseBody List<String> getLanguages() {

        List<String> langauges = new ArrayList<String>();
        List<Language> langs = langService.getAllLanguages();

        for (Language temp : langs) {
            langauges.add(temp.getLangName());
        }

        return langauges;
    }

    /************************************
     * END
     ********************************************************/

    /****************************
     * UPLOAD TIME SCRIPT
     ***********************************************/

    /**
     * add Timescript from contributor role
     * 
     * @param tutorialId int value
     * @param File       MultipartFile
     * @param principal  Principal
     * @return string
     */
    @RequestMapping("/addTimeScript")
    public @ResponseBody String addTimeScript(@RequestParam(value = "id") int tutorialId,
            @RequestParam(value = "uploadsScriptFile") MultipartFile File, Principal principal) {

        // User usr = new User();

        // if (principal != null) {

        // usr = usrservice.findByUsername(principal.getName());
        // }

        if (tutorialId != 0) {
            Tutorial tut = tutService.getById(tutorialId);

            try {

                String folder = CommonData.uploadDirectoryTutorial + tut.getTutorialId() + "/TimeScript";
                String document = ServiceUtility.uploadMediaFile(File, env, folder);

                tut.setTimeScript(document);
                if (tut.isStatus() && tut.isAddedQueue()) {
                    taskProcessingService.addUpdateDeleteTutorial(tut, CommonData.UPDATE_DOCUMENT);
                }

                else if (tut.isStatus()) {
                    taskProcessingService.addUpdateDeleteTutorial(tut, CommonData.ADD_DOCUMENT);
                }
                tutService.save(tut);

                return CommonData.Script_SAVE_SUCCESS_MSG;

            } catch (Exception e) {
                logger.error("Time Script: Upload Error {}", tut, e);
            }

        }
        return CommonData.SCRIPT_UPLOAD_ERROR;

    }

    // route after clicking the link in mail
//	intern start
//	@PostMapping("/process_register")
//    public String processRegister(User user, HttpServletRequest request)
//            throws UnsupportedEncodingException, MessagingException {
//        usrservice.register(user, getSiteURL(request));       
//        return "register_success";
//    }
//	
//	//route to verify
//	
//	@GetMapping("/verify")
//	public String verifyUser(@Param("code") String code) {
//	    if (UserService.verify(code)) {
//	        return "verify_success";
//	    } else {
//	        return "verify_fail";
//	    }
//	}
//	intern end

    /*
     * private String getSiteURL(HttpServletRequest request) { String siteURL =
     * request.getRequestURL().toString(); return
     * siteURL.replace(request.getServletPath(), ""); }
     */

    /***********************************
     * END
     ********************************************************/

    @RequestMapping("/tutCountOnCat")
    public @ResponseBody String getTotalCountCat(String id) {

        Category cat = catService.findBycategoryname(id);
        int total = 0;
        List<Tutorial> tutorials = tutService.findAllByStatus(true);

        for (Tutorial temp : tutorials) {
            if (temp.getConAssignedTutorial().getTopicCatId().getCat().getCatName()
                    .equalsIgnoreCase(cat.getCatName())) {
                total++;
            }
        }

        return "Total number of tutorial under " + id + " is " + total;
    }

    @RequestMapping("/tutCountOnLan")
    public @ResponseBody String getTotalCountLan(String id) {

        Language lan = lanService.getByLanName(id);
        int total = 0;
        List<Tutorial> tutorials = tutService.findAllByStatus(true);

        for (Tutorial temp : tutorials) {
            if (temp.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase(lan.getLangName())) {
                total++;
            }
        }

        return "Total number of tutorial under " + id + " Language is " + total;
    }

    private void changeAllCompStatus(Tutorial tutorial, User user) {
        LogManegement log_outline = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.OUTLINE, CommonData.IMPROVEMENT_STATUS, tutorial.getOutlineStatus(),
                CommonData.contributorRole, user, tutorial);
        tutorial.setOutlineStatus(CommonData.IMPROVEMENT_STATUS);
        logService.save(log_outline);
        LogManegement log_script = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.SCRIPT, CommonData.IMPROVEMENT_STATUS, tutorial.getScriptStatus(),
                CommonData.contributorRole, user, tutorial);
        tutorial.setScriptStatus(CommonData.IMPROVEMENT_STATUS);
        logService.save(log_script);
        LogManegement log_slide = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.SCRIPT, CommonData.IMPROVEMENT_STATUS, tutorial.getScriptStatus(),
                CommonData.contributorRole, user, tutorial);
        tutorial.setSlideStatus(CommonData.IMPROVEMENT_STATUS);
        logService.save(log_slide);
        LogManegement log_video = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.SCRIPT, CommonData.IMPROVEMENT_STATUS, tutorial.getScriptStatus(),
                CommonData.contributorRole, user, tutorial);
        tutorial.setVideoStatus(CommonData.IMPROVEMENT_STATUS);
        logService.save(log_video);
        LogManegement log_keyword = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.SCRIPT, CommonData.IMPROVEMENT_STATUS, tutorial.getScriptStatus(),
                CommonData.contributorRole, user, tutorial);
        tutorial.setKeywordStatus(CommonData.IMPROVEMENT_STATUS);
        logService.save(log_keyword);
        LogManegement log_pre_req = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                CommonData.SCRIPT, CommonData.IMPROVEMENT_STATUS, tutorial.getScriptStatus(),
                CommonData.contributorRole, user, tutorial);
        tutorial.setPreRequisticStatus(CommonData.IMPROVEMENT_STATUS);
        logService.save(log_pre_req);
        tutService.save(tutorial);
    }

    private Tutorial changeComponentStatus(String components, Tutorial tutorial, User user) {
        LogManegement log_outline = null;
        LogManegement log_script = null;
        LogManegement log_slide = null;
        LogManegement log_video = null;
        LogManegement log_keyword = null;
        LogManegement log_pre_req = null;

        if (components.contains(CommonData.OUTLINE)) {
            log_outline = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.OUTLINE,
                    CommonData.IMPROVEMENT_STATUS, tutorial.getOutlineStatus(), CommonData.superUserRole, user,
                    tutorial);
            tutorial.setOutlineStatus(CommonData.IMPROVEMENT_STATUS);
            logService.save(log_outline);

        }
        if (components.contains(CommonData.SCRIPT)) {
            log_script = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT,
                    CommonData.IMPROVEMENT_STATUS, tutorial.getScriptStatus(), CommonData.superUserRole, user,
                    tutorial);
            tutorial.setScriptStatus(CommonData.IMPROVEMENT_STATUS);
            logService.save(log_script);
        }
        if (components.contains(CommonData.SLIDE)) {
            log_slide = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE,
                    CommonData.IMPROVEMENT_STATUS, tutorial.getSlideStatus(), CommonData.superUserRole, user, tutorial);
            tutorial.setSlideStatus(CommonData.IMPROVEMENT_STATUS);
            logService.save(log_slide);
        }
        if (components.contains(CommonData.VIDEO)) {
            log_video = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO,
                    CommonData.IMPROVEMENT_STATUS, tutorial.getVideoStatus(), CommonData.superUserRole, user, tutorial);
            tutorial.setVideoStatus(CommonData.IMPROVEMENT_STATUS);
            logService.save(log_video);
        }
        if (components.contains(CommonData.KEYWORD)) {
            log_keyword = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.KEYWORD,
                    CommonData.IMPROVEMENT_STATUS, tutorial.getKeywordStatus(), CommonData.superUserRole, user,
                    tutorial);
            tutorial.setKeywordStatus(CommonData.IMPROVEMENT_STATUS);
            logService.save(log_keyword);
        }
        if (components.contains(CommonData.PRE_REQUISTIC)) {
            log_pre_req = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(),
                    CommonData.PRE_REQUISTIC, CommonData.IMPROVEMENT_STATUS, tutorial.getPreRequisticStatus(),
                    CommonData.superUserRole, user, tutorial);
            tutorial.setPreRequisticStatus(CommonData.IMPROVEMENT_STATUS);
            logService.save(log_pre_req);
        }

        tutService.save(tutorial);
//		if(log_outline!=null) {
//			logService.save(log_outline);
//		} 
//		if(log_script!=null) {
//			logService.save(log_script);
//		} 
//		if(log_slide!=null) {
//			logService.save(log_slide);
//		} 
//		if(log_video!=null) {
//			logService.save(log_video);
//		}
//		if(log_keyword!=null) {
//			logService.save(log_keyword);
//		} 
//		if(log_pre_req!=null) {
//			logService.save(log_pre_req);
//		} 

        return tutorial;
    }

    @RequestMapping("/unpublishTutorial")
    public @ResponseBody HashMap<String, String> unpublishTutorial(@RequestParam int category, @RequestParam int topic,
            @RequestParam int language, @RequestParam String components, Principal principal) {
        HashMap<String, String> res = new HashMap<String, String>();
        User user = getUser(principal);
        logger.info("Variables of unpublishTutorial usr:{} category : {} topic : {} language : {}", user.getUsername(),
                category, topic, language);
        Category cat_ = catService.findByid(category);
        Topic topic_ = topicService.findById(topic);
        Language lang = langService.getById(language);
        TopicCategoryMapping tcm = topicCatService.findAllByCategoryAndTopic(cat_, topic_);
        ContributorAssignedTutorial conAssignedTut = conService.findByTopicCatAndLanViewPart(tcm, lang);
        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorial(conAssignedTut);
        Tutorial tutorial = null;
        if (!tutorials.isEmpty()) {
            tutorial = tutorials.get(0);
        }
        if (tutorial != null) {
            tutorial = changeComponentStatus(components, tutorial, user);
            tutorial.setStatus(false);
            taskProcessingService.addUpdateDeleteTutorial(tutorial, CommonData.DELETE_DOCUMENT);
            tutService.save(tutorial);
            res.put("response", "1");
            res.put(CommonData.OUTLINE, CommonData.tutorialStatus[tutorial.getOutlineStatus()]);
            res.put(CommonData.SCRIPT, CommonData.tutorialStatus[tutorial.getScriptStatus()]);
            res.put(CommonData.SLIDE, CommonData.tutorialStatus[tutorial.getSlideStatus()]);
            res.put(CommonData.KEYWORD, CommonData.tutorialStatus[tutorial.getKeywordStatus()]);
            res.put(CommonData.VIDEO, CommonData.tutorialStatus[tutorial.getVideoStatus()]);
            res.put(CommonData.PRE_REQUISTIC, CommonData.tutorialStatus[tutorial.getPreRequisticStatus()]);
        } else {
            res.put("response", "0");
        }
        res.put("topic", tutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
        return res;

    }

    @RequestMapping("/loadLanguageByCategoryTopic")
    public @ResponseBody HashMap<Integer, String> getLanguageByContributorRole(
            @RequestParam(value = "category") int category, @RequestParam(value = "topic") int topic) {

        HashMap<Integer, String> languages = new HashMap<Integer, String>();
        Category cat = catService.findByid(category);
        Topic topic_ = topicService.findById(topic);

        TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic_);
        List<TopicCategoryMapping> tcm = new ArrayList<TopicCategoryMapping>();
        tcm.add(localTopicCat);
        List<ContributorAssignedTutorial> catAssgnTut = conService.findAllByTopicCat(tcm);
        for (ContributorAssignedTutorial c : catAssgnTut) {
            if (!tutService.findAllByContributorAssignedTutorial(c).isEmpty()) {

                languages.put(c.getLan().getLanId(), c.getLan().getLangName());
            }

        }

        return languages;
    }

    @RequestMapping("/loadPublishedTopicsByCategory")
    public @ResponseBody HashMap<Integer, String> loadPublishedTopicsByCategory(@RequestParam(value = "id") int id) {

        HashMap<Integer, String> topicName = new HashMap<>();

        Category cat = catService.findByid(id);

        List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat);
        List<ContributorAssignedTutorial> conAssignTut = conService.findAllByTopicCat(local);
        List<Tutorial> tutorials = tutService.findAllByconAssignedTutorialAndStatus(conAssignTut);
        for (Tutorial tut : tutorials) {
            topicName.put(tut.getConAssignedTutorial().getTopicCatId().getTopic().getTopicId(),
                    tut.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
        }

        return topicName;

    }

    @RequestMapping("/getComponentDetails")
    public @ResponseBody HashMap<String, String> getComponentDetails(@RequestParam int category,
            @RequestParam int topic, @RequestParam int language) {

        HashMap<String, String> compStatus = new HashMap<>();
        logger.info("Variables of getComponentDetails category : {} topic : {} language : {}", category, topic,
                language);
        Category category_ = catService.findByid(category);
        Topic topic_ = topicService.findById(topic);
        Language langugage_ = lanService.getById(language);
        TopicCategoryMapping tcm = topicCatService.findAllByCategoryAndTopic(category_, topic_);
        ContributorAssignedTutorial conAssgnTut = conService.findByTopicCatAndLanViewPart(tcm, langugage_);
        List<Tutorial> tutorial = tutService.findAllByContributorAssignedTutorial(conAssgnTut);
        if (!tutorial.isEmpty()) {
            Tutorial t = tutorial.get(0);
            compStatus.put(CommonData.OUTLINE, CommonData.tutorialStatus[t.getOutlineStatus()]);
            compStatus.put(CommonData.SCRIPT, CommonData.tutorialStatus[t.getScriptStatus()]);
            compStatus.put(CommonData.SLIDE, CommonData.tutorialStatus[t.getSlideStatus()]);
            compStatus.put(CommonData.KEYWORD, CommonData.tutorialStatus[t.getKeywordStatus()]);
            compStatus.put(CommonData.VIDEO, CommonData.tutorialStatus[t.getVideoStatus()]);
            compStatus.put(CommonData.PRE_REQUISTIC, CommonData.tutorialStatus[t.getPreRequisticStatus()]);
            compStatus.put("response", "1");
            compStatus.put("topic_details", t.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName() + " ("
                    + t.getConAssignedTutorial().getLan().getLangName() + " )");

            compStatus.put("outline-status", String.valueOf(t.getOutlineStatus()));
            compStatus.put("script-status", String.valueOf(t.getScriptStatus()));
            compStatus.put("slide-status", String.valueOf(t.getSlideStatus()));
            compStatus.put("video-status", String.valueOf(t.getVideoStatus()));
            compStatus.put("keyword-status", String.valueOf(t.getKeywordStatus()));
            compStatus.put("prerequisite-status", String.valueOf(t.getPreRequisticStatus()));
        } else {
            compStatus.put("response", "0");
        }
        return compStatus;

    }

}
