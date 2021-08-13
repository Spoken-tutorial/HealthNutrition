package com.health.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.health.model.Category;
import com.health.model.City;
import com.health.model.Comment;
import com.health.model.Consultant;
import com.health.model.ContributorAssignedMultiUserTutorial;
import com.health.model.ContributorAssignedTutorial;
import com.health.model.District;
import com.health.model.Event;
import com.health.model.FeedbackForm;
import com.health.model.Language;
import com.health.model.LogManegement;
import com.health.model.State;
import com.health.model.Testimonial;
import com.health.model.Topic;
import com.health.model.TopicCategoryMapping;
import com.health.model.TraineeInformation;
import com.health.model.TrainingInformation;
import com.health.model.TrainingTopic;
import com.health.model.Tutorial;
import com.health.model.User;
import com.health.service.BrouchureService;
import com.health.service.CategoryService;
import com.health.service.CityService;
import com.health.service.CommentService;
import com.health.service.ConsultantService;
import com.health.service.ContributorAssignedMultiUserTutorialService;
import com.health.service.ContributorAssignedTutorialService;
import com.health.service.DistrictService;
import com.health.service.EventService;
import com.health.service.FeedbackService;
import com.health.service.LanguageService;
import com.health.service.LogMangementService;
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
import com.health.utility.CommonData;
import com.health.utility.MailConstructor;
import com.health.utility.SecurityUtility;
import com.health.utility.ServiceUtility;

/**
 * This Controller Class takes website AJAX request and process it accordingly
 * @author om prakash soni
 * @version 1.0
 *
 */
@Controller
public class AjaxController{

	@Autowired
	private CategoryService catService;

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


