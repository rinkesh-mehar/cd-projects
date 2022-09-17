package in.cropdata.farmers.app.model;

import lombok.Data;

@Data
public class ResponseMessage {

	private boolean status;
	private String message;
	private String errorCode;
}
