package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriSeedTreatmentInfDto {

	public Integer getId();

	public Integer getCommodityId();

	public Integer getVarietyId();

	public Integer getUomId();

	public String getDose();

	public String getName();

	public String getVariety();

	public String getNote();

	public String getCommodity();

	public String getUom();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();

}