	/**
	 * make visible/disable brochure object in system
	 * @param id int value
	 * @return Boolean value
	 */
	@GetMapping("/enableDisableBrouchure")
	public @ResponseBody boolean enableDisableBrouchure(int id){
		Brouchure bro = broService.findById(id);

		try {
			if(bro.isShowOnHomepage()) {
				bro.setShowOnHomepage(false);
				broService.save(bro);
				return true;

			}else {
				bro.setShowOnHomepage(true);
				broService.save(bro);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * make visible/disable Consultant object in system
	 * @param id int value
	 * @return Boolean value
	 */
	@GetMapping("/enableDisableConsultant")
	public @ResponseBody boolean enableDisableConsultant(int id){
		Consultant con = consultService.findById(id);

		try {
			if(con.isOnHome()) {
				con.setOnHome(false);
				consultService.save(con);
				return true;

			}else {
				con.setOnHome(true);
				consultService.save(con);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * make visible/disable Testimonial object in system
	 * @param id int value
	 * @return Boolean value
	 */
	@GetMapping("/enableDisableTestimonial")
	public @ResponseBody boolean enableDisableTestimonial(int id){
		Testimonial test = testService.findById(id);

		try {
			if(test.isApproved()) {
				test.setApproved(false);
				testService.save(test);
				return true;

			}else {
				test.setApproved(true);
				testService.save(test);
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * update trainee object
	 * @param addharNo String 
	 * @param age int 
	 * @param principal Principal object
	 * @param email String
	 * @param gender String
	 * @param name String
	 * @param org String
	 * @param phone String
	 * @param traineeId int
	 * @return List of String
	 */
	@RequestMapping("/updateTrainee")
	public @ResponseBody List<String> updateUserPassword(@RequestParam(value = "aadhar") String addharNo,
												@RequestParam(value = "age") int age,Principal principal,
												@RequestParam(value = "email") String email,
												@RequestParam(value = "gender") String gender,
												@RequestParam(value = "name") String name,
												@RequestParam(value = "org") String org,
												@RequestParam(value = "phone") String phone,
												@RequestParam(value = "traineeId") int traineeId){
		List<String> status=new ArrayList<String>();

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

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
			e.printStackTrace();
			status.add("failure");
		}


		return status;

	}

	/**
	 * update password of user
	 * @param newPass string
	 * @param oldPass string 
	 * @param principal Principal object
	 * @return List of String object
	 */
	@RequestMapping("/updatePassword")
	public @ResponseBody List<String> updateUserPassword(@RequestParam(value = "password") String newPass,
												@RequestParam(value = "currentPassword") String oldPass,Principal principal){
		List<String> status=new ArrayList<String>();
		boolean statusPassword=false;

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		if(newPass.length()<6) {
			System.out.println("vik");
			status.add("passwordLengthError");
			return status;
		}

		statusPassword =SecurityConfig.passwordEncoder().matches(oldPass, usr.getPassword());

		if(statusPassword) {
		try {
			usr.setPassword(SecurityUtility.passwordEncoder().encode(newPass));
			usrservice.save(usr);
			status.add("Success");
		}catch (Exception e) {
			// TODO: handle exception
			status.add("failure");
		}
		}else {
			status.add("failure");
		}

		return status;

	}

	/**
	 * return trainee object based on given training id
	 * @param id int 
	 * @return list of string
	 */
	@RequestMapping("/loadTraineeByTrainingId")
	public @ResponseBody List<TraineeInformation> getTraineeInfoOnTrainingId(@RequestParam(value = "id") int id) {

		TrainingInformation training = trainingInforService.getById(id);
		List<TraineeInformation> traineeList = traineeService.findAllBytraineeInfos(training);
		List<TraineeInformation> traineeListResponse = new ArrayList<TraineeInformation>() ;

		for(TraineeInformation x : traineeList) {

			System.out.println(x.getAge());
			TraineeInformation tem = new TraineeInformation(x.getTrainingId(), x.getName(), x.getEmail(), x.getPhone(), x.getAge(), x.getAadhar(), x.getGender(), x.getOrganization(), null);
			traineeListResponse.add(tem);
		}

		return traineeListResponse;

	}

	/**
	 * return title name for the training conducted given id
	 * @param id int 
	 * @return HashMap
	 */
	@RequestMapping("/loadTitleNameInMasterTraining")
	public @ResponseBody HashMap<Integer, String> getTopicNameFromMasterTrainer(@RequestParam(value = "id") int id) {

		HashMap<Integer,String> topicName=new HashMap<>();

		Category cat = catService.findByid(id);
		List<TopicCategoryMapping> topicCatList = topicCatService.findAllByCategory(cat);
		Set<TrainingTopic> trainingTopic = trainingTopicService.findByTopicCat(topicCatList);


		for(TrainingTopic x :trainingTopic) {
//			TrainingInformation temp = trainingInforService.getById(x.getTraineeInfos().getTrainingId());
			Event event = eventService.findById(x.getEvent().getEventId());
			System.err.println("***********************");
			System.err.println(event);
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
	 * @param id int 
	 * @return HashMap
	 */
	@RequestMapping("/loadDistrictByState")
	public @ResponseBody HashMap<Integer, String> getDistrictByState(@RequestParam(value = "id") int id) {

		HashMap<Integer,String> disName=new HashMap<>();

		State state=stateService.findById(id);

		List<District> districts= disService.findAllByState(state);

		for(District temp : districts) {

			disName.put(temp.getId(), temp.getDistrictName());
		}
		return disName;

	}

	/**
	 * return city given district id
	 * @param id int 
	 * @return HashMap
	 */
	@RequestMapping("/loadCityByDistrict")
	public @ResponseBody HashMap<Integer, String> getCityByDistrict(@RequestParam(value = "id") int id) {

		HashMap<Integer,String> cityName=new HashMap<>();

		District district=disService.findById(id);

		List<City> cities= cityServie.findAllByDistrict(district);

		for(City temp : cities) {

			cityName.put(temp.getId(), temp.getCityName());
		}
		return cityName;

	}

	/**
	 * return topic given category id
	 * @param id int 
	 * @return HashMap
	 */
	@RequestMapping("/loadTopicByCategory")
	public @ResponseBody HashMap<Integer, String> getTopicByCategory(@RequestParam(value = "id") int id) {

		HashMap<Integer,String> topicName=new HashMap<>();

		Category cat = catService.findByid(id);

		List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat) ;

		for(TopicCategoryMapping temp : local) {

			topicName.put(temp.getTopic().getTopicId(), temp.getTopic().getTopicName());
		}
		return topicName;

	}


	/**
	 * return published tutorial 
	 * @param id int value
	 * @param tutorialId int 
	 * @param langName String
	 * @return hashMao
	 */
	@RequestMapping("/loadTopicByCategoryPreRequistic")
	public @ResponseBody HashMap<Integer, String> getTopicByCategoryPreRequistic(@RequestParam(value = "id") String id,
																			@RequestParam(value = "tutorialId") int tutorialId,
																			@RequestParam(value = "langName") String langName) {
		System.out.println("*********************L");

		HashMap<Integer,String> topicName=new HashMap<>();

		Language lan=lanService.getByLanName(langName);
		Tutorial tut =tutService.getById(tutorialId);
		Category cat = catService.findBycategoryname(id);

		List<TopicCategoryMapping> local = topicCatService.findAllByCategory(cat) ;

		List<ContributorAssignedTutorial> cons=conService.findAllByTopicCat(local);

		List<ContributorAssignedTutorial> tempCon= new ArrayList<ContributorAssignedTutorial>();

		for(ContributorAssignedTutorial x : cons) {

			if(x.getLan().getLangName().equalsIgnoreCase(lan.getLangName())) {
				tempCon.add(x);
			}
		}

		List<Tutorial> tuts = tutService.findAllByContributorAssignedTutorialList(tempCon);

		for(Tutorial temp : tuts) {

			System.out.println("*********************");
			System.out.println(temp.getTutorialId());
			System.out.println("*********************");
			if(temp.getTutorialId() != tut.getTutorialId()) {
				
				if(temp.isStatus()) {
					topicName.put(temp.getTutorialId(), temp.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
				}
				
			}

		}
		return topicName;

	}


	/**
	 * Enable role from approve role tab under super admin role
	 * @param id int 
	 * @return String object
	 */
	@RequestMapping("/enableRoleById")
	public @ResponseBody String enableRoleById(@RequestParam(value = "id") long id) {

		UserRole usrRole=usrRoleService.findById(id);
	

		if(usrRole != null) {
			try {
				int val=usrRoleService.enableRole(usrRole);
				SimpleMailMessage msg = mailConstructor.approveRole(usrRole.getUser(), usrRole.getRole(), usrRole.getCategory(), usrRole.getLan());
				
				mailSender.send(msg);
				
				if(val == 0) {
					// return error
				}
			}catch (Exception e) {
				// TODO: handle exception

				// return error
			}


		}else {

			// return error
		}


		return CommonData.ROLE_APPROVED_SUCCESS_MSG;
	}

	/**
	 * delete role
	 * @param id int 
	 * @return String
	 */
	@RequestMapping("/deleteRole")
	public @ResponseBody String deleteMasterRoleById(@RequestParam(value = "id") long id) {

		UserRole usrRole=usrRoleService.findById(id);

		if(usrRole != null) {
			try {

				if(usrRole.getRole().getRoleId() == 7) {
					User usr = usrRole.getUser();
					usr.setOrgRolev(null);
					usr.setOrganization(null);
					usr.setAge(0);
					usr.setUserRoles(null);
					usrservice.save(usr);
				}
				
				System.out.println("vikash");
				
				usrRole.setRole(null);
				usrRole.setUser(null);
				usrRole.setCat(null);
				usrRole.setLan(null);
				usrRole.setRejected(true);
				usrRoleService.save(usrRole);
				
				SimpleMailMessage msg = mailConstructor.rejectRole(usrRole.getUser(), usrRole.getRole(), usrRole.getCategory(), usrRole.getLan());
				
				mailSender.send(msg);

			}catch (Exception e) {
				// TODO: handle exception

				// return error
			}


		}else {

			// return error
		}


		return "Role Disaaproved";
	}


	/**
	 * return the language for which user has contributor role
	 * @param username string
	 * @return list of string
	 */
	@RequestMapping("/loadLanguageByUser")
	public @ResponseBody List<String> getLanguageByContributor(@RequestParam(value = "id") String username) {

		List<String> langauges=new ArrayList<String>();

		User usr=usrservice.findByUsername(username);
		Role role=roleService.findByname(CommonData.contributorRole);
		Role role1=roleService.findByname(CommonData.externalContributorRole);

		List<UserRole> userRoles=usrRoleService.findAllByRoleUserStatus(role, usr, true);
		userRoles.addAll(usrRoleService.findAllByRoleUserStatus(role1, usr, true));
		for(UserRole temp:userRoles) {
			if(temp.getLanguage()!=null) {
				langauges.add(temp.getLanguage().getLangName());
			}
		}

		return langauges;
	}

	/**
	 * return all the category
	 * @return hashmap
	 */
	@RequestMapping("/loadCategory")
	public @ResponseBody HashMap<Integer, String> getCategory() {

		HashMap<Integer,String> categories=new HashMap<>();

		List<Category> categoriesTemp=catService.findAll();

		for(Category x:categoriesTemp) {
			categories.put(x.getCategoryId(), x.getCatName());
		}

		return categories;
	}

	/**
	 * return topic under category for contributor
	 * @param catName String 
	 * @param principal Principal object
	 * @return hashmap
	 */
	@RequestMapping("/loadTopicByCategoryOnContributorRole")
	public @ResponseBody HashMap<Integer, String> getTopicByCategoryOnContributorRole(@RequestParam(value = "id") String catName,Principal principal) {

		HashMap<Integer,String> topicName=new HashMap<>();

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Category cat = catService.findBycategoryname(catName);

		List<TopicCategoryMapping> localTopicCat = topicCatService.findAllByCategory(cat) ;

		List<ContributorAssignedMultiUserTutorial> conTutorialByUser=conMultiService.getAllByuser(usr);

		for(ContributorAssignedMultiUserTutorial localCon:conTutorialByUser) {

			for(TopicCategoryMapping topicTemp:localTopicCat) {
				
				ContributorAssignedTutorial conTemp = conService.findById(localCon.getConAssignedTutorial().getId());

				if(conTemp.getTopicCatId().getTopicCategoryId() == topicTemp.getTopicCategoryId()) {

					topicName.put(topicTemp.getTopic().getTopicId(), topicTemp.getTopic().getTopicName());
				}
			}
		}


		return topicName;

	}

	/**
	 * Load langauge for contributor role
	 * @param catName String 
	 * @param topicId int value
	 * @param principal Principal object
	 * @return Set
	 */
	@RequestMapping("/loadLanguageForContributorRoleTutorial")
	public @ResponseBody Set<String> getLanguageByContributorRole(@RequestParam(value = "id") String catName,
															@RequestParam(value = "topicId") int topicId,Principal principal) {

		Set<String> languages=new HashSet<String>();

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}
		Category cat = catService.findBycategoryname(catName);
		Topic topic=topicService.findById(topicId);
		TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);

		List<ContributorAssignedMultiUserTutorial> conTutorialByUser=conMultiService.getAllByuser(usr);

		for(ContributorAssignedMultiUserTutorial temp:conTutorialByUser) {

			ContributorAssignedTutorial conTemp = conService.findById(temp.getConAssignedTutorial().getId());
			if(conTemp.getTopicCatId().getTopicCategoryId() == localTopicCat.getTopicCategoryId()) {
				languages.add(conTemp.getLan().getLangName());
			}
		}

		return languages;
	}

	/*********************************** CONTRIBUTOR SIDE *********************************************/
	/**
	 * return outline given tutorial id
	 * @param tutorialId int
	 * @return string
	 */
	@RequestMapping("/viewOutline")
	public @ResponseBody String viewOutline(@RequestParam(value = "id") int tutorialId) {

		Tutorial tut=tutService.getById(tutorialId);
		if(tut.getOutline()!=null) {
			return tut.getOutline();
		}
		return null;

	}

	/**
	 * add outline to the tutorial
	 * @param tutorialId int 
	 * @param outlineData string
	 * @param catName string
	 * @param topicId int
	 * @param lanId string
	 * @param principal principal object
	 * @return string
	 */
	@RequestMapping("/addOutline")
	public @ResponseBody HashMap<Integer, String> addOutline(@RequestParam(value = "id") int tutorialId,
											@RequestParam(value = "saveOutline") String outlineData,
											@RequestParam(value = "categoryname") String catName,
											@RequestParam(value = "topicid") int topicId,
											@RequestParam(value = "lanId") String lanId,
											Principal principal) {

		HashMap<Integer, String> temp = new HashMap<>();
		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		if(tutorialId != 0) {
			Tutorial tut=tutService.getById(tutorialId);

			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.OUTLINE, CommonData.DOMAIN_STATUS, tut.getOutlineStatus(), CommonData.contributorRole, usr, tut);
			tut.setOutline(outlineData);
			tut.setOutlineStatus(CommonData.DOMAIN_STATUS);
			tut.setOutlineUser(usr);

			try {
				tutService.save(tut);
				logService.save(log);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				temp.put(0, "Please Try again");
				return temp;
			}
			temp.put(1, CommonData.Outline_SAVE_SUCCESS_MSG);
			return temp;

		}else {
			
			Category cat = catService.findBycategoryname(catName);
			Topic topic=topicService.findById(topicId);
			TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
			Language lan=lanService.getByLanName(lanId);
			ContributorAssignedTutorial conLocal=conService.findByTopicCatAndLanViewPart(localTopicCat, lan);
			Tutorial local=new Tutorial();
			local.setDateAdded(ServiceUtility.getCurrentTime());
			local.setConAssignedTutorial(conLocal);
			System.out.println(conLocal.getLan().getLangName());
			local.setOutline(outlineData);
			local.setOutlineStatus(CommonData.DOMAIN_STATUS);
			local.setOutlineUser(usr);
			local.setTutorialId(tutService.getNewId());
			
			
			if(!lan.getLangName().equalsIgnoreCase("English")) {
				Language lanEng = lanService.getByLanName("English");
				ContributorAssignedTutorial conLocal1 = conService.findByTopicCatAndLanViewPart(localTopicCat, lanEng);
				local.setRelatedVideo(tutService.findAllByContributorAssignedTutorial(conLocal1).get(0));
				
				Tutorial preReq =tutService.findAllByContributorAssignedTutorial(conLocal1).get(0).getPreRequistic();
				
				if(preReq == null) {
					local.setPreRequistic(null);
					local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
				}else {
					
					ContributorAssignedTutorial conLocal2 = conService.findByTopicCatAndLanViewPart(preReq.getConAssignedTutorial().getTopicCatId(),lan);
					local.setPreRequistic(tutService.findAllByContributorAssignedTutorial(conLocal2).get(0));
					local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
				}
				
				
				
			}

			try {
				tutService.save(local);
				System.out.println("in Exception-block");
				LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.OUTLINE, CommonData.DOMAIN_STATUS, 0, CommonData.contributorRole, usr, local);
				logService.save(log);
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				temp.put(0, "Please Try again");
				return temp;      // throw error
			}
		}

		temp.put(1, CommonData.Outline_SAVE_SUCCESS_MSG);
		return temp;
	}

	/**
	 * view keyword of tutorial given tutorial id
	 * @param tutorialId int
	 * @return string
	 */
	@RequestMapping("/viewKeyword")
	public @ResponseBody String viewkeyword(@RequestParam(value = "id") int tutorialId) {

		Tutorial tut=tutService.getById(tutorialId);
		if(tut.getKeyword()!=null) {
			return tut.getKeyword();
		}
		return null;

	}

	/**
	 * add keyword to the tutorial
	 * @param tutorialId int 
	 * @param keywordData string
	 * @param catName string
	 * @param topicId int
	 * @param lanId string
	 * @param principal principal object
	 * @return string
	 */
	@RequestMapping("/addKeyword")
	public @ResponseBody HashMap<Integer, String> addKeyword(@RequestParam(value = "id") int tutorialId,
											@RequestParam(value = "savekeyword") String keywordData,
											@RequestParam(value = "categoryname") String catName,
											@RequestParam(value = "topicid") int topicId,
											@RequestParam(value = "lanId") String lanId,
											Principal principal) {

		HashMap<Integer, String> temp = new HashMap<>();
		
		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		if(tutorialId != 0) {
			Tutorial tut=tutService.getById(tutorialId);
			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.KEYWORD, CommonData.DOMAIN_STATUS, tut.getKeywordStatus(), CommonData.contributorRole, usr, tut);
			tut.setKeyword(keywordData);
			tut.setKeywordStatus(CommonData.DOMAIN_STATUS);
			tut.setKeywordUser(usr);

			tutService.save(tut);

			logService.save(log);
			temp.put(1,  CommonData.Keyword_SAVE_SUCCESS_MSG);

		}else {

			Category cat = catService.findBycategoryname(catName);
			Topic topic=topicService.findById(topicId);
			TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
			Language lan=lanService.getByLanName(lanId);
			ContributorAssignedTutorial conLocal=conService.findByTopicCatAndLanViewPart(localTopicCat, lan);
			Tutorial local=new Tutorial();
			local.setDateAdded(ServiceUtility.getCurrentTime());
			local.setConAssignedTutorial(conLocal);
			local.setKeyword(keywordData);
			local.setKeywordStatus(CommonData.DOMAIN_STATUS);
			local.setKeywordUser(usr);
			local.setTutorialId(tutService.getNewId());
			

			try {
				tutService.save(local);

				LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.KEYWORD, CommonData.DOMAIN_STATUS, 0, CommonData.contributorRole, usr, local);
				logService.save(log);
				temp.put(1,  CommonData.Keyword_SAVE_SUCCESS_MSG);
			}catch (Exception e) {
				// TODO: handle exception
				temp.put(0,  "Please try again");      // throw error
			}


		}

		return temp;

	}


	/**
	 * Add pre requisite component of tutorial when none given
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String object
	 */
	@RequestMapping("/addPreRequisticWhenNotRequired")
	public @ResponseBody HashMap<Integer, String> addPreRequistic(@RequestParam(value = "id") int tutorialId,
			Principal principal) {
		System.out.println("******************************************Here");
		
		HashMap<Integer, String> temp = new HashMap<>();
		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}
		Tutorial tut = null;

		if(tutorialId != 0) {
			tut=tutService.getById(tutorialId);
		}


		if(tutorialId != 0) {

			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.PRE_REQUISTIC, CommonData.DOMAIN_STATUS, tut.getKeywordStatus(), CommonData.contributorRole, usr, tut);

			tut.setPreRequistic(null);
			tut.setPreRequisticStatus(CommonData.DOMAIN_STATUS);
			tut.setPreRequiticUser(usr);

			tutService.save(tut);

			logService.save(log);
			temp.put(1,  CommonData.PRE_REQUISTIC_SAVE_SUCCESS_MSG);

		}


		return temp;

	}

	/**
	 * Add pre requisite component of tutorial
	 * @param tutorialId int value
	 * @param catName String
 	 * @param topicId int
	 * @param lanId string
	 * @param principal Principal object
	 * @return string 
	 */
	@RequestMapping("/addPreRequistic")
	public @ResponseBody HashMap<Integer, String> addPreRequistic(@RequestParam(value = "id") int tutorialId,
			@RequestParam(value = "categoryname") String catName,
			@RequestParam(value = "topicid") int topicId,
			@RequestParam(value = "lanId") String lanId,Principal principal) {
		System.out.println("******************************************Here");
		
		HashMap<Integer, String> temp = new HashMap<>();
		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}
		Tutorial tut = null;

		if(tutorialId != 0) {
			tut=tutService.getById(tutorialId);
		}

		Tutorial tutorial_temp= tutService.getById(topicId);

		if(tutorialId != 0) {

			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.PRE_REQUISTIC, CommonData.DOMAIN_STATUS, tut.getKeywordStatus(), CommonData.contributorRole, usr, tut);

			tut.setPreRequistic(tutorial_temp);
			tut.setPreRequisticStatus(CommonData.DOMAIN_STATUS);
			tut.setPreRequiticUser(usr);

			tutService.save(tut);

			logService.save(log);
			temp.put(1,  CommonData.PRE_REQUISTIC_SAVE_SUCCESS_MSG);

		}

//		}else {
//

//			Category cat = catService.findBycategoryname(catName);
//			Topic topic=topicService.findById(topicId);
//			TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
//			Language lan=lanService.getByLanName(lanId);
//			ContributorAssignedTutorial conLocal=conService.findByUserTopicCatLan(usr, localTopicCat, lan);
//			Tutorial local=new Tutorial();
//			local.setDateAdded(ServiceUtility.getCurrentTime());
//			local.setConAssignedTutorial(conLocal);
//			local.setPreRequistic(tut);
//			local.setPreRequisticStatus(CommonData.DOMAIN_STATUS);
//			local.setTutorialId(tutService.getNewId());

//
//			try {
//				tutService.save(local);
//
//				LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.PRE_REQUISTIC, CommonData.DOMAIN_STATUS, 0, CommonData.contributorRole, usr, local);
//				logService.save(log);
//

//			}catch (Exception e) {
//				// TODO: handle exception
//				return "error";       // throw error
//			}



//
//
//		}

//>>>>>>> c22072d4967124d69c0e3524156a07ce6a2ad4e2
		return temp;

	}

