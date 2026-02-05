package com.tierra.auth.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.auth.model.ChangePasswordModel;
import com.tierra.auth.model.LoginModel;
import com.tierra.auth.model.UserCreateModel;
import com.tierra.auth.model.UserModel;
import com.tierra.auth.model.UserViewModel;
import com.tierra.auth.utils.BaseResponse;


public interface UserService {
	
	public BaseResponse saveUser(UserCreateModel userModel)throws Exception;
	
	public UserModel login(LoginModel loginRequest)throws Exception;
	
	public BaseResponse updateUser (UserViewModel userModel)throws Exception;
	
	public BaseResponse changePassword (ChangePasswordModel userModel)throws Exception;
	
	public UserViewModel getUserByUserCode (long usercode)throws Exception;
	
	public BaseResponse deleteUser (UserModel userModel)throws Exception;
	
	public Page<UserViewModel> getAllUsers (String userName,Pageable pageable)throws Exception;
	
    public UserModel findByUserId(UUID userID);

	public BaseResponse sendOtp(UserModel userModel);
	
	public BaseResponse forgotPassword(ChangePasswordModel changePasswordModel) throws Exception;
	

}
