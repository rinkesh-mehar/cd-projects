package in.cropdata.farmers.app.exception;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 06/04/2021 - 12:00 PM
 */
public class CaseNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String message;
    private Boolean status;

    public CaseNotFoundException(String errorCode, String message, Boolean status) {
        super(message);
        this.errorCode = errorCode;
        this.message = message;
        this.status = status;
    }

    public Boolean getStatus() {
        return status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

}
