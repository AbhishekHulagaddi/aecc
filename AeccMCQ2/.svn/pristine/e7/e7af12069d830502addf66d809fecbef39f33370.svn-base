package com.rim.transactions.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.rim.masterdata.domain.Product;
import com.rim.masterdata.domain.Quality;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Transactions")
public class Transactions {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private UUID transactionsId;
	private String transactionsName;
	private String requestedBy;
	private String projectName;
	@ManyToOne
	@JoinColumn(name="product")
	private Product product;
	@ManyToOne
	@JoinColumn(name="quality")
	private Quality quality;
	private short width;
	private short height;
	private short lenght;
	private String approvedBy;
	private UUID createdBy;
	private UUID modifiedBy;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private boolean status;
	public UUID getTransactionsId() {
		return transactionsId;
	}
	public void setTransactionsId(UUID transactionsId) {
		this.transactionsId = transactionsId;
	}
	public String getTransactionsName() {
		return transactionsName;
	}
	public void setTransactionsName(String transactionsName) {
		this.transactionsName = transactionsName;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
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
	public short getLenght() {
		return lenght;
	}
	public void setLenght(short lenght) {
		this.lenght = lenght;
	}
	public String getApprovedBy() {
		return approvedBy;
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
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



}
