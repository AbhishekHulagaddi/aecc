package com.tierra.accesspolicy.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tierra.accesspolicy.domain.Menu;
import com.tierra.accesspolicy.domain.SubMenu;
import com.tierra.accesspolicy.mapper.MenuMapper;
import com.tierra.accesspolicy.model.MenuModel;
import com.tierra.accesspolicy.model.SubMenuModel;
import com.tierra.accesspolicy.model.ViewAllAccessPolicyModel;
import com.tierra.accesspolicy.repo.MenuRepo;
import com.tierra.accesspolicy.repo.SubMenuRepo;
import com.tierra.accesspolicy.service.MenuService;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.UserIdPrinciple;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class MenuServiceImpl implements MenuService{
	
	@Autowired
	MenuMapper menuMapper;
	
	@Autowired
	MenuRepo menuRepo;
	
	@Autowired
	SubMenuRepo subMenuRepo;
	
	@Autowired
	UserIdPrinciple principle;

	@Override
	public BaseResponse createMenu(MenuModel menuModel) {
		Menu menu = menuMapper.ConverModelToDomain(menuModel);
		menu.setStatus(true);
		menu.setCreatedDate(LocalDateTime.now());
		menu.setCreatedBy(principle.getUserId());
		try {
		menu = menuRepo.save(menu);
		}catch(Exception e){
			return new BaseResponse(menu.getMenuName() + CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());

		}
		return new BaseResponse(menu.getMenuName() + CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateMenu(MenuModel menuModel) {
		Menu menu = new Menu();
		if(menuModel.getMenuId()!=null)
				menu = menuRepo.findById(menuModel.getMenuId())
					.orElseThrow(() -> new CusException(" Menu not found for the given Id ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot be null For Menu ",HttpStatus.NOT_FOUND);
		menu.setMenuName(menuModel.getMenuName());
		Set<SubMenu> subMenus = new HashSet<>();
		for(SubMenuModel subMenuM : menuModel.getSubMenu()) {
			SubMenu subMenuD = new SubMenu();
			subMenuD = subMenuRepo.findById(subMenuM.getSubMenuId()).get();
			subMenus.add(subMenuD);
		}
		menu.setSubMenu(subMenus);
		menu.setModifiedDate(LocalDateTime.now());
		menu.setModifiedBy(principle.getUserId());
		menu = menuRepo.save(menu);
		return new BaseResponse(menu.getMenuName() + CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteMenu(MenuModel menuModel) {
		Menu menu = new Menu();
		if(menuModel.getMenuId()!=null)
			menu = menuRepo.findById(menuModel.getMenuId())
				.orElseThrow(() -> new CusException(" Menu not found for the given Id ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot be null For Menu ",HttpStatus.NOT_FOUND);
		menu.setStatus(false);
		menu.setModifiedDate(LocalDateTime.now());
		menu.setModifiedBy(principle.getUserId());
		menuRepo.save(menu);
		menuRepo.deleteById(menuModel.getMenuId());
		return new BaseResponse(menu.getMenuName() + CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());


	}
	
	@Override
	public MenuModel findById (MenuModel menuModel) {
		Menu menu = new Menu();
		if(menuModel.getMenuId()!=null)
			menu = menuRepo.findById(menuModel.getMenuId())
				.orElseThrow(() -> new CusException(" Menu not found for the given Id ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot be null For Menu ",HttpStatus.NOT_FOUND);
		menuModel = menuMapper.ConverDomainToModel(menu);
		return menuModel;
	}


	@Override
	public Page<MenuModel> getAllMenu(ViewAllAccessPolicyModel viewModel, Pageable pageable) throws Exception {
		Page<Menu> menu = menuRepo.findAll(new Specification<Menu>() {

			@Override
			public Predicate toPredicate(Root<Menu> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (viewModel.getMenuId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("menuId"), viewModel.getMenuId())));
				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<MenuModel> menuModel = menuMapper.ConverDomainToModel(menu);
	    return menuModel;
	}



}
