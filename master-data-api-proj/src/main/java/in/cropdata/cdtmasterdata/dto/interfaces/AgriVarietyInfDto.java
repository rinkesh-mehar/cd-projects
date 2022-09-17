package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public interface AgriVarietyInfDto {

	public Integer getId();

	public Integer getCommodityId();

	public Integer getHsCodeId();

	public String getCommodity();

	public String getName();

	public String getVarietyCode();

	public String getHsCode();

//	public Integer getMspGroupId();
//	
//	public String getMspGroup();

	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();

	public String getDomesticRestrictions();

	public String getInternationalRestrictions();

	Boolean getIsValid();

	String getErrorMessage();
}
