package in.cropdata.cdtmasterdata.region.model;

import lombok.Data;

@Data
public class RresponseModel {
	private boolean success;
	private String errorCode;
	private String errorMsg;
	private RegionDataModel data;
}
