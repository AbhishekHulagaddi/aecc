package com.tierra.auth.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "otp_user")
public class OtpUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "binary(16)")
	private UUID id;
	
	private String mail;
	private String otp;
	private LocalDateTime requestAt;
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
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
	public LocalDateTime getRequestAt() {
		return requestAt;
	}
	public void setRequestAt(LocalDateTime requestAt) {
		this.requestAt = requestAt;
	}

	

}
