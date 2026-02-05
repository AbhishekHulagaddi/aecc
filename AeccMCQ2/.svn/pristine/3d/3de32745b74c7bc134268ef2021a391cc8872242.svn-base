package com.rim.accesspolicy.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rim.accesspolicy.domain.SubMenu;
import com.rim.accesspolicy.mapper.SubMenuMapper;
import com.rim.accesspolicy.model.SubMenuModel;
import com.rim.accesspolicy.model.ViewAllAccessPolicyModel;
import com.rim.accesspolicy.repo.SubMenuRepo;
import com.rim.accesspolicy.service.SubMenuService;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;
import com.rim.auth.utils.UserIdPrinciple;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class SubMenuServiceImpl implements SubMenuService{
	
	@Autowired
	SubMenuMapper subMenuMapper;
	
	@Autowired
	SubMenuRepo subMenuRepo;
	
	@Autowired
	UserIdPrinciple principle;

	@Override
	public BaseResponse createSubMenu(SubMenuModel subMenuModel) {
		SubMenu subMenu = subMenuMapper.ConverModelToDomain(subMenuModel);
		subMenu.setStatus(true);
		subMenu.setCreatedDate(LocalDateTime.now());
		subMenu.setCreatedBy(principle.getUserId());
		try {
		subMenu = subMenuRepo.save(subMenu);
		}catch(Exception e) {
			return new BaseResponse(subMenu.getSubMenuName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(subMenu.getSubMenuName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateSubMenu(SubMenuModel subMenuModel) {
		SubMenu subMenu = new SubMenu();
		if(subMenuModel.getSubMenuId()!=null)
			subMenu = subMenuRepo.findById(subMenuModel.getSubMenuId())
			.orElseThrow(() -> new CusException(" SubMenu not found ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for SubMenu ",HttpStatus.NOT_FOUND);
		subMenu.setSubMenuName(subMenuModel.getSubMenuName());
		subMenu.setModifiedDate(LocalDateTime.now());
		subMenu.setModifiedBy(principle.getUserId());
		subMenu = subMenuRepo.save(subMenu);
		return new BaseResponse(subMenu.getSubMenuName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteSubMenu(SubMenuModel subMenuModel) {
		SubMenu subMenu = new SubMenu();
		if(subMenuModel.getSubMenuId()!=null)
			subMenu = subMenuRepo.findById(subMenuModel.getSubMenuId())
			.orElseThrow(() -> new CusException(" SubMenu not found ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for SubMenu ",HttpStatus.NOT_FOUND);
		subMenu.setStatus(false);
		subMenu.setModifiedDate(LocalDateTime.now());
		subMenu.setModifiedBy(principle.getUserId());
		subMenuRepo.save(subMenu);
		return new BaseResponse(subMenu.getSubMenuName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}
	
	@Override
	public SubMenuModel findById ( SubMenuModel subMenuModel) {
		SubMenu subMenu = new SubMenu();
		if(subMenuModel.getSubMenuId()!=null)
			subMenu = subMenuRepo.findById(subMenuModel.getSubMenuId())
			.orElseThrow(() -> new CusException(" SubMenu not found ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for SubMenu ",HttpStatus.NOT_FOUND);
		subMenuModel = subMenuMapper.ConverDomainToModel(subMenu);
		return subMenuModel;
	}


	@Override
	public Page<SubMenuModel> getAllSubMenu(ViewAllAccessPolicyModel viewModel, Pageable pageable) throws Exception {
		Page<SubMenu> subMenu = subMenuRepo.findAll(new Specification<SubMenu>() {

			@Override
			public Predicate toPredicate(Root<SubMenu> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (viewModel.getSubMenuId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("subMenuId"), viewModel.getSubMenuId())));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<SubMenuModel> subMenuModel = subMenuMapper.ConverDomainToModel(subMenu);
	    return subMenuModel;
	}

}
