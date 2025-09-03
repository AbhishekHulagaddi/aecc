package com.tierra.accesspolicy.controller;

import java.util.HashMap;
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

import com.tierra.accesspolicy.model.FeatureModel;
import com.tierra.accesspolicy.model.ViewAllAccessPolicyModel;
import com.tierra.accesspolicy.service.FeatureService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;

@RestController
@RequestMapping(WebConstantUrl.Feature)
public class FeatureController {
	
	@Autowired
	FeatureService featureService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createFeature (@RequestBody FeatureModel featureModel){
		BaseResponse baseResponse = featureService.createFeature(featureModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateFeature (@RequestBody FeatureModel featureModel){
		BaseResponse baseResponse = featureService.updateFeature(featureModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteFeature (@RequestBody FeatureModel featureModel){
		BaseResponse baseResponse= featureService.deleteFeature(featureModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody FeatureModel featureModel){
		featureModel = featureService.findById(featureModel);
		return new ResponseEntity<> (featureModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllFeature ( @RequestBody ViewAllAccessPolicyModel viewModel) throws Exception{
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
		Page<FeatureModel> featureModel = featureService.getAllFeature(viewModel, pages);

		if (featureModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", featureModel.getTotalElements());
			map.put("total_pages", featureModel.getTotalPages());
			map.put("Features", featureModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", featureModel.getTotalElements());
		map.put("total_pages", featureModel.getTotalPages());
		map.put("Features", featureModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}
}
