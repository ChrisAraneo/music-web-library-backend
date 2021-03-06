package com.chrisaraneo.mwl.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ForbiddenException extends RuntimeException {
	
	private static final long serialVersionUID = 6371545948458200146L;

	public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
