package com.bookstore.service.impl;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.domain.User;
import com.bookstore.domain.security.PasswordResetToken;
import com.bookstore.domain.security.UserRole;
import com.bookstore.repository.PasswordResetTokenRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;

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

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
	
	@Override                                                              
	public PasswordResetToken getPasswordResetToken(final String token) {
		return passwordResetTokenRepository.findByToken(token);
	}

	@Override
	public void createPasswordResetTokenForUser(final User user, final String token) {
		final PasswordResetToken myToken = new PasswordResetToken(token, user);
		passwordResetTokenRepository.save(myToken);
	}

	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	/**
	 * Create user method
	 * 1. Take username which client insert
	 * 2. Check is already exists if not
	 * 3. Save user roles into roleRepository
	 * 4. Add all user roles to the user
	 * 5. Assign and save(in db) to the localUser
	 * @return localUser
	 */
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
        
        user.getUserRoles().addAll(userRoles);     // Add all user roles
        
        localUser = userRepository.save(user);
        
        return localUser;
	}	

}
