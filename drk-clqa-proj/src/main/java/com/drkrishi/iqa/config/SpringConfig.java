package com.drkrishi.iqa.config;

//import com.azure.storage.blob.BlobContainerClient;
//import com.azure.storage.blob.BlobServiceClient;
//import com.azure.storage.blob.BlobServiceClientBuilder;
import com.drkrishi.iqa.utility.FileUtility;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import in.cropdata.toolsuite.sdk.FileManagerUtil;

@Configuration
public class SpringConfig {

	@Value("${ts.filemanager.url}")
	private String azureFileUrl;

	@Bean
	public RestTemplate restTemplate() {
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

		return new RestTemplate(requestFactory);
	}


	@Value("${azure.storage.connectionString}")
	private String blobStorageConnectionString;

	@Value("${azure.storage.container}")
	private String container;

//	@Bean
//	public BlobContainerClient containerClient() {
//		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
//				.connectionString(blobStorageConnectionString).buildClient();
//
//		return blobServiceClient.getBlobContainerClient(container);
//	}

	/** added fileManagerUtil - CDT-Ujwal */
	@Bean
	public FileManagerUtil getFileManagerUtil() {
		return new FileManagerUtil(azureFileUrl, restTemplate());
	}

	@Bean
	public FileUtility fileUtility() {
		return new FileUtility();
	}
}
