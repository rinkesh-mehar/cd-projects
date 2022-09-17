package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface RegionalVarietyInfDto {

	public Integer getId();

	public Integer getCommodityId();

	public Integer getVarietyId();

//	public Integer getRegionId();
	
	public Integer getSeasonId();

	public Integer getSowingWeekStart();

	public Integer getSowingWeekEnd();

	public Integer getHarvestWeekStart();

	public Integer getHarvestWeekEnd();

	public String getCommodity();

	public Integer getStateCode();

	public String getState();
	
	public String getSeason();

//	public String getRegion();

	public String getVariety();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();
	
//	public int getSowingWeekStart();
//	
//	public int getSowingWeekEnd();
//	
//	public int getHarvestWeekStart();
//	
//	public int getHarvestWeekEnd();

	Boolean getIsValid();

	String getErrorMessage();
}
