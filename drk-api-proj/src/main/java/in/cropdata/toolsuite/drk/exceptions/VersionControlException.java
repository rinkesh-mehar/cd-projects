/**
 * 
 */
package in.cropdata.toolsuite.drk.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Admin
 *
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class VersionControlException extends RuntimeException {

	/**
	 * 
	 */
	public VersionControlException() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public VersionControlException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

}
