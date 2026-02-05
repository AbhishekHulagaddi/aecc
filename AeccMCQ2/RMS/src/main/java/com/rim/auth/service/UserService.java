package com.rim.auth.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.model.ChangePasswordModel;
import com.rim.auth.model.LoginModel;
import com.rim.auth.model.UserCreateModel;
import com.rim.auth.model.UserModel;
import com.rim.auth.model.UserViewModel;
import com.rim.auth.utils.BaseResponse;


public interface UserService {
	
	public BaseResponse saveUser(UserCreateModel userModel)throws Exception;
	
	public UserModel login(LoginModel loginRequest)throws Exception;
	
	public BaseResponse updateUser (UserViewModel userModel)throws Exception;
	
	public BaseResponse changePassword (ChangePasswordModel userModel)throws Exception;
	
	public UserViewModel getUserByUserCode (long usercode)throws Exception;
	
	public BaseResponse deleteUser (UserModel userModel)throws Exception;
	
	public Page<UserViewModel> getAllUsers (String userName,Pageable pageable)throws Exception;
	

	

}
