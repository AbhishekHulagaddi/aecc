package com.tierra.accesspolicy.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.accesspolicy.model.PermissionAccessModel;
import com.tierra.accesspolicy.model.PermissionModel;
import com.tierra.accesspolicy.model.ViewAllAccessPolicyModel;
import com.tierra.auth.utils.BaseResponse;

public interface PermissionService {
	
	public BaseResponse createPermission ( PermissionModel permissionModel);
	
	public BaseResponse updatePermission ( PermissionModel permissionModel);
	
	public BaseResponse deletePermission ( PermissionModel permissionModel);
	
	public PermissionModel findById ( PermissionModel permissionModel);
	
	public Page<PermissionModel> getAllPermission (ViewAllAccessPolicyModel viewModel,Pageable pageable)throws Exception;

	public PermissionModel getPermissionByMenuAndSubMenuAndFeature (PermissionAccessModel permissionAccessModel);

	
	
}
