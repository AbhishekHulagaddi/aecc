package com.tierra.result.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;


public class ResultModel implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6786143202257419868L;
	private UUID resultId;
	private UUID answeredBy;
	private UUID questionId;
	private String question;
	private String answered;
	private boolean isCorrect;
	private boolean isSkipped;
	private LocalDateTime answeredAt;
	private String optionA;
	private String optionB;
	private String optionC;
	private String optionD;
	private String correctAnswer;
	public UUID getResultId() {
		return resultId;
	}
	public void setResultId(UUID resultId) {
		this.resultId = resultId;
	}
	public UUID getAnsweredBy() {
		return answeredBy;
	}
	public void setAnsweredBy(UUID answeredBy) {
		this.answeredBy = answeredBy;
	}
	public UUID getQuestionId() {
		return questionId;
	}
	public void setQuestionId(UUID questionId) {
		this.questionId = questionId;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswered() {
		return answered;
	}
	public void setAnswered(String answered) {
		this.answered = answered;
	}
	public boolean isCorrect() {
		return isCorrect;
	}
	public void setCorrect(boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	public boolean isSkipped() {
		return isSkipped;
	}
	public void setSkipped(boolean isSkipped) {
		this.isSkipped = isSkipped;
	}
	public LocalDateTime getAnsweredAt() {
		return answeredAt;
	}
	public void setAnsweredAt(LocalDateTime answeredAt) {
		this.answeredAt = answeredAt;
	}
	public String getOptionA() {
		return optionA;
	}
	public void setOptionA(String optionA) {
		this.optionA = optionA;
	}
	public String getOptionB() {
		return optionB;
	}
	public void setOptionB(String optionB) {
		this.optionB = optionB;
	}
	public String getOptionC() {
		return optionC;
	}
	public void setOptionC(String optionC) {
		this.optionC = optionC;
	}
	public String getOptionD() {
		return optionD;
	}
	public void setOptionD(String optionD) {
		this.optionD = optionD;
	}
	public String getCorrectAnswer() {
		return correctAnswer;
	}
	public void setCorrectAnswer(String correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
}
