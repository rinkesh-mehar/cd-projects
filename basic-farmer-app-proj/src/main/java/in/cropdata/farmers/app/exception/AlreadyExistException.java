package in.cropdata.farmers.app.exception;

public class AlreadyExistException extends RuntimeException {
	
	 private static final long serialVersionUID = 1L;
		private String errorCode;
		private String message;

		public AlreadyExistException() {
			super();
		}

		public AlreadyExistException(String errorCode, String message) {
			this.errorCode = errorCode;
			this.message = message;
		}

		public AlreadyExistException(String message) {
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
