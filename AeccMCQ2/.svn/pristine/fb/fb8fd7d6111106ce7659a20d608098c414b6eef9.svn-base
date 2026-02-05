package com.rim.masterdata.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.utils.BaseResponse;
import com.rim.masterdata.model.ProductModel;



public interface ProductService {
	
	public BaseResponse createProduct (ProductModel productModel);
	
	public BaseResponse updateProduct (ProductModel productModel);
	
	public BaseResponse deleteProduct (ProductModel productModel);
	
	public ProductModel findById (ProductModel productModel);
	
	public Page<ProductModel> getAllProduct (String productName, Pageable pageable)throws Exception;

}
