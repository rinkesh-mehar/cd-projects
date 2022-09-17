package in.cropdata.cdtmasterdata.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface AgriStageVO {
	
	public int getId();

	public String getName();

	public String getStressID();
	
	public String getDescription();
	
	public String getStatus();

}
