package com.krishi.fls.config;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.krishi.fls.properties.AppProperties;
import com.krishi.fls.utility.FileUtility;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


@Configuration
public class SpringConfig {

    @Value("${file-manager.url}")
    private String azureFileUrl;

    @Bean
    public AppProperties appProperties(){
        return new AppProperties();
    }

    @Bean
    public FileUtility fileUtility() {
        return new FileUtility();
    }

    @Bean
    public RestTemplate restTemplate() {
        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();

        return new RestTemplate(requestFactory);
    }
//    @Bean
//    public RestTemplate restTemplate() {
//        SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
//        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.44.0.121", 8080));
//        requestFactory.setProxy(proxy);
//        return new RestTemplate(requestFactory);
//    }

    @Value("${azure.storage.connectionString}")
    private String blobStorageConnectionString;

    	@Value("${azure.storage.container}")
    private String container;

    @Bean
    public BlobContainerClient containerClient() {
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
                .connectionString(blobStorageConnectionString).buildClient();

        return blobServiceClient.getBlobContainerClient(container);
    }

    @Bean
    public FileManagerUtil getFileManagerUtil() {
        return new FileManagerUtil(azureFileUrl, restTemplate());
    }
}
