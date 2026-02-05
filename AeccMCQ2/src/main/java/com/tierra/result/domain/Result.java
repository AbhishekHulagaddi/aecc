package com.tierra.result.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import com.tierra.question.domain.Question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "result")
public class Result {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(columnDefinition = "binary(16)")
	private UUID resultId;
	private UUID answeredBy;
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;
	private String answered;
	private boolean isCorrect;
	private boolean isSkipped;
	private LocalDateTime answeredAt;
	private UUID createdBy;
	private UUID modifiedBy;
	private LocalDateTime createdDate;
	private LocalDateTime modifiedDate;
	private boolean status;
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
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
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
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}

}
