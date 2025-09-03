package com.rim.masterdata.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.utils.BaseResponse;
import com.rim.masterdata.model.QualityModel;

public interface QualityService {
	
	public BaseResponse createQuality (QualityModel qualityModel);
	
	public BaseResponse updateQuality (QualityModel qualityModel);
	
	public BaseResponse deleteQuality (QualityModel qualityModel);
	
	public QualityModel findById (QualityModel qualityModel);
	
	public Page<QualityModel> getAllQuality (String qualityName, Pageable pageable)throws Exception;

}
