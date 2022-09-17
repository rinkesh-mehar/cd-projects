/**
 * 
 */
package in.cropdata.gateway.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;
import org.springframework.stereotype.Component;

import com.netflix.loadbalancer.ConfigurationBasedServerList;

import in.cropdata.gateway.framework.ApplicationSession;
import in.cropdata.gateway.model.Zuul;
import in.cropdata.gateway.repository.ZuulRepository;
import in.cropdata.gateway.response.ApiResponse;

/**
 * @author Vivek Gajbhiye
 *
 */
@Component
public class ZuulRouteService implements ApplicationRunner {

	private static final Logger log = LoggerFactory.getLogger(ZuulRouteService.class);

	@Autowired
	DiscoveryClientRouteLocator discoveryClientRouteLocator;


	@Autowired
	ZuulRepository zuulRepository;

	@Autowired
	ApiResponse apiResponse;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		ApplicationSession applicationSession = ApplicationSession.getCurrent();
		List<Zuul> zuulList = applicationSession.getZuulList();

		for (Zuul zuul : zuulList) {
			ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
			zuulRoute.setId(String.valueOf(zuul.getId()));
			zuulRoute.setServiceId(zuul.getServiceId());
			zuulRoute.setPath(zuul.getPath());
			zuulRoute.setUrl(zuul.getUrl());
			zuulRoute.setRetryable(true);
			this.discoveryClientRouteLocator.addRoute(zuulRoute);
			log.info(" routes {}", zuulRoute);
		}
	}
	
}
