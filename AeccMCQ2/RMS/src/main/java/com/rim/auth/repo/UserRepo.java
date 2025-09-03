package com.rim.auth.repo;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rim.auth.domain.User;

public interface UserRepo extends JpaRepository<User, UUID>, JpaSpecificationExecutor<User>{

	Optional<User> findByUsercode(long usercode);

	Optional<User> findByUserName(String userName);

	  Boolean existsByUserName(String username);

	  Boolean existsByMail(String email);
	

}
