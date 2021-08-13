package com.health.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
import com.health.model.Event;
import com.health.model.FeedbackMasterTrainer;
import com.health.model.IndianLanguage;
import com.health.model.Language;
import com.health.model.OrganizationRole;
import com.health.model.PostQuestionaire;
import com.health.model.Question;
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
import com.health.service.IndianLanguageService;
import com.health.service.LanguageService;
import com.health.service.OrganizationRoleService;
import com.health.service.PostQuestionaireService;
import com.health.service.QuestionService;
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
import com.health.utility.CommonData;
import com.health.utility.MailConstructor;
import com.health.utility.SecurityUtility;
import com.health.utility.ServiceUtility;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;

/**
 * This Controller Class takes website request and process it accordingly
 * @author om prakash soni
 * @version 1.0
 *
 */
@Controller
public class HomeController {

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
	private IndianLanguageService iLanService;

	@Autowired
	private UserIndianLanguageMappingService userIndianMappingService;

	@Autowired
	private CarouselService caroService;

	@Autowired
	private OrganizationRoleService organizationRoleService;

    private static YouTube youtube;
    
	private static final String VIDEO_FILE_FORMAT = "video/*";

	/**
	 * Index page Url
	 * @param model Model Object
	 * @return String object (Webpapge)
	 */

