/**
 * 
 */
package in.cropdata.toolsuite.drk.exceptions.cases;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 16-Dec-2019
 */
public class SizeMisMatchException extends RuntimeException{
	
	public SizeMisMatchException(String message) {
        super(message);
    }

    public SizeMisMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
