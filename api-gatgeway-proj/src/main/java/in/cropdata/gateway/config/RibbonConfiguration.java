package in.cropdata.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.ribbon.StaticServerList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ServerList;

import in.cropdata.gateway.TestGatewayApplication;




/**
 * @author Vivek Gajbhiye
 *
 */
public class RibbonConfiguration {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RibbonConfiguration.class);
	

	@Autowired
	private DiscoveryClient discoveryClient;

	private String serviceId = "client";
	protected static final String VALUE_NOT_SET = "__not__set__";
	protected static final String DEFAULT_NAMESPACE = "ribbon";

	public RibbonConfiguration() {
	}

	public RibbonConfiguration(String serviceId) {
		this.serviceId = serviceId;
	}

	@Bean
	@ConditionalOnMissingBean
	public ServerList<?> ribbonServerList(IClientConfig config) {

		Server[] servers = discoveryClient.getInstances(config.getClientName()).stream()
				.map(i -> new Server(i.getHost(), i.getPort())).toArray(Server[]::new);
		LOGGER.info("servers={}",servers);
		
		return new StaticServerList(servers);
	}

}
