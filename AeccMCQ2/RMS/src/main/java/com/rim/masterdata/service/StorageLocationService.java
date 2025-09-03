package com.rim.masterdata.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.utils.BaseResponse;
import com.rim.masterdata.model.StorageLocationModel;

public interface StorageLocationService {
	
	public BaseResponse createStorageLocation (StorageLocationModel storageLocationModel);
	
	public BaseResponse updateStorageLocation (StorageLocationModel storageLocationModel);
	
	public BaseResponse deleteStorageLocation (StorageLocationModel storageLocationModel);
	
	public StorageLocationModel findById (StorageLocationModel storageLocationModel);
	
	public Page<StorageLocationModel> getAllStorageLocation (String storageLocationName, Pageable pageable)throws Exception;


}