	@RequestMapping("/")
	public String index(Model model) {

		List<Event> events=eventservice.findAll();
		List<Testimonial> testi=testService.findByApproved(true);
		List<Consultant> consults= consultService.findByOnHome(true);
		List<Category> categories= catService.findAll();
		List<Language> languages = lanService.getAllLanguages();
		List<Brouchure> brochures= broService.findAll();
		List<Carousel> carousel= caroService.findAll();

		Set<String> catTemp = new HashSet<String>();
		Set<String> topicTemp = new HashSet<String>();
		Set<String> lanTemp = new HashSet<String>();

		List<Tutorial> tutorials = tutService.findAllBystatus(true);
		for(Tutorial temp :tutorials) {
			catTemp.add(temp.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
			lanTemp.add(temp.getConAssignedTutorial().getLan().getLangName());
			topicTemp.add(temp.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
		}
		
		List<String> catTempSorted =new ArrayList<String>(catTemp);
		Collections.sort(catTempSorted);
		
		List<String> lanTempSorted =new ArrayList<String>(lanTemp);
		Collections.sort(lanTempSorted);
		
		List<Event> evnHome = new ArrayList<>();
		List<Testimonial> testHome = new ArrayList<>();
		List<Consultant> consulHome = new ArrayList<>();
		List<Category> categoryHome = new ArrayList<>();
		List<Brouchure> brochureHome = new ArrayList<>();
		List<Carousel> carouselHome = new ArrayList<>();



		int upperlimit = 0;

		for(Event local : events) {
			evnHome.add(local);
			if(++upperlimit > 3) {
				break;
			}
		}

		upperlimit = 0;

		for(Testimonial local : testi) {
			testHome.add(local);
			if(++upperlimit > 3) {
				break;
			}
		}

		upperlimit = 0;

		for(Consultant local : consults) {
			if(local.isOnHome()) {
				consulHome.add(local);
			}
			if(++upperlimit > 4) {
				break;
			}
		}
//		set upper limit for categories count
		upperlimit = 4 ;
		categoryHome=(categories.size()>upperlimit) ? categories.subList(0, upperlimit):categories;
		brochureHome=(brochures.size()>upperlimit) ? brochures.subList(0, upperlimit):brochures;
		carouselHome=(carousel.size()>upperlimit) ? carousel.subList(0, upperlimit):carousel;

		if(!consulHome.isEmpty()) {
			model.addAttribute("listOfConsultant", consulHome);
		}

		if(!testHome.isEmpty()) {
			model.addAttribute("listofTestimonial", testHome);
		}

		if(!categoryHome.isEmpty()) {
			model.addAttribute("listofCategories", categoryHome);
		}

		if(!evnHome.isEmpty()) {
			model.addAttribute("events", evnHome);
		}

		if(!brochureHome.isEmpty()) {
			model.addAttribute("listofBrochures", brochureHome);
		}


		model.addAttribute("languageCount", languages.size());
		model.addAttribute("videoCount", tutService.findAll().size());
		model.addAttribute("consultantCount", consults.size());



		model.addAttribute("categories", catTempSorted);
		model.addAttribute("languages", lanTempSorted);
		model.addAttribute("topics", topicTemp);

		if(!carouselHome.isEmpty()) {
			model.addAttribute("carousel", carouselHome.get(0));
			model.addAttribute("carouselList", carouselHome.subList(1, carousel.size()));
		}



//		EPLiteClient client = new EPLiteClient("http://localhost:9001", "bb1fe836e7b81e0158d210baa1c7a9c854b74a183da71f8fa0a32eb108952961");
//		client.createPad("my_pad1");
//		client.setText("my_pad1", "foo!!");
//		String text = client.getText("Demo").get("text").toString();
//		Map result = client.listAllPads();
//		List padIds = (List) result.get("padIDs");


		System.err.println("*************************");
		System.err.println("EPLiteClient");
//		System.err.println(text);
//		System.err.println(padIds);

		System.err.println("*************************");

		return "index";
	}

	/**
	 * Redirects to Tutorials Page
	 * @param req HttpServletRequest object
	 * @param cat String object
	 * @param topic String object
	 * @param lan String object
	 * @param principal principal object
	 * @param model model object
	 * @param page int value
	 * @return String object (Webpapge)
	 */
	@RequestMapping(value = "/tutorials", method = RequestMethod.GET)
	public String viewCoursesAvailable(HttpServletRequest req,
			@RequestParam(name = "categoryName") String cat,
			@RequestParam(name = "topic") String topic,
			@RequestParam(name = "lan") String lan,
			@RequestParam(name ="page",defaultValue = "0") int page , Principal principal,Model model) {

		Set<String> catTemp = new HashSet<String>();
		Set<String> topicTemp = new HashSet<String>();
		Set<String> lanTemp = new HashSet<String>();

		List<Tutorial> tutorials = tutService.findAllBystatus(true);
		for(Tutorial temp :tutorials) {
			catTemp.add(temp.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
			lanTemp.add(temp.getConAssignedTutorial().getLan().getLangName());
			topicTemp.add(temp.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
		}
		
		List<String> catTempSorted =new ArrayList<String>(catTemp);
		Collections.sort(catTempSorted);
		
		List<String> lanTempSorted =new ArrayList<String>(lanTemp);
		Collections.sort(lanTempSorted);

		model.addAttribute("categories", catTempSorted);
		model.addAttribute("languages", lanTempSorted);
		model.addAttribute("topics", topicTemp);
		
		model.addAttribute("category", cat);
		model.addAttribute("language", lan);
		model.addAttribute("topic", topic);

		Category localCat = null;
		Language localLan = null;
		Topic localTopic = null;
		TopicCategoryMapping localTopicCat = null;
		List<TopicCategoryMapping> localTopicCatList = null;
		List<ContributorAssignedTutorial> conAssigTutorialList =null;
		ContributorAssignedTutorial conAssigTutorial = null;

		Page<Tutorial> tut = null;
		List<Tutorial> tutToView = new ArrayList<Tutorial>();

		User usr=new User();
		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		System.out.println(lan);
		if(!cat.contentEquals("Select Category")) {
			localCat = catService.findBycategoryname(cat);
		}

		if(!topic.contentEquals("Select Topic")) {
			localTopic = topicService.findBytopicName(topic);
		}

		if(!lan.contentEquals("Select Language")) {
			localLan= lanService.getByLanName(lan);
		}

		if(localCat != null && localTopic != null) {
			localTopicCat = topicCatService.findAllByCategoryAndTopic(localCat, localTopic);
		}else if (localCat != null) {
			localTopicCatList = topicCatService.findAllByCategory(localCat);
		}else if (localTopic != null) {
			localTopicCatList = topicCatService.findAllByTopic(localTopic);
		}

		if(localTopicCat != null) {

			if(localLan != null) {
				conAssigTutorial = conRepo.findByTopicCatAndLanViewPart(localTopicCat, localLan);
			}else {
				conAssigTutorialList = conRepo.findByTopicCat(localTopicCat);
			}
		}else if(localTopicCatList != null) {

			if(localLan != null) {
				conAssigTutorialList = conRepo.findAllByTopicCatAndLanViewPart(localTopicCatList, localLan);
			}else {
				conAssigTutorialList = conRepo.findAllByTopicCat(localTopicCatList);
			}
		}else {
			if(localLan != null) {
				conAssigTutorialList = conRepo.findAllByLan(localLan);
			}
		}

		Pageable pageable = PageRequest.of(page, 20);

		if(conAssigTutorial != null) {
			tut = tutService.findAllByconAssignedTutorialPagination(conAssigTutorial,pageable);
			
		} else if(conAssigTutorialList != null) {
			tut =tutService.findAllByconAssignedTutorialListPagination(conAssigTutorialList, pageable);
			
		}else {
			tut = tutService.findAllPagination(pageable);
	
		}

		for(Tutorial temp :tut) {
			System.out.println(temp.getTutorialId());
			if(temp.isStatus()) {
				tutToView.add(temp);
			}
		}

		for(Tutorial temp :tutToView) {
			System.out.println(temp.getTutorialId() +"***********");
		}
		
		Collections.sort(tutToView);  // sorting based on order value
		
		model.addAttribute("tutorials", tutToView);
		model.addAttribute("currentPage",page);
		model.addAttribute("totalPages",tut.getTotalPages());

		return "tutorialList";   // add view name (filename)
	}

	/**
	 * redirects to tutorial specific page
	 * @param req HttpServletRequest object
	 * @param id integer value
	 * @param principal Principal object
	 * @param model Model object
	 * @return String object (Webpapge)
	 */
	@RequestMapping(value = "/tutorialView/{catName}/{topicName}/{language}", method = RequestMethod.GET)
	public String viewTutorial(HttpServletRequest req,@PathVariable(name = "catName") String cat,
			@PathVariable (name = "topicName") String topic,
			@PathVariable (name = "language") String lan,Principal principal,Model model) {
		
		Category catName = catService.findBycategoryname(cat);
		Topic topicName = topicService.findBytopicName(topic);
		Language lanName = lanService.getByLanName(lan);
		TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
		ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);
		
		if(catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {
			return "redirect:/";
		}
		
		
		
		
			 Tutorial tutorial = tutService.findAllByContributorAssignedTutorial(conTut).get(0);
			 List<Tutorial> relatedTutorial = new ArrayList<>();
			 
			 if(tutorial == null || tutorial.isStatus() == false) {
				 return "redirect:/";
			 }
			 
			 tutorial.setUserVisit(tutorial.getUserVisit()+1);
			 tutService.save(tutorial);
			 
			 model.addAttribute("tutorial", tutorial);
			 
			 
			 if(!tutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")){
				 model.addAttribute("relatedContent", tutorial.getRelatedVideo());
			 }

			 Category category = catService.findByid(tutorial.getConAssignedTutorial().getTopicCatId().getCat().getCategoryId());
			 List<TopicCategoryMapping> topicCatMapping = topicCatService.findAllByCategory(category);
			 List<ContributorAssignedTutorial> contriAssignedTut = conRepo.findAllByTopicCat(topicCatMapping);
			 List<Tutorial> tutorials = tutService.findAllByContributorAssignedTutorialList(contriAssignedTut);
			 
			 for(Tutorial x: tutorials) {
				 if(x.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase(tutorial.getConAssignedTutorial().getLan().getLangName())) {
					 relatedTutorial.add(x);
				 }
			 }
			 Collections.sort(relatedTutorial);
			 
			 model.addAttribute("tutorials", relatedTutorial);
			 System.out.println("*******************");
			 System.out.println(tutorials);
			 System.out.println("*******************");

				Set<String> catTemp = new HashSet<String>();
				Set<String> topicTemp = new HashSet<String>();
				Set<String> lanTemp = new HashSet<String>();

				List<Tutorial> tutorialse = tutService.findAllBystatus(true);
				for(Tutorial temp :tutorialse) {
					catTemp.add(temp.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
					lanTemp.add(temp.getConAssignedTutorial().getLan().getLangName());
					topicTemp.add(temp.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
				}
				
				List<String> catTempSorted =new ArrayList<String>(catTemp);
				Collections.sort(catTempSorted);
				
				List<String> lanTempSorted =new ArrayList<String>(lanTemp);
				Collections.sort(lanTempSorted);

				model.addAttribute("categories", catTempSorted);
				model.addAttribute("languages", lanTempSorted);
				model.addAttribute("topics", topicTemp);

			return "tutorial";
	}
	
	/**
	 * Redirects to Login Page
	 * @param model Model object
	 * @return String object (Webpapge)
	 */
	@RequestMapping("/login")									// in use
	public String loginGet(Model model) {
		model.addAttribute("classActiveLogin", true);
		return "signup";
	}

	/**
	 * Redirects to ShowEvent Page
	 * @param model Model object
	 * @return String object (Webpapge)
	 */
	@RequestMapping(value = "/showEvent",method = RequestMethod.GET)
	public String showEventGet(Model model) {

		List<Event> events=eventservice.findAll();
		model.addAttribute("Events", events);
		return "events";
	}

	/**
	 * Redirects to Consultant Page
	 * @param model Model object
	 * @return String object (Webpapge)
	 */
	@RequestMapping(value = "/showConsultant",method = RequestMethod.GET)
	public String showConsultantGet(Model model) {

		List<Consultant> consults = consultService.findAll();
		System.out.println("******************************"+consults);
		model.addAttribute("listConsultant", consults);
		return "Consultants";
	}

	/**
	 * Redirects to Testimonail Page
	 * @param model Model object
	 * @return String object (Webpapge)
	 */
	@RequestMapping(value = "/showTestimonial",method = RequestMethod.GET)
	public String showTestimonialGet(Model model) {

		List<Testimonial> testi = testService.findByApproved(true);
		model.addAttribute("Testimonials", testi);
		return "signup";
	}

	/**
	 * redirects to Forget Password Post method
	 * @param request HttpServletRequest object
	 * @param email String object
	 * @param model model object
	 * @return String object (Webpapge)
	 */
	@RequestMapping(value = "/forgetPassword",method = RequestMethod.POST)
	public String forgetPasswordPost(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {

		model.addAttribute("classActiveForgetPassword", true);
		
		User usr = userService.findByEmail(email);
		
		if (usr == null) {
			model.addAttribute("emailNotExist", true);
			return "signup";
		}
		
		try {
			
			String token = UUID.randomUUID().toString();
			usr.setToken(token);
			userService.save(usr);
			
			String appUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
			System.out.println(appUrl);
			SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, usr);

			mailSender.send(newEmail);

			model.addAttribute("forgetPasswordEmailSent", true);
		} catch (MailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error", true);
		}
		
		return "signup";
	}

	/**
	 * Redirects to Forget Password Page
	 * @param model Model object
	 * @return String object (Webpapge)
	 */
	@RequestMapping("/forgetPassword")									// in use
	public String forgetPasswordGet(Model model) {

		model.addAttribute("classActiveForgetPassword", true);
		return "signup";
	}
	
	/**
	 * Url to reset password of the user
	 * @param mv ModelAndView Object
	 * @param token String object
	 * @param principal Princiapl Object
	 * @return String object (Webpapge)
	 */
	@RequestMapping(value = "/reset", method = RequestMethod.GET)
	public ModelAndView resetPasswordGet(ModelAndView mv, @RequestParam("token") String token,Principal principal) {
		
		if(principal != null) {
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			mv.setViewName("accessDeniedPage");
			return mv;
		}

		User usr = userService.findBytoken(token);
		if (usr == null) {
			mv.setViewName("redirect:/");
			return mv;
		}

		System.out.println(token);
		mv.addObject("resetToken", usr.getToken());
		mv.setViewName("resetPassword");
		return mv;

	}
	
	/**
	 * redirects to forget password page
	 * @param mv ModelAndView object
	 * @param req HttpServletRequest object
	 * @param principal HttpServletRequest object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public ModelAndView resetPasswordPost(ModelAndView mv, HttpServletRequest req,Principal principal) {

		String newPassword = req.getParameter("Password");
		String confNewPassword = req.getParameter("Confirm");
		String token = req.getParameter("token");
		
		if(principal != null) {
			User localUser=userService.findByUsername(principal.getName());
			
			mv.addObject("LoggedUser",localUser);
			
			mv.setViewName("redirect:/");
			return mv;
		}


		User usr = userService.findBytoken(token);
		if (usr == null) {
			mv.addObject("Error", "Invalid Request");
			return mv;
		}

		if (!newPassword.contentEquals(confNewPassword)) {
			mv.addObject("Error", "Both password doesn't match");
			return mv;
		}
		
		if(newPassword.length()<6) {
			mv.addObject("Error", "Password must be atleast 6 character");
			return mv;
		}
		
		usr.setPassword(SecurityUtility.passwordEncoder().encode(newPassword));
		usr.setToken(null);
		userService.save(usr);

		mv.addObject("Success", "Password got updated Successfully");
		mv.setViewName("resetPassword");
		return mv;

	}

	/**
	 * redirects to category page
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/categories",method = RequestMethod.GET)
	public String showCategoriesGet(Model model) {

		List<Category> categories=catService.findAll();
		model.addAttribute("categories", categories);
		return "categories";
	}


	/**************************** USER REGISTRATION *************************************************/

	/**
	 * Url to add user into system
	 * @param request HttpServletRequest object
	 * @param username String object
	 * @param firstName String object
	 * @param lastName String object
	 * @param userEmail String object
	 * @param password String object
	 * @param address String object
	 * @param phone String object
	 * @param gender String object
	 * @param model Model Object
	 * @return String object(Webpage)
	 * @throws Exception
	 */
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)    // in use
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("username") String username, @ModelAttribute("firstName") String firstName,
			@ModelAttribute("lastName") String lastName, @ModelAttribute("email") String userEmail,
			@ModelAttribute("password") String password, @ModelAttribute("address") String address,
			@ModelAttribute("phone") String phone,@ModelAttribute("gender") String gender,
			Model model) throws Exception {

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

		if(!ServiceUtility.checkEmailValidity(userEmail)) {   // need to accommodate

			model.addAttribute("emailWrong", true);
			return "signup";
		}

		if(phone.length()>10) {								// need to accommodate

			model.addAttribute("phoneWrong", true);
			return "signup";

		}else {
			phoneLongValue=Long.parseLong(phone);

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

		userService.save(user);
		model.addAttribute("emailSent", "true");

		return "signup";

	}

	/**
	 * Redirects to adduser page
	 * @param model Model Object
	 * @return String object (webpage)
	 */
	@RequestMapping("/newUser")										// in use
	public String newUserGet (Model model) {

		model.addAttribute("classActiveNewAccount", true);
		return "signup";


	}

	/************************** END ****************************************************/

	/**************************** DASHBAORD PAGE FOR ALL USER *****************************************/

	/**
	 * redirects to user Dashboard page 
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/dashBoard",method = RequestMethod.GET)
	public String dashBoardGet (Model model,Principal principal) {

		User usr=new User();
		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<UserRole> userRoles= usrRoleService.findAllByUser(usr);
		List<UserRole> pendingUserRoles= usrRoleService.findAllByUser(usr);
		List<Integer> roleIds = new ArrayList<Integer>();

		for(int i=0; i<userRoles.size();i++) {
			if(!userRoles.get(i).getStatus()) {
//				roleIds.add(userRoles.get(i).getRole().getRoleId());
				pendingUserRoles.add(userRoles.get(i));
			}
		}
		model.addAttribute("roleIds", roleIds);
		model.addAttribute("userRoles", pendingUserRoles);
		return "roleAdminDetail";
	}

	/****************************************** ADD CATEGORY *************************************************/
	
	/**
	 * redirects to add category page
	 * @param model model Object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addCategory",method = RequestMethod.GET)
	public String addCategoryGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Category> categories = catService.findAll();
		model.addAttribute("categories", categories);

		return "addCategory";

	}

	/**
	 * Url to add category to object
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest object
	 * @param files MultipartFile object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addCategory",method = RequestMethod.POST)
	public String addCategoryPost(Model model,Principal principal,HttpServletRequest req,
								  @RequestParam("categoryImage") MultipartFile files) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Category> categoriesTemp = catService.findAll();
		model.addAttribute("categories", categoriesTemp);

		String categoryName = req.getParameter("categoryname");
		String categoryDesc = req.getParameter("categoryDesc");

		if(categoryName == null) {
			model.addAttribute("error_msg", "Please Try Again");
			return "addCategory";
		}
		
		if(categoryDesc == null) {
			model.addAttribute("error_msg", "Please Try Again");
			return "addCategory";
		}

		if(catService.findBycategoryname(categoryName)!=null) {
			model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
			return "addCategory";
		}

		if(!ServiceUtility.checkFileExtensionImage(files)) {
			model.addAttribute("error_msg", CommonData.JPG_PNG_EXT);
			return "addCategory";
		}

		int newCatId=catService.getNewCatId();
		Category cat=new Category();
		cat.setCategoryId(newCatId);
		cat.setCatName(categoryName);
		cat.setDateAdded(ServiceUtility.getCurrentTime());
		cat.setPosterPath("null");
		cat.setDescription(categoryDesc);
		cat.setUser(usr);

		Set<Category> categories=new HashSet<Category>();
		categories.add(cat);

		try {
			userService.addUserToCategory(usr, categories);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "addCategory";
		}

		try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryCategory+newCatId);
				String pathtoUploadPoster=ServiceUtility.uploadFile(files, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryCategory+newCatId);
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				Category local=catService.findBycategoryname(categoryName);
				local.setPosterPath(document);

				catService.save(local);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "addCategory";
		}

		categoriesTemp = catService.findAll();
		model.addAttribute("categories", categoriesTemp);
		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
		return "addCategory";

	}

	/************************************END**********************************************/
	/************************************ADD ORGANIZATIONAL ROLE**********************************************/

	/**
	 * redirect to add organization role page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addOrganizationRole",method = RequestMethod.GET)
	public String addOrganizationRoleGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<OrganizationRole> orgRoles=organizationRoleService.findAll();

		model.addAttribute("orgRoles", orgRoles);

		return "addOrganizationRole";

	}

	/**
	 * add organization role object to database
	 * @param model Model object
	 * @param principal principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addOrganizationRole",method = RequestMethod.POST)
	public String addOrganizationRolePost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<OrganizationRole> orgRoles=organizationRoleService.findAll();

		model.addAttribute("orgRoles", orgRoles);

		String orgRoleName=req.getParameter("role");

		if(orgRoleName==null) {

			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "addOrganizationRole";
		}

		if(organizationRoleService.getByRole(orgRoleName)!=null) {

			model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
			return "addOrganizationRole";
		}

		String roleName = orgRoleName.substring(0, 1).toUpperCase() + orgRoleName.substring(1).toLowerCase();
		OrganizationRole orgRole = new OrganizationRole();
		orgRole.setDateAdded(ServiceUtility.getCurrentTime());
		orgRole.setRoleId(organizationRoleService.getnewRoleId());
		orgRole.setRole(roleName);
		organizationRoleService.save(orgRole);

		Set<OrganizationRole> roles=new HashSet<OrganizationRole>();
		roles.add(orgRole);

		orgRoles=organizationRoleService.findAll();

		model.addAttribute("orgRoles", orgRoles);
		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "addOrganizationRole";

	}
	
	/**
	 * redirect to edit organization role page given id
	 * @param id int value
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/organization_role/edit/{name}", method = RequestMethod.GET)
	public String editOrganizationRoleGet(@PathVariable(name = "name") String orgname,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		OrganizationRole role = organizationRoleService.getByRole(orgname);

		model.addAttribute("role",role);

		return "updateOrganizationalRole";
	}

	/**
	 * update organization role object to database
	 * @param model Model object
	 * @param principal principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/update_organization_role",method = RequestMethod.POST)
	public String updateOrganizationRolePost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String roleName=req.getParameter("role");
		String lanIdInString = req.getParameter("roleId");
		System.err.print("***************** ROLE ID");
		System.err.print(lanIdInString);
		System.err.print("***************** ROLE ID");
		int roleId = Integer.parseInt(lanIdInString);
		OrganizationRole role = organizationRoleService.getById(roleId);
//		Language lan = lanService.getById(lanId);

		if(role == null) {
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			model.addAttribute("role",role);
			return "updateOrganizationalRole";  //  accomodate view part
		}

		if(roleName==null) {

			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			model.addAttribute("role",role);
			return "updateOrganizationalRole";  //  accomodate view part
		}

		if(lanService.getByLanName(roleName)!=null) {

			model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
			model.addAttribute("role",role);
			return "updateOrganizationalRole";   //  accomodate view part
		}

		String role_formatted = roleName.substring(0, 1).toUpperCase() + roleName.substring(1).toLowerCase();

		role.setRole(role_formatted);
		try {
			organizationRoleService.save(role);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("language",role);
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "updateOrganizationalRole";  //  accomodate view part
		}

//		role = lanService.getById(lanId);
		role = organizationRoleService.getById(roleId);
		model.addAttribute("role",role);
		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "updateOrganizationalRole";  //  accomodate view part

	}


	/************************************END**********************************************/

	/************************************ADD LANGUAGE**********************************************/

	/**
	 * redirect to add language page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addLanguage",method = RequestMethod.GET)
	public String addLanguageGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Language> languages=lanService.getAllLanguages();

		model.addAttribute("languages", languages);

		return "addlanguage";

	}

	/**
	 * add language object to database
	 * @param model Model object
	 * @param principal principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addLanguage",method = RequestMethod.POST)
	public String addLanguagePost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Language> languagesTemp=lanService.getAllLanguages();

		model.addAttribute("languages", languagesTemp);

		String languagename=req.getParameter("languageName");

		if(languagename==null) {

			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "addlanguage";
		}

		if(lanService.getByLanName(languagename)!=null) {

			model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
			return "addlanguage";
		}

		String language_formatted = languagename.substring(0, 1).toUpperCase() + languagename.substring(1).toLowerCase();
		Language newLan=new Language();
		newLan.setLanId(lanService.getnewLanId());
		newLan.setLangName(language_formatted);
		newLan.setDateAdded(ServiceUtility.getCurrentTime());
		newLan.setUser(usr);

		Set<Language> languages=new HashSet<Language>();
		languages.add(newLan);

		try {
			userService.addUserToLanguage(usr, languages);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "addlanguage";
		}

		languagesTemp=lanService.getAllLanguages();

		model.addAttribute("languages", languagesTemp);

		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "addlanguage";

	}

	/************************************END**********************************************/

	/******************************ADD CAROUSEL ******************************************/
	
	/**
	 * redirect to add carousel page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addCarousel",method = RequestMethod.GET)
	public String addCarouselGet(Model model,Principal principal) {
		User usr=new User();
		if(principal!=null) {
			usr=userService.findByUsername(principal.getName());
		}
		model.addAttribute("userInfo", usr);

		List<Carousel> cara = caroService.findAll();

		model.addAttribute("carousels", cara);

		return "addCarousel";
	}

	/**
	 * Add Carousel object
	 * @param model Model object
	 * @param principal principal object
	 * @param file MultipartFile
	 * @param name String object
	 * @param desc String object
 	 * @return String object
	 */
	@RequestMapping(value = "/addCarousel",method = RequestMethod.POST)
	public String addCarouselPost(Model model,Principal principal,
								  @RequestParam("file") MultipartFile file,
								  @RequestParam(value = "eventName") String name,
								  @RequestParam(name = "eventDesc") String desc
								  ) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Carousel> cara = caroService.findAll();

		model.addAttribute("carousels", cara);
		
		if(name == null) {  // throw error
			model.addAttribute("error_msg","Please Try Again");
			return "addCarousel";
		}
		
		if(desc == null) {  // throw error
			model.addAttribute("error_msg","Please Try Again");
			return "addCarousel";
		}

		if(!ServiceUtility.checkFileExtensionImage(file)) {  // throw error
			model.addAttribute("error_msg",CommonData.JPG_PNG_EXT);
			return "addCarousel";
		}

		Carousel caraTemp = new Carousel();
		caraTemp.setId(caroService.getNewId());
		caraTemp.setDescription(desc);
		caraTemp.setEventName(name);

		try {

			caroService.save(caraTemp);

			ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadCarousel+caraTemp.getId());
			String pathtoUploadPoster=ServiceUtility.uploadFile(file, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadCarousel+caraTemp.getId());
			int indexToStart=pathtoUploadPoster.indexOf("Media");

			String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

			caraTemp.setPosterPath(document);

			caroService.save(caraTemp);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				model.addAttribute("error_msg",CommonData.RECORD_ERROR);
				caroService.delete(caraTemp);
				return "addCarousel";
			}

		cara = caroService.findAll();

		model.addAttribute("carousels", cara);
		model.addAttribute("success_msg",CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "addCarousel";


	}
	/************************************END**********************************************/

	/******************************ADD BROUCHURE ******************************************/
	
	/**
	 * redirect to add brochure page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addBrochure",method = RequestMethod.GET)
	public String addBrochureGet(Model model,Principal principal) {
		User usr=new User();
		if(principal!=null) {
			usr=userService.findByUsername(principal.getName());
		}
		model.addAttribute("userInfo", usr);
		List<Category> category = catService.findAll();
		model.addAttribute("categories", category);
		List<Language> lans = lanService.getAllLanguages();
		model.addAttribute("languages", lans);
		List<Brouchure> brouchures = broService.findAll();
		model.addAttribute("brouchures", brouchures);
		return "addBrochure";
	}

	/**
	 * Add brochure to the system
	 * @param model Model object
	 * @param principal principal object
	 * @param brochure MultipartFile
	 * @param categoryId int value
	 * @param topicId int value
	 * @param languageId int value
	 * @return String object
	 */
	@RequestMapping(value = "/addBrochure",method = RequestMethod.POST)
	public String addBrochurePost(Model model,Principal principal,
								  @RequestParam("brouchure") MultipartFile brochure,
								  @RequestParam(value = "categoryName") int categoryId,
								  @RequestParam(name = "inputTopicName") int topicId,
								  @RequestParam(name = "languageyName") int languageId) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Brouchure> brouchures = broService.findAll();
		model.addAttribute("brouchures", brouchures);
		List<Language> languages=lanService.getAllLanguages();
		List<Category> categories=catService.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("languages", languages);
		
		if(!ServiceUtility.checkFileExtensionImage(brochure)) {  // throw error
			model.addAttribute("error_msg",CommonData.JPG_PNG_EXT);
			return "addBrochure";
		}
		
		Category cat=catService.findByid(categoryId);
		Topic topic=topicService.findById(topicId);
		
		if(cat == null) {  // throw error
			model.addAttribute("error_msg","Please Try again");
			return "addBrochure";
		}
		
		if(topic == null) {  // throw error
			model.addAttribute("error_msg","Please Try again");
			return "addBrochure";
		}
		
		TopicCategoryMapping topicCat=topicCatService.findAllByCategoryAndTopic(cat, topic);
		Language lan=lanService.getById(languageId);

		int newBroId=broService.getNewId();
		Brouchure brochureTemp = new Brouchure();
		brochureTemp.setId(newBroId);
		brochureTemp.setLan(lan);
		brochureTemp.setTopicCatId(topicCat);

		try {
			broService.save(brochureTemp);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "addBrochure";
		}

		try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadBrouchure+newBroId);
				String pathtoUploadPoster=ServiceUtility.uploadFile(brochure, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadBrouchure+newBroId);
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				brochureTemp.setPosterPath(document);

				broService.save(brochureTemp);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			broService.delete(brochureTemp);
			return "addBrochure";
		}

		brouchures = broService.findAll();
		model.addAttribute("brouchures", brouchures);
		model.addAttribute("success_msg",CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "addBrochure";


	}

	/********************************END****************************************************/

	/************************************ADD TOPIC**********************************************/

	/**
	 * redirect to add topic page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addTopic",method = RequestMethod.GET)
	public String addTopicGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Category> category = catService.findAll();
		
		Collections.sort(category);

		model.addAttribute("categories", category);

		List<Topic> topics = topicService.findAll();

		model.addAttribute("topics", topics);

		return "addTopic";

	}

	/**
	 * add topic into database
	 * @param model Model object
	 * @param principal Principal object
	 * @param categoryId int value
	 * @param topicName String object
	 * @param orderValue int value
	 * @return String object
	 */
	@RequestMapping(value = "/addTopic",method = RequestMethod.POST)
	public String addTopicPost(Model model,Principal principal,
							   @RequestParam(name = "categoryName") int categoryId,
							   @RequestParam(name = "topicName") String topicName,
							   @RequestParam(name = "orderValue") int orderValue) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Topic> topicsTemp = topicService.findAll();

		model.addAttribute("topics", topicsTemp);

		List<Category> category = catService.findAll();

		model.addAttribute("categories", category);

		Category cat =catService.findByid(categoryId);

		if(cat == null) {
			model.addAttribute("error_msg", "Category Doesn't Exist");
			return "addTopic";
		}

		Topic topicTemp = topicService.findBytopicName(topicName);

		if(topicTemp!=null) {

			if(topicCatService.findAllByCategoryAndTopic(cat, topicTemp)==null) {

				TopicCategoryMapping localTopicMap=new TopicCategoryMapping(topicCatService.getNewId(), true, cat, topicTemp,orderValue);
				topicCatService.save(localTopicMap);
				model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
				return "addTopic";

			}else {

				model.addAttribute("error_msg", CommonData.RECORD_ERROR);
				return "addTopic";
			}
		}

		Topic local=new Topic();
		local.setTopicId(topicService.getNewTopicId());
		local.setTopicName(topicName);
		local.setDateAdded(ServiceUtility.getCurrentTime());
		local.setUser(usr);

		Set<Topic> topics=new HashSet<Topic>();
		topics.add(local);

		try {
			userService.addUserToTopic(usr, topics);
			TopicCategoryMapping localTopicMap=new TopicCategoryMapping(topicCatService.getNewId(), true, cat, local,orderValue);
			topicCatService.save(localTopicMap);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "addTopic";
		}
		
		topicsTemp = topicService.findAll();

		model.addAttribute("topics", topicsTemp);

		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
		return "addTopic";

	}

	/**
	 * redirects to edit page of topic given id
	 * @param model Model object
	 * @param principal principal object
	 * @param id int value
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/topic/edit/{topicName}", method = RequestMethod.GET)
	public String editTopicGet(@PathVariable(name = "topicName") String topicTemp,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Topic topic=topicService.findBytopicName(topicTemp);
		
		if(topic == null) {
			return "redirect:/addTopic";
		}

		model.addAttribute("topic",topic);

		return "updateTopic";  // need to accomdate view part
	}

	/**
	 * update topic object 
	 * @param model Model object
	 * @param principal principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/updateTopic",method = RequestMethod.POST)
	public String updateTopicPost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String topicname=req.getParameter("topicName");
		String topicIdInString = req.getParameter("TopicId");
		int topicId = Integer.parseInt(topicIdInString);

		Topic topic = topicService.findById(topicId);

		if(topic == null) {
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			model.addAttribute("topic",topic);
			return "updateTopic";  //  accomodate view part
		}

		if(topicname==null) {

			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			model.addAttribute("topic",topic);
			return "updateTopic";  //  accomodate view part
		}

		if(topicService.findBytopicName(topicname)!=null) {

			model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
			model.addAttribute("topic",topic);
			return "updateTopic";   //  accomodate view part
		}

		topic.setTopicName(topicname);

		try {
			topicService.save(topic);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("topic",topic);
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "updateTopic";  //  accomodate view part
		}

		topic = topicService.findById(topicId);
		model.addAttribute("topic",topic);

		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "updateTopic";  //  accomodate view part

	}


	/************************************END**********************************************/

	/************************************ADD ROLE**********************************************/

	/**
	 * redirect to add role page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addRole",method = RequestMethod.GET)
	public String addRoleGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Role> roles = roleService.findAll();

		model.addAttribute("roles", roles);

		return "addNewRole";

	}

	/**
	 * add role object into database
	 * @param model Model object
	 * @param principal principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addRole",method = RequestMethod.POST)
	public String addRolePost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Role> roles = roleService.findAll();

		model.addAttribute("roles", roles);

		String roleName = req.getParameter("roleName");
		
		if(roleName == null) {

			model.addAttribute("error_msg", "Please Try Again");
			return "addNewRole";
		}

		if(roleService.findByname(roleName)!=null) {

//			model.addAttribute("msg1", true);
			model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
			return "addNewRole";
		}

		Role newRole=new Role();
		newRole.setRoleId(roleService.getNewRoleId());
		newRole.setName(roleName);

		try {
			roleService.save(newRole);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "addNewRole";
		}

		roles = roleService.findAll();

		model.addAttribute("roles", roles);
		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);
		return "addNewRole";

	}



	/************************************END**********************************************/

	/************************************ADD QUESTION**********************************************/

	/**
	 * redirects to upload question page
	 * @param model Model object
	 * @param principal principal object
	 * @return Strig object (webpage)
	 */
	@RequestMapping(value = "/uploadQuestion",method = RequestMethod.GET)
	public String addQuestionGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Question> questions = questService.findAll();
		model.addAttribute("questions", questions);

		List<Language> languages=lanService.getAllLanguages();

		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		return "uploadQuestion";


	}

