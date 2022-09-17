package in.cropdata.farmers.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.utils.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;


/**
 * @author Rinkesh KM
 *
 * 28-Jan-2021
 */

@Configuration
public class BeanConfig {
	
	@Autowired
	private AppProperties appConfig;

	@Bean
	public ResponseMessageUtil responseMessageUtil() {
		return new ResponseMessageUtil();
	}

	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.writer().withDefaultPrettyPrinter();
		return mapper;
	}
	
	@Bean
//  @LoadBalanced
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	@Bean
	public FileManagerUtil getFileManagerUtil() {
		String filemanagerURL = appConfig.getFileManagerURL();// "http://api.cropdata.tk/file-manager/";
		return new FileManagerUtil(filemanagerURL, restTemplate);
	}
	

}
