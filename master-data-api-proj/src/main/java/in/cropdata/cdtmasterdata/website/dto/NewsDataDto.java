package in.cropdata.cdtmasterdata.website.dto;

/**
 * Used for fetching news data.
 * 
 * @author PranaySK
 * @Date 22-07-2020
 */

public interface NewsDataDto
{
	public Integer getId();

	public String getPlatform();

	public String getSubject();

	public String getDescription();

	public String getExternalUrl();

	public String getImageUrl();

	public String getPublishedDate();

	public String getStatus();
	
	public Integer getPriority();
}
