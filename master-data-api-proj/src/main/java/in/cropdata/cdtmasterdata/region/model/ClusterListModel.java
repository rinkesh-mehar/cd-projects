package in.cropdata.cdtmasterdata.region.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClusterListModel {
	private int region;
	private String regionName;
	private String subRegion;
	private String state;
	private String district;
	private String village;

}
