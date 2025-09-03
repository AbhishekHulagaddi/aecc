package com.tierra.result.model;

import java.util.UUID;

public class DashBoardModel {
	
	private UUID subjectId;
	
	private String subjectName;
	
	private Long availableTest;
	
	private Long attendedTest;

	public UUID getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(UUID subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public Long getAvailableTest() {
		return availableTest;
	}

	public void setAvailableTest(Long availableTest) {
		this.availableTest = availableTest;
	}

	public Long getAttendedTest() {
		return attendedTest;
	}

	public void setAttendedTest(Long attendedTest) {
		this.attendedTest = attendedTest;
	}
	
	

}
