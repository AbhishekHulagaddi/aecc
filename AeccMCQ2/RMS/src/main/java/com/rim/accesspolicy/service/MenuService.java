package com.rim.accesspolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.accesspolicy.model.MenuModel;
import com.rim.accesspolicy.model.ViewAllAccessPolicyModel;
import com.rim.auth.utils.BaseResponse;

public interface MenuService {
	
	public BaseResponse createMenu ( MenuModel menuModel);
	
	public BaseResponse updateMenu ( MenuModel menuModel);
	
	public BaseResponse deleteMenu ( MenuModel menuModel);
	
	public MenuModel findById (MenuModel menuModel);
	
	public Page<MenuModel> getAllMenu (ViewAllAccessPolicyModel viewModel,Pageable pageable)throws Exception;


}
