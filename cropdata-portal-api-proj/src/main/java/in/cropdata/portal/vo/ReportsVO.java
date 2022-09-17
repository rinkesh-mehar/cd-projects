package in.cropdata.portal.vo;

/**
 * Used for fetching reports data.
 * 
 * @author PranaySK
 * @Date 22-07-2020
 */

public interface ReportsVO {
	public Integer getId();

	public Integer getPlatformID();

	public String getPlatform();

	public String getTitle();

	public String getFileUrl();

	public String getStatus();
	
	public String getAuthenticate();
}
