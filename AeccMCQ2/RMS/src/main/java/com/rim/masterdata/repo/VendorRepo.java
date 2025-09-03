package com.rim.masterdata.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rim.masterdata.domain.Vendor;

public interface VendorRepo extends JpaRepository<Vendor, UUID>,JpaSpecificationExecutor<Vendor>{

}
