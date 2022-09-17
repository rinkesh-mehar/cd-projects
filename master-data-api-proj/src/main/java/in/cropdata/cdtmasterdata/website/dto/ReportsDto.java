package in.cropdata.cdtmasterdata.website.dto;

/**
 * Used for fetching reports data.
 * 
 * @author PranaySK
 * @Date 22-07-2020
 */

public interface ReportsDto {
	public Integer getId();

	public String getPlatform();

	public String getTitle();

	public String getFileUrl();

	public String getStatus();
	
	public String getAuthenticate();
}
