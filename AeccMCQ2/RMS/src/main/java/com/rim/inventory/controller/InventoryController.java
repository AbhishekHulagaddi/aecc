package com.rim.inventory.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rim.inventory.model.InventoryModel;
import com.rim.inventory.model.ViewAllInventoryModel;
import com.rim.inventory.service.InventoryService;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.WebConstantUrl;

@RestController
@RequestMapping(WebConstantUrl.Inventory)
public class InventoryController {
	
	
	@Autowired
	InventoryService inventoryService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createInventory (@RequestBody InventoryModel inventoryModel){
		BaseResponse baseResponse = inventoryService.createInventory(inventoryModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateInventory (@RequestBody InventoryModel inventoryModel){
		BaseResponse baseResponse = inventoryService.updateInventory(inventoryModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteInventory (@RequestBody InventoryModel inventoryModel){
		BaseResponse baseResponse = inventoryService.deleteInventory(inventoryModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody InventoryModel inventoryModel){
		inventoryModel = inventoryService.findById(inventoryModel);
		return new ResponseEntity<> (inventoryModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllInventory ( @RequestBody ViewAllInventoryModel viewModel) throws Exception{
		Map<String, Object> map = new HashMap<>();
		Sort sort =null;
		Pageable pages = null;
		
		if(viewModel.getPropertyName()!=null&&viewModel.getDirection()!=null) {
			if(viewModel.getDirection().equalsIgnoreCase("desc")) {
				 sort = Sort.by(Sort.Direction.DESC, viewModel.getPropertyName());
			}else{
				 sort = Sort.by(Sort.Direction.ASC, viewModel.getPropertyName());
			}
			 pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize(), sort);
			
		}else {
			pages = PageRequest.of(viewModel.getPage() - 1, viewModel.getSize() == 0 ? Integer.MAX_VALUE : viewModel.getSize());

		}
		Page<InventoryModel> inventoryModel = inventoryService.getAllInventory(viewModel.getInventoryName(), pages);

		if (inventoryModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", inventoryModel.getTotalElements());
			map.put("total_pages", inventoryModel.getTotalPages());
			map.put("Inventorys", inventoryModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", inventoryModel.getTotalElements());
		map.put("total_pages", inventoryModel.getTotalPages());
		map.put("Inventorys", inventoryModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
