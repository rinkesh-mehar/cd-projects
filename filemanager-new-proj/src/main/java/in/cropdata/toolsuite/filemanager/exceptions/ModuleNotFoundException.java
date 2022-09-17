package in.cropdata.toolsuite.filemanager.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class ModuleNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ModuleNotFoundException(String exception) {
		super(exception);
	}
}