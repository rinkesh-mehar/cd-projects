package in.cropdata.aefc.exception;


/**
 * @author RinkeshKM
 * @date 05/12/21
 */

public class InvalidDataException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public InvalidDataException(String message) {
        super(message);
    }
}
