package com.rim.masterdata.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rim.masterdata.domain.PlanStatus;

public interface PlanStatusRepo extends JpaRepository<PlanStatus, UUID>, JpaSpecificationExecutor<PlanStatus>{

}
