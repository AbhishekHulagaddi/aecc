package com.tierra.auth.domain;




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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;


@Entity
@Table(name="roles")
public class Roles  {	


	@Id
    @GeneratedValue(strategy = GenerationType.UUID)
	@Column(columnDefinition = "binary(16)")
	private UUID roleId;
	@Column(unique = true)
	private String roleName;
	private String discription;
	private boolean status;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private UUID createdBy;
	private UUID modifiedBy;
    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)  // mappedBy for bidirectional relationship
    private Set<User> users = new HashSet<>();
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
	public String getDiscription() {
		return discription;
	}
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
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

	
	
}
