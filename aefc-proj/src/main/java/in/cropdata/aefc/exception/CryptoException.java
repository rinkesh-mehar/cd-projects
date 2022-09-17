package in.cropdata.aefc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception class for handling exceptions occurred while encryption/decryption
 * process.
 * 
 * @since 1.0
 * @author RinkeshKM
 */

@ResponseStatus(code = HttpStatus.NOT_ACCEPTABLE)
public class CryptoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public CryptoException(String message) {
		super(message);
	}
}
