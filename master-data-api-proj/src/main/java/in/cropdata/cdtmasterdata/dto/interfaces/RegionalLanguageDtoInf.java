package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface RegionalLanguageDtoInf {
	
	public Integer getID();
	
	public Integer getStateCode();
	
	public String getState();
	
	public Integer getRegionID();
	
	public String getRegion();
	
	public Integer getLanguageID();
	
	public String getLanguage();
	
	public Date getUpdatedAt();

	public Date getCreatedAt();

	public String getStatus();
	Boolean getIsValid();

	String getErrorMessage();

}
