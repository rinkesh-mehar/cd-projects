package in.cropdata.gateway;




import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

import in.cropdata.gateway.config.RibbonConfiguration;
import in.cropdata.gateway.repository.ZuulRepository;
import springfox.documentation.swagger2.annotations.EnableSwagger2;




/**
 * @author Vivek Gajbhiye
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
@EnableSwagger2
@AutoConfigureAfter(RibbonAutoConfiguration.class)
@RibbonClients(defaultConfiguration = RibbonConfiguration.class)
@RefreshScope
public class TestGatewayApplication {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestGatewayApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TestGatewayApplication.class, args);
	}

	@Autowired
	ZuulRepository zuulRepository;
	
	@Autowired
	DiscoveryClient client;

	@PostConstruct
	public void init() {
		LOGGER.info("Services: {}", client.getServices());
		Stream<ServiceInstance> s = client.getServices().stream().flatMap(it -> client.getInstances(it).stream());
		
		LOGGER.info("");
		
		s.forEach(it -> LOGGER.info("Instance: url={}:{}, id={}, service={} , metadata={}", it.getHost(), it.getPort(),
				it.getInstanceId(), it.getServiceId(),it.getMetadata()));
		
		
	}

}
