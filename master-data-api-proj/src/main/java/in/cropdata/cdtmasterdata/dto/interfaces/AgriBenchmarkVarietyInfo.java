package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriBenchmarkVarietyInfo {
	
	public Integer getId();

	public Integer getStateCode();
	
	public Integer getRegionId();
	
	public Integer getSeasonId();
	
	public Integer getCommodityId();
	
	public Integer getVarietyId();
	
	public String getState();
	
	public String getRegion();
	
	public String getSeason();
	
	public String getCommodity();
	
	public String getVariety();
	
	public String getIsDrkBenchmark();
	
	public String getIsAgmBenchmark();
	
	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();
}
