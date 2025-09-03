package com.rim.masterdata.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.rim.auth.domain.User;
import com.rim.auth.repo.UserRepo;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;
import com.rim.auth.utils.UserIdPrinciple;
import com.rim.masterdata.domain.PlanStatus;
import com.rim.masterdata.mapper.PlanStatusMapper;
import com.rim.masterdata.model.PlanStatusModel;
import com.rim.masterdata.repo.PlanStatusRepo;
import com.rim.masterdata.service.PlanStatusService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class PlanStatusServiceImpl implements PlanStatusService{

	
	@Autowired
	PlanStatusMapper planStatusMapper;
	
	@Autowired
	PlanStatusRepo planStatusRepo;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Override
	public BaseResponse createPlanStatus(PlanStatusModel planStatusModel) {
		PlanStatus planStatus = planStatusMapper.convertModelToDomain(planStatusModel);
		planStatus.setStatus(true);  
		planStatus.setCreatedDate(LocalDateTime.now());
		planStatus.setCreatedBy(principle.getUserId());
		try {
		planStatus = planStatusRepo.save(planStatus);
		}catch(Exception e) {
			return new BaseResponse(planStatus.getPlanStatusName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(planStatus.getPlanStatusName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updatePlanStatus(PlanStatusModel planStatusModel) {
		PlanStatus planStatus = new PlanStatus();
		if(planStatusModel.getPlanStatusId()!=null)
		    planStatus = planStatusRepo.findById(planStatusModel.getPlanStatusId())
		    			.orElseThrow(() -> new CusException(" PlanStatus Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for PlanStatus ", HttpStatus.NOT_FOUND);
		planStatus.setPlanStatusName(planStatusModel.getPlanStatusName());
		planStatus.setModifiedDate(LocalDateTime.now());
		planStatus.setModifiedBy(principle.getUserId());
		planStatus = planStatusRepo.save(planStatus);
		return new BaseResponse(planStatus.getPlanStatusName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public BaseResponse deletePlanStatus(PlanStatusModel planStatusModel) {
		PlanStatus planStatus = new PlanStatus();
		if(planStatusModel.getPlanStatusId()!=null)
		    planStatus = planStatusRepo.findById(planStatusModel.getPlanStatusId())
		    			.orElseThrow(() -> new CusException(" PlanStatus Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for PlanStatus ", HttpStatus.NOT_FOUND);
		planStatus.setStatus(false);
		planStatus.setModifiedDate(LocalDateTime.now());
		planStatus.setModifiedBy(principle.getUserId());
		planStatusRepo.save(planStatus);
		return new BaseResponse(planStatus.getPlanStatusName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public PlanStatusModel findById(PlanStatusModel planStatusModel) {
		PlanStatus planStatus = new PlanStatus();
		if(planStatusModel.getPlanStatusId()!=null)
		    planStatus = planStatusRepo.findById(planStatusModel.getPlanStatusId())
		    			.orElseThrow(() -> new CusException(" PlanStatus Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for PlanStatus ", HttpStatus.NOT_FOUND);
		planStatusModel = planStatusMapper.convertDomainToModel(planStatus);
		return planStatusModel;
	}

	@Override
	public Page<PlanStatusModel> getAllPlanStatus (String planStatusName, Pageable pageable)throws Exception{
		Page<PlanStatus> planStatus = planStatusRepo.findAll(new Specification<PlanStatus>() {

			@Override
			public Predicate toPredicate(Root<PlanStatus> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (planStatusName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("planStatusName"), "%" + planStatusName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<PlanStatusModel> planStatusModel = planStatusMapper.ConverDomainToModel(planStatus);
	    return planStatusModel;
	}
	


}
