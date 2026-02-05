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
import com.rim.masterdata.model.VendorModel;
import com.rim.masterdata.model.ViewAllMasterDataModel;
import com.rim.masterdata.service.VendorService;

@RestController
@RequestMapping(WebConstantUrl.Vendor)
public class VendorController {
	
	
	@Autowired
	VendorService vendorService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createVendor (@RequestBody VendorModel vendorModel){
		BaseResponse baseResponse = vendorService.createVendor(vendorModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateVendor (@RequestBody VendorModel vendorModel){
		BaseResponse baseResponse = vendorService.updateVendor(vendorModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteVendor (@RequestBody VendorModel vendorModel){
		BaseResponse baseResponse = vendorService.deleteVendor(vendorModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody VendorModel vendorModel){
		vendorModel = vendorService.findById(vendorModel);
		return new ResponseEntity<> (vendorModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllVendor ( @RequestBody ViewAllMasterDataModel viewModel) throws Exception{
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
		Page<VendorModel> vendorModel = vendorService.getAllVendor(viewModel.getVendorName(), pages);

		if (vendorModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", vendorModel.getTotalElements());
			map.put("total_pages", vendorModel.getTotalPages());
			map.put("Vendors", vendorModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", vendorModel.getTotalElements());
		map.put("total_pages", vendorModel.getTotalPages());
		map.put("Vendors", vendorModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
