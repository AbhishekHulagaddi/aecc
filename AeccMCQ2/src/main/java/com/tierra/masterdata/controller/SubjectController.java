package com.tierra.masterdata.controller;

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

import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;
import com.tierra.masterdata.model.SubjectModel;
import com.tierra.masterdata.model.ViewAllMasterDataModel;
import com.tierra.masterdata.service.SubjectService;

@RestController
@RequestMapping(WebConstantUrl.Subject)
public class SubjectController {
	
	
	@Autowired
	SubjectService subjectService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createSubject (@RequestBody SubjectModel subjectModel){
		BaseResponse baseResponse = subjectService.createSubject(subjectModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateSubject (@RequestBody SubjectModel subjectModel){
		BaseResponse baseResponse = subjectService.updateSubject(subjectModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteSubject (@RequestBody SubjectModel subjectModel){
		BaseResponse baseResponse = subjectService.deleteSubject(subjectModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody SubjectModel subjectModel){
		subjectModel = subjectService.findById(subjectModel);
		return new ResponseEntity<> (subjectModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllSubject ( @RequestBody ViewAllMasterDataModel viewModel) throws Exception{
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
		Page<SubjectModel> subjectModel = subjectService.getAllSubject(viewModel.getSubjectName(), pages);
		
		List<SubjectModel> subjectModelList = subjectModel.getContent();
		if (subjectModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", subjectModel.getTotalElements());
			map.put("total_pages", subjectModel.getTotalPages());
			map.put("Subjects", subjectModelList);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", subjectModel.getTotalElements());
		map.put("total_pages", subjectModel.getTotalPages());
		map.put("Subjects", subjectModelList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