	/**
	 * upload question object into database
	 * @param model Model object
	 * @param principal principal object
	 * @param quesPdf MultipartFile
	 * @param categoryId int value
	 * @param topicId int value
	 * @param languageId int value
	 * @return string object
	 */
	@RequestMapping(value = "/uploadQuestion",method = RequestMethod.POST)
	public String addQuestionPost(Model model,Principal principal,
								  @RequestParam("questionName") MultipartFile quesPdf,
								  @RequestParam(value = "categoryName") int categoryId,
								  @RequestParam(name = "inputTopicName") int topicId,
								  @RequestParam(name = "languageyName") int languageId) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Question> questionsTemp = questService.findAll();
		model.addAttribute("questions", questionsTemp);

		List<Language> languages=lanService.getAllLanguages();

		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		if(!ServiceUtility.checkFileExtensiononeFilePDF(quesPdf)) {  // throw error

			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "uploadQuestion";
		}
		
		if(!ServiceUtility.checkScriptSlideProfileQuestion(quesPdf)) {
			
			model.addAttribute("error_msg","File Size must be less than 20MB");
			return "uploadQuestion";
		}

		Category cat=catService.findByid(categoryId);
		Topic topic=topicService.findById(topicId);
		
		if(cat == null) {  // throw error

			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "uploadQuestion";
		}
		
		if(topic == null) {  // throw error

			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "uploadQuestion";
		}
		
		TopicCategoryMapping topicCat=topicCatService.findAllByCategoryAndTopic(cat, topic);
		Language lan=lanService.getById(languageId);

		Question quesTemp = questService.getQuestionBasedOnTopicCatAndLan(topicCat, lan);

		if(quesTemp != null) {

			model.addAttribute("error_msg",CommonData.QUESTION_EXIST);
			return "uploadQuestion";
		}
		int newQuestionId=questService.getNewId();
		Question question=new Question();
		question.setQuestionId(newQuestionId);
		question.setDateAdded(ServiceUtility.getCurrentTime());
		question.setLan(lan);
		question.setQuestionPath("null");
		question.setTopicCatId(topicCat);
		question.setUser(usr);

		Set<Question> questions=new HashSet<Question>();
		questions.add(question);

		try {
			userService.addUserToQuestion(usr, questions);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "uploadQuestion";
		}

