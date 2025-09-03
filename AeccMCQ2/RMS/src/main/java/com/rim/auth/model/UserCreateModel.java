package com.rim.auth.model;

import java.util.List;
import java.util.UUID;

public class UserCreateModel {
	
	private String userName;
	private String firstName;
	private String lastName;
	private String address;
	private String panNumber;
	private String mail;
//	private Set<RoleModel> roles = new HashSet<>();
	private List<UUID> roleIds;
	private long mobileNumber;
	private String password;
	private String confirmpassword;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public long getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getConfirmpassword() {
		return confirmpassword;
	}
	public void setConfirmpassword(String confirmpassword) {
		this.confirmpassword = confirmpassword;
	}
//    public Set<RoleModel> getRoles() {
//		return roles;
//	}
//	public void setRoles(Set<RoleModel> roles) {
//		this.roles = roles;
//	}
//	public UserCreateModel(String userName, String mail, String password) {
//        this.userName = userName;
//        this.mail = mail;
//        this.password = password;
//    }
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPanNumber() {
		return panNumber;
	}
	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}
	public List<UUID> getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(List<UUID> roleIds) {
		this.roleIds = roleIds;
	}
		

	
}
