package com.rim.masterdata.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.utils.BaseResponse;
import com.rim.masterdata.model.PlanStatusModel;

public interface PlanStatusService {
	
	public BaseResponse createPlanStatus (PlanStatusModel planStatusModel);
	
	public BaseResponse updatePlanStatus (PlanStatusModel planStatusModel);
	
	public BaseResponse deletePlanStatus (PlanStatusModel planStatusModel);
	
	public PlanStatusModel findById (PlanStatusModel planStatusModel);
	
	public Page<PlanStatusModel> getAllPlanStatus (String planStatusName, Pageable pageable)throws Exception;


}
