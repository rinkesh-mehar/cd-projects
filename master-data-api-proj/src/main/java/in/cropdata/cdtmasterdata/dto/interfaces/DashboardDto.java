package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigInteger;

/**
 * @author RinkeshKM
 * @Date 14/09/20
 */

@JsonInclude(value = JsonInclude.Include.NON_NULL)
public interface DashboardDto {

    BigInteger getTotalLeads();

    BigInteger getCurrentLeads();

    Integer getVerifiedLeads();

    Integer getRejectedLeads();

    Integer getRegionId();
    
    Integer getId();

    String getCommodityId();

    String getName();

    String getRegionName();

    String getCommodityName();

    Integer getCommId();

    BigInteger getCurrentCropArea();

    BigInteger getTotalCropArea();

}
