package in.cropdata.gateway.service;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.discovery.ServiceRouteMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @author Vivek Gajbhiye
 *
 */
public class DiscoveryRouteLocatorService extends DiscoveryClientRouteLocator {

	private final ZuulProperties properties;
	
	
	
	
	public DiscoveryRouteLocatorService(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
			ServiceInstance localServiceInstance) {
		super(servletPath, discovery, properties, localServiceInstance);
		this.properties = properties;
	}

	public DiscoveryRouteLocatorService(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
			ServiceRouteMapper serviceRouteMapper, ServiceInstance localServiceInstance) {
		this(servletPath, discovery, properties, localServiceInstance);
	}

	// here is new method to remove route from .properties.getRoutes()
	public void removeRoute(String path) {
		this.properties.getRoutes().remove(path);
		refresh();
	}
}