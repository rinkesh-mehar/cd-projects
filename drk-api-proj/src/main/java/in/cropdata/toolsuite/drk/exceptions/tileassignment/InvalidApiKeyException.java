package in.cropdata.toolsuite.drk.exceptions.tileassignment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidApiKeyException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String errorCode;

    public InvalidApiKeyException(String errorCode, String exception) {
	super(exception);
	this.errorCode = errorCode;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
	return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
	this.errorCode = errorCode;
    }

}