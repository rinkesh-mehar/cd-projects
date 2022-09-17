package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface PricingMspGroupInfo {

    Integer getID();

    Integer getCommodityID();

    String getCommodity();

    String getName();

//	public Date getUpdatedAt();
//
//	public Date getCreatedAt();

    String getStatus();

    Integer getStateCode();

    Integer getRegionID();

    String getStateName();

    String getRegionName();

    String getCommodityName();

    Integer getVarietyID();

    String getVarietyName();

    Integer getGradeID();

    String getGradeName();

    Integer getMsp();

    String getIsBenchmark();

    String getMfp();

    String getMarginConstant();

    String getBuyerConstant();

    String getSellerConstant();

    String getMbep();

    String getPmp();

    String getPriceSpread();

    Double getSlopeMin();

    Double getSlopeMax();
}
