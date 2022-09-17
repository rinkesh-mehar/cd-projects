package in.cropdata.cdtmasterdata.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface AgriStateCommodityVO {
	
	public int getId();

	public String getState();

	public String getCommodity();
	
	public String getStatus();

}
