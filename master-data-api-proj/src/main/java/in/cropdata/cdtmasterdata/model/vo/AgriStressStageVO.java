package in.cropdata.cdtmasterdata.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface AgriStressStageVO {
	
	public int getId();

	public String getStress();

	public String getStage();
	
	public String getStatus();

}
