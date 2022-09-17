package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface GeoStateInfDto {

	public Integer getId();

	public Integer getStateCode();

	public Integer getCountryCode();

	public String getCountry();

	public String getName();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

}