	/**
	 * Add video component of tutorial
	 * @param tutorialId int value
	 * @param videoFile MultipartFile
	 * @param catName String
 	 * @param topicId int
	 * @param lanId string
	 * @param principal Principal object
	 * @return string 
	 */
	@RequestMapping("/addVideo")
	public @ResponseBody HashMap<Integer, String> addKeyword(@RequestParam(value = "id") int tutorialId,
											@RequestParam(value = "videoFileName") MultipartFile videoFile,
											@RequestParam(value = "categoryname") String catName,
											@RequestParam(value = "topicid") int topicId,
											@RequestParam(value = "lanId") String lanId,
											Principal principal) {

		
		HashMap<Integer, String> temp = new HashMap<>();
		
		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}
		
		
		if(!ServiceUtility.checkFileExtensionVideo(videoFile)) { // throw error on extension
			
			temp.put(0, "File extension must be in MP4") ;
			return temp;
		}
		
		if(!ServiceUtility.checkVideoSize(videoFile)) {
			
			temp.put(0, "File Size must be under 400 MB") ;
			return temp;
		}

		if(tutorialId != 0) {
			Tutorial tut=tutService.getById(tutorialId);
			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO, CommonData.ADMIN_STATUS, tut.getVideoStatus(), CommonData.contributorRole, usr, tut);

