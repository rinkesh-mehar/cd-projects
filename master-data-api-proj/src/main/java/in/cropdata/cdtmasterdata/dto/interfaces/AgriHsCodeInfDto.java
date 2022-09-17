package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriHsCodeInfDto {

	public Integer getId();

	public Integer getCommodityId();

	public Integer getGeneralCommodityId();

	public Integer getCommodityClassId();

	public String getHsCode();
	
	public Integer getUomId();
	
	public String getUom();

	public String getGeneralCommodity();

	public String getCommodityClass();

	public String getCommodity();

	public String getDescription();

//	public Date getUpdatedAt();
//
//	public Date getCreatedAt();

	public String getStatus();

	public Boolean getIsValid();

	String getErrorMessage();

}
