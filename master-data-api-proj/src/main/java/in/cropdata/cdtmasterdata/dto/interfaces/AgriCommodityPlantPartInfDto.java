package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriCommodityPlantPartInfDto {

    Integer getId();

    Integer getCommodityId();

    Integer getPhenophaseId();

    Integer getPlantPartId();

    String getPlantPart();

    String getCommodity();

    String getImageURL();

    String getPhenophase();

    Date getUpdatedAt();

    Date getCreatedAt();

    String getStatus();

    Boolean getIsValid();

    String getErrorMessage();

}
