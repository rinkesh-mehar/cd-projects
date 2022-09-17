package in.cropdata.farmers.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class UnauthorizedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;

	public UnauthorizedException() {
		super();
	}

	public UnauthorizedException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public UnauthorizedException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
