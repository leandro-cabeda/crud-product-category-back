package com.crud_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;
import java.util.function.Supplier;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RegisterExistsException extends RuntimeException {

	public RegisterExistsException(String msg) {
		super(msg);
	}
	
	public static <T extends Serializable> Supplier<RegisterExistsException> registerExists(final T register) {
	    return () -> new RegisterExistsException("Error: "+ register);
	}
}
