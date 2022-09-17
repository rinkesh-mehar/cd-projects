package in.cropdata.farmers.app.DTO;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(value = Include.NON_NULL)
public class StressDetailsDTO {
	
	private String caseID;
	private Integer symptomID;
	private String genericImage;
	private String farmerID;
	private String stressName;
	private String symptom;
	private Integer commodityID;
	private boolean reported;
	
}
