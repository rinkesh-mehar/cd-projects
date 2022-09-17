package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriVarietyStressInfDto {

	 Integer getId();

	 Integer getStateCode();

	 Integer getRegionId();

	 Integer getCommodityId();

	 Integer getStressTypeId();

	 Integer getVarietyId();

//	 int getResistantStressId();
//
//	 int getSusceptibleStressId();
//
//	 int getTolerantStressId();

	 String getState();

	 String getRegion();

	 String getVariety();

	 String getCommodity();

	 String getStressType();

	 String getResistantStress();

	 String getSusceptibleStress();

	 String getTolerantStress();

	 Date getUpdatedAt();

	 Date getCreatedAt();

	 String getStatus();

	 String getDescription();

	 String getName();

     Boolean getIsValid();

	String getErrorMessage();

}
