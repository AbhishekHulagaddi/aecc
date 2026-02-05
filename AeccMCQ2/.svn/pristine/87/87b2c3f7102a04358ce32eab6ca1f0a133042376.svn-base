package com.rim.accesspolicy.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rim.accesspolicy.domain.Feature;
import com.rim.accesspolicy.domain.Menu;
import com.rim.accesspolicy.domain.Permission;
import com.rim.accesspolicy.domain.SubMenu;
import com.rim.accesspolicy.mapper.PermissionMapper;
import com.rim.accesspolicy.model.PermissionAccessModel;
import com.rim.accesspolicy.model.PermissionModel;
import com.rim.accesspolicy.model.ViewAllAccessPolicyModel;
import com.rim.accesspolicy.repo.FeatureRepo;
import com.rim.accesspolicy.repo.MenuRepo;
import com.rim.accesspolicy.repo.PermissionRepo;
import com.rim.accesspolicy.repo.SubMenuRepo;
import com.rim.accesspolicy.service.PermissionService;
import com.rim.auth.domain.Roles;
import com.rim.auth.repo.RoleRepo;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;
import com.rim.auth.utils.UserIdPrinciple;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;


@Service
public class PermissionServiceImpl implements PermissionService{
	
	@Autowired
	PermissionMapper permissionMapper;
	@Autowired
	PermissionRepo permissionRepo;
	@Autowired
	MenuRepo menuRepo;
	@Autowired
	SubMenuRepo subMenuRepo;
	@Autowired
	FeatureRepo featureRepo;
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	UserIdPrinciple principle;

	@Override
	public BaseResponse createPermission(PermissionModel permissionModel) {
		Permission permission = permissionMapper.ConverModelToDomain(permissionModel);
		permission.setStatus(true);
		permission.setCreatedDate(LocalDateTime.now());
		permission.setCreatedBy(principle.getUserId());
		try {
		permission = permissionRepo.save(permission);
		}catch(Exception e) {
			return new BaseResponse(permission.getPermissionName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
			
		}
		return new BaseResponse(permission.getPermissionName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updatePermission(PermissionModel permissionModel) {
		Permission permission = new Permission();
		if(permissionModel.getPermissionId()!=null)
			permission = permissionRepo.findById(permissionModel.getPermissionId())
					.orElseThrow(() -> new CusException(" Permission not found for the given Id ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot be null For Permission ",HttpStatus.NOT_FOUND);				
		permission.setEnable(permissionModel.isEnable());
		permission.setPermissionName(permissionModel.getPermissionName());
		permission.setModifiedDate(LocalDateTime.now());
		permission.setModifiedBy(principle.getUserId());
		permission = permissionRepo.save(permission);
		return new BaseResponse(permission.getPermissionName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deletePermission(PermissionModel permissionModel) {
		Permission permission = new Permission();
		if(permissionModel.getPermissionId()!=null)
			permission = permissionRepo.findById(permissionModel.getPermissionId())
					.orElseThrow(() -> new CusException(" Permission not found for the given Id ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot be null For Permission ",HttpStatus.NOT_FOUND);		
		permission.setStatus(false);
		permission.setModifiedDate(LocalDateTime.now());
		permission.setModifiedBy(principle.getUserId());
		permission.setEnable(false);
//		permissionRepo.deleteById(permissionModel.getPermissionId());
		//permissionRepo.deleteAll();
		permissionRepo.save(permission);
		return new BaseResponse(permission.getPermissionName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}
	
	@Override
	public PermissionModel findById ( PermissionModel permissionModel) {
		Permission permission = new Permission();
		if(permissionModel.getPermissionId()!=null)
			permission = permissionRepo.findById(permissionModel.getPermissionId())
					.orElseThrow(() -> new CusException(" Permission not found for the given Id ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot be null For Permission ",HttpStatus.NOT_FOUND);	
		permissionModel = permissionMapper.ConverDomainToModel(permission);
		return permissionModel;
	}

	@Override
	public Page<PermissionModel> getAllPermission(ViewAllAccessPolicyModel viewModel, Pageable pageable) throws Exception {
		Page<Permission> permission = permissionRepo.findAll(new Specification<Permission>() {

			@Override
			public Predicate toPredicate(Root<Permission> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

//				if (menuName != null) {
//					predicates.add(
//							criteriaBuilder.and(criteriaBuilder.like(root.get("MenuName"), "%" + menuName + "%")));
//
//				}
				if (viewModel.getPermissionId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("permissionId"), viewModel.getPermissionId())));
				}
				if (viewModel.getRoleId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("roles"), viewModel.getRoleId())));
				}
				if (viewModel.getFeatureId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("features"), viewModel.getFeatureId())));
				}
				if (viewModel.getMenuId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("menus"), viewModel.getMenuId())));
				}
				if (viewModel.getSubMenuId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("subMenus"), viewModel.getSubMenuId())));
				}
	


				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<PermissionModel> permissionModel = permissionMapper.ConverDomainToModel(permission);
	    return permissionModel;
	}

	@Override
	public PermissionModel getPermissionByMenuAndSubMenuAndFeature(PermissionAccessModel permissionAccessModel) {
		Menu menuD = menuRepo.findByMenuName(permissionAccessModel.getMenu());
		SubMenu subMenuD = subMenuRepo.findBySubMenuName(permissionAccessModel.getSubMenu());
		Roles roleD = roleRepo.findByRoleName(permissionAccessModel.getRole());
		Feature featureD = featureRepo.findByFeatureName(permissionAccessModel.getFeature());
		Permission permission = permissionRepo.findByMenusAndSubMenusAndFeaturesAndRoles(menuD,subMenuD,featureD,roleD);
		if(permission==null) {
		return null;
		}
		PermissionModel permissionModel = permissionMapper.ConverDomainToModel(permission);
		return permissionModel;
	}

}
