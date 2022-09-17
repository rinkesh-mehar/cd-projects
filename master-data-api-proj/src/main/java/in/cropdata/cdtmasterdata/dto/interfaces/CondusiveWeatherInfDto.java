package in.cropdata.cdtmasterdata.dto.interfaces;

public interface CondusiveWeatherInfDto {

	public Integer getCommodityId();

	public Integer getCondusiveStartWeek();

	public Integer getCondusiveEndWeek();

	public String getPrimaryWeatherParamName();

	public String getSecondaryWeatherParamName();

	public String getPrimarySpecificationAverage();

	public String getPrimarySpecificationLower();

	public String getPrimarySpecificationUpper();

	public String getPrimaryStressDuration();

	public String getSecondarySpecificationAverage();

	public String getSecondarySpecificationLower();

	public String getSecondarySpecificationUpper();

	public String getSecondaryStressDuration();

	public String getBioticStress();

}
