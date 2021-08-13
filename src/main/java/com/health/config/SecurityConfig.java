
package com.health.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.health.service.impl.UserSecurityService;
import com.health.utility.SecurityUtility;
/**
 * Spring security configuration file
 * @author om prakash soni
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private Environment env;

	@Autowired
	private UserSecurityService userSecurityService;

	public static BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}

	/**
	 * public matcher doesn't require authentication
	 */
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/",
			"/files/**",
			"/newUser",
			"/Media/**",
			"/forgetPassword",
			"/feedcustomerdat",
			"/feedbackForUser",
			"/login",
			"/tutorials",
			"/s/**",
			"/loadByCategoryTuturial",
			"/addContactForm",
			"/ControllerHealth",
			"/showTutorial",
			"/findTutorialByLanand",
			"/showListTutorial",
			"/showVideoWithContained",
			"/showListOfTutorial",
			"/showListTutorial",
			"/viewVideo/view/{id}",
			"viewVideoList/view/{id}",
			"/showListConsultants",
			"/categories",
			"/showEvent",
			"/eventDetails",
			"/listTopicsByCategory",
			"/listLangByCategoryTopic",
			"/testimonialList",
			"/brochure",
			"/fonts/**",
			"/eventDetails/**",
			"/tutorialView/**",
			"/getConsultantDetails/**",
			"/reset/**",
			"/resetPassword/**",
			"/getCatAndLan/**",
			"/getTopicOnCatAndLan/**",
			"/getRolesOnCatLanUser/**",
			"/getTutorial/**",
			"/statistics/**",
			"/tutCountOnCat/**",
			"/tutCountOnLan/**",
			"/cdContentInfo/**",
			"/getContributorByTutLanUser/**"
	};

	/**
	 * url matcher for SUPERADMIN
	 */
	public static final String[] SUPERUSER_URL= {
			"/addCategory/**",
			"/updateCategory/**",
			"/category/edit/**",
			"/addOrganizationRole/**",
			"/organization_role/edit/**",
			"/update_organization_role/**",
			"/addLanguage/**",
			"/language/edit/**",
			"/updateLanguage/**",
			"/addRole/**",
			"/addTopic/**",
			"/topic/edit/**",
			"/updateTopic/**",
			"/uploadQuestion/**",
			"/question/edit/**",
			"/updateQuestion/**",
			"/addBrochure/**",
			"/addCarousel/**",
			"/approveRole/**",
			"/assignTutorialToContributor/**",
			"/assignContributor/edit/**",
			"/enableDisableBrouchure/**",
			"/enableDisableConsultant/**",
			"/enableDisableTestimonial/**",
			"/enableRoleById/**",
			"/deleteMasterRole/**",
			"/viewTrainee/**",
			"/details/**",
			"/tutorialStatus/**",
			"/users/**"

	};

	/**
	 * url matcher for MASTERTRAINER
	 */
	public static final String[] MASTERTRAINER_URL= {

			"/masterTrainerOperation/**",
			"/details/**",
			"/addTrainingInfo/**",
			"/uploadfeedback/**",
			"/uploadPostQuestionaire/**",
			"/training/edit/**",
			"/updateTraining/**",
			"/trainee/edit/**",
			"/updateTrainee/**",


	};

	/**
	 * url matcher for CONTRIBUTOR
	 */
	public static final String[] CONTRIBUTOR_URL= {
			"/uploadTutorial/**",
			"/addOutline/**",
			"/addKeyword**",
			"/addPreRequisticWhenNotRequired/**",
			"/addPreRequistic/**",
			"/addVideo/**",
			"/addSlide/**",
			"/addScript/**",
			"/listTutorialForContributorReview/**",
			"/Contributor/review/**",
			"/uploadTimescript/**",



	};
	
	/**
	 * url matcher for Admin Role
	 */
	public static final String[] ADMIN_URL= {
			"/listTutorialForAdminReview/**",
			"/adminreview/review/**",
			"/acceptAdminVideo/**",


	};

	/**
	 * url matcher for domain role
	 */
	public static final String[] DOMAIN_URL= {
			"/listTutorialForDomainReview/**",
			"/domainreview/review/**",
			"/acceptDomainOutline/**",
			"/acceptDomainScript/**",
			"/acceptDomainVideo/**",
			"/acceptDomainSlide/**",
			"/acceptDomainKeywords/**",
			"/acceptDomainPreRequistic/**",

	};

	/**
	 * url matcher for quality role
	 */
	public static final String[] QUALITY_URL= {
			"/listTutorialForQualityReview/**",
			"/publish/**",
			"/tutorialToPublish/**",
			"/qualityreview/review/**",
			"/acceptQualityOutline/**",
			"/acceptQualityScript/**",
			"/acceptQualityVideo/**",
			"/acceptQualitySlide/**",
			"/acceptQualityKeywords/**",
			"/acceptQualityPreRequistic/**",

	};

	/**
	 * configuration method
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http

		.headers()
			.frameOptions().sameOrigin()
			.httpStrictTransportSecurity().disable();
		http
		// ...
		.headers()
			// do not use any default headers unless explicitly listed
			.defaultsDisabled()
			.cacheControl();

		   http
		      // ...
		   .headers().disable();




		http
			.authorizeRequests()
			.antMatchers("/").hasAnyAuthority()
			.antMatchers(SUPERUSER_URL).hasAnyAuthority("SUPER_USER")
			.antMatchers(MASTERTRAINER_URL).hasAnyAuthority("MASTER_TRAINER")
			.antMatchers(CONTRIBUTOR_URL).hasAnyAuthority("CONTRIBUTOR","EXTERNAL_CONTRIBUTOR")
			.antMatchers(ADMIN_URL).hasAnyAuthority("ADMIN_REVIEWER")
			.antMatchers(DOMAIN_URL).hasAnyAuthority("DOMAIN_REVIEWER")
			.antMatchers(QUALITY_URL).hasAnyAuthority("QUALITY_REVIEWER")
			.antMatchers("/addEvent/**","/addTestimonial/**","/event/edit/**","/updateEvent/**","/testimonial/edit/**","/updateTestimonial/**","/viewTrainee/**","/downloadQuestion/**").hasAnyAuthority("SUPER_USER","MASTER_TRAINER")
			.antMatchers(PUBLIC_MATCHERS).
				permitAll().anyRequest().authenticated().
				and()
				.exceptionHandling().accessDeniedPage("/access-denied");

			http.headers().frameOptions().disable();


		  http.
		  formLogin().
		  defaultSuccessUrl("/", true);


		http
			.csrf().disable().cors().disable()
			.formLogin().failureUrl("/login?error")
			//("userDetail.html")
			/*.defaultSuccessUrl("/")*/
			.loginPage("/login").permitAll()
			.and()
			.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
			.logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
			.and()
			.rememberMe();
	}




	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());

	}

}
