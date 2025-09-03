package com.tierra.result.model;

import java.util.UUID;

public class ViewAllResultModel {
	
	private int page;
	private int size;
	private String PropertyName;
	private String direction;
	private String answered;
	private UUID chapterId;
	private UUID subjectId;
	private UUID answeredBy;
	private int section;
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public String getPropertyName() {
		return PropertyName;
	}
	public void setPropertyName(String propertyName) {
		PropertyName = propertyName;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getAnswered() {
		return answered;
	}
	public void setAnswered(String answered) {
		this.answered = answered;
	}
	public UUID getChapterId() {
		return chapterId;
	}
	public void setChapterId(UUID chapterId) {
		this.chapterId = chapterId;
	}
	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public UUID getAnsweredBy() {
		return answeredBy;
	}
	public void setAnsweredBy(UUID answeredBy) {
		this.answeredBy = answeredBy;
	}
	public UUID getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(UUID subjectId) {
		this.subjectId = subjectId;
	}


	
	

}
