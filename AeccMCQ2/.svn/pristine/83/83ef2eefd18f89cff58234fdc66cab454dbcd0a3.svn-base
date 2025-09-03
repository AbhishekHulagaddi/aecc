package com.rim.masterdata.controller;

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

import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.WebConstantUrl;
import com.rim.masterdata.model.QualityModel;
import com.rim.masterdata.model.ViewAllMasterDataModel;
import com.rim.masterdata.service.QualityService;

@RestController
@RequestMapping(WebConstantUrl.Quality)
public class QualityController {
	
	
	@Autowired
	QualityService qualityService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createQuality (@RequestBody QualityModel qualityModel){
		BaseResponse baseResponse = qualityService.createQuality(qualityModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateQuality (@RequestBody QualityModel qualityModel){
		BaseResponse baseResponse = qualityService.updateQuality(qualityModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteQuality (@RequestBody QualityModel qualityModel){
		BaseResponse baseResponse = qualityService.deleteQuality(qualityModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody QualityModel qualityModel){
		qualityModel = qualityService.findById(qualityModel);
		return new ResponseEntity<> (qualityModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllQuality ( @RequestBody ViewAllMasterDataModel viewModel) throws Exception{
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
		Page<QualityModel> qualityModel = qualityService.getAllQuality(viewModel.getQualityName(), pages);

		if (qualityModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", qualityModel.getTotalElements());
			map.put("total_pages", qualityModel.getTotalPages());
			map.put("Qualitys", qualityModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", qualityModel.getTotalElements());
		map.put("total_pages", qualityModel.getTotalPages());
		map.put("Qualitys", qualityModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
