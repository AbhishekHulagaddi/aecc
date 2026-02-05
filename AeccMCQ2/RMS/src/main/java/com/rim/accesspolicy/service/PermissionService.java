package com.rim.accesspolicy.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.accesspolicy.model.PermissionAccessModel;
import com.rim.accesspolicy.model.PermissionModel;
import com.rim.accesspolicy.model.ViewAllAccessPolicyModel;
import com.rim.auth.utils.BaseResponse;

public interface PermissionService {
	
	public BaseResponse createPermission ( PermissionModel permissionModel);
	
	public BaseResponse updatePermission ( PermissionModel permissionModel);
	
	public BaseResponse deletePermission ( PermissionModel permissionModel);
	
	public PermissionModel findById ( PermissionModel permissionModel);
	
	public Page<PermissionModel> getAllPermission (ViewAllAccessPolicyModel viewModel,Pageable pageable)throws Exception;

	public PermissionModel getPermissionByMenuAndSubMenuAndFeature (PermissionAccessModel permissionAccessModel);

	
	
}
