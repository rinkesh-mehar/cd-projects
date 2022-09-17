/**
 * 
 */
package in.cropdata.gateway.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import in.cropdata.gateway.service.DiscoveryRouteLocatorService;


/**
 * @author Vivek Gajbhiye
 *
 */
@Configuration
public class BeanConfig {
	
	private static final HttpMethod[] SUPPORTED_MULTIPART_METHODS = { HttpMethod.POST, HttpMethod.PUT };
	
	
	@Bean
	DiscoveryClientRouteLocator discoveryClientRouteLocator(ServerProperties server, ZuulProperties zuulProperties,
			DiscoveryClient discovery, ServiceRouteMapper serviceRouteMapper,
			@Autowired(required = false) Registration registration) {
		return new DiscoveryClientRouteLocator(server.getServlet().getContextPath(), discovery, zuulProperties,
				serviceRouteMapper, registration);
	}
	
	
	@Bean
	public CorsFilter corsFilter() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    final CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOrigins(Collections.singletonList("*"));
	    config.setAllowedHeaders(Collections.singletonList("*"));
	    config.setAllowedMethods(Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList()));
	    source.registerCorsConfiguration("/**", config);
	    return new CorsFilter(source);
	}
	
	
	@Bean
	public MultipartResolver multipartResolver() {
	    return new StandardServletMultipartResolver() {
	        @Override
	        public boolean isMultipart(HttpServletRequest request) {
	            boolean methodMatches = Arrays.stream(SUPPORTED_MULTIPART_METHODS)
	                .anyMatch(method -> method.matches(request.getMethod()));
	            String contentType = request.getContentType();
	            return methodMatches && (contentType != null && contentType.toLowerCase().startsWith("multipart/"));
	        }
	    };
	}
	

}
