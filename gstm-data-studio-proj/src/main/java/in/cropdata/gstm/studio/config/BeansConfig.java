package in.cropdata.gstm.studio.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Configuration
@Component
public class BeansConfig {

	@Autowired
	@Lazy
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
