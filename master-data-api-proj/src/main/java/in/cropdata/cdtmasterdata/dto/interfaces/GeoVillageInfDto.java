package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public interface GeoVillageInfDto {

	public Integer getId();

	public Integer getStateCode();

	public Integer getDistrictCode();

	public Integer getPanchayatCode();

	public Integer getVillageCode();

	public Integer getRegionId();

//	public Integer getSubRegionId();
//
//	public Integer getVillageVersion();

//	public double getLatitude();
//
//	public double getLongitude();

	public Integer getTehsilCode();

//	public int getPIN();

	public String getDistrict();

	public String getState();

	public String getName();

	public String getTehsil();

//	public Date getUpdatedAt();
//
//	public Date getCreatedAt();

	public String getStatus();

}
