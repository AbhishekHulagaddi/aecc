package com.rim.masterdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.masterdata.domain.PlanStatus;
import com.rim.masterdata.model.PlanStatusModel;

@Component
public class PlanStatusMapper {
	
	public PlanStatus convertModelToDomain (PlanStatusModel planStatusModel) {
		PlanStatus planStatus = new PlanStatus();
		BeanUtils.copyProperties(planStatusModel, planStatus);
		return planStatus;
	}
	
	public PlanStatusModel convertDomainToModel (PlanStatus planStatus) {
		PlanStatusModel planStatusModel = new PlanStatusModel();
		BeanUtils.copyProperties(planStatus, planStatusModel);
		return planStatusModel;
	}

	public Page<PlanStatusModel> ConverDomainToModel(Page<PlanStatus> planStatus) {
			
	        List<PlanStatusModel> planStatusModel =  new ArrayList<>();
	        for(PlanStatus planStatusD : planStatus)
	        {
	        	PlanStatusModel PlanStatusM = new PlanStatusModel();
	              BeanUtils.copyProperties(planStatusD, PlanStatusM);
	              planStatusModel.add(PlanStatusM);
	        }
			
			
	        return new PageImpl<>(planStatusModel, planStatus.getPageable(), planStatus.getTotalElements());
	    }

}

