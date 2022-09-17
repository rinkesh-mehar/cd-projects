package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GeneralWeatherParameterDto {

    Integer getId();

    String getName();

    String getLabel();

    String getUnit();

    String getstatus();

}
