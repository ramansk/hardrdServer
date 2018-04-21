package com.crux.hardrdServer.exception;

public class TehnicalException extends Exception{
	private final String code;

	public TehnicalException(String code, String message) {
		super(message);
		this.code = code;
	}

	public String getCode() {
		return code;
	}
}
