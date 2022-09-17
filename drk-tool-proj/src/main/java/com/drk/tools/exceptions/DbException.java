package com.drk.tools.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class DbException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;

	public DbException(String errorMsg) {
		super(errorMsg);
	}

	public DbException(String errorCode, String exception) {
		super(exception);
		this.errorCode = errorCode;
	}

	/**
	 * @return the errorCode
	 */
	public String getErrorCode() {
		return errorCode;
	}

	/**
	 * @param errorCode the errorCode to set
	 */
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

}
