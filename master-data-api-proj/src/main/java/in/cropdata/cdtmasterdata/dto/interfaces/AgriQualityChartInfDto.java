package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriQualityChartInfDto {

	public Integer getID();

//	public String getName();

	public Integer getCommodityID();

	public String getCommodity();

	public Integer getPhenophaseID();

	public String getPhenophase();

	public Integer getHealthParameterID();

	public String getHealthParameter();

	public String getGradeI();

	public String getGradeII();

	public String getGradeIII();

//	public Float getMinBandI();
//
//	public Float getMaxBandI();
//
//	public Float getMinBandII();
//
//	public Float getMaxBandII();
//	
//	public Float getMinBandIII();
//
//	public Float getMaxBandIII();
	
	public Float getMingradeI();

	public Float getMaxgradeI();

	public Float getMingradeII();

	public Float getMaxgradeII();
	
	public Float getMingradeIII();

	public Float getMaxgradeIII();
	
	public String getStatus();
}
