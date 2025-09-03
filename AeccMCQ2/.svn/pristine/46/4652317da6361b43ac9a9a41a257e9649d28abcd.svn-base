package com.rim.masterdata.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.utils.BaseResponse;
import com.rim.masterdata.model.VendorModel;

public interface VendorService {
	
	public BaseResponse createVendor (VendorModel vendorModel);
	
	public BaseResponse updateVendor (VendorModel vendorModel);
	
	public BaseResponse deleteVendor (VendorModel vendorModel);
	
	public VendorModel findById (VendorModel vendorModel);
	
	public Page<VendorModel> getAllVendor (String vendorName,Pageable pageable)throws Exception;

}
