package com.rim.accesspolicy.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.accesspolicy.domain.Permission;
import com.rim.accesspolicy.model.PermissionModel;
import com.rim.accesspolicy.repo.FeatureRepo;
import com.rim.accesspolicy.repo.MenuRepo;
import com.rim.accesspolicy.repo.SubMenuRepo;
import com.rim.auth.repo.RoleRepo;

@Component
public class PermissionMapper {
	
	@Autowired
	MenuRepo menuRepo;
	@Autowired
	SubMenuRepo subMenuRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	FeatureRepo featureRepo;
	
	public PermissionModel ConverDomainToModel (Permission permission) {
		PermissionModel permissionModel = new PermissionModel();
		permissionModel = setMenuSubMenuFeatureRoles(permission);
		BeanUtils.copyProperties(permission,permissionModel );
		return permissionModel;
	}

	public Permission ConverModelToDomain (PermissionModel permissionModel) {
		Permission permission = new Permission();
		BeanUtils.copyProperties(permissionModel,permission );
		permission.setMenus(menuRepo.findById(permissionModel.getMenuId()).get());
		permission.setSubMenus(subMenuRepo.findById(permissionModel.getSubMenuId()).get());
		permission.setRoles(roleRepo.findById(permissionModel.getRoleId()).get());
		permission.setFeatures(featureRepo.findById(permissionModel.getFeatureId()).get());
		return permission;
	}
	public Page<PermissionModel> ConverDomainToModel(Page<Permission> permission) {
        List<PermissionModel> permissionModel =  new ArrayList<>();
        for(Permission permissionD : permission)
        {
        	PermissionModel permissionM = new PermissionModel();
              BeanUtils.copyProperties(permissionD, permissionM);
              permissionM = setMenuSubMenuFeatureRoles(permissionD);
              BeanUtils.copyProperties(permissionD, permissionM);
              permissionModel.add(permissionM);
        }
		
		
        return new PageImpl<>(permissionModel, permission.getPageable(), permission.getTotalElements());
    }
	
	public PermissionModel setMenuSubMenuFeatureRoles(Permission permission) {
		PermissionModel permissionModel = new PermissionModel();
		permissionModel.setMenuId(permission.getMenus().getMenuId());
		permissionModel.setMenuName(permission.getMenus().getMenuName());
		permissionModel.setSubMenuId(permission.getSubMenus().getSubMenuId());
		permissionModel.setSubMenuName(permission.getSubMenus().getSubMenuName());
		permissionModel.setRoleId(permission.getRoles().getRoleId());
		permissionModel.setRoleName(permission.getRoles().getRoleName());
		permissionModel.setFeatureId(permission.getFeatures().getFeatureId());
		permissionModel.setFeatureName(permission.getFeatures().getFeatureName());
		return permissionModel;
		
	}

}
