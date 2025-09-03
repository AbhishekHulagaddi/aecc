package com.tierra.result.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.tierra.auth.utils.CusException;
import com.tierra.question.domain.Question;
import com.tierra.question.repo.QuestionRepo;
import com.tierra.result.domain.Result;
import com.tierra.result.model.ResultModel;


@Component
public class ResultMapper {

  @Autowired
  private QuestionRepo questionRepo;

	public Result convertModelToDomain (ResultModel resultModel) {
		Result result = new Result();
		BeanUtils.copyProperties(resultModel, result);
		Question question = questionRepo.findById(resultModel.getQuestionId())
				.orElseThrow(()->new CusException(" Question Not Found ", HttpStatus.NOT_FOUND));
		result.setQuestion(question);
		return result;
	}
	
	public ResultModel convertDomainToModel (Result result) {
		ResultModel resultModel = new ResultModel();
		BeanUtils.copyProperties(result, resultModel);
		resultModel.setQuestion(result.getQuestion().getQuestionName());
		resultModel.setQuestionId(result.getQuestion().getQuestionId());
		resultModel.setOptionA(result.getQuestion().getOptionA());
		resultModel.setOptionB(result.getQuestion().getOptionB());
		resultModel.setOptionC(result.getQuestion().getOptionC());
		resultModel.setOptionD(result.getQuestion().getOptionD());
		resultModel.setCorrectAnswer(result.getQuestion().getCorrectAnswer());
		return resultModel;
	}

	public Page<ResultModel> ConverDomainToModel(Page<Result> result) {
		
	        List<ResultModel> resultModel =  new ArrayList<>();
	        for(Result resultD : result)
	        {
	        	ResultModel resultM = new ResultModel();
	              BeanUtils.copyProperties(resultD, resultM);
	              resultM.setQuestion(resultD.getQuestion().getQuestionName());
	              resultM.setQuestionId(resultD.getQuestion().getQuestionId());
	              resultM.setOptionA(resultD.getQuestion().getOptionA());
	              resultM.setOptionB(resultD.getQuestion().getOptionB());
	              resultM.setOptionC(resultD.getQuestion().getOptionC());
	              resultM.setOptionD(resultD.getQuestion().getOptionD());
	              resultM.setCorrectAnswer(resultD.getQuestion().getCorrectAnswer());
	              resultModel.add(resultM);
	        }
			
			
	        return new PageImpl<>(resultModel, result.getPageable(), result.getTotalElements());
	    }
}
