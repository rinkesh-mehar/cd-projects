package com.krishi.model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @created 20/Dec/2021-12:26 PM
 */

public interface ViewRightsModel {

    String getRightId();

    Integer getRightsVersion();

    String getCaseId();

    String getPennydropStatus();

    Float getCurrentQty();

    Float getEstimatedQty();

    Float getStandardQty();

    Float getAllowableVarianceInqtyPos();

    Float getAllowableVarianceInqtyNeg();

    Float getAllowableVarianceInqtyPosPer();

    Float getAllowableVarianceInqtyNegPer();

    String getEstimatedQly();

    String getCurrentQly();

    String getAllowableVarianceInqtyGrade();

    Double getMbep();


    String getDomrestrictions();


    String getIntRestrictions();


    Date getDelDateTime();


    String getLogisticHubId();


    String getLogisticHubAddress();


    String getFarmerDefault();


    String getSplitField();


    String getGeoAdjacent();


    String getRiskReport();


    String getRightCertificate();


    Integer getRecordBy();


    Timestamp getRecordDate();


    Date getStatusReceivingDate();


    String getLotId();


    String getTransactionId();


    Integer getPhenophaseId();


    String getStage();


    Float getDueMoneyByFarmer();


    Double getContractedPrice();


    String getRightSignUrl();


    String getCropType();


    Integer getShiftId();


    Boolean getFamerKycVerified();


    Boolean getCaseKmlVerified();


    String getStatus();


    Boolean getIsVerified();

    Double getAmountCollected();

    String getComments();

    String getInternationalRestriction();

}
