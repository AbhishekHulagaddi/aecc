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
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;
import com.rim.auth.utils.UserIdPrinciple;
import com.rim.masterdata.domain.Quality;
import com.rim.masterdata.mapper.QualityMapper;
import com.rim.masterdata.model.QualityModel;
import com.rim.masterdata.repo.QualityRepo;
import com.rim.masterdata.service.QualityService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Service
public class QualityServiceImpl implements QualityService{

	
	@Autowired
	QualityMapper qualityMapper;
	
	@Autowired
	QualityRepo qualityRepo;
	
	@Autowired
	UserIdPrinciple principle;
	

	@Override
	public BaseResponse createQuality(QualityModel qualityModel) {
		Quality quality = qualityMapper.convertModelToDomain(qualityModel);
		quality.setStatus(true);
		quality.setCreatedDate(LocalDateTime.now());
		quality.setCreatedBy(principle.getUserId());
		try {
		quality = qualityRepo.save(quality);
		}catch (Exception e) {
			return new BaseResponse(quality.getQualityName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(quality.getQualityName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateQuality(QualityModel qualityModel) {
		Quality quality = new Quality();
		if(qualityModel.getQualityId()!=null)
			quality = qualityRepo.findById(qualityModel.getQualityId())
					.orElseThrow(() -> new CusException(" Quality Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id not found for Quality ", HttpStatus.NOT_FOUND);
		quality.setQualityName(qualityModel.getQualityName());
		quality.setModifiedDate(LocalDateTime.now());
		quality.setModifiedBy(principle.getUserId());
		quality = qualityRepo.save(quality);
		return new BaseResponse(quality.getQualityName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteQuality(QualityModel qualityModel) {
		Quality quality = new Quality();
		if(qualityModel.getQualityId()!=null)
			quality = qualityRepo.findById(qualityModel.getQualityId())
					.orElseThrow(() -> new CusException(" Quality Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id not found for Quality ", HttpStatus.NOT_FOUND);
		quality.setStatus(false);
		quality.setModifiedDate(LocalDateTime.now());
		quality.setModifiedBy(principle.getUserId());
		qualityRepo.save(quality);
		return new BaseResponse(quality.getQualityName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public QualityModel findById(QualityModel qualityModel) {
		Quality quality = new Quality();
		if(qualityModel.getQualityId()!=null)
			quality = qualityRepo.findById(qualityModel.getQualityId())
					.orElseThrow(() -> new CusException(" Quality Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id not found for Quality ", HttpStatus.NOT_FOUND);
		qualityModel = qualityMapper.convertDomainToModel(quality);
		return qualityModel;
	}

	@Override
	public Page<QualityModel> getAllQuality (String qualityName, Pageable pageable)throws Exception{
		Page<Quality> quality = qualityRepo.findAll(new Specification<Quality>() {

			@Override
			public Predicate toPredicate(Root<Quality> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (qualityName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("qualityName"), "%" + qualityName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<QualityModel> qualityModel = qualityMapper.ConverDomainToModel(quality);
	    return qualityModel;
	}
	


}
