/**
 * 
 */
package in.cropdata.toolsuite.drk.exceptions.cases;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 25-Nov-2019
 */
public class FileStorageException extends RuntimeException{

	public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
