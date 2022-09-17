package in.cropdata.portal.dto;

import lombok.Data;

import java.util.Map;

@Data
public class ResponseMessage {
	
	private boolean success;
	private String message;
	private String error;
	private Map<String,Object> data;

}
