package in.cropdata.cdtmasterdata.dto.interfaces;

public interface GeneralBankBranchInf {
	
	public Integer getID();
	
	public Integer getBankId();
	
	public String getBank();
	
	public String getName();
	
	public String getIfscCode();
	
	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();
}
