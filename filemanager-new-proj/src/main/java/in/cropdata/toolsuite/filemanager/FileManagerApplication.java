package in.cropdata.toolsuite.filemanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * The Class FileManagerApplication.
 * 
 * @author Siddhant Rangari
 */
@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@RefreshScope
public class FileManagerApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(FileManagerApplication.class, args);
	}

}// FileManagerApplication
