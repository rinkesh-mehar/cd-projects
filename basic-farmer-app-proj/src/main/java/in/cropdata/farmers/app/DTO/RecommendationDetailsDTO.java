package in.cropdata.farmers.app.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class RecommendationDetailsDTO {
	
	private Integer stressControlMeasureID;
	private String controlMeasure;
	private Integer recommendationID;
	private String recommendation;
	private Integer stressID;
	private String stressName;
	private Integer symptomID;
	
}
