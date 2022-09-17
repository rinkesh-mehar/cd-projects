package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriAgrochemicalInfDto {
	
	public Integer getID();
	
	public Integer getCommodityID();
	
	public String getCommodity();
	
	public Integer getAgrochemicalTypeID();
	
	public String getAgrochemicalType();
	
	public Integer getStressTypeID();
	
	public String getStressType();
	
	public String getCIBRCApproved();
	
	public String getName();
	
	public Integer getWaitingPeriod();
	
	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();

}
