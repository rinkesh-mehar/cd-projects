package in.cropdata.toolsuite.drk.config;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UploadFileConfiguration {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
	MultipartConfigFactory factory = new MultipartConfigFactory();
	factory.setMaxFileSize("1024MB");
	factory.setMaxRequestSize("1024MB");
	return factory.createMultipartConfig();
    }
}
