package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface RegionalSeasonInfDto {

	public Integer getId();

	public Integer getRegionId();

	public Integer getSeasonId();
	
	public Integer getStateCode();
	
	public String getState();

	public Integer getStartWeek();

	public Integer getEndWeek();

	public String getRegion();

	public String getSeason();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();
}
