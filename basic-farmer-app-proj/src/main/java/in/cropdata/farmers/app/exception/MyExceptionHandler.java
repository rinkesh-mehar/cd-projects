/**
 * 
 */
package in.cropdata.farmers.app.exception;

/**
 * @author cropdata-Aniket Naik
 *
 */

public class MyExceptionHandler extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;
	private Boolean status;

	public MyExceptionHandler() {
		super();
	}

	public MyExceptionHandler(String errorCode, String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	public MyExceptionHandler(String errorCode, String message, Boolean status) {
		super();
		this.errorCode = errorCode;
		this.message = message;
		this.status = status;
	}

	public MyExceptionHandler(String message) {
		this.message = message;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
