package com.drk.tools.util;

import com.drk.tools.dto.ResponseMessage;

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
