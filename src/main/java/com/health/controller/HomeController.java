package com.health.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.googleapis.media.MediaHttpUploaderProgressListener;
import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import com.google.common.collect.Lists;
import com.health.config.Auth;
import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.Brouchure;
import com.health.model.Carousel;
import com.health.model.Category;
import com.health.model.Comment;
import com.health.model.Consultant;
import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.DocumentSearch;
import com.health.model.Event;
import com.health.model.FeedbackMasterTrainer;
import com.health.model.FilesofBrouchure;
import com.health.model.IndianLanguage;
import com.health.model.Language;
import com.health.model.LiveTutorial;
import com.health.model.LogManegement;
import com.health.model.OrganizationRole;
import com.health.model.PathofPromoVideo;
import com.health.model.PostQuestionaire;
import com.health.model.PromoVideo;
import com.health.model.Question;
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
import com.health.model.UserIndianLanguageMapping;
import com.health.model.Version;
import com.health.repository.LiveTutorialRepository;
import com.health.repository.TopicCategoryMappingRepository;
import com.health.repository.VersionRepository;
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
import com.health.service.FeedBackMasterTrainerService;
import com.health.service.FilesofBrouchureService;
import com.health.service.IndianLanguageService;
import com.health.service.LanguageService;
import com.health.service.LiveTutorialService;
import com.health.service.LogMangementService;
import com.health.service.OrganizationRoleService;
import com.health.service.PathofPromoVideoService;
import com.health.service.PostQuestionaireService;
import com.health.service.PromoVideoService;
import com.health.service.QuestionService;
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
import com.health.service.UserIndianLanguageMappingService;
import com.health.service.UserRoleService;
import com.health.service.UserService;
import com.health.service.VersionService;
import com.health.threadpool.TaskProcessingService;
import com.health.utility.CommonData;
import com.health.utility.MailConstructor;
import com.health.utility.SecurityUtility;
import com.health.utility.ServiceUtility;
import com.opencsv.exceptions.CsvException;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

