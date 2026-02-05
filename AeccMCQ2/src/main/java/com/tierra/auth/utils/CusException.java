package com.tierra.auth.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CusException extends RuntimeException{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7570146809297994080L;
	
	private static final Logger logger = LoggerFactory.getLogger(CusException.class);
	
	private HttpStatus status;

    public CusException(String message, HttpStatus status) {
        super(message);
        logger.info(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
