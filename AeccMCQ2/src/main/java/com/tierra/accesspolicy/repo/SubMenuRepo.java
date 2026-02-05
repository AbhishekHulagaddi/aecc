package com.tierra.accesspolicy.repo;


import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tierra.accesspolicy.domain.SubMenu;

public interface SubMenuRepo extends JpaRepository<SubMenu, UUID> , JpaSpecificationExecutor<SubMenu>{

	SubMenu findBySubMenuName(String subMenu);


}
