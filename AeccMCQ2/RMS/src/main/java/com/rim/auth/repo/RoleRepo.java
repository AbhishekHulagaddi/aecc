package com.rim.auth.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rim.auth.domain.Roles;

public interface RoleRepo extends JpaRepository<Roles, UUID>, JpaSpecificationExecutor<Roles>{

	Optional<Roles> findByRoleId(long id);
	

	Roles findByRoleName(String roleName);



}
