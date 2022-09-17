package in.cropdata.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
//@EnableEurekaClient
public class CropdataConfigServerApplication {
	public static void main(String[] args) {
		SpringApplication.run(CropdataConfigServerApplication.class, args);
	}
}
