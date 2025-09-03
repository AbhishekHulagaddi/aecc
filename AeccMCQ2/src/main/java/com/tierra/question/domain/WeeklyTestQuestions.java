package com.tierra.question.domain;

import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "WeeklyTestQuestions")
public class WeeklyTestQuestions {
	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	@Column(columnDefinition = "binary(16)")
	private UUID weeklytestQuestionId;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime startDate;
	
	private LocalDateTime endDate;
	
	private boolean status;
	
	@ManyToOne
	@JoinColumn(name = "question")
	private Question question;

	public UUID getWeeklytestQuestionId() {
		return weeklytestQuestionId;
	}

	public void setWeeklytestQuestionId(UUID weeklytestQuestionId) {
		this.weeklytestQuestionId = weeklytestQuestionId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}



	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Question getQuestion() {
		return question;
	}

	public void setQuestion(Question question) {
		this.question = question;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	
	
	

}
