package in.cropdata.toolsuite.drk.model.mbep;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PmpResponse {
	
	private Integer stateCode;
	private Integer districtCode;
	private Integer commodityID;
	private Integer varietyID;
	private Double pmp;

}
