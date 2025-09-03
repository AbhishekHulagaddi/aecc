package com.tierra.auth.controller;

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

import com.tierra.auth.model.RoleModel;
import com.tierra.auth.model.RoleViewModel;
import com.tierra.auth.model.ViewAllAuthModel;
import com.tierra.auth.service.RoleService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;


@RestController
@RequestMapping(WebConstantUrl.Roles)
public class RoleController {
	
	@Autowired
	RoleService roleService;
	

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> saveRole (@RequestBody RoleViewModel roleModel)throws Exception{
		BaseResponse baseResponse = roleService.saveRole(roleModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> UpdateRole(@RequestBody RoleModel roleModel)throws Exception{
		BaseResponse baseResponse = roleService.updateRole(roleModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteRole (@RequestBody RoleModel roleModel)throws Exception{
		BaseResponse baseResponse  = roleService.deleteRole(roleModel);
		return new ResponseEntity<>(baseResponse,HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindByRoleId)
	public ResponseEntity<?> getRoleByRoleId (@RequestBody RoleModel roleModel)throws Exception{
		RoleModel roleM = roleService.getRoleByRoleId(roleModel.getRoleId());
		return new ResponseEntity<>(roleM,HttpStatus.OK);
	}
	

	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllRoles (@RequestBody ViewAllAuthModel viewModel)throws Exception{
		Map<String, Object> map = new HashMap<>();
		Sort sort =null;
		Pageable pages = null;
		
		if(viewModel.getRoleName()!=null&&viewModel.getDirection()!=null) {
			if(viewModel.getDirection().equalsIgnoreCase("desc")) {
				 sort = Sort.by(Sort.Direction.DESC, viewModel.getRoleName());
			}else{
				 sort = Sort.by(Sort.Direction.ASC, viewModel.getRoleName());
			}
			 pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize(), sort);
			
		}else {
			pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize());

		}
		Page<RoleModel> roleModel = roleService.getAllRoles(viewModel.getRoleName(), pages);
		List<RoleModel> roleModelList = roleModel.getContent();
		
		if (roleModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", roleModel.getTotalElements());
			map.put("total_pages", roleModel.getTotalPages());
			map.put("roleModelList", roleModelList);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", roleModel.getTotalElements());
		map.put("total_pages", roleModel.getTotalPages());
		map.put("roleModelList", roleModelList);
		return new ResponseEntity<>(map, HttpStatus.OK);
	 }
	}
	
	
	
	


}
