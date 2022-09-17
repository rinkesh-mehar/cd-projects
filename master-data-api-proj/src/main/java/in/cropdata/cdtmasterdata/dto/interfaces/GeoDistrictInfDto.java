package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GeoDistrictInfDto {

	public Integer getId();

	public Integer getStateCode();

	public Integer getDistrictCode();

	public String getState();

	public String getName();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

}
