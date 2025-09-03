package com.tierra.auth.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.auth.model.RoleModel;
import com.tierra.auth.model.RoleViewModel;
import com.tierra.auth.utils.BaseResponse;

public interface RoleService {
	
	public BaseResponse saveRole (RoleViewModel roleModel)throws Exception;
	
	public BaseResponse updateRole (RoleModel roleModel);
	
	public RoleModel getRoleByRoleId (UUID roleModel)throws Exception;
	
	public BaseResponse deleteRole (RoleModel roleModel)throws Exception;
	
	public Page<RoleModel> getAllRoles (String roleName ,Pageable pageable)throws Exception;

}
