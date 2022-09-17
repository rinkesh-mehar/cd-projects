/**
 * 
 */
package in.cropdata.toolsuite.drk.model.cases;

import org.springframework.stereotype.Component;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 25-Nov-2019
 */
@Component
public class GenericResponse {
	
	private boolean status;

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

}
