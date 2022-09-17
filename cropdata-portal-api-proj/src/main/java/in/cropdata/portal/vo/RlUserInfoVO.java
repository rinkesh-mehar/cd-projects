package in.cropdata.portal.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * Used for fetching RL User data.
 * 
 * @author PranaySK
 * @Date 02-09-2020
 */

@JsonInclude(value = Include.NON_NULL)
public interface RlUserInfoVO {
	public Integer getId();

	public Integer getRegionId();

	public String getRegionName();

	public String getDocumentUrl();

	public String getStatus();
}
