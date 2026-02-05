package com.rim.masterdata.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vendor")
public class Vendor {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID vendorId;
	private String vendorName;
	private String email;
	private String address;
	private long Mobilenumber;
	private String GST_Number;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private UUID createdBy;
	private UUID modifiedBy;
	private boolean status;
	public UUID getVendorId() {
		return vendorId;
	}
	public void setVendorId(UUID vendorId) {
		this.vendorId = vendorId;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public UUID getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}
	public UUID getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UUID modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public long getMobilenumber() {
		return Mobilenumber;
	}
	public void setMobilenumber(long mobilenumber) {
		Mobilenumber = mobilenumber;
	}

	public String getGST_Number() {
		return GST_Number;
	}
	public void setGST_Number(String gST_Number) {
		GST_Number = gST_Number;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
}
