package in.cropdata.cdtmasterdata.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface AgriQualityCommodityParameterVO {
	
	public Integer getId();
	
	public int getCommodityId();

	public String getCommodity();

	public String getQualityParameter();
	
	public String getStatus();

}
