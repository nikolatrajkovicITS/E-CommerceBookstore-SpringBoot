package com.adminportal.config;

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

import com.adminportal.service.impl.UserSecurityService;
import com.adminportal.utility.SecurityUtility;

/**
 * 
 * @author nikola.trajkovic
 * Security configuration class
 */
@Configuration                                                     // This tells the system this is configuration class
@EnableWebSecurity                                                 // Enables web Security provide from Spring 
@EnableGlobalMethodSecurity(prePostEnabled=true)                   // We can have more fine grand user rows 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Autowired
	private UserSecurityService userSecurityService;
	
	private BCryptPasswordEncoder passwordEncoder() {
		return SecurityUtility.passwordEncoder();
	}
	
	/**
	 * This resources will have access 
	 * without Security
	 * */
	private static final String[] PUBLIC_MATCHERS = {
			"/css/**",
			"/js/**",
			"/image/**",
			"/newUser",
			"/forgetPassword",
			"/login",
			"/fonts/**"
	};
	
	/**
	 * 
	 * @void Any HTTP Request if is matched
	 * with PUBLIC_MATCHERS will be allowed,
	 * otherwise user will do the authentication.
	 * @throws Exception
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
		    .authorizeRequests().
		 /*  antMatchers("/**)                                              - this means allows everything */
		    antMatchers(PUBLIC_MATCHERS).
		    permitAll().anyRequest().authenticated();
		
		http
		    .csrf().disable().cors().disable()                              // If we use REST we should disable csrf and cors
		    .formLogin().failureUrl("/login?error").defaultSuccessUrl("/")  // On front: th:if="${param.error != null}": ako je "/login?error" ako je param ?error" kao ovde sto je navedno onda on izbacuje validaciju za wrong username i pw
		    .defaultSuccessUrl("/")
		    .loginPage("/login").permitAll()                                // Permit access on the Login page
		    .and()
		    .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		    .logoutSuccessUrl("/?logout").deleteCookies("remmember-me").permitAll()   // "/?logout" is root + logout path
	        .and()
	        .rememberMe();
	}
	
	/**
	 * 
	 * @void Define Authentication 
	 * @throws Exception
	 * */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userSecurityService).passwordEncoder(passwordEncoder());
	}
}




