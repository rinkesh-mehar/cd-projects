package in.cropdata.cdtmasterdata.dto.interfaces;

import java.util.Date;

public interface AgriFavourableWeatherInfDto {
	
	public Integer getID();
	
	public Integer getCommodityID();
	
	public String getCommodity();
	
	public Integer getPhenophaseID();
	
	public String getPhenophase();
	
	public Integer getWeatherParameterID();
	
	public String getWeatherParameter();
	
	public Double getSpecificationAverage();
	
	public Double getSpecificationLower();
	
	public Double getSpecificationUpper();
	
	public Date getCreatedAt();
	
	public Date getUpdatedAt();
	
	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();

}
