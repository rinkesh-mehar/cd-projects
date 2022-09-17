package in.cropdata.toolsuite.filemanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidMetadataException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidMetadataException(String exception) {
		super(exception);
	}
}