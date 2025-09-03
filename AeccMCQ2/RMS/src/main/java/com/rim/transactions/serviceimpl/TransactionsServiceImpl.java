package com.rim.transactions.serviceimpl;

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
import com.rim.masterdata.domain.Quality;
import com.rim.masterdata.repo.ProductRepo;
import com.rim.masterdata.repo.QualityRepo;
import com.rim.transactions.domain.Transactions;
import com.rim.transactions.mapper.TransactionsMapper;
import com.rim.transactions.model.TransactionsModel;
import com.rim.transactions.repo.TransactionsRepo;
import com.rim.transactions.service.TransactionsService;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class TransactionsServiceImpl implements TransactionsService{
	
	@Autowired
	TransactionsMapper transactionsMapper;
	
	@Autowired
	TransactionsRepo transactionsRepo;
	
	@Autowired
	UserIdPrinciple principle;
	
	@Autowired
	ProductRepo productRepo;
	
	@Autowired
	QualityRepo qualityRepo;
	

	@Override
	public BaseResponse createTransactions(TransactionsModel transactionsModel) {
		Transactions transactions = transactionsMapper.convertModelToDomain(transactionsModel);
		transactions.setStatus(true);
		transactions.setCreatedDate(LocalDateTime.now());
		transactions.setCreatedBy(principle.getUserId());
		try {
		transactions = transactionsRepo.save(transactions);
		}catch(Exception e) {
			return new BaseResponse(transactions.getTransactionsName()+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(transactions.getTransactionsName()+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateTransactions(TransactionsModel transactionsModel) {
		Transactions transactions = new Transactions();
		if(transactionsModel.getTransactionsId()!=null)
				transactions = transactionsRepo.findById(transactionsModel.getTransactionsId())
				.orElseThrow(() -> new CusException(" Transaction Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Transaction ", HttpStatus.NOT_FOUND);
		transactions.setTransactionsName(transactionsModel.getTransactionsName());
		Product product = productRepo.findById(transactionsModel.getProductId())
				.orElseThrow(() -> new CusException(" Product Not Found For Given Transaction ", null));
		transactions.setProduct(product);
		Quality quality = qualityRepo.findById(transactionsModel.getQualityId())
				.orElseThrow(() -> new CusException(" Quality Not Found For Given Transaction ", null));
		transactions.setQuality(quality);
		transactions.setModifiedDate(LocalDateTime.now());
		transactions.setModifiedBy(principle.getUserId());
		transactions = transactionsRepo.save(transactions);
		return new BaseResponse(transactions.getTransactionsName()+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteTransactions(TransactionsModel transactionsModel) {
		Transactions transactions = new Transactions();
		if(transactionsModel.getTransactionsId()!=null)
				transactions = transactionsRepo.findById(transactionsModel.getTransactionsId())
				.orElseThrow(() -> new CusException(" Transaction Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Transaction ", HttpStatus.NOT_FOUND);
		transactions.setStatus(false);
		transactions.setModifiedDate(LocalDateTime.now());
		transactions.setModifiedBy(principle.getUserId());
		transactionsRepo.save(transactions);
		return new BaseResponse(transactions.getTransactionsName()+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public TransactionsModel findById(TransactionsModel transactionsModel) {
		Transactions transactions = new Transactions();
		if(transactionsModel.getTransactionsId()!=null)
				transactions = transactionsRepo.findById(transactionsModel.getTransactionsId())
				.orElseThrow(() -> new CusException(" Transaction Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null for Transaction ", HttpStatus.NOT_FOUND);
		transactionsModel = transactionsMapper.convertDomainToModel(transactions);
		return transactionsModel;
	}

	@Override
	public Page<TransactionsModel> getAllTransactions (String transactionsName, Pageable pageable)throws Exception{
		Page<Transactions> Transactions = transactionsRepo.findAll(new Specification<Transactions>() {

			@Override
			public Predicate toPredicate(Root<Transactions> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (transactionsName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("transactionsName"), "%" + transactionsName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    Page<TransactionsModel> transactionsModel = transactionsMapper.ConverDomainToModel(Transactions);
	    return transactionsModel;
	}
	


}
