package com.tierra.question.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.question.model.QuestionModel;
import com.tierra.question.model.SectionModel;

public interface QuestionService {
	
	public BaseResponse createQuestion (QuestionModel questionModel);
	
	public BaseResponse updateQuestion (QuestionModel questionModel);
	
	public BaseResponse deleteQuestion (QuestionModel questionModel);
	
	public QuestionModel findById (QuestionModel questionModel);
	
	public Page<QuestionModel> getAllQuestion (String questionName, UUID subjectId,UUID chapterId, int section, Pageable pageable)throws Exception;
	
	public SectionModel getDistinctAvailavleSections (QuestionModel questionModel);

	public List<QuestionModel> findWeeklyTestQuestions(); 
	
	public String addWeeklyTest(List<QuestionModel> questionModelList,LocalDateTime startDate,LocalDateTime endDate);
	

}
