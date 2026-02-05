package com.tierra.accesspolicy.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.tierra.accesspolicy.domain.SubMenu;
import com.tierra.accesspolicy.model.SubMenuModel;


@Component
public class SubMenuMapper {
	
	public SubMenuModel ConverDomainToModel (SubMenu subMenu) {
		SubMenuModel subMenuModel = new SubMenuModel();
		BeanUtils.copyProperties(subMenu,subMenuModel );
		return subMenuModel;
	}

	public SubMenu ConverModelToDomain (SubMenuModel subMenuModel) {
		SubMenu subMenu = new SubMenu();
		BeanUtils.copyProperties(subMenuModel,subMenu );
		return subMenu;
	}

	public Page<SubMenuModel> ConverDomainToModel(Page<SubMenu> subMenu) {
        List<SubMenuModel> subMenuModel =  new ArrayList<>();
        for(SubMenu subMenuD : subMenu)
        {
        	SubMenuModel subMenuM = new SubMenuModel();
              BeanUtils.copyProperties(subMenuD, subMenuM);
              subMenuModel.add(subMenuM);
        }
		
		
        return new PageImpl<>(subMenuModel, subMenu.getPageable(), subMenu.getTotalElements());
    }
	

}
