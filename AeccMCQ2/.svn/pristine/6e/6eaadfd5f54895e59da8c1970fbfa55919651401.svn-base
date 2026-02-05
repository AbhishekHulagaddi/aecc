package com.rim.inventory.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;
import com.rim.auth.utils.IDGeneration;
import com.rim.auth.utils.UserIdPrinciple;
import com.rim.inventory.domain.Inventory;
import com.rim.inventory.mapper.InventoryMapper;
import com.rim.inventory.model.InventoryModel;
import com.rim.inventory.repo.InventoryRepo;
import com.rim.inventory.service.InventoryService;
import com.rim.masterdata.domain.Product;
import com.rim.masterdata.domain.Quality;
import com.rim.masterdata.domain.StorageLocation;
import com.rim.masterdata.domain.Vendor;
import com.rim.masterdata.repo.ProductRepo;
import com.rim.masterdata.repo.QualityRepo;
import com.rim.masterdata.repo.StorageLocationRepo;
import com.rim.masterdata.repo.VendorRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class InventoryServiceImpl implements InventoryService{
	
	@Autowired
	InventoryMapper inventoryMapper;
	
	@Autowired
	InventoryRepo inventoryRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	QualityRepo qualityRepo;
	
	@Autowired
	StorageLocationRepo storageLocationRepo;
	
	@Autowired
	VendorRepo vendorRepo;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Autowired
	IDGeneration idGeneration;
	

	@Override
	public BaseResponse createInventory(InventoryModel inventoryModel) {
		Inventory inventory = inventoryMapper.convertModelToDomain(inventoryModel);
		inventory.setStatus(true);
		inventory.setCreatedDate(LocalDateTime.now());
		inventory.setCreatedBy(principle.getUserId());
		inventory.setInventoryCode("INV"+idGeneration.GenerateId());
		try {
		inventory = inventoryRepo.save(inventory);
		}catch(Exception e) {
			return new BaseResponse(inventory.getInventoryName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(inventory.getInventoryName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateInventory(InventoryModel inventoryModel) {
		Inventory inventory = new Inventory();
		if(inventoryModel.getInventoryId()!=null)
			inventory = inventoryRepo.findById(inventoryModel.getInventoryId())
				.orElseThrow(()->new CusException(" Inventory Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Inventory ", HttpStatus.NOT_FOUND);
		inventory.setInventoryName(inventoryModel.getInventoryName());
		Vendor vendor = vendorRepo.findById(inventoryModel.getVendorId())
				.orElseThrow(() -> new CusException(" Vendor Not Found For Inventory ", HttpStatus.NOT_FOUND));
		Product product = productRepo.findById(inventoryModel.getProductId())
				.orElseThrow(() -> new CusException(" Product Not Found For Inventory ", HttpStatus.NOT_FOUND));
		Quality quality = qualityRepo.findById(inventoryModel.getQualityId())
				.orElseThrow(() -> new CusException(" Quality Not Found For Inventory ", HttpStatus.NOT_FOUND));
		StorageLocation storageLocation = storageLocationRepo.findById(inventoryModel.getStorageLocationId())
				.orElseThrow(() -> new CusException(" StorageLocation Not Found For Inventory ", HttpStatus.NOT_FOUND));
		inventory.setVendor(vendor);
		inventory.setProduct(product);
		inventory.setQuality(quality);
		inventory.setStorageLocation(storageLocation);
		inventory.setModifiedDate(LocalDateTime.now());
		inventory.setModifiedBy(principle.getUserId());
		inventory = inventoryRepo.save(inventory);
		return new BaseResponse(inventory.getInventoryName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public BaseResponse deleteInventory(InventoryModel inventoryModel) {
		Inventory inventory = new Inventory();
		if(inventoryModel.getInventoryId()!=null)
			inventory = inventoryRepo.findById(inventoryModel.getInventoryId())
				.orElseThrow(()->new CusException(" Inventory Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Inventory ", HttpStatus.NOT_FOUND);
		inventory.setStatus(false);
		inventory.setModifiedDate(LocalDateTime.now());
		inventory.setModifiedBy(principle.getUserId());
		inventoryRepo.save(inventory);
		return new BaseResponse(inventory.getInventoryName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public InventoryModel findById(InventoryModel inventoryModel) {
		Inventory inventory = new Inventory();
		if(inventoryModel.getInventoryId()!=null)
			inventory = inventoryRepo.findById(inventoryModel.getInventoryId())
				.orElseThrow(()->new CusException(" Inventory Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Inventory ", HttpStatus.NOT_FOUND);
		inventoryModel = inventoryMapper.convertDomainToModel(inventory);
		return inventoryModel;
	}

	@Override
	public Page<InventoryModel> getAllInventory (String inventoryName, Pageable pageable)throws Exception{
		Page<Inventory> inventory = inventoryRepo.findAll(new Specification<Inventory>() {

			@Override
			public Predicate toPredicate(Root<Inventory> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (inventoryName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("inventoryName"), "%" + inventoryName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<InventoryModel> inventoryModel = inventoryMapper.ConverDomainToModel(inventory);
	    return inventoryModel;
	}
	

}
