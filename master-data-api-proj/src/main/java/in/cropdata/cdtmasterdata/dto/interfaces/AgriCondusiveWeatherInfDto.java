package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriCondusiveWeatherInfDto {

	public Integer getID();

	public Integer getCommodityID();

	public String getCommodity();

	public Integer getStressTypeID();

	public String getStressType();

	public Integer getStressID();

	public String getStress();

	public String getStatus();

	public Integer getPrimaryWeatherParameterID();

	public String getPrimaryWeatherParameter();

	public Integer getSecondaryWeatherParameterID();

	public String getSecondaryWeatherParameter();

//	public double getPrimarySpecificationAverage();
//	
//	public double getPrimarySpecificationLower();
//	
//	public double getPrimarySpecificationUpper();
//	
	public Integer getPrimaryStressDurationPast();

	public Integer getPrimaryStressDurationFuture();

//	
//	public double getSecondarySpecificationAverage();
//	
//	public double getSecondarySpecificationLower();
//	
//	public double getSecondarySpecificationUpper();
//	
	public Integer getSecondaryStressDurationPast();

	public Integer getSecondaryStressDurationFuture();

//	public String getPrimaryWeatherParamName();

//	public String getSecondaryWeatherParamName();

	public String getPrimarySpecificationAverage();

	public String getPrimarySpecificationLower();

	public String getPrimarySpecificationUpper();

	public String getPrimaryStressDuration();

	public String getSecondarySpecificationAverage();

	public String getSecondarySpecificationLower();

	public String getSecondarySpecificationUpper();

	public String getSecondaryStressDuration();

	Boolean getIsValid();

	String getErrorMessage();
}
