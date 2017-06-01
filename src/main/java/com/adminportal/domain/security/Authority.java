package com.adminportal.domain.security;

import org.springframework.security.core.GrantedAuthority;

/**
 *@author nikola.trajkovic
 *Represents an authority granted to an Authentication object. 
 *
 * */
public class Authority implements GrantedAuthority{
	private final String authority;
	
	public Authority(String authority) {
		this.authority = authority;
	}
	
	@Override
	public String getAuthority() {
		return authority;
	}
}
