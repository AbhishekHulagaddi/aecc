package com.rim.auth.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.auth.domain.Roles;
import com.rim.auth.domain.User;
import com.rim.auth.model.RoleModel;
import com.rim.auth.model.UserCreateModel;
import com.rim.auth.model.UserModel;
import com.rim.auth.model.UserViewModel;
import com.rim.auth.repo.RoleRepo;
import com.rim.auth.service.RoleService;

import jakarta.persistence.EntityNotFoundException;

@Component
public class UserMapper {
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	RoleService roleService;
	
	public UserViewModel convertDomainToViewModel (User user) throws Exception {
		UserViewModel userModel = new UserViewModel();
		Set<RoleModel> RoleModels = new HashSet<>();
		BeanUtils.copyProperties(user, userModel);
		for(Roles roleD :user.getRoles())
		{	RoleModel  roleM = new RoleModel();
			roleM.setRoleId(roleD.getRoleId());
		    roleM.setRoleName(roleD.getRoleName());
		    roleM.setDiscription(roleD.getDiscription());
		    RoleModels.add(roleM);
			
		}
		userModel.setRoles(RoleModels);
		//userModel.setRoleModel( roleService.getRoleByRoleId(userDomain.getRoles().getRoleId()));
		return userModel;
	}
	
	public User convertCreateModelToDomain (UserCreateModel userModel) {
		User user = new User();
		Set<Roles> roles = new HashSet<>();
		for(UUID roleId : userModel.getRoleIds()) {
			Roles roleD = new Roles();
			roleD = roleRepo.findById(roleId)
					.orElseThrow(() -> new EntityNotFoundException("Role not found for given RoleId "+ roleId));
			roles.add(roleD);
		}
		BeanUtils.copyProperties(userModel, user);
		user.setRoles(roles);
		return user;
	}
	
	public User convertViewModelToDomain (UserViewModel userModel) {
		User user = new User();
		BeanUtils.copyProperties(userModel, user);
		return user;
	}
	
	public UserModel convertDomainToModel (User user) {
		UserModel userModel = new UserModel();
		Set<RoleModel> roles = new HashSet<>();
		for(Roles roleD : user.getRoles()) {
			RoleModel roleM = new RoleModel();
			roleM.setDiscription(roleD.getDiscription());
			roleM.setRoleId(roleD.getRoleId());
			roleM.setRoleName(roleD.getRoleName());
			roles.add(roleM);
		}
		BeanUtils.copyProperties(user, userModel);
		userModel.setRoles(roles);
		return userModel;
	}


	public Page<UserViewModel> ConverDomainToModel(Page<User> user) {
		List<UserViewModel> userModels = new ArrayList();
		for(User userD: user)
		{
			UserViewModel userM = new UserViewModel();
			Set<RoleModel> roles = new HashSet<>();
			for(Roles roleD : userD.getRoles()) {
				RoleModel roleM = new RoleModel();
				roleM.setDiscription(roleD.getDiscription());
				roleM.setRoleId(roleD.getRoleId());
				roleM.setRoleName(roleD.getRoleName());
				roles.add(roleM);
			}
			BeanUtils.copyProperties(userD, userM);
			userM.setRoles(roles);
			//userM.setRoleModel( roleService.getRoleByRoleId(userD.getRoles().getRoleId()));
			userModels.add(userM);
		}
		 return new PageImpl<>(userModels, user.getPageable(), user.getTotalElements());
	}

}
