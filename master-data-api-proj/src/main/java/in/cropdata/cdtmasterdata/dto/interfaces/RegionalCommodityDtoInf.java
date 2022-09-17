package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RegionalCommodityDtoInf {
	
	public Integer getID();

	public Integer getRegionalCommodityID();

	public Integer getRegionID();

	Integer getSubRegionID();

	Integer getMsp();

	Integer getCommodityID();

	Integer getVarietyID();

	List<Integer> getStateID();
	
	public Integer getSeasonID();
	
	public String getSeason();

	public String getCommodity();
	
	public Integer getStateCode();
	
	public String getState();
	
	public String getCommodityName();
	
	public Integer getTargetValue();
	
	public Integer getMinLotSize();
	
	public Integer getMaxRigtsInLot();
	
	public Integer getHarvestRelaxation();
	
//	public Date getUpdatedAt();
//
//	public Date getCreatedAt();

	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();
}
