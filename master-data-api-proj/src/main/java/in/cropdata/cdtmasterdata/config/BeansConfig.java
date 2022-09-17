package in.cropdata.cdtmasterdata.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import in.cropdata.cdtmasterdata.properties.AppProperties;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;

@Configuration
@Component
public class BeansConfig {

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	@Autowired
	private AppProperties appProperties;

	@Bean
	public AclHistoryUtil getApproveUtil() {
		return new AclHistoryUtil();
	}

	@Bean
	public ResponseMessageUtil getMessageUtil() {
		return new ResponseMessageUtil();
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Bean
	public FileManagerUtil getFileManagerUtil() {
		return new FileManagerUtil(appProperties.getFileManagerURL(), restTemplate);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
