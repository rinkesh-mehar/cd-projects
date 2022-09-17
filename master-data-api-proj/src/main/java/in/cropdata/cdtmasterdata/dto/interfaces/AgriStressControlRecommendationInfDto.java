package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriStressControlRecommendationInfDto {

	public Integer getId();

	public String getCommodity();

	public String getStressControlMeasure();

	public String getStress();

	public String getStatus();

	public String getAgrochemical();

	public String getStressType();

	public String getAgroApplicationType();

	public String getInstructions();

	public String getPerHectareUOM();

	public String getPerAcreUOM();

	public String getPerHectareWaterUOM();

	public String getPerAcreWaterUOM();

	public String getDosePerAcre();

	public String getDosePerHectare();

	public String getWaterPerAcre();

	public String getWaterPerHectare();

	public String getAgroChemicalInstructions();

	Boolean getIsValid();

	String getErrorMessage();
	
	public String getRecommendationName();
	
	public String getState();
	
	public String getDistrict();

}
