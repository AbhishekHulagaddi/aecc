package com.rim.masterdata.controller;

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

import com.rim.masterdata.service.ProductService;
import com.rim.masterdata.model.ProductModel;
import com.rim.masterdata.model.ViewAllMasterDataModel;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.WebConstantUrl;


@RestController
@RequestMapping(WebConstantUrl.Product)
public class ProductController {
	
	
	@Autowired
	ProductService productService;


	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createProduct (@RequestBody ProductModel productModel){
		BaseResponse baseResponse = productService.createProduct(productModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateProduct (@RequestBody ProductModel productModel){
		BaseResponse baseResponse = productService.updateProduct(productModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteProduct (@RequestBody ProductModel productModel){
		BaseResponse baseResponse = productService.deleteProduct(productModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody ProductModel productModel){
		productModel = productService.findById(productModel);
		return new ResponseEntity<> (productModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllProduct ( @RequestBody ViewAllMasterDataModel viewModel) throws Exception{
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
		Page<ProductModel> productModel = productService.getAllProduct(viewModel.getProductName(), pages);

		if (productModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", productModel.getTotalElements());
			map.put("total_pages", productModel.getTotalPages());
			map.put("Products", productModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", productModel.getTotalElements());
		map.put("total_pages", productModel.getTotalPages());
		map.put("Products", productModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
