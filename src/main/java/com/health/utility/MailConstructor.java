package com.health.utility;

import java.util.Locale;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.health.domain.security.Role;
import com.health.model.Category;
import com.health.model.Language;
import com.health.model.User;
/**
 * Mail constructor Class file
 * @author om prakash soni
 * @version 1.0
 *
 */
@Component
public class MailConstructor {
	
	/**
	 * This method used to achieve forget password feature in application
	 * @param contextPath context path of application
	 * @param locale locale object
	 * @param token token generated for user 
	 * @param user user object
	 * @return SimpleMailMessage object
	 */
	public SimpleMailMessage constructResetTokenEmail(
			String contextPath, Locale locale, String token, User user)
	
	{
		
		String url = contextPath + "/reset?token="+token;
		String message = "\nPlease click on this link to Update your password";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setSubject("Reset Password");
		email.setText(url+message);
		return email;
		
	}
	
	/**
	 * This method used to send email to consultant
	 * @param usr user object
	 * @return SimpleMailMessage object
	 */
	public SimpleMailMessage domainRoleMailSend(User usr) {
		String message="Hello Your account has been created please use below details to access\n"
				+ "username : "+ usr.getEmail() +"\n"
				+ "password : "+ CommonData.COMMON_PASSWORD;
		SimpleMailMessage email=new SimpleMailMessage();
		email.setSubject("Credentials to login into Health Website");
		email.setText(message);
		email.setTo(usr.getEmail());
	
		return email;
	}
	
	/**
	 * 
	 * This method to send email to user when role is being accepted from super admin
	 * @param usr user object
	 * @param role role object
	 * @param cat category object
	 * @param lan language object
	 * @return SimpleMailMessage object
	 */
	public SimpleMailMessage approveRole(User usr, Role role, Category cat, Language lan) {
		
		String message;
		
		if(role.getRoleId() == 8) {
			 message="Your request for "+ role.getName() + "role under the language " + lan.getLangName() +" is been approved.";
		}else if(role.getRoleId() == 7){
			 message="Your request for "+ role.getName() + " is been approved.";
		}else {
			message="Your request for "+ role.getName() + "role under the category "+cat.getCatName() + " and language " + lan.getLangName() +" is been approved.";
		}
		
		SimpleMailMessage email=new SimpleMailMessage();
		email.setSubject("Update on Role Request");
		
		email.setText(message);
		email.setTo(usr.getEmail());
		return email;
	}
	
	/**
	 * This method to send email to user when role is being rejected from super admin
	 * @param usr user object
	 * @param role role object
	 * @param cat category object
	 * @param lan language object
	 * @return SimpleMailMessage object
	 */
	public SimpleMailMessage rejectRole(User usr, Role role, Category cat, Language lan) {
		
		String message;
		
		if(role.getRoleId() == 8) {
			 message="Your request for "+ role.getName() + "role under the language " + lan.getLangName() +" is been approved.";
		}else if(role.getRoleId() == 7){
			 message="Your request for "+ role.getName() + " is been approved.";
		}else {
			message="Your request for "+ role.getName() + "role under the category "+cat.getCatName() + " and language " + lan.getLangName() +" is been approved.";
		}
		
		SimpleMailMessage email=new SimpleMailMessage();
		email.setSubject("Update on Role Request");
		email.setText(message);
		email.setTo(usr.getEmail());
		return email;
	}
}
