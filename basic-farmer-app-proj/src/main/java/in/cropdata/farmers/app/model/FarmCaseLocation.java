/**
 * 
 */
package in.cropdata.farmers.app.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;



/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
public class FarmCaseLocation implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;


	@JsonProperty("AczID")
	public Integer aczID;
	@JsonProperty("DistrictCode")
	public Integer districtCode;
	@JsonProperty("RegionID")
	public Integer regionID;
	@JsonProperty("StateCode")
	public Integer stateCode;
	@JsonProperty("SubRegionID")
	public String subRegionID;
	@JsonProperty("TehsilCode")
	public Integer tehsilCode;
	@JsonProperty("Tile14")
	public String tile14;
	@JsonProperty("VillageCode")
	public Integer villageCode;

}
