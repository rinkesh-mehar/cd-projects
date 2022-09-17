package in.cropdata.toolsuite.drk.exceptions.tileassignment;

public class ImageUploadFailedException extends RuntimeException {
    private static final long serialVersionUID = 1338076170789171287L;

    public ImageUploadFailedException() {
    }

    public ImageUploadFailedException(String message) {
	super(message);
    }

    public ImageUploadFailedException(Throwable cause) {
	super(cause);
    }

    public ImageUploadFailedException(String message, Throwable cause) {
	super(message, cause);
    }

    public ImageUploadFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
	super(message, cause, enableSuppression, writableStackTrace);
    }

}
