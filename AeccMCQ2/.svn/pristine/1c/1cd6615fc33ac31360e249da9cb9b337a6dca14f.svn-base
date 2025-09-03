package com.rim.inventory.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.rim.auth.utils.CusException;
import com.rim.inventory.domain.Inventory;
import com.rim.inventory.model.InventoryModel;
import com.rim.masterdata.domain.Product;
import com.rim.masterdata.domain.Quality;
import com.rim.masterdata.domain.StorageLocation;
import com.rim.masterdata.domain.Vendor;
import com.rim.masterdata.repo.ProductRepo;
import com.rim.masterdata.repo.QualityRepo;
import com.rim.masterdata.repo.StorageLocationRepo;
import com.rim.masterdata.repo.VendorRepo;


@Component
public class InventoryMapper {
	
	@Autowired
	VendorRepo vendorRepo;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	QualityRepo qualityRepo;
	
	@Autowired
	StorageLocationRepo storageLocationRepo;

	public Inventory convertModelToDomain (InventoryModel inventoryModel) {
		Inventory inventory = new Inventory();
		BeanUtils.copyProperties(inventoryModel, inventory);
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
		return inventory;
	}
	
	public InventoryModel convertDomainToModel (Inventory inventory) {
		InventoryModel inventoryModel = new InventoryModel();
		BeanUtils.copyProperties(inventory, inventoryModel);
		inventoryModel.setVendorId(inventory.getVendor().getVendorId());
		inventoryModel.setProductId(inventory.getProduct().getProductId());
		inventoryModel.setQualityId(inventory.getQuality().getQualityId());
		inventoryModel.setStorageLocationId(inventory.getStorageLocation().getStorageLocationId());
		inventoryModel.setVendorName(inventory.getVendor().getVendorName());
		inventoryModel.setProductName(inventory.getProduct().getProductName());
		inventoryModel.setQualityName(inventory.getQuality().getQualityName());
		inventoryModel.setStorageLocationName(inventory.getStorageLocation().getStorageLocationName());
		return inventoryModel;
	}

	public Page<InventoryModel> ConverDomainToModel(Page<Inventory> inventory) {
		
	        List<InventoryModel> inventoryModel =  new ArrayList<>();
	        for(Inventory inventoryD : inventory)
	        {
	        	InventoryModel inventoryM = new InventoryModel();
	              BeanUtils.copyProperties(inventoryD, inventoryM);
	              inventoryM.setVendorId(inventoryD.getVendor().getVendorId());
	              inventoryM.setProductId(inventoryD.getProduct().getProductId());
	              inventoryM.setQualityId(inventoryD.getQuality().getQualityId());
	              inventoryM.setStorageLocationId(inventoryD.getStorageLocation().getStorageLocationId());
	              inventoryM.setVendorName(inventoryD.getVendor().getVendorName());
	              inventoryM.setProductName(inventoryD.getProduct().getProductName());
	              inventoryM.setQualityName(inventoryD.getQuality().getQualityName());
	              inventoryM.setStorageLocationName(inventoryD.getStorageLocation().getStorageLocationName());

	              inventoryModel.add(inventoryM);
	        }
			
			
	        return new PageImpl<>(inventoryModel, inventory.getPageable(), inventory.getTotalElements());
	    }
}
