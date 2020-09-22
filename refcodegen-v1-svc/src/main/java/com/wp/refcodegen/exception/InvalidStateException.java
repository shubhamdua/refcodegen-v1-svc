package com.wp.refcodegen.exception;

@SuppressWarnings("serial")
public class InvalidStateException extends RuntimeException {

	public InvalidStateException(String message) {
		super(message);
	}
}
