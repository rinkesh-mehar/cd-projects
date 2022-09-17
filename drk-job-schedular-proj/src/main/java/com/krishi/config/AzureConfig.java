package com.krishi.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import com.krishi.utility.FileUtility;

/**
 * Configuration for Azure Blob Storage.
 * 
 * @author CDT - Pranay
 */

@Configuration
public class AzureConfig {

//	@Value("${azure.storage.connectionString}")
	private String blobStorageConnectionString = "DefaultEndpointsProtocol=https;AccountName=cdtuatts;AccountKey=k9heaaghkWY2d/DWwr+aklEZOn0aJ+1i30DSkQK+/JPF1PPhp5i5+OfHJ7zaWVF5XBhDHtiJkxfkiKFdiEMj8w==;EndpointSuffix=core.windows.net";

//	@Value("${azure.storage.container}")
	private String container = "cdtuatts";

	/*@Bean
	public BlobContainerClient containerClient() {
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.connectionString(blobStorageConnectionString).buildClient();

		return blobServiceClient.getBlobContainerClient(container);
	}*/

	@Bean
	public FileUtility fileUtility() {
		return new FileUtility();
	}

}
