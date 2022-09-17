package in.cropdata.gstm.studio.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration for adding CORS filters.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Configuration
@EnableWebMvc
public class GstmDataStudioConfig implements WebMvcConfigurer {

	/**
	 * This bean is used to add the CORS filters in API.
	 */
	/*
	 * @Bean public FilterRegistrationBean<CorsFilter> simpleCorsFilter() {
	 * UrlBasedCorsConfigurationSource source = new
	 * UrlBasedCorsConfigurationSource(); CorsConfiguration config = new
	 * CorsConfiguration(); config.setAllowCredentials(true);
	 * config.setAllowedOrigins(Collections.singletonList("*"));
	 * config.setAllowedMethods(Arrays.asList("GET"));
	 * config.setAllowedHeaders(Collections.singletonList("*"));
	 * source.registerCorsConfiguration("/**", config);
	 * FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<>(new
	 * CorsFilter(source)); bean.setOrder(Ordered.HIGHEST_PRECEDENCE); return bean;
	 * }
	 */

}
