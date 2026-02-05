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
import com.rim.masterdata.domain.StorageLocation;
import com.rim.masterdata.mapper.StorageLocationMapper;
import com.rim.masterdata.model.StorageLocationModel;
import com.rim.masterdata.repo.StorageLocationRepo;
import com.rim.masterdata.service.StorageLocationService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class StorageLocationServiceImpl implements StorageLocationService{

	@Autowired
	StorageLocationMapper storageLocationMapper;
	
	@Autowired
	StorageLocationRepo storageLocationRepo;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Override
	public BaseResponse createStorageLocation(StorageLocationModel storageLocationModel) {
		StorageLocation storageLocation = storageLocationMapper.convertModelToDomain(storageLocationModel);
		storageLocation.setStatus(true);  
		storageLocation.setCreatedDate(LocalDateTime.now());
		storageLocation.setCreatedBy(principle.getUserId());
		try {
		storageLocation = storageLocationRepo.save(storageLocation);
		}catch (Exception e) {
			return new BaseResponse(storageLocation.getStorageLocationName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(storageLocation.getStorageLocationName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateStorageLocation(StorageLocationModel storageLocationModel) {
		StorageLocation storageLocation = new StorageLocation();
		if(storageLocationModel.getStorageLocationId()!=null)
				storageLocation = storageLocationRepo.findById(storageLocationModel.getStorageLocationId())
						.orElseThrow(() -> new CusException(" Storage Location Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot Be Null for Storage Location ", HttpStatus.NOT_FOUND);
		storageLocation.setStorageLocationName(storageLocationModel.getStorageLocationName());
		storageLocation.setModifiedDate(LocalDateTime.now());
		storageLocation.setModifiedBy(principle.getUserId());
		storageLocation.setHeight(storageLocationModel.getHeight());
		storageLocation.setLength(storageLocationModel.getLength());
		storageLocation.setWidth(storageLocationModel.getWidth());
		storageLocation.setQuantity(storageLocationModel.getQuantity());
		storageLocation = storageLocationRepo.save(storageLocation);
		return new BaseResponse(storageLocation.getStorageLocationName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteStorageLocation(StorageLocationModel storageLocationModel) {
		StorageLocation storageLocation = new StorageLocation();
		if(storageLocationModel.getStorageLocationId()!=null)
				storageLocation = storageLocationRepo.findById(storageLocationModel.getStorageLocationId())
						.orElseThrow(() -> new CusException(" Storage Location Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot Be Null for Storage Location ", HttpStatus.NOT_FOUND);
		storageLocation.setStatus(false);
		storageLocation.setModifiedDate(LocalDateTime.now());
		storageLocation.setModifiedBy(principle.getUserId());
		storageLocationRepo.save(storageLocation);
		return new BaseResponse(storageLocation.getStorageLocationName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public StorageLocationModel findById(StorageLocationModel storageLocationModel) {
		StorageLocation storageLocation = new StorageLocation();
		if(storageLocationModel.getStorageLocationId()!=null)
				storageLocation = storageLocationRepo.findById(storageLocationModel.getStorageLocationId())
						.orElseThrow(() -> new CusException(" Storage Location Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id Cannot Be Null for Storage Location ", HttpStatus.NOT_FOUND);
		storageLocationModel = storageLocationMapper.convertDomainToModel(storageLocation);
		return storageLocationModel;
	}

	@Override
	public Page<StorageLocationModel> getAllStorageLocation (String storageLocationName, Pageable pageable)throws Exception{
		Page<StorageLocation> storageLocation = storageLocationRepo.findAll(new Specification<StorageLocation>() {

			@Override
			public Predicate toPredicate(Root<StorageLocation> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (storageLocationName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("StorageLocationName"), "%" + storageLocationName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<StorageLocationModel> storageLocationModel = storageLocationMapper.ConverDomainToModel(storageLocation);
	    return storageLocationModel;
	}
	

}
