package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

/**
 * @author RinkeshKM
 * @Date 09/11/20
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GeneralTerrainTypeDto {

    Integer getId();

    Integer getRegionId();

    String getRegionName();

    String getTerrainType();

    String getMinPerKm();

    String getStatus();

    Boolean getIsValid();

    String getErrorMessage();

}
