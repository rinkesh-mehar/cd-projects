package in.cropdata.gateway.api;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

/**
 * @author Vivek Gajbhiye
 *
 */

@Component
@Primary
//@EnableAutoConfiguration
public class GatewayApi {

	@Autowired
	ZuulProperties properties;
	
	@Autowired
	DiscoveryClient client;

	@Primary
	@Bean
	public SwaggerResourcesProvider swaggerResourcesProvider() {
		return () -> {
			List<SwaggerResource> resources = new ArrayList<>();
			Stream<ServiceInstance> s = client.getServices().stream().flatMap(it -> client.getInstances(it).stream());
//			properties.getRoutes().values().stream()
//					.forEach(route -> resources.add(createResource(route.getServiceId(), route.getServiceId(), "2.0")));
			s.forEach(it -> resources.add(createResource(it.getServiceId(), it.getServiceId(), "")));
			return resources;
		};
	}

	private SwaggerResource createResource(String name, String location, String version) {
		SwaggerResource swaggerResource = new SwaggerResource();
		swaggerResource.setName(name);
		swaggerResource.setLocation("/" +  "drk/");
		swaggerResource.setSwaggerVersion(version);
		return swaggerResource;
	}


}