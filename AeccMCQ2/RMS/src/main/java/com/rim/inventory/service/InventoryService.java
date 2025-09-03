package com.rim.inventory.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.utils.BaseResponse;
import com.rim.inventory.model.InventoryModel;

public interface InventoryService {
	
	public BaseResponse createInventory (InventoryModel inventoryModel);
	
	public BaseResponse updateInventory (InventoryModel inventoryModel);
	
	public BaseResponse deleteInventory (InventoryModel inventoryModel);
	
	public InventoryModel findById (InventoryModel inventoryModel);
	
	public Page<InventoryModel> getAllInventory (String inventoryName, Pageable pageable)throws Exception;
	

	

}
