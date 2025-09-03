package com.rim.masterdata.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.rim.auth.utils.CusException;
import com.rim.masterdata.domain.Product;
import com.rim.masterdata.domain.Vendor;
import com.rim.masterdata.model.ProductModel;
import com.rim.masterdata.repo.VendorRepo;

@Component
public class ProductMapper {
	
	@Autowired
	VendorRepo vendorRepo;
	
	public Product convertModelToDomain (ProductModel productModel) {
		Product product = new Product();
		BeanUtils.copyProperties(productModel, product);
		Vendor vendor = vendorRepo.findById(productModel.getVendorId())
				.orElseThrow(() -> new CusException(" Vendor Not Found for Product '"+productModel.getProductName()+"' ", HttpStatus.NOT_FOUND ));
		product.setVendor(vendor);
		return product;
	}
	
	public ProductModel convertDomainToModel (Product product) {
		ProductModel productModel = new ProductModel();
		BeanUtils.copyProperties(product, productModel);
		productModel.setVendorId(product.getVendor().getVendorId());
		productModel.setVendorName(product.getVendor().getVendorName());
		return productModel;
	}

	public Page<ProductModel> ConverDomainToModel(Page<Product> product) {
			
	        List<ProductModel> productModel =  new ArrayList<>();
	        for(Product productD : product)
	        {
	        	ProductModel productM = new ProductModel();
	              BeanUtils.copyProperties(productD, productM);
	              productM.setVendorId(productD.getVendor().getVendorId());
	              productM.setVendorName(productD.getVendor().getVendorName());
	              productModel.add(productM);
	        }
			
			
	        return new PageImpl<>(productModel, product.getPageable(), product.getTotalElements());
	    }

}
