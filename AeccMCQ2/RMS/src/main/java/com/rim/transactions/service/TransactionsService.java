package com.rim.transactions.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.rim.auth.utils.BaseResponse;
import com.rim.transactions.model.TransactionsModel;

public interface TransactionsService {
	
	public BaseResponse createTransactions (TransactionsModel transactionsModel);
	
	public BaseResponse updateTransactions (TransactionsModel transactionsModel);
	
	public BaseResponse deleteTransactions (TransactionsModel transactionsModel);
	
	public TransactionsModel findById (TransactionsModel transactionsModel);
	
	public Page<TransactionsModel> getAllTransactions (String transactionsName, Pageable pageable)throws Exception;
	

	

}
