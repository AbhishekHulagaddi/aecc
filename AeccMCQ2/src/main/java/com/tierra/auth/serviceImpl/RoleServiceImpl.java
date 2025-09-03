package com.tierra.auth.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tierra.auth.domain.Roles;
import com.tierra.auth.mapper.RoleMapper;
import com.tierra.auth.model.RoleModel;
import com.tierra.auth.model.RoleViewModel;
import com.tierra.auth.repo.RoleRepo;
import com.tierra.auth.service.RoleService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.UserIdPrinciple;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class RoleServiceImpl implements RoleService{
	
	@Autowired
	RoleRepo roleRepo;
	
	@Autowired
	RoleMapper roleMapper;
	
	@Autowired
	UserIdPrinciple principle;

	@Override
	public BaseResponse saveRole(RoleViewModel roleModel) throws Exception {
		Roles roles = roleMapper.convertViewModelToDomain(roleModel);
		roles.setCreatedDate(LocalDateTime.now());
		roles.setCreatedBy(principle.getUserId());
		roles.setStatus(true);
		try {
		roles = roleRepo.save(roles);
		}
		catch(Exception e)
		{
			return new BaseResponse(roles.getRoleName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(roles.getRoleName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateRole(RoleModel roleModel) {
		Roles roles;
		if (roleModel.getRoleId() != null)
			roles = roleRepo.findById(roleModel.getRoleId())
					.orElseThrow(() -> new CusException("Role not found for given Id",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for Role ",HttpStatus.NOT_FOUND);
		roles.setModifiedDate(LocalDateTime.now());
		roles.setModifiedBy(principle.getUserId());
		roles.setStatus(true);
		roles.setDiscription(roleModel.getDiscription());
		roles.setRoleName(roleModel.getRoleName());
		roles = roleRepo.save(roles);
		roleModel = roleMapper.convertDomainToModel(roles);
		return new BaseResponse(roles.getRoleName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	
	}

	@Override
	public RoleModel getRoleByRoleId(UUID roleId) throws Exception {
		Roles roles;
		if(roleId!=null)
			roles = roleRepo.findById(roleId)
					.orElseThrow(() -> new CusException("Role not found for given RoleId",HttpStatus.NOT_FOUND));
        else
        	throw new CusException(" RoleId cannot be null for Role ",HttpStatus.NOT_FOUND);
       RoleModel roleModel = roleMapper.convertDomainToModel(roles);
		return roleModel;
	}

	@Override
	public BaseResponse deleteRole(RoleModel roleModel) throws Exception {
		Roles roles;
		if (roleModel.getRoleId() != null)
			roles = roleRepo.findById(roleModel.getRoleId())
					.orElseThrow(() -> new CusException("Role not found for given Id",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for Role ",HttpStatus.NOT_FOUND);
		roles.setStatus(false);
		roles.setModifiedDate(LocalDateTime.now());
		roles.setModifiedBy(principle.getUserId());
		roles = roleRepo.save(roles);
		return new BaseResponse(roles.getRoleName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());

		
	}

	@Override
	public Page<RoleModel> getAllRoles(String roleName,Pageable pageable) throws Exception {
		Page<Roles> role = roleRepo.findAll(new Specification<Roles>() {

			@Override
			public Predicate toPredicate(Root<Roles> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (roleName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("roleName"), "%" + roleName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<RoleModel> roleModel = roleMapper.ConverDomainToModel(role);
	    return roleModel;
	}
	



}