		try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryQuestion+newQuestionId);
				String pathtoUploadPoster=ServiceUtility.uploadFile(quesPdf, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryQuestion+newQuestionId);
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				Question temp=questService.findById(newQuestionId);

				temp.setQuestionPath(document);

				questService.save(temp);




		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "uploadQuestion";
		}

		questionsTemp = questService.findAll();
		model.addAttribute("questions", questionsTemp);

		model.addAttribute("success_msg",CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "uploadQuestion";


	}

	/**
	 * redirect to edit question page given id
	 * @param id int value
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/question/edit/{catName}/{topicName}/{language}", method = RequestMethod.GET)
	public String editQuestionGet(@PathVariable(name = "catName") String cat,
			@PathVariable (name = "topicName") String topic,
			@PathVariable (name = "language") String lan,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		Category catName = catService.findBycategoryname(cat);
		Topic topicName = topicService.findBytopicName(topic);
		Language lanName = lanService.getByLanName(lan);
		TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
		
		Question ques = questService.getQuestionBasedOnTopicCatAndLan(topicCatMap, lanName);
		
		if(catName == null || topicName == null || lanName == null || topicCatMap == null || ques == null) {
			return "redirect:/uploadQuestion";
		}

		
		model.addAttribute("question",ques);

		return "updateQuestion"; // question edit page
	}

	/**
	 * update question object
	 * @param req HttpServletRequest
	 * @param model Model object
	 * @param principal principal object
	 * @param quesPdf MultipartFile object
	 * @return String object
	 */
	@RequestMapping(value = "/updateQuestion",method = RequestMethod.POST)
	public String updateQuestionPost(HttpServletRequest req,Model model,Principal principal,
								  @RequestParam("questionName") MultipartFile quesPdf) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String quesIdInString=req.getParameter("id");
		int idQues =  Integer.parseInt(quesIdInString);
		Question ques = questService.findById(idQues);
		
		if(ques == null) {  // throw error

			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			model.addAttribute("question",ques);
			return "updateQuestion"; // accomodate error
		}

		if(!ServiceUtility.checkFileExtensiononeFilePDF(quesPdf)) {  // throw error

			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			model.addAttribute("question",ques);
			return "updateQuestion"; // accomodate error
		}

		if(!ServiceUtility.checkScriptSlideProfileQuestion(quesPdf)) {
			
			model.addAttribute("error_msg","File Size must be less than 20MB");
			model.addAttribute("question",ques);
			return "updateQuestion";
		}

		try {

				String pathtoUploadPoster=ServiceUtility.uploadFile(quesPdf, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryQuestion+ques.getQuestionId());
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				ques.setQuestionPath(document);

				questService.save(ques);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			model.addAttribute("question",ques);
			return "updateQuestion";   // accomodate view part
		}

		ques = questService.findById(idQues);
		model.addAttribute("question",ques);

		model.addAttribute("success_msg",CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "updateQuestion";     // accomodate view part


	}

	/************************************END**********************************************/

	/************************************ADD CONSULTANT**********************************************/

	/**
	 *  redirect to add consultant page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object (Webpage)
	 */ 
	@RequestMapping(value = "/addConsultant",method = RequestMethod.GET)
	public String addConsultantGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Consultant> consultants = consultService.findAll();
		List<Category> cat=catService.findAll();
		List<Language> lans=lanService.getAllLanguages();

		model.addAttribute("categories", cat);
		model.addAttribute("consultants", consultants);
		model.addAttribute("languages", lans);

		return "addConsultant";

	}

	/**
	 * Add consultant to the system
	 * @param model Model object
	 * @param principal principal object
	 * @param name String object
	 * @param catId int value
	 * @param lanId int value
	 * @param email String object
	 * @return String object
	 */
	@RequestMapping(value = "/addConsultant",method = RequestMethod.POST)
	public String addConsultantPost(Model model,Principal principal,
									@RequestParam("nameConsaltant") String name,
									@RequestParam("categoryName") int catId,
									@RequestParam("lanName") int lanId,
									@RequestParam("email") String email) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Consultant> consultants = consultService.findAll();
		List<Category> cat=catService.findAll();
		List<Language> lans=lanService.getAllLanguages();

		model.addAttribute("categories", cat);
		model.addAttribute("consultants", consultants);
		model.addAttribute("languages", lans);

		if(!ServiceUtility.checkEmailValidity(email)) {  // throw email wromng error

			model.addAttribute("msg", CommonData.NOT_VALID_EMAIL_ERROR);
			return "addConsultant";
		}

		if (userService.findByUsername(email) != null) {
			model.addAttribute("emailExists", true);
			return "addConsultant";
		}

		if (userService.findByEmail(email) != null) {
			model.addAttribute("emailExists", true);
			return "addConsultant";
		}

		Category cats = catService.findByid(catId);
		Language lan =lanService.getById(lanId);
		
		if(cats == null){  // throw email wromng error

			model.addAttribute("msg", "Please Try Again");
			return "addConsultant";
		}
		
		if(lan == null) {  // throw email wromng error

			model.addAttribute("msg", "Please Try Again");
			return "addConsultant";
		}
		
		Role role = roleService.findByname(CommonData.domainReviewerRole);

		User userTemp = new User();
		userTemp.setId(userService.getNewId());
		userTemp.setEmail(email);
		userTemp.setUsername(email);
		userTemp.setDateAdded(ServiceUtility.getCurrentTime());
		userTemp.setPassword(SecurityUtility.passwordEncoder().encode(CommonData.COMMON_PASSWORD));

		userService.save(userTemp);


		int newConsultid=consultService.getNewConsultantId();
		Consultant local=new Consultant();
		local.setConsultantId(newConsultid);
		local.setDescription("null");
		local.setDateAdded(ServiceUtility.getCurrentTime());
		local.setUser(userTemp);

		Set<Consultant> consults=new HashSet<Consultant>();
		consults.add(local);

		try {
			userService.addUserToConsultant(usr, consults);

			UserRole usrRole= new UserRole();
			usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());
			usrRole.setCat(cats);
			usrRole.setLan(lan);
			usrRole.setUser(userTemp);
			usrRole.setRole(role);
			usrRole.setStatus(true);
			usrRole.setCreated(ServiceUtility.getCurrentTime());

			usrRoleService.save(usrRole);
			
			SimpleMailMessage msg = mailConstructor.domainRoleMailSend(userTemp);
			
			mailSender.send(msg);



		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("msg",CommonData.RECORD_ERROR);
			return "addConsultant";    // throw a error
		}

		consultants = consultService.findAll();
		model.addAttribute("consultants", consultants);
		model.addAttribute("msg",CommonData.RECORD_SAVE_SUCCESS_MSG);
		return "addConsultant";

	}

	/************************************END**********************************************/

	/************************************ADD EVENT**********************************************/
	/**
	 * redirect to add page
	 * @param model Model object
	 * @param principal principal object
	 * @return String object
	 */
	@RequestMapping(value = "/addEvent",method = RequestMethod.GET)
	public String addEventGet(Model model,Principal principal) {
		User usr=new User();
		if(principal!=null) {
			usr=userService.findByUsername(principal.getName());
		}
		model.addAttribute("userInfo", usr);
		List<Event> events = eventservice.findByUser(usr);
		model.addAttribute("events", events);
		List<State> states = stateService.findAll();
		model.addAttribute("states", states);
		List<Category> categories = catService.findAll();
		model.addAttribute("categories", categories);
		List<Language> lans = lanService.getAllLanguages();
		model.addAttribute("lans", lans);

		return "addEvent";
	}

	/**
	 *  Add Event object into database
	 * @param model Model object
	 * @param principal principal object
	 * @param req  HttpServletRequest object
	 * @param files MultipartFile
	 * @param topicId list of integer value
	 * @param catName int value
	 * @return String (webpage)
	 */
	@RequestMapping(value = "/addEvent",method = RequestMethod.POST)
	public String addEventPost(Model model,Principal principal,HttpServletRequest req,
						@RequestParam("Image") MultipartFile files,@RequestParam(value="inputTopic") int[] topicId,
						@RequestParam(value="categoryName") int catName) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Event> eventsTemp = eventservice.findAll();
		model.addAttribute("events", eventsTemp);

		String eventName = req.getParameter("eventname");
		String desc = req.getParameter("description");
		String venueName = req.getParameter("venuename");
		String contactPerson = req.getParameter("contactperson");
		String contactNumber = req.getParameter("contactnumber");
		String email = req.getParameter("email");
		String startDateTemp=req.getParameter("date");
		String endDateTemp=req.getParameter("endDate");

		String pinCode = req.getParameter("pinCode");
		String stateName = req.getParameter("stateName");
		String districtName = req.getParameter("districtName");
		String cityName = req.getParameter("cityName");
		String addressInformationName = req.getParameter("addressInformationName");
		String language = req.getParameter("language");

		Date startDate;
		Date endDate;
		int newEventid;
		
		if(stateService.findById(Integer.parseInt(stateName))==null){
			model.addAttribute("error_msg", "Please Select State");
			return "addEvent";
		}
		
		if(districtService.findById(Integer.parseInt(districtName))==null){
			model.addAttribute("error_msg", "Please Select District");
			return "addEvent";
		}
		
		if(cityService.findById(Integer.parseInt(cityName))==null){
			model.addAttribute("error_msg", "Please Select City");
			return "addEvent";
		}
		
		if(lanService.getByLanName(language)==null){
			model.addAttribute("error_msg", "Please Select language");
			return "addEvent";
		}
		
		if(catService.findByid(catName)==null){
			model.addAttribute("error_msg", "Please Select Category");
			return "addEvent";
		}

		try {
			startDate=ServiceUtility.convertStringToDate(startDateTemp);
			endDate=ServiceUtility.convertStringToDate(endDateTemp);

			if(!ServiceUtility.checkFileExtensionImage(files)) {
				model.addAttribute("error_msg", CommonData.JPG_PNG_EXT);
				return "addEvent";
			}

			if(endDate.before(startDate)) {      // throws error if end date is previous to start date
				model.addAttribute("error_msg",CommonData.EVENT_CHECK_DATE);
				return "addEvent";
			}

			if(!ServiceUtility.checkEmailValidity(email)) { // throw error on wrong email
				model.addAttribute("error_msg",CommonData.EVENT_CHECK_EMAIL);
				return "addEvent";
			}

			if(contactNumber.length() != 10) {        // throw error on wrong phone number
				model.addAttribute("error_msg",CommonData.EVENT_CHECK_CONTACT);
				return "addEvent";
			}

			long contact=Long.parseLong(contactNumber);

			newEventid=eventservice.getNewEventId();
			Event event=new Event();

			
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryEvent+newEventid);
				String pathtoUploadPoster=ServiceUtility.uploadFile(files, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryEvent+newEventid);
				int indexToStart=pathtoUploadPoster.indexOf("Media");
				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
				event.setPosterPath(document);
			
			
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
			event.setCity(cityService.findById(Integer.parseInt(cityName)));
			event.setPincode(Integer.parseInt(pinCode));
			event.setLan(lanService.getByLanName(language));
			Set<TrainingTopic> trainingTopicTemp = new HashSet<>();
			Category cat=catService.findByid(catName);

			try {
//				trainingInfoService.save(trainingData);
				int trainingTopicId=trainingTopicServ.getNewId();
				for(int topicID : topicId) {
					Topic topicTemp=topicService.findById(topicID);
					TopicCategoryMapping topicCatMap=topicCatService.findAllByCategoryAndTopic(cat, topicTemp);
					TrainingTopic trainingTemp=new TrainingTopic(trainingTopicId++, topicCatMap, event);
					trainingTopicTemp.add(trainingTemp);

				}

				event.setTrainingTopicId(trainingTopicTemp);

			Set<Event> events=new HashSet<Event>();
			events.add(event);

			userService.addUserToEvent(usr, events);


		}catch (Exception e){

		}
		}catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			e.printStackTrace();
			return "addEvent";
		}finally {

		}

		eventsTemp = eventservice.findAll();
		model.addAttribute("events", eventsTemp);
		model.addAttribute("success_msg",CommonData.RECORD_SAVE_SUCCESS_MSG);
		return "addEvent";


	}


	/************************************END**********************************************/

	/************************************ADD TESTIMONIAL**********************************************/

	/**
	 * redirects page to add testimonial page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/addTestimonial",method = RequestMethod.GET)
	public String addTestimonialGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Testimonial> testimonials = testService.findAll();
		List<TrainingInformation> trainings = trainingInfoService.findAll();
		List<Event> events = eventservice.findAll();
		model.addAttribute("testimonials", testimonials);
		model.addAttribute("trainings", trainings);
		model.addAttribute("events", events);

		return "addTestimonial";


	}

	/**
	 * Add testimonial into database
	 * @param model Model object
	 * @param principal Principal object
	 * @param file MultipartFile
	 * @param consent MultipartFile
	 * @param name String 
	 * @param desc String
	 * @param trainingId String
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addTestimonial",method = RequestMethod.POST)
	public String addTestimonialPost(Model model,Principal principal,
									@RequestParam("uploadTestimonial") MultipartFile file,
									@RequestParam("consent") MultipartFile consent,
									@RequestParam("testimonialName") String name,
									@RequestParam("description") String desc,
									@RequestParam(value ="trainingName", required = false ) String trainingId) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Testimonial> testimonials = testService.findAll();
		List<TrainingInformation> trainings = trainingInfoService.findAll();
		model.addAttribute("testimonials", testimonials);
		model.addAttribute("trainings", trainings);
		
		if(!ServiceUtility.checkFileExtensionImage(consent) && !ServiceUtility.checkFileExtensiononeFilePDF(consent)) {
			model.addAttribute("error_msg",CommonData.VIDEO_CONSENT_FILE_EXTENSION_ERROR);
			return "addTestimonial";
		}

		if(!file.isEmpty()) {
		if(!ServiceUtility.checkFileExtensionVideo(file)) { // throw error on extension
			model.addAttribute("error_msg",CommonData.VIDEO_FILE_EXTENSION_ERROR);
			return "addTestimonial";
		}
		
		if(!ServiceUtility.checkVideoSizeTestimonial(file)) {
			model.addAttribute("error_msg","File size must be less than 20MB");
			return "addTestimonial";
		}

		String pathSampleVideo = null;;
		try {
			pathSampleVideo = ServiceUtility.uploadVideoFile(file, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		IContainer container = IContainer.make();
		int result=10;
		result = container.open(pathSampleVideo,IContainer.Type.READ,null);

		try {
			if(result<0) {

				model.addAttribute("error_msg",CommonData.RECORD_ERROR);
				return "addTestimonial";

			}else {
					if(container.getDuration()>CommonData.videoDuration) {

						model.addAttribute("error_msg",CommonData.VIDEO_DURATION_ERROR);
						Path deletePreviousPath=Paths.get(pathSampleVideo);
						Files.delete(deletePreviousPath);
						return "addTestimonial";
				}
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "addTestimonial";
		}

		int newTestiId=testService.getNewTestimonialId();
		Testimonial test=new Testimonial();
		test.setDateAdded(ServiceUtility.getCurrentTime());
		test.setDescription(desc);
		test.setName(name);
		test.setUser(usr);
		test.setTestimonialId(newTestiId);
		test.setFilePath("null");

		if(trainingId != null) {
			TrainingInformation train = trainingInfoService.getById(Integer.parseInt(trainingId));
			test.setTraineeInfos(train);
			test.setApproved(false);
		}

		Set<Testimonial> testi=new HashSet<Testimonial>();
		testi.add(test);

		try {
			userService.addUserToTestimonial(usr, testi);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "addTestimonial";
		}

		try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial+newTestiId);
				String pathtoUploadPoster=ServiceUtility.uploadVideoFile(file, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial+newTestiId);
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				Testimonial temp=testService.findById(newTestiId);

				temp.setFilePath(document);
				
				pathtoUploadPoster=ServiceUtility.uploadFile(consent, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial+newTestiId);
				indexToStart=pathtoUploadPoster.indexOf("Media");

				document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
				
				temp.setConsentLetter(document);

				testService.save(temp);



		}catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "addTestimonial";    // throw a error
		}
		}else {

			Testimonial test=new Testimonial();
			test.setDateAdded(ServiceUtility.getCurrentTime());
			test.setDescription(desc);
			test.setName(name);
			test.setUser(usr);
			test.setTestimonialId(testService.getNewTestimonialId());
			test.setFilePath("null");

			if(trainingId != null) {
				TrainingInformation train = trainingInfoService.getById(Integer.parseInt(trainingId));
				test.setTraineeInfos(train);
				test.setApproved(false);
			}

			Set<Testimonial> testi=new HashSet<Testimonial>();
			testi.add(test);

			userService.addUserToTestimonial(usr, testi);
			
			try {
				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial+test.getTestimonialId());
				String pathtoUploadPoster=ServiceUtility.uploadFile(consent, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial+test.getTestimonialId());
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
				
				test.setConsentLetter(document);

				testService.save(test);


		}catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			return "addTestimonial";    // throw a error
		}
			
			
		}

		testimonials = testService.findAll();
		model.addAttribute("testimonials", testimonials);
		model.addAttribute("success_msg",CommonData.RECORD_SAVE_SUCCESS_MSG);
		return "addTestimonial";


	}

	/************************************END**********************************************/

	/************************************UPDATE SECTION AND VIEW OF CATEGORY**********************************************/

	/**
	 * redirects to add category page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/category", method = RequestMethod.GET)
	public String viewCategoryGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Category> cat=catService.findAll();

		model.addAttribute("categories", cat);

		return "category";
	}

	/**
	 * redirects to edit category page
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/category/edit/{catName}", method = RequestMethod.GET)
	public String editCategoryGet(@PathVariable(name = "catName") String catName,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Category cat=catService.findBycategoryname(catName);
		
		if(cat == null) {
			return "redirect:/category";
		}

		model.addAttribute("category",cat);

		return "updateCategory";
	}

	/**
	 * update category object
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest object
	 * @param file MultipartFile object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/updateCategory", method = RequestMethod.POST)
	public String updateCategoryGet(Model model,Principal principal,HttpServletRequest req,
			@RequestParam("categoryImage") MultipartFile file) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String catId=req.getParameter("id");
		String catName=req.getParameter("categoryname");
		String categoryDesc = req.getParameter("categoryDesc");


		Category cat=catService.findByid(Integer.parseInt(catId));

		if(cat==null) {
			 // accommodate  error message
			model.addAttribute("category",cat);
			model.addAttribute("error_msg","Category doesn't exist");
			return "updateCategory";
		}

		List<Category> cats=catService.findAll();
		for(Category x : cats) {
			if(x.getCategoryId()!=cat.getCategoryId()) {
				if(catName.equalsIgnoreCase(x.getCatName())) {
					// accommodate  error message
					model.addAttribute("category",cat);
					model.addAttribute("error_msg","Category Name Already Exist");
					return "updateCategory";
				}
				}
		}

		cat.setCatName(catName);
		cat.setDescription(categoryDesc);

		if(!file.isEmpty()) {
			try {

					String pathtoUploadPoster=ServiceUtility.uploadFile(file, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryCategory+cat.getCategoryId());

					int indexToStart=pathtoUploadPoster.indexOf("Media");

					String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

					cat.setPosterPath(document);

					catService.save(cat);


			}catch (Exception e) {
				// TODO: handle exception

				e.printStackTrace();
				model.addAttribute("category",cat);
				model.addAttribute("error_msg",CommonData.RECORD_ERROR);
				return "updateCategory";  // throw a error
			}
			}else {

				catService.save(cat);
			}

		cat=catService.findByid(Integer.parseInt(catId));
		model.addAttribute("category",cat);

		model.addAttribute("success_msg",CommonData.RECORD_UPDATE_SUCCESS_MSG);   // need to accommodate

		return "updateCategory";
	}


	/************************************END**********************************************/

	/************************************UPDATE AND VIEW SECTION OF EVENT**********************************************/

	/**
	 * redirects to event details in homepage
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/eventDetails/{id}", method = RequestMethod.GET)
	public String eventGet(@PathVariable int id,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Event event= eventservice.findById(id);
		
		if(event == null) {
			return "redirect:/event";
		}
		
		model.addAttribute("event", event);

		return "event";
	}

	/**
	 * redirects to add event page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/event", method = RequestMethod.GET)
	public String viewEventGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Event> event=eventservice.findAll();
		model.addAttribute("events", event);

		return "event";
	}

	/**
	 * redirects to edit event page given id
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/event/edit/{id}", method = RequestMethod.GET)
	public String editEventGet(@PathVariable int id,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Event event= eventservice.findById(id);
		
		if(event == null) {

			return "redirect:/addEvent";
		}

		if(event.getUser().getId() != usr.getId()) {

			return "redirect:/addEvent";
		}
		model.addAttribute("events", event);

		return "updateEvent";
	}

	/**
	 * update event object 
	 * @param req HttpServletRequest object
	 * @param model Model object
	 * @param principal Principal object
	 * @param files MultipartFile object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/updateEvent", method = RequestMethod.POST)
	public String updateEventGet(HttpServletRequest req,Model model,Principal principal,
			@RequestParam("Image") MultipartFile files) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String eventId=req.getParameter("eventId");
		String eventName = req.getParameter("eventname");
		String desc = req.getParameter("description");
		String venueName = req.getParameter("venuename");
		String contactPerson = req.getParameter("contactperson");
		String contactNumber = req.getParameter("contactnumber");
		String email = req.getParameter("email");
		String startDateTemp=req.getParameter("date");
		String endDateTemp=req.getParameter("endDate");
		Date startDate;
		Date endDate;

		Event event= eventservice.findById(Integer.parseInt(eventId));

		model.addAttribute("events", event);

		if(event==null) {
			model.addAttribute("error_msg","Event doesn't exist");
			return "updateEvent";
		}

		try {
			startDate=ServiceUtility.convertStringToDate(startDateTemp);
			endDate=ServiceUtility.convertStringToDate(endDateTemp);

			if(endDate.before(startDate)) {      // throws error if end date is previous to start date

				model.addAttribute("error_msg","End date must be after Start date");
				return "updateEvent";
			}

			if(contactNumber.length() != 10) {        // throw error on wrong phone number

				model.addAttribute("error_msg","Contact number must be 10 digit");
				return "updateEvent";
			}

			if(!ServiceUtility.checkEmailValidity(email)) {    // throw error on wrong email

				model.addAttribute("error_msg",CommonData.NOT_VALID_EMAIL_ERROR);
				return "updateEvent";
			}

			if(!files.isEmpty()) {
				if(!ServiceUtility.checkFileExtensionImage(files)) { // throw error on extension
					model.addAttribute("error_msg",CommonData.JPG_PNG_EXT);
					return "updateEvent";
			}
			}


			long contact=Long.parseLong(contactNumber);

			event.setContactPerson(contactPerson);
			event.setEmail(email);
			event.setDescription(desc);
			event.setEndDate(endDate);
			event.setStartDate(startDate);
			event.setContactNumber(contact);
			event.setEventName(eventName);
			event.setLocation(venueName);

			if(!files.isEmpty()) {
				String pathtoUploadPoster=ServiceUtility.uploadFile(files, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryEvent+event.getEventId());
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				event.setPosterPath(document);

			}

			eventservice.save(event);

		}catch (Exception e) {
			// TODO: handle exception
			model.addAttribute("error_msg",CommonData.RECORD_ERROR);
			model.addAttribute("events", event);
			return "updateEvent";        // need to add some error message
		}


		model.addAttribute("success_msg",CommonData.RECORD_UPDATE_SUCCESS_MSG);
		model.addAttribute("events", event);

		return "updateEvent";
	}


	/************************************END**********************************************/

	/************************************VIEW SECTION OF LANGAUAGE**********************************************/


	/**
	 * redirects to add language page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/language", method = RequestMethod.GET)
	public String viewLanguageGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Language> lan=lanService.getAllLanguages();
		model.addAttribute("lan", lan);

		return "language";
	}

	/**
	 * redirects to edit language page given id
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/language/edit/{lanName}", method = RequestMethod.GET)
	public String editLanguageGet(@PathVariable(name = "lanName") String lanTemp,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Language lan=lanService.getByLanName(lanTemp);
		
		if(lan == null) {
			return "redirect:/addLanguage";
		}

		model.addAttribute("language",lan);

		return "updateLanguage";  // need to accomdate view part
	}
	
	/**
	 * update language object
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/updateLanguage",method = RequestMethod.POST)
	public String updateLanguagePost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String languagename=req.getParameter("languageName");
		String lanIdInString = req.getParameter("lanId");
		int lanId = Integer.parseInt(lanIdInString);

		Language lan = lanService.getById(lanId);

		if(lan == null) {
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			model.addAttribute("language",lan);
			return "updateLanguage";  //  accomodate view part
		}

		if(languagename==null) {

			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			model.addAttribute("language",lan);
			return "updateLanguage";  //  accomodate view part
		}

		if(lanService.getByLanName(languagename)!=null) {

			model.addAttribute("error_msg", CommonData.RECORD_EXISTS);
			model.addAttribute("language",lan);
			return "updateLanguage";   //  accomodate view part
		}

		String language_formatted = languagename.substring(0, 1).toUpperCase() + languagename.substring(1).toLowerCase();
		lan.setLangName(language_formatted);

		try {
			lanService.save(lan);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("language",lan);
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "updateLanguage";  //  accomodate view part
		}

		lan = lanService.getById(lanId);
		model.addAttribute("language",lan);
		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "updateLanguage";  //  accomodate view part

	}

	/************************************END**********************************************/

	/*********************************** VIEW SECTION OF DOMAIN REVIEWER ************************************/

	/**
	 * redirects to domain reviewer page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/domainReviewer", method = RequestMethod.GET)
	public String viewDomaineGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role domain=roleService.findByname(CommonData.domainReviewerRole);

		List<UserRole> domains = usrRoleService.findAllByRole(domain);

		model.addAttribute("domains", domains);

		return "viewDomainReviewer";
	}

	/************************************END**********************************************/

	/*********************************** VIEW SECTION OF QUALITY REVIEWER ************************************/

	/**
	 * redirects to quality reviewer page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/qualityReviewer", method = RequestMethod.GET)
	public String viewQualityeGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		Role quality=roleService.findByname(CommonData.qualityReviewerRole);

		List<UserRole> qualities = usrRoleService.findAllByRole(quality);

		model.addAttribute("qualities", qualities);

		return "viewQualityReviewer";
	}

	/************************************END**********************************************/

	/*********************************** VIEW SECTION OF MASTER TRAINER ************************************/

	/**
	 * redirects to master trainer page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/masterTrainer", method = RequestMethod.GET)
	public String viewMasterTrainerGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		Role master=roleService.findByname(CommonData.masterTrainerRole);

		List<UserRole> masters = usrRoleService.findAllByRole(master);
		model.addAttribute("masters", masters);

		return "viewMasterTrainer";
	}

	/************************************END**********************************************/

	/*********************************** VIEW SECTION OF QUESTIONNAIRE ************************************/

	@RequestMapping(value = "/downloadQuestion", method = RequestMethod.GET)
	public String PostQuestionaireGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<PostQuestionaire> postQuestionnaires = postQuestionService.findAll();

		model.addAttribute("postQuestionnaires", postQuestionnaires);

		return "viewQuestionnaire";
	}

	/************************************END**********************************************/
	/************************************BROCHURE**********************************************/



	/************************************END**********************************************/
	/************************************UPDATE AND VIEW SECTION OF TESTIMONIAL**********************************************/

	/**
	 * redirects to testimonial page on homepage
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/testimonialList", method = RequestMethod.GET)
	public String viewtestimonialListGet(Model model,Principal principal) {
		List<Testimonial> test=testService.findAll();
		model.addAttribute("testimonials", test);

		return "testimonialList";
	}

	/**
	 * redirects to testimonial page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/testimonial", method = RequestMethod.GET)
	public String viewtestimonialGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Testimonial> test=testService.findAll();
		model.addAttribute("testimonials", test);

		return "testimonial";
	}

	/**
	 * redirects to edit testimonial page given testimonial id
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/testimonial/edit/{id}", method = RequestMethod.GET)
	public String edittestimonialGet(@PathVariable int id,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Testimonial test=testService.findById(id);
		
		if(test == null) {

			return "redirect:/addTestimonial";
		}

		if(test.getUser().getId() != usr.getId()) {

			return "redirect:/addTestimonial";
		}

		model.addAttribute("testimonials", test);

		return "updateTestimonial";
	}

	/**
	 * Update testimonial object in database
	 * @param req HttpServletRequest object
	 * @param model Model object
	 * @param principal Principal object
	 * @param file MultipartFile object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/updateTestimonial", method = RequestMethod.POST)
	public String updatetestimonialGet(HttpServletRequest req,Model model,Principal principal,@RequestParam("TestiVideo") MultipartFile file) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		String testiId=req.getParameter("testimonialId");
		String name=req.getParameter("testimonialName");
		String desc=req.getParameter("desc");

		Testimonial test=testService.findById(Integer.parseInt(testiId));

		if(test==null) {
			// accommodate error message
			model.addAttribute("error_msg", CommonData.TESTIMONIAL_NOT_ERROR);
			return "updateTestimonial";
		}

		if(!file.isEmpty()) {
		try {

			String pathSampleVideo = null;;
			try {
				pathSampleVideo = ServiceUtility.uploadVideoFile(file, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			IContainer container = IContainer.make();
			int result=10;
			result = container.open(pathSampleVideo,IContainer.Type.READ,null);

			try {
				if(result<0) {

					model.addAttribute("error_msg",CommonData.RECORD_ERROR);
					return "updateTestimonial";

				}else {
						if(container.getDuration()>CommonData.videoDuration) {

							model.addAttribute("error_msg",CommonData.VIDEO_DURATION_ERROR);
							Path deletePreviousPath=Paths.get(pathSampleVideo);
							Files.delete(deletePreviousPath);
							return "updateTestimonial";
					}
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				model.addAttribute("error_msg",CommonData.RECORD_ERROR);
				return "updateTestimonial";
			}

			    ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial+test.getTestimonialId());
				String pathtoUploadPoster=ServiceUtility.uploadVideoFile(file, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial+test.getTestimonialId());
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());
				test.setName(name);
				test.setDescription(desc);
				test.setFilePath(document);

				testService.save(test);


		}catch (Exception e) {
			// TODO: handle exception

			e.printStackTrace();
			model.addAttribute("error_msg", CommonData.RECORD_ERROR);
			return "updateTestimonial";    // throw a error
		}
		}else {

			test.setName(name);
			test.setDescription(desc);

			testService.save(test);
		}


		model.addAttribute("success_msg", CommonData.RECORD_SAVE_SUCCESS_MSG);

		return "updateTestimonial";
	}


	/************************************END**********************************************/

	/************************************ROLE MANGAEMENT OPERATION**********************************************/

	/**
	 * redirects to add contributor page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addContributorRole", method = RequestMethod.GET)
	public String addContributorGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Language> languages=lanService.getAllLanguages();

		model.addAttribute("languages", languages);
		return "addContributorRole";
	}

	/**
	 * add contributor role into system
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addContributorRole", method = RequestMethod.POST)
	public String addContributorPost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String lanName=req.getParameter("selectedLan");
		
		if(lanName == null) {
			
			return "redirect:/addContributorRole";
		}

		Language lan=lanService.getByLanName(lanName);
		
		if(lan == null) {
			
			return "redirect:/addContributorRole";
		}

		Role role=roleService.findByname(CommonData.contributorRole);
		List<UserRole> userRoles = usrRoleService.findByLanUser(lan, usr, role);
		if(!userRoles.isEmpty()) {
			System.out.println("***************IF True*****");
			System.out.println(usrRoleService.findByLanUser(lan, usr, role));

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);

			model.addAttribute("error_msg", CommonData.CONTRIBUTOR_ERROR);

			return "addContributorRole";
		}

		UserRole usrRole=new UserRole();
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
			e.printStackTrace();
		}

		List<Language> languages=lanService.getAllLanguages();

		model.addAttribute("languages", languages);

		return "addContributorRole";
	}
	
	/**
	 * redirects to add contributor page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addExternalContributorRole", method = RequestMethod.GET)
	public String addExternalContributorGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Language> languages=lanService.getAllLanguages();

		model.addAttribute("languages", languages);
		return "addExternalContributorRole";
	}

	/**
	 * add contributor role into system
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addExternalContributorRole", method = RequestMethod.POST)
	public String addExternalContributorPost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String lanName=req.getParameter("selectedLan");
		
		if(lanName == null) {
			
			return "redirect:/addExternalContributorRole";
		}

		Language lan=lanService.getByLanName(lanName);
		
		if(lan == null) {
			
			return "redirect:/addExternalContributorRole";
		}

		Role role=roleService.findByname(CommonData.externalContributorRole);
		List<UserRole> userRoles = usrRoleService.findByLanUser(lan, usr, role);
		if(!userRoles.isEmpty()) {
			System.out.println("***************IF True*****");
			System.out.println(usrRoleService.findByLanUser(lan, usr, role));

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);

			model.addAttribute("error_msg", CommonData.CONTRIBUTOR_ERROR);

			return "addExternalContributorRole";
		}

		UserRole usrRole=new UserRole();
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
			e.printStackTrace();
		}

		List<Language> languages=lanService.getAllLanguages();

		model.addAttribute("languages", languages);

		return "addExternalContributorRole";
	}

	/**
	 * redirects to add admin role page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addAdminRole", method = RequestMethod.GET)
	public String addAdminPost(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Language> languages=lanService.getAllLanguages();
		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);
		model.addAttribute("languages", languages);


		return "addAdminRole";
	}

	/**
	 * add admin role into system
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addAdminRole", method = RequestMethod.POST)
	public String addAdminPost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String lanName=req.getParameter("selectedLan");
		String catName=req.getParameter("catSelected");

		Category cat=catService.findBycategoryname(catName);

		Language lan=lanService.getByLanName(lanName);
		
		if(cat == null) {

			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", "Please Try Again");

			return "addAdminRole";
		}
		
		if(lan == null) {

			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", "Please Try Again");

			return "addAdminRole";
		}

		Role role=roleService.findByname(CommonData.adminReviewerRole);

		if(usrRoleService.findByLanCatUser(lan, cat, usr, role)!=null) {

			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);

			return "addAdminRole";
		}

		UserRole usrRole=new UserRole();
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
			e.printStackTrace();
												// accommodate error message
		}

		List<Language> languages=lanService.getAllLanguages();
		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		model.addAttribute("success_msg", CommonData.ADMIN_ADDED_SUCCESS_MSG);
		return "addAdminRole";
	}

	/**
	 * redirects to add domain role page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addDomainRole", method = RequestMethod.GET)
	public String addDomainGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Language> languages=lanService.getAllLanguages();
		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		return "addDomainRole";
	}

	/**
	 * add domain role into system
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addDomainRole", method = RequestMethod.POST)
	public String addDomainPost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String lanName=req.getParameter("selectedLan");
		String catName=req.getParameter("catSelected");

		Category cat=catService.findBycategoryname(catName);

		Language lan=lanService.getByLanName(lanName);
		
		if(cat == null) {

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", "Please try Again");

			return "addDomainRole";
		}
		
		if(lan == null) {

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", "Please try Again");

			return "addDomainRole";
		}
		
		Role role=roleService.findByname(CommonData.domainReviewerRole);

		if(usrRoleService.findByLanCatUser(lan, cat, usr, role)!=null) {

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);

			return "addDomainRole";
		}
		
		int newConsultid=consultService.getNewConsultantId();
		Consultant local=new Consultant();
		local.setConsultantId(newConsultid);
		local.setDescription("null");
		local.setDateAdded(ServiceUtility.getCurrentTime());
		local.setUser(usr);

		Set<Consultant> consults=new HashSet<Consultant>();
		consults.add(local);


		UserRole usrRole=new UserRole();
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
			e.printStackTrace();
			return "addDomainRole";									// accommodate error message
		}

		List<Language> languages=lanService.getAllLanguages();
		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		return "addDomainRole";
	}
	
	/**
	 * redirects to add quality page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addQualityRole", method = RequestMethod.GET)
	public String addQualityGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Language> languages=lanService.getAllLanguages();
		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		return "addQualityRole";
	}

	/**
	 * add quality role into system
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addQualityRole", method = RequestMethod.POST)
	public String addQualityPost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String lanName=req.getParameter("selectedLan");
		String catName=req.getParameter("catSelected");

		Category cat=catService.findBycategoryname(catName);

		Language lan=lanService.getByLanName(lanName);
		
		if(cat == null) {

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", "Please try Again");

			return "addQualityRole";
		}
		
		if(lan == null) {

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", "Please try Again");

			return "addQualityRole";
		}
		
		Role role=roleService.findByname(CommonData.qualityReviewerRole);

		if(usrRoleService.findByLanCatUser(lan, cat, usr, role)!=null) {

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);
			List<Language> languages=lanService.getAllLanguages();
			List<Category> categories=catService.findAll();

			model.addAttribute("categories", categories);

			model.addAttribute("languages", languages);
			model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);

			return "addQualityRole";
		}

		UserRole usrRole=new UserRole();
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
			e.printStackTrace();
												// accommodate error message
		}

		List<Language> languages=lanService.getAllLanguages();
		List<Category> categories=catService.findAll();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		return "addQualityRole";
	}

	/**
	 * redirects to add master trainer page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addMasterTrainerRole", method = RequestMethod.GET)
	public String addMasterTrainerGet(Model model,Principal principal) {

		User usr=new User();
		List<IndianLanguage> languages = iLanService.findAll();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());

		}

		model.addAttribute("userInfo", usr);
		model.addAttribute("languages", languages);

		if(usr.getProfilePic() == null) {
			model.addAttribute("error_msg", CommonData.ADD_PROFILE_PIC_CONSTRAINT);
			return "profileView";
		}

		Role role = roleService.findByname(CommonData.masterTrainerRole);

		if(!usrRoleService.findByRoleUser(usr, role).isEmpty()) {

			model.addAttribute("success_msg", "Request Already submitted for role");
			model.addAttribute("alredaySubmittedFlag", true);
			return "addMasterTrainerRole";
		}
		List<OrganizationRole> org_roles = organizationRoleService.findAll();
		System.err.println("**********************ROLES**********************************");
		System.err.println(org_roles);
		System.err.println("*************************ROLES*******************************");
		model.addAttribute("org_roles", org_roles);

		model.addAttribute("alredaySubmittedFlag", false);
		return "addMasterTrainerRole";
	}

	/**
	 * add master trainer into system
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addMasterTrainerRole", method = RequestMethod.POST)
	public String addMasterTrainerPost(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String name=req.getParameter("name");
		String age=req.getParameter("age");
		String mobileNumber=req.getParameter("phone");
		String address=req.getParameter("address");
		String organization=(req.getParameter("org"));
		String exp=req.getParameter("experience");
		String aadhar=req.getParameter("aadharNumber");
		String lang=req.getParameter("languages");
		String roleOrg=req.getParameter("newRole");
		System.out.println("******"+lang);

		List<OrganizationRole> org_roles = organizationRoleService.findAll();
		System.err.println("**********************ROLES**********************************");
		System.err.println(org_roles);
		System.err.println("*************************ROLES*******************************");
		model.addAttribute("org_roles", org_roles);

		int userIndianMappingId=userIndianMappingService.getNewId();

		Set<UserIndianLanguageMapping> userIndianMapping = new HashSet<UserIndianLanguageMapping>();

		if(aadhar.length()!=12) {
			 // throw error
			model.addAttribute("error_msg", "Invalid aadhar number");
			return "addMasterTrainerRole";
		}

		if(mobileNumber.length()!=10) {

			// throw error
			model.addAttribute("error_msg", "Invalid phone number");
			return "addMasterTrainerRole";
		}


		String[] lan = lang.split("&");
		for(String x : lan) {


			System.out.println("lanName:"+ x);

			String[] y = x.split("_");
			for(int z=1 ; z<y.length ; z++) {

				IndianLanguage temp = iLanService.findByName(y[0]);
				System.out.println(temp.getLanName());

				UserIndianLanguageMapping tempUser = new UserIndianLanguageMapping();
				tempUser.setId(userIndianMappingId++);
				tempUser.setUser(usr);
				tempUser.setIndianlan(temp);

				for(char xx : y[z].toCharArray()) {

					System.out.println(xx);
					if(xx=='r') {
						tempUser.setRead(true);
						System.out.println("read"+xx);
					}
//					else if(xx=='w') {
//						tempUser.setWrite(true);
//						System.out.println(xx);
//					}
					else if(xx=='s') {
						tempUser.setSpeak(true);
						System.out.println(xx);
					}
				}

				userIndianMapping.add(tempUser);
				System.out.println("name"+y[z]);
			}

		}

		Role role=roleService.findByname(CommonData.masterTrainerRole);

		List<UserRole> userRoles = usrRoleService.findByRoleUser(usr, role);
		if(!userRoles.isEmpty()) {
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
		System.err.print("**********************************");
		System.err.print(roleOrg);
		System.err.print("**********************************");

		List<OrganizationRole> orgRolesList = organizationRoleService.findAll();
		boolean isRoleExist = orgRolesList.stream().anyMatch(o -> o.getRole().equals(roleOrg));
		OrganizationRole r;
		if(isRoleExist) {
			 r = organizationRoleService.getByRole(roleOrg);
		}else {
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

			UserRole usrRole=new UserRole();
			usrRole.setCreated(ServiceUtility.getCurrentTime());
			usrRole.setUser(usr);
			usrRole.setRole(role);
			usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

			usrRoleService.save(usrRole);

			model.addAttribute("success_msg", "Request submitted for role successfully");

		}catch (Exception e) {
			// TODO: handle exception

			model.addAttribute("error_msg", "Error in submitting request");
			// throw error
		}
		List<IndianLanguage> languages = iLanService.findAll();

		model.addAttribute("userInfo", usr);
		model.addAttribute("success_msg", "Request submitted for role successfully");

		return "addMasterTrainerRole";
	}


	/************************************END****************************************************************/

	/*********************************** Approve Role ************************************************/

	/**
	 * redirects to approve role page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/approveRole", method = RequestMethod.GET)
	public String approveRoleGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role contributor=roleService.findByname(CommonData.contributorRole);
		Role admin=roleService.findByname(CommonData.adminReviewerRole);
		Role master=roleService.findByname(CommonData.masterTrainerRole);
		Role quality=roleService.findByname(CommonData.qualityReviewerRole);
		Role domain=roleService.findByname(CommonData.domainReviewerRole);
		Role external=roleService.findByname(CommonData.externalContributorRole);

		List<UserRole> adminReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(admin,false,false);
		List<UserRole> masterTrainer = usrRoleService.findAllByRoleAndStatusAndRevoked(master,false,false);
		List<UserRole> qualityReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(quality,false,false);
		List<UserRole> contributorReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(contributor,false,false);
		List<UserRole> domainReviewer = usrRoleService.findAllByRoleAndStatusAndRevoked(domain,false,false);
		List<UserRole> externalUser= usrRoleService.findAllByRoleAndStatusAndRevoked(external,false,false);
		

		model.addAttribute("userInfoAdmin", adminReviewer);
		model.addAttribute("userInfoQuality", qualityReviewer);
		model.addAttribute("userInfoContributor", contributorReviewer);
		model.addAttribute("userInfoMaster", masterTrainer);
		model.addAttribute("userInfoDomain", domainReviewer);
		model.addAttribute("userInfoExternal", externalUser);


		return "approveRole";
	}




	/******************************************END **************************************************/


	/*************************************** ASSIGN CONTRINUTOR (NOT IN USE FOR NOW) ****************************************/
	
	/**
	 * redirects to assign contributor edit page
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/assignContributor/edit/{id}", method = RequestMethod.GET)
	public String editAssignContributor(@PathVariable Long id,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}
		Role role=roleService.findByname(CommonData.contributorRole);
		List<UserRole> userRoles= usrRoleService.findAllByRoleAndStatusAndRevoked(role, true,false);

		model.addAttribute("userByContributors", userRoles);
		model.addAttribute("userInfo", usr);

		return "updateAssignContributor";
	}

	/**
	 * redirects to assign contributor page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/assignTutorialToContributor", method = RequestMethod.GET)
	public String assignTutorialToContributoreGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role role=roleService.findByname(CommonData.contributorRole);
		Role role1=roleService.findByname(CommonData.externalContributorRole);

		List<ContributorAssignedTutorial> userRoles = conRepo.findAll();
		
		List<UserRole> userRolesTemp= usrRoleService.findAllByRoleAndStatusAndRevoked(role, true,false);
		userRolesTemp.addAll(usrRoleService.findAllByRoleAndStatusAndRevoked(role1, true,false));
		
		HashSet<String> userRolesUniqueTemp = new HashSet<>();
		
		for(UserRole x : userRolesTemp) {
			userRolesUniqueTemp.add(x.getUser().getUsername());
		}

		model.addAttribute("userByContributorsAssigned", userRoles);
		
		model.addAttribute("userByContributors", userRolesUniqueTemp);

		return "assignContributorList";
	}

	/**
	 * Assign tutorial to contributor
	 * @param model Model object
	 * @param principal Principal object
	 * @param contributorName String object
	 * @param lanName String object
	 * @param catName String object
	 * @param topics list of String object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/assignTutorialToContributor", method = RequestMethod.POST)
	public String assignTutorialToContributorePost(Model model,Principal principal,
												@RequestParam(name = "contributorName") String contributorName,
												@RequestParam(name = "languageName") String lanName,
												@RequestParam(name = "contributorCategory") String catName,
												@RequestParam(name = "inputTopic") String[] topics) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role role=roleService.findByname(CommonData.contributorRole);
		Role role1=roleService.findByname(CommonData.externalContributorRole);

		List<ContributorAssignedTutorial> userRoles = conRepo.findAll();

		List<UserRole> userRolesTemp= usrRoleService.findAllByRoleAndStatusAndRevoked(role, true,false);
		userRolesTemp.addAll(usrRoleService.findAllByRoleAndStatusAndRevoked(role1, true,false));
		
		HashSet<String> userRolesUniqueTemp = new HashSet<>();
		
		for(UserRole x : userRolesTemp) {
			userRolesUniqueTemp.add(x.getUser().getUsername());
		}

		model.addAttribute("userByContributorsAssigned", userRoles);
		
		model.addAttribute("userByContributors", userRolesUniqueTemp);

		System.out.println(catName);

		Language lan=lanService.getByLanName(lanName);
		Category cat=catService.findByid(Integer.parseInt(catName));
		User userAssigned=userService.findByUsername(contributorName);
		Set<ContributorAssignedTutorial> conTutorials=new HashSet<ContributorAssignedTutorial>();
		int conNewId=conRepo.getNewId();
		int conMutliUserNewId = conMultiUser.getNewId();

		for(String topic:topics) {

			Topic localtopic=topicService.findById(Integer.parseInt(topic));
			if(localtopic != null) {

				TopicCategoryMapping topicCat=topicCatService.findAllByCategoryAndTopic(cat, localtopic);

				System.out.println(topicCat.getTopicCategoryId());
				ContributorAssignedTutorial x=conRepo.findByTopicCatAndLanViewPart(topicCat, lan);

				if(x == null) {

					ContributorAssignedTutorial temp=new ContributorAssignedTutorial(conNewId++, ServiceUtility.getCurrentTime(), topicCat, lan);
					conRepo.save(temp);
					ContributorAssignedMultiUserTutorial multiUser = new ContributorAssignedMultiUserTutorial(conMutliUserNewId++, ServiceUtility.getCurrentTime(), userAssigned, temp);
					conMultiUser.save(multiUser);
					
					//conTutorials.add(temp);

				}else {
					// throw error for repeated task
					
					//model.addAttribute("error_msg", CommonData.CONTRIBUTOR_ERROR_MSG);
					
					ContributorAssignedMultiUserTutorial multiUser = new ContributorAssignedMultiUserTutorial(conMutliUserNewId++, ServiceUtility.getCurrentTime(), userAssigned, x);
					conMultiUser.save(multiUser);
					
					// return "assignContributorList";
				}



			}else {
				// throw error as topic doesn't exist
				model.addAttribute("error_msg", CommonData.CONTRIBUTOR_TOPIC_ERROR);
				return "assignContributorList";
			}
		}


		//userService.addUserToContributorTutorial(userAssigned, conTutorials);
		
		userRoles = conRepo.findAll();

		userRolesTemp= usrRoleService.findAllByRoleAndStatusAndRevoked(role, true,false);
		
		for(UserRole x : userRolesTemp) {
			userRolesUniqueTemp.add(x.getUser().getUsername());
		}

		model.addAttribute("userByContributorsAssigned", userRoles);
		
		model.addAttribute("userByContributors", userRolesUniqueTemp);


		model.addAttribute("success_msg", CommonData.CONTRIBUTOR_ASSIGNED_TUTORIAL);

		return "assignContributorList";
	}




	/********************************************END*************************************************/


	/*********************************** CONTRIBUTOR ROLE OPERATION *************************************/
	/**
	 * redirects page to uplaod tutorial under contributor role
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/uploadTutorial", method = RequestMethod.GET)
	public String uploadTutorialGet(Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<String> catName = new ArrayList<String>();
		List<ContributorAssignedMultiUserTutorial> con=conMultiUser.getAllByuser(usr);

		for(ContributorAssignedMultiUserTutorial temp:con) {
			ContributorAssignedTutorial conTemp = conRepo.findById(temp.getConAssignedTutorial().getId());
			catName.add(conTemp.getTopicCatId().getCat().getCatName());

		}
		HashSet<String> uniqueCatName=new HashSet<String>(catName);    // to return unique value


		model.addAttribute("contributorTutorial", uniqueCatName);
		return "uploadTutorialPre";
	}


	/**
	 * load tutorial component page to add various component for the tutorial
	 * @param model Model object
	 * @param principal Principal object
	 * @param categoryName String object
	 * @param topicId int value
	 * @param langName String object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/uploadTutorial", method = RequestMethod.POST)
	public String uploadTutorialPost(Model model,Principal principal,
										@RequestParam(value="categoryName") String categoryName,
										@RequestParam(name="inputTopic") int topicId,
										@RequestParam(name="inputLanguage") String langName) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Category> categories = catService.findAll();
		Category cat=catService.findBycategoryname(categoryName);
		Topic topic=topicService.findById(topicId);
		Language lan=lanService.getByLanName(langName);
		TopicCategoryMapping topicCat=topicCatService.findAllByCategoryAndTopic(cat, topic);
		
		if(!lan.getLangName().equalsIgnoreCase("english")) {
			
			boolean goAhead = false;
			List<Tutorial> tutTemp = null ;
			List<ContributorAssignedTutorial> tempCon = conRepo.findByTopicCat(topicCat);
			for(ContributorAssignedTutorial x : tempCon) {
				if(x.getLan().getLangName().equalsIgnoreCase("english")) {
					goAhead =true;
					tutTemp = tutService.findAllByContributorAssignedTutorial(x);
					break;
				}
			}
			
			if(goAhead == false) {
				model.addAttribute("error_msg", "Please Add English Verion of tutorial first");
				return "uploadTutorialPost";
			}else {
				
				if(tutTemp.isEmpty()) {
					model.addAttribute("error_msg", "Please Add English Verion of tutorial first");
					return "uploadTutorialPost";
					
				}else {
					for(Tutorial x : tutTemp) {
						if(!x.isStatus()) {
							model.addAttribute("error_msg", "Please Add English Verion of tutorial first");
							return "uploadTutorialPost";
						}
					}
				}
				
			}
			
		}
		
		ContributorAssignedTutorial conTutorial=conRepo.findByTopicCatAndLanViewPart( topicCat, lan);
		List<Tutorial> tutorials=tutService.findAllByContributorAssignedTutorial(conTutorial);

		if(tutorials.isEmpty()) {

			model.addAttribute("statusOutline", CommonData.ADD_CONTENT);
			model.addAttribute("statusScript", CommonData.ADD_CONTENT);
			model.addAttribute("statusSlide", CommonData.ADD_CONTENT);
			model.addAttribute("statusVideo", CommonData.ADD_CONTENT);
			model.addAttribute("statusKeyword", CommonData.ADD_CONTENT);
			model.addAttribute("statusPreReq", CommonData.ADD_CONTENT);
			model.addAttribute("statusGraphics", CommonData.ADD_CONTENT);
			model.addAttribute("tutorial", null);
		}else {

			for(Tutorial local:tutorials) {

				model.addAttribute("statusOutline", CommonData.tutorialStatus[local.getOutlineStatus()]);
				model.addAttribute("statusScript", CommonData.tutorialStatus[local.getScriptStatus()]);
				model.addAttribute("statusSlide", CommonData.tutorialStatus[local.getSlideStatus()]);
				model.addAttribute("statusVideo", CommonData.tutorialStatus[local.getVideoStatus()]);
				model.addAttribute("statusKeyword", CommonData.tutorialStatus[local.getKeywordStatus()]);
				model.addAttribute("statusPreReq", CommonData.tutorialStatus[local.getPreRequisticStatus()]);

				model.addAttribute("tutorial", local);
				

				
				if(local.getVideo() != null) {

				IContainer container = IContainer.make();
				int result=10;
				result = container.open(env.getProperty("spring.applicationexternalPath.name")+local.getVideo(),IContainer.Type.READ,null);
				
				System.out.println("Video Duration"+container.getDuration()/1000000);
				System.out.println("file Size"+container.getFileSize()/1000000);

			
					
				IStream stream = container.getStream(0);
				IStreamCoder coder = stream.getStreamCoder();
				System.out.println("width :"+coder.getWidth());
				System.out.println("Height :"+coder.getHeight());
				
				model.addAttribute("FileWidth", coder.getWidth());
				model.addAttribute("FileHeight", coder.getHeight());
				model.addAttribute("fileSizeInMB", container.getFileSize()/1000000);
				model.addAttribute("FileDurationInSecond", container.getDuration()/1000000);
				
				container.close();


				}

				

				List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO, local);
				List<Comment> comScript = comService.getCommentBasedOnTutorialType(CommonData.SCRIPT, local);
				List<Comment> comSlide = comService.getCommentBasedOnTutorialType(CommonData.SLIDE, local);

				List<Comment> comKeyword = comService.getCommentBasedOnTutorialType(CommonData.KEYWORD, local);
				List<Comment> comPreRequistic = comService.getCommentBasedOnTutorialType(CommonData.PRE_REQUISTIC, local);
				List<Comment> comOutline = comService.getCommentBasedOnTutorialType(CommonData.OUTLINE, local);

				model.addAttribute("comOutline", comOutline);
				model.addAttribute("comScript",comScript );
				model.addAttribute("comSlide",comSlide );
				model.addAttribute("comVideo", comVideo);
				model.addAttribute("comKeyword", comKeyword);
				model.addAttribute("comPreReq", comPreRequistic);
			}
		}

		model.addAttribute("category", cat);
		model.addAttribute("topic", topic);
		model.addAttribute("language", lan);
		model.addAttribute("categories", categories);
		if(!lan.getLangName().equalsIgnoreCase("english")) {
			model.addAttribute("otherLan", false);
		}else {
			model.addAttribute("otherLan", true);
		}
		return "uploadTutorialPost";
	}

	/**
	 * List all the tutorial for contributor  to review
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "listTutorialForContributorReview", method = RequestMethod.GET)
	public String listContributorReviewTutorialGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		Set<Tutorial> tutorials = new HashSet<Tutorial>();

		List<ContributorAssignedMultiUserTutorial> conTutorials=conMultiUser.getAllByuser(usr);
		
		for(ContributorAssignedMultiUserTutorial conMultiTemp : conTutorials) {
			
			ContributorAssignedTutorial conTemp = conRepo.findById(conMultiTemp.getConAssignedTutorial().getId());
			
			tutorials.addAll(tutService.findAllByContributorAssignedTutorial(conTemp));
		}

		//List<Tutorial> tutorials =  tutService.findAllByContributorAssignedTutorialList(conTutorials);

		model.addAttribute("tutorial", tutorials);

		return "listTutorialContributorReviwer";

	}

	/**
	 * redirect to contributor review page given tutorial id
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "Contributor/review/{catName}/{topicName}/{language}", method = RequestMethod.GET)
	public String listContributorReviewTutorialGet(@PathVariable(name = "catName") String cat,
			@PathVariable (name = "topicName") String topic,
			@PathVariable (name = "language") String lan,Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}
		
		List<Category> categories = catService.findAll();
		model.addAttribute("categories", categories);
		model.addAttribute("userInfo", usr);
		
		Category catName = catService.findBycategoryname(cat);
		Topic topicName = topicService.findBytopicName(topic);
		Language lanName = lanService.getByLanName(lan);
		TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
		ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);
		
		if(catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {
			return "redirect:/listTutorialForContributorReview";
		}
		
		Tutorial tutorial=tutService.findAllByContributorAssignedTutorial(conTut).get(0);

		if(tutorial == null) {
			// throw a error
			model.addAttribute("error_msg", CommonData.STATUS_ERROR);
			model.addAttribute("tutorialNotExist", "Bad request");   //  throw proper error
			return "redirect:/listTutorialForContributorReview";

		}

//		if(tutorial.getConAssignedTutorial().getUser().getId() != usr.getId()) {
//
//			return "redirect:/listTutorialForContributorReview";
//		}

		model.addAttribute("statusOutline", CommonData.tutorialStatus[tutorial.getOutlineStatus()]);
		model.addAttribute("statusScript", CommonData.tutorialStatus[tutorial.getScriptStatus()]);
		model.addAttribute("statusSlide", CommonData.tutorialStatus[tutorial.getSlideStatus()]);
		model.addAttribute("statusVideo", CommonData.tutorialStatus[tutorial.getVideoStatus()]);
		model.addAttribute("statusKeyword", CommonData.tutorialStatus[tutorial.getKeywordStatus()]);
		model.addAttribute("statusPreReq", CommonData.tutorialStatus[tutorial.getPreRequisticStatus()]);

		model.addAttribute("tutorial", tutorial);
		

		if(tutorial.getVideo() != null) {

		IContainer container = IContainer.make();
		int result=10;
		result = container.open(env.getProperty("spring.applicationexternalPath.name")+tutorial.getVideo(),IContainer.Type.READ,null);
		
		System.out.println("Video Duration"+container.getDuration()/1000000);
		System.out.println("file Size"+container.getFileSize()/1000000);
		

		IStream stream = container.getStream(0);
		IStreamCoder coder = stream.getStreamCoder();
		System.out.println("width :"+coder.getWidth());
		System.out.println("Height :"+coder.getHeight());
		
		model.addAttribute("FileWidth", coder.getWidth());
		model.addAttribute("FileHeight", coder.getHeight());
		

		model.addAttribute("fileSizeInMB", container.getFileSize()/1000000);
		model.addAttribute("FileDurationInSecond", container.getDuration()/1000000);
		
		container.close();

		}


		List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO, tutorial);
		List<Comment> comScript = comService.getCommentBasedOnTutorialType(CommonData.SCRIPT, tutorial);
		List<Comment> comSlide = comService.getCommentBasedOnTutorialType(CommonData.SLIDE, tutorial);

		List<Comment> comKeyword = comService.getCommentBasedOnTutorialType(CommonData.KEYWORD, tutorial);
		List<Comment> comPreRequistic = comService.getCommentBasedOnTutorialType(CommonData.PRE_REQUISTIC, tutorial);
		List<Comment> comOutline = comService.getCommentBasedOnTutorialType(CommonData.OUTLINE, tutorial);

		model.addAttribute("comOutline", comOutline);
		model.addAttribute("comScript",comScript );
		model.addAttribute("comSlide",comSlide );
		model.addAttribute("comVideo", comVideo);
		model.addAttribute("comKeyword", comKeyword);
		model.addAttribute("comPreReq", comPreRequistic);

		model.addAttribute("category", tutorial.getConAssignedTutorial().getTopicCatId().getCat());
		model.addAttribute("topic", tutorial.getConAssignedTutorial().getTopicCatId().getTopic());
		model.addAttribute("language", tutorial.getConAssignedTutorial().getLan());
		
		if(!tutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {
			model.addAttribute("otherLan", false);
		}else {
			model.addAttribute("otherLan", true);
		}

		return "uploadTutorialPost";



	}




	/****************************************END********************************************************/

