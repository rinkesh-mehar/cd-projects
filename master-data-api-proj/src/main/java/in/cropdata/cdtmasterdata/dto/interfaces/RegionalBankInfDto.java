package in.cropdata.cdtmasterdata.dto.interfaces;

public interface RegionalBankInfDto {
	
	public Integer getID();
	
	public Integer getStateCode();
	
	public String getState();
	
	public Integer getBankID();
	
	public String getBank();
	
	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();
}
