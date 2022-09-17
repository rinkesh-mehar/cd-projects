package in.cropdata.farmers.app.masters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 4:11 PM
 */
public interface FieldActivityDTO {

    @JsonProperty("ID")
    Integer getID();

    String getName();

    String getDescription();
}
