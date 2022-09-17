package in.cropdata.cdtmasterdata.region.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.region.vo
 * @date 12/09/20
 * @time 11:29 AM
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface panchayatMapVO
{
    public String getName();

    public int getRegionId();
    Boolean getIsValid();

    String getErrorMessage();


}
