package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriCommodityStressStageInfDto {

    Integer getId();

    Integer getCommodityId();

//	 int getRegionId();

//	 int getPhenophaseId();

    Integer getStressTypeId();

    Integer getStressId();

    String getStartPhenophaseName();

    String getEndPhenophaseName();

    String getDescription();

    String getRegion();

    String getName();

    String getStress();

    String getCommodity();

    String getStressType();

    String getPhenophase();

    Date getUpdatedAt();

    Date getCreatedAt();

    String getStatus();

    Boolean getIsValid();

    String getErrorMessage();

}
