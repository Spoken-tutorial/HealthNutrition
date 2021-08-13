package com.health.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.services.youtube.YouTube.Thumbnails.Set;
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
import com.health.utility.CommonData;

/**
 * Rest APi class return data in JSON format
 * @author om prakash
 * @version 1.0
 *
 */
@RestController
public class RestApi {
	
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
	
	/**
	 * This url fetches all the category and language available in the system
	 * @return ResponseEntity object
	 */
	@GetMapping("/getCatAndLan")
	public ResponseEntity<Object> getcat(){
		
		List<Category> cats = catService.findAll();
		List<Language> lans = lanService.getAllLanguages();
		
		Map<String, Map<String, List<Map<String, String>>>> mapdataReturn = new HashMap<String,Map<String, List<Map<String, String>>>>();
		
		Map<String, List<Map<String, String>>> mapdata = new HashMap<String,List<Map<String, String>>>();
		
		List<Map<String, String>> allCategories = new ArrayList<>();
		List<Map<String, String>> allLanguage = new ArrayList<>();
		
		for(Category x : cats) {
			
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("id", Integer.toString(x.getCategoryId()));
			temp.put("foss", x.getCatName());
			temp.put("description", x.getDescription());
			
			allCategories.add(temp);
			
		}
		
		for(Language x :lans) {
			
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("id",Integer.toString(x.getLanId()));
			temp.put("name", x.getLangName());
			
			allLanguage.add(temp);
			
		}
		
		mapdata.put("foss", allCategories);
		mapdata.put("language", allLanguage);
		
		mapdataReturn.put("healthnutrition", mapdata);
		
		return new ResponseEntity<Object>(mapdataReturn,HttpStatus.OK);
 		
	}
	
	/**
	 * This url fetches out all the tutorial based on given category id and language id
	 * @param catId int value
	 * @param lanId int value
	 * @return ResponseEntity object
	 */
	@GetMapping("/getTopicOnCatAndLan/{catId}/{lanId}")
	public ResponseEntity<Object> getTopic(@PathVariable (name = "catId") int catId,
			@PathVariable (name = "lanId") int lanId){
		
		Category cat = catService.findByid(catId);
		Language lan = lanService.getById(lanId);
		
		if(cat == null || lan==null) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
		Map<String, Map<String, List<Map<String, String>>>> mapdataReturn = new HashMap<String,Map<String, List<Map<String, String>>>>();
		
		Map<String, List<Map<String, String>>> mapdata = new HashMap<String,List<Map<String, String>>>();
		
		List<Map<String, String>> alltutorial = new ArrayList<>();
		
		List<TopicCategoryMapping> localTopicCatList = null;
		List<ContributorAssignedTutorial> conAssigTutorialList =null;
		List<Tutorial> tut = null;
		
		System.out.println(cat.getCatName());
		System.out.println(lan.getLangName());
		
		localTopicCatList = topicCatService.findAllByCategory(cat);
		
		conAssigTutorialList = conRepo.findAllByTopicCatAndLanViewPart(localTopicCatList, lan);
		
		tut =tutService.findAllByContributorAssignedTutorialList(conAssigTutorialList);
		
		
		for(Tutorial x :tut) {
			
			Map<String, String> temp = new HashMap<String, String>();
			temp.put("id", Integer.toString(x.getTutorialId()));
			temp.put("outline", x.getOutline());
			temp.put("tutorial", x.getConAssignedTutorial().getTopicCatId().getTopic().getTopicName());
			
			alltutorial.add(temp);
		}
		
		mapdata.put("tutorials", alltutorial);
		
		mapdataReturn.put("healthnutrition", mapdata);
		
		
		return new ResponseEntity<Object>(mapdataReturn,HttpStatus.OK);
		
	}
	
