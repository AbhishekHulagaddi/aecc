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
import com.rim.masterdata.model.StorageLocationModel;
import com.rim.masterdata.model.ViewAllMasterDataModel;
import com.rim.masterdata.service.StorageLocationService;


@RestController
@RequestMapping(WebConstantUrl.StorageLocation)
public class StorageLocationController {
	
	
	@Autowired
	StorageLocationService storageLocationService;


	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createStorageLocation (@RequestBody StorageLocationModel storageLocationModel){
		BaseResponse baseResponse = storageLocationService.createStorageLocation(storageLocationModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateStorageLocation (@RequestBody StorageLocationModel storageLocationModel){
		BaseResponse baseResponse = storageLocationService.updateStorageLocation(storageLocationModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteStorageLocation (@RequestBody StorageLocationModel storageLocationModel){
		BaseResponse baseResponse = storageLocationService.deleteStorageLocation(storageLocationModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody StorageLocationModel storageLocationModel){
		storageLocationModel = storageLocationService.findById(storageLocationModel);
		return new ResponseEntity<> (storageLocationModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllStorageLocation ( @RequestBody ViewAllMasterDataModel viewModel) throws Exception{
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
		Page<StorageLocationModel> storageLocationModel = storageLocationService.getAllStorageLocation(viewModel.getStorageLocationName(), pages);

		if (storageLocationModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", storageLocationModel.getTotalElements());
			map.put("total_pages", storageLocationModel.getTotalPages());
			map.put("StorageLocations", storageLocationModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", storageLocationModel.getTotalElements());
		map.put("total_pages", storageLocationModel.getTotalPages());
		map.put("StorageLocations", storageLocationModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
