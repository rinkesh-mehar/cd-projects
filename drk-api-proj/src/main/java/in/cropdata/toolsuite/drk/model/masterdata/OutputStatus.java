package in.cropdata.toolsuite.drk.model.masterdata;

import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class OutputStatus {
	private String id;
	private String msg;
	private String publicUrl;
	private String errCode;
	private boolean status;
	private long timestamp;

}