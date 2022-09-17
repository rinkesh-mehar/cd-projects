package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriRemedialMeasuresInfDto {

	public Integer getId();

	public Integer getStateCode();

	public Integer getCommodityId();

	public Integer getCompanyId();

	public Integer getVarietyId();

	public Integer getStressTypeId();

	public Integer getAgrchemicalTypeId();

	public String getRemedialStatus();

	public String getStressTypeName();

	public String getAgrochemicalTypename();

	public String getCommodity();

	public String getVariety();

	public String getName();

	public String getBrandName();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

	public String getBioticStressId();

	public String getStressStateCode();

	public String getStressCommodityId();

	public String getBioticStressTypeId();

	public String getBioticStressName();

	public String getScientificName();

	public String getStartPhenophaseId();

	public String getEndPhenophaseId();

	public String getSymptoms();

	public String getAgrochemicalId();

	public String getAgrochemicalTypeId();

	public String getagrochemicalName();

	public String getWaitingPeriod();

	public String getCommodityIdForAgrochemical();

	public String getAgroStressTypeId();

}
