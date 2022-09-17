package in.cropdata.toolsuite.drk.model.masterdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

/**
 * @author RinkeshKM
 * @project DRK
 * @created 25/02/2021 - 12:57 PM
 */

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ParameterBandDetails {

    List<ParameterBandDetails> agriQualityParameterList;

    Integer zonalCommodityID;

    private Integer parameterID;

    private Float value;

//    private Integer stateCode;

    private Integer aczID;

    private Integer sowingWeek;
}
