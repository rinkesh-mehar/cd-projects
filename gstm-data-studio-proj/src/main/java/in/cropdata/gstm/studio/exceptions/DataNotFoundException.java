package in.cropdata.gstm.studio.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Exception class for exception handling while data fetching from DB is not
 * present.
 * 
 * @author PranaySK
 * @since 1.0
 */

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
@Data
@EqualsAndHashCode(callSuper = false)
public class DataNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;

	public DataNotFoundException(String errorCode, String exMsg) {
		super(exMsg);
		this.errorCode = errorCode;
	}

}
