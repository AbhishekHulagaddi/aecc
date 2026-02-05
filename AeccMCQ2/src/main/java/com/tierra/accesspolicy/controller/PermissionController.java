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

import com.tierra.accesspolicy.model.PermissionAccessModel;
import com.tierra.accesspolicy.model.PermissionModel;
import com.tierra.accesspolicy.model.ViewAllAccessPolicyModel;
import com.tierra.accesspolicy.service.PermissionService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;



@RestController
@RequestMapping(WebConstantUrl.Permission)
public class PermissionController {
	
	@Autowired
	PermissionService permissionService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createPermission (@RequestBody PermissionModel permissionModel){
		BaseResponse baseResponse = permissionService.createPermission(permissionModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updatePermission (@RequestBody PermissionModel permissionModel){
		BaseResponse baseResponse = permissionService.updatePermission(permissionModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deletePermission (@RequestBody PermissionModel permissionModel){
		BaseResponse baseResponse = permissionService.deletePermission(permissionModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody PermissionModel permissionModel){
		permissionModel = permissionService.findById(permissionModel);
		return new ResponseEntity<> (permissionModel, HttpStatus.OK);
	}
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllMenu ( @RequestBody ViewAllAccessPolicyModel viewModel) throws Exception{
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
		Page<PermissionModel> permissionModel = permissionService.getAllPermission(viewModel, pages);

		if (permissionModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", permissionModel.getTotalElements());
			map.put("total_pages", permissionModel.getTotalPages());
			map.put("Permission", permissionModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", permissionModel.getTotalElements());
		map.put("total_pages", permissionModel.getTotalPages());
		map.put("Permission", permissionModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}
	
	@PostMapping(WebConstantUrl.FindPermissionByMenuAndSubMenuAndFeature)
	public ResponseEntity<?> getPermissionByMenuAndSubMenuAndFeature (@RequestBody PermissionAccessModel permissionAccessModel){
		PermissionModel permissionModel = permissionService.getPermissionByMenuAndSubMenuAndFeature(permissionAccessModel);
		return new ResponseEntity<> (permissionModel, HttpStatus.OK);
	}

}