			try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/Video");
					String pathtoUploadPoster=ServiceUtility.uploadVideoFile(videoFile, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/Video");
					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

					tut.setVideo(document);
					tut.setVideoStatus(CommonData.ADMIN_STATUS);
					tut.setVideoUser(usr);

					logService.save(log);
					tutService.save(tut);

					temp.put(1, CommonData.Video_SAVE_SUCCESS_MSG) ;

			}catch (Exception e) {
				// TODO: handle exception
				// throw error
				temp.put(0, "Please Try again") ;
			}

		}else {

			Category cat = catService.findBycategoryname(catName);
			Topic topic=topicService.findById(topicId);
			TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
			Language lan=lanService.getByLanName(lanId);
			ContributorAssignedTutorial conLocal=conService.findByTopicCatAndLanViewPart(localTopicCat,lan);
			int newTutorialid=tutService.getNewId();

			Tutorial local=new Tutorial();
			local.setDateAdded(ServiceUtility.getCurrentTime());
			local.setConAssignedTutorial(conLocal);
			local.setTutorialId(newTutorialid);
			
			
			if(!lan.getLangName().equalsIgnoreCase("English")) {
				Language lanEng = lanService.getByLanName("English");
				ContributorAssignedTutorial conLocal1 = conService.findByTopicCatAndLanViewPart(localTopicCat, lanEng);
				local.setRelatedVideo(tutService.findAllByContributorAssignedTutorial(conLocal1).get(0));
				
				Tutorial preReq =tutService.findAllByContributorAssignedTutorial(conLocal1).get(0).getPreRequistic();
				
				if(preReq == null) {
					local.setPreRequistic(null);
					local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
				}else {
					
					ContributorAssignedTutorial conLocal2 = conService.findByTopicCatAndLanViewPart(preReq.getConAssignedTutorial().getTopicCatId(),lan);
					local.setPreRequistic(tutService.findAllByContributorAssignedTutorial(conLocal2).get(0));
					local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
				}
			}
			
		

			try {
				tutService.save(local);

				if(ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+newTutorialid+"/Video")) {
					String pathtoUploadPoster=ServiceUtility.uploadVideoFile(videoFile, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+newTutorialid+"/Video");
					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
					Tutorial tut1=tutService.getById(newTutorialid);
					tut1.setVideo(document);
					tut1.setVideoStatus(CommonData.ADMIN_STATUS);
					tut1.setVideoUser(usr);
					tutService.save(tut1);

					LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.KEYWORD, CommonData.DOMAIN_STATUS, 0, CommonData.contributorRole, usr, local);
					logService.save(log);

					temp.put(1, CommonData.Video_SAVE_SUCCESS_MSG) ;

				}else {

					temp.put(0, "Please Try again") ; /////////////////  throw error
				}


			}catch (Exception e) {
				// TODO: handle exception
				temp.put(0, "Please Try again") ;     // throw error
			}


		}

		return temp;

	}

	/**
	 * Add Slide component of tutorial
	 * @param tutorialId int value
	 * @param videoFile MultipartFile
	 * @param catName String
 	 * @param topicId int
	 * @param lanId string
	 * @param principal Principal object
	 * @return string 
	 */
	@RequestMapping("/addSlide")
	public @ResponseBody HashMap<Integer, String> addSlide(@RequestParam(value = "id") int tutorialId,
											@RequestParam(value = "uploadsSlideFile") MultipartFile videoFile,
											@RequestParam(value = "categoryname") String catName,
											@RequestParam(value = "topicid") int topicId,
											@RequestParam(value = "lanId") String lanId,
											Principal principal) {

		HashMap<Integer, String> temp = new HashMap<>();
		
		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		
		if(!ServiceUtility.checkFileExtensionZip(videoFile)) { // throw error on extension
			
			temp.put(0, "File extension must be in ZIP") ;
			return temp;
		}
		
		if(!ServiceUtility.checkScriptSlideProfileQuestion(videoFile)) {
			
			temp.put(0, "File Size must be under 20 MB") ;
			return temp;
		}
		
		if(tutorialId != 0) {
			Tutorial tut=tutService.getById(tutorialId);
			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE, CommonData.DOMAIN_STATUS, tut.getSlideStatus(), CommonData.contributorRole, usr, tut);

			try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/Slide");
					String pathtoUploadPoster=ServiceUtility.uploadVideoFile(videoFile, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/Slide");
					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

					tut.setSlide(document);
					tut.setSlideStatus(CommonData.DOMAIN_STATUS);
					tut.setSlideUser(usr);
					tutService.save(tut);
					logService.save(log);
					temp.put(1, CommonData.Slide_SAVE_SUCCESS_MSG) ;

			}catch (Exception e) {
				// TODO: handle exception
				temp.put(0, "Please Try again") ;
				// throw error
			}

		}else {

			Category cat = catService.findBycategoryname(catName);
			Topic topic=topicService.findById(topicId);
			TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
			Language lan=lanService.getByLanName(lanId);
			ContributorAssignedTutorial conLocal=conService.findByTopicCatAndLanViewPart(localTopicCat, lan);
			int newTutorialid=tutService.getNewId();

			Tutorial local=new Tutorial();
			local.setDateAdded(ServiceUtility.getCurrentTime());
			local.setConAssignedTutorial(conLocal);
			local.setTutorialId(newTutorialid);
			

			try {
				tutService.save(local);

				if(ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+newTutorialid+"/Slide")) {
					String pathtoUploadPoster=ServiceUtility.uploadVideoFile(videoFile, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+newTutorialid+"/Slide");
					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
					Tutorial tut1=tutService.getById(newTutorialid);
					tut1.setSlide(document);
					tut1.setSlideStatus(CommonData.DOMAIN_STATUS);
					tut1.setSlideUser(usr);
					tutService.save(tut1);

					LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE, CommonData.DOMAIN_STATUS, 0, CommonData.contributorRole, usr, local);
					logService.save(log);
					temp.put(1, CommonData.Slide_SAVE_SUCCESS_MSG) ;

				}else {

					temp.put(0, "Please Try again") ; /////////////////  throw error
				}


			}catch (Exception e) {
				// TODO: handle exception
				temp.put(0, "Please Try again") ;     // throw error
			}


		}

		return temp;

	}

	/**
	 * Add script component of tutorial
	 * @param tutorialId int value
	 * @param videoFile MultipartFile
	 * @param catName String
 	 * @param topicId int
	 * @param lanId string
	 * @param principal Principal object
	 * @return string 
	 */
	@RequestMapping("/addScript")
	public @ResponseBody HashMap<Integer, String> addScript(@RequestParam(value = "id") int tutorialId,
											@RequestParam(value = "uploadsScriptFile") MultipartFile videoFile,
											@RequestParam(value = "categoryname") String catName,
											@RequestParam(value = "topicid") int topicId,
											@RequestParam(value = "lanId") String lanId,
											Principal principal) {

		HashMap<Integer, String> temp = new HashMap<>();
		
		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}
		
		
		if(!ServiceUtility.checkFileExtensiononeFilePDF(videoFile)) { // throw error on extension
			
			temp.put(0, "File extension must be in PDF") ;
			return temp;
		}
		
		if(!ServiceUtility.checkScriptSlideProfileQuestion(videoFile)) {
			
			temp.put(0, "File Size must be under 20 MB") ;
			return temp;
		}

		if(tutorialId != 0) {
			Tutorial tut=tutService.getById(tutorialId);
			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT, CommonData.DOMAIN_STATUS, tut.getScriptStatus(), CommonData.contributorRole, usr, tut);
			try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/Script");
					String pathtoUploadPoster=ServiceUtility.uploadVideoFile(videoFile, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/Script");
					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

					tut.setScript(document);
					tut.setScriptStatus(CommonData.DOMAIN_STATUS);
					tut.setScriptUser(usr);
					try {
						tutService.save(tut);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						temp.put(0, "Please Try again") ;
						return temp;
					}

					logService.save(log);
					temp.put(1, CommonData.Script_SAVE_SUCCESS_MSG);
					return temp;

			}catch (Exception e) {
				temp.put(0, "Please Try again") ;
				return temp;
			}

		}else {

			Category cat = catService.findBycategoryname(catName);
			Topic topic=topicService.findById(topicId);
			TopicCategoryMapping localTopicCat = topicCatService.findAllByCategoryAndTopic(cat, topic);
			Language lan=lanService.getByLanName(lanId);
			ContributorAssignedTutorial conLocal=conService.findByTopicCatAndLanViewPart( localTopicCat, lan);
			int newTutorialid=tutService.getNewId();

			Tutorial local=new Tutorial();
			local.setDateAdded(ServiceUtility.getCurrentTime());
			local.setConAssignedTutorial(conLocal);
			local.setTutorialId(newTutorialid);
			
			
			if(!lan.getLangName().equalsIgnoreCase("English")) {
				Language lanEng = lanService.getByLanName("English");
				ContributorAssignedTutorial conLocal1 = conService.findByTopicCatAndLanViewPart(localTopicCat, lanEng);
				local.setRelatedVideo(tutService.findAllByContributorAssignedTutorial(conLocal1).get(0));
				
				Tutorial preReq =tutService.findAllByContributorAssignedTutorial(conLocal1).get(0).getPreRequistic();
				
				if(preReq == null) {
					local.setPreRequistic(null);
					local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
				}else {
					
					ContributorAssignedTutorial conLocal2 = conService.findByTopicCatAndLanViewPart(preReq.getConAssignedTutorial().getTopicCatId(),lan);
					local.setPreRequistic(tutService.findAllByContributorAssignedTutorial(conLocal2).get(0));
					local.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
					local.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
				}
			}
			

			try {
				tutService.save(local);

				if(ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+newTutorialid+"/Script")) {
					String pathtoUploadPoster=ServiceUtility.uploadVideoFile(videoFile, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+newTutorialid+"/Script");
					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
					Tutorial tut1=tutService.getById(newTutorialid);
					tut1.setScript(document);
					tut1.setScriptStatus(CommonData.DOMAIN_STATUS);
					tut1.setScriptUser(usr);
					tutService.save(tut1);

					LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT, CommonData.DOMAIN_STATUS, 0, CommonData.contributorRole, usr, local);
					logService.save(log);
					temp.put(1, CommonData.Script_SAVE_SUCCESS_MSG);
					return temp;

				}else {

					temp.put(0, "Please Try again") ;
					return temp; /////////////////  throw error
				}


			}catch (Exception e) {
				// TODO: handle exception
				temp.put(0, "Please Try again") ;
				return temp;       // throw error
			}


		}


	}



	/*************************************** END ******************************************************/


	/********************************** operation at Admin End *****************************************/
	
	/**
	 * accept video component from admin reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptAdminVideo")
	public @ResponseBody String addAdminVideo(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO, CommonData.DOMAIN_STATUS, tutorial.getVideoStatus(), CommonData.adminReviewerRole, usr, tutorial);

		tutorial.setVideoStatus(CommonData.DOMAIN_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Video_STATUS_SUCCESS_MSG;

	}



	/***********************************END ***************************************************************/

	/********************************** operation at DOMAIN USER *****************************************/
	
	/**
	 * accept outline component from domain reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptDomainOutline")
	public @ResponseBody String acceptDomainOutline(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.OUTLINE, CommonData.QUALITY_STATUS, tutorial.getOutlineStatus(), CommonData.domainReviewerRole, usr, tutorial);
		tutorial.setOutlineStatus(CommonData.QUALITY_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Outline_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept script component from domain reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptDomainScript")
	public @ResponseBody String acceptDomainScript(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT, CommonData.QUALITY_STATUS, tutorial.getScriptStatus(), CommonData.domainReviewerRole, usr, tutorial);

		tutorial.setScriptStatus(CommonData.QUALITY_STATUS);
		tutService.save(tutorial);
		logService.save(log);

		return CommonData.Script_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept video component from domain reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptDomainVideo")
	public @ResponseBody String acceptDomainVideo(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO, CommonData.QUALITY_STATUS, tutorial.getVideoStatus(), CommonData.domainReviewerRole, usr, tutorial);

		tutorial.setVideoStatus(CommonData.QUALITY_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Video_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept slide component from domain reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptDomainSlide")
	public @ResponseBody String acceptDomainSlide(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE, CommonData.QUALITY_STATUS, tutorial.getSlideStatus(), CommonData.domainReviewerRole, usr, tutorial);
		tutorial.setSlideStatus(CommonData.QUALITY_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Slide_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept keyword component from domain reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptDomainKeywords")
	public @ResponseBody String acceptDomainKeywords(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.KEYWORD, CommonData.QUALITY_STATUS, tutorial.getKeywordStatus(), CommonData.domainReviewerRole, usr, tutorial);
		tutorial.setKeywordStatus(CommonData.QUALITY_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Keyword_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept pre requisite component from domain reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptDomainPreRequistic")
	public @ResponseBody String acceptDomainPreRequistic(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}
		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.PRE_REQUISTIC, CommonData.QUALITY_STATUS, tutorial.getPreRequisticStatus(), CommonData.domainReviewerRole, usr, tutorial);
		tutorial.setPreRequisticStatus(CommonData.QUALITY_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.PRE_REQUISTIC_SAVE_SUCCESS_MSG;

	}



	/***********************************END ***************************************************************/

	/********************************** operation at Quality USER *****************************************/
	
	/**
	 * accept outline component from quality reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptQualityOutline")
	public @ResponseBody String acceptQualityOutline(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.OUTLINE, CommonData.WAITING_PUBLISH_STATUS, tutorial.getOutlineStatus(), CommonData.qualityReviewerRole, usr, tutorial);
		tutorial.setOutlineStatus(CommonData.WAITING_PUBLISH_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Outline_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept script component from quality reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptQualityScript")
	public @ResponseBody String acceptQualityScript(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SCRIPT, CommonData.WAITING_PUBLISH_STATUS, tutorial.getScriptStatus(), CommonData.qualityReviewerRole, usr, tutorial);
		tutorial.setScriptStatus(CommonData.WAITING_PUBLISH_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Script_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept video component from quality reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptQualityVideo")
	public @ResponseBody String acceptQualityVideo(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO, CommonData.WAITING_PUBLISH_STATUS, tutorial.getVideoStatus(), CommonData.qualityReviewerRole, usr, tutorial);
		tutorial.setVideoStatus(CommonData.WAITING_PUBLISH_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Video_STATUS_SUCCESS_MSG;

	}

	/**
	 * accept slide component from quality reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptQualitySlide")
	public @ResponseBody String acceptQualitySlide(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.SLIDE, CommonData.WAITING_PUBLISH_STATUS, tutorial.getSlideStatus(), CommonData.qualityReviewerRole, usr, tutorial);
		tutorial.setSlideStatus(CommonData.WAITING_PUBLISH_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Slide_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept keyword component from quality reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptQualityKeywords")
	public @ResponseBody String acceptQualityKeywords(@RequestParam(value = "id") int tutorialId,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.KEYWORD, CommonData.WAITING_PUBLISH_STATUS, tutorial.getKeywordStatus(), CommonData.qualityReviewerRole, usr, tutorial);
		tutorial.setKeywordStatus(CommonData.WAITING_PUBLISH_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.Keyword_SAVE_SUCCESS_MSG;

	}

	/**
	 * accept pre requisite component from quality reviewer
	 * @param tutorialId int value
	 * @param principal Principal object
	 * @return String
	 */
	@RequestMapping("/acceptQualityPreRequistic")
	public @ResponseBody String acceptQualityPreRequistic(@RequestParam(value = "id") int tutorialId, Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tutorial=tutService.getById(tutorialId);
		LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.PRE_REQUISTIC, CommonData.WAITING_PUBLISH_STATUS, tutorial.getPreRequisticStatus(), CommonData.qualityReviewerRole, usr, tutorial);
		tutorial.setPreRequisticStatus(CommonData.WAITING_PUBLISH_STATUS);
		tutService.save(tutorial);
		logService.save(log);
		return CommonData.PRE_REQUISTIC_SAVE_SUCCESS_MSG;

	}



	/***********************************END ***************************************************************/
