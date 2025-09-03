package com.tierra.masterdata.serviceimpl;

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

import com.tierra.auth.domain.User;
import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.UserIdPrinciple;
import com.tierra.masterdata.domain.Subject;
import com.tierra.masterdata.mapper.SubjectMapper;
import com.tierra.masterdata.model.SubjectModel;
import com.tierra.masterdata.repo.SubjectRepo;
import com.tierra.masterdata.service.SubjectService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class SubjectServiceImpl implements SubjectService{


	
	@Autowired
	SubjectMapper subjectMapper;
	
	@Autowired
	SubjectRepo subjectRepo;
	
	@Autowired
	UserIdPrinciple principle;
	

	@Override
	public BaseResponse createSubject(SubjectModel subjectModel) {
		Subject subject = subjectMapper.convertModelToDomain(subjectModel);
		subject.setStatus(true);
		subject.setCreatedDate(LocalDateTime.now());
		subject.setCreatedBy(principle.getUserId());
		try {
		subject = subjectRepo.save(subject);
		}catch (Exception e) {
			return new BaseResponse(subject.getSubjectName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(subject.getSubjectName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateSubject(SubjectModel subjectModel) {
		Subject subject = new Subject();
		if(subjectModel.getSubjectId()!=null) 
				subject = subjectRepo.findById(subjectModel.getSubjectId())
						.orElseThrow(() -> new CusException(" Subject Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Subject ", HttpStatus.NOT_FOUND);
		subject.setSubjectName(subjectModel.getSubjectName());
		subject.setModifiedDate(LocalDateTime.now());
		subject.setModifiedBy(principle.getUserId());
		subject = subjectRepo.save(subject);
		return new BaseResponse(subject.getSubjectName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteSubject(SubjectModel subjectModel) {
		Subject subject = new Subject();
		if(subjectModel.getSubjectId()!=null) 
				subject = subjectRepo.findById(subjectModel.getSubjectId())
						.orElseThrow(() -> new CusException(" Subject Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Subject ", HttpStatus.NOT_FOUND);
		subject.setStatus(false);
		subject.setModifiedDate(LocalDateTime.now());
		subject.setModifiedBy(principle.getUserId());
		subjectRepo.save(subject);
		return new BaseResponse(subject.getSubjectName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public SubjectModel findById(SubjectModel subjectModel) {
		Subject subject = new Subject();
		if(subjectModel.getSubjectId()!=null) 
				subject = subjectRepo.findById(subjectModel.getSubjectId())
						.orElseThrow(() -> new CusException(" Subject Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Subject ", HttpStatus.NOT_FOUND);
		subjectModel = subjectMapper.convertDomainToModel(subject);
		return subjectModel;
	}

	@Override
	public Page<SubjectModel> getAllSubject (String subjectName, Pageable pageable)throws Exception{
		Page<Subject> subject = subjectRepo.findAll(new Specification<Subject>() {

			@Override
			public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (subjectName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("SubjectName"), "%" + subjectName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    return subjectMapper.ConverDomainToModel(subject);
	}
	


}

