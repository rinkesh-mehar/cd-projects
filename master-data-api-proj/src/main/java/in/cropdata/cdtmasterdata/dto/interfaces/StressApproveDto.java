package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public interface StressApproveDto {

    String getID();

    String getCaseID();

    String getSymptomDescription();

    String getStressName();

    String getFarmerGivenYield();

    String getTehsilName();

    String getVillageName();

    String getStateName();

    Integer getStateCode();

    String getVarietyName();

    Integer getVarietyID();

    Integer getPhenophaseID();

    String getPhenophaseName();

    Integer getCommodityID();

    Integer getDistrictID();

    String getDistrictName();

    String getCommodityName();

    Integer getSymptomID();

    Integer getStressID();

    String getStressImage();

    Integer getStageID();

    Integer getPlantPartID();

    String getStageName();

    String getPlantPartName();

    String getFarmerGivenImage();

    Integer getStressTypeID();

    String getStressTypeName();

    String getReferenceImages();

    String getStatus();

    String getFrontPhoto();

    String getLeftPhoto();

    String getRightPhoto();

    String getSymptomImageUrl();

}
