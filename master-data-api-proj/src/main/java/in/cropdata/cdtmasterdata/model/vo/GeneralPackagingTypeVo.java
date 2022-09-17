package in.cropdata.cdtmasterdata.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GeneralPackagingTypeVo {
	
	public int getId();

	public String getName();

	public String getStatus();

}
