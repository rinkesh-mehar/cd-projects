package in.cropdata.cdtmasterdata.dto.interfaces;

public interface AgriFieldActivityInfDto {

	public Integer getId();

	public Integer getCommodityId();

//	public int getRegionId();

	public Integer getSeasonId();

	public Integer getPhenophaseId();

	public String getSeason();

	public String getDescription();

	public String getCommodity();

//	public String getRegion();

	public String getPhenophase();

	public String getName();

//	public Date getUpdatedAt();
//
//	public Date getCreatedAt();

	public String getStatus();

	Boolean getIsValid();

	String getErrorMessage();
	
	String getImageURL();
	
	

}
