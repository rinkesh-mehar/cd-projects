package com.drk.tools.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AppConfig {

	@Value("${right-add-update.url}")
	public String addUpdateRightURL;

    @Value("${file-manager.url}")
	private String fileManagerURL;

	@Value("${quantity-calculator.hours}")
	private long hours;

//	@Value("${home.dir}")
	private String homeDir = "D:\\Toolsuite\\${spring.application.name}";

//	@Value("${path.dir.media}")
	private String mediaFilesDir = "D:\\Toolsuite\\DrK_Tools_API\\media";

//	@Value("${path.dir.zip}")
	private String zipFilesDir = "D:\\Toolsuite\\DrK_Tools_API\\zips";
	
	@Value("${gstm.py.url}")
	private String gstmPyUri;

//	@Value("${extract-all-path}")
	public String extractAllPath = "D:\\Toolsuite\\Flipbook_Temp\\extract-all";

//	@Value("${excel-file}")
	public String excelPath = "D:\\Toolsuite\\Flipbook_Temp\\extract-all\\excel-file";

	@Value("${drk-bm-api-url}")
	public String bmApiUrl;

	@Value("${drk-api-key}")
	public String apiKey;

	@Value("${blob-env-url}")
	public String blobEnvUrl;

	@Value("${azure-connecting-string}")
	public String azureConnectingString;

	@Value("${azure-container-root-path}")
	public String azureContainerRootPath;

	@Value("${azure-module-name}")
	public String azureModuleName;
	
	@Value("${bfa-api-key}")
	public String bfaApiKey;
	
	@Value("${bfa-app-key}")
	public String bfaAppKey;
	
	@Value("${bfa-notification-api-url}")
	public String bfaNotificationApiUrl;

	@Value("${zip.file.url-all-symptom}")
	public String zipFileUrlAllSymptom;

}