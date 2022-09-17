package in.cropdata.toolsuite.drk.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AppProperties {

	@Value("${ts.file-manager.url}")
	private String fileManagerURL;

//	@Value("${security.api-key}")
//	public String apiKeyProperty;

	@Value("${security.agriota.api-key}")
	public String apiKeyAgriota;

//	@Value("${security.drkrishi.api-key}")
//	public String apiKeyCdt;

	@Value("${ts.file-manager.serviceID}")
	public String fileManagerServiceID;

	@Value("${ts.mbep-api.serviceID}")
	public String mbepService;

	@Value("${ts.mbep-api.url}")
	public String mbepUrl;
	
	@Value("${media.file.id}")
	private String mediaFileId;

	/**
	 * @return the fileManagerURL
	 */
	public String getFileManagerURL() {
		System.err.println("FileManagerURL: " + fileManagerURL);
		if (fileManagerURL != null && !fileManagerURL.endsWith("/")) {
			fileManagerURL += "/";
		}
		return fileManagerURL;
	}

}
