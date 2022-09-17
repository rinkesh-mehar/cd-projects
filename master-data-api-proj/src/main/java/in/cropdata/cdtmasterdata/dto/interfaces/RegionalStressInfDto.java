package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface RegionalStressInfDto {

	public Integer getId();

	public Integer getRegionId();

	public Integer getStateCode();

	public Integer getStressId();

	public String getRegion();

	public String getStress();

	public String getState();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();
	Boolean getIsValid();

	String getErrorMessage();
}
