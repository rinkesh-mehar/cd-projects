package in.cropdata.portal.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Used for fetching news data.
 * 
 * @author PranaySK
 * @Date 22-07-2020
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface NewsVO {
	public Integer getId();

	public Integer getPlatformId();

	public String getPlatform();

	public String getTitle();

	public String getDescription();

	public String getExternalUrl();

	public String getSource();

	public String getImageUrl();

	public String getPublishedDate();

	public String getStatus();

	public String getLatestNews();
	
	public Integer getPriority();
}
