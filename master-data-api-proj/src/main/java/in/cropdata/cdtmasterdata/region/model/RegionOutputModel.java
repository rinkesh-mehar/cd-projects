package in.cropdata.cdtmasterdata.region.model;

import java.util.List;

import lombok.Data;

@Data
public class RegionOutputModel {
	 private String state;
	 private List<String> district;
	 private List<String> village;
	 private List<String> subRegion;
	 private int stateCount;
	 private int districtCount;
	 private int totalSubRegion;
	 private int villageCount;	
}
