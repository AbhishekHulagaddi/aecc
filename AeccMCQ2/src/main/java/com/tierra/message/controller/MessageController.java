package com.tierra.message.controller;

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

import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.WebConstantUrl;
import com.tierra.masterdata.model.ViewAllMasterDataModel;
import com.tierra.message.model.MessageModel;
import com.tierra.message.model.ViewAllMessageModel;
import com.tierra.message.service.MessageService;

@RestController
@RequestMapping(WebConstantUrl.Message)
public class MessageController {
	
	
	@Autowired
	MessageService messageService;

	@PostMapping(WebConstantUrl.Create)
	public ResponseEntity<?> createMessage (@RequestBody MessageModel messageModel){
		BaseResponse baseResponse = messageService.createMessage(messageModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(WebConstantUrl.Update)
	public ResponseEntity<?> updateMessage (@RequestBody MessageModel messageModel){
		BaseResponse baseResponse = messageService.updateMessage(messageModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.Delete)
	public ResponseEntity<?> deleteMessage (@RequestBody MessageModel messageModel){
		BaseResponse baseResponse = messageService.deleteMessage(messageModel);
		return new ResponseEntity<> (baseResponse, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.FindById)
	public ResponseEntity<?> findById (@RequestBody MessageModel messageModel){
		messageModel = messageService.findById(messageModel);
		return new ResponseEntity<> (messageModel, HttpStatus.OK);
	}
	
	@PostMapping(WebConstantUrl.View)
	public ResponseEntity<?> getAllMessage ( @RequestBody ViewAllMessageModel viewModel) throws Exception{
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
		Page<MessageModel> messageModel = messageService.getAllMessage(viewModel.getMessageText(), pages);

		if (messageModel.getContent().size() == 0) {
			map.put("status_code", HttpStatus.NO_CONTENT);
			map.put("total_records", messageModel.getTotalElements());
			map.put("total_pages", messageModel.getTotalPages());
			map.put("Messages", messageModel);

			return new ResponseEntity<>(map, HttpStatus.OK);
		} else {
		map.put("status_code", HttpStatus.OK);
		map.put("total_records", messageModel.getTotalElements());
		map.put("total_pages", messageModel.getTotalPages());
		map.put("Messages", messageModel);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	}


}
