package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriFertilizerInfDto {

	public Integer getId();

	public Integer getCommodityId();

	public Integer getStateCode();

//	public Integer getRegionId();

	public Integer getSeasonId();

	public Integer getDoseFactorId();

	public Integer getUomId();

	public double getDose();

	public String getName();

	public String getNote();

	public String getSeason();

	public String getCommodity();

//	public String getRegion();

	public String getState();

	public String getDoseFactor();

	public String getUom();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();
}
