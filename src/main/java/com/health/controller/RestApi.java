package com.health.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.health.domain.security.UserRole;
import com.health.model.Category;
import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.Language;
import com.health.model.TopicCategoryMapping;
import com.health.model.Tutorial;
import com.health.model.User;
import com.health.service.CategoryService;
import com.health.service.ContributorAssignedTutorialService;
import com.health.service.LanguageService;
import com.health.service.TopicCategoryMappingService;
import com.health.service.TutorialService;
import com.health.service.UserRoleService;
import com.health.service.UserService;
import com.health.threadpool.ZipHealthTutorialThreadService;
import com.health.utility.CommonData;
import com.health.utility.ServiceUtility;

/**
 * Rest APi class return data in JSON format
 * 
 * @author om prakash
 * @version 1.0
 *
 */
@RestController
public class RestApi {

    private static final Logger logger = LoggerFactory.getLogger(RestApi.class);

    @Autowired
    private CategoryService catService;

    @Autowired
    private LanguageService lanService;

    @Autowired
    private TopicCategoryMappingService topicCatService;

    @Autowired
    private ContributorAssignedTutorialService conRepo;

    @Autowired
    private TutorialService tutService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService usrRoleService;

    @Autowired
    private ZipHealthTutorialThreadService zipHealthTutorialThreadService;

    @Autowired
    private Environment env;

    @Value("${downloadLimit}")
    private int downloadLimit;

    @Value("${downloadTimeOut}")
    private long downloadTimeOut;

    @Value("${app.base-url}")
    private String baseUrl;

    private AtomicInteger downloadCount = new AtomicInteger(0);

    /**
     * This url fetches all the category and language available in the system
     * 
     * @return ResponseEntity object
     */
    @GetMapping("/getCatAndLan")
    public ResponseEntity<Object> getcat() {

        List<Category> cats = catService.findAll();
        List<Language> lans = lanService.getAllLanguages();

        Map<String, Map<String, List<Map<String, String>>>> mapdataReturn = new HashMap<String, Map<String, List<Map<String, String>>>>();

        Map<String, List<Map<String, String>>> mapdata = new HashMap<String, List<Map<String, String>>>();

        List<Map<String, String>> allCategories = new ArrayList<>();
        List<Map<String, String>> allLanguage = new ArrayList<>();

        for (Category x : cats) {

            Map<String, String> temp = new HashMap<String, String>();
            temp.put("id", Integer.toString(x.getCategoryId()));
            temp.put("foss", x.getCatName());
            temp.put("description", x.getDescription());

            allCategories.add(temp);

        }

        for (Language x : lans) {

            Map<String, String> temp = new HashMap<String, String>();
            temp.put("id", Integer.toString(x.getLanId()));
            temp.put("name", x.getLangName());

            allLanguage.add(temp);

        }

        mapdata.put("foss", allCategories);
        mapdata.put("language", allLanguage);

        mapdataReturn.put("healthnutrition", mapdata);

        return new ResponseEntity<Object>(mapdataReturn, HttpStatus.OK);

    }

