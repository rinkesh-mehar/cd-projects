/**
 * 
 */
package in.cropdata.aefc.utils;


import in.cropdata.aefc.model.ResponseMessage;

public class ResponseMessageUtil
{
	public ResponseMessage sendResponse(boolean isSuccess, String message, int errorCode) {

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(isSuccess);
		if (isSuccess) {
			responseMessage.setMessage(message);
		} else {
			responseMessage.setStatusCode(errorCode);
		}
		return responseMessage;
	}

	public ResponseMessage sendResponse(boolean isSuccess, String message) {

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setStatus(isSuccess);
		if (isSuccess) {
			responseMessage.setMessage(message);
		}
		return responseMessage;
	}
}
