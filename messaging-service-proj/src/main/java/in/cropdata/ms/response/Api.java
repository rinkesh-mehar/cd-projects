package in.cropdata.ms.response;

import in.cropdata.ms.error.ErrorStatus;
import lombok.Data;

import java.util.Map;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class Api {

    private boolean success;
    private String message;
    private String errorCode;
    private Map<String,String> data;

    public Api(boolean success, String message, String errorCode) {
        this.success = success;
        this.message = message;
        this.errorCode = errorCode;
    }

    public Api(boolean success, ErrorStatus errorStatus) {
        this.success = success;
        this.message = errorStatus.getReasonPhrase();
        this.errorCode = errorStatus.getErrorCode();
    }

    public Api() {

    }
}
