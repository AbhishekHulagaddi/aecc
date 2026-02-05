package com.tierra.message.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import com.tierra.message.domain.Message;
import com.tierra.message.model.MessageModel;

@Component
public class MessageMapper {
	
	public Message convertModelToDomain (MessageModel messageModel) {
		Message message = new Message();
		BeanUtils.copyProperties(messageModel, message);
		return message;
	}
	
	public MessageModel convertDomainToModel (Message message) {
		MessageModel messageModel = new MessageModel();
		BeanUtils.copyProperties(message, messageModel);
		return messageModel;
	}

	public Page<MessageModel> ConverDomainToModel(Page<Message> message) {
        List<MessageModel> messageModel =  new ArrayList<>();
        for(Message messageD : message)
        {
        	MessageModel messageM = new MessageModel();
              BeanUtils.copyProperties(messageD, messageM);
              messageModel.add(messageM);
        }
		
		
        return new PageImpl<>(messageModel, message.getPageable(), message.getTotalElements());
    
	}

}
