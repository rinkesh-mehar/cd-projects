package in.cropdata.cdtmasterdata.util;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;

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

}
