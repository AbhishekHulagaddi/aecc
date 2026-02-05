package com.tierra.auth.model;

import java.util.List;
import java.util.UUID;

public class LoginResponseModel {
	
	private UUID userId;
	private long usercode;
	private String userName;
	private String mail;
	private List<String> roles;
	private String token;
	private String tokenExpiry;
	public long getUsercode() {
		return usercode;
	}
	public void setUsercode(long usercode) {
		this.usercode = usercode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTokenExpiry() {
		return tokenExpiry;
	}
	public void setTokenExpiry(String tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	public UUID getUserId() {
		return userId;
	}
	public void setUserId(UUID userId) {
		this.userId = userId;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}


	
	
	
	

	
}
