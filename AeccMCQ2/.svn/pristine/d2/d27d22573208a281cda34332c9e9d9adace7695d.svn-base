package com.rim.masterdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.masterdata.domain.StorageLocation;
import com.rim.masterdata.model.StorageLocationModel;

@Component
public class StorageLocationMapper {
	
	public StorageLocation convertModelToDomain (StorageLocationModel storageLocationModel) {
		StorageLocation storageLocation = new StorageLocation();
		BeanUtils.copyProperties(storageLocationModel, storageLocation);
		return storageLocation;
	}
	
	public StorageLocationModel convertDomainToModel (StorageLocation storageLocation) {
		StorageLocationModel storageLocationModel = new StorageLocationModel();
		BeanUtils.copyProperties(storageLocation, storageLocationModel);
		return storageLocationModel;
	}

	public Page<StorageLocationModel> ConverDomainToModel(Page<StorageLocation> storageLocation) {
			
	        List<StorageLocationModel> storageLocationModel =  new ArrayList<>();
	        for(StorageLocation storageLocationD : storageLocation)
	        {
	        	StorageLocationModel StorageLocationM = new StorageLocationModel();
	              BeanUtils.copyProperties(storageLocationD, StorageLocationM);
	              storageLocationModel.add(StorageLocationM);
	        }
			
			
	        return new PageImpl<>(storageLocationModel, storageLocation.getPageable(), storageLocation.getTotalElements());
	    }


}
