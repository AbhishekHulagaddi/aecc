package com.rim.accesspolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.accesspolicy.model.FeatureModel;
import com.rim.accesspolicy.model.ViewAllAccessPolicyModel;
import com.rim.auth.utils.BaseResponse;

public interface FeatureService {
	
	public BaseResponse createFeature ( FeatureModel featureModel);
	
	public BaseResponse updateFeature ( FeatureModel featureModel);
	
	public BaseResponse deleteFeature ( FeatureModel featureModel);
	
	public FeatureModel findById ( FeatureModel featureModel);
	
	public Page<FeatureModel> getAllFeature (ViewAllAccessPolicyModel viewModel,Pageable pageable)throws Exception;


}
