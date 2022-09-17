package in.cropdata.cdtmasterdata.region.model;

import lombok.Data;

import java.util.List;

@Data
public class CreateRegion {
	private String _regionName ;
	private String _desc ;
	private String regionId;
	private Integer state;
	private Integer district;
	private String address;
	private String pin;
	private String mobileNo;
	private String contactPerson;
	private String lat;
	private String log;
	private String sourceId;
	private List<String> _subregions; 

}
