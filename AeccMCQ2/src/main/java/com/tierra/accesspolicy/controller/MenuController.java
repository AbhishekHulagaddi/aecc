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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tierra.accesspolicy.model.MenuModel;
import com.tierra.accesspolicy.model.ViewAllAccessPolicyModel;
import com.tierra.accesspolicy.service.MenuService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;

import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping(WebConstantUrl.Menu)
public class MenuController {
	
	@Autowired
	MenuService menuService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createMenu (@RequestBody MenuModel menuModel){
		BaseResponse baseResponse = menuService.createMenu(menuModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateMenu (@RequestBody MenuModel menuModel){
		BaseResponse baseResponse = menuService.updateMenu(menuModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteMenu (@RequestBody MenuModel menuModel){
		BaseResponse baseResponse = menuService.deleteMenu(menuModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody MenuModel menuModel){
		menuModel = menuService.findById(menuModel);
		return new ResponseEntity<> (menuModel, HttpStatus.OK);
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
		Page<MenuModel> menuModel = menuService.getAllMenu(viewModel, pages);

		if (menuModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", menuModel.getTotalElements());
			map.put("total_pages", menuModel.getTotalPages());
			map.put("Menus", menuModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", menuModel.getTotalElements());
		map.put("total_pages", menuModel.getTotalPages());
		map.put("Menus", menuModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}

}
