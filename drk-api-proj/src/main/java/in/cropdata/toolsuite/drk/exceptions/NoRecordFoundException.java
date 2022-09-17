package in.cropdata.toolsuite.drk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class NoRecordFoundException extends RuntimeException {

	public NoRecordFoundException(String message) {
		super(message);
	}

}
