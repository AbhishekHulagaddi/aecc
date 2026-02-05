package com.rim.accesspolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.accesspolicy.model.SubMenuModel;
import com.rim.accesspolicy.model.ViewAllAccessPolicyModel;
import com.rim.auth.utils.BaseResponse;


public interface SubMenuService {

	public BaseResponse createSubMenu ( SubMenuModel subMenuModel);
	
	public BaseResponse updateSubMenu ( SubMenuModel subMenuModel);
	
	public BaseResponse deleteSubMenu ( SubMenuModel subMenuModel);
	
	public SubMenuModel findById ( SubMenuModel subMenuModel);
	
	public Page<SubMenuModel> getAllSubMenu (ViewAllAccessPolicyModel viewModel,Pageable pageable)throws Exception;
}
