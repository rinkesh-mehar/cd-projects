/**
 * 
 */
package in.cropdata.toolsuite.drk.exceptions.cases;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 28-Nov-2019
 */
public class DataStoreException extends RuntimeException{
	
	public DataStoreException(String message) {
        super(message);
    }

    public DataStoreException(String message, Throwable cause) {
        super(message, cause);
    }

}
