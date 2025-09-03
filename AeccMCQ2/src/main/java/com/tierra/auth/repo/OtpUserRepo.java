package com.tierra.auth.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tierra.auth.domain.OtpUser;

public interface OtpUserRepo extends JpaRepository<OtpUser, UUID>{

	OtpUser findByMail(String mail);

}
