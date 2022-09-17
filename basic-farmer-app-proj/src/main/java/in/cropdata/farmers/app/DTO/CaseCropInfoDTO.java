package in.cropdata.farmers.app.DTO;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 11/02/2021 - 10:20 AM
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public interface CaseCropInfoDTO {

    @JsonProperty("ID")
    String getID();

    Integer getCropTypeID();

    String getCropTypeName();

    Integer getOwnershipTypeID();

    String getOwnershipTypeName();

    String getCaseID();

    String getFarmerID();

    String getFarmID();

    Integer getVarietyID();

    Integer getSeasonID();

    String getVarietyName();

    Float getCropArea();

    Integer getSowingWeek();

    Integer getSowingYear();

    Integer getHarvestWeek();

    String getCommodityName();

    Integer getCommodityID();

    Integer getIrrigationSourceID();

    String getIrrigationSourceName();

    Integer getQuantity();

    Double getLatitude();

    Double getLongitude();

    String getCaseStatus();

    Date getSowingDate();
    
    Integer getCaseStatusID();

    String getAdvisoryType();

}
