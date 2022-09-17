package in.cropdata.portal.gstmDataModels.dto;


import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.gstmDataModels.dto
 * @date 14/08/20
 * @time 11:46 AM
 * To change this template use File | Settings | File and Code Templates
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface WinnerMarketDTO
{

    public Integer getID();

    public String getStateCode();

    public String getDistrictCode();

    public Integer getCommodityID();

    public String getPricingAgriVarietyID();

    public String getMarketID();

    public String getMinPrice();

    public String getMaxPrice();

    public String getModalPrice();

    public String getRrivalDate();

    public String getStateName();

    public String getDistrictName();

    public String getCommodityName();

    public String getMarketName();

    public String getVarietyName();

    public String getPricingAgriVarietyName();

    public String getMinPriceChangePercentage();

    public String getMaxPriceChangePercentage();

    public String getModalPriceChangePercentage();

    public Date getArrivalDate();

    public Date getDates();

}
