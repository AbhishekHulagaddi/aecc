package com.tierra.accesspolicy.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.accesspolicy.model.FeatureModel;
import com.tierra.accesspolicy.model.ViewAllAccessPolicyModel;
import com.tierra.auth.utils.BaseResponse;

public interface FeatureService {
	
	public BaseResponse createFeature ( FeatureModel featureModel);
	
	public BaseResponse updateFeature ( FeatureModel featureModel);
	
	public BaseResponse deleteFeature ( FeatureModel featureModel);
	
	public FeatureModel findById ( FeatureModel featureModel);
	
	public Page<FeatureModel> getAllFeature (ViewAllAccessPolicyModel viewModel,Pageable pageable)throws Exception;


}
