package in.cropdata.toolsuite.drk.config;

import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.toolsuite.drk.exceptions.ResponseMessageUtil;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.sdk.FileManagerUtil;

@Configuration
public class BeansConfig {
	
	
	@Autowired
//	@Lazy
	private RestTemplate restTemplate;

	@Autowired
	private AppProperties appProperties;

	@Bean
	public ResponseMessageUtil getMessageUtil() {
		return new ResponseMessageUtil();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	

//	@Bean("fileManagerUtil")
//	public FileManagerUtil fileManagerUtil() {
//		String filemanagerURL = appProperties.getFileManagerURL();//"http://192.168.0.252:8762/file-manager/";
//		return new FileManagerUtil(filemanagerURL, restTemplate);
//	}

	@Bean
	public FileManagerUtil getFileManagerUtil() {
		return new FileManagerUtil(appProperties.getFileManagerURL(), restTemplate);
	}

	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
//      mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
		return mapper;
	}

//  @Bean
//  public CommonsMultipartResolver multipartResolver(){
//      CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
//      commonsMultipartResolver.setDefaultEncoding("utf-8");
//      commonsMultipartResolver.setMaxUploadSize(5000000);//5MB
//      return commonsMultipartResolver;
//  }

//	@Bean(name = "multipartResolver")
//	public CommonsMultipartResolver multipartResolver() 
//	{
//	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//	    multipartResolver.setMaxUploadSize(20848820);
//	    return multipartResolver;
//	}
}
