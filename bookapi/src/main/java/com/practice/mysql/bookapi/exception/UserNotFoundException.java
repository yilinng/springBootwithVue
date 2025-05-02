package com.practice.mysql.bookapi.exception;

import java.io.Serial;

public class UserNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 1;

	public UserNotFoundException(String message) {
		super(message);
	}

}
