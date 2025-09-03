package com.rim.accesspolicy.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rim.accesspolicy.domain.Feature;
import com.rim.accesspolicy.mapper.FeatureMapper;
import com.rim.accesspolicy.model.FeatureModel;
import com.rim.accesspolicy.model.ViewAllAccessPolicyModel;
import com.rim.accesspolicy.repo.FeatureRepo;
import com.rim.accesspolicy.service.FeatureService;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;
import com.rim.auth.utils.UserIdPrinciple;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class FeatureServiceImpl implements FeatureService{
	
	@Autowired
	FeatureMapper featureMapper;
	
	@Autowired
	FeatureRepo featureRepo;
	
	@Autowired
	UserIdPrinciple principle;

	@Override
	public BaseResponse createFeature(FeatureModel featureModel) {
		Feature feature = featureMapper.ConverModelToDomain(featureModel);
		feature.setStatus(true);
		feature.setCreatedDate(LocalDateTime.now());
		feature.setCreatedBy(principle.getUserId());
		try {
		feature = featureRepo.save(feature);
		}catch(Exception e) {
			return new BaseResponse(feature.getFeatureName()+CustomMessage.SAVE_FAILED_MESSAGE,HttpStatus.BAD_REQUEST.value());

		}
		return new BaseResponse(feature.getFeatureName()+CustomMessage.SAVE_SUCCESS_MESSAGE,HttpStatus.CREATED.value());
	
	}

	@Override
	public BaseResponse updateFeature(FeatureModel featureModel) {
		Feature feature = new Feature();
		if(featureModel.getFeatureId()!=null)
			feature = featureRepo.findById(featureModel.getFeatureId())
			.orElseThrow(() -> new CusException(" Feature not found ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for Feature ",HttpStatus.NOT_FOUND);
		feature.setFeatureName(featureModel.getFeatureName());
		feature.setModifiedDate(LocalDateTime.now());
		feature.setModifiedBy(principle.getUserId());
		feature = featureRepo.save(feature);
		return new BaseResponse(feature.getFeatureName()+CustomMessage.UPDATE_SUCCESS_MESSAGE,HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteFeature(FeatureModel featureModel) {
		Feature feature = new Feature();
		if(featureModel.getFeatureId()!=null)
			feature = featureRepo.findById(featureModel.getFeatureId())
			.orElseThrow(() -> new CusException(" Feature not found ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for Feature ",HttpStatus.NOT_FOUND);
		feature.setStatus(false);
		feature.setModifiedDate(LocalDateTime.now());
		feature.setModifiedBy(principle.getUserId());
		featureRepo.save(feature);
		return new BaseResponse(feature.getFeatureName()+CustomMessage.DELETE_SUCCESS_MESSAGE,HttpStatus.OK.value());
	}
	
	@Override
	public FeatureModel findById ( FeatureModel featureModel) {
		Feature feature = new Feature();
		if(featureModel.getFeatureId()!=null)
			feature = featureRepo.findById(featureModel.getFeatureId())
			.orElseThrow(() -> new CusException(" Feature not found ",HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be null for Feature ",HttpStatus.NOT_FOUND);
		featureModel = featureMapper.ConverDomainToModel(feature);
		return featureModel;
	}


	@Override
	public Page<FeatureModel> getAllFeature(ViewAllAccessPolicyModel viewModel, Pageable pageable) throws Exception {
		Page<Feature> Feature = featureRepo.findAll(new Specification<Feature>() {

			@Override
			public Predicate toPredicate(Root<Feature> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (viewModel.getFeatureId() != null) {
					predicates.add(criteriaBuilder.and(
							criteriaBuilder.equal(root.get("featureId"), viewModel.getFeatureId())));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<FeatureModel> featureModel = featureMapper.ConverDomainToModel(Feature);
	    return featureModel;
	}


}
