package com.tierra.accesspolicy.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tierra.accesspolicy.domain.Feature;
import com.tierra.accesspolicy.domain.Menu;
import com.tierra.accesspolicy.domain.Permission;
import com.tierra.accesspolicy.domain.SubMenu;
import com.tierra.auth.domain.Roles;


public interface PermissionRepo extends JpaRepository<Permission, UUID>, JpaSpecificationExecutor<Permission>{


	Permission findByMenusAndSubMenusAndFeaturesAndRoles(Menu menu, SubMenu subMenu,Feature features, Roles role);

}
