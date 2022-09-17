package in.cropdata.cdtmasterdata.dto.interfaces;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.dto.interfaces
 * @date 28/07/20
 * @time 11:47 AM
 * To change this template use File | Settings | File and Code Templates
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public interface AliasDto {

	public Integer getId();

	public String getAlias();

	public Integer getStateCode();

	public String getStateName();

	public Integer getDistrictCode();

	public String getDistrictName();

	public Integer getTehsilCode();

	public String getTehsilName();
}
