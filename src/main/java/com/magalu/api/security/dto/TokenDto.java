package com.magalu.api.security.dto;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

public class TokenDto {

	private String token; 
	private Collection<? extends GrantedAuthority> authorities;
	
	public TokenDto() {
	}
	
	public TokenDto(String token) {
		this.token = token;	    
	}
	
	public TokenDto(String token, Collection<? extends GrantedAuthority> authorities) {
		this.token = token;
	    this.authorities = authorities;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	
	
	

}
