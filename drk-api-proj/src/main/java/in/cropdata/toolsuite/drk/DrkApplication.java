package in.cropdata.toolsuite.drk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
@EnableDiscoveryClient
@RefreshScope
public class DrkApplication {

	public static final String apiVersion = "v1.0";
	public static final String flipbookModuleName = "flipbook";

	public static void main(String[] args) {
		SpringApplication.run(DrkApplication.class, args);
	}

}
