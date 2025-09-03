package com.rim.accesspolicy.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rim.accesspolicy.domain.Feature;
import com.rim.accesspolicy.domain.Menu;
import com.rim.accesspolicy.domain.Permission;
import com.rim.accesspolicy.domain.SubMenu;
import com.rim.auth.domain.Roles;


public interface PermissionRepo extends JpaRepository<Permission, UUID>, JpaSpecificationExecutor<Permission>{


	Permission findByMenusAndSubMenusAndFeaturesAndRoles(Menu menu, SubMenu subMenu,Feature features, Roles role);

}
