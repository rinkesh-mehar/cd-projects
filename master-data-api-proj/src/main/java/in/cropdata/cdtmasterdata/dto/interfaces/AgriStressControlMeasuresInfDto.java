package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriStressControlMeasuresInfDto {

	public Integer getId();

	public String getCommodity();

	public String getStress();

	public String getStatus();

//	public int getCommodityID();
//	
//	public int getStressID();
//	
//	public int getStressSeverityID();
//	
//	public int getStressControlMeasureID();
//
	public String getStressSeverity();

	public String getStressControlMeasure();

	Boolean getIsValid();

	String getErrorMessage();
}
