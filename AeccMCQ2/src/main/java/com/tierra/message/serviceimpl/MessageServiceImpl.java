package com.tierra.message.serviceimpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.auth.utils.CusException;
import com.tierra.auth.utils.CustomMessage;
import com.tierra.auth.utils.UserIdPrinciple;
import com.tierra.message.domain.Message;
import com.tierra.message.mapper.MessageMapper;
import com.tierra.message.model.MessageModel;
import com.tierra.message.repo.MessageRepo;
import com.tierra.message.service.MessageService;

import io.swagger.v3.oas.annotations.servers.Server;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Service
public class MessageServiceImpl implements MessageService {


	
	@Autowired
	MessageMapper messageMapper;
	
	@Autowired
	MessageRepo messageRepo;
	
	@Autowired
	UserIdPrinciple principle;
	

	@Override
	public BaseResponse createMessage(MessageModel messageModel) {
		Message message = messageMapper.convertModelToDomain(messageModel);
		message.setStatus(true);
		message.setCreatedDate(LocalDateTime.now());
	//	message.setCreatedBy(principle.getUserId());
		try {
		message = messageRepo.save(message);
		}catch (Exception e) {
			return new BaseResponse(" Message "+CustomMessage.SAVE_FAILED_MESSAGE, HttpStatus.BAD_REQUEST.value());
		}
		return new BaseResponse(" Message "+CustomMessage.SAVE_SUCCESS_MESSAGE, HttpStatus.CREATED.value());
	}

	@Override
	public BaseResponse updateMessage(MessageModel messageModel) {
		Message message = new Message();
		if(message.getMessageId()!=null) 
				message = messageRepo.findById(messageModel.getMessageId())
						.orElseThrow(() -> new CusException(" Message Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Message ", HttpStatus.NOT_FOUND);
		message.setCity(messageModel.getCity());
		message.setEmail(messageModel.getEmail());
		message.setMessageText(messageModel.getMessageText());
		message.setMobile(messageModel.getMobile());
		message.setName(messageModel.getName());
		message.setModifiedDate(LocalDateTime.now());
		message.setModifiedBy(principle.getUserId());
		message = messageRepo.save(message);
		return new BaseResponse(" Message "+CustomMessage.UPDATE_SUCCESS_MESSAGE, HttpStatus.OK.value());

	}

	@Override
	public BaseResponse deleteMessage(MessageModel messageModel) {
		Message message = new Message();
		if(message.getMessageId()!=null) 
				message = messageRepo.findById(messageModel.getMessageId())
						.orElseThrow(() -> new CusException(" Message Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Message ", HttpStatus.NOT_FOUND);
		message.setStatus(false);
		message.setModifiedDate(LocalDateTime.now());
		message.setModifiedBy(principle.getUserId());
		messageRepo.save(message);
		return new BaseResponse(" Message "+CustomMessage.DELETE_SUCCESS_MESSAGE, HttpStatus.OK.value());
	}

	@Override
	public MessageModel findById(MessageModel messageModel) {
		Message message = new Message();
		if(messageModel.getMessageId()!=null) 
				message = messageRepo.findById(messageModel.getMessageId())
						.orElseThrow(() -> new CusException(" Message Not Found ", HttpStatus.NOT_FOUND));
		else
			throw new CusException(" Id cannot be Null For a Message ", HttpStatus.NOT_FOUND);
		messageModel = messageMapper.convertDomainToModel(message);
		return messageModel;
	}

	@Override
	public Page<MessageModel> getAllMessage (String messageName, Pageable pageable)throws Exception{
		Page<Message> message = messageRepo.findAll(new Specification<Message>() {

			@Override
			public Predicate toPredicate(Root<Message> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<>();
				
				predicates.add(criteriaBuilder.and(criteriaBuilder.equal(root.get("status"),true )));

				if (messageName != null) {
					predicates.add(
							criteriaBuilder.and(criteriaBuilder.like(root.get("MessageName"), "%" + messageName + "%")));

				}

				return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
			}

		}, pageable);
	    return messageMapper.ConverDomainToModel(message);
	}
	


}

