package in.cropdata.cdtmasterdata.region.model;

import lombok.Data;

@Data
public class RegionDataModel {
	private String stateName;
	private String imageID;
	private GeoPointModel centroids;
	private String fileUrl;
}
