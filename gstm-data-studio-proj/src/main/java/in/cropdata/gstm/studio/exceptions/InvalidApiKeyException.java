package in.cropdata.gstm.studio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception class for catching exception raised while accessing API using
 * invalid API key.
 * 
 * @author PranaySK
 * @since 1.0
 */

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidApiKeyException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;

	public InvalidApiKeyException(String errorCode, String exMsg) {
		super(exMsg);
		this.errorCode = errorCode;
	}

}