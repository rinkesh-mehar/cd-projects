package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriHealthInfDto {
	
	public Integer getID();
	
	public Integer getCommodityID();
	
	public Integer getPhenophaseID();
	
	public Integer getHealthParameterID();
	
	public String getCommodity();

	public String getPhenophase();
	
	public String getHealthParameter();
	
	public String getSpecification();
	
	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();
}
