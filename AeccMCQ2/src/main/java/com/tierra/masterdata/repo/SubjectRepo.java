package com.tierra.masterdata.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tierra.masterdata.domain.Subject;

public interface SubjectRepo extends JpaRepository<Subject, UUID>,JpaSpecificationExecutor<Subject>{

}
