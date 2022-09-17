package in.cropdata.cdtmasterdata.region.model;

import lombok.Data;

@Data
public class ResponseModel {
	private boolean success;
	private String errorCode;
	private String errorMsg;
	private Object msg;

}
