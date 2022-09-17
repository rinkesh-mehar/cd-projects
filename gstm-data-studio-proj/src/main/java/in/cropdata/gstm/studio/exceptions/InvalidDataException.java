package in.cropdata.gstm.studio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception class for catching exception raised while processing request data.
 * 
 * @author PranaySK
 * @since 1.0
 */

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
@EqualsAndHashCode(callSuper = false)
public class InvalidDataException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;

	public InvalidDataException(String errorCode, String exMsg) {
		super(exMsg);
		this.errorCode = errorCode;
	}

}
