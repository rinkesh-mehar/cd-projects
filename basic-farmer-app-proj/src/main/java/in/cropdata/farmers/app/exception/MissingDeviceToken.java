package in.cropdata.farmers.app.exception;

public class MissingDeviceToken extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	private String errorCode;
	private String message;

	public MissingDeviceToken() {
		super();

	}

	public MissingDeviceToken(String errorCode, String message) {
		this.errorCode = errorCode;
		this.message = message;
	}

	public MissingDeviceToken(String message) {
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