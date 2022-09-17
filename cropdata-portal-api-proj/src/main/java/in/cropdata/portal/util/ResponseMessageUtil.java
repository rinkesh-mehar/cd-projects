package in.cropdata.portal.util;

import in.cropdata.portal.dto.ResponseMessage;

import java.util.Map;

public class ResponseMessageUtil {

	public ResponseMessage sendResponse(boolean isSuccess, String message, String error) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setSuccess(isSuccess);

		if (isSuccess) {
			responseMessage.setMessage(message);
		} else {
			responseMessage.setError(error);
		}

		return responseMessage;
	}
	public ResponseMessage sendResponseWithData(boolean isSuccess, String message, Map<String,Object> data) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setSuccess(isSuccess);

		if (isSuccess) {
			responseMessage.setMessage(message);
			responseMessage.setData(data);
		} else {
		}

		return responseMessage;
	}

}
