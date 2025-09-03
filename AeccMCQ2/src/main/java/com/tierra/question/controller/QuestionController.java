package com.tierra.question.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;
import com.tierra.question.model.QuestionModel;
import com.tierra.question.model.SectionModel;
import com.tierra.question.model.ViewAllQuestionModel;
import com.tierra.question.service.QuestionService;
import com.tierra.result.service.ResultService;

@RestController
@RequestMapping(WebConstantUrl.Question)
public class QuestionController {
	
	
	@Autowired
	QuestionService questionService;
	
	@Autowired
	ResultService resultService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createQuestion (@RequestBody QuestionModel questionModel){
		BaseResponse baseResponse = questionService.createQuestion(questionModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateQuestion (@RequestBody QuestionModel questionModel){
		BaseResponse baseResponse = questionService.updateQuestion(questionModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteQuestion (@RequestBody QuestionModel questionModel){
		BaseResponse baseResponse = questionService.deleteQuestion(questionModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody QuestionModel questionModel){
		questionModel = questionService.findById(questionModel);
		return new ResponseEntity<> (questionModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindSection)
	public ResponseEntity<?> findSections(@RequestBody QuestionModel questionModel){
		SectionModel section = questionService.getDistinctAvailavleSections(questionModel);
		if(section==null) {
			return new ResponseEntity<> ("No sections Found", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<> (section, HttpStatus.OK);
	}
	
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllQuestion ( @RequestBody ViewAllQuestionModel viewModel) throws Exception{
		Map<String, Object> map = new HashMap<>();
		Sort sort =null;
		Pageable pages = null;
		if(viewModel.getPropertyName()!=null) {
			List<QuestionModel> questionModelList = questionService.findWeeklyTestQuestions();
			map.put("status_code", HttpStatus.OK);
			map.put("total_records", questionModelList.size());
			map.put("Questions", questionModelList);

			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		if(viewModel.getSubjectId()!=null&&!resultService.IsUserAttendedThisTestAlready(viewModel)) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", 0);
			map.put("total_pages", 0);
			map.put("message", "You Already Attended this Test, Try after someTime!");
			return new ResponseEntity<>(map, HttpStatus.OK);
		}
		if(viewModel.getPropertyName()!=null&&viewModel.getDirection()!=null) {
			if(viewModel.getDirection().equalsIgnoreCase("desc")) {
				 sort = Sort.by(Sort.Direction.DESC, viewModel.getPropertyName());
			}else{
				 sort = Sort.by(Sort.Direction.ASC, viewModel.getPropertyName());
			}
			 pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize(), sort);
			
		}else {
			pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize());

		}
		Page<QuestionModel> questionModel = questionService.getAllQuestion(viewModel.getQuestionName(),viewModel.getSubjectId(),viewModel.getChapterId(),viewModel.getSection(), pages);
		List<QuestionModel> questionModelList = questionModel.getContent();
		if (questionModel.getContent().isEmpty()) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", questionModel.getTotalElements());
			map.put("total_pages", questionModel.getTotalPages());
			map.put("Questions", questionModelList);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", questionModel.getTotalElements());
		map.put("total_pages", questionModel.getTotalPages());
		map.put("Questions", questionModelList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}
	
	@PostMapping(WebConstantUrl.AddWeeklyTest)
	public ResponseEntity<?> createWeeklyTest (@RequestBody List<QuestionModel> questionModel,@RequestParam LocalDateTime startDate,@RequestParam LocalDateTime toDate){
		String baseResponse = questionService.addWeeklyTest(questionModel,startDate,toDate);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	


}