/**
 * This Controller Class takes website request and process it accordingly
 * 
 * @author om prakash soni
 * @version 1.0
 *
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private VersionRepository verRepository;

    @Autowired
    private CommonData commonData;

    @Autowired
    private LiveTutorialService liveTutorialService;

    @Autowired
    private LiveTutorialRepository liveTutorialRepo;

    @Autowired
    private AjaxController ajaxController;

    @Autowired
    private TopicCategoryMappingRepository tcmRepository;

    @Autowired
    private VersionService verService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MailConstructor mailConstructor;

    @Autowired
    private UserService userService;

    @Autowired
    private LanguageService lanService;

    @Autowired
    private CategoryService catService;

    @Autowired
    private ResearchPaperService researchPaperService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private TopicService topicService;

    @Autowired
    private TopicCategoryMappingService topicCatService;

    @Autowired
    private QuestionService questService;

    @Autowired
    private EventService eventservice;

    @Autowired
    private TestimonialService testService;

    @Autowired
    private FilesofBrouchureService filesofbrouchureService;

    @Autowired
    private ConsultantService consultService;

    @Autowired
    private Environment env;

    @Autowired
    private UserRoleService usrRoleService;

    @Autowired
    private ContributorAssignedTutorialService conRepo;

    @Autowired
    private ContributorAssignedMultiUserTutorialService conMultiUser;

    @Autowired
    private TutorialService tutService;

    @Autowired
    private StateService stateService;

    @Autowired
    private TrainingInformationService trainingInfoService;

    @Autowired
    private DistrictService districtService;

    @Autowired
    private CityService cityService;

    @Autowired
    private TraineeInformationService traineeService;

    @Autowired
    private TrainingTopicService trainingTopicServ;

    @Autowired
    private FeedBackMasterTrainerService feedServ;

    @Autowired
    private CommentService comService;

    @Autowired
    private PostQuestionaireService postQuestionService;

    @Autowired
    private BrouchureService broService;

    @Autowired
    private PromoVideoService promoVideoService;

    @Autowired
    private PathofPromoVideoService pathofPromoVideoService;

    @Autowired
    private IndianLanguageService iLanService;

    @Autowired
    private UserIndianLanguageMappingService userIndianMappingService;

    @Autowired
    private CarouselService caroService;

    @Autowired
    private OrganizationRoleService organizationRoleService;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private TaskProcessingService taskProcessingService;

    @Autowired
    private LogMangementService logMangementService;

    @Value("${scriptmanager_api}")
    private String scriptmanager_api;

    @Value("${scriptmanager_url}")
    private String scriptmanager_url;

    @Value("${scriptmanager_path}")
    private String scriptmanager_path;

    private static YouTube youtube;

    private static final String VIDEO_FILE_FORMAT = "video/*";

    private User getUser(Principal principal) {

        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return new User();
    }

    static void setCompStatus(Model model, List<Tutorial> tutorials) {

        if (tutorials.isEmpty()) {

            model.addAttribute("statusOutline", CommonData.ADD_CONTENT);
            model.addAttribute("statusScript", CommonData.ADD_CONTENT);
            model.addAttribute("statusSlide", CommonData.ADD_CONTENT);
            model.addAttribute("statusVideo", CommonData.ADD_CONTENT);
            model.addAttribute("statusKeyword", CommonData.ADD_CONTENT);
            model.addAttribute("statusPreReq", CommonData.ADD_CONTENT);
            model.addAttribute("statusGraphics", CommonData.ADD_CONTENT);
            model.addAttribute("tutorial", null);
        } else {
            for (Tutorial local : tutorials) {
                model.addAttribute("statusOutline", CommonData.tutorialStatus[local.getOutlineStatus()]);
                model.addAttribute("statusScript", CommonData.tutorialStatus[local.getScriptStatus()]);
                model.addAttribute("statusSlide", CommonData.tutorialStatus[local.getSlideStatus()]);
                model.addAttribute("statusVideo", CommonData.tutorialStatus[local.getVideoStatus()]);
                model.addAttribute("statusKeyword", CommonData.tutorialStatus[local.getKeywordStatus()]);
                model.addAttribute("statusPreReq", CommonData.tutorialStatus[local.getPreRequisticStatus()]);

//				model.addAttribute("tutorial", local);

            }

        }
    }

    private void setVideoInfo(Model model, List<Tutorial> tutorials) {

        if (!tutorials.isEmpty()) {
            for (Tutorial local : tutorials) {
                if (local.getVideo() != null) {

                    IContainer container = IContainer.make();
                    // int result = 10;
                    // result =
                    // container.open(env.getProperty("spring.applicationexternalPath.name") +
                    // local.getVideo(),
                    // IContainer.Type.READ, null);

                    IStream stream = container.getStream(0);
                    if (stream != null) {
                        IStreamCoder coder = stream.getStreamCoder();
                        model.addAttribute("FileWidth", coder.getWidth());
                        model.addAttribute("FileHeight", coder.getHeight());
                        model.addAttribute("fileSizeInMB", container.getFileSize() / 1000000);
                        model.addAttribute("FileDurationInSecond", container.getDuration() / 1000000);

                        container.close();
                    }
                }
            }
        }

    }

    static void setCompComment(Model model, List<Tutorial> tutorials, CommentService comService) {

        if (!tutorials.isEmpty()) {
            for (Tutorial local : tutorials) {
                List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO, local);
                List<Comment> comScript = comService.getCommentBasedOnTutorialType(CommonData.SCRIPT, local);
                List<Comment> comSlide = comService.getCommentBasedOnTutorialType(CommonData.SLIDE, local);

                List<Comment> comKeyword = comService.getCommentBasedOnTutorialType(CommonData.KEYWORD, local);
                List<Comment> comPreRequistic = comService.getCommentBasedOnTutorialType(CommonData.PRE_REQUISTIC,
                        local);
                List<Comment> comOutline = comService.getCommentBasedOnTutorialType(CommonData.OUTLINE, local);

                model.addAttribute("comOutline", comOutline);
                model.addAttribute("comScript", comScript);
                model.addAttribute("comSlide", comSlide);
                model.addAttribute("comVideo", comVideo);
                model.addAttribute("comKeyword", comKeyword);
                model.addAttribute("comPreReq", comPreRequistic);

            }
        }
    }

    static void setEngLangStatus(Model model, Language lan) {
        try {

            if (!lan.getLangName().equalsIgnoreCase("english")) {
                model.addAttribute("isEnglish", false);
            } else {
                model.addAttribute("isEnglish", true);
            }

        } catch (Exception e) {
            logger.error("Error in set English Status: {}", lan, e);
        }

    }

    private boolean checkRole(User usr, Category cat, Language lan) {

        if (usr == null) {
            return false;
        }

        Role role = null;
        List<UserRole> usrRoles = null;

        role = roleService.findByname(CommonData.contributorRole);
        usrRoles = usrRoleService.findByLanUser(lan, usr, role);
        if (usrRoles != null && usrRoles.size() > 0) {
            return true;

        }

        role = roleService.findByname(CommonData.domainReviewerRole);
        UserRole usrRole = null;
        usrRole = usrRoleService.findByLanCatUser(lan, cat, usr, role);
        if (usrRole != null) {
            return true;

        }
        role = roleService.findByname(CommonData.qualityReviewerRole);
        usrRole = null;
        usrRole = usrRoleService.findByLanCatUser(lan, cat, usr, role);
        if (usrRole != null) {
            return true;
        }

        return false;

    }

    private String setPreReqInfo(Tutorial tut) {
        String prefix = "Selected prerequisite : ";
        String pre_req = "";
        if (tut.getPreRequistic() != null) {
            Tutorial local = tut.getPreRequistic();
            String catName = local.getConAssignedTutorial().getTopicCatId().getCat().getCatName();
            String topicName = local.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName();
            pre_req = prefix + catName + " - " + topicName;
        } else {
            pre_req = prefix + "This tutorial has no prerequisite.";
        }
        return pre_req;
    }

    @Cacheable(cacheNames = "scriptapi")
    static List<Integer> getApiVersion(String scripmanager_api, int catId, int TutorialId, int lanId) {

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
                logger.info("API request failed with status code:{} ", statusCode);

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

    static String setScriptManagerUrl(Model model, String scriptmanager_url, String scriptmanager_path,
            Tutorial tutorial, Topic topic, Language lan, Category cat, int x) {
        String topic_name = topic.getTopicName();
        topic_name = topic_name.replaceAll(" ", "-");
        model.addAttribute("topic_name", topic_name);
        model.addAttribute("script_manager_view_url", scriptmanager_url + scriptmanager_path);
        model.addAttribute("sm_default_param", CommonData.SM_DEFAULT_PARAM);
        String tutorial_id = "";
        if (tutorial != null) {
            tutorial_id = Integer.toString(tutorial.getTutorialId());
        }

        StringBuilder sb = new StringBuilder();

        sb.append(scriptmanager_url);
        sb.append(scriptmanager_path);
        sb.append(cat.getCategoryId());
        sb.append("/");
        sb.append(tutorial_id);
        sb.append("/");
        sb.append(lan.getLanId());
        sb.append("/");
        sb.append(topic_name);
        sb.append("/");
        sb.append(x);
        String sm_url = sb.toString();
        return sm_url;
    }

    /*
     * static String setScriptManagerUrlforVersion2(Model model, String
     * scriptmanager_url, String scriptmanager_path, Tutorial tutorial, Topic topic,
     * Language lan, Category cat) { String topic_name = topic.getTopicName();
     * topic_name = topic_name.replaceAll(" ", "-");
     * model.addAttribute("topic_name", topic_name);
     * model.addAttribute("script_manager_view_url", scriptmanager_url +
     * scriptmanager_path); model.addAttribute("sm_default_param",
     * CommonData.SM_DEFAULT_PARAM); String tutorial_id = ""; if (tutorial != null)
     * { tutorial_id = Integer.toString(tutorial.getTutorialId()); }
     * 
     * StringBuilder sb = new StringBuilder();
     * 
     * sb.append(scriptmanager_url); sb.append(scriptmanager_path);
     * sb.append(cat.getCategoryId()); sb.append("/"); sb.append(tutorial_id);
     * sb.append("/"); sb.append(lan.getLanId()); sb.append("/");
     * sb.append(topic_name); sb.append("/"); sb.append("2"); String sm_url =
     * sb.toString(); return sm_url; }
     */
    private List<Category> getCategories() {
        List<Tutorial> tutorials = tutService.findAllByStatus(true);
        Set<Category> catTemp = new HashSet<Category>();
        for (Tutorial temp : tutorials) {

            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {
                catTemp.add(c);
            }

        }

        List<Category> catTempSorted = new ArrayList<Category>(catTemp);
        Collections.sort(catTempSorted);

        return catTempSorted;
    }

    private List<Topic> getTopics() {
        List<Tutorial> tutorials = tutService.findAllByStatus(true);
        Set<Topic> topicTemp = new HashSet<Topic>();
        for (Tutorial temp : tutorials) {

            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {
                topicTemp.add(temp.getConAssignedTutorial().getTopicCatId().getTopic());
            }
        }
        List<Topic> topicTempSorted = new ArrayList<Topic>(topicTemp);
        Collections.sort(topicTempSorted);
        return topicTempSorted;
    }

    private List<Language> getLanguages() {
        List<Tutorial> tutorials = tutService.findAllByStatus(true);

        Set<Language> langTemp = new HashSet<Language>();
        for (Tutorial temp : tutorials) {
            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {
                langTemp.add(temp.getConAssignedTutorial().getLan());
            }

        }
        List<Language> lanTempSorted = new ArrayList<Language>(langTemp);
        Collections.sort(lanTempSorted);

        return lanTempSorted;
    }

    private void getModelData(Model model) {
        getModelData(model, 0, 0, 0, "");

    }

    private void getModelData(Model model, int catId, int topicId, int lanId, String query) {

        ArrayList<Map<String, Integer>> arlist = ajaxController.getTopicAndLanguageByCategory(catId, topicId, lanId);
        ArrayList<Map<String, Integer>> arlist1 = ajaxController.getCategoryAndLanguageByTopic(catId, topicId, lanId);
        Map<String, Integer> cat = arlist1.get(0);
        Map<String, Integer> lan = arlist1.get(1);
        Map<String, Integer> topic = arlist.get(0);
        model.addAttribute("categories", cat);
        model.addAttribute("languages", lan);
        model.addAttribute("topics", topic);
        model.addAttribute("localCat", catId);
        model.addAttribute("localTopic", topicId);
        model.addAttribute("localLan", lanId);
        model.addAttribute("query", query);
        model.addAttribute("languageCount", lan.size());

    }

    @RequestMapping("/")
    public String index(Model model) {

        List<Event> events = eventservice.findAllEventForCache(); // findAllEvent();
        List<Testimonial> testi = testService.findAllTestimonialByapprovedForCache(); // findAllTestimonialByapproved();
        List<Consultant> consults = consultService.findAllConsultHomeTrueForCache(); // findAllConsultHomeTrue();
        List<Brouchure> brochures = broService.findAllBrouchuresForCache(); // findAllBrouchures();
        List<Carousel> carousel = caroService.findCarouselForCache(); // findCarousel();
        List<PromoVideo> promoVideos = promoVideoService.findAllByShowOnHomePage();
        List<ResearchPaper> researchPapers = researchPaperService.findAllByShowOnHomePage();
        List<Event> evnHome = new ArrayList<>();
        List<Testimonial> testHome = new ArrayList<>();
        List<Consultant> consulHome = new ArrayList<>();
        List<Category> categoryHome = new ArrayList<>();

        List<Version> versionHome = new ArrayList<>();
        List<Carousel> carouselHome = new ArrayList<>();
        List<PromoVideo> promoVideoHome = new ArrayList<>();
        List<ResearchPaper> researchPapersHome = new ArrayList<>();

        List<Category> catTempSorted = catService.getCategoriesForCache(); // getCategories();

        List<Version> allVersions = verService.findAll();
        List<Version> versions = new ArrayList<Version>();
        for (Brouchure bro : brochures) {
            for (Version ver : allVersions) {
                if (bro.getId() == ver.getBrouchure().getId() && bro.getPrimaryVersion() == ver.getBroVersion())
                    versions.add(ver);
            }
        }
        Collections.sort(versions, Version.SortByBroVersionTime);

        getModelData(model);

        int upperlimit = 0;

        for (Event local : events) {
            evnHome.add(local);
            if (++upperlimit > 3) {
                break;
            }
        }

        upperlimit = 0;

        for (Testimonial local : testi) {
            testHome.add(local);
            if (++upperlimit > 3) {
                break;
            }
        }

        upperlimit = 0;

        for (Consultant local : consults) {
            if (local.isOnHome()) {
                consulHome.add(local);
            }
            if (++upperlimit > 4) {
                break;
            }
        }

        upperlimit = 4;

        categoryHome = (catTempSorted.size() > upperlimit) ? catTempSorted.subList(0, upperlimit) : catTempSorted;
        versionHome = (versions.size() > upperlimit) ? versions.subList(0, upperlimit) : versions;
        carouselHome = (carousel.size() > upperlimit) ? carousel.subList(0, upperlimit) : carousel;
        promoVideoHome = (promoVideos.size() > 1) ? promoVideos.subList(0, 1) : promoVideos;
        researchPapersHome = (researchPapers.size() > upperlimit) ? researchPapers.subList(0, upperlimit)
                : researchPapers;

        if (!consulHome.isEmpty()) {
            model.addAttribute("listOfConsultant", consulHome);
        }

        if (!researchPapersHome.isEmpty()) {
            model.addAttribute("listOfResearchPapers", researchPapersHome);
        }

        if (!testHome.isEmpty()) {
            model.addAttribute("listofTestimonial", testHome);
        }

        if (!categoryHome.isEmpty()) {
            model.addAttribute("listofCategories", categoryHome);
        }

        if (!evnHome.isEmpty()) {
            Collections.sort(evnHome, Event.SortByEventAddedTimeInDesc);
            model.addAttribute("events", evnHome);
        }

        if (!promoVideoHome.isEmpty()) {
            model.addAttribute("listofPromoVideos", promoVideoHome);
            model.addAttribute("PromoVideoLanguages", promoVideoHome.get(0).findAlllanguages());
            model.addAttribute("PromoVideos", promoVideoHome.get(0).getVideoFiles());

        }

        if (!versionHome.isEmpty()) {
            Collections.sort(versionHome, Version.SortByBroVersionTime);
            model.addAttribute("listofVesrsions", versionHome);
        }

        List<Tutorial> finalTutorials = tutService.getFinalTutorialsForCache(); // getFinalTutorials();

        model.addAttribute("videoCount", finalTutorials.size());
        model.addAttribute("consultantCount", consults.size());

        if (!carouselHome.isEmpty()) {
            model.addAttribute("carousel", carouselHome.get(0));
            model.addAttribute("carouselList", carouselHome.subList(1, carouselHome.size()));

        }

        return "index";
    }

    /*
     * A controller to clear all caches Author: Alok Kumar
     */

    @GetMapping("/clearAllCaches")
    public String ClearAllCache(Principal principal, Model model) {

        for (String name : cacheManager.getCacheNames()) {
            cacheManager.getCache(name).clear(); // clear cache by name
        }

        User usr = getUser(principal);
        model.addAttribute("userInfo", usr);
        model.addAttribute("success_msg", CommonData.All_Caches_Clear_MSG);
        return "ClearAllCaches";
    }

    private Page<DocumentSearch> searchonElasticSearch(Pageable pageable, int cat, int topic, int lan, String query,
    		String typeTutorial, String typeTimeScript,  String typeBrochure, String typeResearchPaper) {

        StringBuilder documentSb = new StringBuilder();
        documentSb.append(commonData.elasticSearch_url);
        documentSb.append("/");
        documentSb.append("search");
        
        logger.info("typeTutorial :{}, typeTimeScript:{}, typeBrochure:{}, typeResearchPaper:{}, query:{}",
        		typeTutorial, typeTimeScript, typeBrochure, typeResearchPaper, query);

        String tempUrl = documentSb.toString();

        List<DocumentSearch> documentSearchTutorialList = new ArrayList<>();
        
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {

            try {

                logger.info("API_URL:{}", tempUrl);

                HttpPost request = new HttpPost(tempUrl);

                List<NameValuePair> paramsforAddDocument = new ArrayList<>();
                paramsforAddDocument.add(new BasicNameValuePair("categoryId", Integer.toString(cat)));
                paramsforAddDocument.add(new BasicNameValuePair("topicId", Integer.toString(topic)));
                paramsforAddDocument.add(new BasicNameValuePair("languageId", Integer.toString(lan)));
                paramsforAddDocument.add(new BasicNameValuePair("query", query));
                paramsforAddDocument.add(new BasicNameValuePair("typeTutorial", typeTutorial));
                paramsforAddDocument.add(new BasicNameValuePair("typeTimeScript", typeTimeScript));
                paramsforAddDocument.add(new BasicNameValuePair("typeBrochure", typeBrochure));
                paramsforAddDocument.add(new BasicNameValuePair("typeResearchPaper", typeResearchPaper));

                request.setEntity(new UrlEncodedFormEntity(paramsforAddDocument, "UTF-8"));

                HttpResponse response = httpClient.execute(request);

                int statusCode = response.getStatusLine().getStatusCode();

                if (statusCode == 200 || statusCode == 201) {
                    String jsonResponse = EntityUtils.toString(response.getEntity());
                    JSONObject mainJsonObject = new JSONObject(jsonResponse);
                    JSONArray jsonArray = (JSONArray) mainJsonObject.get("documentSearchList");

                    logger.info("jsonArray:{}", jsonArray);

                    if (jsonArray != null) {

                        for (int i = 0; i < jsonArray.length(); i++) {
                            DocumentSearch docSearch = new DocumentSearch();
                            JSONObject jsonObjet = (JSONObject) jsonArray.get(i);

                            docSearch.setId((String) jsonObjet.get("id"));
                            
                            docSearch.setDocumentType((String) jsonObjet.get("documentType"));
                            docSearch.setDocumentId((String) jsonObjet.get("documentId"));
                            docSearch.setLanguage((String) jsonObjet.get("language"));
                            docSearch.setLanguageId((int) jsonObjet.get("languageId"));
                            docSearch.setCategory((String) jsonObjet.get("category"));
                            docSearch.setCategoryId((int) jsonObjet.get("categoryId"));
                            docSearch.setTopic((String) jsonObjet.get("topic"));
                            docSearch.setTopicId((int) jsonObjet.get("topicId"));
                            docSearch.setRank((int) jsonObjet.get("rank"));
                            docSearch.setVideoPath((String) jsonObjet.get("videoPath"));
                            docSearch.setViewUrl((String) jsonObjet.get("viewUrl"));
                            docSearch.setCreationTime((Long) jsonObjet.get("creationTime"));
                            
                           
                            docSearch.setDocumentUrl((String) jsonObjet.get("documentUrl"));

                            if (((String) jsonObjet.get("documentType"))
                                    .equals(CommonData.DOCUMENT_TYPE_TUTORIAL_ORIGINAL_SCRIPT) || ((String) jsonObjet.get("documentType"))
                                    .equals(CommonData.DOCUMENT_TYPE_TUTORIAL_TIME_SCRIPT)) {
                            	docSearch.setOutlineContent((String) jsonObjet.get("outlineContent")); 
                            }
                            
                            if (((String) jsonObjet.get("documentType"))
                                    .equals(CommonData.DOCUMENT_TYPE_BROCHURE) || ((String) jsonObjet.get("documentType"))
                                    .equals(CommonData.DOCUMENT_TYPE_RESEARCHPAPER)) {
                            	docSearch.setTitle((String) jsonObjet.get("title")); 
                            }
                            
                            if(((String) jsonObjet.get("documentType"))
                                    .equals(CommonData.DOCUMENT_TYPE_RESEARCHPAPER)) {
                            	 docSearch.setDescription((String) jsonObjet.get("description"));
                            }
                            
                            documentSearchTutorialList.add(docSearch);
                        }

                    } else {
                        logger.error("documentIds is not an array or is null");
                    }

                } else {
                    logger.info("Status Code:{} API URl:{}", statusCode, tempUrl);

                }

            } catch (Exception e) {
                logger.error("Exception of serach :{}", query, e);

            }

        } catch (IOException e1) {

            logger.error("Exception: ", e1);
        }

        List<DocumentSearch> newDocumentSearchTutorialList = documentSearchTutorialList.stream().distinct()
                .collect(Collectors.toList());
        /*
         * List<DocumentSearch> newDocumentSearchList = documentSearchList.stream()
         * .collect(Collectors.toMap(DocumentSearch::getVideoPath, Function.identity(),
         * (existing, replacement) -> existing, LinkedHashMap::new)) // Use
         * LinkedHashMap to preserve // insertion order
         * .values().stream().collect(Collectors.toList());
         * 
         */

//        if (cat == 0 && topic == 0 && lan == 0 && query.isEmpty()) {
//            newDocumentSearchList = newDocumentSearchList.stream().sorted(Comparator.comparing(Tutorial::getTutorialId))
//                    .collect(Collectors.toList());
//        }
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), newDocumentSearchTutorialList.size());

        List<DocumentSearch> sublist = newDocumentSearchTutorialList.subList(start, end);

        return new PageImpl<>(sublist, pageable, newDocumentSearchTutorialList.size());

    }

    @GetMapping("/tutorials")
    public String viewCoursesAvailable(HttpServletRequest req,
            @RequestParam(name = "categoryName", required = false, defaultValue = "0") int cat,
            @RequestParam(name = "topic", required = false, defaultValue = "0") int topic,
            @RequestParam(name = "lan", required = false, defaultValue = "0") int lan,
            @RequestParam(name = "query", required = false, defaultValue = "") String query,
            @RequestParam(name = "typeTutorial", required = false, defaultValue = "") String typeTutorial,
            @RequestParam(name = "typeTimeScript", required = false, defaultValue = "") String typeTimeScript,
            @RequestParam(name = "typeBrochure", required = false, defaultValue = "") String typeBrochure,
            @RequestParam(name = "typeResearchPaper", required = false, defaultValue = "") String typeResearchPaper,
            @RequestParam(name = "page", defaultValue = "0") int page, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("category", cat);
        model.addAttribute("language", lan);
        model.addAttribute("topic", topic);
        
        if(typeTutorial!=null && !typeTutorial.isEmpty() && typeTutorial.equals("typeTutorial")) {
        	typeTutorial=CommonData.DOCUMENT_TYPE_TUTORIAL_ORIGINAL_SCRIPT;
        }else {
			typeTutorial="";
		}
        
        if(typeTimeScript!=null && !typeTimeScript.isEmpty() && typeTimeScript.equals("typeTimeScript")) {
        	typeTimeScript=CommonData.DOCUMENT_TYPE_TUTORIAL_TIME_SCRIPT;
        }else {
        	typeTimeScript="";
		}
        
        if(typeBrochure!=null && !typeBrochure.isEmpty() && typeBrochure.equals("typeBrochure")) {
        	typeBrochure=CommonData.DOCUMENT_TYPE_BROCHURE;
        }else {
        	typeBrochure="";
		}
        
        if(typeResearchPaper!=null && !typeResearchPaper.isEmpty() && typeResearchPaper.equals("typeResearchPaper")) {
        	typeResearchPaper=CommonData.DOCUMENT_TYPE_RESEARCHPAPER;
        }else {
        	typeResearchPaper="";
		}
        
        

        if (query.equals("q")) {
            query = "";
        }
       
        Category localCat = null;
        Language localLan = null;
        Topic localTopic = null;

        Page<DocumentSearch> docSearchPage = null;

        List<DocumentSearch> docSearchToView1 = new ArrayList<DocumentSearch>();

        model.addAttribute("userInfo", usr);

        Pageable pageable = PageRequest.of(page, 10);
        getModelData(model, cat, topic, lan, query);

        if (cat != 0) {
            localCat = catService.findByid(cat);
            model.addAttribute("catforQuery", localCat);
        }
        if (topic != 0) {
            localTopic = topicService.findById(topic);
            model.addAttribute("topicforQuery", localTopic);
        }
        if (lan != 0) {
            localLan = lanService.getById(lan);
            model.addAttribute("lanforQuery", localLan);
        }


       
        docSearchPage = searchonElasticSearch( pageable,  cat,  topic,  lan,  query,
        		typeTutorial,  typeTimeScript,   typeBrochure,  typeResearchPaper);

        for (DocumentSearch temp : docSearchPage) {
            {
                docSearchToView1.add(temp);
            }
        }

        logger.info("DocumentSearch size:{}", docSearchToView1.size());

//        if (localCat == null) {
//            Collections.sort(tutToView1, Tutorial.UserVisitComp);
//        } else {
//            Collections.sort(tutToView1, Tutorial.SortByOrderValue);
//        }

        // sorting based on order value

        int totalPages = 0;

        if (docSearchPage != null) {
            totalPages = docSearchPage.getTotalPages();
        } else {
            totalPages = 1;
        }

        int firstPage = page + 1 > 2 ? page + 1 - 2 : 1;
        int lastPage = page + 1 < totalPages - 5 ? page + 1 + 5 : totalPages;

        model.addAttribute("docSearchList", docSearchToView1);
        model.addAttribute("currentPage", page);
        model.addAttribute("firstPage", firstPage);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("totalPages", totalPages);

        return "tutorialList";
    }

    @GetMapping("/addLiveTutorial")
    public String addLiveTutorial(HttpServletRequest req, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        Path csvFilePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadLiveTutorial, "Sample_CSV_file" + ".csv");
        String temp = csvFilePath.toString();
        int indexToStart = temp.indexOf("Media");
        String document = temp.substring(indexToStart, temp.length());
        model.addAttribute("sample_csv_file", document);
        List<LiveTutorial> liveTutorials = liveTutorialService.findAll();
        Collections.sort(liveTutorials, LiveTutorial.SortByUploadTime);
        model.addAttribute("liveTutorials", liveTutorials);
        return "addLiveTutorial";
    }

    @PostMapping("/addLiveTutorial")
    public String addLiveTuotorialPost(@RequestParam(value = "add_csv_file") MultipartFile csv_file,

            HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        Path csvFilePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                CommonData.uploadLiveTutorial, "Sample_CSV_file" + ".csv");
        String temp = csvFilePath.toString();
        int indexToStart = temp.indexOf("Media");
        String document = temp.substring(indexToStart, temp.length());
        model.addAttribute("sample_csv_file", document);

        try {
            liveTutorialService.saveLiveTutorialsFromCSV(csv_file, model);

        } catch (IOException e) {

            model.addAttribute("error_msg", "Some Errors Occured Please contact Admin or try again");
            logger.error("Exception: ", e);
            return "addLiveTutorial";
        } catch (CsvException e) {
            model.addAttribute("error_msg", "Some Errors Occured Please contact Admin or try again");
            logger.error("Exception: ", e);
            return "addLiveTutorial";
        }

        List<LiveTutorial> liveTutorials = liveTutorialService.findAll();
        Collections.sort(liveTutorials, LiveTutorial.SortByUploadTime);
        model.addAttribute("liveTutorials", liveTutorials);

        return "addLiveTutorial";
    }

    @GetMapping("/downloads")
    public String downloadResources(HttpServletRequest req, Principal principal, Model model) {

        List<Category> categories = getCategories();
        model.addAttribute("categories", categories);
        boolean downloadSection = false;
        model.addAttribute("downloadSection", downloadSection);

        try {
            ServiceUtility.deleteFilesOlderThanNDays(1, env, CommonData.uploadDirectoryScriptZipFiles);

            Path zipDirectoryPath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                    CommonData.uploadDirectoryScriptZipFiles);

            Long sizeofDirectory = FileUtils.sizeOfDirectory(zipDirectoryPath.toFile());
            logger.info("Size of Directory:{}", sizeofDirectory);

            if (sizeofDirectory >= CommonData.Zip_DIR_MAX_SIZE_GB) {
                ServiceUtility.deleteFilesOlderThanNDays(0, env, CommonData.uploadDirectoryScriptZipFiles);

            }

        } catch (IOException e) {

            logger.error("Exception: ", e);
        }

        return "downloadResources";
    }

    @PostMapping("/downloads")
    public String downloadResourcesPost(HttpServletRequest req, Principal principal, Model model,
            @RequestParam(name = "resourceCategoryName") String catId,
            @RequestParam(name = "languageResourceName") Optional<String[]> langIds,
            @RequestParam(name = "topicResourceName") Optional<String[]> topicIds) {

        List<Category> categories = getCategories();
        model.addAttribute("categories", categories);
        boolean downloadSection = false;
        model.addAttribute("downloadSection", downloadSection);

        Category cat = catService.findByid(Integer.parseInt(catId));
        if (cat == null) { // throw error
            downloadSection = false;
            model.addAttribute("downloadSection", downloadSection);
            model.addAttribute("error_msg", "Please Select Category");
            return "downloadResources";
        }

        List<ContributorAssignedTutorial> conList = new ArrayList<>();
        List<Language> languages = new ArrayList<>();
        List<Topic> topics = new ArrayList<>();
        List<Tutorial> tutList = new ArrayList<>();
        List<TopicCategoryMapping> tcmList = new ArrayList<>();

        if ((topicIds != null && topicIds.isPresent() && topicIds.get().length != 0)) {
            for (String topicId : topicIds.get()) {
                Topic topic = topicService.findById(Integer.parseInt(topicId));
                logger.info("Topic Name: {}", topic.getTopicName());
                topics.add(topic);
                tcmList.add(topicCatService.findAllByCategoryAndTopic(cat, topic));
            }
        }

        if ((langIds != null && langIds.isPresent() && langIds.get().length != 0)) {
            for (String lanId : langIds.get()) {
                logger.info("Language Name: {}", lanService.getById(Integer.parseInt(lanId)).getLangName());
                languages.add(lanService.getById(Integer.parseInt(lanId)));
            }
        }

        if ((langIds != null && langIds.isPresent() && langIds.get().length != 0)
                && (topicIds != null && topicIds.isPresent() && topicIds.get().length != 0)) {

            for (Language lan : languages) {

                List<ContributorAssignedTutorial> conList1 = conRepo.findAllByTopicCatAndLan(tcmList, lan);
                if (conList1 != null) {
                    conList.addAll(conList1);

                }
            }

        } else if ((langIds != null && langIds.isPresent() && langIds.get().length != 0)) {
            List<TopicCategoryMapping> tcm = topicCatService.findAllByCategory(cat);
            for (Language lan : languages) {

                List<ContributorAssignedTutorial> conList1 = conRepo.findAllByTopicCatAndLan(tcm, lan);
                if (conList1 != null) {
                    conList.addAll(conList1);

                }
            }

        } else if ((topicIds != null && topicIds.isPresent() && topicIds.get().length != 0)) {
            conList = conRepo.findAllByTopicCat(tcmList);

        } else {
            List<TopicCategoryMapping> tcm = topicCatService.findAllByCategory(cat);
            conList = conRepo.findAllByTopicCat(tcm);
        }

        tutList = tutService.findAllByconAssignedTutorialAndStatus(conList);

        Set<Tutorial> tutorialSet = new LinkedHashSet<>(tutList);

        int countTutorial = tutorialSet.size();
        if (countTutorial == 0) {
            model.addAttribute("error_msg", "No Tutorials are available for selected category, topic and language");
            downloadSection = false;
            model.addAttribute("downloadSection", downloadSection);
            return "downloadResources";
        }

        else {
            String document = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
            String sdfString = "scripts-" + sdf.format(new java.util.Date());
            String newCatNamewith_ = cat.getCatName().replace(' ', '_');
            Path destInationDirectory1 = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                    CommonData.uploadDirectoryScriptZipFiles, sdfString, File.separator, newCatNamewith_);

            for (Tutorial tut : tutorialSet) {
                int tutorialId = tut.getTutorialId();
                ContributorAssignedTutorial con = tut.getConAssignedTutorial();
                String langName = con.getLan().getLangName();
                String topicName = con.getTopicCatId().getTopic().getTopicName();

                topicName = topicName.replace(' ', '_');

                Path destInationDirectoryforTopiccAndLan = Paths.get(destInationDirectory1.toString(), File.separator,
                        langName, File.separator);
                try {
                    ServiceUtility.createFolder(destInationDirectoryforTopiccAndLan);
                } catch (IOException e) {

                    logger.error("Exception: ", e);
                }

                try {

                    Path filePath = Paths.get(env.getProperty("spring.applicationexternalPath.name"),
                            CommonData.uploadDirectoryScriptOdtFileforDownload, tutorialId + ".odt");
                    Path destainationPath = destInationDirectoryforTopiccAndLan.resolve(topicName + ".odt");

                    File sourceFile = filePath.toFile();
                    if (sourceFile.exists()) {

                        FileUtils.copyFile(sourceFile, destainationPath.toFile());

                    }

                } catch (IOException e) {

                    logger.error("Exception: ", e);
                }

            }
            String temp = destInationDirectory1.toString();
            int indexToStart = temp.indexOf("Media");
            document = temp.substring(indexToStart, temp.length());
            try {
                String zipUrl = ServiceUtility.zipFileWithSubDirectories(document, env);
                logger.info(destInationDirectory1.toString());
                FileUtils.deleteDirectory(destInationDirectory1.toFile());

                if (zipUrl.isEmpty()) {
                    downloadSection = false;
                    model.addAttribute("downloadSection", downloadSection);
                    model.addAttribute("error_msg", "Some Errors Occured Please contact Admin or try again");
                    return "downloadResources";

                } else {

                    List<String> fileInfoList = ServiceUtility.FileInfoSizeAndCreationDate(zipUrl, env);

                    if (fileInfoList != null) {
                        Long fileSize = Long.parseLong(fileInfoList.get(0));
                        if (fileSize > CommonData.SCRIPT_MAX_SIZE_MB) {
                            downloadSection = false;
                            model.addAttribute("downloadSection", downloadSection);
                            model.addAttribute("error_msg",
                                    "The file size is larger than expected please try to select few tutorials");
                            return "downloadResources";

                        } else {
                            model.addAttribute("zipUrl", zipUrl);
                        }

                    } else {
                        downloadSection = false;
                        model.addAttribute("downloadSection", downloadSection);
                        model.addAttribute("error_msg", "Some errors occured please try again");
                        return "downloadResources";
                    }

                }

            } catch (IOException e) {
                logger.error("Exception: ", e);
                downloadSection = false;
                model.addAttribute("downloadSection", downloadSection);
                model.addAttribute("error_msg", "Some Errors Occured; please contact Admin or try again");
                return "downloadResources";

            }

        }

        model.addAttribute("success_msg",
                "Record Submitted Successfully ! Click on download link to download resources");
        downloadSection = true;
        model.addAttribute("downloadSection", downloadSection);

        return "downloadResources";
    }

    @GetMapping("/downloadResources/{zipFileUrl}")
    public String downloadScriptFile(HttpServletRequest req, @PathVariable String zipFileUrl, Principal principal,
            Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        String url = zipFileUrl;

        return "redirect:/files/" + url;

    }

    @GetMapping("/tutorialView/{catName}/{topicName}/{language}/{query}/")
    public String viewTutorial(HttpServletRequest req, @PathVariable(name = "catName") String cat,
            @PathVariable(name = "topicName") String topic, @PathVariable(name = "language") String lan,
            @PathVariable(name = "query") String query, Principal principal, Model model) {

        Category catName = catService.findBycategoryname(cat);

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        if (query.equals("q")) {
            query = "";
        }
        if (catName == null) {
            return "redirect:/";
        }

        if (!catName.isStatus()) {
            catName = null;
        }

        Topic topicName = topicService.findBytopicName(topic);
        Language lanName = lanService.getByLanName(lan);
        TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
        ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);
        int catId = catName.getCategoryId();
        int topicId = topicName.getTopicId();
        int lanId = lanName.getLanId();

        if (catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {
            return "redirect:/";
        }

        List<Tutorial> tempTutorials = tutService.findAllByContributorAssignedTutorialEnabled(conTut);
        if (tempTutorials == null || tempTutorials.size() == 0) {
            return "redirect:/";

        }

        Tutorial tutorial = tempTutorials.get(0);

        List<Tutorial> relatedTutorial = new ArrayList<>();

        if (tutorial == null || tutorial.isStatus() == false) {

            return "redirect:/";
        }

        tutorial.setUserVisit(tutorial.getUserVisit() + 1);
        tutService.save(tutorial);

        model.addAttribute("tutorial", tutorial);

        if (!tutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {
            model.addAttribute("relatedContent", tutorial.getRelatedVideo());
        }

        Category category = catService
                .findByid(tutorial.getConAssignedTutorial().getTopicCatId().getCat().getCategoryId());
        List<TopicCategoryMapping> topicCatMapping = topicCatService.findAllByCategory(category);
        List<ContributorAssignedTutorial> contriAssignedTut = conRepo.findAllByTopicCat(topicCatMapping);
        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList1(contriAssignedTut);

        for (Tutorial x : tutorials) {
            if (x == tutorial) {
                continue;
            }

            if (x.getConAssignedTutorial().getLan().getLangName()
                    .equalsIgnoreCase(tutorial.getConAssignedTutorial().getLan().getLangName())) {
                relatedTutorial.add(x);
            }

        }

        Collections.sort(relatedTutorial, Tutorial.UserVisitComp);

        model.addAttribute("tutorials", relatedTutorial);

        Set<String> catTemp = new HashSet<String>();
        Set<String> topicTemp = new HashSet<String>();
        Set<String> lanTemp = new HashSet<String>();

        List<Tutorial> tutorialse = tutService.findAllByStatus(true);
        for (Tutorial temp : tutorialse) {
            ContributorAssignedTutorial conAssignedTutorial = temp.getConAssignedTutorial();
            catTemp.add(conAssignedTutorial.getTopicCatId().getCat().getCatName());
            lanTemp.add(conAssignedTutorial.getLan().getLangName());
            topicTemp.add(conAssignedTutorial.getTopicCatId().getTopic().getTopicName());
        }

        getModelData(model, catId, topicId, lanId, query);
        List<Integer> scriptVerList = getApiVersion(scriptmanager_api, category.getCategoryId(),
                tutorial.getTutorialId(), lanName.getLanId());
        String sm_url = "";
        String sm_url2 = "";
        StringBuilder sb = new StringBuilder();

        sb.append(scriptmanager_url);
        sb.append(scriptmanager_path);
        sb.append(String.valueOf(category.getCategoryId()));
        sb.append("/");
        sb.append(String.valueOf(tutorial.getTutorialId()));
        sb.append("/");
        sb.append(String.valueOf(lanName.getLanId()));
        sb.append("/");
        sb.append(String.valueOf(tutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()));
        sb.append("/");
        if (scriptVerList != null && scriptVerList.size() > 0) {

            StringBuilder sb2 = new StringBuilder(sb);
            sb2.append(scriptVerList.get(0));
            sm_url = sb2.toString();
        }

        if (principal != null) {

            boolean flag = checkRole(usr, category, lanName);
            if (flag) {
                StringBuilder sb1 = new StringBuilder(sb);
                sb1.append('2');
                sm_url2 = sb1.toString();

            }
        }

        model.addAttribute("sm_url", sm_url);
        model.addAttribute("sm_url2", sm_url2);
        return "tutorial";
    }

    @RequestMapping("/login") // in use
    public String loginGet(Model model) {
        model.addAttribute("classActiveLogin", true);
        return "signup";
    }

    @GetMapping("/showEvent")
    public String showEventGet(HttpServletRequest req, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<Event> events = eventservice.findAll();

        model.addAttribute("Events", events);
        return "events";
    }

    @GetMapping("/showConsultant")
    public String showConsultantGet(HttpServletRequest req, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<Consultant> consults = consultService.findAll();
        model.addAttribute("listConsultant", consults);

        HashMap<Integer, String> map = new HashMap<>();

        for (Consultant c : consults) {
            String s = "";
            if (c.isOnHome()) {
                Set<UserRole> userRoles = c.getUser().getUserRoles();
                for (UserRole ur : userRoles) {
                    if (ur.getRole().getName().equals(CommonData.domainReviewerRole)) {
                        s = s + ur.getCategory().getCatName() + " , ";

                    }

                }
                if (s.length() == 0) {
                    continue;
                }

                map.put(c.getConsultantId(), s.substring(0, s.length() - 2));
            }

        }

        model.addAttribute("map", map);
        return "Consultants";
    }

    @GetMapping("/showLanguages")
    public String showLanguagesGet(HttpServletRequest req, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        HashMap<String, Integer> map = new HashMap<>();
        List<Language> langs = getLanguages();
        for (Language lang : langs) {
            List<ContributorAssignedTutorial> con = conRepo.findAllByLan(lang);
            List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList1(con);
            List<Tutorial> finalTutorials = new ArrayList<>();

            for (Tutorial temp : tutorials) {

                Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
                if (c.isStatus()) {
                    finalTutorials.add(temp);
                }
            }

            map.put(lang.getLangName(), finalTutorials.size());
        }
        model.addAttribute("map", map);
        return "languages";
    }

    @GetMapping("/showTestimonial")
    public String showTestimonialGet(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<Testimonial> testi = testService.findByApproved(true);
        model.addAttribute("Testimonials", testi);
        return "signup";
    }

    @PostMapping("/forgetPassword")
    public String forgetPasswordPost(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {

        model.addAttribute("classActiveForgetPassword", true);

        User usr = userService.findByEmail(email);
        if (usr != null) {

            logger.info("{} {} {}", usr.getUsername(), request.getMethod(), request.getRequestURI());
        }

        if (usr == null) {
            model.addAttribute("emailNotExist", true);
            return "signup";
        }

        try {
            String token = UUID.randomUUID().toString();
            usr.setToken(token);
            userService.save(usr);

            String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath();
            SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token,
                    usr);

            mailSender.send(newEmail);

            model.addAttribute("forgetPasswordEmailSent", true);
        } catch (MailException e) {
            // TODO Auto-generated catch block
            logger.error("Error in forgot Password: {}", usr, e);
            model.addAttribute("error", true);
        }

        return "signup";
    }

    /**
     * Redirects to Forget Password Page
     * 
     * @param model Model object
     * @return String object (Webpapge)
     */
    @RequestMapping("/forgetPassword") // in use
    public String forgetPasswordGet(Model model) {

        model.addAttribute("classActiveForgetPassword", true);
        return "signup";
    }

    /**
     * Url to reset password of the user
     * 
     * @param mv        ModelAndView Object
     * @param token     String object
     * @param principal Princiapl Object
     * @return String object (Webpapge)
     */
    @GetMapping("/reset")
    public ModelAndView resetPasswordGet(ModelAndView mv, @RequestParam("token") String token, HttpServletRequest req,
            Principal principal) {

        if (principal != null) {
            User localUser = userService.findByUsername(principal.getName());
            logger.info("{} {} {}", localUser.getUsername(), req.getMethod(), req.getRequestURI());
            mv.addObject("LoggedUser", localUser);

            mv.setViewName("accessDeniedPage");
            return mv;
        }

        User usr = userService.findBytoken(token);
        if (usr == null) {
            mv.setViewName("redirect:/");
            return mv;
        } else {
            logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        }

        mv.addObject("resetToken", usr.getToken());
        mv.setViewName("resetPassword");
        return mv;

    }

    @GetMapping("/Brochure/{filePathId}")
    public String getBrochure(HttpServletRequest req, @PathVariable int filePathId, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        FilesofBrouchure fileBro = filesofbrouchureService.findById(filePathId);

        Brouchure bro = fileBro.getVersion().getBrouchure();

        try {
            bro.setBrochureVisit(bro.getBrochureVisit() + 1);
            broService.save(bro);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in  count Resource Visit of TimeScript: {}", bro, e);

        }
        String res = fileBro.getWebPath();
        return "redirect:/files/" + res;

    }

    @GetMapping("/Brochure-English/{verId}")
    public String getBrochureEnglish(HttpServletRequest req, @PathVariable int verId, Principal principal,
            Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        Version ver = verService.findById(verId);

        Brouchure bro = ver.getBrouchure();

        try {
            bro.setBrochureVisit(bro.getBrochureVisit() + 1);
            broService.save(bro);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in  count Resource Visit of TimeScript: {}", bro, e);

        }
        String res = ver.findWebFileofEnglish();
        return "redirect:/files/" + res;

    }

    @GetMapping("/ResearchPaper/{id}")
    public String getResearchPaper(HttpServletRequest req, @PathVariable int id, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        ResearchPaper researchPaper = researchPaperService.findById(id);

        try {
            researchPaper.setResearchPaperVisit(researchPaper.getResearchPaperVisit() + 1);
            researchPaperService.save(researchPaper);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in  count Resource Visit of TimeScript: {}", researchPaper, e);

        }
        String res = researchPaper.getResearchPaperPath();
        return "redirect:/files/" + res;

    }

    @GetMapping("/TimeScript/{id}")
    public String getTimeScript(HttpServletRequest req, @PathVariable int id, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        Tutorial tut = tutService.getById(id);

        try {
            tut.setResourceVisit(tut.getResourceVisit() + 1);
            tutService.save(tut);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in  count Resource Visit of TimeScript: {}", tut, e);

        }
        String res = tut.getTimeScript();
        return "redirect:/files/" + res;

    }

    @GetMapping("/OriginalScript/{id}")
    public ModelAndView getScript(HttpServletRequest req, @PathVariable int id, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        Tutorial tut = tutService.getById(id);
        String sm_url = "";

        try {

            List<Integer> scriptVerList = getApiVersion(scriptmanager_api,
                    tut.getConAssignedTutorial().getTopicCatId().getCat().getCategoryId(), tut.getTutorialId(),
                    tut.getConAssignedTutorial().getLan().getLanId());

            StringBuilder sb = new StringBuilder();

            sb.append(scriptmanager_url);
            sb.append(scriptmanager_path);
            sb.append(String.valueOf(tut.getConAssignedTutorial().getTopicCatId().getCat().getCategoryId()));
            sb.append("/");
            sb.append(String.valueOf(tut.getTutorialId()));
            sb.append("/");
            sb.append(String.valueOf(tut.getConAssignedTutorial().getLan().getLanId()));
            sb.append("/");
            sb.append(String.valueOf(tut.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName()));
            sb.append("/");
            if (scriptVerList != null && scriptVerList.size() > 0) {

                StringBuilder sb2 = new StringBuilder(sb);
                sb2.append(scriptVerList.get(0));
                sm_url = sb2.toString();
            }
            tut.setResourceVisit(tut.getResourceVisit() + 1);
            tutService.save(tut);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in  count Resource Visit of Slide: {}", tut, e);

        }
        return new ModelAndView("redirect:" + sm_url);

    }

    @GetMapping("/Slide/{id}")
    public String getSlide(HttpServletRequest req, @PathVariable int id, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        Tutorial tut = tutService.getById(id);

        try {
            tut.setResourceVisit(tut.getResourceVisit() + 1);
            tutService.save(tut);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in  count Resource Visit of Slide: {}", tut, e);

        }
        String res = "";

        if (!tut.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {
            res = tut.getRelatedVideo().getSlide();
        } else {
            res = tut.getSlide();
        }

        return "redirect:/files/" + res;

    }

    /**
     * redirects to forget password page
     * 
     * @param mv        ModelAndView object
     * @param req       HttpServletRequest object
     * @param principal HttpServletRequest object
     * @return String object (webpage)
     */
    @PostMapping("/resetPassword")
    public ModelAndView resetPasswordPost(ModelAndView mv, HttpServletRequest req, Principal principal) {

        String newPassword = req.getParameter("Password");
        String confNewPassword = req.getParameter("Confirm");
        String token = req.getParameter("token");

        if (principal != null) {
            User localUser = userService.findByUsername(principal.getName());
            logger.info("{} {} {}", localUser.getUsername(), req.getMethod(), req.getRequestURI());
            mv.addObject("LoggedUser", localUser);

            mv.setViewName("redirect:/");
            return mv;
        }

        User usr = userService.findBytoken(token);
        if (usr == null) {
            mv.addObject("Error", "Invalid Request");
            return mv;
        } else {
            logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        }

        if (!newPassword.contentEquals(confNewPassword)) {
            mv.addObject("Error", "Both password doesn't match");
            return mv;
        }

        if (newPassword.length() < 6) {
            mv.addObject("Error", "Password must be atleast 6 character");
            return mv;
        }

        usr.setPassword(SecurityUtility.passwordEncoder().encode(newPassword));
        usr.setToken(null);
        userService.save(usr);

        mv.addObject("Success", "Password updated Successfully");
        mv.setViewName("resetPassword");
        return mv;

    }

    /**
     * redirects to category page
     * 
     * @param model Model object
     * @return String object(webpage)
     */
    @GetMapping("/categories")
    public String showCategoriesGet(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        List<Category> categories = getCategories();
        model.addAttribute("categories", categories);
        return "categories";
    }

    @GetMapping("/researchPapers")
    public String showResearchPapersGet(HttpServletRequest req, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<ResearchPaper> researchPapers = researchPaperService.findAllByShowOnHomePage();

        model.addAttribute("researchPapers", researchPapers);
        return "researchPapers";
    }

    /****************************
     * USER REGISTRATION
     *************************************************/

    /**
     * Url to add user into system
     * 
     * @param request   HttpServletRequest object
     * @param username  String object
     * @param firstName String object
     * @param lastName  String object
     * @param userEmail String object
     * @param password  String object
     * @param address   String object
     * @param phone     String object
     * @param gender    String object
     * @param model     Model Object
     * @return String object(Webpage)
     * @throws Exception
     */
    @PostMapping("/newUser") // in use
    public String newUserPost(HttpServletRequest request, @ModelAttribute("username") String username,
            @ModelAttribute("firstName") String firstName, @ModelAttribute("lastName") String lastName,
            @ModelAttribute("email") String userEmail, @ModelAttribute("password") String password,
            @ModelAttribute("address") String address, @ModelAttribute("phone") String phone,
            @ModelAttribute("gender") String gender, Model model) throws Exception {

        long phoneLongValue;
        model.addAttribute("classActiveNewAccount", true);
        model.addAttribute("email", userEmail);
        model.addAttribute("username", username);

        if (userService.findByUsername(username) != null) {
            model.addAttribute("usernameExists", true);
            return "signup";
        }

        if (userService.findByEmail(userEmail) != null) {
            model.addAttribute("emailExists", true);
            return "signup";
        }

        if (!ServiceUtility.checkEmailValidity(userEmail)) { // need to accommodate

            model.addAttribute("emailWrong", true);
            return "signup";
        }

        if (phone.length() > 10) { // need to accommodate

            model.addAttribute("phoneWrong", true);
            return "signup";

        } else {
            phoneLongValue = Long.parseLong(phone);

        }
        User user = new User();
        user.setId(userService.getNewId());
        user.setUsername(username);
        user.setEmail(userEmail);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAddress(address);
        user.setGender(gender);
        user.setPhone(phoneLongValue);
        user.setPassword(SecurityUtility.passwordEncoder().encode(password));
        user.setDateAdded(ServiceUtility.getCurrentTime());
        user.setEmailVerificationCode("");

        userService.save(user);
        model.addAttribute("emailSent", "true");

        return "signup";

    }

    @RequestMapping("/newUser") // in use
    public String newUserGet(Model model) {

        model.addAttribute("classActiveNewAccount", true);
        return "signup";

    }

    @GetMapping("/dashBoard")
    public String dashBoardGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = new User();
        if (principal != null) {

            usr = userService.findByUsername(principal.getName());
            usr.setLoggedInTime(ServiceUtility.getCurrentTime());
            userService.save(usr);
        }
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<UserRole> userRoles = usrRoleService.findAllByUser(usr);
        List<UserRole> pendingUserRoles = usrRoleService.findAllByUser(usr);
        List<Integer> roleIds = new ArrayList<Integer>();

        for (int i = 0; i < userRoles.size(); i++) {
            if (!userRoles.get(i).getStatus()) {
                pendingUserRoles.add(userRoles.get(i));
            }
        }
        model.addAttribute("roleIds", roleIds);
        model.addAttribute("userRoles", pendingUserRoles);
        return "roleAdminDetail";
    }

    @GetMapping("/addCategory")
    public String addCategoryGet(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        List<Category> categories = catService.findAll();
        model.addAttribute("categories", categories);

        return "addCategory";

    }

    @PostMapping("/addCategory")
    public String addCategoryPost(Model model, Principal principal, HttpServletRequest req,
            @RequestParam("categoryImage") MultipartFile files) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        List<Category> categoriesTemp = catService.findAll();
        model.addAttribute("categories", categoriesTemp);

        String categoryName = req.getParameter("categoryname");
        String categoryDesc = req.getParameter("categoryDesc");

        if (categoryName == null) {
            model.addAttribute("error_msg", "Please Try Again");
            return "addCategory";
        }

        if (categoryDesc == null) {
            model.addAttribute("error_msg", "Please Try Again");
            return "addCategory";
        }

        if (catService.findBycategoryname(categoryName) != null) {
            model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
            return "addCategory";
        }

        if (!ServiceUtility.checkFileExtensionImage(files)) {
            model.addAttribute("error_msg", CommonData.JPG_PNG_EXT);
            return "addCategory";
        }

        int newCatId = catService.getNewCatId();
        Category cat = new Category();
        cat.setCategoryId(newCatId);
        cat.setCatName(categoryName);
        cat.setDateAdded(ServiceUtility.getCurrentTime());
        cat.setPosterPath("null");
        cat.setDescription(categoryDesc);
        cat.setUser(usr);

        Set<Category> categories = new HashSet<Category>();
        categories.add(cat);

        try {
            userService.addUserToCategory(usr, categories);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Category: {}", categories, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "addCategory";
        }

        try {

            String folder = CommonData.uploadDirectoryCategory + newCatId;
            String document = ServiceUtility.uploadMediaFile(files, env, folder);

            Category local = catService.findBycategoryname(categoryName);
            local.setPosterPath(document);

            catService.save(local);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Category1: {}", categoryName, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "addCategory";
        }

        categoriesTemp = catService.findAll();
        model.addAttribute("categories", categoriesTemp);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
        return "addCategory";

    }

    @GetMapping("/addOrganizationRole")
    public String addOrganizationRoleGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        List<OrganizationRole> orgRoles = organizationRoleService.findAll();

        model.addAttribute("orgRoles", orgRoles);

        return "addOrganizationRole";

    }

    @PostMapping("/addOrganizationRole")
    public String addOrganizationRolePost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<OrganizationRole> orgRoles = organizationRoleService.findAll();

        model.addAttribute("orgRoles", orgRoles);

        String orgRoleName = req.getParameter("role");

        if (orgRoleName == null) {

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "addOrganizationRole";
        }

        if (organizationRoleService.getByRole(orgRoleName) != null) {

            model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
            return "addOrganizationRole";
        }

        String roleName = orgRoleName.substring(0, 1).toUpperCase() + orgRoleName.substring(1).toLowerCase();
        OrganizationRole orgRole = new OrganizationRole();
        orgRole.setDateAdded(ServiceUtility.getCurrentTime());
        orgRole.setRoleId(organizationRoleService.getnewRoleId());
        orgRole.setRole(roleName);
        organizationRoleService.save(orgRole);
        Set<OrganizationRole> roles = new HashSet<OrganizationRole>();
        roles.add(orgRole);

        orgRoles = organizationRoleService.findAll();

        model.addAttribute("orgRoles", orgRoles);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "addOrganizationRole";

    }

    @GetMapping("/organization_role/edit/{name}")
    public String editOrganizationRoleGet(@PathVariable(name = "name") String orgname, HttpServletRequest req,
            Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        OrganizationRole role = organizationRoleService.getByRole(orgname);

        model.addAttribute("role", role);

        return "updateOrganizationalRole";
    }

    @PostMapping("/update_organization_role")
    public String updateOrganizationRolePost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String roleName = req.getParameter("role");
        String lanIdInString = req.getParameter("roleId");
        int roleId = Integer.parseInt(lanIdInString);
        OrganizationRole role = organizationRoleService.getById(roleId);

        if (role == null) {
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("role", role);
            return "updateOrganizationalRole"; // accomodate view part
        }

        if (roleName == null) {

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("role", role);
            return "updateOrganizationalRole"; // accomodate view part
        }

        if (lanService.getByLanName(roleName) != null) {

            model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
            model.addAttribute("role", role);
            return "updateOrganizationalRole"; // accomodate view part
        }

        String role_formatted = roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase();

        role.setRole(role_formatted);
        try {
            organizationRoleService.save(role);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in update Organization Role: {}", role, e);
            model.addAttribute("language", role);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "updateOrganizationalRole"; // accomodate view part
        }

        role = organizationRoleService.getById(roleId);
        model.addAttribute("role", role);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "updateOrganizationalRole"; // accomodate view part

    }

    @GetMapping("/addLanguage")
    public String addLanguageGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Language> languages = lanService.getAllLanguages();

        model.addAttribute("languages", languages);

        return "addlanguage";

    }

    @PostMapping("/addLanguage")
    public String addLanguagePost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        List<Language> languagesTemp = lanService.getAllLanguages();

        model.addAttribute("languages", languagesTemp);

        String languagename = req.getParameter("languageName");

        if (languagename == null) {

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "addlanguage";
        }

        if (lanService.getByLanName(languagename) != null) {

            model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
            return "addlanguage";
        }

        String language_formatted = languagename.substring(0, 1).toUpperCase()
                + languagename.substring(1).toLowerCase();
        Language newLan = new Language();
        newLan.setLanId(lanService.getnewLanId());
        newLan.setLangName(language_formatted);
        newLan.setDateAdded(ServiceUtility.getCurrentTime());
        newLan.setUser(usr);

        Set<Language> languages = new HashSet<Language>();
        languages.add(newLan);

        try {
            userService.addUserToLanguage(usr, languages);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Language: {}", languages, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "addlanguage";
        }

        languagesTemp = lanService.getAllLanguages();
        model.addAttribute("languages", languagesTemp);

        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "addlanguage";

    }

    @GetMapping("/addCarousel")
    public String addCarouselGet(HttpServletRequest req, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI(), req.getQueryString());
        model.addAttribute("userInfo", usr);

        List<Carousel> cara = caroService.findAll();

        model.addAttribute("carousels", cara);

        return "addCarousel";
    }

    @PostMapping("/addCarousel")
    public String addCarouselPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("file") MultipartFile file, @RequestParam(value = "eventName") String name,
            @RequestParam(name = "eventDesc") String desc) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI(), req.getQueryString());

        model.addAttribute("userInfo", usr);

        List<Carousel> cara = caroService.findAll();

        model.addAttribute("carousels", cara);

        if (name == null) { // throw error
            model.addAttribute("error_msg", "Please Try Again");
            return "addCarousel";
        }

        if (desc == null) { // throw error
            model.addAttribute("error_msg", "Please Try Again");
            return "addCarousel";
        }

        if (!ServiceUtility.checkFileExtensionImage(file)) { // throw error
            model.addAttribute("error_msg", CommonData.JPG_PNG_EXT);
            return "addCarousel";
        }

        Carousel caraTemp = new Carousel();
        caraTemp.setId(caroService.getNewId());
        caraTemp.setDescription(desc);
        caraTemp.setEventName(name);

        try {

            caroService.save(caraTemp);

            String folder = CommonData.uploadCarousel + caraTemp.getId();
            String document = ServiceUtility.uploadMediaFile(file, env, folder);

            caraTemp.setPosterPath(document);

            caroService.save(caraTemp);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Carousel: {}", caraTemp, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            caroService.delete(caraTemp);
            return "addCarousel";
        }

        cara = caroService.findAll();
        model.addAttribute("carousels", cara);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "addCarousel";

    }

    @GetMapping("/addResearchPaper")
    public String addResearchPaperGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<ResearchPaper> researchPapers = researchPaperService.findAll();

        model.addAttribute("researchPapers", researchPapers);

        return "addResearchPaper";
    }

    @PostMapping("/addResearchPaper")
    public String addResearchPaperPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("researchFile") MultipartFile researchFile, @RequestParam(value = "title") String title,
            @RequestParam(name = "researchPaperDesc") String researchPaperDesc) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        boolean viewSection = false;
        model.addAttribute("viewSection", viewSection);

        List<ResearchPaper> researchPapers = researchPaperService.findAll();

        model.addAttribute("researchPapers", researchPapers);

        if (title == null) { // throw error
            model.addAttribute("error_msg", "Please Try Again");
            return "addResearchPaper";
        }

        if (researchPaperDesc == null) { // throw error
            model.addAttribute("error_msg", "Please Try Again");
            return "addResearchPaper";
        }

        if (!ServiceUtility.checkFileExtensiononeFilePDF(researchFile)) { // throw error
            model.addAttribute("error_msg", "Only PDf file is required");
            return "addResearchPaper";
        }

        ResearchPaper researchPaperTemp = new ResearchPaper();
        researchPaperTemp.setId(researchPaperService.getNewId());
        researchPaperTemp.setDateAdded(ServiceUtility.getCurrentTime());

        try {

            String folder = CommonData.uploadResearchPaper + researchPaperTemp.getId();
            List<String> documents = ServiceUtility.UploadMediaFileAndCreateThumbnail(researchFile, env, folder);

            researchPaperTemp.setResearchPaperPath(documents.get(0));
            researchPaperTemp.setThumbnailPath(documents.get(1));
            researchPaperTemp.setDescription(researchPaperDesc);
            researchPaperTemp.setTitle(title);

            researchPaperService.save(researchPaperTemp);

        } catch (Exception e) {
            logger.error("Exception while updating research paper: {} {} {}", title, researchPaperDesc, researchFile,
                    e);

            viewSection = false;
            model.addAttribute("viewSection", viewSection);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            researchPaperService.delete(researchPaperTemp);
            return "addResearchPaper";
        }

        viewSection = false;
        model.addAttribute("viewSection", viewSection);
        researchPapers = researchPaperService.findAll();
        model.addAttribute("researchPapers", researchPapers);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "addResearchPaper";

    }

    @GetMapping("/addPromoVideo")
    public String addPromoVideoGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Language> lans = lanService.getAllLanguages();
        model.addAttribute("languages", lans);

        List<PromoVideo> promovideos = promoVideoService.findAll();
        List<PathofPromoVideo> pathofPromoVideos = pathofPromoVideoService.findAll();

        model.addAttribute("promoVideos", promovideos);
        model.addAttribute("pathofPromoVideos", pathofPromoVideos);

        return "addPromoVideo";
    }

    @PostMapping("/addPromoVideo")
    public String addPromoVideoPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("promoVideo") List<MultipartFile> promoVideos,
            @RequestParam(name = "languageName") List<Integer> languageIds,
            @RequestParam(name = "title") String title) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        boolean viewSection = false;
        model.addAttribute("viewSection", viewSection);

        List<Language> languages = lanService.getAllLanguages();
        model.addAttribute("languages", languages);
        List<PromoVideo> promoVideosList = promoVideoService.findAll();
        List<PathofPromoVideo> pathofPromoVideos = pathofPromoVideoService.findAll();

        model.addAttribute("promoVideos", promoVideosList);
        model.addAttribute("pathofPromoVideos", pathofPromoVideos);

        for (MultipartFile uniquefile : promoVideos) {
            if (!uniquefile.isEmpty()) {

                if (!ServiceUtility.checkFileExtensionVideo(uniquefile)) { // throw error on extension
                    model.addAttribute("error_msg", CommonData.VIDEO_FILE_EXTENSION_ERROR);
                    return addPromoVideoGet(req, model, principal);
                }

                if (!ServiceUtility.checkVideoSizePromoVideo(uniquefile)) {
                    model.addAttribute("error_msg", "File size must be less than 1 GB");
                    return addPromoVideoGet(req, model, principal);
                }

            }

        }

        if (title == null) { // throw error
            model.addAttribute("error_msg", "Please Try again");
            return addPromoVideoGet(req, model, principal);
        }

        boolean filesError = false;
        boolean duplicatLanguage = false;

        int newPromoVideoId = promoVideoService.getNewId();
        PromoVideo promoVideoTemp = new PromoVideo();
        promoVideoTemp.setPromoId(newPromoVideoId);
        promoVideoTemp.setTitle(title);
        promoVideoTemp.setDateAdded(ServiceUtility.getCurrentTime());

        try {
            List<PathofPromoVideo> pathofPromoVideoList = new ArrayList<>();

            String document1 = "";

            int newPathOfPromoId = pathofPromoVideoService.getNewId();
            List<String> addedLanguages = new ArrayList<>();
            for (int i = 0; i < languageIds.size(); i++) {
                document1 = "";

                if (languageIds.get(i) == 0) {
                    break;
                }
                if (!promoVideos.get(i).isEmpty()) {

                    String langName = lanService.getById(languageIds.get(i)).getLangName();

                    if (!promoVideos.get(i).isEmpty()) {

                        String folder = CommonData.uploadPromoVideo + newPromoVideoId + "/" + langName;
                        document1 = ServiceUtility.uploadMediaFile(promoVideos.get(i), env, folder);
                    }

                    for (String testLan : addedLanguages) {
                        if (testLan == langName) {
                            duplicatLanguage = true;
                        }

                    }

                    addedLanguages.add(langName);

                    pathofPromoVideoList.add(new PathofPromoVideo(newPathOfPromoId, ServiceUtility.getCurrentTime(),
                            document1, promoVideoTemp, lanService.getById(languageIds.get(i))));
                    newPathOfPromoId += 1;

                } else {
                    filesError = true;
                }
            }

            if (filesError == false && duplicatLanguage == false) {

                try {
                    promoVideoService.save(promoVideoTemp);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    logger.error("Error in PromoVideo", e);
                    model.addAttribute("error_msg", CommonData.RECORD_ERROR);

                    return addPromoVideoGet(req, model, principal);
                }

                pathofPromoVideoService.saveAll(pathofPromoVideoList);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Promo Video ", e);
            viewSection = false;
            model.addAttribute("viewSection", viewSection);

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);

            return addPromoVideoGet(req, model, principal);

        }

        if (filesError == true || duplicatLanguage == true) {

            viewSection = false;

            model.addAttribute("viewSection", viewSection);

            if (filesError == true) {
                model.addAttribute("error_msg", "Video Files should not be null for selected language");
            } else {
                model.addAttribute("error_msg", "Duplicate Languages are not allowed");
            }

        } else {
            viewSection = false;
            model.addAttribute("viewSection", viewSection);

            model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
        }
        return addPromoVideoGet(req, model, principal);
    }

    @GetMapping("/addBrochure")
    public String addBrochureGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        List<Category> category = catService.findAll();
        model.addAttribute("categories", category);
        List<Language> lans = lanService.getAllLanguages();
        model.addAttribute("languages", lans);
        List<Brouchure> brouchures = broService.findAll();

        List<Version> versions = new ArrayList<Version>();
        for (Brouchure bro : brouchures) {
            Version ver = verService.findByBrouchureAndPrimaryVersion(bro, bro.getPrimaryVersion());
            versions.add(ver);

        }
        Collections.sort(versions, Version.SortByBroVersionTime);

        model.addAttribute("brouchures", brouchures);
        model.addAttribute("versions", versions);
        model.addAttribute("filesofbrouchureService", filesofbrouchureService);

        return "addBrochure";
    }

    /**
     * Add brochure to the system
     * 
     * @param model      Model object
     * @param principal  principal object
     * @param brochure   MultipartFile
     * @param categoryId int value
     * @param topicId    int value
     * @param languageId int value
     * @return String object
     */
    @PostMapping("/addBrochure")
    public String addBrochurePost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("brouchure") List<MultipartFile> brochures,
            @RequestParam(value = "categoryName") int categoryId, @RequestParam(name = "inputTopicName") int topicId,
            @RequestParam(name = "languageName") List<Integer> languageIds,
            @RequestParam(value = "primaryVersion") int primaryVersion, @RequestParam(name = "title") String title) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        boolean viewSection = false;
        model.addAttribute("viewSection", viewSection);

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("languages", languages);
        List<Brouchure> brouchures = broService.findAll();

        List<Version> versions = new ArrayList<>();
        for (Brouchure bro : brouchures) {
            Version ver = verService.findByBrouchureAndPrimaryVersion(bro, bro.getPrimaryVersion());
            versions.add(ver);
        }
        Collections.sort(versions, Version.SortByBroVersionTime);
        model.addAttribute("brouchures", brouchures);
        model.addAttribute("versions", versions);
        model.addAttribute("filesofbrouchureService", filesofbrouchureService);

        for (MultipartFile uniquefile : brochures) {
            if (!uniquefile.isEmpty()) {
                if (!ServiceUtility.checkFileExtensionImage(uniquefile)
                        && !ServiceUtility.checkFileExtensiononeFilePDF(uniquefile)) { // throw error
                    model.addAttribute("error_msg", "Only image and pdf files are supported");
                    return addBrochureGet(req, model, principal);
                }
            }

        }

        Category cat = catService.findByid(categoryId);

        Topic topic = topicService.findById(topicId);

        String versionStr = Integer.toString(primaryVersion);

        if (versionStr == null) { // throw error
            model.addAttribute("error_msg", "Please Try again");
            return addBrochureGet(req, model, principal);
        }

        if (title == null) { // throw error
            model.addAttribute("error_msg", "Please Try again");
            return addBrochureGet(req, model, principal);
        }

        boolean filesError = false;
        boolean duplicatLanguage = false;
        Language lan = lanService.getById(languageIds.get(0));

        int newBroId = broService.getNewId();
        int newVerid = verService.getNewId();
        Brouchure brochureTemp = new Brouchure();
        brochureTemp.setId(newBroId);
        brochureTemp.setLan(lan);
        brochureTemp.setTitle(title);
        brochureTemp.setPrimaryVersion(primaryVersion);

        if (cat != null) {
            brochureTemp.setCatId(cat);
        }

        if (cat != null && topic != null)

        {
            TopicCategoryMapping topicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
            brochureTemp.setTopicCatId(topicCat);
        }

        Version version = new Version();

        try {
            List<FilesofBrouchure> filesofbrochureList = new ArrayList<>();

            List<String> documents = new ArrayList<>();
            String folder = "";
            int newbroFileId = filesofbrouchureService.getNewId();
            List<String> addedLanguages = new ArrayList<>();
            for (int i = 0; i < languageIds.size(); i++) {

                folder = "";
                documents.clear();

                if (languageIds.get(i) == 0) {
                    break;
                }
                if (!brochures.get(i).isEmpty()) {

                    String langName = lanService.getById(languageIds.get(i)).getLangName();

                    if (!brochures.get(i).isEmpty()) {
                        folder = CommonData.uploadBrouchure + newBroId + "/" + primaryVersion + "/" + "web" + "/"
                                + langName;

                        documents = ServiceUtility.UploadMediaFileAndCreateThumbnail(brochures.get(i), env, folder);
                    }

                    for (String testLan : addedLanguages) {
                        if (testLan == langName) {
                            duplicatLanguage = true;
                        }

                    }

                    addedLanguages.add(langName);

                    filesofbrochureList.add(new FilesofBrouchure(newbroFileId, ServiceUtility.getCurrentTime(),
                            documents.get(0), documents.get(1), version, lanService.getById(languageIds.get(i))));
                    newbroFileId += 1;

                } else {
                    filesError = true;
                }
            }

            if (filesError == false && duplicatLanguage == false) {

                try {
                    broService.save(brochureTemp);

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    logger.error("Error in Add Brochure: {}", brochureTemp, e);
                    model.addAttribute("error_msg", CommonData.RECORD_ERROR);

                    return addBrochureGet(req, model, principal);
                }

                version.setVerId(newVerid);
                version.setVersionPosterPath("");
                version.setBrouchure(brochureTemp);
                version.setBroVersion(primaryVersion);
                version.setDateAdded(ServiceUtility.getCurrentTime());
                verService.save(version);

                filesofbrouchureService.saveAll(filesofbrochureList);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Brochure: {} {} {} {}", newVerid, primaryVersion, version, brochureTemp, e);
            viewSection = false;
            model.addAttribute("viewSection", viewSection);

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            verService.delete(version);

            return addBrochureGet(req, model, principal);

        }

        if (filesError == true || duplicatLanguage == true) {
            viewSection = false;
            model.addAttribute("viewSection", viewSection);
            if (filesError == true) {
                model.addAttribute("error_msg", "Web Files should not be null for selected language");
            } else {
                model.addAttribute("error_msg", "Duplicate Languages are not allowed");
            }

        } else {
            viewSection = false;
            model.addAttribute("viewSection", viewSection);

            model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
        }
        return addBrochureGet(req, model, principal);
    }

    @GetMapping("/addTopic")
    public String addTopicGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<TopicCategoryMapping> tcm = topicCatService.findAll();

        List<TopicCategoryMapping> tcm1 = new ArrayList<>();

        for (TopicCategoryMapping temp : tcm) {
            if (temp.getCat().isStatus() && temp.getTopic().isStatus())
                tcm1.add(temp);
        }

        model.addAttribute("userInfo", usr);

        List<Category> category = catService.findAll();
        Collections.sort(category);
        List<Category> categories = new ArrayList<>();
        List<Category> getCats = catService.findAll();
        for (Category cat : getCats) {
            if (cat.isStatus()) {
                categories.add(cat);
            }
        }

        model.addAttribute("categories", categories);

        List<Topic> getTopics = topicService.findAll();
        List<Topic> topics = new ArrayList<>();

        for (Topic topic : getTopics) {
            if (topic.isStatus()) {
                topics.add(topic);
            }
        }

        model.addAttribute("topics", topics);
        model.addAttribute("tcm", tcm1);
        return "addTopic";

    }

    @PostMapping("/addTopic")
    public String addTopicPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(name = "topicId") int topicId, @RequestParam(name = "categoryName") int categoryId,
            @RequestParam(name = "topicName") String topicName, @RequestParam(name = "orderValue") int orderValue) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        Category cat = catService.findByid(categoryId);

        if (cat == null) {
            model.addAttribute("error_msg", "Category Doesn't Exist");
            return addTopicGet(req, model, principal);
        }

        Topic topicTemp;

        if (topicId == -1) {
            topicTemp = topicService.findBytopicName(topicName);
        } else {
            topicTemp = topicService.findById(topicId);
        }

        if (topicTemp != null) {

            if (topicCatService.findAllByCategoryAndTopic(cat, topicTemp) == null) {

                TopicCategoryMapping localTopicMap = new TopicCategoryMapping(topicCatService.getNewId(), true, cat,
                        topicTemp, orderValue);
                topicCatService.save(localTopicMap);
                model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
                return addTopicGet(req, model, principal);

            } else {

                model.addAttribute("error_msg", CommonData.RECORD_ERROR);
                return addTopicGet(req, model, principal);
            }
        }

        Topic local = new Topic();
        local.setTopicId(topicService.getNewTopicId());
        local.setTopicName(topicName);
        local.setDateAdded(ServiceUtility.getCurrentTime());
        local.setUser(usr);

        Set<Topic> topics = new HashSet<Topic>();
        topics.add(local);

        try {
            userService.addUserToTopic(usr, topics);
            TopicCategoryMapping localTopicMap = new TopicCategoryMapping(topicCatService.getNewId(), true, cat, local,
                    orderValue);
            topicCatService.save(localTopicMap);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Topic : {} {}", usr, topics, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return addTopicGet(req, model, principal);
        }

        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return addTopicGet(req, model, principal);

    }

    @GetMapping("/topic/edit/{topicCatId}")
    public String editTopicGet(@PathVariable(name = "topicCatId") int topicCatId, HttpServletRequest req, Model model,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        TopicCategoryMapping tcp = tcmRepository.findById(topicCatId).get();

        Topic topic = tcp.getTopic();

        if (topic == null) {
            return "redirect:/addTopic";
        }

        model.addAttribute("topic", topic);
        model.addAttribute("topicCatMap", tcp);

        return "updateTopic"; // need to accomdate view part
    }

    @PostMapping("/updateTopic")
    public String updateTopicPost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String topicname = req.getParameter("topicName");
        String topicIdInString = req.getParameter("TopicId");
        int topicId = Integer.parseInt(topicIdInString);

        String orderVal = req.getParameter("orderValue");
        int orderValue = Integer.parseInt(orderVal);
        String topicCatIdInString = req.getParameter("TopicCatId");
        int topicCatId = Integer.parseInt(topicCatIdInString);

        TopicCategoryMapping tcp = tcmRepository.findById(topicCatId).get();
        Topic topic = topicService.findById(topicId);

        if (topic == null || tcp == null) {
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("topicCatMap", tcp);
            model.addAttribute("topic", topic);
            return "updateTopic"; // accomodate view part
        }

        if (topicname == null) {

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("topicCatMap", tcp);
            model.addAttribute("topic", topic);
            return "updateTopic"; // accomodate view part
        }

        if (topicService.findBytopicName(topicname) != null && topicService.findBytopicName(topicname) != topic) {
            model.addAttribute("topicCatMap", tcp);
            model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
            model.addAttribute("topic", topic);
            return "updateTopic"; // accomodate view part
        }

        topic.setTopicName(topicname);
        tcp.setOrder(orderValue);

        try {
            topicService.save(topic);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Update Topic: {}", topic, e);

        }
        try {

            topicCatService.save(tcp);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Update Topic: {}", tcp, e);

        }

        topic = topicService.findById(topicId);
        tcp = tcmRepository.findById(topicCatId).get();
        model.addAttribute("topic", topic);
        model.addAttribute("topicCatMap", tcp);

        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "updateTopic"; // accomodate view part

    }

    @GetMapping("/addRole")
    public String addRoleGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        List<Role> roles = roleService.findAll();

        model.addAttribute("roles", roles);

        return "addNewRole";

    }

    @PostMapping("/addRole")
    public String addRolePost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Role> roles = roleService.findAll();

        model.addAttribute("roles", roles);

        String roleName = req.getParameter("roleName");

        if (roleName == null) {

            model.addAttribute("error_msg", "Please Try Again");
            return "addNewRole";
        }

        if (roleService.findByname(roleName) != null) {

            model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
            return "addNewRole";
        }

        Role newRole = new Role();
        newRole.setRoleId(roleService.getNewRoleId());
        newRole.setName(roleName);

        try {
            roleService.save(newRole);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add New Role: {}", newRole, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "addNewRole";
        }

        roles = roleService.findAll();

        model.addAttribute("roles", roles);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
        return "addNewRole";

    }

    @GetMapping("/uploadQuestion")
    public String addQuestionGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        List<Question> questions = questService.findAll();
        model.addAttribute("questions", questions);

        List<Language> languages = lanService.getAllLanguages();

        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        return "uploadQuestion";

    }

    @PostMapping("/uploadQuestion")
    public String addQuestionPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("questionName") MultipartFile quesPdf, @RequestParam(value = "categoryName") int categoryId,
            @RequestParam(name = "inputTopicName") int topicId, @RequestParam(name = "languageyName") int languageId) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Question> questionsTemp = questService.findAll();
        model.addAttribute("questions", questionsTemp);

        List<Language> languages = lanService.getAllLanguages();

        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        if (!ServiceUtility.checkFileExtensiononeFilePDF(quesPdf)) { // throw error

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "uploadQuestion";
        }

        if (!ServiceUtility.checkScriptSlideProfileQuestion(quesPdf)) {

            model.addAttribute("error_msg", "File Size must be less than 20MB");
            return "uploadQuestion";
        }

        Category cat = catService.findByid(categoryId);
        Topic topic = topicService.findById(topicId);

        if (cat == null) { // throw error

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "uploadQuestion";
        }

        if (topic == null) { // throw error

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "uploadQuestion";
        }

        TopicCategoryMapping topicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
        Language lan = lanService.getById(languageId);

        Question quesTemp = questService.getQuestionBasedOnTopicCatAndLan(topicCat, lan);

        if (quesTemp != null) {

            model.addAttribute("error_msg", CommonData.QUESTION_EXIST);
            return "uploadQuestion";
        }
        int newQuestionId = questService.getNewId();
        Question question = new Question();
        question.setQuestionId(newQuestionId);
        question.setDateAdded(ServiceUtility.getCurrentTime());
        question.setLan(lan);
        question.setQuestionPath("null");
        question.setTopicCatId(topicCat);
        question.setUser(usr);

        Set<Question> questions = new HashSet<Question>();
        questions.add(question);

        try {
            userService.addUserToQuestion(usr, questions);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Upload Question: {} {}", usr, questions, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "uploadQuestion";
        }

        try {

            String folder = CommonData.uploadDirectoryQuestion + newQuestionId;
            String document = ServiceUtility.uploadMediaFile(quesPdf, env, folder);

            Question temp = questService.findById(newQuestionId);

            temp.setQuestionPath(document);

            questService.save(temp);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Upload Question: {} {}", newQuestionId, quesPdf, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "uploadQuestion";
        }

        questionsTemp = questService.findAll();
        model.addAttribute("questions", questionsTemp);

        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "uploadQuestion";

    }

    @GetMapping("/question/edit/{catName}/{topicName}/{language}")
    public String editQuestionGet(@PathVariable(name = "catName") String cat,
            @PathVariable(name = "topicName") String topic, @PathVariable(name = "language") HttpServletRequest req,
            String lan, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Category catName = catService.findBycategoryname(cat);
        Topic topicName = topicService.findBytopicName(topic);
        Language lanName = lanService.getByLanName(lan);
        TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);

        Question ques = questService.getQuestionBasedOnTopicCatAndLan(topicCatMap, lanName);

        if (catName == null || topicName == null || lanName == null || topicCatMap == null || ques == null) {
            return "redirect:/uploadQuestion";
        }

        model.addAttribute("question", ques);

        return "updateQuestion";
    }

    @PostMapping("/updateQuestion")
    public String updateQuestionPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("questionName") MultipartFile quesPdf) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        String quesIdInString = req.getParameter("id");
        int idQues = Integer.parseInt(quesIdInString);
        Question ques = questService.findById(idQues);

        if (ques == null) { // throw error

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("question", ques);
            return "updateQuestion"; // accomodate error
        }

        if (!ServiceUtility.checkFileExtensiononeFilePDF(quesPdf)) { // throw error

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("question", ques);
            return "updateQuestion"; // accomodate error
        }

        if (!ServiceUtility.checkScriptSlideProfileQuestion(quesPdf)) {

            model.addAttribute("error_msg", "File Size must be less than 20MB");
            model.addAttribute("question", ques);
            return "updateQuestion";
        }

        try {

            String folder = CommonData.uploadDirectoryQuestion + ques.getQuestionId();
            String document = ServiceUtility.uploadMediaFile(quesPdf, env, folder);

            ques.setQuestionPath(document);

            questService.save(ques);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Update Question: {} {}", ques, quesPdf, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("question", ques);
            return "updateQuestion"; // accomodate view part
        }

        ques = questService.findById(idQues);
        model.addAttribute("question", ques);

        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "updateQuestion"; // accomodate view part

    }

    @GetMapping("/addConsultant")
    public String addConsultantGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Consultant> consultants = consultService.findAll();
        List<Category> cat = catService.findAll();
        List<Language> lans = lanService.getAllLanguages();

        model.addAttribute("categories", cat);
        model.addAttribute("consultants", consultants);
        model.addAttribute("languages", lans);

        return "addConsultant";

    }

    @PostMapping("/addConsultant")
    public String addConsultantPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("nameConsaltant") String name, @RequestParam("lastname") String lastname,
            @RequestParam("categoryName") int catId, @RequestParam("lanName") int lanId,
            @RequestParam("email") String email, @RequestParam("desc") String desc,
            @RequestParam("photo") MultipartFile photo) {

        User usr = getUser(principal);

        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Consultant> consultants = consultService.findAll();
        List<Category> cat = catService.findAll();
        List<Language> lans = lanService.getAllLanguages();

        model.addAttribute("categories", cat);
        model.addAttribute("consultants", consultants);
        model.addAttribute("languages", lans);

        if (!ServiceUtility.checkEmailValidity(email)) { // throw email wromng error

            model.addAttribute("error_msg", CommonData.NOT_VALID_EMAIL_ERROR);
            return "addConsultant";
        }

        Category cats = catService.findByid(catId);
        Language lan = lanService.getById(lanId);

        if (cats == null) { // throw email wromng error

            model.addAttribute("error_msg", "Cat is null, Please Try Again");
            return "addConsultant";
        }

        if (lan == null) { // throw email wromng error

            model.addAttribute("error_msg", " Language is null, Please Try Again");
            return "addConsultant";
        }

        if (!photo.isEmpty()) {
            if (!ServiceUtility.checkFileExtensionImage(photo)) {
                model.addAttribute("error_msg", CommonData.VIDEO_CONSENT_FILE_EXTENSION_ERROR);
                return "addConsultant";
            }

        }

        boolean flagforExistingUser = false;
        User userTemp = userService.findByUsername(email);

        if (userTemp == null) {
            userTemp = new User();
            userTemp.setId(userService.getNewId());
            userTemp.setFirstName(name);
            userTemp.setLastName(lastname);
            userTemp.setEmail(email);
            userTemp.setUsername(email);
            userTemp.setDateAdded(ServiceUtility.getCurrentTime());
            userTemp.setPassword(SecurityUtility.passwordEncoder().encode(CommonData.COMMON_PASSWORD));
            userTemp.setEmailVerificationCode("");
        } else {
            if (consultService.findByUser(userTemp) != null) {
                model.addAttribute("error_msg", " Email is already assigned to consultant");
                return "addConsultant";
            }

            flagforExistingUser = true;
        }

        int newConsultid = consultService.getNewConsultantId();
        Consultant consultant = new Consultant();
        consultant.setConsultantId(newConsultid);
        consultant.setDescription(desc);
        consultant.setDateAdded(ServiceUtility.getCurrentTime());
        try {
            if (!photo.isEmpty()) {

                String folder = CommonData.uploadDirectoryConsultant + newConsultid;
                String cons_photo = ServiceUtility.uploadMediaFile(photo, env, folder);
                userTemp.setProfilePic(cons_photo);
            }

        } catch (Exception e1) {
            logger.error("Error in Add Consultant: {} {}", newConsultid, photo, e1);
        }

        userService.save(userTemp);

        consultant.setUser(userTemp);

        Set<Consultant> consults = new HashSet<Consultant>();
        consults.add(consultant);

        try {

            userService.addUserToConsultant(usr, consults);
            Role role = roleService.findByname(CommonData.domainReviewerRole);

            UserRole usrRole = new UserRole();
            usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());
            usrRole.setCat(cats);
            usrRole.setLan(lan);
            usrRole.setUser(userTemp);
            usrRole.setRole(role);
            usrRole.setStatus(true);
            usrRole.setCreated(ServiceUtility.getCurrentTime());

            usrRoleService.save(usrRole);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Add Consultant: {} {}", consults, userTemp, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);

            return "addConsultant"; // throw a error
        }

        if (flagforExistingUser == true) {
            SimpleMailMessage msg1 = mailConstructor.domainRoleMailSendforExistingUser(userTemp);
            mailSender.send(msg1);
        } else {
            SimpleMailMessage msg2 = mailConstructor.domainRoleMailSend(userTemp);
            mailSender.send(msg2);
        }

        consultants = consultService.findAll();
        model.addAttribute("consultants", consultants);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
        return "addConsultant";
    }

    @GetMapping("/consultant/edit/{id}")
    public String editConsultant(@PathVariable int id, HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        Consultant consultant = consultService.findById(id);

        if (consultant == null) {

            return "redirect:/addConsultant";
        }

        model.addAttribute("consultant", consultant);

        return "updateConsultant";
    }

    @PostMapping("/updateConsultant")
    public String updateConnsultant(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("photo") MultipartFile file) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        String consultant_id = req.getParameter("consultant_id");
        String name = req.getParameter("name");
        String lastname = req.getParameter("lastname");
        String desc = req.getParameter("desc");

        Consultant consultant = consultService.findById(Integer.parseInt(consultant_id));

        if (consultant == null) {
            // accommodate error message
            model.addAttribute("error_msg", CommonData.CONSULTANT_ERROR);
            return "addConsultant";
        }

        if (!file.isEmpty()) {
            try {

                ;
                try {

                } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    logger.error("Error in Upadte Consultant: {}", file, e1);
                }

                String folder2 = CommonData.uploadDirectoryConsultant + consultant.getConsultantId();
                String document = ServiceUtility.uploadMediaFile(file, env, folder2);
                consultant.getUser().setFirstName(name);
                consultant.getUser().setLastName(lastname);
                consultant.setDescription(desc);
                consultant.getUser().setProfilePic(document);
                consultService.save(consultant);

            } catch (Exception e) {
                // TODO: handle exception

                logger.error("Error in Update Consultant: {} {} {} {}", name, lastname, desc, consultant, e);
                model.addAttribute("error_msg", CommonData.RECORD_ERROR);
                model.addAttribute("consultant", consultant);
                return "updateConsultant"; // throw a error
            }
        } else {

            consultant.getUser().setFirstName(name);
            consultant.getUser().setLastName(lastname);
            consultant.setDescription(desc);

            consultService.save(consultant);
        }
        model.addAttribute("consultant", consultant);

        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "updateConsultant";
    }

    @GetMapping("/addEvent")
    public String addEventGet(HttpServletRequest req, Principal principal, Model model) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Event> events = eventservice.findAll();
        model.addAttribute("events", events);
        List<State> states = stateService.findAll();
        model.addAttribute("states", states);
        List<Category> categories = catService.findAll();
        model.addAttribute("categories", categories);
        List<Language> lans = lanService.getAllLanguages();
        model.addAttribute("lans", lans);

        return "addEvent";
    }

    @PostMapping("/addEvent")
    public String addEventPost(Model model, Principal principal, HttpServletRequest req,
            @RequestParam("Image") MultipartFile files, @RequestParam(value = "inputTopic") int[] topicId,
            @RequestParam(value = "categoryName") int catName) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        List<Event> eventsTemp = eventservice.findAll();
        model.addAttribute("events", eventsTemp);

        List<State> states = stateService.findAll();
        model.addAttribute("states", states);
        List<Category> categories = catService.findAll();
        model.addAttribute("categories", categories);
        List<Language> lans = lanService.getAllLanguages();
        model.addAttribute("lans", lans);

        String eventName = req.getParameter("eventname");
        String desc = req.getParameter("description");
        String venueName = req.getParameter("venuename");
        String contactPerson = req.getParameter("contactperson");
        String contactNumber = req.getParameter("contactnumber");
        String email = req.getParameter("email");
        String startDateTemp = req.getParameter("date");
        String endDateTemp = req.getParameter("endDate");

        String pinCode = req.getParameter("pinCode");
        String stateName = req.getParameter("stateName");
        String districtName = req.getParameter("districtName");
        String cityName = req.getParameter("cityName");
        String addressInformationName = req.getParameter("addressInformationName");
        String language = req.getParameter("language");

        Date startDate;
        Date endDate;
        int newEventid;

        if (stateService.findById(Integer.parseInt(stateName)) == null) {
            model.addAttribute("error_msg", "Please Select State");
            return "addEvent";
        }

        if (districtService.findById(Integer.parseInt(districtName)) == null) {
            model.addAttribute("error_msg", "Please Select District");
            return "addEvent";
        }

        if (lanService.getByLanName(language) == null) {
            model.addAttribute("error_msg", "Please Select language");
            return "addEvent";
        }

        if (catService.findByid(catName) == null) {
            model.addAttribute("error_msg", "Please Select Category");
            return "addEvent";
        }

        try {
            startDate = ServiceUtility.convertStringToDate(startDateTemp);
            endDate = ServiceUtility.convertStringToDate(endDateTemp);
            if (!files.isEmpty()) {
                if (!ServiceUtility.checkFileExtensionImage(files)) {
                    model.addAttribute("error_msg", CommonData.JPG_PNG_EXT);
                    return "addEvent";
                }
            }

            if (endDate.before(startDate)) { // throws error if end date is previous to start date
                model.addAttribute("error_msg", CommonData.EVENT_CHECK_DATE);
                return "addEvent";
            }
            if (!email.isEmpty()) {
                if (!ServiceUtility.checkEmailValidity(email)) { // throw error on wrong email
                    model.addAttribute("error_msg", CommonData.EVENT_CHECK_EMAIL);
                    return "addEvent";
                }
            }

            if (!contactNumber.isEmpty()) {
                if (contactNumber.length() != 10) { // throw error on wrong phone number
                    model.addAttribute("error_msg", CommonData.EVENT_CHECK_CONTACT);
                    return "addEvent";
                }
            }

            long contact = 0;
            if (!contactNumber.isEmpty()) {
                contact = Long.parseLong(contactNumber);
            }

            newEventid = eventservice.getNewEventId();
            Event event = new Event();

            ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")
                    + CommonData.uploadDirectoryEvent + newEventid);
            if (!files.isEmpty()) {

                String folder = CommonData.uploadDirectoryEvent + newEventid;
                String document = ServiceUtility.uploadMediaFile(files, env, folder);
                event.setPosterPath(document);
            }

            event.setEventId(newEventid);
            event.setContactPerson(contactPerson);
            event.setDateAdded(ServiceUtility.getCurrentTime());
            event.setEmail(email);
            event.setDescription(desc);
            event.setEndDate(endDate);
            event.setStartDate(startDate);
            event.setUser(usr);
            event.setContactNumber(contact);
            event.setEventName(eventName);
            event.setLocation(venueName);
            event.setAddress(addressInformationName);
            event.setState(stateService.findById(Integer.parseInt(stateName)));
            event.setDistrict(districtService.findById(Integer.parseInt(districtName)));
            if (cityService.findById(Integer.parseInt(cityName)) != null) {
                event.setCity(cityService.findById(Integer.parseInt(cityName)));
            }

            event.setPincode(Integer.parseInt(pinCode));
            event.setLan(lanService.getByLanName(language));
            Set<TrainingTopic> trainingTopicTemp = new HashSet<>();
            Category cat = catService.findByid(catName);

            try {
                int trainingTopicId = trainingTopicServ.getNewId();
                for (int topicID : topicId) {
                    Topic topicTemp = topicService.findById(topicID);
                    TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(cat, topicTemp);
                    TrainingTopic trainingTemp = new TrainingTopic(trainingTopicId++, topicCatMap, event);
                    trainingTopicTemp.add(trainingTemp);

                }

                event.setTrainingTopicId(trainingTopicTemp);

                Set<Event> events = new HashSet<Event>();
                events.add(event);

                userService.addUserToEvent(usr, events);

            } catch (Exception e) {
                logger.error("Error in Add Event: {} {}", cat, event, e);
            }
        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            logger.error("Error in Add Event", e);
            return "addEvent";
        } finally {

        }

        eventsTemp = eventservice.findAll();
        model.addAttribute("events", eventsTemp);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
        return "addEvent";

    }

    @GetMapping("/addTestimonial")
    public String addTestimonialGet(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Testimonial> testimonials = testService.findAll();
        List<TrainingInformation> trainings = trainingInfoService.findAll();
        List<Event> events = eventservice.findAll();
        model.addAttribute("testimonials", testimonials);
        model.addAttribute("trainings", trainings);
        model.addAttribute("events", events);

        return "addTestimonial";

    }

    @PostMapping("/addTestimonial")
    public String addTestimonialPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("uploadTestimonial") MultipartFile file, @RequestParam("consent") MultipartFile consent,
            @RequestParam("testimonialName") String name, @RequestParam("description") String desc,
            @RequestParam(value = "trainingName", required = false) String trainingId) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Testimonial> testimonials = testService.findAll();
        List<TrainingInformation> trainings = trainingInfoService.findAll();
        model.addAttribute("testimonials", testimonials);
        model.addAttribute("trainings", trainings);

        if (!consent.isEmpty()) {
            if (!ServiceUtility.checkFileExtensionImage(consent)
                    && !ServiceUtility.checkFileExtensiononeFilePDF(consent)) {
                model.addAttribute("error_msg", "Pdf file is required");
                return "addTestimonial";
            }
        }

        if (!file.isEmpty()) {
            if (!ServiceUtility.checkFileExtensionVideo(file)) { // throw error on extension
                model.addAttribute("error_msg", CommonData.VIDEO_FILE_EXTENSION_ERROR);
                return "addTestimonial";
            }

            if (!ServiceUtility.checkVideoSizeTestimonial(file)) {
                model.addAttribute("error_msg", "File size must be less than 20MB");
                return "addTestimonial";
            }

            int newTestiId = testService.getNewTestimonialId();
            Testimonial test = new Testimonial();
            test.setDateAdded(ServiceUtility.getCurrentTime());
            test.setDescription(desc);
            test.setName(name);
            test.setUser(usr);
            test.setTestimonialId(newTestiId);
            test.setFilePath("null");

            if (trainingId != null) {
                TrainingInformation train = trainingInfoService.getById(Integer.parseInt(trainingId));
                test.setTraineeInfos(train);
                test.setApproved(false);
            }

            Set<Testimonial> testi = new HashSet<Testimonial>();
            testi.add(test);

            try {
                userService.addUserToTestimonial(usr, testi);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                logger.error("Error in Add Testimonial: {} {}", usr, testi, e);
                model.addAttribute("error_msg", CommonData.RECORD_ERROR);
                return "addTestimonial";
            }

            try {

                String folder2 = CommonData.uploadDirectoryTestimonial + newTestiId;
                String document = ServiceUtility.uploadMediaFile(file, env, folder2);
                Testimonial temp = testService.findById(newTestiId);

                temp.setFilePath(document);

                if (!consent.isEmpty()) {

                    String folder = CommonData.uploadDirectoryTestimonial + newTestiId;
                    List<String> documents = ServiceUtility.UploadMediaFileAndCreateThumbnail(consent, env, folder);
                    temp.setConsentLetter(documents.get(0));
                    temp.setThumbnailPath(documents.get(1));
                }

                testService.save(temp);

            } catch (Exception e) {
                // TODO: handle exception

                logger.error("Error in Add Testimonial: {} {}", consent, newTestiId, e);
                model.addAttribute("error_msg", CommonData.RECORD_ERROR);
                return "addTestimonial"; // throw a error
            }
        }

        testimonials = testService.findAll();
        model.addAttribute("testimonials", testimonials);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
        return "addTestimonial";

    }

    @GetMapping("/category/edit/{catName}")
    public String editCategoryGet(@PathVariable(name = "catName") String catName, HttpServletRequest req, Model model,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Category cat = catService.findBycategoryname(catName);

        if (cat == null) {
            return "redirect:/category";
        }

        model.addAttribute("category", cat);

        return "updateCategory";
    }

    @PostMapping("/updateCategory")
    public String updateCategoryPost(Model model, Principal principal, HttpServletRequest req,
            @RequestParam("categoryImage") MultipartFile file) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String catId = req.getParameter("id");
        String catName = req.getParameter("categoryname");
        String categoryDesc = req.getParameter("categoryDesc");

        Category cat = catService.findByid(Integer.parseInt(catId));

        if (cat == null) {
            // accommodate error message
            model.addAttribute("category", cat);
            model.addAttribute("error_msg", "Category doesn't exist");
            return "updateCategory";
        }

        List<Category> cats = catService.findAll();
        for (Category x : cats) {
            if (x.getCategoryId() != cat.getCategoryId()) {
                if (catName.equalsIgnoreCase(x.getCatName())) {
                    // accommodate error message
                    model.addAttribute("category", cat);
                    model.addAttribute("error_msg", "Category Name Already Exist");
                    return "updateCategory";
                }
            }
        }

        cat.setCatName(catName);
        cat.setDescription(categoryDesc);

        if (!file.isEmpty()) {
            try {

                String folder = CommonData.uploadDirectoryCategory + cat.getCategoryId();
                String document = ServiceUtility.uploadMediaFile(file, env, folder);

                cat.setPosterPath(document);

                catService.save(cat);

            } catch (Exception e) {
                // TODO: handle exception

                logger.error("Error in Update Category: {}", cat, e);
                model.addAttribute("category", cat);
                model.addAttribute("error_msg", CommonData.RECORD_ERROR);
                return "updateCategory"; // throw a error
            }
        } else {

            catService.save(cat);
        }

        cat = catService.findByid(Integer.parseInt(catId));
        model.addAttribute("category", cat);

        model.addAttribute("success_msg", CommonData.RECORD_UPDATE_SUCCESS_MSG); // need to accommodate

        return "updateCategory";
    }

    @GetMapping("/eventDetails/{id}")
    public String eventGet(@PathVariable int id, HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Event event = eventservice.findById(id);

        if (event == null) {
            return "redirect:/event";
        }

        model.addAttribute("event", event);

        return "event";
    }

    @GetMapping("/event")
    public String viewEventGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Event> event = eventservice.findAll();
        model.addAttribute("events", event);

        return "event";
    }

    @GetMapping("/event/edit/{id}")
    public String editEventGet(@PathVariable int id, HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Event event = eventservice.findById(id);

        if (event == null) {

            return "redirect:/addEvent";
        }

        model.addAttribute("events", event);

        return "updateEvent";
    }

    @PostMapping("/updateEvent")
    public String updateEventPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("Image") MultipartFile files) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String eventId = req.getParameter("eventId");
        String eventName = req.getParameter("eventname");
        String desc = req.getParameter("description");
        String venueName = req.getParameter("venuename");
        String contactPerson = req.getParameter("contactperson");
        String contactNumber = req.getParameter("contactnumber");
        String email = req.getParameter("email");
        String startDateTemp = req.getParameter("date");
        String endDateTemp = req.getParameter("endDate");
        Date startDate;
        Date endDate;

        Event event = eventservice.findById(Integer.parseInt(eventId));

        model.addAttribute("events", event);

        if (event == null) {
            model.addAttribute("error_msg", "Event doesn't exist");
            return "updateEvent";
        }

        try {
            startDate = ServiceUtility.convertStringToDate(startDateTemp);
            endDate = ServiceUtility.convertStringToDate(endDateTemp);

            if (endDate.before(startDate)) { // throws error if end date is previous to start date

                model.addAttribute("error_msg", "End date must be after Start date");
                return "updateEvent";
            }

            if (!email.isEmpty()) {
                if (!ServiceUtility.checkEmailValidity(email)) { // throw error on wrong email

                    model.addAttribute("error_msg", CommonData.NOT_VALID_EMAIL_ERROR);
                    return "updateEvent";
                }
            }

            if (!files.isEmpty()) {
                if (!ServiceUtility.checkFileExtensionImage(files)) { // throw error on extension
                    model.addAttribute("error_msg", CommonData.JPG_PNG_EXT);
                    return "updateEvent";
                }
            }
            long contact = 0;
            if (!contactNumber.isEmpty()) {
                contact = Long.parseLong(contactNumber);
            }

            event.setContactPerson(contactPerson);
            event.setEmail(email);
            event.setDescription(desc);
            event.setEndDate(endDate);
            event.setStartDate(startDate);
            event.setContactNumber(contact);
            event.setEventName(eventName);
            event.setLocation(venueName);

            if (!files.isEmpty()) {

                String folder = CommonData.uploadDirectoryEvent + event.getEventId();
                String document = ServiceUtility.uploadMediaFile(files, env, folder);

                event.setPosterPath(document);

            }

            eventservice.save(event);

        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("events", event);
            return "updateEvent"; // need to add some error message
        }

        model.addAttribute("success_msg", CommonData.RECORD_UPDATE_SUCCESS_MSG);
        model.addAttribute("events", event);

        return "updateEvent";
    }

    @GetMapping("/promoVideo/edit/{id}")
    public String promoVideoGet(@PathVariable int id, HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        PromoVideo promoVideo = promoVideoService.findById(id);

        if (promoVideo == null) {

            return "redirect:/addPromoVideo";
        }

        List<Language> languages = lanService.getAllLanguages();
        model.addAttribute("languages", languages);

        List<PathofPromoVideo> pathofPromoVideoList = pathofPromoVideoService.findByPromoVideo(promoVideo);

        model.addAttribute("pathofPromoVideoList", pathofPromoVideoList);
        model.addAttribute("promoVideo", promoVideo);

        List<PromoVideo> promoVideos = promoVideoService.findAll();

        model.addAttribute("promoVideos", promoVideos);

        return "updatePromoVideo";
    }

    @PostMapping("/updatePromoVideo")
    public String updatePromoVideoPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(name = "languageName") List<Integer> languageIds,
            @RequestParam("promoVideo") List<MultipartFile> promoVideoFiles) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String title = req.getParameter("title");
        String promoVideoId = req.getParameter("promoVideoId");
        int promoVideoIdInt = Integer.parseInt(promoVideoId);

        List<Language> languages = lanService.getAllLanguages();
        model.addAttribute("languages", languages);

        PromoVideo promoVideo = promoVideoService.findById(Integer.parseInt(promoVideoId));

        if (promoVideo == null) {
            model.addAttribute("error_msg", "Brouchure doesn't exist");
            return "updatePromoVideo";
        }

        List<PathofPromoVideo> pathofPromoVideoList = pathofPromoVideoService.findByPromoVideo(promoVideo);
        model.addAttribute("pathofPromoVideoList", pathofPromoVideoList);
        model.addAttribute("promoVideo", promoVideo);

        if (title.isEmpty()) {
            model.addAttribute("error_msg", "Title doesn't exist with empty");
            return "updatePromoVideo";
        }

        boolean fileError = false;
        boolean duplicatLanguage = false;

        try {

            for (MultipartFile uniquefile : promoVideoFiles) {
                if (!uniquefile.isEmpty()) {

                    if (!ServiceUtility.checkFileExtensionVideo(uniquefile)) { // throw error on extension
                        model.addAttribute("error_msg", CommonData.VIDEO_FILE_EXTENSION_ERROR);
                        return "updatePromoVideo";
                    }

                    if (!ServiceUtility.checkVideoSizePromoVideo(uniquefile)) {
                        model.addAttribute("error_msg", "File size must be less than 1 GB");
                        return "updatePromoVideo";
                    }

                }

            }

            String document1 = "";
            int newpathofPromoVideoId = pathofPromoVideoService.getNewId();
            List<PathofPromoVideo> pathofPromoVideoList1 = new ArrayList<>();
            List<String> addedlanguages = new ArrayList<>();
            for (int i = 0; i < languageIds.size(); i++) {
                document1 = "";

                if (languageIds.get(i) == 0) {
                    break;
                }

                Language language = lanService.getById(languageIds.get(i));
                String langName = language.getLangName();

                PathofPromoVideo pathofPromoVideo1 = pathofPromoVideoService.findByLanguageandPromoVideo(language,
                        promoVideo);

                if (!promoVideoFiles.get(i).isEmpty()) {

                    String folder = CommonData.uploadPromoVideo + promoVideoIdInt + "/" + langName;
                    document1 = ServiceUtility.uploadMediaFile(promoVideoFiles.get(i), env, folder);

                }

                for (String testlan : addedlanguages) {
                    if (testlan == langName) {
                        duplicatLanguage = true;
                    }
                }
                addedlanguages.add(langName);

                if (pathofPromoVideo1 != null) {
                    pathofPromoVideo1.setLan(language);

                    if (!promoVideoFiles.get(i).isEmpty()) {
                        pathofPromoVideo1.setVideoPath(document1);
                    }

                    pathofPromoVideoService.save(pathofPromoVideo1);

                }

                else {

                    if (!promoVideoFiles.get(i).isEmpty()) {
                        pathofPromoVideoList1.add(new PathofPromoVideo(newpathofPromoVideoId,
                                ServiceUtility.getCurrentTime(), document1, promoVideo, language));
                        newpathofPromoVideoId = newpathofPromoVideoId + 1;

                    } else {
                        fileError = true;
                    }

                }

            }
            if (fileError == false && duplicatLanguage == false) {

                promoVideo.setTitle(title);
                promoVideoService.save(promoVideo);

                pathofPromoVideoService.saveAll(pathofPromoVideoList1);
            }

        }

        catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);

            pathofPromoVideoList = pathofPromoVideoService.findByPromoVideo(promoVideo);
            model.addAttribute("pathofPromoVideoList", pathofPromoVideoList);
            model.addAttribute("promoVideo", promoVideo);

            return "updatePromoVideo"; // need to add some error message
        }

        if (fileError == true) {
            model.addAttribute("error_msg", "PromoVideo  file is required for new Language");
            return "updatePromoVideo";

        }

        if (duplicatLanguage == true) {
            model.addAttribute("error_msg", "Duplicate Languages are not allowed");
            return "updatePromoVideo";

        }

        model.addAttribute("success_msg", CommonData.RECORD_UPDATE_SUCCESS_MSG);
        pathofPromoVideoList = pathofPromoVideoService.findByPromoVideo(promoVideo);
        model.addAttribute("pathofPromoVideoList", pathofPromoVideoList);
        model.addAttribute("promoVideo", promoVideo);

        return "updatePromoVideo";
    }

    @GetMapping("/brochure/edit/{id}")
    public String BrochureGet(@PathVariable int id, HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Brouchure brochure = broService.findById(id);
        if (brochure == null) {

            return "redirect:/addBrochure";
        }

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("languages", languages);

        Language langByBrochure = brochure.getLan();

        Set<Version> verSet = brochure.getVersions();

        Version version = null;

        for (Version ver : verSet) {
            if (brochure.getPrimaryVersion() == ver.getBroVersion()) {
                version = ver;
                break;
            }
        }

        List<Version> listofVersions = new ArrayList<>(verSet);

        Collections.sort(listofVersions, Version.SortByBroVersionTime);

        List<Version> newlistofVersion = new ArrayList<>();
        int temp = 0;
        for (Version ver : listofVersions) {
            newlistofVersion.add(ver);
            temp++;
            if (temp == 3) {
                break;
            }

        }

        List<FilesofBrouchure> newfilesList = new ArrayList<>();
        for (Version ver1 : newlistofVersion) {
            for (Language lan : languages) {
                FilesofBrouchure filesBro = filesofbrouchureService.findByLanguageandVersion(lan, ver1);
                if (filesBro != null) {
                    newfilesList.add(filesBro);
                }
            }
        }

        model.addAttribute("newfilesList", newfilesList);

        List<FilesofBrouchure> filesOfBroList = filesofbrouchureService.findByVersion(version);

        model.addAttribute("filesOfBroList", filesOfBroList);
        model.addAttribute("listofVersions", newlistofVersion);
        model.addAttribute("version", version);
        model.addAttribute("brouchure", brochure);
        model.addAttribute("langByBrouchure", langByBrochure);

        List<Brouchure> brouchures = broService.findAll();
        List<Version> versions = new ArrayList<Version>();
        for (Brouchure bro : brouchures) {
            Version ver = verService.findByBrouchureAndPrimaryVersion(bro, bro.getPrimaryVersion());
            versions.add(ver);
        }
        Collections.sort(versions, Version.SortByBroVersionTime);
        for (Version ver : versions) {

        }
        model.addAttribute("brouchures", brouchures);
        model.addAttribute("versions", versions);

        return "updateBrochure";
    }

    @PostMapping("/updateBrochure")
    public String updatBrochurePost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(name = "languageName") List<Integer> languageIds,
            @RequestParam("brouchure") List<MultipartFile> brochures) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String title = req.getParameter("title");
        String brochureId = req.getParameter("brochureId");

        String overwrite = req.getParameter("overwrite");
        int overwriteValue = 0;
        if (overwrite != null)
            overwriteValue = Integer.parseInt(overwrite);
        /*
         * String cat = req.getParameter("categoryName"); String topic =
         * req.getParameter("inputTopic"); String lang =
         * req.getParameter("languageyName");
         */

        List<Language> languages = lanService.getAllLanguages();
        model.addAttribute("languages", languages);

        Brouchure brouchure = broService.findById(Integer.parseInt(brochureId));

        if (brouchure == null) {
            model.addAttribute("error_msg", "Brouchure doesn't exist");
            return "updateBrochure";
        }

        Version version = verRepository.findByBrouchureAndBroVersion(brouchure, brouchure.getPrimaryVersion());

        List<FilesofBrouchure> filesOfBroList = filesofbrouchureService.findByVersion(version);
        model.addAttribute("filesOfBroList", filesOfBroList);
        model.addAttribute("version", version);

        model.addAttribute("brouchure", brouchure);

        Set<Version> verSet = brouchure.getVersions();
        List<Version> listofVersions = new ArrayList<>(verSet);
        Collections.sort(listofVersions, Version.SortByBroVersionTime);
        List<Version> newlistofVesrion = new ArrayList<>();
        int temp = 0;
        for (Version ver : listofVersions) {
            newlistofVesrion.add(ver);
            temp++;
            if (temp == 3)
                break;
        }

        List<FilesofBrouchure> newfilesList = new ArrayList<>();
        for (Version ver1 : newlistofVesrion) {
            for (Language lan : languages) {
                FilesofBrouchure filesBro = filesofbrouchureService.findByLanguageandVersion(lan, ver1);
                if (filesBro != null) {
                    newfilesList.add(filesBro);
                }
            }
        }

        model.addAttribute("newfilesList", newfilesList);

        model.addAttribute("listofVersions", newlistofVesrion);

        if (title.isEmpty()) {
            model.addAttribute("error_msg", "Title doesn't exist with empty");
            return "updateBrochure";
        }

        int newVerId = verService.getNewId();
        int versionValue = brouchure.getPrimaryVersion();
        int newVersionValue = versionValue + 1;
        boolean filesError = false;
        boolean duplicatLanguage = false;
        boolean duplicatelangforOverride = false;
        boolean webfileErrorforOverride = false;

        try {

            for (MultipartFile uniquefile : brochures) {
                if (!uniquefile.isEmpty()) {
                    if (!ServiceUtility.checkFileExtensionImage(uniquefile)
                            && !ServiceUtility.checkFileExtensiononeFilePDF(uniquefile)) { // throw error
                        model.addAttribute("error_msg", "Only image and pdf files are supported");
                        return "addBrochure";
                    }
                }

            }

            if (overwriteValue != 0) {

                // String document1="";
                List<String> documents1 = new ArrayList<>();
                String folder = "";

                int newbroFileId = filesofbrouchureService.getNewId();
                List<FilesofBrouchure> filesBroList = new ArrayList<>();
                List<String> addedlanguagesforOverride = new ArrayList<>();
                for (int i = 0; i < languageIds.size(); i++) {
                    documents1.clear();
                    folder = "";

                    if (languageIds.get(i) == 0) {
                        break;
                    }

                    Language language = lanService.getById(languageIds.get(i));
                    String langName = language.getLangName();

                    FilesofBrouchure fileBro = filesofbrouchureService.findByLanguageandVersion(language, version);

                    if (!brochures.get(i).isEmpty()) {
                        folder = CommonData.uploadBrouchure + brochureId + "/" + versionValue + "/" + "web" + "/"
                                + langName;

                        documents1 = ServiceUtility.UploadMediaFileAndCreateThumbnail(brochures.get(i), env, folder);

                    }

                    for (String testlan : addedlanguagesforOverride) {
                        if (testlan == langName) {
                            duplicatelangforOverride = true;
                        }
                    }
                    addedlanguagesforOverride.add(langName);

                    if (fileBro != null) {
                        fileBro.setLan(language);

                        if (!brochures.get(i).isEmpty()) {
                            fileBro.setWebPath(documents1.get(0));
                            fileBro.setThumbnailPath(documents1.get(1));
                        }

                        filesofbrouchureService.save(fileBro);

                    }

                    else {

                        if (!brochures.get(i).isEmpty()) {
                            filesBroList.add(new FilesofBrouchure(newbroFileId, ServiceUtility.getCurrentTime(),
                                    documents1.get(0), documents1.get(1), version, language));
                            newbroFileId = newbroFileId + 1;

                        } else {
                            webfileErrorforOverride = true;
                        }

                    }

                }
                if (webfileErrorforOverride == false && duplicatelangforOverride == false) {
                    version.setVersionPosterPath("");
                    verService.save(version);
                    brouchure.setTitle(title);
                    broService.save(brouchure);

                    filesofbrouchureService.saveAll(filesBroList);
                }

            }

            else {

                List<String> documents2 = new ArrayList<>();
                String folder2 = "";
                int newbroFileId = filesofbrouchureService.getNewId();
                List<FilesofBrouchure> filesBroList1 = new ArrayList<>();
                Version newVer = new Version();
                List<String> addedLanguages = new ArrayList<>();
                for (int i = 0; i < languageIds.size(); i++) {
                    documents2.clear();
                    folder2 = "";

                    if (languageIds.get(i) == 0) {
                        break;
                    }

                    String langName = lanService.getById(languageIds.get(i)).getLangName();

                    if (!brochures.get(i).isEmpty()) {

                        folder2 = CommonData.uploadBrouchure + brochureId + "/" + newVersionValue + "/" + "web" + "/"
                                + langName;

                        documents2 = ServiceUtility.UploadMediaFileAndCreateThumbnail(brochures.get(i), env, folder2);

                        for (String testlan : addedLanguages) {
                            if (testlan == langName) {
                                duplicatLanguage = true;
                            }
                        }
                        addedLanguages.add(langName);

                        filesBroList1.add(new FilesofBrouchure(newbroFileId, ServiceUtility.getCurrentTime(),
                                documents2.get(0), documents2.get(1), newVer, lanService.getById(languageIds.get(i))));
                        newbroFileId += 1;

                    }

                    else {
                        filesError = true;
                    }

                }

                if (filesError == false && duplicatLanguage == false) {

                    brouchure.setTitle(title);
                    brouchure.setPrimaryVersion(version.getBroVersion() + 1);
                    broService.save(brouchure);

                    newVer.setVerId(newVerId);
                    newVer.setBrouchure(brouchure);
                    newVer.setDateAdded(ServiceUtility.getCurrentTime());
                    newVer.setBroVersion(version.getBroVersion() + 1);
                    newVer.setVersionPosterPath("");
                    verService.save(newVer);

                    filesofbrouchureService.saveAll(filesBroList1);

                    brouchure = broService.findById(Integer.parseInt(brochureId));
                    verSet = brouchure.getVersions();
                    listofVersions = new ArrayList<>(verSet);
                    model.addAttribute("listofVersions", listofVersions);

                }

            }

        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            logger.error("Check path error", e);

            version = verRepository.findByBrouchureAndBroVersion(brouchure, brouchure.getPrimaryVersion());
            filesOfBroList = filesofbrouchureService.findByVersion(version);
            model.addAttribute("filesOfBroList", filesOfBroList);
            model.addAttribute("version", version);
            model.addAttribute("brouchure", brouchure);

            verSet = brouchure.getVersions();
            listofVersions = new ArrayList<>(verSet);
            Collections.sort(listofVersions, Version.SortByBroVersionTime);
            newlistofVesrion = new ArrayList<>();
            temp = 0;
            for (Version ver : listofVersions) {
                newlistofVesrion.add(ver);
                temp++;
                if (temp == 3)
                    break;
            }

            newfilesList = new ArrayList<>();
            for (Version ver1 : newlistofVesrion) {
                for (Language lan : languages) {
                    FilesofBrouchure filesBro = filesofbrouchureService.findByLanguageandVersion(lan, ver1);
                    if (filesBro != null) {
                        newfilesList.add(filesBro);
                    }
                }
            }

            model.addAttribute("newfilesList", newfilesList);

            model.addAttribute("listofVersions", newlistofVesrion);
            return "updateBrochure"; // need to add some error message
        }

        if (webfileErrorforOverride == true) {
            model.addAttribute("error_msg", "brouchure web file is required for new Language");
            return "updateBrochure";

        }

        if (duplicatLanguage == true) {
            model.addAttribute("error_msg", "Duplicate Languages are not allowed");
            return "updateBrochure";

        }

        if (duplicatelangforOverride == true) {
            model.addAttribute("error_msg", "Duplicate Languages are not allowed");
            return "updateBrochure";

        }

        if (filesError == true) {
            model.addAttribute("error_msg", "brouchure web file is required for new version");
            return "updateBrochure";

        }

        model.addAttribute("success_msg", CommonData.RECORD_UPDATE_SUCCESS_MSG);
        brouchure = broService.findById(Integer.parseInt(brochureId));
        version = verRepository.findByBrouchureAndBroVersion(brouchure, brouchure.getPrimaryVersion());
        filesOfBroList = filesofbrouchureService.findByVersion(version);
        model.addAttribute("filesOfBroList", filesOfBroList);
        model.addAttribute("version", version);
        model.addAttribute("brouchure", brouchure);
        verSet = brouchure.getVersions();
        listofVersions = new ArrayList<>(verSet);
        Collections.sort(listofVersions, Version.SortByBroVersionTime);
        newlistofVesrion = new ArrayList<>();
        temp = 0;
        for (Version ver : listofVersions) {
            newlistofVesrion.add(ver);
            temp++;
            if (temp == 3)
                break;
        }
        newfilesList = new ArrayList<>();
        for (Version ver1 : newlistofVesrion) {
            for (Language lan : languages) {
                FilesofBrouchure filesBro = filesofbrouchureService.findByLanguageandVersion(lan, ver1);
                if (filesBro != null) {
                    newfilesList.add(filesBro);
                }
            }
        }

        model.addAttribute("newfilesList", newfilesList);

        model.addAttribute("listofVersions", newlistofVesrion);

        List<Brouchure> brouchures = broService.findAll();
        List<Version> versions = new ArrayList<Version>();
        for (Brouchure bro : brouchures) {
            Version ver = verService.findByBrouchureAndPrimaryVersion(bro, bro.getPrimaryVersion());
            versions.add(ver);
        }
        Collections.sort(versions, Version.SortByBroVersionTime);

        model.addAttribute("brouchures", brouchures);
        model.addAttribute("versions", versions);

        return "updateBrochure";
    }

    @GetMapping("/researchPaper/edit/{id}")
    public String editResearchPaperGet(@PathVariable int id, HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        ResearchPaper researchPaper = researchPaperService.findById(id);

        if (researchPaper == null) {

            return "redirect:/addResearchPaper";
        }

        model.addAttribute("researchPaper", researchPaper);

        return "updateResearchPaper";
    }

    @PostMapping("/updateResearchPaper")
    public String updateResearchPaperPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("researchFile") MultipartFile researchFile) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String rseacrhPaperId = req.getParameter("researchPaperId");
        String title = req.getParameter("title");
        String desc = req.getParameter("description");

        ResearchPaper researchPaper = researchPaperService.findById(Integer.parseInt(rseacrhPaperId));

        if (researchPaper == null) {
            model.addAttribute("error_msg", "ResearchPaper doesn't exist");
            return "updateResearchPaper";
        }

        model.addAttribute("researchPaper", researchPaper);

        try {

            if (!researchFile.isEmpty()) {
                if (!ServiceUtility.checkFileExtensiononeFilePDF(researchFile)) { // throw error on extension
                    model.addAttribute("error_msg", "Only pdf file is required");
                    return "updateResearchPaper";
                }
            }

            researchPaper.setTitle(title);
            researchPaper.setDescription(desc);

            if (!researchFile.isEmpty()) {

                String folder = CommonData.uploadResearchPaper + researchPaper.getId();
                List<String> documents = ServiceUtility.UploadMediaFileAndCreateThumbnail(researchFile, env, folder);
                researchPaper.setResearchPaperPath(documents.get(0));
                researchPaper.setThumbnailPath(documents.get(1));

            }

            researchPaperService.save(researchPaper);

        } catch (Exception e) {
            logger.error("Exception while updating research paper: {} {} {}", title, desc, researchFile, e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("researchPaper", researchPaper);
            return "updateResearchPaper";
        }

        model.addAttribute("success_msg", CommonData.RECORD_UPDATE_SUCCESS_MSG);
        model.addAttribute("researchPaper", researchPaper);

        return "updateResearchPaper";
    }

    @GetMapping("/carousel/edit/{id}")
    public String editCarouselGet(@PathVariable int id, HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Carousel carousel = caroService.findById(id);

        if (carousel == null) {

            return "redirect:/addEvent";
        }

        model.addAttribute("carousels", carousel);

        return "updateCarousel";
    }

    @PostMapping("/updateCarousel")
    public String updateCaroUselPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("Image") MultipartFile files) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String carouselId = req.getParameter("carouselId");
        String eventName = req.getParameter("eventname");
        String desc = req.getParameter("description");

        Carousel carousel = caroService.findById(Integer.parseInt(carouselId));

        model.addAttribute("carousels", carousel);

        if (carousel == null) {
            model.addAttribute("error_msg", "Event doesn't exist");
            return "updateCarousel";
        }

        try {

            if (!files.isEmpty()) {
                if (!ServiceUtility.checkFileExtensionImage(files)) { // throw error on extension
                    model.addAttribute("error_msg", CommonData.JPG_PNG_EXT);
                    return "updateCarousel";
                }
            }

            carousel.setEventName(eventName);
            carousel.setDescription(desc);

            if (!files.isEmpty()) {

                String folder = CommonData.uploadCarousel + carousel.getId();
                String document = ServiceUtility.uploadMediaFile(files, env, folder);

                carousel.setPosterPath(document);

            }

            caroService.save(carousel);

        } catch (Exception e) {
            // TODO: handle exception
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("carousels", carousel);
            return "updateCarousel"; // need to add some error message
        }

        model.addAttribute("success_msg", CommonData.RECORD_UPDATE_SUCCESS_MSG);
        model.addAttribute("carousels", carousel);

        return "updateCarousel";
    }

    @GetMapping("/language/edit/{lanName}")
    public String editLanguageGet(HttpServletRequest req, @PathVariable(name = "lanName") String lanTemp, Model model,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        Language lan = lanService.getByLanName(lanTemp);

        if (lan == null) {
            return "redirect:/addLanguage";
        }

        model.addAttribute("language", lan);

        return "updateLanguage"; // need to accomdate view part
    }

    @PostMapping("/updateLanguage")
    public String updateLanguagePost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String languagename = req.getParameter("languageName");
        String lanIdInString = req.getParameter("lanId");
        int lanId = Integer.parseInt(lanIdInString);

        Language lan = lanService.getById(lanId);

        if (lan == null) {
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("language", lan);
            return "updateLanguage"; // accomodate view part
        }

        if (languagename == null) {

            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            model.addAttribute("language", lan);
            return "updateLanguage"; // accomodate view part
        }

        if (lanService.getByLanName(languagename) != null) {

            model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
            model.addAttribute("language", lan);
            return "updateLanguage"; // accomodate view part
        }

        String language_formatted = languagename.substring(0, 1).toUpperCase()
                + languagename.substring(1).toLowerCase();
        lan.setLangName(language_formatted);

        try {
            lanService.save(lan);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Update Language: {}", lan, e);
            model.addAttribute("language", lan);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "updateLanguage"; // accomodate view part
        }

        lan = lanService.getById(lanId);
        model.addAttribute("language", lan);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "updateLanguage"; // accomodate view part

    }

    @GetMapping("/domainReviewer")
    public String viewDomaineGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Role domain = roleService.findByname(CommonData.domainReviewerRole);

        List<UserRole> domains = usrRoleService.findAllByRole(domain);

        model.addAttribute("domains", domains);

        return "viewDomainReviewer";
    }

    @GetMapping("/qualityReviewer")
    public String viewQualityeGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        Role quality = roleService.findByname(CommonData.qualityReviewerRole);
        List<UserRole> qualities = usrRoleService.findAllByRole(quality);

        model.addAttribute("qualities", qualities);

        return "viewQualityReviewer";
    }

    @GetMapping("/masterTrainer")
    public String viewMasterTrainerGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        Role master = roleService.findByname(CommonData.masterTrainerRole);

        List<UserRole> masters = usrRoleService.findAllByRole(master);
        model.addAttribute("masters", masters);

        return "viewMasterTrainer";
    }

    @GetMapping("/downloadQuestion")
    public String PostQuestionaireGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);
        List<PostQuestionaire> postQuestionnaires = postQuestionService.findAll();

        model.addAttribute("postQuestionnaires", postQuestionnaires);

        return "viewQuestionnaire";
    }

    @GetMapping("/testimonialList")
    public String viewtestimonialListGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<Testimonial> test = new ArrayList<>();
        List<Testimonial> test1 = testService.findAll();
        for (Testimonial temp : test1) {
            if (temp.isApproved()) {
                test.add(temp);
            }
        }

        model.addAttribute("testimonials", test);

        return "testimonialList";
    }

    @GetMapping("/testimonial/edit/{id}")
    public String edittestimonialGet(HttpServletRequest req, @PathVariable int id, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Testimonial test = testService.findById(id);

        if (test == null) {

            return "redirect:/addTestimonial";
        }

        model.addAttribute("testimonials", test);

        return "updateTestimonial";
    }

    @PostMapping("/updateTestimonial")
    public String updateTestimonialPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("TestimonialVideo") MultipartFile file, @RequestParam("consent") MultipartFile consent) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);
        String testiId = req.getParameter("testimonialId");
        String name = req.getParameter("testimonialName");
        String desc = req.getParameter("desc");

        Testimonial test = testService.findById(Integer.parseInt(testiId));

        if (test == null) {

            model.addAttribute("error_msg", CommonData.TESTIMONIAL_NOT_ERROR);
            return "updateTestimonial";
        }

        if (!consent.isEmpty()) {
            if (!ServiceUtility.checkFileExtensionImage(consent)
                    && !ServiceUtility.checkFileExtensiononeFilePDF(consent)) {
                model.addAttribute("error_msg", "Pdf file is required");
                return "updateTestimonial";
            }
        }

        if (!file.isEmpty()) {
            if (!ServiceUtility.checkFileExtensionVideo(file)) { // throw error on extension
                model.addAttribute("error_msg", CommonData.VIDEO_FILE_EXTENSION_ERROR);
                return "updateTestimonial";
            }
        }

        test.setName(name);
        test.setDescription(desc);

        try {
            if (!file.isEmpty()) {
                String folder1 = CommonData.uploadDirectoryTestimonial + test.getTestimonialId();
                String document1 = ServiceUtility.uploadMediaFile(file, env, folder1);
                test.setFilePath(document1);
            }

            if (!consent.isEmpty()) {
                String folder2 = CommonData.uploadDirectoryTestimonial + test.getTestimonialId();
                List<String> documents = ServiceUtility.UploadMediaFileAndCreateThumbnail(consent, env, folder2);
                test.setConsentLetter(documents.get(0));
                test.setThumbnailPath(documents.get(1));
            }

            testService.save(test);

        }

        catch (Exception e) {
            // TODO: handle exception

            logger.error("Error in Update Testimonial: {}", test.getTestimonialId(), e);
            model.addAttribute("error_msg", CommonData.RECORD_ERROR);
            return "updateTestimonial"; // throw a error
        }

        model.addAttribute("testimonials", test);
        model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

        return "updateTestimonial";
    }

    @GetMapping("/addContributorRole")
    public String addContributorGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Language> languages = lanService.getAllLanguages();

        model.addAttribute("languages", languages);
        return "addContributorRole";
    }

    @PostMapping("/addContributorRole")
    public String addContributorPost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String lanName = req.getParameter("selectedLan");

        if (lanName == null) {

            return "redirect:/addContributorRole";
        }

        Language lan = lanService.getByLanName(lanName);

        if (lan == null) {

            return "redirect:/addContributorRole";
        }

        Role role = roleService.findByname(CommonData.contributorRole);
        List<UserRole> userRoles = usrRoleService.findByLanUser(lan, usr, role);

        if (!userRoles.isEmpty()) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);

            model.addAttribute("error_msg", CommonData.CONTRIBUTOR_ERROR);

            return "addContributorRole";
        }

        UserRole usrRole = new UserRole();
        usrRole.setCreated(ServiceUtility.getCurrentTime());
        usrRole.setUser(usr);
        usrRole.setRole(role);
        usrRole.setLanguage(lan);
        usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

        try {
            usrRoleService.save(usrRole);
            model.addAttribute("success_msg", CommonData.CONTRIBUTOR_ADDED_SUCCESS_MSG);
        } catch (Exception e) {
            model.addAttribute("error_msg", CommonData.CONTRIBUTOR_ERROR_MSG);
            // TODO Auto-generated catch block
            logger.error("Error in Add Contributor Role : {}", usrRole, e);
        }

        List<Language> languages = lanService.getAllLanguages();

        model.addAttribute("languages", languages);

        return "addContributorRole";
    }

    @GetMapping("/addExternalContributorRole")
    public String addExternalContributorGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Language> languages = lanService.getAllLanguages();

        model.addAttribute("languages", languages);
        return "addExternalContributorRole";
    }

    @PostMapping("/addExternalContributorRole")
    public String addExternalContributorPost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String lanName = req.getParameter("selectedLan");

        if (lanName == null) {

            return "redirect:/addExternalContributorRole";
        }

        Language lan = lanService.getByLanName(lanName);

        if (lan == null) {

            return "redirect:/addExternalContributorRole";
        }

        Role role = roleService.findByname(CommonData.externalContributorRole);
        List<UserRole> userRoles = usrRoleService.findByLanUser(lan, usr, role);
        if (!userRoles.isEmpty()) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);

            model.addAttribute("error_msg", CommonData.CONTRIBUTOR_ERROR);

            return "addExternalContributorRole";
        }

        UserRole usrRole = new UserRole();
        usrRole.setCreated(ServiceUtility.getCurrentTime());
        usrRole.setUser(usr);
        usrRole.setRole(role);
        usrRole.setLanguage(lan);
        usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

        try {
            usrRoleService.save(usrRole);
            model.addAttribute("success_msg", CommonData.CONTRIBUTOR_ADDED_SUCCESS_MSG);
        } catch (Exception e) {
            model.addAttribute("error_msg", CommonData.CONTRIBUTOR_ERROR_MSG);
            // TODO Auto-generated catch block
            logger.error("Error in Add External Contributor Role: {}", usrRole, e);
        }

        List<Language> languages = lanService.getAllLanguages();

        model.addAttribute("languages", languages);

        return "addExternalContributorRole";
    }

    @GetMapping("/addAdminRole")
    public String addAdminPost(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);
        model.addAttribute("languages", languages);

        return "addAdminRole";
    }

    @PostMapping("/addAdminRole")
    public String addAdminPost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String lanName = req.getParameter("selectedLan");
        String catName = req.getParameter("catSelected");

        Category cat = catService.findBycategoryname(catName);

        Language lan = lanService.getByLanName(lanName);

        if (cat == null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", "Please Try Again");

            return "addAdminRole";
        }

        if (lan == null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", "Please Try Again");

            return "addAdminRole";
        }

        Role role = roleService.findByname(CommonData.adminReviewerRole);

        if (usrRoleService.findByLanCatUser(lan, cat, usr, role) != null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);

            return "addAdminRole";
        }

        UserRole usrRole = new UserRole();
        usrRole.setCreated(ServiceUtility.getCurrentTime());
        usrRole.setUser(usr);
        usrRole.setRole(role);
        usrRole.setLanguage(lan);
        usrRole.setCategory(cat);
        usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

        try {
            usrRoleService.save(usrRole);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            model.addAttribute("error_msg", CommonData.ROLE_ERROR_MSG);
            logger.error("Error in Add Admin Role: {}", usrRole, e);
            // accommodate error message
        }

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        model.addAttribute("success_msg", CommonData.ADMIN_ADDED_SUCCESS_MSG);
        return "addAdminRole";
    }

    @GetMapping("/addDomainRole")
    public String addDomainGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        return "addDomainRole";
    }

    @PostMapping("/addDomainRole")
    public String addDomainPost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String lanName = req.getParameter("selectedLan");
        String catName = req.getParameter("catSelected");

        Category cat = catService.findBycategoryname(catName);

        Language lan = lanService.getByLanName(lanName);

        if (cat == null) {

            // throw error
            // model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", "Please try Again");

            return "addDomainRole";
        }

        if (lan == null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", "Please try Again");

            return "addDomainRole";
        }

        Role role = roleService.findByname(CommonData.domainReviewerRole);

        if (usrRoleService.findByLanCatUser(lan, cat, usr, role) != null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);

            return "addDomainRole";
        }

        int newConsultid = consultService.getNewConsultantId();
        Consultant local = new Consultant();
        local.setConsultantId(newConsultid);
        local.setDescription("null");
        local.setDateAdded(ServiceUtility.getCurrentTime());
        local.setUser(usr);

        Set<Consultant> consults = new HashSet<Consultant>();
        consults.add(local);

        UserRole usrRole = new UserRole();
        usrRole.setCreated(ServiceUtility.getCurrentTime());
        usrRole.setUser(usr);
        usrRole.setRole(role);
        usrRole.setCategory(cat);
        usrRole.setLanguage(lan);
        usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

        try {
            usrRoleService.save(usrRole);
            userService.addUserToConsultant(usr, consults);
            model.addAttribute("success_msg", "Request Submitted Sucessfully");
        } catch (Exception e) {
            // TODO Auto-generated catch block
            model.addAttribute("error_msg", CommonData.ROLE_REQUEST_ERROR);
            logger.error("Error in Add Domain Role: {} {} {}", usrRole, usr, consults, e);
            return "addDomainRole"; // accommodate error message
        }

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        return "addDomainRole";
    }

    @GetMapping("/addQualityRole")
    public String addQualityGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        return "addQualityRole";
    }

    @PostMapping("/addQualityRole")
    public String addQualityPost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String lanName = req.getParameter("selectedLan");
        String catName = req.getParameter("catSelected");

        Category cat = catService.findBycategoryname(catName);

        Language lan = lanService.getByLanName(lanName);

        if (cat == null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", "Please try Again");

            return "addQualityRole";
        }

        if (lan == null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", "Please try Again");

            return "addQualityRole";
        }

        Role role = roleService.findByname(CommonData.qualityReviewerRole);

        if (usrRoleService.findByLanCatUser(lan, cat, usr, role) != null) {

            List<Language> languages = lanService.getAllLanguages();
            List<Category> categories = catService.findAll();

            model.addAttribute("categories", categories);

            model.addAttribute("languages", languages);
            model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);

            return "addQualityRole";
        }

        UserRole usrRole = new UserRole();
        usrRole.setCreated(ServiceUtility.getCurrentTime());
        usrRole.setUser(usr);
        usrRole.setRole(role);
        usrRole.setCategory(cat);
        usrRole.setLanguage(lan);
        usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

        try {
            usrRoleService.save(usrRole);
            model.addAttribute("success_msg", CommonData.QUALITY_ADDED_SUCCESS_MSG);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            model.addAttribute("error_msg", CommonData.ROLE_REQUEST_ERROR);
            logger.error("Error in Add Quality Role:{}", usrRole, e);
            // accommodate error message
        }

        List<Language> languages = lanService.getAllLanguages();
        List<Category> categories = catService.findAll();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        return "addQualityRole";
    }

    @GetMapping("/addMasterTrainerRole")
    public String addMasterTrainerGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<IndianLanguage> languages = iLanService.findAll();

        model.addAttribute("userInfo", usr);
        model.addAttribute("languages", languages);

        if (usr.getProfilePic() == null) {
            model.addAttribute("error_msg", CommonData.ADD_PROFILE_PIC_CONSTRAINT);
            return "profileView";
        }

        Role role = roleService.findByname(CommonData.masterTrainerRole);

        if (!usrRoleService.findByRoleUser(usr, role).isEmpty()) {

            model.addAttribute("success_msg", "Request Already submitted for role");
            model.addAttribute("alredaySubmittedFlag", true);
            return "addMasterTrainerRole";
        }
        List<OrganizationRole> org_roles = organizationRoleService.findAll();

        model.addAttribute("org_roles", org_roles);

        model.addAttribute("alredaySubmittedFlag", false);
        return "addMasterTrainerRole";
    }

    @PostMapping("/addMasterTrainerRole")
    public String addMasterTrainerPost(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String mobileNumber = req.getParameter("phone");
        String address = req.getParameter("address");
        String organization = (req.getParameter("org"));
        String exp = req.getParameter("experience");
        String aadhar = req.getParameter("aadharNumber");
        String lang = req.getParameter("languages");
        String roleOrg = req.getParameter("newRole");

        List<OrganizationRole> org_roles = organizationRoleService.findAll();

        model.addAttribute("org_roles", org_roles);

        int userIndianMappingId = userIndianMappingService.getNewId();

        Set<UserIndianLanguageMapping> userIndianMapping = new HashSet<UserIndianLanguageMapping>();

        if (aadhar.length() != 12) {
            // throw error
            model.addAttribute("error_msg", "Invalid aadhar number");
            return "addMasterTrainerRole";
        }

        if (mobileNumber.length() != 10) {

            // throw error
            model.addAttribute("error_msg", "Invalid phone number");
            return "addMasterTrainerRole";
        }

        String[] lan = lang.split("&");
        for (String x : lan) {

            String[] y = x.split("_");
            for (int z = 1; z < y.length; z++) {

                IndianLanguage temp = iLanService.findByName(y[0]);

                UserIndianLanguageMapping tempUser = new UserIndianLanguageMapping();
                tempUser.setId(userIndianMappingId++);
                tempUser.setUser(usr);
                tempUser.setIndianlan(temp);

                for (char xx : y[z].toCharArray()) {

                    if (xx == 'r') {
                        tempUser.setRead(true);
                    } else if (xx == 's') {
                        tempUser.setSpeak(true);
                    }
                }

                userIndianMapping.add(tempUser);
            }

        }

        Role role = roleService.findByname(CommonData.masterTrainerRole);

        List<UserRole> userRoles = usrRoleService.findByRoleUser(usr, role);
        if (!userRoles.isEmpty()) {
            // throw error
            model.addAttribute("error_msg", "Error in submitting request");
            return "addMasterTrainerRole";
        }

        usr.setAadharNumber(Long.parseLong(aadhar));
        usr.setExperience(Integer.parseInt(exp));
        usr.setAddress(address);
        usr.setFirstName(name);
        usr.setOrganization(organization);
        usr.setAge(Integer.parseInt(age));
        usr.setPhone(Long.parseLong(mobileNumber));

        List<OrganizationRole> orgRolesList = organizationRoleService.findAll();
        boolean isRoleExist = orgRolesList.stream().anyMatch(o -> o.getRole().equals(roleOrg));
        OrganizationRole r;
        if (isRoleExist) {
            r = organizationRoleService.getByRole(roleOrg);
        } else {
            r = new OrganizationRole();
            r.setDateAdded(ServiceUtility.getCurrentTime());
            r.setRole(roleOrg);
            r.setRoleId(organizationRoleService.getnewRoleId());
            organizationRoleService.save(r);
        }
        usr.setOrgRolev(r);
        userService.save(usr);
        try {

            userService.addUserToUserIndianMapping(usr, userIndianMapping);

            UserRole usrRole = new UserRole();
            usrRole.setCreated(ServiceUtility.getCurrentTime());
            usrRole.setUser(usr);
            usrRole.setRole(role);
            usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

            usrRoleService.save(usrRole);

            model.addAttribute("success_msg", "Request submitted for role successfully");

        } catch (Exception e) {
            // TODO: handle exception

            model.addAttribute("error_msg", "Error in submitting request");
            // throw error
        }

        model.addAttribute("userInfo", usr);
        model.addAttribute("success_msg", "Request submitted for role successfully");

        return "addMasterTrainerRole";
    }

    @GetMapping("/approveRole")
    public String approveRoleGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Role contributor = roleService.findByname(CommonData.contributorRole);
        Role admin = roleService.findByname(CommonData.adminReviewerRole);
        Role master = roleService.findByname(CommonData.masterTrainerRole);
        Role quality = roleService.findByname(CommonData.qualityReviewerRole);
        Role domain = roleService.findByname(CommonData.domainReviewerRole);
        Role external = roleService.findByname(CommonData.externalContributorRole);

        List<UserRole> adminReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(admin, false, false);
        List<UserRole> masterTrainer = usrRoleService.findAllByRoleAndStatusAndRevoked(master, false, false);
        List<UserRole> qualityReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(quality, false, false);
        List<UserRole> contributorReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(contributor, false, false);
        List<UserRole> domainReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(domain, false, false);
        List<UserRole> externalUser = usrRoleService.findAllByRoleAndStatusAndRevoked(external, false, false);

        int countAdminreviewer = adminReviewer.size();
        int countMasterTrainer = masterTrainer.size();
        int countQualityReviewer = qualityReviewer.size();
        int countContributorReviewer = contributorReviewer.size();
        int countDomainReviwer = domainReviewer.size();
        int countExternalUser = externalUser.size();

        model.addAttribute("countAdminreviewer", countAdminreviewer);
        model.addAttribute("countMasterTrainer", countMasterTrainer);
        model.addAttribute("countQualityReviewer", countQualityReviewer);
        model.addAttribute("countContributorReviewer", countContributorReviewer);
        model.addAttribute("countDomainReviwer", countDomainReviwer);
        model.addAttribute("countExternalUser", countExternalUser);

        model.addAttribute("userInfoAdmin", adminReviewer);
        model.addAttribute("userInfoQuality", qualityReviewer);
        model.addAttribute("userInfoContributor", contributorReviewer);
        model.addAttribute("userInfoMaster", masterTrainer);
        model.addAttribute("userInfoDomain", domainReviewer);
        model.addAttribute("userInfoExternal", externalUser);

        return "approveRole";
    }

    @GetMapping("/assignContributor/edit/{id}")
    public String editAssignContributor(HttpServletRequest req, @PathVariable Long id, Model model,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        Role role = roleService.findByname(CommonData.contributorRole);
        List<UserRole> userRoles = usrRoleService.findAllByRoleAndStatusAndRevoked(role, true, false);

        model.addAttribute("userByContributors", userRoles);
        model.addAttribute("userInfo", usr);

        return "updateAssignContributor";
    }

    @GetMapping("/assignTutorialToContributor")
    public String assignTutorialToContributoreGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", getUser(principal));

        Role role = roleService.findByname(CommonData.contributorRole);
        Role role1 = roleService.findByname(CommonData.externalContributorRole);

        List<ContributorAssignedTutorial> userRoles = conRepo.findAll();
        List<ContributorAssignedTutorial> userRoles1 = new ArrayList<>();
        for (ContributorAssignedTutorial temp : userRoles) {
            if (temp.getTopicCatId().getCat().isStatus() && temp.getTopicCatId().getTopic().isStatus()) {
                userRoles1.add(temp);
            }
        }

        List<UserRole> userRolesTemp = usrRoleService.findAllByRoleAndStatusAndRevoked(role, true, false);
        userRolesTemp.addAll(usrRoleService.findAllByRoleAndStatusAndRevoked(role1, true, false));
        Collections.sort(userRolesTemp);
        LinkedHashSet<User> userRolesUniqueTemp = new LinkedHashSet<>();
        for (UserRole x : userRolesTemp) {
            userRolesUniqueTemp.add(x.getUser());
        }
        model.addAttribute("userByContributors", userRolesUniqueTemp);
        model.addAttribute("userByContributorsAssigned", userRoles1);

        return "assignContributorList";
    }

    @PostMapping("/assignTutorialToContributor")
    public String assignTutorialToContributorePost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(name = "contributorName") String contributorName,
            @RequestParam(name = "languageName") String lanName,
            @RequestParam(name = "contributorCategory") String catName,
            @RequestParam(name = "inputTopic") String[] topics) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Role role = roleService.findByname(CommonData.contributorRole);
        Role role1 = roleService.findByname(CommonData.externalContributorRole);

        List<ContributorAssignedTutorial> userRoles = conRepo.findAll();
        List<ContributorAssignedTutorial> userRoles1 = new ArrayList<>();
        for (ContributorAssignedTutorial temp : userRoles) {
            if (temp.getTopicCatId().getCat().isStatus() && temp.getTopicCatId().getTopic().isStatus()) {
                userRoles1.add(temp);
            }
        }

        List<UserRole> userRolesTemp = usrRoleService.findAllByRoleAndStatusAndRevoked(role, true, false);
        userRolesTemp.addAll(usrRoleService.findAllByRoleAndStatusAndRevoked(role1, true, false));
        Collections.sort(userRolesTemp);
        LinkedHashSet<User> userRolesUniqueTemp = new LinkedHashSet<>();
        for (UserRole x : userRolesTemp) {
            userRolesUniqueTemp.add(x.getUser());
        }

        for (UserRole x : userRolesTemp) {
            userRolesUniqueTemp.add(x.getUser());
        }

        model.addAttribute("userByContributorsAssigned", userRoles1);

        model.addAttribute("userByContributors", userRolesUniqueTemp);

        Language lan = lanService.getByLanName(lanName);
        Category cat = catService.findByid(Integer.parseInt(catName));
        User userAssigned = userService.findByUsername(contributorName);

        int conNewId = conRepo.getNewId();
        int conMutliUserNewId = conMultiUser.getNewId();

        for (String topic : topics) {

            Topic localtopic = topicService.findById(Integer.parseInt(topic));
            if (localtopic != null) {

                TopicCategoryMapping topicCat = topicCatService.findAllByCategoryAndTopic(cat, localtopic);

                ContributorAssignedTutorial x = conRepo.findByTopicCatAndLanViewPart(topicCat, lan);

                if (x == null) {

                    ContributorAssignedTutorial temp = new ContributorAssignedTutorial(conNewId++,
                            ServiceUtility.getCurrentTime(), topicCat, lan);
                    conRepo.save(temp);
                    ContributorAssignedMultiUserTutorial multiUser = new ContributorAssignedMultiUserTutorial(
                            conMutliUserNewId++, ServiceUtility.getCurrentTime(), userAssigned, temp);
                    conMultiUser.save(multiUser);

                } else {

                    ContributorAssignedMultiUserTutorial multiUser = new ContributorAssignedMultiUserTutorial(
                            conMutliUserNewId++, ServiceUtility.getCurrentTime(), userAssigned, x);
                    conMultiUser.save(multiUser);

                }

            } else {

                model.addAttribute("error_msg", CommonData.CONTRIBUTOR_TOPIC_ERROR);
                return "assignContributorList";
            }
        }

        userRoles = conRepo.findAll();

        userRoles1 = new ArrayList<>();
        for (ContributorAssignedTutorial temp : userRoles) {
            if (temp.getTopicCatId().getCat().isStatus() && temp.getTopicCatId().getTopic().isStatus()) {
                userRoles1.add(temp);
            }
        }

        userRolesTemp = usrRoleService.findAllByRoleAndStatusAndRevoked(role, true, false);

        for (UserRole x : userRolesTemp) {
            userRolesUniqueTemp.add(x.getUser());
        }

        model.addAttribute("userByContributorsAssigned", userRoles1);

        model.addAttribute("userByContributors", userRolesUniqueTemp);

        model.addAttribute("success_msg", CommonData.CONTRIBUTOR_ASSIGNED_TUTORIAL);

        return "assignContributorList";
    }

    @GetMapping("/uploadTutorial")
    public String uploadTutorialGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<String> catName = new ArrayList<String>();
        List<ContributorAssignedMultiUserTutorial> con = conMultiUser.getAllByuser(usr);

        for (ContributorAssignedMultiUserTutorial temp : con) {
            ContributorAssignedTutorial conTemp = conRepo.findById(temp.getConAssignedTutorial().getId());
            catName.add(conTemp.getTopicCatId().getCat().getCatName());
        }
        HashSet<String> uniqueCatName = new HashSet<String>(catName);
        List<String> categories = new ArrayList<String>(uniqueCatName);
        Collections.sort(categories);
        model.addAttribute("contributorTutorial", categories);
        return "uploadTutorialPre";
    }

    private List<Category> getPreReqCategories(Language lan) {
        List<Category> categories = new ArrayList<Category>();
        List<Category> preReqCategories = new ArrayList<Category>();
        Set<Category> preReqCatSet = new HashSet<Category>();
        categories = catService.findAll();
        for (Category category : categories) {
            if (category.isStatus()) {
                List<TopicCategoryMapping> tcm = topicCatService.findAllByCategory(category);
                List<ContributorAssignedTutorial> con_t = new ArrayList<ContributorAssignedTutorial>();
                if (!tcm.isEmpty()) {
                    if (lan == null) {
                        con_t = conRepo.findAllByTopicCat(tcm);
                    } else {
                        con_t = conRepo.findAllByTopicCatAndLan(tcm, lan);
                    }
                    for (ContributorAssignedTutorial c : con_t) {
                        List<Tutorial> tut = tutService.findAllByContributorAssignedTutorial(c);
                        if (tut != null && tut.size() > 0) {
                            if (tut.get(0).isStatus()) {
                                preReqCatSet.add(category);
                            }
                        }

                    }
                }

            }
        }
        for (Category c : preReqCatSet) {
            preReqCategories.add(c);
        }
        return preReqCategories;
    }

    @PostMapping("/uploadTutorial")
    public String uploadTutorialPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(value = "categoryName") String categoryName, @RequestParam(name = "inputTopic") int topicId,
            @RequestParam(name = "inputLanguage") String langName) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        Language lan = lanService.getByLanName(langName);
        model.addAttribute("language", lan);
        List<Category> categories = getPreReqCategories(lan);
        model.addAttribute("categories", categories);
        Category cat = catService.findBycategoryname(categoryName);
        model.addAttribute("category", cat);
        Topic topic = topicService.findById(topicId);
        model.addAttribute("topic", topic);

        TopicCategoryMapping topicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);

        Tutorial tutorial = null;

        ContributorAssignedTutorial conTutorial = conRepo.findByTopicCatAndLanViewPart(topicCat, lan);

        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorial(conTutorial);

        setCompStatus(model, tutorials);

        setEngLangStatus(model, lan);

        if (!lan.getLangName().equalsIgnoreCase("english")) {

            boolean goAhead = false;
            Language englishLan = lanService.getByLanName("english");
            ContributorAssignedTutorial conTutorialforEnglish = conRepo.findByTopicCatAndLanViewPart(topicCat,
                    englishLan);
            List<Tutorial> tutorialsForEnglish = tutService.findAllByContributorAssignedTutorial(conTutorialforEnglish);

            if (tutorialsForEnglish.size() > 0) {

                goAhead = true;

            }

            if (goAhead == false) {
                model.addAttribute("error_msg", "Please add English version of tutorial first.");
                model.addAttribute("disable", true);
                return "uploadTutorialPost";
            }

        }
        if (!tutorials.isEmpty()) {

            setVideoInfo(model, tutorials);
            setCompComment(model, tutorials, comService);
            for (Tutorial local : tutorials) {
                tutorial = local;

                List<Integer> scriptversionList = getApiVersion(scriptmanager_api, cat.getCategoryId(),
                        tutorial.getTutorialId(), lan.getLanId());
                String sm_url = "";
                if (scriptversionList != null && scriptversionList.size() > 0) {
                    sm_url = setScriptManagerUrl(model, scriptmanager_url, scriptmanager_path, tutorial, topic, lan,
                            cat, scriptversionList.get(0));

                }

                String sm_url_Ver2 = setScriptManagerUrl(model, scriptmanager_url, scriptmanager_path, tutorial, topic,
                        lan, cat, 2);
                model.addAttribute("sm_url", sm_url);
                model.addAttribute("sm_url_Ver2", sm_url_Ver2);
                model.addAttribute("tutorial", local);
                if (local.getPreRequisticStatus() != 0) {
                    model.addAttribute("pre_req", setPreReqInfo(local));
                }
            }
        }
        return "uploadTutorialPost";
    }

    @GetMapping("listTutorialForContributorReview")
    public String listContributorReviewTutorialGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Set<Tutorial> tutorials = new HashSet<Tutorial>();

        List<ContributorAssignedMultiUserTutorial> conTutorials = conMultiUser.getAllByuser(usr);

        for (ContributorAssignedMultiUserTutorial conMultiTemp : conTutorials) {

            ContributorAssignedTutorial conTemp = conRepo.findById(conMultiTemp.getConAssignedTutorial().getId());

            tutorials.addAll(tutService.findAllByContributorAssignedTutorial(conTemp));
        }

        model.addAttribute("tutorial", tutorials);

        return "listTutorialContributorReviwer";

    }

    @GetMapping("Contributor/review/{catName}/{topicName}/{language}")
    public String listContributorReviewTutorialGet(HttpServletRequest req, @PathVariable(name = "catName") String cat,
            @PathVariable(name = "topicName") String topic, @PathVariable(name = "language") String lan, Model model,
            Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<Category> categories = catService.findAll();
        model.addAttribute("categories", categories);
        model.addAttribute("userInfo", usr);

        Category catName = catService.findBycategoryname(cat);
        Topic topicName = topicService.findBytopicName(topic);
        Language lanName = lanService.getByLanName(lan);
        TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
        ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);

        if (catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {
            return "redirect:/listTutorialForContributorReview";
        }

        List<Tutorial> findAllByContributorAssignedTutorial = tutService.findAllByContributorAssignedTutorial(conTut);

        if (findAllByContributorAssignedTutorial == null || findAllByContributorAssignedTutorial.size() == 0) {

            // throw a error
            model.addAttribute("error_msg", CommonData.STATUS_ERROR);
            model.addAttribute("tutorialNotExist", "Bad request"); // throw proper error
            return "redirect:/listTutorialForContributorReview";

        }
        Tutorial tutorial = findAllByContributorAssignedTutorial.get(0);

        model.addAttribute("statusOutline", CommonData.tutorialStatus[tutorial.getOutlineStatus()]);
        model.addAttribute("statusScript", CommonData.tutorialStatus[tutorial.getScriptStatus()]);
        model.addAttribute("statusSlide", CommonData.tutorialStatus[tutorial.getSlideStatus()]);
        model.addAttribute("statusVideo", CommonData.tutorialStatus[tutorial.getVideoStatus()]);
        model.addAttribute("statusKeyword", CommonData.tutorialStatus[tutorial.getKeywordStatus()]);
        model.addAttribute("statusPreReq", CommonData.tutorialStatus[tutorial.getPreRequisticStatus()]);

        model.addAttribute("tutorial", tutorial);

        if (tutorial.getVideo() != null) {

            IContainer container = IContainer.make();

            IStream stream = container.getStream(0);
            IStreamCoder coder = stream.getStreamCoder();

            model.addAttribute("FileWidth", coder.getWidth());
            model.addAttribute("FileHeight", coder.getHeight());

            model.addAttribute("fileSizeInMB", container.getFileSize() / 1000000);
            model.addAttribute("FileDurationInSecond", container.getDuration() / 1000000);

            container.close();

        }

        List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO, tutorial);
        List<Comment> comScript = comService.getCommentBasedOnTutorialType(CommonData.SCRIPT, tutorial);
        List<Comment> comSlide = comService.getCommentBasedOnTutorialType(CommonData.SLIDE, tutorial);

        List<Comment> comKeyword = comService.getCommentBasedOnTutorialType(CommonData.KEYWORD, tutorial);
        List<Comment> comPreRequistic = comService.getCommentBasedOnTutorialType(CommonData.PRE_REQUISTIC, tutorial);
        List<Comment> comOutline = comService.getCommentBasedOnTutorialType(CommonData.OUTLINE, tutorial);

        model.addAttribute("comOutline", comOutline);
        model.addAttribute("comScript", comScript);
        model.addAttribute("comSlide", comSlide);
        model.addAttribute("comVideo", comVideo);
        model.addAttribute("comKeyword", comKeyword);
        model.addAttribute("comPreReq", comPreRequistic);

        model.addAttribute("category", tutorial.getConAssignedTutorial().getTopicCatId().getCat());
        model.addAttribute("topic", tutorial.getConAssignedTutorial().getTopicCatId().getTopic());
        model.addAttribute("language", tutorial.getConAssignedTutorial().getLan());

        if (!tutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {
            model.addAttribute("otherLan", false);
        } else {
            model.addAttribute("otherLan", true);
        }

        return "uploadTutorialPost";

    }

    @GetMapping("listTutorialForAdminReview")
    public String listAdminReviewTutorialGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        HashSet<Tutorial> toReview = new HashSet<>();
        HashSet<Tutorial> reviewed = new HashSet<>();
        Role role = roleService.findByname(CommonData.adminReviewerRole);

        List<UserRole> userRoles = usrRoleService.findByRoleUser(usr, role);
        List<TopicCategoryMapping> localMap = topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

        List<ContributorAssignedTutorial> conTutorials = conRepo.findByTopicCatLan(localMap, userRoles);

        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList(conTutorials);
        for (Tutorial temp : tutorials) {
            int videoStatus = temp.getVideoStatus();
            if (videoStatus == CommonData.ADMIN_STATUS) {
                toReview.add(temp);

            } else if (videoStatus > CommonData.ADMIN_STATUS) {
                reviewed.add(temp);
            }

        }

        model.addAttribute("tutorialToReview", toReview);
        model.addAttribute("tutorialReviewed", reviewed);
        return "listTutorialAdminReviwer";
    }

    @GetMapping("adminreview/review/{tutorialId}")
    public String listAdminReviewTutorialGet(HttpServletRequest req, @PathVariable int tutorialId, Model model,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Tutorial tutorial1 = tutService.getById(tutorialId);
        if (tutorial1 == null) {

            return "redirect:/listTutorialForAdminReview";
        }

        Category catName = tutorial1.getConAssignedTutorial().getTopicCatId().getCat();
        Topic topicName = tutorial1.getConAssignedTutorial().getTopicCatId().getTopic();
        Language lanName = tutorial1.getConAssignedTutorial().getLan();
        TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
        ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);

        if (catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {

            return "redirect:/listTutorialForAdminReview";
        }
        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorial(conTut);

        if (tutorial1.getVideoStatus() != CommonData.ADMIN_STATUS) {

            return "redirect:/listTutorialForAdminReview";
        }

        model.addAttribute("category", tutorial1.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
        model.addAttribute("topic", tutorial1.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
        model.addAttribute("language", tutorial1.getConAssignedTutorial().getLan().getLangName());
        model.addAttribute("tutorial", tutorial1);

        List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO, tutorial1);
        model.addAttribute("comVideo", comVideo);
        setVideoInfo(model, tutorials);

        return "addContentAdminReview";

    }

    @GetMapping("listTutorialForDomainReview")
    public String listDomainReviewTutorialGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        HashSet<Tutorial> toReview = new HashSet<>();
        HashSet<Tutorial> published = new HashSet<>();
        Role role = roleService.findByname(CommonData.domainReviewerRole);

        List<UserRole> userRoles = usrRoleService.findByRoleUser(usr, role);
        List<TopicCategoryMapping> localMap = topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

        List<ContributorAssignedTutorial> conTutorials = conRepo.findByTopicCatLan(localMap, userRoles);

        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList(conTutorials);
        for (Tutorial temp : tutorials) {

            if (temp.getOutlineStatus() > CommonData.DOMAIN_STATUS && temp.getScriptStatus() > CommonData.DOMAIN_STATUS
                    && temp.getSlideStatus() > CommonData.DOMAIN_STATUS
                    && temp.getKeywordStatus() > CommonData.DOMAIN_STATUS
                    && temp.getVideoStatus() > CommonData.DOMAIN_STATUS
                    && temp.getPreRequisticStatus() > CommonData.DOMAIN_STATUS) {

                published.add(temp);
            } else {
                if (temp.getOutlineStatus() == CommonData.DOMAIN_STATUS
                        || temp.getScriptStatus() == CommonData.DOMAIN_STATUS
                        || temp.getSlideStatus() == CommonData.DOMAIN_STATUS
                        || temp.getKeywordStatus() == CommonData.DOMAIN_STATUS
                        || temp.getVideoStatus() == CommonData.DOMAIN_STATUS
                        || temp.getPreRequisticStatus() == CommonData.DOMAIN_STATUS) {

                    toReview.add(temp);
                }

            }
        }

        model.addAttribute("tutorialToReview", toReview);
        model.addAttribute("tutorialReviewed", published);
        return "listTutorialDomainReviewer";
    }

    @GetMapping("domainreview/review/{tutorialId}")
    public String listDomainReviewTutorialGet(HttpServletRequest req, @PathVariable int tutorialId, Model model,
            Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Tutorial tutorial1 = tutService.getById(tutorialId);

        if (tutorial1 == null) {
            return "redirect:/listTutorialForDomainReview";
        }

        Category category = tutorial1.getConAssignedTutorial().getTopicCatId().getCat();
        Topic topic = tutorial1.getConAssignedTutorial().getTopicCatId().getTopic();
        Language language = tutorial1.getConAssignedTutorial().getLan();
        TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(category, topic);
        ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, language);
        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorial(conTut);

        model.addAttribute("category", tutorial1.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
        model.addAttribute("topic", tutorial1.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
        model.addAttribute("language", tutorial1.getConAssignedTutorial().getLan().getLangName());

        if (category == null || topic == null || language == null || topicCatMap == null || conTut == null) {
            return "redirect:/listTutorialForDomainReview";
        }

        setCompComment(model, tutorials, comService);
        setCompStatus(model, tutorials);
        setVideoInfo(model, tutorials);
        model.addAttribute("tutorial", tutorial1);
        setEngLangStatus(model, language);

        List<Integer> scriptversionList = getApiVersion(scriptmanager_api, category.getCategoryId(),
                tutorial1.getTutorialId(), language.getLanId());
        String sm_url = "";
        if (scriptversionList != null && scriptversionList.size() > 0) {
            sm_url = setScriptManagerUrl(model, scriptmanager_url, scriptmanager_path, tutorial1, topic, language,
                    category, scriptversionList.get(0));

        }
        model.addAttribute("sm_url", sm_url);

        String sm_url_Ver2 = setScriptManagerUrl(model, scriptmanager_url, scriptmanager_path, tutorial1, topic,
                language, category, 2);

        model.addAttribute("sm_url_Ver2", sm_url_Ver2);

        return "addContentDomainReview";

    }

    @GetMapping("listTutorialForQualityReview")
    public String listQualityReviewTutorialGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        HashSet<Tutorial> toReview = new HashSet<>();
        HashSet<Tutorial> published = new HashSet<>();
        Role role = roleService.findByname(CommonData.qualityReviewerRole);

        List<UserRole> userRoles = usrRoleService.findByRoleUser(usr, role);
        List<TopicCategoryMapping> localMap = topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

        List<ContributorAssignedTutorial> conTutorials = conRepo.findByTopicCatLan(localMap, userRoles);

        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList(conTutorials);
        for (Tutorial temp : tutorials) {

            if (temp.getOutlineStatus() == CommonData.PUBLISH_STATUS
                    && temp.getScriptStatus() == CommonData.PUBLISH_STATUS
                    && temp.getSlideStatus() == CommonData.PUBLISH_STATUS
                    && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS
                    && temp.getVideoStatus() == CommonData.PUBLISH_STATUS
                    && temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

                published.add(temp);
            }

            else {
                if (temp.getOutlineStatus() > CommonData.DOMAIN_STATUS
                        || temp.getScriptStatus() > CommonData.DOMAIN_STATUS
                        || temp.getSlideStatus() > CommonData.DOMAIN_STATUS
                        || temp.getKeywordStatus() > CommonData.DOMAIN_STATUS
                        || temp.getVideoStatus() > CommonData.DOMAIN_STATUS
                        || temp.getPreRequisticStatus() > CommonData.DOMAIN_STATUS) {

                    toReview.add(temp);
                }

            }

        }

        model.addAttribute("tutorialToReview", toReview);
        model.addAttribute("tutorialReviewed", published);

        return "listTutorialQualityReviewer";

    }

    @GetMapping("tutorialToPublish")
    public String tutorialToPublishGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        HashSet<Tutorial> published = new HashSet<>();
        Role role = roleService.findByname(CommonData.qualityReviewerRole);

        List<UserRole> userRoles = usrRoleService.findByRoleUser(usr, role);
        List<TopicCategoryMapping> localMap = topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

        List<ContributorAssignedTutorial> conTutorials = conRepo.findByTopicCatLan(localMap, userRoles);

        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList(conTutorials);
        for (Tutorial temp : tutorials) {

            if (temp.getOutlineStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getScriptStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getSlideStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getKeywordStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getVideoStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getPreRequisticStatus() >= CommonData.WAITING_PUBLISH_STATUS) {

                published.add(temp);
            }

        }

        model.addAttribute("tutorial", published);

        return "listTutorialPublishQualityReviwer";

    }

    @GetMapping("publish/{id}")
    public String publishTutorialGet(HttpServletRequest req, @PathVariable int id, Model model, Principal principal,
            RedirectAttributes redirectAttributes) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);
        Tutorial tutorial = tutService.getById(id);

        if (tutorial == null) {
            model.addAttribute("tutorialNotExist", "Bad request"); // throw proper error
            return "redirect:/tutorialToPublish";

        }

        tutorial.setKeywordStatus(CommonData.PUBLISH_STATUS);
        tutorial.setOutlineStatus(CommonData.PUBLISH_STATUS);
        tutorial.setSlideStatus(CommonData.PUBLISH_STATUS);
        tutorial.setScriptStatus(CommonData.PUBLISH_STATUS);
        tutorial.setPreRequisticStatus(CommonData.PUBLISH_STATUS);
        tutorial.setVideoStatus(CommonData.PUBLISH_STATUS);
        tutorial.setStatus(true);
        taskProcessingService.addUpdateDeleteTutorial(tutorial, CommonData.ADD_DOCUMENT);

        tutService.save(tutorial);
        model.addAttribute("success_msg", CommonData.PUBLISHED_SUCCESS);

        HashSet<Tutorial> published = new HashSet<>();
        Role role = roleService.findByname(CommonData.qualityReviewerRole);

        List<UserRole> userRoles = usrRoleService.findByRoleUser(usr, role);
        List<TopicCategoryMapping> localMap = topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

        List<ContributorAssignedTutorial> conTutorials = conRepo.findByTopicCatLan(localMap, userRoles);

        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList(conTutorials);
        for (Tutorial temp : tutorials) {

            if (temp.getOutlineStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getScriptStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getSlideStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getKeywordStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getVideoStatus() >= CommonData.WAITING_PUBLISH_STATUS
                    && temp.getPreRequisticStatus() >= CommonData.WAITING_PUBLISH_STATUS) {

                published.add(temp);
            }

        }

        model.addAttribute("tutorial", published);

        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");

        try {

            Credential credential = Auth.authorize(scopes, "uploadvideo");

            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).build();

            Video videoObjectDefiningMetadata = new Video();

            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus("public");
            videoObjectDefiningMetadata.setStatus(status);

            VideoSnippet snippet = new VideoSnippet();

            Calendar cal = Calendar.getInstance();
            snippet.setTitle("sample" + cal.getTime());
            snippet.setDescription(
                    "Video uploaded via YouTube Data API V3 using the Java library " + "on " + cal.getTime());

            List<String> tags = new ArrayList<String>();
            tags.add("test");
            tags.add("example");
            tags.add("java");
            tags.add("YouTube Data API V3");
            tags.add("erase me");
            snippet.setTags(tags);

            videoObjectDefiningMetadata.setSnippet(snippet);

            InputStream sam = new FileInputStream(
                    env.getProperty("spring.applicationexternalPath.name") + tutorial.getVideo());

            InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT, sam);

            YouTube.Videos.Insert videoInsert = youtube.videos().insert("snippet,statistics,status",
                    videoObjectDefiningMetadata, mediaContent);

            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                @Override
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                    case INITIATION_STARTED:
                        logger.info("Initiation Started");
                        break;
                    case INITIATION_COMPLETE:
                        logger.info("Initiation Completed");
                        break;
                    case MEDIA_IN_PROGRESS:
                        logger.info("Upload in progress");
                        logger.info("Upload percentage: {}", uploader.getProgress());
                        break;
                    case MEDIA_COMPLETE:
                        logger.info("Upload Completed!");
                        break;
                    case NOT_STARTED:
                        logger.info("Upload Not Started!");
                        break;
                    }
                }
            };
            uploader.setProgressListener(progressListener);

            Video returnedVideo = videoInsert.execute();

            logger.info("\n================== Returned Video ==================\n");
            logger.info("  - Id: {}", returnedVideo.getId());
            logger.info("  - Title: {}", returnedVideo.getSnippet().getTitle());
            logger.info("  - Tags: {}", returnedVideo.getSnippet().getTags());
            logger.info("  - Privacy Status:{} ", returnedVideo.getStatus().getPrivacyStatus());
            logger.info("  - Video Count: {}", returnedVideo.getStatistics().getViewCount());

        } catch (GoogleJsonResponseException e) {
            logger.error("GoogleJsonResponseException code: {} {} ", e.getDetails().getCode(),
                    e.getDetails().getMessage(), e);

        } catch (IOException e) {
            logger.error("IOException:{} ", e.getMessage(), e);

        } catch (Throwable t) {
            logger.error("Throwable:{} ", t.getMessage(), t);

        }

        return "listTutorialPublishQualityReviwer";

    }

    @GetMapping("qualityreview/review/{tutorialId}")
    public String listQualityReviewTutorialGet(HttpServletRequest req, @PathVariable int tutorialId, Model model,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Tutorial tutorial1 = tutService.getById(tutorialId);

        Category category = tutorial1.getConAssignedTutorial().getTopicCatId().getCat();
        Topic topic = tutorial1.getConAssignedTutorial().getTopicCatId().getTopic();
        Language language = tutorial1.getConAssignedTutorial().getLan();
        TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(category, topic);
        ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, language);

        if (category == null || topic == null || language == null || topicCatMap == null || conTut == null) {
            return "redirect:/listTutorialForQualityReview";
        }

        List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorial(conTut);
        if (tutorials == null) {
            return "redirect:/listTutorialForQualityReview";
        }

        model.addAttribute("category", tutorial1.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
        model.addAttribute("topic", tutorial1.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
        model.addAttribute("language", tutorial1.getConAssignedTutorial().getLan().getLangName());

        model.addAttribute("tutorial", tutorial1);
        setCompComment(model, tutorials, comService);
        setCompStatus(model, tutorials);
        setVideoInfo(model, tutorials);
        setEngLangStatus(model, language);

        List<Integer> scriptversionList = getApiVersion(scriptmanager_api, category.getCategoryId(),
                tutorial1.getTutorialId(), language.getLanId());
        String sm_url = "";
        if (scriptversionList != null && scriptversionList.size() > 0) {
            sm_url = setScriptManagerUrl(model, scriptmanager_url, scriptmanager_path, tutorial1, topic, language,
                    category, scriptversionList.get(0));

        }
        model.addAttribute("sm_url", sm_url);
        String sm_url_Ver2 = setScriptManagerUrl(model, scriptmanager_url, scriptmanager_path, tutorial1, topic,
                language, category, 2);
        model.addAttribute("sm_url_Ver2", sm_url_Ver2);

        return "addContentQualityReview";

    }

    @GetMapping("/trainerProfile")
    public String profileMasterTrainerGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        return "traineeView";

    }

    @GetMapping("/masterTrainerOperation")
    public String MasterTrainerGet(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Event> events = eventservice.findByUser(usr);

        List<Category> cat = catService.findAll();

        List<State> states = stateService.findAll();

        List<Language> lan = lanService.getAllLanguages();

        model.addAttribute("categories", cat);

        model.addAttribute("states", states);
        model.addAttribute("lans", lan);

        model.addAttribute("events", events);

        return "masterTrainerOperation";

    }

    @GetMapping("/details")
    public String MasterTrainerDetailsGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<PostQuestionaire> postQuestionnaire = postQuestionService.findByUser(usr);
        List<FeedbackMasterTrainer> feedbackMasterTrainer = feedServ.findByUser(usr);
        List<TrainingInformation> trainingInfo = trainingInfoService.findByUser(usr);
        model.addAttribute("postQuestionnaire", postQuestionnaire);
        model.addAttribute("feedbackMasterTrainer", feedbackMasterTrainer);
        model.addAttribute("trainingInfo", trainingInfo);

        return "masterTrainerViewDetails";

    }

    @PostMapping("/downloadQuestion")
    public String downloadQuestionPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(value = "catMasterId") int catName, @RequestParam(value = "lanMasterTrId") int topicId,
            @RequestParam(value = "dwnByLanguageId") String lanName) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Category cat = catService.findByid(catName);
        Topic topic = topicService.findById(topicId);
        TopicCategoryMapping topicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
        Language lan = lanService.getByLanName(lanName);
        Question questions = questService.getQuestionBasedOnTopicCatAndLan(topicCat, lan);
        model.addAttribute("post", "true");
        model.addAttribute("Questions", questions);

        List<Category> cats = catService.findAll();

        List<State> states = stateService.findAll();

        List<Language> lans = lanService.getAllLanguages();

        model.addAttribute("categories", cats);

        model.addAttribute("states", states);
        model.addAttribute("lans", lans);
        model.addAttribute("question", "question");

        return "masterTrainerOperation";

    }

    @GetMapping("/viewTrainee")
    public String downloadQuestionPost(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        List<TraineeInformation> trainees = traineeService.findAll();
        List<Category> categories = catService.findAll();

        model.addAttribute("TraineesData", trainees);
        model.addAttribute("categories", categories);

        return "traineeView";

    }

    @GetMapping("/trainee/edit/{id}")
    public String editTraineeGet(HttpServletRequest req, @PathVariable int id, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        TraineeInformation trainee = traineeService.findById(id);

        if (trainee == null) {

            return "redirect:/viewTrainee";
        }

        if (trainee.getTraineeInfos().getUser().getId() != usr.getId()) {

            return "redirect:/viewTrainee";
        }

        model.addAttribute("trainee", trainee);

        return "editTrainee"; // need to accomdate view part
    }

    @PostMapping("/updateTrainee")
    public String editTraineeGet(Model model, Principal principal, HttpServletRequest req) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        String Id = req.getParameter("traineeId");
        String name = req.getParameter("name");
        String age = req.getParameter("age");
        String email = req.getParameter("email");
        String gender = req.getParameter("gender");
        String aadhar = req.getParameter("aadhar");
        String org = req.getParameter("org");
        String phone = req.getParameter("contactnumber");

        TraineeInformation trainee = traineeService.findById(Integer.parseInt(Id));

        model.addAttribute("trainee", trainee);

        if (aadhar.length() != 12) {

            model.addAttribute("error_msg", "Invalid aadhar number");
            return "editTrainee";
        }

        if (phone.length() != 10) {

            model.addAttribute("error_msg", "Invalid phone number");
            return "editTrainee";
        }

        if (!ServiceUtility.checkEmailValidity(email)) { // need to accommodate

            model.addAttribute("error_msg", "E-mail Wrong");
            return "editTrainee";
        }

        trainee.setAadhar(Long.parseLong(aadhar));
        trainee.setAge(Integer.parseInt(age));
        trainee.setEmail(email);
        trainee.setGender(gender);
        trainee.setOrganization(org);
        trainee.setPhone(Long.parseLong(phone));
        trainee.setName(name);

        traineeService.save(trainee);

        model.addAttribute("trainee", trainee);
        model.addAttribute("success_msg", CommonData.RECORD_UPDATE_SUCCESS_MSG);

        return "editTrainee"; // need to accomdate view part
    }

    @PostMapping("/addTrainingInfo")
    public String addTrainingInfoPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam("ParticipantsPhoto") MultipartFile trainingImage,
            @RequestParam("traineeInformation") MultipartFile traineeInfo, @RequestParam(value = "event") int eventId,
            @RequestParam(value = "traningInfo") String trainingInformation,
            @RequestParam(value = "totalPar") int totaltrainee) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Event event = eventservice.findById(eventId);

        if (!ServiceUtility.checkFileExtensiononeFileCSV(traineeInfo)) {

            model.addAttribute("error_msg", CommonData.CSV_ERROR);
            return "masterTrainerOperation";
        }

        if (!ServiceUtility.checkFileExtensionZip(trainingImage)) {

            model.addAttribute("error_msg", CommonData.ZIP_ERROR);
            return "masterTrainerOperation";
        }

        int newTrainingdata = trainingInfoService.getNewId();
        TrainingInformation trainingData = new TrainingInformation();
        trainingData.setDateAdded(ServiceUtility.getCurrentTime());
        trainingData.setTrainingId(newTrainingdata);
        trainingData.setTotalParticipant(totaltrainee);
        trainingData.setUser(usr);
        trainingData.setEvent(event);

        try {
            trainingInfoService.save(trainingData);

            trainingInfoService.save(trainingData);

            String folder = CommonData.uploadDirectoryMasterTrainer + newTrainingdata;
            String document = ServiceUtility.uploadMediaFile(trainingImage, env, folder);

            trainingData.setPosterPath(document);

            byte[] bytes = traineeInfo.getBytes();
            String completeData = new String(bytes);

            String[] rows = completeData.split("\n");

            Set<TraineeInformation> trainees = new HashSet<TraineeInformation>();
            int newTraineeId = traineeService.getNewId();

            for (int i = 0; i < rows.length; i++) {
                String[] columns = rows[i].split(",");
                TraineeInformation temp = new TraineeInformation(newTraineeId++, columns[0], columns[1],
                        Long.parseLong(columns[2]), Integer.parseInt(columns[3]), Long.parseLong(columns[4]),
                        columns[5], columns[6], trainingData);
                trainees.add(temp);
            }

            trainingInfoService.addTrainee(trainingData, trainees);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Master Trainer Operation : {} ", trainingData, e);

            model.addAttribute("error_msg", CommonData.EVENT_ERROR);
            return "masterTrainerOperation";
        }

        model.addAttribute("success_msg", CommonData.EVENT_SUCCESS);
        return "masterTrainerOperation";

    }

    @PostMapping("/uploadfeedback")
    public String uploadFeedbackPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(value = "catMasId") int catId, @RequestParam(value = "feedbackmasterId") int trainingTitle,
            @RequestParam(value = "feedbackForm") MultipartFile feedbackFile,
            @RequestParam(value = "traningInformation") String desc) {
        User usr = getUser(principal);

        model.addAttribute("userInfo", usr);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<Category> cats = catService.findAll();

        List<State> states = stateService.findAll();

        List<Language> lan = lanService.getAllLanguages();

        model.addAttribute("categories", cats);

        model.addAttribute("states", states);
        model.addAttribute("lans", lan);

        if (!ServiceUtility.checkFileExtensionZip(feedbackFile)) {

            model.addAttribute("error_msg", CommonData.ZIP_ERROR); // Accommodate error message
            return "masterTrainerOperation";
        }

        TrainingInformation trainingInfo = trainingInfoService.getById(trainingTitle);

        FeedbackMasterTrainer feed = new FeedbackMasterTrainer(feedServ.getNewId(), desc,
                ServiceUtility.getCurrentTime(), null, trainingInfo, usr);
        try {
            feedServ.save(feed);

            String folder = CommonData.uploadDirectoryMasterTrainerFeedback + feed.getId();
            String document = ServiceUtility.uploadMediaFile(feedbackFile, env, folder);

            feed.setPath(document);
            feedServ.save(feed);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            model.addAttribute("error_msg", CommonData.EVENT_ERROR);
            logger.error("Error in Master Trainer Operation1  {}", feed, e);
        }

        model.addAttribute("success_msg", CommonData.EVENT_SUCCESS);
        return "masterTrainerOperation";

    }

    @PostMapping("/uploadPostQuestionaire")
    public String uploadQuestionPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(value = "catMasPostId") int catId, @RequestParam(value = "postTraining") int trainingTitle,
            @RequestParam(value = "postQuestions") MultipartFile postQuestions) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        List<Category> cats = catService.findAll();

        List<State> states = stateService.findAll();

        List<Language> lan = lanService.getAllLanguages();

        model.addAttribute("categories", cats);

        model.addAttribute("states", states);
        model.addAttribute("lans", lan);

        if (!ServiceUtility.checkFileExtensionZip(postQuestions)) {

            model.addAttribute("error_msg", CommonData.ZIP_ERROR); // Accommodate error message
            return "masterTrainerOperation";
        }

        TrainingInformation trainingInfo = trainingInfoService.getById(trainingTitle);

        PostQuestionaire feed = new PostQuestionaire(postQuestionService.getNewCatId(), null,
                ServiceUtility.getCurrentTime(), trainingInfo, usr);
        try {
            postQuestionService.save(feed);

            String folder = CommonData.uploadPostQuestion + feed.getId();
            String document = ServiceUtility.uploadMediaFile(postQuestions, env, folder);

            feed.setQuestionPath(document);
            postQuestionService.save(feed);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            model.addAttribute("error_msg", CommonData.EVENT_ERROR);
            logger.error("Error in Master Trainer Operation  {}", feed, e);
            return "masterTrainerOperation";
        }

        model.addAttribute("success_msg", CommonData.EVENT_SUCCESS);
        return "masterTrainerOperation";

    }

    @GetMapping("/assignRoleToAdmin")
    public String assignRoleToDomainGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Category> categories = catService.findAll();
        List<Language> languages = lanService.getAllLanguages();

        model.addAttribute("categories", categories);

        model.addAttribute("languages", languages);

        return "assignRoleToDomain"; // add html page

    }

    @PostMapping("/assignRoleToAdmin")
    public String assignRoleToDomainPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(value = "category") String cat, @RequestParam(value = "language") String lan) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Category> categories = catService.findAll();
        List<Language> languages = lanService.getAllLanguages();

        model.addAttribute("categories", categories);
        model.addAttribute("languages", languages);

        Category category = catService.findBycategoryname(cat);
        Language language = lanService.getByLanName(lan);

        if (language == null) {
            model.addAttribute("error_msg", "Please select language");

            return "assignRoleToDomain";
        }

        if (category == null) {
            model.addAttribute("error_msg", "Please select Category");

            return "assignRoleToDomain";
        }

        Role role = roleService.findByname(CommonData.adminReviewerRole);

        if (usrRoleService.findByLanCatUser(language, category, usr, role) != null) {
            model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);
            return "assignRoleToDomain";
        }

        UserRole usrRole = new UserRole();
        usrRole.setCreated(ServiceUtility.getCurrentTime());
        usrRole.setUser(usr);
        usrRole.setRole(role);
        usrRole.setLanguage(language);
        usrRole.setCategory(category);
        usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

        try {
            usrRoleService.save(usrRole);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            model.addAttribute("error_msg", CommonData.ROLE_ERROR_MSG);
            logger.error("Error in assign Roles to Domain : {}", usrRole, e);
            return "assignRoleToDomain"; // accommodate error message
        }

        model.addAttribute("success_msg", CommonData.ADMIN_REVIEWER_REQ);

        return "assignRoleToDomain";

    }

    @GetMapping("/profile")
    public String profileUserGet(HttpServletRequest req, Model model, Principal principal) {
        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        model.addAttribute("userInfo", usr);

        return "profileView";

    }

    @GetMapping("/revokeRole/{usrRoleId}")
    public String revokeRoleByRole(HttpServletRequest req, @PathVariable long usrRoleId, Principal principal,
            Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());

        UserRole usrRole = usrRoleService.findById(usrRoleId);

        if (usrRole.getUser().getId() == usr.getId()) {
            usrRole.setStatus(false);
            usrRole.setRevoked(true);
            usrRoleService.save(usrRole);

            model.addAttribute("success_msg", "Role revoked Successfully");
        } else {
            model.addAttribute("error_msg", "Wrong operation");
        }

        model.addAttribute("userInfo", usr);

        return "revokeRole";
    }

    @PostMapping("/profile")
    public String updateuserdataPost(HttpServletRequest req, Model model, Principal principal,
            @ModelAttribute("firstName") String firstName, @ModelAttribute("lastName") String lastName,
            @ModelAttribute("phone") String phone, @ModelAttribute("address") String address,
            @ModelAttribute("birthday") String dob, @ModelAttribute("description") String desc) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        long phoneLongValue;
        if (phone.length() > 10) {

            model.addAttribute("error_msg", "Entered Mobile Number is not of 10 digit");
            return "profileView";

        } else {
            phoneLongValue = Long.parseLong(phone);

        }

        try {
            usr.setFirstName(firstName);
            usr.setLastName(lastName);
            usr.setAddress(address);
            usr.setPhone(phoneLongValue);
            usr.setDob(ServiceUtility.convertStringToDate(dob));

            userService.save(usr);

            if (!desc.isEmpty()) {
                Consultant consult = consultService.findByUser(usr);
                consult.setDescription(desc);
                consultService.save(consult);
            }

            model.addAttribute("success_msg", "Data Updated Successfully");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Profile View  {} {} {} {} {}", firstName, lastName, address, phoneLongValue, dob, e);
            model.addAttribute("error_msg", "Please Try Later");
        }

        usr = userService.findByUsername(principal.getName());
        model.addAttribute("userInfo", usr);
        return "profileView";

    }

    @GetMapping("/revokeRoleContributor")
    public String revokeRoleByContributor(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Role role = roleService.findByname(CommonData.contributorRole);

        List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

        model.addAttribute("userRoles", userRoles);

        return "revokeRole";
    }

    @GetMapping("/revokeRoleDomain")
    public String revokeRoleByDomain(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Role role = roleService.findByname(CommonData.domainReviewerRole);

        List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

        model.addAttribute("userRoles", userRoles);

        return "revokeRole";
    }

    @GetMapping("/revokeRoleQuality")
    public String revokeRoleBYQuality(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Role role = roleService.findByname(CommonData.qualityReviewerRole);

        List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

        model.addAttribute("userRoles", userRoles);

        return "revokeRole";
    }

    @GetMapping("/revokeRoleMaster")
    public String revokeRoleMaster(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Role role = roleService.findByname(CommonData.masterTrainerRole);

        List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

        model.addAttribute("userRoles", userRoles);

        return "revokeRole";
    }

    @GetMapping("/brochures")
    public String brochure(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Brouchure> brouchures = broService.findAllBrouchuresForCache();
        List<Language> languages = lanService.getAllLanguages();

        List<Version> versions = new ArrayList<Version>();
        for (Brouchure bro : brouchures) {
            Version ver = verService.findByBrouchureAndPrimaryVersion(bro, bro.getPrimaryVersion());
            versions.add(ver);

        }
        Collections.sort(versions, Version.SortByBroVersionTime);

        List<FilesofBrouchure> filesList = new ArrayList<>();
        for (Version ver1 : versions) {
            for (Language lan : languages) {
                FilesofBrouchure filesBro = filesofbrouchureService.findByLanguageandVersion(lan, ver1);
                if (filesBro != null) {
                    filesList.add(filesBro);
                }
            }
        }

        model.addAttribute("filesList", filesList);
        model.addAttribute("brouchures", brouchures);
        model.addAttribute("versions", versions);
        model.addAttribute("languages", languages);

        return "brochures";
    }

    @GetMapping("/training/edit/{id}")
    public String editTrainingGet(HttpServletRequest req, @PathVariable int id, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        TrainingInformation training = trainingInfoService.getById(id);

        if (training.getUser().getId() != usr.getId()) {

            return "redirect:/viewTrainee";
        }

        List<State> states = stateService.findAll();

        model.addAttribute("states", states);

        model.addAttribute("training", training);

        return "updateTraining";
    }

    @PostMapping("/updateTraining")
    public String updateTrainingPost(HttpServletRequest req, Model model, Principal principal,
            @RequestParam(value = "stateName") int state, @RequestParam(value = "districtName") int district,
            @RequestParam(value = "cityName") int city, @RequestParam(value = "totalPar") int totaltrainee,
            @RequestParam(value = "addressInformationName") String address,
            @RequestParam(value = "pinCode") int pinCode, @RequestParam(value = "trainingId") int trainingId) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        List<State> states = stateService.findAll();

        model.addAttribute("states", states);

        model.addAttribute("userInfo", usr);

        TrainingInformation trainingData = trainingInfoService.getById(trainingId);

        model.addAttribute("training", trainingData);

        trainingData.setTotalParticipant(totaltrainee);

        trainingData.setAddress(address);

        try {
            trainingInfoService.save(trainingData);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            logger.error("Error in Update Training: {}", trainingData, e);
            // throw a error
            model.addAttribute("error_msg", CommonData.EVENT_ERROR);
            return "updateTraining";
        }

        model.addAttribute("success_msg", CommonData.EVENT_SUCCESS);
        model.addAttribute("training", trainingData);
        return "updateTraining";

    }

    @GetMapping("/tutorialStatus")
    public String tutorialStatus(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        List<Tutorial> published = new ArrayList<Tutorial>();
        List<Tutorial> tutorials = tutService.findAll();
        for (Tutorial temp : tutorials) {

            if (temp.getOutlineStatus() == CommonData.PUBLISH_STATUS
                    && temp.getScriptStatus() == CommonData.PUBLISH_STATUS
                    && temp.getSlideStatus() == CommonData.PUBLISH_STATUS
                    && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS
                    && temp.getVideoStatus() == CommonData.PUBLISH_STATUS
                    && temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

                published.add(temp);
            }

        }

        model.addAttribute("tutorial", published);

        return "listTutorialSuperAdmin";
    }

    @GetMapping("/tutorialStatus/{id}")
    public String publishTutorial(HttpServletRequest req, @PathVariable int id, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        List<Tutorial> published = new ArrayList<Tutorial>();
        List<Tutorial> tutorials;

        Tutorial tut = tutService.getById(id);

        if (tut.isStatus()) {
            tut.setStatus(false);
            taskProcessingService.addUpdateDeleteTutorial(tut, CommonData.DELETE_DOCUMENT);
            model.addAttribute("success_msg", "Tutorial unpublished Successfully");
        } else {
            tut.setStatus(true);

            taskProcessingService.addUpdateDeleteTutorial(tut, CommonData.ADD_DOCUMENT);
            model.addAttribute("success_msg", "Tutorial published Successfully");
        }

        try {

            tutService.save(tut);

        } catch (Exception e) {
            // TODO: handle exception

            tutorials = tutService.findAll();
            for (Tutorial temp : tutorials) {

                if (temp.getOutlineStatus() == CommonData.PUBLISH_STATUS
                        && temp.getScriptStatus() == CommonData.PUBLISH_STATUS
                        && temp.getSlideStatus() == CommonData.PUBLISH_STATUS
                        && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS
                        && temp.getVideoStatus() == CommonData.PUBLISH_STATUS
                        && temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

                    published.add(temp);
                }

            }
            model.addAttribute("tutorial", published);
            model.addAttribute("error_msg", "Please Try again.");
            return "listTutorialSuperAdmin";
        }

        tutorials = tutService.findAll();
        for (Tutorial temp : tutorials) {

            if (temp.getOutlineStatus() == CommonData.PUBLISH_STATUS
                    && temp.getScriptStatus() == CommonData.PUBLISH_STATUS
                    && temp.getSlideStatus() == CommonData.PUBLISH_STATUS
                    && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS
                    && temp.getVideoStatus() == CommonData.PUBLISH_STATUS
                    && temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

                published.add(temp);
            }

        }

        model.addAttribute("tutorial", published);

        return "listTutorialSuperAdmin";

    }

    @GetMapping("/uploadTimescript")
    public String uploadTimescriptGet(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        Set<Tutorial> tutorials = new HashSet<Tutorial>();
        Set<Tutorial> published = new HashSet<Tutorial>();

        List<ContributorAssignedMultiUserTutorial> conTutorials = conMultiUser.getAllByuser(usr);

        for (ContributorAssignedMultiUserTutorial conMultiTemp : conTutorials) {

            ContributorAssignedTutorial conTemp = conRepo.findById(conMultiTemp.getConAssignedTutorial().getId());

            tutorials.addAll(tutService.findAllByContributorAssignedTutorial(conTemp));
        }

        for (Tutorial x : tutorials) {
            if (x.isStatus()) {
                published.add(x);
            }
        }

        model.addAttribute("tutorial", published);

        return "uploadTimescript";
    }

    @GetMapping("/users")
    public String usersGet(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<User> allUser = userService.findAll();
        model.addAttribute("users", allUser);
        model.addAttribute("usrRoleService", usrRoleService);

        for (User user : allUser) {

            List<UserRole> ur1 = usrRoleService.findAllByUser(user);

            for (UserRole temp : ur1) {
                if (temp.getStatus()) {
                    String str1 = new String();
                    String str2 = new String();
                    if (temp.getCategory() == null) {
                        str1 = "NA";
                    } else {
                        str1 = temp.getCategory().getCatName();
                    }
                    if (temp.getLanguage() == null) {
                        str2 = "NA";
                    } else {
                        str2 = temp.getLanguage().getLangName();
                    }

                }
            }

        }

        return "showUsers";
    }

    @GetMapping("/statistics")
    public String statistics(HttpServletRequest req, Principal principal, Model model,
            @RequestParam(name = "categoryId", defaultValue = "0") int categoryId,
            @RequestParam(name = "languageId", defaultValue = "0") int languageId) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Category> cat = getCategories();

        List<Language> lan = getLanguages();

        List<ContributorAssignedTutorial> contributor_Role = conRepo.findAll();

        Collections.sort(cat);
        Collections.sort(lan);
        LinkedHashMap<Integer, String> catMap = new LinkedHashMap<>();
        LinkedHashMap<Integer, String> lanMap = new LinkedHashMap<>();
        for (Category c : cat) {

            catMap.put(c.getCategoryId(), c.getCatName());

        }
        for (Language l : lan) {
            lanMap.put(l.getLanId(), l.getLangName());
        }

        List<Tutorial> tutorilaListForCount = tutService.findAllByStatus(true);
        List<Tutorial> finaltutoriallistforcount = new ArrayList<>();
        for (Tutorial temp : tutorilaListForCount) {

            Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
            if (c.isStatus()) {
                finaltutoriallistforcount.add(temp);
            }
        }

        model.addAttribute("catMap", catMap);
        model.addAttribute("lanMap", lanMap);

        List<Tutorial> tutorials;
        List<Tutorial> finalTutorials = new ArrayList<>();
        if (categoryId != 0 & languageId != 0) {
            Language language = lanService.getById(languageId);
            Category category = catService.findByid(categoryId);
            List<TopicCategoryMapping> topicCategoryMappings = topicCatService.findAllByCategory(category);
            List<ContributorAssignedTutorial> contributorAssignedTutorials = conRepo
                    .findAllByTopicCatAndLan(topicCategoryMappings, language);
            tutorials = tutService.findAllByContributorAssignedTutorialList(contributorAssignedTutorials);

            for (Tutorial temp : tutorials) {

                Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
                if (c.isStatus()) {
                    finalTutorials.add(temp);
                }
            }

            model.addAttribute("cat_value", category.getCategoryId());
            model.addAttribute("lan_value", language.getLanId());
        } else if (categoryId != 0) {
            Category category = catService.findByid(categoryId);
            List<TopicCategoryMapping> topicCategoryMappings = topicCatService.findAllByCategory(category);
            if (!topicCategoryMappings.isEmpty()) {
                List<ContributorAssignedTutorial> contributorAssignedTutorials = conRepo
                        .findAllByTopicCat(topicCategoryMappings);
                tutorials = tutService.findAllByContributorAssignedTutorialList(contributorAssignedTutorials);
                for (Tutorial temp : tutorials) {

                    Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
                    if (c.isStatus()) {
                        finalTutorials.add(temp);
                    }
                }
            } else {
                tutorials = null;
                finalTutorials = null;
            }

            model.addAttribute("cat_value", category.getCategoryId());
            model.addAttribute("lan_value", 0);
        } else if (languageId != 0) {
            Language language = lanService.getById(languageId);
            List<ContributorAssignedTutorial> contributorAssignedTutorials = conRepo.findAllByLan(language);
            tutorials = tutService.findAllByContributorAssignedTutorialList(contributorAssignedTutorials);

            for (Tutorial temp : tutorials) {

                Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
                if (c.isStatus()) {
                    finalTutorials.add(temp);
                }
            }

            model.addAttribute("cat_value", 0);
            model.addAttribute("lan_value", language.getLanId());
        } else {
            tutorials = tutService.findAllByStatus(true);
            for (Tutorial temp : tutorials) {

                Category c = temp.getConAssignedTutorial().getTopicCatId().getCat();
                if (c.isStatus()) {
                    finalTutorials.add(temp);
                }
            }
            model.addAttribute("cat_value", 0);
            model.addAttribute("lan_value", 0);
        }

        model.addAttribute("categories", cat);
        model.addAttribute("languages", lan);
        model.addAttribute("catTotal", cat.size());
        model.addAttribute("lanTotal", lan.size());
        model.addAttribute("tutTotal", finaltutoriallistforcount.size());
        model.addAttribute("tutorials", finalTutorials);
        model.addAttribute("contributor_Role", contributor_Role);

        HashMap<String, Integer> cat_count = new HashMap<>();
        HashMap<ContributorAssignedTutorial, Integer> contriRole_count = new HashMap<>();

        model.addAttribute("cat_count", cat_count);
        model.addAttribute("contriRole_count", contriRole_count);

        return "statistics";
    }

    @GetMapping("/cdContentInfo")
    public String cdContentInfoGet(HttpServletRequest req, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        List<Category> cat = catService.findAll();
        List<Language> lan = lanService.getAllLanguages();
        Collections.sort(cat);
        Collections.sort(lan);

        model.addAttribute("categories", cat);
        model.addAttribute("languages", lan);

        return "cdContent";
    }

    @PostMapping("/cdContentInfo")
    public String cdContentInfoPost(HttpServletRequest req, @RequestParam(name = "categoryName") String category,
            @RequestParam(name = "lan") String language, Principal principal, Model model) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);

        long totalSize = 0;
        int totalNumberOfVideo = 0;

        List<Category> cat = catService.findAll();
        List<Language> lan = lanService.getAllLanguages();
        Collections.sort(cat);
        Collections.sort(lan);

        model.addAttribute("categories", cat);
        model.addAttribute("languages", lan);

        Category catTemp = catService.findBycategoryname(category);
        Language lanTemp = lanService.getByLanName(language);

        if (catTemp == null || lanTemp == null) {

            return "redirect:/cdContentInfo";
        }

        model.addAttribute("categoryName", category);
        model.addAttribute("lanName", language);

        List<Tutorial> tutorials = tutService.findAll();

        for (Tutorial x : tutorials) {
            if (x.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase(lanTemp.getLangName())
                    && x.getConAssignedTutorial().getTopicCatId().getCat().getCatName()
                            .equalsIgnoreCase(catTemp.getCatName())) {

                Path path = Paths.get(env.getProperty("spring.applicationexternalPath.name") + x.getVideo());

                try {
                    totalSize += Files.size(path);
                    totalNumberOfVideo += 1;
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    logger.error("Error in cdContent {}", path, e);
                }
            }
        }

        model.addAttribute("value", totalSize / 1024 / 1024);
        model.addAttribute("totalVideo", totalNumberOfVideo);
        if (totalSize > 0 && totalNumberOfVideo > 0) {
            if (principal == null) {
                model.addAttribute("rate", "500");
            } else {
                model.addAttribute("rate", "Free");
            }
        }

        return "cdContent";
    }

    @GetMapping("/unpublishTopic")
    public String unpublishTopic(HttpServletRequest req, Model model, Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), req.getMethod(), req.getRequestURI());
        model.addAttribute("userInfo", usr);
        List<Category> categories = catService.findAll();
        List<Category> filtered_categories = new ArrayList<Category>();
        for (Category cat : categories) {
            if (!cat.getTopicCategoryMap().isEmpty()) {
                filtered_categories.add(cat);
            }
        }

        List<Topic> tops = topicService.findAll();
        List<String> topics = new ArrayList<String>();
        for (Topic t : tops) {
            topics.add(t.getTopicName());

        }

        List<Language> langs = lanService.getAllLanguages();
        List<String> langauges = new ArrayList<String>();
        for (Language temp : langs) {
            langauges.add(temp.getLangName());
        }
        List<LogManegement> lms = logMangementService.getLogsWithSuperUser();
        Set<Tutorial> tutorials = new HashSet<Tutorial>();
        for (LogManegement l : lms) {
            if (!l.getTutorialInfos().isStatus()) {
                tutorials.add(l.getTutorialInfos());

            }
        }

        model.addAttribute("langauges", langauges);
        model.addAttribute("categories", filtered_categories);
        model.addAttribute("topics", topics);
        model.addAttribute("tutorials", tutorials);

        model.addAttribute("method", "get");
        return "unpublishTopic";

    }

    @PostMapping("/unpublishTopic")
    public String unpublishTopicPost(HttpServletRequest request, @ModelAttribute("category") Integer categoryId,
            @ModelAttribute("topic") Integer topicId, @ModelAttribute("language") String language, Model model,
            Principal principal) {

        User usr = getUser(principal);
        logger.info("{} {} {}", usr.getUsername(), request.getMethod(), request.getRequestURI());

        model.addAttribute("classActiveForgetPassword", true);
        Category cat = catService.findByid(categoryId);
        Topic topic = topicService.findById(topicId);
        model.addAttribute("topicname", topic.getTopicName());

        Language lan = lanService.getByLanName(language);
        model.addAttribute("cat", cat.getCategoryId());
        model.addAttribute("topic", topic.getTopicId());
        model.addAttribute("lan", lan.getLangName());
        model.addAttribute("catname", cat.getCatName());
        TopicCategoryMapping tcm = topicCatService.findAllByCategoryAndTopic(cat, topic);
        ContributorAssignedTutorial con = conRepo.findByTopicCatAndLanViewPart(tcm, lan);
        List<Tutorial> tut = tutService.findAllByContributorAssignedTutorial(con);

        if (tut == null || tut.size() == 0) {
            return "unpublishTopic";
        }
        Tutorial t = tut.get(0);
        model.addAttribute("tut", tut);
        model.addAttribute("tutorial_id", t.getTutorialId());

        List<Category> categories_lst = catService.findAll();

        HashMap<Integer, String> map = new HashMap<>();
        for (Category c : categories_lst) {
            map.put(c.getCategoryId(), c.getCatName());
        }

        List<Language> langs = lanService.getAllLanguages();
        List<String> langauges = new ArrayList<String>();
        for (Language temp : langs) {
            langauges.add(temp.getLangName());
        }

        HashMap<Integer, String> topicName = new HashMap<>();

        List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat);

        for (TopicCategoryMapping temp : local) {

            topicName.put(temp.getTopic().getTopicId(), temp.getTopic().getTopicName());

        }

        model.addAttribute("topics", topicName);
        model.addAttribute("langauges", langauges);
        model.addAttribute("categories", map);
        model.addAttribute("method", "post");

        model.addAttribute("status", "can_unpublish");

        if (t.isStatus()) {
            model.addAttribute("status", "can_unpublish");
        } else {
            model.addAttribute("status", "already_unpublished");
        }

        model.addAttribute("userInfo", usr);

        return "unpublishTopic";
    }

}
