package in.cropdata.cdtmasterdata.website.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.website.model.vo
 * @date 19/09/20
 * @time 12:03 PM
 */

@JsonInclude(value = Include.NON_NULL)
public interface RlUserVo
{
	public Integer getId();

	public Integer getRegionId();

	public String getRoleName();

	public String getName();

	public String getEmail();

	public String getMobileNumber();

	public String getAadharNo();

	public String getPanNo();

	public String getUserImageUrl();

	public String getAadharImageUrl();

	public String getPanImageUrl();

	public String getDrivingLicenseImageUrl();

	public String getAggrementAccepted();

	public String getStatus();

	public String getBankName();

	public String getAccountNumber();

	public String getIFSCCode();

	public String getBankImageUrl();
	
	public String getRegionName();
}
