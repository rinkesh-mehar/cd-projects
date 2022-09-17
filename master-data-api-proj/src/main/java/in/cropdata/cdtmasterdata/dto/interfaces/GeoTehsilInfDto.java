package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public interface GeoTehsilInfDto {

	public Integer getId();

	public Integer getStateCode();

	public Integer getDistrictCode();

	public Integer getTehsilCode();

	public String getDistrict();

	public String getState();

	public String getName();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

}
