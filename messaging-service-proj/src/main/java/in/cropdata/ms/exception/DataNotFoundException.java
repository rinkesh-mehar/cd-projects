package in.cropdata.ms.exception;

import in.cropdata.ms.error.ErrorStatus;

/**
 * @author Vivek Gajbhiye
 */
public class DataNotFoundException extends RuntimeException{

    private String errorCode;

    public DataNotFoundException(ErrorStatus errorStatus) {
        super(errorStatus.getReasonPhrase());
        this.errorCode = errorStatus.getErrorCode();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
