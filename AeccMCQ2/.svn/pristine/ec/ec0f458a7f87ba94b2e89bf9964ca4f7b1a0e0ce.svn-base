package com.rim.masterdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.masterdata.domain.Vendor;
import com.rim.masterdata.model.VendorModel;

@Component
public class VendorMapper {
	
	public Vendor convertModelToDomain (VendorModel vendorModel) {
		Vendor vendor = new Vendor();
		BeanUtils.copyProperties(vendorModel, vendor);
		return vendor;
	}
	
	public VendorModel convertDomainToModel (Vendor vendor) {
		VendorModel vendorModel = new VendorModel();
		BeanUtils.copyProperties(vendor, vendorModel);
		return vendorModel;
	}

	public Page<VendorModel> ConverDomainToModel(Page<Vendor> vendor) {
        List<VendorModel> vendorModel =  new ArrayList<>();
        for(Vendor vendorD : vendor)
        {
        	VendorModel vendorM = new VendorModel();
              BeanUtils.copyProperties(vendorD, vendorM);
              vendorModel.add(vendorM);
        }
		
		
        return new PageImpl<>(vendorModel, vendor.getPageable(), vendor.getTotalElements());
    
	}

}
