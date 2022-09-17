package in.cropdata.toolsuite.drk.dto;

import lombok.Data;

@Data
public class QualitySpread {
	
	private String date;
	private Integer regionId;
	private Integer commodityId;
	private Integer varietyId;
	private Integer prevBandId;
	private Integer curBandId;

}
