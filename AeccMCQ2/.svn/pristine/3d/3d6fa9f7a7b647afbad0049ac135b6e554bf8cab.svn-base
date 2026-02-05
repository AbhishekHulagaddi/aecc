package com.rim.accesspolicy.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.accesspolicy.domain.Menu;
import com.rim.accesspolicy.domain.SubMenu;
import com.rim.accesspolicy.model.MenuModel;
import com.rim.accesspolicy.model.SubMenuModel;
import com.rim.accesspolicy.repo.SubMenuRepo;

@Component
public class MenuMapper {
	
	@Autowired
	SubMenuRepo subMenuRepo;
	
	public MenuModel ConverDomainToModel (Menu menu) {
		MenuModel menuModel = new MenuModel();
		BeanUtils.copyProperties(menu,menuModel );
		Set<SubMenuModel> subMenus = new HashSet<>();
		for(SubMenu subMenuD : menu.getSubMenu()) {
			SubMenuModel subMenuM = new SubMenuModel();
			subMenuM.setSubMenuId(subMenuD.getSubMenuId());
			subMenuM.setSubMenuName(subMenuD.getSubMenuName());
			subMenus.add(subMenuM);
		}
		menuModel.setSubMenu(subMenus);
		return menuModel;
	}

	public Menu ConverModelToDomain (MenuModel menuModel) {
		Menu menu = new Menu();
		Set<SubMenu> subMenus = new HashSet<>();
		BeanUtils.copyProperties(menuModel,menu );
		for(SubMenuModel subMenuM : menuModel.getSubMenu()) {
		SubMenu submenuD = subMenuRepo.findById(subMenuM.getSubMenuId()).get();
		subMenus.add(submenuD);
		}
		menu.setSubMenu(subMenus);
		return menu;
	}

	public Page<MenuModel> ConverDomainToModel(Page<Menu> menu) {
        List<MenuModel> menuModel =  new ArrayList<>();
        for(Menu menuD : menu)
        {
        	MenuModel menuM = new MenuModel();
              BeanUtils.copyProperties(menuD, menuM);
      		Set<SubMenuModel> subMenus = new HashSet<>();
    		for(SubMenu subMenuD : menuD.getSubMenu()) {
    			SubMenuModel subMenuM = new SubMenuModel();
    			subMenuM.setSubMenuId(subMenuD.getSubMenuId());
    			subMenuM.setSubMenuName(subMenuD.getSubMenuName());
    			subMenus.add(subMenuM);
    		}
    		menuM.setSubMenu(subMenus);
            menuModel.add(menuM);
        }
		
		
        return new PageImpl<>(menuModel, menu.getPageable(), menu.getTotalElements());
    }

}
