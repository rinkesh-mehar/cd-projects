package in.cropdata.toolsuite.filemanager.model;

public class ResponseMessage {
	
	private boolean success;
	private String message;
	private String error;
	private String path;
	
	
	
	
	/**
	 * 
	 */
	public ResponseMessage() {
	}

	/**
	 * @param success
	 * @param message
	 * @param error
	 * @param path
	 */
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param success
	 * @param message
	 * @param error
	 * @param path
	 */
	public ResponseMessage(boolean success, String message, String error, String path) {
		super();
		this.success = success;
		this.message = message;
		this.error = error;
		this.path = path;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the error
	 */
	public String getError() {
		return error;
	}

	/**
	 * @param error the error to set
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * @return the path
	 */
	public String getPath() {
		return path;
	}

	/**
	 * @param path the path to set
	 */
	public void setPath(String path) {
		this.path = path;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}

	
	
	
	
}
