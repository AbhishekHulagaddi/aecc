package com.rim.auth.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.model.RoleModel;
import com.rim.auth.model.RoleViewModel;
import com.rim.auth.utils.BaseResponse;

public interface RoleService {
	
	public BaseResponse saveRole (RoleViewModel roleModel)throws Exception;
	
	public BaseResponse updateRole (RoleModel roleModel);
	
	public RoleModel getRoleByRoleId (UUID roleModel)throws Exception;
	
	public BaseResponse deleteRole (RoleModel roleModel)throws Exception;
	
	public Page<RoleModel> getAllRoles (String roleName ,Pageable pageable)throws Exception;

}
