package com.bookstore.controller;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.service.UserService;
import com.bookstore.service.impl.UserSecurityService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	UserSecurityService userSecurityService;
	
	@RequestMapping("/")
	public String index() {
		return "index";
	}
	
	/**
	 * @param model
	 * @return from login to myAccount
	 */
	@RequestMapping("/login")
	public String login(Model model) {
		model.addAttribute("classActiveLogin", true);  // classActiveLogin = true
		return "myAccount";
	}
	
	@RequestMapping(value="/newUser", method=RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model
			) throws Exception {
		model.addAttribute("classActiveNewAccount", true);
		model.addAttribute("email", userEmail);
		model.addAttribute("username", username);
			
		
		return null;
	}
	
	/**
	 * After we sent token in email to the user,
	 * he will click on the link, link then come 
	 * back to map with this path "/newUser",
	 * And we retrieve token from the link.
	 * 
	 * 1.Retrieve token and check is he valid.
	 * 2.Retrieve user by token
	 * 3.Set current login-session to the retrieved user
	 * 4.Retrieve true(value) to the classActiveEdit
	 * @return myProfile
	 */
	@RequestMapping("/newUser")
	public String createNewAccount(Locale locale, @RequestParam("token") String token,
			Model model) {
		PasswordResetToken passToken = userService.getPasswordResetToken(token);
		
		if (passToken == null) {
			String message = "Invalid Token.";
			model.addAttribute("message", message);
			return "redirect:/badRequest";
		}
		
		User user = passToken.getUser();       // Retrieve user by Token
		String username = user.getUsername();  
		
		UserDetails userDetails = userSecurityService.loadUserByUsername(username);  // get userDetails from userSecurityService
		
		// Define authentication environment using userDetails, userPassword and Authorities 
		Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());                                            
		
		SecurityContextHolder.getContext().setAuthentication(authentication);   // Retrieve the current SecurityContext and set authentication to the current user
		
		model.addAttribute("classActiveEdit", true);  
		return "myProfile";
	}
	
	
	/**
	 * @RequestParam token is for:"extract token".
	 * @param locale
	 * @return myAccount.html
	 */
	@RequestMapping("/forgetPassword")          
	public String forgetPassword(Model model) {	
		model.addAttribute("classActiveForgetPassword", true);  
		return "myAccount";
	}
}