package com.tierra.accesspolicy.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Menu")
public class Menu {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(columnDefinition = "binary(16)")
	private UUID menuId;
	@Column(unique = true)
	private String menuName;
	 @ManyToMany(fetch = FetchType.LAZY)
	 @JoinTable(name = "menus_submenus", 
	            joinColumns = @JoinColumn(name = "menu_Id"),
	            inverseJoinColumns = @JoinColumn(name = "submenu_id"))
	private Set<SubMenu> subMenu = new HashSet<>();
	private boolean status;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private UUID createdBy;
	private UUID modifiedBy;
	public UUID getMenuId() {
		return menuId;
	}
	public void setMenuId(UUID menuId) {
		this.menuId = menuId;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public Set<SubMenu> getSubMenu() {
		return subMenu;
	}
	public void setSubMenu(Set<SubMenu> subMenu) {
		this.subMenu = subMenu;
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



}
