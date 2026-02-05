package com.rim.masterdata.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.CusException;
import com.rim.auth.utils.CustomMessage;
import com.rim.auth.utils.UserIdPrinciple;
import com.rim.masterdata.domain.Product;
import com.rim.masterdata.domain.Vendor;
import com.rim.masterdata.mapper.ProductMapper;
import com.rim.masterdata.model.ProductModel;
import com.rim.masterdata.repo.ProductRepo;
import com.rim.masterdata.repo.VendorRepo;
import com.rim.masterdata.service.ProductService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
@Service
public class ProductServiceImpl implements ProductService{

	
	@Autowired
	ProductMapper productMapper;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	VendorRepo vendorRepo;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Override
	public BaseResponse createProduct(ProductModel productModel) {
		Product product = productMapper.convertModelToDomain(productModel);
		product.setStatus(true);  
		product.setCreatedDate(LocalDateTime.now());
		product.setCreatedBy(principle.getUserId());
		try {
		product = productRepo.save(product);
		}catch(Exception e) {
			return new BaseResponse(product.getProductName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(product.getProductName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateProduct(ProductModel productModel) {
		Product product = new Product();
		if(productModel.getProductId()!=null)
		   product = productRepo.findById(productModel.getProductId())
		   				.orElseThrow(() -> new CusException(" Product Not Found ", HttpStatus.NOT_FOUND));
		else 
			throw new CusException(" Id cannot be Null for Product ", HttpStatus.NOT_FOUND);
		product.setProductName(productModel.getProductName());
		product.setModifiedDate(LocalDateTime.now());
		product.setModifiedBy(principle.getUserId());
		Vendor vendor = vendorRepo.findById(productModel.getVendorId())
				.orElseThrow(() -> new CusException(" Vendor Not Found for Product " , HttpStatus.NOT_FOUND ));
		product.setVendor(vendor);
		product = productRepo.save(product);
		return new BaseResponse(product.getProductName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteProduct(ProductModel productModel) {
		Product product = new Product();
		if(productModel.getProductId()!=null)
		   product = productRepo.findById(productModel.getProductId())
		   				.orElseThrow(() -> new CusException(" Product Not Found ", HttpStatus.NOT_FOUND));
		else 
			throw new CusException(" Id cannot be Null for Product ", HttpStatus.NOT_FOUND);
		product.setStatus(false);
		product.setModifiedDate(LocalDateTime.now());
		product.setModifiedBy(principle.getUserId());
		productRepo.save(product);
		return new BaseResponse(product.getProductName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public ProductModel findById(ProductModel productModel) {
		Product product = new Product();
		if(productModel.getProductId()!=null)
		   product = productRepo.findById(productModel.getProductId())
		   				.orElseThrow(() -> new CusException(" Product Not Found ", HttpStatus.NOT_FOUND));
		else 
			throw new CusException(" Id cannot be Null for Product ", HttpStatus.NOT_FOUND);
		productModel = productMapper.convertDomainToModel(product);
		return productModel;
	}

	@Override
	public Page<ProductModel> getAllProduct (String productName, Pageable pageable)throws Exception{
		Page<Product> product = productRepo.findAll(new Specification<Product>() {

			@Override
			public Predicate toPredicate(Root<Product> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (productName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("productName"), "%" + productName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<ProductModel> ProductModel = productMapper.ConverDomainToModel(product);
	    return ProductModel;
	}
	


}
