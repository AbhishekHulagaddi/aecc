package com.rim.masterdata.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "StorageLocation")
public class StorageLocation {
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID storageLocationId;
	private String storageLocationName;
	private short length;
	private short width;
	private short height;
	private short quantity;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private UUID createdBy;
	private UUID modifiedBy;
	private boolean status;
	public UUID getStorageLocationId() {
		return storageLocationId;
	}
	public void setStorageLocationId(UUID storageLocationId) {
		this.storageLocationId = storageLocationId;
	}
	public String getStorageLocationName() {
		return storageLocationName;
	}
	public void setStorageLocationName(String storageLocationName) {
		this.storageLocationName = storageLocationName;
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public short getLength() {
		return length;
	}
	public void setLength(short length) {
		this.length = length;
	}
	public short getWidth() {
		return width;
	}
	public void setWidth(short width) {
		this.width = width;
	}
	public short getHeight() {
		return height;
	}
	public void setHeight(short height) {
		this.height = height;
	}
	public short getQuantity() {
		return quantity;
	}
	public void setQuantity(short quantity) {
		this.quantity = quantity;
	}
	


}


	