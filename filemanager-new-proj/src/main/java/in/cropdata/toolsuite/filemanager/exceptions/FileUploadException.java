package in.cropdata.toolsuite.filemanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileUploadException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadException(String exception) {
		super(exception);
	}
}