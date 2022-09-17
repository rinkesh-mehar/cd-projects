/**
 * 
 */
package in.cropdata.toolsuite.drk.model.cases;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 *         17-Dec-2019
 */
@Data
@NoArgsConstructor
public class DetailedMessage {

	
	private HttpStatus status;
	private boolean success;
	private String message;
	private List<String> errors;
	

	public DetailedMessage(HttpStatus status, String message, List<String> errors) {
		super();
		this.status = status;
		this.message = message;
		this.errors = errors;
	}

	public DetailedMessage(HttpStatus status, String message, String error) {
		super();
		this.status = status;
		this.message = message;
		errors = Arrays.asList(error);
	}
	public DetailedMessage(Boolean success, String message, List<String> errors) {
		super();
		this.success = success;
		this.message = message;
		this.errors = errors;
	}


}
