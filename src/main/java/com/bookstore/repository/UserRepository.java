package com.bookstore.repository;

import org.springframework.data.repository.CrudRepository;

import com.bookstore.domain.User;

/**
 * 
 * @author nikola.trajkovic
 * This: CrudRepository contain
 * Methods: find, delete, findAll,
 * deleteAll, save...
 * 
 */
public interface UserRepository extends CrudRepository<User, Long> {

	/**
	 * 
	 * @param username
	 * @return Spring boot is smart enough to pass to this method and accept username as parameter, and return <b>user</b> based on <b>username</b> 
	 */
	User findByUsername(String username);  
	
	User findByEmail(String email);  
}
