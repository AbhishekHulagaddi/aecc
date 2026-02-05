package com.tierra.message.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.tierra.auth.utils.BaseResponse;
import com.tierra.message.model.MessageModel;

public interface MessageService {

	public BaseResponse createMessage (MessageModel messageModel);
	
	public BaseResponse updateMessage (MessageModel messageModel);
	
	public BaseResponse deleteMessage (MessageModel messageModel);
	
	public MessageModel findById (MessageModel messageModel);
	
	public Page<MessageModel> getAllMessage (String MessageTxt,Pageable pageable)throws Exception;

}
