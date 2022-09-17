package in.cropdata.farmers.app.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author RinkeshKM
 * @Date 29/01/21
 */

public interface GeoDTO {

    @JsonProperty("ID")
    Integer getID();

    String getName();

    Integer getDistrictCode();

}
