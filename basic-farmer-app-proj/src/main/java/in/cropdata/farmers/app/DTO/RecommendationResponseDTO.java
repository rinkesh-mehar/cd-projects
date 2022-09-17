package in.cropdata.farmers.app.DTO;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class RecommendationResponseDTO {
	
	private String controlMeasure;
	private List<RecommendationDTO> recommendations;
	
}
