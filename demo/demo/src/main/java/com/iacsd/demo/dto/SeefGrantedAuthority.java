package com.iacsd.demo.dto;

import org.springframework.security.core.GrantedAuthority;

public class SeefGrantedAuthority implements GrantedAuthority{

	private static final long serialVersionUID = 1L;
	
	private String authority;
	
	public SeefGrantedAuthority(String authority) {
		this.authority=authority;
	}
	
	@Override
	public String getAuthority() {
		return this.authority;
	}

	public static SeefGrantedAuthority from(String permission) {
		return new SeefGrantedAuthority(permission);
	}
	
	

}
