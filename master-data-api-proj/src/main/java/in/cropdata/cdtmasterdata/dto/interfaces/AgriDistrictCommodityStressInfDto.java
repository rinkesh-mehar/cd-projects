package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;


public interface AgriDistrictCommodityStressInfDto {

	public Integer getId();

	public Integer getCommodityId();

	public String getCommodity();

	public Integer getStressTypeId();

	public String getStressType();

	public Integer getStartPhenophaseId();

	public String getStartPhenophase();

	public Integer getEndPhenophaseId();

	public String getEndPhenophase();

	public String getName();

	public String getScientificName();

	public String getSymptomsList();
	
	public String getSymptomsListName();
	
	public String getImageURL();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

	public Boolean getIsValid();

	Integer getStateCode();

	Integer getDistrictCode();

	Integer getErrorMessage();

	Integer getSeasonID();

	Integer getVarietyID();
	
	public String getStateName();
	
	public String getDistrictName();
	
	public String getSeasonName();
	
	public String getVarietyName();
}
