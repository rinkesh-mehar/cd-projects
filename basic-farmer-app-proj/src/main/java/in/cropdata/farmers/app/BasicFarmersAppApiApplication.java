package in.cropdata.farmers.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
//@EnableAutoConfiguration  
public class BasicFarmersAppApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BasicFarmersAppApiApplication.class, args);
	}

}
