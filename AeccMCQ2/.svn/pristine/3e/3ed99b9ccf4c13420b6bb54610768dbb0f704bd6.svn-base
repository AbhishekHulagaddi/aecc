package com.rim.accesspolicy.repo;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.rim.accesspolicy.domain.Feature;

public interface FeatureRepo extends JpaRepository<Feature, UUID> , JpaSpecificationExecutor<Feature>{

	Feature findByFeatureName(String feature);

}
