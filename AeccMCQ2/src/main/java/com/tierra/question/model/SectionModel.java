package com.tierra.question.model;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SectionModel {
	
	private List<Integer> sections;
	
	private String subjectName;
	
	private UUID chapterId;
	
	private UUID subjectId;

	public List<Integer> getSections() {
		return sections;
	}

	public void setSections(List<Integer> sections) {
		this.sections = sections;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public UUID getChapterId() {
		return chapterId;
	}

	public void setChapterId(UUID chapterId) {
		this.chapterId = chapterId;
	}

	public UUID getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(UUID subjectId) {
		this.subjectId = subjectId;
	}
	
	
	
	

}
