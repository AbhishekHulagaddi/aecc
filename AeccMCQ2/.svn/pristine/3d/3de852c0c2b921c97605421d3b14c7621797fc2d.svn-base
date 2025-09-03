package com.rim.transactions.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.rim.auth.utils.CusException;
import com.rim.masterdata.domain.Product;
import com.rim.masterdata.domain.Quality;
import com.rim.masterdata.repo.ProductRepo;
import com.rim.masterdata.repo.QualityRepo;
import com.rim.transactions.domain.Transactions;
import com.rim.transactions.model.TransactionsModel;


@Component
public class TransactionsMapper {
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	QualityRepo qualityRepo;

	public Transactions convertModelToDomain (TransactionsModel transactionsModel) {
		Transactions transactions = new Transactions();
		BeanUtils.copyProperties(transactionsModel, transactions);
		Product product = productRepo.findById(transactionsModel.getProductId())
				.orElseThrow(() -> new CusException(" Product Not Found For Given Transaction ", null));
		transactions.setProduct(product);
		Quality quality = qualityRepo.findById(transactionsModel.getQualityId())
				.orElseThrow(() -> new CusException(" Quality Not Found For Given Transaction ", null));
		transactions.setQuality(quality);
		return transactions;
	}
	
	public TransactionsModel convertDomainToModel (Transactions transactions) {
		TransactionsModel transactionsModel = new TransactionsModel();
		BeanUtils.copyProperties(transactions, transactionsModel);
		transactionsModel.setProductId(transactions.getProduct().getProductId());
		transactionsModel.setQualityId(transactions.getQuality().getQualityId());
		transactionsModel.setProductName(transactions.getProduct().getProductName());
		transactionsModel.setQualityName(transactions.getQuality().getQualityName());
		return transactionsModel;
	}

	public Page<TransactionsModel> ConverDomainToModel(Page<Transactions> transactions) {
		
	        List<TransactionsModel> transactionsModel =  new ArrayList<>();
	        for(Transactions transactionsD : transactions)
	        {
	        	TransactionsModel transactionsM = new TransactionsModel();
	              BeanUtils.copyProperties(transactionsD, transactionsM);
	              transactionsM.setProductId(transactionsD.getProduct().getProductId());
	              transactionsM.setQualityId(transactionsD.getQuality().getQualityId());
	              transactionsM.setProductName(transactionsD.getProduct().getProductName());
	      		transactionsM.setQualityName(transactionsD.getQuality().getQualityName());
	              transactionsModel.add(transactionsM);
	        }
			
			
	        return new PageImpl<>(transactionsModel, transactions.getPageable(), transactions.getTotalElements());
	    }
}