/******************************* COMMENT MODULE UNDER CREATION PART ********************************/

	/**
	 * records comment made by user under admin role interface
	 * @param tutorialId int value
	 * @param msg string
	 * @param principal Principal object
	 * @return string
	 */
	@RequestMapping("/commentByAdminReviewer")
	public @ResponseBody String commentByAdminReviewer(@RequestParam(value = "id") int tutorialId,
													@RequestParam(value = "msg") String msg, Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tut = tutService.getById(tutorialId);

		Comment com = new Comment();
		com.setComment(msg);
		com.setCommentId(comService.getNewCommendId());
		com.setDateAdded(ServiceUtility.getCurrentTime());
		com.setType(CommonData.VIDEO);
		com.setUser(usr);
		com.setTutorialInfos(tut);
		com.setRoleName(CommonData.adminReviewerRole);

		try {
			comService.save(com);

			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), CommonData.VIDEO, CommonData.IMPROVEMENT_STATUS, tut.getVideoStatus(), CommonData.adminReviewerRole, usr, tut);
			tut.setVideoStatus(CommonData.IMPROVEMENT_STATUS);
			tutService.save(tut);
			logService.save(log);
			return CommonData.COMMENT_SUCCESS;

		}catch (Exception e) {
			// TODO: handle exception
			return CommonData.FAILURE;
		}



	}

	/**
	 * records comment made by user given type of component
	 * @param tutorialId int value
	 * @param msg string
	 * @param type string
	 * @param role String
	 * @param principal Principal object
	 * @return string
	 */
	@RequestMapping("/commentByReviewer")
	public @ResponseBody String commentByReviewer(@RequestParam(value = "id") int tutorialId,
													@RequestParam(value = "msg") String msg,
													@RequestParam(value = "type") String type,
													@RequestParam(value = "role") String role,
													Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		String roleName = null;
		int statusvalue=0;
		String typeValue=null;

		if(role.equalsIgnoreCase("Quality")) {
			roleName=CommonData.qualityReviewerRole;
		}else if(role.equalsIgnoreCase("Domain")) {
			roleName=CommonData.domainReviewerRole;
		}
		Tutorial tut = tutService.getById(tutorialId);

		Comment com = new Comment();
		com.setComment(msg);
		com.setCommentId(comService.getNewCommendId());
		com.setDateAdded(ServiceUtility.getCurrentTime());
		com.setRoleName(roleName);
		if(type.equalsIgnoreCase(CommonData.SCRIPT)) {
			com.setType(CommonData.SCRIPT);
			statusvalue = tut.getScriptStatus();
			typeValue = CommonData.SCRIPT;

		}else if(type.equalsIgnoreCase(CommonData.KEYWORD)) {
			com.setType(CommonData.KEYWORD);
			statusvalue = tut.getKeywordStatus();
			typeValue = CommonData.KEYWORD;

		}else if(type.equalsIgnoreCase(CommonData.SLIDE)) {
			com.setType(CommonData.SLIDE);
			statusvalue = tut.getSlideStatus();
			typeValue = CommonData.SLIDE;

		}else if(type.equalsIgnoreCase(CommonData.VIDEO)) {
			com.setType(CommonData.VIDEO);
			statusvalue = tut.getVideoStatus();
			typeValue = CommonData.VIDEO;

		}else if(type.equalsIgnoreCase(CommonData.PRE_REQUISTIC)) {
			com.setType(CommonData.PRE_REQUISTIC);
			statusvalue = tut.getPreRequisticStatus();
			typeValue = CommonData.PRE_REQUISTIC;

		}else if(type.equalsIgnoreCase(CommonData.OUTLINE)) {
			com.setType(CommonData.OUTLINE);
			statusvalue = tut.getOutlineStatus();
			typeValue = CommonData.OUTLINE;

		}else {

			return CommonData.FAILURE;
		}

		com.setUser(usr);
		com.setTutorialInfos(tut);



		try {
			comService.save(com);

			LogManegement log = new LogManegement(logService.getNewId(), ServiceUtility.getCurrentTime(), typeValue, CommonData.IMPROVEMENT_STATUS, statusvalue, roleName, usr, tut);

			if(type.equalsIgnoreCase(CommonData.SCRIPT)) {
				tut.setScriptStatus(CommonData.IMPROVEMENT_STATUS);
			}else if(type.equalsIgnoreCase(CommonData.KEYWORD)) {
				tut.setKeywordStatus(CommonData.IMPROVEMENT_STATUS);
			}else if(type.equalsIgnoreCase(CommonData.SLIDE)) {
				tut.setSlideStatus(CommonData.IMPROVEMENT_STATUS);
			}else if(type.equalsIgnoreCase(CommonData.VIDEO)) {
				tut.setVideoStatus(CommonData.IMPROVEMENT_STATUS);
			}else if(type.equalsIgnoreCase(CommonData.PRE_REQUISTIC)) {
				tut.setPreRequisticStatus(CommonData.IMPROVEMENT_STATUS);
			}else if(type.equalsIgnoreCase(CommonData.OUTLINE)) {
				tut.setOutlineStatus(CommonData.IMPROVEMENT_STATUS);
			}

			tutService.save(tut);
			logService.save(log);

			return CommonData.COMMENT_SUCCESS;

		}catch (Exception e) {
			// TODO: handle exception
			return CommonData.FAILURE;
		}



	}


	/**
	 * records comment made by user under contributor role given type of component
	 * @param tutorialId int value
	 * @param msg string
	 * @param type string
	 * @param principal Principal object
	 * @return string
	 */
	@RequestMapping("/commentByContributor")
	public @ResponseBody String commentByContributor(@RequestParam(value = "id") int tutorialId,
													@RequestParam(value = "type") String type,
													@RequestParam(value = "msg") String msg, Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		Tutorial tut = tutService.getById(tutorialId);

		Comment com = new Comment();
		com.setComment(msg);
		com.setCommentId(comService.getNewCommendId());
		com.setDateAdded(ServiceUtility.getCurrentTime());
		com.setUser(usr);
		com.setTutorialInfos(tut);
		com.setRoleName(CommonData.contributorRole);

		if(type.equalsIgnoreCase(CommonData.SCRIPT)) {
			com.setType(CommonData.SCRIPT);

		}else if(type.equalsIgnoreCase(CommonData.KEYWORD)) {
			com.setType(CommonData.KEYWORD);

		}else if(type.equalsIgnoreCase(CommonData.SLIDE)) {
			com.setType(CommonData.SLIDE);

		}else if(type.equalsIgnoreCase(CommonData.VIDEO)) {
			com.setType(CommonData.VIDEO);

		}else if(type.equalsIgnoreCase(CommonData.PRE_REQUISTIC)) {
			com.setType(CommonData.PRE_REQUISTIC);

		}else if(type.equalsIgnoreCase(CommonData.OUTLINE)) {
			com.setType(CommonData.OUTLINE);

		}

		try {
			comService.save(com);

			return CommonData.COMMENT_SUCCESS;

		}catch (Exception e) {
			// TODO: handle exception
			return CommonData.FAILURE;
		}



	}


