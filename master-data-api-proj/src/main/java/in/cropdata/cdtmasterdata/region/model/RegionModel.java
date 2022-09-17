package in.cropdata.cdtmasterdata.region.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionModel {
	 private String state;
	 private String district;
	 private String village;
	 private String subRegion;
	 private int stateCount;
	 private int districtCount;
	 private int totalSubRegion;
	 private int villageCount;	
}
