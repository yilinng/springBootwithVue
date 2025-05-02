package com.practice.mysql.bookapi.exception;

import java.io.Serial;

public class BookNotFoundException extends RuntimeException {
	@Serial
	private static final long serialVersionUID = 2;

	public BookNotFoundException(String message) {
		super(message);
	}
}
