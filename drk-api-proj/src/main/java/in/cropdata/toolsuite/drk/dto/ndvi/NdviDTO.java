/**
 * 
 */
package in.cropdata.toolsuite.drk.dto.ndvi;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NdviDTO {

    @JsonProperty("CaseId")
    private BigInteger caseId;

    @JsonProperty("Year")
    private Integer year;

    @JsonProperty("Week")
    private Integer week;

    private String url;

    private Integer ndvi;


}
