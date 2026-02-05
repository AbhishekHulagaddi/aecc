package com.rim.masterdata.model;

import java.util.UUID;

public class PlanStatusModel {
	
	private UUID planStatusId;
	private String planStatusName;
	public UUID getPlanStatusId() {
		return planStatusId;
	}
	public void setPlanStatusId(UUID planStatusId) {
		this.planStatusId = planStatusId;
	}
	public String getPlanStatusName() {
		return planStatusName;
	}
	public void setPlanStatusName(String planStatusName) {
		this.planStatusName = planStatusName;
	}
	

}
