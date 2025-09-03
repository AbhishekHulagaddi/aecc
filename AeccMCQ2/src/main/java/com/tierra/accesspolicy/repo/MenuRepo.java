package com.tierra.accesspolicy.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tierra.accesspolicy.domain.Menu;

public interface MenuRepo extends JpaRepository<Menu, UUID>, JpaSpecificationExecutor<Menu>{

	Menu findByMenuName(String menu);

}
