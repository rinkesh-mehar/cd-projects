package in.cropdata.cdtmasterdata.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model.vo
 * @date 09/11/20
 * @time 3:28 PM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RegionalMonthWBTravelTimeVO
{
     Integer       getId();

     Integer getRegionID();

     String     getMonth();

     Integer getUnitType();

     String  getUnitName();

     String getRegionName();
    Boolean getIsValid();
    String getErrorMessage();
}
