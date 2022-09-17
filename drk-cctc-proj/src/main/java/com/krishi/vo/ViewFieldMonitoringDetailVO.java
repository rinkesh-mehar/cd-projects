package com.krishi.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.sql.Date;

/**
 * Used for fetching Field Monitoring data.
 *
 * @author Rinkesh KM
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public interface ViewFieldMonitoringDetailVO {

    Integer getTaskId();

    String getRightId();

    String getEntityId();

    String getTaskTypeId();

    String getTaskDate();

    String getCropName();

    String getVarietyName();

    Double getCropArea();

    Integer getSowingWeek();

    Integer getHarvestWeek();

    Integer getHarvestYear();

    Integer getSowingYear();

    String getSeedName();

    Boolean getSeedsSampleReceived();

    Double getSeedsRates();

    String getSeedUnitName();

    Double getSpacingRow();

    Double getSpacingPlant();

    String getIrrigationSourceId();

    String getIrrigationMethodId();

    Integer getWeekOfIrrigation();

    Integer getYearOfIrrigation();

    Integer getNumberOfIrrigation();

    String getFertilizerTypeOfApplication();

    String getFertilizerName();

    String getFertilizerUnitName();

    Integer getFertilizerDose();

    String getFertilizerSplitDose();

    Integer getFertilizerWeekOfApplcation();

    Integer getFertilizerYearOfApplcation();

    Boolean getSeedTreatment();

    String getSeedTreatmentUnitName();

    Integer getSeedTreatmentDose();

    Integer getSeedTreatmentAgentId();

    String getAgrochemicalBrandName();

    String getAgrochemicalName();

    String getAgrochemicalUnitName();

    Integer getAgrochemicalDose();

    Integer getAgrochemicalWeekOfApplcation();

    Integer getAgrochemicalYearOfApplcation();

    String getFarmerPrimaryMobNumber();

    String getFarmerAlternativeMobNumber();

    Date getNextMonitoringDate();

    String getStateName();

    String getRegionName();

    String getDistrictName();

    String getTehsilName();

    String getVillageName();

    String getFarmerName();

    Integer getCurrentQuantity();

    String getHarvestDate();

    Integer getAllowableVarianceQtyPos();

    Integer getAllowableVarianceQtyNeg();

    Date getMinDate();

    Date getMaxDate();
}
