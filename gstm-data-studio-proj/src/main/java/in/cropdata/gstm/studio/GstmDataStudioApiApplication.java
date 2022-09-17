package in.cropdata.gstm.studio;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;

import in.cropdata.gstm.studio.service.CacheLoadingService;

@SpringBootApplication
@RefreshScope
public class GstmDataStudioApiApplication {

	

	@Autowired
	private CacheLoadingService service;

	public static void main(String[] args) {
		SpringApplication.run(GstmDataStudioApiApplication.class, args);
	}

	@PostConstruct
	public void afterStartupCall() {
		System.out.println("Hello Pranay!");
//		service.loadGstmData();
	}

}
