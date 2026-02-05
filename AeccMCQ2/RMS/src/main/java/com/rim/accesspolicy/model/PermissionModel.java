package com.rim.accesspolicy.model;



import java.util.UUID;

public class PermissionModel {
	
	private UUID permissionId;
	private String permissionName;
	private UUID menuId;
	private String menuName;
	private UUID subMenuId;
	private String subMenuName;
	private UUID featureId;
	private String featureName;
	private UUID roleId;
	private String roleName;
	private boolean isEnable;
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
	public UUID getSubMenuId() {
		return subMenuId;
	}
	public void setSubMenuId(UUID subMenuId) {
		this.subMenuId = subMenuId;
	}
	public String getSubMenuName() {
		return subMenuName;
	}
	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}
	public UUID getFeatureId() {
		return featureId;
	}
	public void setFeatureId(UUID featureId) {
		this.featureId = featureId;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public UUID getRoleId() {
		return roleId;
	}
	public void setRoleId(UUID roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public boolean isEnable() {
		return isEnable;
	}
	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}



	

}
