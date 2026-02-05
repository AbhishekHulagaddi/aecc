package com.rim.inventory.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.rim.masterdata.domain.Product;
import com.rim.masterdata.domain.Quality;
import com.rim.masterdata.domain.StorageLocation;
import com.rim.masterdata.domain.Vendor;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Inventory")
public class Inventory {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID inventoryId;
	private String inventoryName;
	private String inventoryCode;
	private String parentCode;
	private String returnedFrom;
	@ManyToOne
	@JoinColumn(name = "vendor")
	private Vendor vendor;
	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;
	@ManyToOne
	@JoinColumn(name = "quality")
	private Quality quality;
	@ManyToOne
	@JoinColumn(name = "storageLocation")
	private StorageLocation storageLocation;
	private short length;
	private short width;
	private short height;
	private String fileName;
	private String base64;
	private String extension;
	private UUID createdBy;
	private UUID modifiedBy;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private boolean status;
	public UUID getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(UUID inventoryId) {
		this.inventoryId = inventoryId;
	}
	public String getInventoryName() {
		return inventoryName;
	}
	public void setInventoryName(String inventoryName) {
		this.inventoryName = inventoryName;
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
	public Vendor getVendor() {
		return vendor;
	}
	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Quality getQuality() {
		return quality;
	}
	public void setQuality(Quality quality) {
		this.quality = quality;
	}
	public StorageLocation getStorageLocation() {
		return storageLocation;
	}
	public void setStorageLocation(StorageLocation storageLocation) {
		this.storageLocation = storageLocation;
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
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBase64() {
		return base64;
	}
	public void setBase64(String base64) {
		this.base64 = base64;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getInventoryCode() {
		return inventoryCode;
	}
	public void setInventoryCode(String inventoryCode) {
		this.inventoryCode = inventoryCode;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getReturnedFrom() {
		return returnedFrom;
	}
	public void setReturnedFrom(String returnedFrom) {
		this.returnedFrom = returnedFrom;
	}
	
	
	
	

}
