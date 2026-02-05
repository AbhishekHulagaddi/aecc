package com.tierra.auth.model;

public class ChangePasswordModel {
	
	private Long userCode;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
	private String otp;
	private String mail;
	
	
	

	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public Long getUserCode() {
		return userCode;
	}
	public void setUserCode(Long userCode) {
		this.userCode = userCode;
	}

	
	

}
