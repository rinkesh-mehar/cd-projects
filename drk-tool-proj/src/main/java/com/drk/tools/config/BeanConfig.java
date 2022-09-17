/**
 * 
 */
package com.drk.tools.config;

//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobServiceClient;
//import com.azure.storage.blob.BlobServiceClientBuilder;
import com.drk.tools.util.ResponseMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.toolsuite.sdk.FileManagerUtil;

/**
 * @author cropdata-user
 *
 */
@Configuration
public class BeanConfig {

	@Autowired
	AppConfig appConfig;

	@Bean
	public ResponseMessageUtil getMessageUtil() {
		return new ResponseMessageUtil();
	}

	@Bean
//   @LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	@Bean
	public FileManagerUtil getFileManagerUtil() {
		String filemanagerURL = appConfig.getFileManagerURL();// "http://api.cropdatadev.tk/file-manager/";
		return new FileManagerUtil(filemanagerURL, restTemplate);
	}
	
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.writer().withDefaultPrettyPrinter();
		return mapper;
	}

	/**
	 * @author Rinkesh KM
	 * @beanNote handle azure blob storage
	 * @return
	 */
//	@Bean
//	public BlobContainerClient containerClient() {
//		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//				.connectionString(appConfig.azureConnectingString).buildClient();
//
//		return blobServiceClient.getBlobContainerClient(appConfig.azureContainerRootPath);
//	}

}
