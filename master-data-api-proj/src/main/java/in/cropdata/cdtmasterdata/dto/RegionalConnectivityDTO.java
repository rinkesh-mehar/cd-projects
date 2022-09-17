package in.cropdata.cdtmasterdata.dto;

public interface RegionalConnectivityDTO {

	Integer getId();
	
	Integer getRegionId();

	Double getSurfacedProportion();

	Double getUnsurfacedProportion();

	Double getSurfacedTimeMin();

	Double getUnsurfacedTimeMin();

	String getStatus();

	String getRegionName();

}
