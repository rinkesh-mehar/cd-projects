/**
 * 
 */
package in.cropdata.farmers.app.utils;

import in.cropdata.farmers.app.model.ResponseMessage;

public class ResponseMessageUtil {
	public ResponseMessage sendResponse(boolean isSuccess, String message, String error) {

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(isSuccess);
		if (isSuccess) {
			responseMessage.setMessage(message);
		} else {
			responseMessage.setErrorCode(error);
		}
		return responseMessage;
	}
}
