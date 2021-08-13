package com.health;

import java.io.File;
import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.env.Environment;

import com.health.domain.security.Role;
import com.health.domain.security.UserRole;
import com.health.model.OrganizationRole;
import com.health.model.User;
import com.health.service.OrganizationRoleService;
import com.health.service.RoleService;
import com.health.service.UserRoleService;
import com.health.service.UserService;
import com.health.utility.CommonData;
import com.health.utility.SecurityUtility;
import com.health.utility.ServiceUtility;


@SpringBootApplication
public class HealthNutrition extends org.springframework.boot.web.servlet.support.SpringBootServletInitializer implements CommandLineRunner {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserRoleService usrRoleService;

	@Autowired
	private Environment env;

	@Autowired
	private OrganizationRoleService orgRoleService;

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// TODO Auto-generated method stub
		return builder.sources(HealthNutrition.class);

	}

	public static void main(String[] args)  {

		SpringApplication.run(HealthNutrition.class, args);


	}

	@Override
	public void run(String... args) throws Exception {

		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryCategory).mkdirs();
		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryQuestion).mkdirs();
		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTutorial).mkdirs();
		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadUserImage).mkdirs();
		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryTestimonial).mkdirs();
		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryMasterTrainer).mkdirs();
		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryEvent).mkdirs();
		new File(env.getProperty("spring.applicationexternalPath.name")+CommonData.uploadDirectoryMasterTrainerFeedback).mkdirs();


//		Role role=new Role();
//		role.setRoleId(1);
//		role.setName("SUPER_USER");
//		roleService.save(role);
//
//		Role role1=new Role();
//		role1.setRoleId(2);
//		role1.setName("CREATION_ADMIN");
//		roleService.save(role1);
//
//		Role role2=new Role();
//		role2.setRoleId(3);
//		role2.setName("TRAINING_ADMIN");
//		roleService.save(role2);
//
//		Role role3=new Role();
//		role3.setRoleId(4);
//		role3.setName("DOMAIN_REVIEWER");
//		roleService.save(role3);
//
//		Role role4=new Role();
//		role4.setRoleId(5);
//		role4.setName("QUALITY_REVIEWER");
//		roleService.save(role4);
//
//		Role role5=new Role();
//		role5.setRoleId(6);
//		role5.setName("ADMIN_REVIEWER");
//		roleService.save(role5);
//
//		Role role6=new Role();
//		role6.setRoleId(7);
//		role6.setName("MASTER_TRAINER");
//		roleService.save(role6);
//
//		Role role7=new Role();
//		role7.setRoleId(8);
//		role7.setName("CONTRIBUTOR");
//		roleService.save(role7);
//		
//		Role role8=new Role();
//		role8.setRoleId(9);
//		role8.setName("EXTERNAL_CONTRIBUTOR");
//		roleService.save(role8);
//
//		Timestamp ts = new Timestamp(System.currentTimeMillis());
//		OrganizationRole orgRole = new OrganizationRole();
//		orgRole.setRoleId(1);
//		orgRole.setDateAdded(ts);
//		orgRole.setRole("Other");
//		orgRoleService.save(orgRole);
//
//		User user=new User();
//		user.setId((long) 1);
//		user.setUsername("spoken");
//		user.setEmail("spoken@spoken.org");
//		user.setPassword(SecurityUtility.passwordEncoder().encode("spoken"));
//		user.setFirstName("spoken");
//		user.setLastName("spoken");
//		user.setAddress("iit bombay");
//		user.setPhone(1234567890);
//		user.setRegistered(true);
//		user.setDateAdded(ServiceUtility.getCurrentTime());
//
//		userService.save(user);
//
//		Role temp=roleService.findByname("SUPER_USER");
//
//		User tempUser = userService.findByUsername("spoken");
//
//		UserRole userRole=new UserRole();
//		userRole.setUserRoleId((long) 1);
//		userRole.setCategory(null);
//		userRole.setCreated(ServiceUtility.getCurrentTime());
//		userRole.setRole(temp);
//		userRole.setUser(tempUser);
//		userRole.setStatus(true);
//
//		usrRoleService.save(userRole);



	}
}