	/**
	 * This url fetches out all the roles user entitled to based on given category and language id
	 * @param catId int value
	 * @param lanId int value
	 * @param username String object
	 * @return ResponseEntity object
	 */
	@GetMapping("/getRolesOnCatLanUser/{catId}/{lanId}/{username}")
	public ResponseEntity<Object> getRoles(@PathVariable (name = "catId") int catId,
			@PathVariable (name = "lanId") int lanId,@PathVariable (name = "username") String username){
		
		Category cat = catService.findByid(catId);
		Language lan = lanService.getById(lanId);
		User usr = userService.findByUsername(username);
		
		if(cat == null || lan==null || usr == null) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
		Map<String, Map<String, java.util.Set<String>>> mapdataReturn = new HashMap<String,Map<String, java.util.Set< String>>>();
		
		Map<String, java.util.Set<String>> mapdata = new HashMap<String,java.util.Set< String>>();
		
		java.util.Set<String> roles = new HashSet<String>();
		
		List<UserRole> usrRole =  usrRoleService.findAllByUser(usr);
		
		for(UserRole x : usrRole) {
		
			if(x.getStatus()) {
				if(x.getRole().getName().equalsIgnoreCase(CommonData.contributorRole) && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName())) {
					roles.add("Contributor");
				}else if(x.getRole().getName().equalsIgnoreCase(CommonData.domainReviewerRole) && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName()) && x.getCat().getCatName().equalsIgnoreCase(cat.getCatName())) {
					roles.add("Domain-Reviewer");
					
				}else if(x.getRole().getName().equalsIgnoreCase(CommonData.qualityReviewerRole) && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName()) && x.getCat().getCatName().equalsIgnoreCase(cat.getCatName())) {
					roles.add("Quality-Reviewer");
					
				}else if(x.getRole().getName().equalsIgnoreCase(CommonData.adminReviewerRole) && x.getLan().getLangName().equalsIgnoreCase(lan.getLangName()) && x.getCat().getCatName().equalsIgnoreCase(cat.getCatName())) {
					roles.add(x.getRole().getName());
				}
			}
		
				
		}
		
		mapdata.put("roles", roles);
		
		mapdataReturn.put("healthnutrition", mapdata);
		
		
		return new ResponseEntity<Object>(mapdataReturn,HttpStatus.OK);
		
	}
	
	/**
	 * This url fetch out the tutorial data given tutorial id
	 * @param tutorialId int value
	 * @return  ResponseEntity object
	 */
	@GetMapping("/getTutorial/{tutorialId}")
	public ResponseEntity<Object> getTutorial(@PathVariable (name = "tutorialId") int tutorialId){
		
		Tutorial tut = tutService.getById(tutorialId);
		
		if(tut == null) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
		Map<String, Map<String, List<Map<String, String>>>> mapdataReturn = new HashMap<String,Map<String, List<Map<String, String>>>>();
		
		Map<String, List<Map<String, String>>> mapdata = new HashMap<String,List<Map<String, String>>>();
		
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
		
		
		return new ResponseEntity<Object>(mapdataReturn,HttpStatus.OK);
		
	}
	
	/**
	 * This url tells whether a user has contributor role or not on tutorial
	 * @param tutId int value
	 * @param lanId int value
	 * @param username String object
	 * @return ResponseEntity object
	 */
	@GetMapping("/getContributorByTutLanUser/{tutorialId}/{lanId}/{username}")
	public ResponseEntity<Object> getContributorBoolean(@PathVariable (name = "tutorialId") int tutId,
			@PathVariable (name = "lanId") int lanId,@PathVariable (name = "username") String username){
		
		Tutorial tut = tutService.getById(tutId);
		Language lan = lanService.getById(lanId);
		User usr = userService.findByUsername(username);
		
		if(tut == null || lan==null || usr == null) {
			return new ResponseEntity<Object>(HttpStatus.BAD_REQUEST);
		}
		
		
		Map<String, Boolean> mapdata = new HashMap<String,Boolean>();
		
		boolean role = false; 
		
		if(tut.getConAssignedTutorial().getLan().getLangName().equalsIgnoreCase(lan.getLangName())){
			
			for(ContributorAssignedMultiUserTutorial x :tut.getConAssignedTutorial().getMultiUserAssigned()) {
				if(x.getUser().getUsername().equalsIgnoreCase(usr.getUsername())) {
					role = true;
				}
			}
		}
		
		mapdata.put("healthnutrition", role);
			
		return new ResponseEntity<Object>(mapdata,HttpStatus.OK);
		
	}
	
	

}
