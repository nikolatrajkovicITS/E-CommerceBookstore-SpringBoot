package com.adminportal.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adminportal.domain.User;
import com.adminportal.domain.security.PasswordResetToken;
import com.adminportal.domain.security.UserRole;
import com.adminportal.repository.RoleRepository;
import com.adminportal.repository.UserRepository;
import com.adminportal.service.UserService;


/**
 * 
 * @author nikola.trajkovic
 * Service bean class
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public User createUser(User user, Set<UserRole> userRoles) throws Exception{
		User localUser = userRepository.findByUsername(user.getUsername());

        if(localUser != null) {
        	LOG.info("user {} already exits. Nothing will be done.", user.getUsername());
        } else {
        	for (UserRole ur : userRoles) {
        		roleRepository.save(ur.getRole());
        	}
        }
        
        user.getUserRoles().addAll(userRoles);      
        
        localUser = userRepository.save(user);
        
        return localUser;
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}	

}