    /**
     * This url fetches out all the tutorial based on given category id and language
     * id
     * 
     * @param catId int value
     * @param lanId int value
     * @return ResponseEntity object
     */
    @GetMapping("/getTopicOnCatAndLan/{catId}/{lanId}")
    public ResponseEntity<Object> getTopic(@PathVariable(name = "catId") int catId,
            @PathVariable(name = "lanId") int lanId) {

        Category cat = catService.findByid(catId);
        Language lan = lanService.getById(lanId);

        if (cat == null || lan == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Map<String, List<Map<String, String>>>> mapdataReturn = new HashMap<String, Map<String, List<Map<String, String>>>>();

        Map<String, List<Map<String, String>>> mapdata = new HashMap<String, List<Map<String, String>>>();

        List<Map<String, String>> alltutorial = new ArrayList<>();

        List<TopicCategoryMapping> localTopicCatList = null;
        List<ContributorAssignedTutorial> conAssigTutorialList = null;
        List<Tutorial> tut = null;

        localTopicCatList = topicCatService.findAllByCategory(cat);

        conAssigTutorialList = conRepo.findAllByTopicCatAndLan(localTopicCatList, lan);

        tut = tutService.findAllByContributorAssignedTutorialList(conAssigTutorialList);

        for (Tutorial x : tut) {

            Map<String, String> temp = new HashMap<String, String>();
            temp.put("id", Integer.toString(x.getTutorialId()));
            temp.put("outline", x.getOutline());
            temp.put("tutorial", x.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());

            alltutorial.add(temp);
        }

        mapdata.put("tutorials", alltutorial);

        mapdataReturn.put("healthnutrition", mapdata);

        return new ResponseEntity<Object>(mapdataReturn, HttpStatus.OK);

    }

    /**
     * This url fetches out all the roles user entitled to based on given category
     * and language id
     * 
     * @param catId    int value
     * @param lanId    int value
     * @param username String object
     * @return ResponseEntity object
     */
    @GetMapping("/getRolesOnCatLanUser/{catId}/{lanId}/{username}")
    public ResponseEntity<Object> getRoles(@PathVariable(name = "catId") int catId,
            @PathVariable(name = "lanId") int lanId, @PathVariable(name = "username") String username) {

        Category cat = catService.findByid(catId);
        Language lan = lanService.getById(lanId);
        User usr = userService.findByUsername(username);

        if (cat == null || lan == null || usr == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Map<String, java.util.Set<String>>> mapdataReturn = new HashMap<String, Map<String, java.util.Set<String>>>();

        Map<String, java.util.Set<String>> mapdata = new HashMap<String, java.util.Set<String>>();

        java.util.Set<String> roles = new HashSet<String>();

        List<UserRole> usrRole = usrRoleService.findAllByUser(usr);

        for (UserRole x : usrRole) {

            if (x.getStatus()) {
                if (x.getRole().getName().equalsIgnoreCase(CommonData.contributorRole)
                        && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName())) {
                    roles.add("Contributor");
                } else if (x.getRole().getName().equalsIgnoreCase(CommonData.domainReviewerRole)
                        && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName())
                        && x.getCat().getCatName().equalsIgnoreCase(cat.getCatName())) {
                    roles.add("Domain-Reviewer");

                } else if (x.getRole().getName().equalsIgnoreCase(CommonData.qualityReviewerRole)
                        && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName())
                        && x.getCat().getCatName().equalsIgnoreCase(cat.getCatName())) {
                    roles.add("Quality-Reviewer");

                } else if (x.getRole().getName().equalsIgnoreCase(CommonData.adminReviewerRole)
                        && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName())
                        && x.getCat().getCatName().equalsIgnoreCase(cat.getCatName())) {
                    roles.add(x.getRole().getName());
                }
            }

        }

        mapdata.put("roles", roles);

        mapdataReturn.put("healthnutrition", mapdata);

        return new ResponseEntity<Object>(mapdataReturn, HttpStatus.OK);

    }

    /**
     * This url fetch out the tutorial data given tutorial id
     * 
     * @param tutorialId int value
     * @return ResponseEntity object
     */
    @GetMapping("/getTutorial/{tutorialId}")
    public ResponseEntity<Object> getTutorial(@PathVariable(name = "tutorialId") int tutorialId) {

        Tutorial tut = tutService.getById(tutorialId);

        if (tut == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Map<String, List<Map<String, String>>>> mapdataReturn = new HashMap<String, Map<String, List<Map<String, String>>>>();

        Map<String, List<Map<String, String>>> mapdata = new HashMap<String, List<Map<String, String>>>();

        List<Map<String, String>> tutorials = new ArrayList<>();

        Map<String, String> temp = new HashMap<String, String>();

        temp.put("lid", Integer.toString(tut.getConAssignedTutorial().getLan().getLanId()));
        temp.put("language", tut.getConAssignedTutorial().getLan().getLangName());
        temp.put("fid", Integer.toString(tut.getConAssignedTutorial().getTopicCatId().getCat().getCategoryId()));
        temp.put("foss", tut.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
        temp.put("tid", Integer.toString(tut.getTutorialId()));
        temp.put("outline", tut.getOutline());
        temp.put("tutorial", tut.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());

        tutorials.add(temp);

        mapdata.put("tutorial", tutorials);

        mapdataReturn.put("healthnutrition", mapdata);

        return new ResponseEntity<Object>(mapdataReturn, HttpStatus.OK);

    }

    /**
     * This url tells whether a user has contributor role or not on tutorial
     * 
     * @param tutId    int value
     * @param lanId    int value
     * @param username String object
     * @return ResponseEntity object
     */
    @GetMapping("/getContributorByTutLanUser/{tutorialId}/{lanId}/{username}")
    public ResponseEntity<Object> getContributorBoolean(@PathVariable(name = "tutorialId") int tutId,
            @PathVariable(name = "lanId") int lanId, @PathVariable(name = "username") String username) {

        Tutorial tut = tutService.getById(tutId);
        Language lan = lanService.getById(lanId);
        User usr = userService.findByUsername(username);

        if (tut == null || lan == null || usr == null) {
            return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
        }

        Map<String, Boolean> mapdata = new HashMap<String, Boolean>();

        boolean role = false;

        if (tut.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase(lan.getLangName())) {

            for (ContributorAssignedMultiUserTutorial x : tut.getConAssignedTutorial().getMultiUserAssigned()) {
                if (x.getUser().getUsername().equalsIgnoreCase(usr.getUsername())) {
                    role = true;
                }
            }
        }

        mapdata.put("healthnutrition", role);

        return new ResponseEntity<Object>(mapdata, HttpStatus.OK);

    }

    /// downloadHealthTutorials?courseName=HealthTutorial&videoQuality=L&catIds=1,2&lanIds=3,4
    @GetMapping("/downloadHealthTutorials")
    public ResponseEntity<Map<Integer, String>> getZipUrlOfHealthTutorial(@RequestParam String courseName,
            @RequestParam(required = false) String videoQuality, @RequestParam List<Integer> catIds,
            @RequestParam(required = false) List<Integer> lanIds) {

        Map<Integer, String> resultMap = new HashMap<>();

        if (courseName == null || courseName.trim().isEmpty()) {
            resultMap.put(2, "No CourseName  is available in url");
            return ResponseEntity.ok(resultMap);
        }

        if (catIds == null || catIds.isEmpty()) {
            resultMap.put(2, "No Category Id is available in url");
            return ResponseEntity.ok(resultMap);
        }
        String quality;

        if (videoQuality == null || videoQuality.isEmpty()) {
            quality = "High";
        } else {
            switch (videoQuality.toUpperCase()) {
            case "H":
                quality = "High";
                break;
            case "M":
                quality = "Medium";
                break;
            case "L":
                quality = "Low";
                break;
            default:
                quality = "High";
            }
        }

        Collections.sort(catIds);
        Set<Integer> uniqeCatIds = new LinkedHashSet<>(catIds);
        List<Integer> updatedLanIds = new ArrayList<>();
        if (lanIds != null) {
            updatedLanIds.addAll(lanIds);
        }
        // Added English Lan Id to use by default
        updatedLanIds.add(22);
        Collections.sort(updatedLanIds);

        Set<Integer> uniquelanIds = new LinkedHashSet<>(updatedLanIds);

        for (int catId : uniqeCatIds) {
            Category cat = catService.findByid(catId);
            if (cat == null) {
                resultMap.put(2, "No Category exists for this catId: " + catId);
                return ResponseEntity.ok(resultMap);

            } else if (!cat.isStatus()) {
                resultMap.put(2, "No Enable Category exists for this catId: " + catId);
                return ResponseEntity.ok(resultMap);
            }
        }

        for (int lanId : uniquelanIds) {
            Language lan = lanService.getById(lanId);
            if (lan == null) {
                resultMap.put(2, "No Language exists for this lanId: " + lanId);
                return ResponseEntity.ok(resultMap);

            }
        }

        String zipUrl = zipHealthTutorialThreadService.getZipName(courseName, quality, uniqeCatIds, uniquelanIds, env);

        if (zipUrl == null || zipUrl.isEmpty()) {
            resultMap.put(0, "Zip creation in progress... Please check back after 30 minutes.");
        } else if ("Error".equals(zipUrl)) {
            resultMap.put(2, "No tutorials are available for the selected category and language.");
        } else if (downloadCount.get() == downloadLimit) {
            resultMap.put(0, "Download limit reached. Please try again after 30 minutes.");
        } else {
            double zipSizeInMb = ServiceUtility.getZipSizeInMB(zipUrl, env);
            String resultantzipUrl = ServiceUtility.convertFilePathToUrl(zipUrl);
            resultMap.put(1, baseUrl + "/downloadManagerforhst?zipUrl=" + resultantzipUrl);
            resultMap.put(3, String.valueOf(zipSizeInMb) + "MB");

        }

        return ResponseEntity.ok(resultMap);
    }

    @GetMapping("/downloadManagerforhst")
    public String downloadManager(@RequestParam(name = "zipUrl") String zipUrl, HttpServletResponse response) {

        String message = ServiceUtility.downloadManager(zipUrl, downloadCount, downloadLimit, downloadTimeOut, env,
                response);

        return message;

    }
}
