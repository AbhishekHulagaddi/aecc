package com.rim.masterdata.model;

import java.util.UUID;

public class StorageLocationModel {
	
	private UUID storageLocationId;
	private String storageLocationName;
	private short length;
	private short width;
	private short height;
	private short quantity;
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
