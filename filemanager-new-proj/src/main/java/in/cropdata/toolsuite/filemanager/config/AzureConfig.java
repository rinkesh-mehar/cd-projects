/**
 * 
 */
package in.cropdata.toolsuite.filemanager.config;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;

import in.cropdata.toolsuite.filemanager.properties.Properties;
import in.cropdata.toolsuite.filemanager.utils.ResponseMessageUtil;

/**
 * The Class AzureConfig represents beans Configuration.
 *
 * @author Siddhant Rangari
 */
@Configuration
public class AzureConfig {
	
	private static Logger LOGGER = LoggerFactory.getLogger(AzureConfig.class);

	/** The properties. */
	@Autowired
	private Properties properties;

	/**
	 * Creates ContainerClient from the env specific container(retrieves from
	 * properties.)
	 * 
	 * @return the blob container client
	 * 
	 * @author Siddhant Rangari
	 */
	@Bean
	public BlobContainerClient containerClient() {
		// Create a BlobServiceClient object which will be used to create a container
		// client
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
				.connectionString(properties.getStorageConnectionString()).buildClient();

		// Create a unique name for the container
		String containerName = properties.getContainer();

		// Create the container and return a container client object
		return blobServiceClient.getBlobContainerClient(containerName);
	}

	/**
	 * Response message util.
	 *
	 * @return the response message util
	 */
	@Bean
	public ResponseMessageUtil responseMessageUtil() {
		return new ResponseMessageUtil();
	}

	/**
	 * Rest template.
	 *
	 * @return the rest template
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate(clientHttpRequestFactory());
	}

	/**
	 * Set Read & Connection Timeout to 5 Minutes
	 * 
	 * @author Siddhant Rangari
	 * 
	 * @return ClientHttpRequestFactory
	 */
	private ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
		factory.setReadTimeout(300000);
		factory.setConnectTimeout(300000);
		return factory;
	}

	/**
	 * Gets the object mapper.
	 *
	 * @return the object mapper
	 */
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		return mapper;
	}
	
	@Bean
	public CloudBlobContainer blobContainer() throws Exception {
		
		CloudStorageAccount storageAccount;
		
	    try {
		// Create a CloudStorageAccount object which will be used to create a blobClient
		storageAccount = CloudStorageAccount.parse(properties.getStorageConnectionString());
		
		// Create a CloudBlobClient object which will be used to create a blobContainer
		CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

		// Create a unique name for the container
		String containerName = properties.getContainer();

		// Create a CloudBlobContainer object.
		CloudBlobContainer blobContainer = blobClient.getContainerReference(containerName);
		return blobContainer;
	    } catch ( Exception e ) {
	      LOGGER.error( "failed to construct blobContainer", e );
	      throw e;
	    }
		
	}
	
}// AzureConfig