/**
 * Add user details from homepage to database
 * @param contactData FeedbackForm object
 * @return list of String
 */
	@PostMapping("/addContactForm")
	public @ResponseBody List<String> addContactData(@Valid @RequestBody FeedbackForm contactData){
		List<String> status=new ArrayList<String>();



		try {
			FeedbackForm addLocal=new FeedbackForm();
			addLocal.setId(ffService.getNewId());
			addLocal.setDateAdded(ServiceUtility.getCurrentTime());
			addLocal.setEmail(contactData.getEmail());
			addLocal.setMessage(contactData.getMessage());
			addLocal.setName(contactData.getName());

			ffService.save(addLocal);
			status.add("Success");

		} catch (Exception e) {

			status.add("Failure");
			e.printStackTrace();
		}



		return status;
	}


	/**
	 * add profile picture of user
	 * @param uploadPhoto MultipartFile
	 * @param principal Principal object
	 * @return string
	 * @throws Exception
	 */
	@PostMapping("/updateProfilePic")
	public @ResponseBody String updateProfilePic(@RequestParam("profilePicture") MultipartFile uploadPhoto,Principal principal) throws Exception{


		ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadUserImage+principal.getName());

		String createFolder=env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadUserImage+principal.getName();

		String documentLocal=ServiceUtility.uploadFile(uploadPhoto, createFolder);

		int indexToStart=documentLocal.indexOf("Media");

		String document=documentLocal.substring(indexToStart, documentLocal.length());

		User usr=usrservice.findByUsername(principal.getName());
		usr.setProfilePic(document);

		usrservice.save(usr);

		return "ok";
	}

	/**
	 * return consultant record
	 * @param consultantId int value
	 * @return list of String
	 */
	@GetMapping("/getConsultantDetails")
	public @ResponseBody List<String> getConsultantDetailsInfo(@RequestParam(value = "id") int consultantId) {
		System.err.print("inside*********************************************");
		System.out.println("inside test *********************************************");
		Consultant cons = consultantService.findById(consultantId);
		User user = cons.getUser();


		List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(roleService.findByname(CommonData.domainReviewerRole), user, true);
		List<String> cat_lang = new ArrayList();
		if(userRoles.size()>0) {
			for (UserRole u : userRoles) {
				  if (u.getRole().getRoleId()==4) {

					  cat_lang.add(u.getCat().getCatName() + " - "+  u.getLan().getLangName());

				}
			}
		}

		System.err.print("cat-lang*********************************************");
		System.err.print(cat_lang);
		System.err.print("cat-lang*********************************************");
		return cat_lang;

	}

	/**
	 * returns all the language available in the database
	 * @return list of string
	 */
	@RequestMapping("/loadLanguages")
	public @ResponseBody List<String> getLanguages() {

		List<String> langauges=new ArrayList<String>();
		List<Language> langs = langService.getAllLanguages();

		for(Language temp:langs) {
				langauges.add(temp.getLangName());
		}

		return langauges;
	}

	/************************************ END ********************************************************/
	
	/**************************** UPLOAD TIME SCRIPT ***********************************************/
	
	/**
	 * add Timescript from contributor role
	 * @param tutorialId int value
	 * @param File  MultipartFile
	 * @param principal Principal
	 * @return string
	 */
	@RequestMapping("/addTimeScript")
	public @ResponseBody String addTimeScript(@RequestParam(value = "id") int tutorialId,
			@RequestParam(value = "uploadsScriptFile") MultipartFile File,
											Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=usrservice.findByUsername(principal.getName());
		}

		if(tutorialId != 0) {
			Tutorial tut=tutService.getById(tutorialId);
			
			try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/TimeScript");
					String pathtoUploadPoster=ServiceUtility.uploadFile(File, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial+tut.getTutorialId()+"/TimeScript");
					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

					tut.setTimeScript(document);
					tutService.save(tut);

					return CommonData.Script_SAVE_SUCCESS_MSG;

			}catch (Exception e) {
				// TODO: handle exception

				// throw error
			}

		}
		return CommonData.Script_SAVE_SUCCESS_MSG;

	}
	
	/*********************************** END ********************************************************/
	
	@RequestMapping("/tutCountOnCat")
	public @ResponseBody String getTotalCountCat(String id) {

		Category cat = catService.findBycategoryname(id);
		int total = 0;
		List<Tutorial> tutorials = tutService.findAllBystatus(true);
		
		for(Tutorial temp :tutorials) {
			if(temp.getConAssignedTutorial().getTopicCatId().getCat().getCatName().equalsIgnoreCase(cat.getCatName())) {
				total++;
			}
		}
		
		return "Total number of tutorial under "+id +" is "+total;
	}
	
	@RequestMapping("/tutCountOnLan")
	public @ResponseBody String getTotalCountLan(String id) {

		Language lan = lanService.getByLanName(id);
		int total = 0;
		List<Tutorial> tutorials = tutService.findAllBystatus(true);
		
		for(Tutorial temp :tutorials) {
			if(temp.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase(lan.getLangName())) {
				total++;
			}
		}
		
		return "Total number of tutorial under "+id +" Language is "+total;
	}
		
}
