package in.cropdata.cdtmasterdata.dto.interfaces;

public interface FarmerInsuranceCompanyInfDto {
	
	public Integer getID();
	
	public String getName();
	
	public Integer getInsuranceTypeID();
	
	public String getInsuranceType();
	
	public String getStatus();
	Boolean getIsValid();

	String getErrorMessage();

}
