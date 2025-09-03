package com.rim.auth.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class CusException extends RuntimeException{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -7570146809297994080L;
	private HttpStatus status;

    public CusException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
