package com.tierra.result.model;

import java.util.List;
import java.util.UUID;

public class UserResultDisplayModel {

	private UUID subjectId;
	private String subjectName;
	private UUID chapterId;
	private String chapterName;
	private int section;
	private double score;
	private String percentage;
	private double correctAnswers;
	private double wrongAnswers;
	private double skippedAnswers;
	private List<ResultModel> resultModels;
	
	public double getCorrectAnswers() {
		return correctAnswers;
	}
	public void setCorrectAnswers(double correctAnswers) {
		this.correctAnswers = correctAnswers;
	}
	public double getWrongAnswers() {
		return wrongAnswers;
	}
	public void setWrongAnswers(double wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}
	public double getSkippedAnswers() {
		return skippedAnswers;
	}
	public void setSkippedAnswers(double skippedAnswers) {
		this.skippedAnswers = skippedAnswers;
	}
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
	public UUID getChapterId() {
		return chapterId;
	}
	public void setChapterId(UUID chapterId) {
		this.chapterId = chapterId;
	}
	public String getChapterName() {
		return chapterName;
	}
	public void setChapterName(String chapterName) {
		this.chapterName = chapterName;
	}

	public int getSection() {
		return section;
	}
	public void setSection(int section) {
		this.section = section;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
	public String getPercentage() {
		return percentage;
	}
	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}
	public List<ResultModel> getResultModels() {
		return resultModels;
	}
	public void setResultModels(List<ResultModel> resultModels) {
		this.resultModels = resultModels;
	}

	
}
