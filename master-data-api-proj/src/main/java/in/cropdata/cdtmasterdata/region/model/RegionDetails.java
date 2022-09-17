package in.cropdata.cdtmasterdata.region.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegionDetails {
	@JsonIgnore
	private int regionId;	
	private int subRegionId;

	
}
