package com.tierra.result.service;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.auth.model.UserModel;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.question.model.QuestionModel;
import com.tierra.question.model.SectionModel;
import com.tierra.question.model.ViewAllQuestionModel;
import com.tierra.result.model.DashBoardModel;
import com.tierra.result.model.ResultModel;
import com.tierra.result.model.UserResultDisplayModel;
import com.tierra.result.model.ViewAllResultModel;

public interface ResultService {
	
	public BaseResponse createResult (ResultModel resultModel);
	
	public UserResultDisplayModel createResults(List<ResultModel> resultModels);
	
	public BaseResponse updateResult (ResultModel resultModel);
	
	public BaseResponse deleteResult (ResultModel resultModel);
	
	public ResultModel findById (ResultModel resultModel);
	
	public Page<ResultModel> getAllResult (UUID chapterId,int section, Pageable pageable)throws Exception;
	
	public UserResultDisplayModel getUserResult(ViewAllResultModel resultModel);
	
	public Boolean IsUserAttendedThisTestAlready( ViewAllQuestionModel viewModel);
	
	public List<SectionModel> getDistinctAvailavleSections();
	
	public List<UserModel> findUserAttendedTests();

	public List<DashBoardModel> getDashboardData();



}
