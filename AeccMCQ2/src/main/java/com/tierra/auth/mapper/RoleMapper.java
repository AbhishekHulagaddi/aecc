package com.tierra.auth.mapper;

import java.util.*;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.tierra.auth.domain.Roles;
import com.tierra.auth.model.RoleModel;
import com.tierra.auth.model.RoleViewModel;

@Component
public class RoleMapper {
	
	public RoleViewModel convertDomainToViewModel (Roles roles) {
		RoleViewModel roleModel = new RoleViewModel();
		BeanUtils.copyProperties(roles, roleModel);
		return roleModel;
	}
	
	public Roles convertViewModelToDomain (RoleViewModel roleModel) {
		Roles roles = new Roles();
		BeanUtils.copyProperties(roleModel, roles);
		return roles;
	}
	
	public RoleModel convertDomainToModel (Roles roles) {
		RoleModel roleModel = new RoleModel();
		BeanUtils.copyProperties(roles, roleModel);
		return roleModel;
	}

	public Page<RoleModel> convertDomainToModel(Page<Roles> roles) {
        List<RoleModel> roleModels =  new ArrayList<>();
        for(Roles roleD : roles)
        {
        	  RoleModel roleM = new RoleModel();
              BeanUtils.copyProperties(roleD, roleM);
              roleModels.add(roleM);
        }
		
		
        return new PageImpl<>(roleModels, roles.getPageable(), roles.getTotalElements());
    }

	public Page<RoleModel> ConverDomainToModel(Page<Roles> role) {
        List<RoleModel> roleModel =  new ArrayList<>();
        for(Roles roleD : role)
        {
        	RoleModel roleM = new RoleModel();
              BeanUtils.copyProperties(roleD, roleM);
              roleModel.add(roleM);
        }
		
		
        return new PageImpl<>(roleModel, role.getPageable(), role.getTotalElements());
    }

}
