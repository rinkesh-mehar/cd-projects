/**
 * 
 */
package in.cropdata.toolsuite.drk.model.cases;

import lombok.Data;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 30-Nov-2019
 */
@Data
public class FaieldReasion {
	
	private int id;
	private String exception;
	private int failedAttempts;

}
