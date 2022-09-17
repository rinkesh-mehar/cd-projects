package in.cropdata.gateway.response;

import org.springframework.stereotype.Component;

/**
 * @author Vivek Gajbhiye
 *
 */
@Component
public class ApiResponse {

	public ApiResponseEntity apiResponse(boolean status, String message, String error) {
		ApiResponseEntity entity = new ApiResponseEntity();
		entity.setStatus(status);
		if (entity.isStatus()) {
			entity.setMessage(message);
		} else {
			entity.setError(error);
		}
		return entity;
	}

}