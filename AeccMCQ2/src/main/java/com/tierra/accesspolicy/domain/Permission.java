package com.tierra.accesspolicy.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tierra.auth.domain.Roles;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Permission")
public class Permission {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(columnDefinition = "binary(16)")
	private UUID permissionId;
	@Column(unique = true)
	private String permissionName;
	@ManyToOne
	@JoinColumn(name = "menus")
	private Menu menus;
	@ManyToOne
	@JoinColumn(name = "subMenus")
	private SubMenu subMenus;
	@ManyToOne
	@JoinColumn(name = "roles")
	private Roles roles;
	@ManyToOne
	@JoinColumn(name = "features")
	private Feature features;
	private boolean isEnable;
	private boolean status;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private UUID createdBy;
	private UUID modifiedBy;
	public UUID getPermissionId() {
		return permissionId;
	}
	public void setPermissionId(UUID permissionId) {
		this.permissionId = permissionId;
	}
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public Menu getMenus() {
		return menus;
	}
	public void setMenus(Menu menus) {
		this.menus = menus;
	}
	public SubMenu getSubMenus() {
		return subMenus;
	}
	public void setSubMenus(SubMenu subMenus) {
		this.subMenus = subMenus;
	}
	public Roles getRoles() {
		return roles;
	}
	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}
	public UUID getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(UUID createdBy) {
		this.createdBy = createdBy;
	}
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public UUID getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(UUID modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Feature getFeatures() {
		return features;
	}
	public void setFeatures(Feature features) {
		this.features = features;
	}
	

}
