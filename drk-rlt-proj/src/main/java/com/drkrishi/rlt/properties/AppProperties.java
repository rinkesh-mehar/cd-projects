package com.drkrishi.rlt.properties;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Value("${drk.url}")
	private String drkUrl;

	@Value("${drk.api.key}")
	private String drkApiKey;

	@Value("${aerial.photos.path}")
	private String aerialPhotoPath;

	public String getDrkUrl() {
		return drkUrl;
	}

	public void setDrkUrl(String drkUrl) {
		this.drkUrl = drkUrl;
	}

	public String getDrkApiKey() {
		return drkApiKey;
	}

	public void setDrkApiKey(String drkApiKey) {
		this.drkApiKey = drkApiKey;
	}

	public String getAerialPhotoPath() {
		return aerialPhotoPath;
	}

	public void setAerialPhotoPath(String aerialPhotoPath) {
		this.aerialPhotoPath = aerialPhotoPath;
	}
}