/********************************** operation at Admin End *****************************************/
	
	/**
	 * List all the tutorial for Admin reviewer to review
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "listTutorialForAdminReview", method = RequestMethod.GET)
	public String listAdminReviewTutorialGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		HashSet<Tutorial> toReview = new HashSet<>();
		HashSet<Tutorial> reviewed = new HashSet<>();
		Role role=roleService.findByname(CommonData.adminReviewerRole);

		List<UserRole> userRoles=usrRoleService.findByRoleUser(usr, role);
		List<TopicCategoryMapping> localMap=topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

		List<ContributorAssignedTutorial> conTutorials=conRepo.findByTopicCatLan(localMap, userRoles);

		List<Tutorial> tutorials =  tutService.findAllByContributorAssignedTutorialList(conTutorials);
		for(Tutorial temp:tutorials) {

			if(temp.getVideoStatus() == CommonData.ADMIN_STATUS) {
				toReview.add(temp);
			}else if(temp.getVideoStatus() > CommonData.ADMIN_STATUS) {
				reviewed.add(temp);
			}
		}

		model.addAttribute("tutorialToReview", toReview);
		model.addAttribute("tutorialReviewed", reviewed);
		return "listTutorialAdminReviwer";



	}

	/**
	 * redirect to admin review page given tutorial id
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "adminreview/review/{catName}/{topicName}/{language}", method = RequestMethod.GET)
	public String listAdminReviewTutorialGet(@PathVariable(name = "catName") String cat,
			@PathVariable (name = "topicName") String topic,
			@PathVariable (name = "language") String lan,Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		Category catName = catService.findBycategoryname(cat);
		Topic topicName = topicService.findBytopicName(topic);
		Language lanName = lanService.getByLanName(lan);
		TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
		ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);
		
		if(catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {
			return "redirect:/listTutorialForAdminReview";
		}
		
		Tutorial tutorial=tutService.findAllByContributorAssignedTutorial(conTut).get(0);

		if(tutorial.getVideoStatus() != CommonData.ADMIN_STATUS) {
			 // return some error
			return "redirect:/listTutorialForAdminReview";
		}

		if(tutorial == null) {
			// throw a error
			return "redirect:/listTutorialForAdminReview";

		}


		List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO, tutorial);

		model.addAttribute("comVideo", comVideo);


		model.addAttribute("category", tutorial.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
		model.addAttribute("topic", tutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
		model.addAttribute("language", tutorial.getConAssignedTutorial().getLan().getLangName());
		model.addAttribute("tutorial", tutorial);
		

		if(tutorial.getVideo() != null) {
		IContainer container = IContainer.make();
		int result=10;
		result = container.open(env.getProperty("spring.applicationexternalPath.name")+tutorial.getVideo(),IContainer.Type.READ,null);
		
		System.out.println("Video Duration"+container.getDuration()/1000000);
		System.out.println("file Size"+container.getFileSize()/1000000);
		

		IStream stream = container.getStream(0);
		IStreamCoder coder = stream.getStreamCoder();
		System.out.println("width :"+coder.getWidth());
		System.out.println("Height :"+coder.getHeight());
		
		model.addAttribute("FileWidth", coder.getWidth());
		model.addAttribute("FileHeight", coder.getHeight());
		
		model.addAttribute("fileSizeInMB", container.getFileSize()/1000000);
		model.addAttribute("FileDurationInSecond", container.getDuration()/1000000);
		
		container.close();
		
		}
		

		model.addAttribute("success_msg", CommonData.Video_STATUS_SUCCESS_MSG);
		return "addContentAdminReview";



	}



	/***********************************END ***************************************************************/

	/*************************** OPERATION AT DOMAIN REVIEWER END ***********************************/
	
	/**
	 * List all the tutorial for domain reviewer to review
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "listTutorialForDomainReview", method = RequestMethod.GET)
	public String listDomainReviewTutorialGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		HashSet<Tutorial> toReview = new HashSet<>();
		HashSet<Tutorial> published = new HashSet<>();
		Role role=roleService.findByname(CommonData.domainReviewerRole);

		List<UserRole> userRoles=usrRoleService.findByRoleUser(usr, role);
		List<TopicCategoryMapping> localMap=topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

		List<ContributorAssignedTutorial> conTutorials=conRepo.findByTopicCatLan(localMap, userRoles);

		List<Tutorial> tutorials =  tutService.findAllByContributorAssignedTutorialList(conTutorials);
		for(Tutorial temp:tutorials) {

			if(temp.getOutlineStatus() > CommonData.DOMAIN_STATUS && temp.getScriptStatus() > CommonData.DOMAIN_STATUS &&
					temp.getSlideStatus() > CommonData.DOMAIN_STATUS && temp.getKeywordStatus() > CommonData.DOMAIN_STATUS &&
					temp.getVideoStatus() > CommonData.DOMAIN_STATUS &&
					temp.getPreRequisticStatus() > CommonData.DOMAIN_STATUS) {

				published.add(temp);
			}else {
				toReview.add(temp);
			}

		}

		model.addAttribute("tutorialToReview", toReview);
		model.addAttribute("tutorialReviewed", published);
		return "listTutorialDomainReviewer";
	}

	/**
	 * redirect to domain review page given tutorial id
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "domainreview/review/{catName}/{topicName}/{language}", method = RequestMethod.GET)
	public String listDomainReviewTutorialGet(@PathVariable(name = "catName") String cat,
			@PathVariable (name = "topicName") String topic,
			@PathVariable (name = "language") String lan,Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		Category catName = catService.findBycategoryname(cat);
		Topic topicName = topicService.findBytopicName(topic);
		Language lanName = lanService.getByLanName(lan);
		TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
		ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);
		
		if(catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {
			return "redirect:/listTutorialForDomainReview";
		}
		
		Tutorial tutorial=tutService.findAllByContributorAssignedTutorial(conTut).get(0);


		if(tutorial == null) {

			return "redirect:/listTutorialForDomainReview";

		}

		List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO,  tutorial);
		List<Comment> comScript = comService.getCommentBasedOnTutorialType(CommonData.SCRIPT,tutorial);
		List<Comment> comSlide = comService.getCommentBasedOnTutorialType(CommonData.SLIDE, tutorial);

		List<Comment> comKeyword = comService.getCommentBasedOnTutorialType(CommonData.KEYWORD, tutorial);
		List<Comment> comPreRequistic = comService.getCommentBasedOnTutorialType(CommonData.PRE_REQUISTIC,  tutorial);
		List<Comment> comOutline = comService.getCommentBasedOnTutorialType(CommonData.OUTLINE, tutorial);

		model.addAttribute("comOutline", comOutline);
		model.addAttribute("comScript",comScript );
		model.addAttribute("comSlide",comSlide );
		model.addAttribute("comVideo", comVideo);
		model.addAttribute("comKeyword", comKeyword);
		model.addAttribute("comPreReq", comPreRequistic);


		model.addAttribute("statusOutline", CommonData.tutorialStatus[tutorial.getOutlineStatus()]);
		model.addAttribute("statusScript", CommonData.tutorialStatus[tutorial.getScriptStatus()]);
		model.addAttribute("statusSlide", CommonData.tutorialStatus[tutorial.getSlideStatus()]);
		model.addAttribute("statusVideo", CommonData.tutorialStatus[tutorial.getVideoStatus()]);
		model.addAttribute("statusKeyword", CommonData.tutorialStatus[tutorial.getKeywordStatus()]);
		model.addAttribute("statusPreReq", CommonData.tutorialStatus[tutorial.getPreRequisticStatus()]);

		model.addAttribute("tutorial", tutorial); 
		

		if(tutorial.getVideo() != null) {
		IContainer container = IContainer.make();
		int result=10;
		result = container.open(env.getProperty("spring.applicationexternalPath.name")+tutorial.getVideo(),IContainer.Type.READ,null);
		
		System.out.println("Video Duration"+container.getDuration()/1000000);
		System.out.println("file Size"+container.getFileSize()/1000000);
		

		IStream stream = container.getStream(0);
		IStreamCoder coder = stream.getStreamCoder();
		System.out.println("width :"+coder.getWidth());
		System.out.println("Height :"+coder.getHeight());
		
		model.addAttribute("FileWidth", coder.getWidth());
		model.addAttribute("FileHeight", coder.getHeight());
		
		model.addAttribute("fileSizeInMB", container.getFileSize()/1000000);
		model.addAttribute("FileDurationInSecond", container.getDuration()/1000000);
		
		container.close();
		}


		model.addAttribute("category", tutorial.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
		model.addAttribute("topic", tutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
		model.addAttribute("language", tutorial.getConAssignedTutorial().getLan().getLangName());
		
		if(!tutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {
			model.addAttribute("otherLan", false);
		}else {
			model.addAttribute("otherLan", true);
		}

		return "addContentDomainReview";



	}

	/*********************************** END *******************************************************/

	/*************************** OPERATION AT QUALITY REVIEWER END ***********************************/

	/**
	 * List all the tutorial for quality review
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "listTutorialForQualityReview", method = RequestMethod.GET)
	public String listQualityReviewTutorialGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		HashSet<Tutorial> toReview = new HashSet<>();
		HashSet<Tutorial> published = new HashSet<>();
		Role role=roleService.findByname(CommonData.qualityReviewerRole);

		List<UserRole> userRoles=usrRoleService.findByRoleUser(usr, role);
		List<TopicCategoryMapping> localMap=topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

		List<ContributorAssignedTutorial> conTutorials=conRepo.findByTopicCatLan(localMap, userRoles);

		List<Tutorial> tutorials =  tutService.findAllByContributorAssignedTutorialList(conTutorials);
		for(Tutorial temp:tutorials) {

			if(temp.getOutlineStatus() == CommonData.PUBLISH_STATUS && temp.getScriptStatus() == CommonData.PUBLISH_STATUS &&
					temp.getSlideStatus() == CommonData.PUBLISH_STATUS && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS &&
					temp.getVideoStatus() == CommonData.PUBLISH_STATUS  &&
					temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

				published.add(temp);
			}else {
				toReview.add(temp);
			}

		}

		model.addAttribute("tutorialToReview", toReview);
		model.addAttribute("tutorialReviewed", published);

		return "listTutorialQualityReviewer";


	}

	/**
	 * redirects page to view all the tutorial to be published
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "tutorialToPublish", method = RequestMethod.GET)
	public String tutorialToPublishGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		HashSet<Tutorial> published = new HashSet<>();
		Role role=roleService.findByname(CommonData.qualityReviewerRole);

		List<UserRole> userRoles=usrRoleService.findByRoleUser(usr, role);
		List<TopicCategoryMapping> localMap=topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

		List<ContributorAssignedTutorial> conTutorials=conRepo.findByTopicCatLan(localMap, userRoles);

		List<Tutorial> tutorials =  tutService.findAllByContributorAssignedTutorialList(conTutorials);
		for(Tutorial temp:tutorials) {

			if(temp.getOutlineStatus() >= CommonData.WAITING_PUBLISH_STATUS && temp.getScriptStatus() >= CommonData.WAITING_PUBLISH_STATUS &&
					temp.getSlideStatus() >= CommonData.WAITING_PUBLISH_STATUS && temp.getKeywordStatus() >= CommonData.WAITING_PUBLISH_STATUS &&
					temp.getVideoStatus() >= CommonData.WAITING_PUBLISH_STATUS &&
					temp.getPreRequisticStatus() >= CommonData.WAITING_PUBLISH_STATUS) {

				published.add(temp);
			}

		}

		model.addAttribute("tutorial", published);

		return "listTutorialPublishQualityReviwer";


	}

	/**
	 * publish the tutorial under quality role
	 * @param id int value
	 * @param model Model object
	 * @param principal principal object
	 * @param redirectAttributes RedirectAttributes object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "publish/{id}", method = RequestMethod.GET)
	public String publishTutorialGet(@PathVariable int id,Model model,Principal principal,RedirectAttributes redirectAttributes) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		Tutorial tutorial=tutService.getById(id);

		if(tutorial == null) {
			// throw a error
			model.addAttribute("tutorialNotExist", "Bad request");   //  throw proper error
			return "redirect:/tutorialToPublish";

		}

		tutorial.setKeywordStatus(CommonData.PUBLISH_STATUS);
		tutorial.setOutlineStatus(CommonData.PUBLISH_STATUS);
		tutorial.setSlideStatus(CommonData.PUBLISH_STATUS);
		tutorial.setScriptStatus(CommonData.PUBLISH_STATUS);
		tutorial.setPreRequisticStatus(CommonData.PUBLISH_STATUS);
		tutorial.setVideoStatus(CommonData.PUBLISH_STATUS);
		tutorial.setStatus(true);

		tutService.save(tutorial);
		model.addAttribute("success_msg", CommonData.PUBLISHED_SUCCESS);
		
		HashSet<Tutorial> published = new HashSet<>();
		Role role=roleService.findByname(CommonData.qualityReviewerRole);

		List<UserRole> userRoles=usrRoleService.findByRoleUser(usr, role);
		List<TopicCategoryMapping> localMap=topicCatService.findAllByCategoryBasedOnUserRoles(userRoles);

		List<ContributorAssignedTutorial> conTutorials=conRepo.findByTopicCatLan(localMap, userRoles);

		List<Tutorial> tutorials =  tutService.findAllByContributorAssignedTutorialList(conTutorials);
		for(Tutorial temp:tutorials) {

			if(temp.getOutlineStatus() >= CommonData.WAITING_PUBLISH_STATUS && temp.getScriptStatus() >= CommonData.WAITING_PUBLISH_STATUS &&
					temp.getSlideStatus() >= CommonData.WAITING_PUBLISH_STATUS && temp.getKeywordStatus() >= CommonData.WAITING_PUBLISH_STATUS &&
					temp.getVideoStatus() >= CommonData.WAITING_PUBLISH_STATUS &&
					temp.getPreRequisticStatus() >= CommonData.WAITING_PUBLISH_STATUS) {

				published.add(temp);
			}

		}

		model.addAttribute("tutorial", published);
		
	
        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.upload");

        try {
            
            Credential credential = Auth.authorize(scopes, "uploadvideo");

           
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).build();

            System.out.println("Uploading: " + tutorial.getTutorialId());

            
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
            
            InputStream sam = new FileInputStream(env.getProperty("spring.applicationexternalPath.name")+tutorial.getVideo());

            InputStreamContent mediaContent = new InputStreamContent(VIDEO_FILE_FORMAT,sam);

            YouTube.Videos.Insert videoInsert = youtube.videos()
                    .insert("snippet,statistics,status", videoObjectDefiningMetadata, mediaContent);

            MediaHttpUploader uploader = videoInsert.getMediaHttpUploader();

            uploader.setDirectUploadEnabled(false);

            MediaHttpUploaderProgressListener progressListener = new MediaHttpUploaderProgressListener() {
                public void progressChanged(MediaHttpUploader uploader) throws IOException {
                    switch (uploader.getUploadState()) {
                        case INITIATION_STARTED:
                            System.out.println("Initiation Started");
                            break;
                        case INITIATION_COMPLETE:
                            System.out.println("Initiation Completed");
                            break;
                        case MEDIA_IN_PROGRESS:
                            System.out.println("Upload in progress");
                            System.out.println("Upload percentage: " + uploader.getProgress());
                            break;
                        case MEDIA_COMPLETE:
                            System.out.println("Upload Completed!");
                            break;
                        case NOT_STARTED:
                            System.out.println("Upload Not Started!");
                            break;
                    }
                }
            };
            uploader.setProgressListener(progressListener);

           
            Video returnedVideo = videoInsert.execute();

            System.out.println("\n================== Returned Video ==================\n");
            System.out.println("  - Id: " + returnedVideo.getId());
            System.out.println("  - Title: " + returnedVideo.getSnippet().getTitle());
            System.out.println("  - Tags: " + returnedVideo.getSnippet().getTags());
            System.out.println("  - Privacy Status: " + returnedVideo.getStatus().getPrivacyStatus());
            System.out.println("  - Video Count: " + returnedVideo.getStatistics().getViewCount());

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
        
        
        return "listTutorialPublishQualityReviwer";
		
		
	}

	/**
	 * redirects to quality review page given tutorial id
	 * @param id int value
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "qualityreview/review/{catName}/{topicName}/{language}", method = RequestMethod.GET)
	public String listQualityReviewTutorialGet(@PathVariable(name = "catName") String cat,
			@PathVariable (name = "topicName") String topic,
			@PathVariable (name = "language") String lan,Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		Category catName = catService.findBycategoryname(cat);
		Topic topicName = topicService.findBytopicName(topic);
		Language lanName = lanService.getByLanName(lan);
		TopicCategoryMapping topicCatMap = topicCatService.findAllByCategoryAndTopic(catName, topicName);
		ContributorAssignedTutorial conTut = conRepo.findByTopicCatAndLanViewPart(topicCatMap, lanName);
		
		if(catName == null || topicName == null || lanName == null || topicCatMap == null || conTut == null) {
			return "redirect:/listTutorialForQualityReview";
		}
		
		Tutorial tutorial=tutService.findAllByContributorAssignedTutorial(conTut).get(0);
		

		if(tutorial == null) {
			return "redirect:/listTutorialForQualityReview";

		}


		List<Comment> comVideo = comService.getCommentBasedOnTutorialType(CommonData.VIDEO,  tutorial);
		List<Comment> comScript = comService.getCommentBasedOnTutorialType(CommonData.SCRIPT,tutorial);
		List<Comment> comSlide = comService.getCommentBasedOnTutorialType(CommonData.SLIDE,  tutorial);

		List<Comment> comKeyword = comService.getCommentBasedOnTutorialType(CommonData.KEYWORD,  tutorial);
		List<Comment> comPreRequistic = comService.getCommentBasedOnTutorialType(CommonData.PRE_REQUISTIC,  tutorial);
		List<Comment> comOutline = comService.getCommentBasedOnTutorialType(CommonData.OUTLINE, tutorial);

		model.addAttribute("comOutline", comOutline);
		model.addAttribute("comScript",comScript );
		System.out.println(comScript+"********************");
		model.addAttribute("comSlide",comSlide );
		model.addAttribute("comVideo", comVideo);
		model.addAttribute("comKeyword", comKeyword);
		model.addAttribute("comPreReq", comPreRequistic);


		model.addAttribute("statusOutline", CommonData.tutorialStatus[tutorial.getOutlineStatus()]);
		model.addAttribute("statusScript", CommonData.tutorialStatus[tutorial.getScriptStatus()]);
		model.addAttribute("statusSlide", CommonData.tutorialStatus[tutorial.getSlideStatus()]);
		model.addAttribute("statusVideo", CommonData.tutorialStatus[tutorial.getVideoStatus()]);
		model.addAttribute("statusKeyword", CommonData.tutorialStatus[tutorial.getKeywordStatus()]);
		model.addAttribute("statusPreReq", CommonData.tutorialStatus[tutorial.getPreRequisticStatus()]);

		model.addAttribute("tutorial", tutorial);
		

		if(tutorial.getVideo() != null) {
		IContainer container = IContainer.make();
		int result=10;
		result = container.open(env.getProperty("spring.applicationexternalPath.name")+tutorial.getVideo(),IContainer.Type.READ,null);
		
		System.out.println("Video Duration"+container.getDuration()/1000000);
		System.out.println("file Size"+container.getFileSize()/1000000);
		

		IStream stream = container.getStream(0);
		IStreamCoder coder = stream.getStreamCoder();
		System.out.println("width :"+coder.getWidth());
		System.out.println("Height :"+coder.getHeight());
		
		model.addAttribute("FileWidth", coder.getWidth());
		model.addAttribute("FileHeight", coder.getHeight());
		
		model.addAttribute("fileSizeInMB", container.getFileSize()/1000000);
		model.addAttribute("FileDurationInSecond", container.getDuration()/1000000);
		
		container.close();

		}


		model.addAttribute("category", tutorial.getConAssignedTutorial().getTopicCatId().getCat().getCatName());
		model.addAttribute("topic", tutorial.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
		model.addAttribute("language", tutorial.getConAssignedTutorial().getLan().getLangName());
		
		if(!tutorial.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase("english")) {
			model.addAttribute("otherLan", false);
		}else {
			model.addAttribute("otherLan", true);
		}

		return "addContentQualityReview";



	}

	/*********************************** END *******************************************************/

	/************************* OPERATION AT MASTER TRAINER ******************************************/

	@RequestMapping(value = "/trainerProfile", method = RequestMethod.GET)
	public String profileMasterTrainerGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		return "traineeView";



	}

	/**
	 * redirects to master trainer operation page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/masterTrainerOperation", method = RequestMethod.GET)
	public String MasterTrainerGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Event> events = eventservice.findByUser(usr);

		List<Category> cat=catService.findAll();

		List<State> states=stateService.findAll();

		List<Language> lan=lanService.getAllLanguages();

		model.addAttribute("categories", cat);

		model.addAttribute("states", states);
		model.addAttribute("lans", lan);

		model.addAttribute("events", events);

		return "masterTrainerOperation";

	}

	/**
	 * redirects page to view master trainer details
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/details", method = RequestMethod.GET)
	public String MasterTrainerDetailsGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<PostQuestionaire> postQuestionnaire = postQuestionService.findByUser(usr);
		List<FeedbackMasterTrainer> feedbackMasterTrainer = feedServ.findByUser(usr);
		List<TrainingInformation> trainingInfo = trainingInfoService.findByUser(usr);
		model.addAttribute("postQuestionnaire", postQuestionnaire);
		model.addAttribute("feedbackMasterTrainer", feedbackMasterTrainer);
		model.addAttribute("trainingInfo", trainingInfo);

		return "masterTrainerViewDetails";

	}

	/**
	 * Download question 
	 * @param model Model object
	 * @param principal Principal object
	 * @param catName int value
	 * @param topicId int value
	 * @param lanName String object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/downloadQuestion", method = RequestMethod.POST)
	public String downloadQuestionPost(Model model,Principal principal,
										@RequestParam(value="catMasterId") int catName,
										@RequestParam(value="lanMasterTrId") int topicId,
										@RequestParam(value="dwnByLanguageId") String lanName) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Category cat=catService.findByid(catName);
		Topic topic=topicService.findById(topicId);
		TopicCategoryMapping topicCat=topicCatService.findAllByCategoryAndTopic(cat, topic);
		Language lan = lanService.getByLanName(lanName);
		Question questions = questService.getQuestionBasedOnTopicCatAndLan(topicCat, lan);
		model.addAttribute("post", "true");
		model.addAttribute("Questions", questions);

		List<Category> cats=catService.findAll();

		List<State> states=stateService.findAll();

		List<Language> lans=lanService.getAllLanguages();

		model.addAttribute("categories", cats);

		model.addAttribute("states", states);
		model.addAttribute("lans", lans);
		model.addAttribute("question", "question");

		return "masterTrainerOperation";

	}

	/**
	 * redirects page to view all the trainee
	 * @param model Model object
	 * @param principal Principal Object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/viewTrainee", method = RequestMethod.GET)
	public String downloadQuestionPost(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<TraineeInformation> trainees = traineeService.findAll();
		List<Category> categories=catService.findAll();

		model.addAttribute("TraineesData", trainees);
		model.addAttribute("categories", categories);

		return "traineeView";

	}

	/**
	 * redirects to edit trainee information given trainee id
	 * @param id int value
	 * @param model model object
	 * @param principal principal object
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/trainee/edit/{id}", method = RequestMethod.GET)
	public String editTraineeGet(@PathVariable int id,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		TraineeInformation trainee=traineeService.findById(id);
		
		if(trainee == null) {

			 return "redirect:/viewTrainee";
		}

		if(trainee.getTraineeInfos().getUser().getId() != usr.getId()) {

			 return "redirect:/viewTrainee";
		}

		model.addAttribute("trainee",trainee);

		return "editTrainee";  // need to accomdate view part
	}

	/**
	 * update trainee information 
	 * @param model Model object
	 * @param principal Principal object
	 * @param req HttpServletRequest object
	 * @return String object (Webpage)
	 */
	@RequestMapping(value = "/updateTrainee", method = RequestMethod.POST)
	public String editTraineeGet(Model model,Principal principal,HttpServletRequest req) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		String Id=req.getParameter("traineeId");
		String name = req.getParameter("name");
		String age = req.getParameter("age");
		String email = req.getParameter("email");
		String gender = req.getParameter("gender");
		String aadhar = req.getParameter("aadhar");
		String org = req.getParameter("org");
		String phone=req.getParameter("contactnumber");

		TraineeInformation trainee=traineeService.findById(Integer.parseInt(Id));

		model.addAttribute("trainee",trainee);

		if(aadhar.length()!=12) {
			 // throw error
			model.addAttribute("error_msg", "Invalid aadhar number");
			return "editTrainee";
		}

		if(phone.length()!=10) {

			// throw error
			model.addAttribute("error_msg", "Invalid phone number");
			return "editTrainee";
		}

		if(!ServiceUtility.checkEmailValidity(email)) {   // need to accommodate

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

		model.addAttribute("trainee",trainee);
		model.addAttribute("success_msg",CommonData.RECORD_UPDATE_SUCCESS_MSG);

		return "editTrainee";  // need to accomdate view part
	}

	/**
	 * Add training information into object
	 * @param model Model object
	 * @param principal Principal object
	 * @param trainingImage MultipartFile object 
	 * @param traineeInfo MultipartFile object
	 * @param eventId int value
	 * @param trainingInformation String object
	 * @param totaltrainee int value
	 * @return String object (webpage)
	 */
	@RequestMapping(value = "/addTrainingInfo", method = RequestMethod.POST)
	public String addTrainingInfoPost(Model model,Principal principal,
			@RequestParam("ParticipantsPhoto") MultipartFile trainingImage,
			@RequestParam("traineeInformation") MultipartFile traineeInfo,
			@RequestParam(value="event") int eventId,
			@RequestParam(value="traningInfo") String trainingInformation,
			@RequestParam(value = "totalPar") int totaltrainee) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Event event = eventservice.findById(eventId);

