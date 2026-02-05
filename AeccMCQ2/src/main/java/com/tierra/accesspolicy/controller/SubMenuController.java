package com.tierra.accesspolicy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tierra.accesspolicy.model.SubMenuModel;
import com.tierra.accesspolicy.model.ViewAllAccessPolicyModel;
import com.tierra.accesspolicy.service.SubMenuService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping(WebConstantUrl.SubMenu)
public class SubMenuController {
	
	@Autowired
	SubMenuService subMenuService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createSubMenu (@RequestBody SubMenuModel subMenuModel){
		BaseResponse baseResponse = subMenuService.createSubMenu(subMenuModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateSubMenu (@RequestBody SubMenuModel subMenuModel){
		BaseResponse baseResponse = subMenuService.updateSubMenu(subMenuModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteSubMenu (@RequestBody SubMenuModel subMenuModel){
		BaseResponse baseResponse = subMenuService.deleteSubMenu(subMenuModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody SubMenuModel subMenuModel){
		subMenuModel = subMenuService.findById(subMenuModel);
		return new ResponseEntity<> (subMenuModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllSubMenu ( @RequestBody ViewAllAccessPolicyModel viewModel) throws Exception{
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
		Page<SubMenuModel> subMenuModel = subMenuService.getAllSubMenu(viewModel, pages);

		if (subMenuModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", subMenuModel.getTotalElements());
			map.put("total_pages", subMenuModel.getTotalPages());
			map.put("SubMenus", subMenuModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", subMenuModel.getTotalElements());
		map.put("total_pages", subMenuModel.getTotalPages());
		map.put("SubMenus", subMenuModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}
}
