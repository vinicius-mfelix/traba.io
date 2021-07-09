package com.growth.startupone.domain.exception;

public class EmailAlreadyInUseException extends BusinessException {
	
	private static final long serialVersionUID = 1L;

	public EmailAlreadyInUseException(String message) {
		super(message);
	}

}
