package in.cropdata.cdtmasterdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import in.cropdata.cdtmasterdata.acl.interceptor.AuthInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public AuthInterceptor getAuthInterceptor() {
	return new AuthInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/**");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//	registry.addMapping("/**")
//		.allowedOrigins("http://localhost:4200")
//		.allowedMethods("GET", "POST", "OPTIONS", "PUT","DELETE")
//		.allowedHeaders("Content-Type", "X-Requested-With", "accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
//		.exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials")
//		.allowCredentials(true)
//		.maxAge(3600);
//    }
}