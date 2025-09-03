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
import com.rim.masterdata.domain.Vendor;
import com.rim.masterdata.mapper.VendorMapper;
import com.rim.masterdata.model.VendorModel;
import com.rim.masterdata.repo.VendorRepo;
import com.rim.masterdata.service.VendorService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class VendorServiceImpl implements VendorService{


	
	@Autowired
	VendorMapper vendorMapper;
	
	@Autowired
	VendorRepo vendorRepo;
	
	@Autowired
	UserIdPrinciple principle;
	

	@Override
	public BaseResponse createVendor(VendorModel vendorModel) {
		Vendor vendor = vendorMapper.convertModelToDomain(vendorModel);
		vendor.setStatus(true);
		vendor.setCreatedDate(LocalDateTime.now());
		vendor.setCreatedBy(principle.getUserId());
		try {
		vendor = vendorRepo.save(vendor);
		}catch (Exception e) {
			return new BaseResponse(vendor.getVendorName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(vendor.getVendorName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateVendor(VendorModel vendorModel) {
		Vendor vendor = new Vendor();
		if(vendor.getVendorId()!=null) 
				vendor = vendorRepo.findById(vendorModel.getVendorId())
						.orElseThrow(() -> new CusException(" Vendor Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Vendor ", HttpStatus.NOT_FOUND);
		vendor.setVendorName(vendorModel.getVendorName());
		vendor.setAddress(vendorModel.getAddress());
		vendor.setEmail(vendorModel.getEmail());
		vendor.setGST_Number(vendorModel.getGST_Number());
		vendor.setMobilenumber(vendorModel.getMobilenumber());
		vendor.setModifiedDate(LocalDateTime.now());
		vendor.setModifiedBy(principle.getUserId());
		vendor = vendorRepo.save(vendor);
		return new BaseResponse(vendor.getVendorName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteVendor(VendorModel vendorModel) {
		Vendor vendor = new Vendor();
		if(vendor.getVendorId()!=null) 
				vendor = vendorRepo.findById(vendorModel.getVendorId())
						.orElseThrow(() -> new CusException(" Vendor Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Vendor ", HttpStatus.NOT_FOUND);
		vendor.setStatus(false);
		vendor.setModifiedDate(LocalDateTime.now());
		vendor.setModifiedBy(principle.getUserId());
		vendorRepo.save(vendor);
		return new BaseResponse(vendor.getVendorName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public VendorModel findById(VendorModel vendorModel) {
		Vendor vendor = new Vendor();
		if(vendorModel.getVendorId()!=null) 
				vendor = vendorRepo.findById(vendorModel.getVendorId())
						.orElseThrow(() -> new CusException(" Vendor Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Vendor ", HttpStatus.NOT_FOUND);
		vendorModel = vendorMapper.convertDomainToModel(vendor);
		return vendorModel;
	}

	@Override
	public Page<VendorModel> getAllVendor (String vendorName, Pageable pageable)throws Exception{
		Page<Vendor> vendor = vendorRepo.findAll(new Specification<Vendor>() {

			@Override
			public Predicate toPredicate(Root<Vendor> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (vendorName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("VendorName"), "%" + vendorName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<VendorModel> vendorModel = vendorMapper.ConverDomainToModel(vendor);
	    return vendorModel;
	}
	


}

