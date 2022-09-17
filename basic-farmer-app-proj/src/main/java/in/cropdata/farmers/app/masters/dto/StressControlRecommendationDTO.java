package in.cropdata.farmers.app.masters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public interface StressControlRecommendationDTO {
    @JsonProperty("ID")
    Integer getId();

    String getStressControlMeasureName();

    String getRecommendation();
}
