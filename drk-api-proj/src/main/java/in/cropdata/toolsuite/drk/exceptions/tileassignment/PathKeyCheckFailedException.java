package in.cropdata.toolsuite.drk.exceptions.tileassignment;

public class PathKeyCheckFailedException extends RuntimeException {

    private static final long serialVersionUID = 406756030010231514L;

    public PathKeyCheckFailedException(String message) {
	super(message);
    }

    public PathKeyCheckFailedException(Throwable cause) {
	super(cause);
    }

    public PathKeyCheckFailedException(String message, Throwable cause) {
	super(message, cause);
    }

    public PathKeyCheckFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
