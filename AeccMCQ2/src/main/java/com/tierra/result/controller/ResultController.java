package com.tierra.result.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.tierra.auth.model.UserModel;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;
import com.tierra.question.model.SectionModel;
import com.tierra.result.model.DashBoardModel;
import com.tierra.result.model.ResultModel;
import com.tierra.result.model.UserResultDisplayModel;
import com.tierra.result.model.ViewAllResultModel;
import com.tierra.result.service.ResultService;

@RestController
@RequestMapping(WebConstantUrl.Result)
public class ResultController {
	
	
	@Autowired
	ResultService resultService;

//	@PostMapping(WebConstantUrl.Create)
//	public ResponseEntity<?> createResult (@RequestBody ResultModel resultModel){
//		BaseResponse baseResponse = resultService.createResult(resultModel);
//		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
//	}
	
	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createResults(@RequestBody List<ResultModel> resultModels) {
		UserResultDisplayModel userResultModel = resultService.createResults(resultModels);
	    System.out.println(userResultModel);
		return new ResponseEntity<>(userResultModel, HttpStatus.CREATED);
	}

	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateResult (@RequestBody ResultModel resultModel){
		BaseResponse baseResponse = resultService.updateResult(resultModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteResult (@RequestBody ResultModel resultModel){
		BaseResponse baseResponse = resultService.deleteResult(resultModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody ResultModel resultModel){
		resultModel = resultService.findById(resultModel);
		return new ResponseEntity<> (resultModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindUsersAttendedTests)
	public ResponseEntity<?> findUsersAttendedTests(){
		List<UserModel> userModelList=resultService.findUserAttendedTests();
		return new ResponseEntity<> (userModelList,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindUserResult)
	public ResponseEntity<?> findUserResult (@RequestBody ViewAllResultModel resultModel){
		UserResultDisplayModel userResultModel = resultService.getUserResult(resultModel);
		return new ResponseEntity<> (userResultModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindSection)
	public ResponseEntity<?> findSections(){
		List<SectionModel> section = resultService.getDistinctAvailavleSections();
		return new ResponseEntity<> (section, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.UserDashboard)
	public ResponseEntity<?> userDashboard(){
		List<DashBoardModel> section = resultService.getDashboardData();
		return new ResponseEntity<> (section, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllResult ( @RequestBody ViewAllResultModel viewModel) throws Exception{
		Map<String, Object> map = new HashMap<>();
		Sort sort =null;
		Pageable pages = null;
		
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
		Page<ResultModel> resultModel = resultService.getAllResult(viewModel.getChapterId(),viewModel.getSection(), pages);
		List<ResultModel> resultModelList=resultModel.getContent();
		if (resultModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", resultModel.getTotalElements());
			map.put("total_pages", resultModel.getTotalPages());
			map.put("Results", resultModelList);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", resultModel.getTotalElements());
		map.put("total_pages", resultModel.getTotalPages());
		map.put("Results", resultModelList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
