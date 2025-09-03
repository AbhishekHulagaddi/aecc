package com.rim.transactions.controller;

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

import com.rim.transactions.model.TransactionsModel;
import com.rim.transactions.model.ViewAllTransactionModel;
import com.rim.transactions.service.TransactionsService;
import com.rim.auth.utils.BaseResponse;
import com.rim.auth.utils.WebConstantUrl;

@RestController
@RequestMapping(WebConstantUrl.Transactions)
public class TransactionsController {
	
	
	@Autowired
	TransactionsService transactionsService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createTransactions (@RequestBody TransactionsModel transactionsModel){
		BaseResponse baseResponse = transactionsService.createTransactions(transactionsModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateTransactions (@RequestBody TransactionsModel transactionsModel){
		BaseResponse baseResponse = transactionsService.updateTransactions(transactionsModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteTransactions (@RequestBody TransactionsModel transactionsModel){
		BaseResponse baseResponse = transactionsService.deleteTransactions(transactionsModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody TransactionsModel transactionsModel){
		transactionsModel = transactionsService.findById(transactionsModel);
		return new ResponseEntity<> (transactionsModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllTransactions ( @RequestBody ViewAllTransactionModel viewModel) throws Exception{
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
		Page<TransactionsModel> transactionsModel = transactionsService.getAllTransactions(viewModel.getTransactionsName(), pages);

		if (transactionsModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", transactionsModel.getTotalElements());
			map.put("total_pages", transactionsModel.getTotalPages());
			map.put("Transactionss", transactionsModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", transactionsModel.getTotalElements());
		map.put("total_pages", transactionsModel.getTotalPages());
		map.put("Transactionss", transactionsModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
