/**
 * 
 */
package in.cropdata.aefc.exception;
/**
 * @author cropdata-ujwal
 *
 */

public class InvalidUserCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;

	public InvalidUserCredentialsException() {
		super();
	}

	public InvalidUserCredentialsException(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public InvalidUserCredentialsException(String message) {
		super(message);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

}
