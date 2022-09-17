package in.cropdata.toolsuite.drk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DataInsertionException extends RuntimeException {

	public DataInsertionException(String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;

}
