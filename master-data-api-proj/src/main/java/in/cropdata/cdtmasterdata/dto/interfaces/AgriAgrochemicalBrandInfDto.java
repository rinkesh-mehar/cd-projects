package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriAgrochemicalBrandInfDto {
	
	public Integer getID();
	
	public String getName();
	
	public Integer getCompanyID();
	
	public String getCompany();
	
	public Integer getAgrochemicalID();
	
	public String getAgrochemical();
	
	public String getAgrochemicalStatus();
	
	public String getStatus();
	
	public Date getUpdatedAt();
	
	public Date getCreatedAt();

	Boolean getIsValid();

	String getErrorMessage();

}
