package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriQuantityLossChartInfDto {

	public Integer getId();

	public Float getMinQuantityCorrectionPercent();
	
	public Float getMaxQuantityCorrectionPercent();

	public Float getMinBandValue();

	public Float getMaxBandValue();

//	public Float getActualQuantityCorrectionPercent();

	public String getBandName();

	public String getStress();

	public String getCommodity();

	public String getPhenophase();

//	public int getStressId();

//	public int getCommodityID();

//	public int getPhenophaseID();

	public String getStatus();

	public String getCriteria();

	Boolean getIsValid();

	String getErrorMessage();

}
