package in.cropdata.gateway.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vivek Gajbhiye
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseEntity {

	private boolean status;
	private String message;
	private String error;
	

}
