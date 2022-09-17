package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriHealthParameterInfDto {
	
	public Integer getId();
	
	public Integer getCommodityId();
	
	public String getCommodity();
	
	public Integer getPhenophaseId();
	
	public String getPhenophase();
	
	public String getName();
	
	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

}