//		Set<TrainingTopic> trainingTopicTemp = new HashSet<>();

		if(!ServiceUtility.checkFileExtensiononeFileCSV(traineeInfo)) {

			// throw error on output
			model.addAttribute("error_msg",CommonData.CSV_ERROR);
			return "masterTrainerOperation";
		}

		if(!ServiceUtility.checkFileExtensionZip(trainingImage)) {

			// throw error on output
			model.addAttribute("error_msg",CommonData.ZIP_ERROR);
			return "masterTrainerOperation";
		}

//		if(trainingInfoService.findByTopicName(titleName) != null) {
//
//			// throw error on output
//			model.addAttribute("error_msg",CommonData.NAME_ERROR);
//			return "masterTrainerOperation";
//		}

//		Language lan=lanService.getByLanName(lanName);


//		Topic topic=topicService.findById(topicId);
//		TopicCategoryMapping topicCatMap=topicCatService.findAllByCategoryAndTopic(cat, topic);

		int newTrainingdata=trainingInfoService.getNewId();
		TrainingInformation trainingData=new TrainingInformation();
		trainingData.setDateAdded(ServiceUtility.getCurrentTime());
		trainingData.setTrainingId(newTrainingdata);
		trainingData.setTotalParticipant(totaltrainee);
//		trainingData.setLan(lan);
//		trainingData.setAddress(address);
		trainingData.setUser(usr);
		trainingData.setEvent(event);

		try {
			trainingInfoService.save(trainingData);
			int trainingTopicId=trainingTopicServ.getNewId();
//			for(int topicID : topicId) {
//				Topic topicTemp=topicService.findById(topicID);
//				TopicCategoryMapping topicCatMap=topicCatService.findAllByCategoryAndTopic(cat, topicTemp);
//				TrainingTopic trainingTemp=new TrainingTopic(trainingTopicId++, topicCatMap, trainingData);
//				trainingTopicTemp.add(trainingTemp);
//
//			}
//
//			trainingData.setTrainingTopicId(trainingTopicTemp);
			trainingInfoService.save(trainingData);

				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryMasterTrainer+newTrainingdata);
				String pathtoUploadPoster=ServiceUtility.uploadFile(trainingImage, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryMasterTrainer+newTrainingdata);
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				trainingData.setPosterPath(document);

				byte[] bytes = traineeInfo.getBytes();
	            String completeData = new String(bytes);
	            System.out.println(completeData);
	            String[] rows = completeData.split("\n");
	            System.out.println("data"+rows.length);

	            Set<TraineeInformation> trainees=new HashSet<TraineeInformation>();
	            int newTraineeId=traineeService.getNewId();

	            for(int i=0;i<rows.length;i++) {
	            	String[] columns = rows[i].split(",");
	            	TraineeInformation temp=new TraineeInformation(newTraineeId++, columns[0], columns[1], Long.parseLong(columns[2]),Integer.parseInt(columns[3]), Long.parseLong(columns[4]), columns[5], columns[6], trainingData);
	            	trainees.add(temp);
	            }

	            trainingInfoService.addTrainee(trainingData, trainees);





		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 // throw a error
			model.addAttribute("error_msg",CommonData.EVENT_ERROR);
			return "masterTrainerOperation";
		}


		model.addAttribute("success_msg",CommonData.EVENT_SUCCESS);
		return "masterTrainerOperation";

	}

	/**
	 * upload feedback given by master trainer
	 * @param model Model object
	 * @param principal Principal object
	 * @param catId int value
	 * @param trainingTitle int value
	 * @param feedbackFile MultipartFile object
	 * @param desc String object
	 * @return String object (Webpage)
	 */
	@RequestMapping(value = "/uploadfeedback", method = RequestMethod.POST)
	public String uploadFeedbackPost(Model model,Principal principal,
								@RequestParam(value = "catMasId") int catId,
								@RequestParam(value = "feedbackmasterId") int trainingTitle,
								@RequestParam(value = "feedbackForm") MultipartFile feedbackFile,
								@RequestParam(value = "traningInformation") String desc) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Category> cats=catService.findAll();

		List<State> states=stateService.findAll();

		List<Language> lan=lanService.getAllLanguages();

		model.addAttribute("categories", cats);

		model.addAttribute("states", states);
		model.addAttribute("lans", lan);

		if(!ServiceUtility.checkFileExtensionZip(feedbackFile)) {

			model.addAttribute("error_msg",CommonData.ZIP_ERROR);								// Accommodate error message
			return "masterTrainerOperation";
		}

		TrainingInformation trainingInfo = trainingInfoService.getById(trainingTitle);

		FeedbackMasterTrainer feed = new FeedbackMasterTrainer(feedServ.getNewId(), desc, ServiceUtility.getCurrentTime(), null, trainingInfo, usr);
		try {
			feedServ.save(feed);

				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryMasterTrainerFeedback+feed.getId());
				String pathtoUploadPoster=ServiceUtility.uploadFile(feedbackFile, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryMasterTrainerFeedback+feed.getId());
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				feed.setPath(document);
				feedServ.save(feed);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.addAttribute("error_msg",CommonData.EVENT_ERROR);
			e.printStackTrace();
		}


		model.addAttribute("success_msg",CommonData.EVENT_SUCCESS);
		return "masterTrainerOperation";

	}


	/**
	 * upload post questionnaire under master trainer role
	 * @param model Model object
	 * @param principal Principal object
	 * @param catId int value
	 * @param trainingTitle int value
	 * @param postQuestions MultipartFile object
	 * @return String object(Webpage)
	 */
	@RequestMapping(value = "/uploadPostQuestionaire", method = RequestMethod.POST)
	public String uploadQuestionPost(Model model,Principal principal,
								@RequestParam(value = "catMasPostId") int catId,
								@RequestParam(value = "postTraining") int trainingTitle,
								@RequestParam(value = "postQuestions") MultipartFile postQuestions) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Category> cats=catService.findAll();

		List<State> states=stateService.findAll();

		List<Language> lan=lanService.getAllLanguages();

		model.addAttribute("categories", cats);

		model.addAttribute("states", states);
		model.addAttribute("lans", lan);

		if(!ServiceUtility.checkFileExtensionZip(postQuestions)) {

			model.addAttribute("error_msg",CommonData.ZIP_ERROR);								// Accommodate error message
			return "masterTrainerOperation";
		}

		TrainingInformation trainingInfo = trainingInfoService.getById(trainingTitle);

		PostQuestionaire feed = new PostQuestionaire(postQuestionService.getNewCatId(), null, ServiceUtility.getCurrentTime(), trainingInfo, usr);
		try {
			postQuestionService.save(feed);

				ServiceUtility.createFolder(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadPostQuestion+feed.getId());
				String pathtoUploadPoster=ServiceUtility.uploadFile(postQuestions, env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadPostQuestion+feed.getId());
				int indexToStart=pathtoUploadPoster.indexOf("Media");

				String document=pathtoUploadPoster.substring(indexToStart, pathtoUploadPoster.length());

				feed.setQuestionPath(document);
				postQuestionService.save(feed);


		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.addAttribute("error_msg",CommonData.EVENT_ERROR);
			e.printStackTrace();
			return "masterTrainerOperation";
		}


		model.addAttribute("success_msg",CommonData.EVENT_SUCCESS);
		return "masterTrainerOperation";

	}


	/************************ DOMAIN ROLE CONSULTANT MAPPING *************************************/

	/**
	 * redirects to admin role request page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object(Webpage)
	 */
	@RequestMapping(value = "/assignRoleToAdmin" , method = RequestMethod.GET)
	public String assignRoleToDomainGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Category> categories = catService.findAll();
		List<Language> languages = lanService.getAllLanguages();

		model.addAttribute("categories", categories);

		model.addAttribute("languages", languages);

		return "assignRoleToDomain"; // add html page

	}

	/**
	 * Assign role to admin given category name and language name
	 * @param model Model object
	 * @param principal Principal object
	 * @param cat String object
	 * @param lan String object
	 * @return String object(Webpage)
	 */
	@RequestMapping(value = "/assignRoleToAdmin" , method = RequestMethod.POST)
	public String assignRoleToDomainPost(Model model,Principal principal,
									@RequestParam(value = "category") String cat,
									@RequestParam(value = "language") String lan) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Category> categories = catService.findAll();
		List<Language> languages = lanService.getAllLanguages();

		model.addAttribute("categories", categories);
		model.addAttribute("languages", languages);

		Category category = catService.findBycategoryname(cat);
		Language language = lanService.getByLanName(lan);
		
		if(language == null) {
			model.addAttribute("error_msg", "Please select language");

			return "assignRoleToDomain";
		}
		
		if(category == null) {
			model.addAttribute("error_msg", "Please select Category");

			return "assignRoleToDomain";
		}
		
		Role role = roleService.findByname(CommonData.adminReviewerRole);

		if(usrRoleService.findByLanCatUser(language, category, usr, role)!=null) {

			// throw error
			//model.addAttribute("msgSuccefull", CommonData.ADMIN_ADDED_SUCCESS_MSG);

			model.addAttribute("error_msg", CommonData.DUPLICATE_ROLE_ERROR);

			return "assignRoleToDomain";
		}

		UserRole usrRole=new UserRole();
		usrRole.setCreated(ServiceUtility.getCurrentTime());
		usrRole.setUser(usr);
		usrRole.setRole(role);
		usrRole.setLanguage(language);
		usrRole.setCategory(category);
		usrRole.setUserRoleId(usrRoleService.getNewUsrRoletId());

		try {
			usrRoleService.save(usrRole);

//			SimpleMailMessage newEmail = mailConstructor.domainRoleMailSend(usrTemp);
//
//			mailSender.send(newEmail);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			model.addAttribute("error_msg", CommonData.ROLE_ERROR_MSG);
			e.printStackTrace();
			return "assignRoleToDomain";				// accommodate error message
		}


		model.addAttribute("success_msg", CommonData.ADMIN_REVIEWER_REQ);


		return "assignRoleToDomain"; // add html page

	}


	/****************************** END ***********************************************************/

	/**
	 * redirects to profile page
	 * @param model Model object
	 * @param principal Principal object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String profileUserGet(Model model,Principal principal) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		return "profileView";

	}

	/**
	 * Revoke role from user interface
	 * @param usrRoleId long value
	 * @param principal Principal object
	 * @param model Model object
	 * @return String object (Webpage)
	 */
	@GetMapping("/revokeRole/{usrRoleId}")
	public String revokeRoleByRole(@PathVariable long usrRoleId,Principal principal,Model model){

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		UserRole usrRole = usrRoleService.findById(usrRoleId);

		if(usrRole.getUser().getId() == usr.getId()) {
			usrRole.setStatus(false);
			usrRole.setRevoked(true);
			usrRoleService.save(usrRole);

			model.addAttribute("success_msg", "Role revoked Successfully");
		}else {
			model.addAttribute("error_msg", "Wrong operation");
		}

		model.addAttribute("userInfo", usr);

		return "revokeRole";
	}

	/************************ PROFILE UPDATE SECTION *****************************************/
	
	/**
	 * update profile data on user object
	 * @param req HttpServletRequest object
	 * @param model Model object
	 * @param principal Principal object
	 * @param firstName String object
	 * @param lastName String object
	 * @param phone String object
	 * @param address String object
	 * @param dob String object
	 * @param desc String object
	 * @return String object(Webpage)
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String updateuserdataPost(HttpServletRequest req,Model model,Principal principal,
			 						@ModelAttribute("firstName") String firstName,
			 						@ModelAttribute("lastName") String lastName,
			 						@ModelAttribute("phone") String phone,
			 						@ModelAttribute("address") String address,
			 						@ModelAttribute("birthday") String dob,
			 						@ModelAttribute("description") String desc) {
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}
		model.addAttribute("userInfo", usr);

		long phoneLongValue ;
		if(phone.length()>10) {								// need to accommodate

			model.addAttribute("error_msg", "Entered Mobile Number is not of 10 digit");
			return "profileView";

		}else {
			phoneLongValue = Long.parseLong(phone);

		}


		try {
			usr.setFirstName(firstName);
			usr.setLastName(lastName);
			usr.setAddress(address);
			usr.setPhone(phoneLongValue);
			usr.setDob(ServiceUtility.convertStringToDate(dob));

			userService.save(usr);

			if(!desc.isEmpty()) {
				Consultant consult = consultService.findByUser(usr);
				consult.setDescription(desc);
				consultService.save(consult);
			}

			model.addAttribute("success_msg", "Data Updated Successfully");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("error_msg", "Please Try Later");
		}

		usr=userService.findByUsername(principal.getName());
		model.addAttribute("userInfo", usr);
		return "profileView";

	}

	/**
	 * redirects to revoke role page under Contributor role
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@GetMapping("/revokeRoleContributor")
	public String revokeRoleByContributor(Principal principal,Model model){

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role role = roleService.findByname(CommonData.contributorRole);

		List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

		model.addAttribute("userRoles", userRoles);

		return "revokeRole";
	}

	/**
	 * redirects to revoke role page under Admin role
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@GetMapping("/revokeRoleDomain")
	public String revokeRoleByDomain(Principal principal,Model model){

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role role = roleService.findByname(CommonData.domainReviewerRole);

		List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

		model.addAttribute("userRoles", userRoles);

		return "revokeRole";
	}

	/**
	 * redirects to revoke role page under Quality role
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@GetMapping("/revokeRoleQuality")
	public String revokeRoleBYQuality(Principal principal,Model model){

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role role = roleService.findByname(CommonData.qualityReviewerRole);

		List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

		model.addAttribute("userRoles", userRoles);

		return "revokeRole";
	}

	/**
	 * redirects to revoke role page under master role
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@GetMapping("/revokeRoleMaster")
	public String revokeRoleMaster(Principal principal,Model model){

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		Role role = roleService.findByname(CommonData.masterTrainerRole);

		List<UserRole> userRoles = usrRoleService.findAllByRoleUserStatus(role, usr, true);

		model.addAttribute("userRoles", userRoles);

		return "revokeRole";
	}

	/**
	 * redirects to brochure page
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@GetMapping("/brochure")
	public String brochure(Principal principal,Model model){

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		List<Category> cat = catService.findAll();

		Map<Category, List<TopicCategoryMapping>> dataToSend = new HashMap<Category, List<TopicCategoryMapping>>();

		for(Category temp : cat) {
			List<TopicCategoryMapping> tempTopic = topicCatService.findAllByCategory(temp);
			dataToSend.put(temp, tempTopic);

		}
		model.addAttribute("brochuresData", dataToSend);

		return "brochures";  // view name
	}


	/**
	 * redirects page to edit the training data given training id
	 * @param id  int value
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/training/edit/{id}", method = RequestMethod.GET)
	public String editTrainingGet(@PathVariable int id,Model model,Principal principal) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);

		TrainingInformation training=trainingInfoService.getById(id);

		if(training.getUser().getId() != usr.getId()) {

			 return "redirect:/viewTrainee";
		}

		List<State> states=stateService.findAll();

		model.addAttribute("states",states);

		model.addAttribute("training",training);

		return "updateTraining";  // need to accomdate view part
	}

	/**
	 * update training object
	 * @param model model object
	 * @param principal Principal Object
	 * @param state int value
	 * @param district int value
	 * @param city int value
	 * @param totaltrainee int value
	 * @param address int value
	 * @param pinCode int value
	 * @param trainingId int value
	 * @return String object(Webpage)
	 */
	@RequestMapping(value = "/updateTraining", method = RequestMethod.POST)
	public String updateTrainingPost(Model model,Principal principal,
			@RequestParam(value="stateName") int state,
			@RequestParam(value="districtName") int district,
			@RequestParam(value="cityName") int city,
			@RequestParam(value = "totalPar") int totaltrainee,
			@RequestParam(value="addressInformationName") String address,
			@RequestParam(value="pinCode") int pinCode,
			@RequestParam(value="trainingId") int trainingId) {

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		List<State> states=stateService.findAll();

		model.addAttribute("states",states);

		model.addAttribute("userInfo", usr);

		TrainingInformation trainingData=trainingInfoService.getById(trainingId);

		model.addAttribute("training",trainingData);

//		if(trainingInfoService.findByTopicName(titleName) != null) {
//
//			// throw error on output
//			model.addAttribute("error_msg",CommonData.NAME_ERROR);
//			return "updateTraining";
//		}

		trainingData.setTotalParticipant(totaltrainee);

		trainingData.setAddress(address);

		try {
			trainingInfoService.save(trainingData);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			 // throw a error
			model.addAttribute("error_msg",CommonData.EVENT_ERROR);
			return "updateTraining";
		}


		model.addAttribute("success_msg",CommonData.EVENT_SUCCESS);
		model.addAttribute("training",trainingData);
		return "updateTraining";

	}
	
	/**
	 * Redirects page to list all the tutorial under super admin interface
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@GetMapping("/tutorialStatus")
	public String tutorialStatus(Principal principal,Model model){

		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Tutorial> published = new ArrayList<Tutorial>();
		List<Tutorial> tutorials =  tutService.findAll();
		for(Tutorial temp:tutorials) {

			if(temp.getOutlineStatus() == CommonData.PUBLISH_STATUS && temp.getScriptStatus() == CommonData.PUBLISH_STATUS &&
					temp.getSlideStatus() == CommonData.PUBLISH_STATUS && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS &&
					temp.getVideoStatus() == CommonData.PUBLISH_STATUS &&
					temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

				published.add(temp);
			}

		}

		model.addAttribute("tutorial", published);

		return "listTutorialSuperAdmin";
	}
	
	/**
	 * publish or unpublish tutorial from the system under super admin role
	 * @param id tutorial id int value
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/tutorialStatus/{id}", method = RequestMethod.GET)
	public String publishTutorial(@PathVariable int id,Principal principal,Model model) {
		
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		List<Tutorial> published = new ArrayList<Tutorial>();
		List<Tutorial> tutorials ;
		
		
		Tutorial tut = tutService.getById(id);
		if(tut.isStatus()) {
			tut.setStatus(false);
			model.addAttribute("success_msg", "Tutorial unpublished Successfully");
		}else {
			tut.setStatus(true);
			model.addAttribute("success_msg", "Tutorial published Successfully");
		}
		
		try {
			
			tutService.save(tut);
			
		}catch (Exception e) {
			// TODO: handle exception
			
			tutorials =  tutService.findAll();
			for(Tutorial temp:tutorials) {

				if(temp.getOutlineStatus() == CommonData.PUBLISH_STATUS && temp.getScriptStatus() == CommonData.PUBLISH_STATUS &&
						temp.getSlideStatus() == CommonData.PUBLISH_STATUS && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS &&
						temp.getVideoStatus() == CommonData.PUBLISH_STATUS &&
						temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

					published.add(temp);
				}

			}
			model.addAttribute("tutorial", published);
			model.addAttribute("error_msg", "Please Try again.");
			return "listTutorialSuperAdmin";
		}
		
		
		tutorials =  tutService.findAll();
		for(Tutorial temp:tutorials) {

			if(temp.getOutlineStatus() == CommonData.PUBLISH_STATUS && temp.getScriptStatus() == CommonData.PUBLISH_STATUS &&
					temp.getSlideStatus() == CommonData.PUBLISH_STATUS && temp.getKeywordStatus() == CommonData.PUBLISH_STATUS &&
					temp.getVideoStatus() == CommonData.PUBLISH_STATUS &&
					temp.getPreRequisticStatus() == CommonData.PUBLISH_STATUS) {

				published.add(temp);
			}

		}

		model.addAttribute("tutorial", published);

		return "listTutorialSuperAdmin";
		
	}
	
	/**
	 * Redirects to upload time script for the tutorial
	 * @param principal Principal Object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/uploadTimescript", method = RequestMethod.GET)
	public String uploadTimescriptGet(Principal principal,Model model) {
		
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		Set<Tutorial> tutorials = new HashSet<Tutorial>();
		Set<Tutorial> published = new HashSet<Tutorial>();

		List<ContributorAssignedMultiUserTutorial> conTutorials=conMultiUser.getAllByuser(usr);
		
		for(ContributorAssignedMultiUserTutorial conMultiTemp : conTutorials) {
			
			ContributorAssignedTutorial conTemp = conRepo.findById(conMultiTemp.getConAssignedTutorial().getId());
			
			tutorials.addAll(tutService.findAllByContributorAssignedTutorial(conTemp));
		}
		
		for(Tutorial x : tutorials) {
			if(x.isStatus()) {
				published.add(x);
			}
		}

		model.addAttribute("tutorial", published);


		return "uploadTimescript";
	}
	
	/**
	 * Url to show entire user in a system on super admin interface
	 * @param principal Principal object
	 * @param model Model object
	 * @return String object(webpage)
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String usersGet(Principal principal,Model model) {
		
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
	
		List<User> allUser= userService.findAll();
		
		model.addAttribute("users", allUser);

		return "showUsers";
	}
	
	@RequestMapping(value = "/statistics",method = RequestMethod.GET)
	public String statistics(Principal principal, Model model) {
		
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		List<Category> cat = catService.findAll();
		List<Language> lan =lanService.getAllLanguages();
		List<Tutorial> tut = tutService.findAllBystatus(true);
		Collections.sort(cat);
		Collections.sort(lan);
			
		model.addAttribute("categories", cat);
		model.addAttribute("languages", lan);
		model.addAttribute("catTotal", cat.size());
		model.addAttribute("lanTotal", lan.size());
		model.addAttribute("tutTotal", tut.size());
		
		return "statistics";
	}
	
	@RequestMapping(value = "/cdContentInfo",method = RequestMethod.GET)
	public String cdContentInfoGet(Principal principal, Model model) {
		
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		List<Category> cat = catService.findAll();
		List<Language> lan =lanService.getAllLanguages();
		Collections.sort(cat);
		Collections.sort(lan);
		
		model.addAttribute("categories", cat);
		model.addAttribute("languages", lan);
		
		return "cdContent";
	}
	
	@RequestMapping(value = "/cdContentInfo",method = RequestMethod.POST)
	public String cdContentInfoPost(@RequestParam(name = "categoryName") String category,
			@RequestParam(name = "lan") String language,Principal principal, Model model) {
		
		User usr=new User();

		if(principal!=null) {

			usr=userService.findByUsername(principal.getName());
		}

		model.addAttribute("userInfo", usr);
		
		long totalSize = 0;
		int totalNumberOfVideo=0;
		
		List<Category> cat = catService.findAll();
		List<Language> lan =lanService.getAllLanguages();
		Collections.sort(cat);
		Collections.sort(lan);
		
		model.addAttribute("categories", cat);
		model.addAttribute("languages", lan);
		
		Category catTemp = catService.findBycategoryname(category);
		Language lanTemp = lanService.getByLanName(language);
		
		if(catTemp == null || lanTemp == null ) {
			System.out.println("vik");
			return "redirect:/cdContentInfo";
		}
		
		model.addAttribute("categoryName", category);
		model.addAttribute("lanName", language);
		
		List<Tutorial> tutorials = tutService.findAll();
		
		for(Tutorial x : tutorials) {
			if(x.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase(lanTemp.getLangName()) &&
					x.getConAssignedTutorial().getTopicCatId().getCat().getCatName().equalsIgnoreCase(catTemp.getCatName())){
				
				Path path = Paths.get(env.getProperty("spring.applicationexternalPath.name")+x.getVideo());
				
				try {
					totalSize += Files.size(path);
					totalNumberOfVideo+=1;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println(totalSize);
		model.addAttribute("value", totalSize/1024/1024);
		model.addAttribute("totalVideo", totalNumberOfVideo);
		if(totalSize > 0 && totalNumberOfVideo > 0) {
			if(principal == null) {
				model.addAttribute("rate", "500");
			}else {
				model.addAttribute("rate", "Free");
			}
		}
		
		return "cdContent";
	}
	
	
}
