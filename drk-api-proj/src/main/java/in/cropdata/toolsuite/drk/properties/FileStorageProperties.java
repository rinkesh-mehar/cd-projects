/**
 * 
 */
package in.cropdata.toolsuite.drk.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author Vivek Gajbhiye - Cropdata
 *
 * 26-Nov-2019
 */
@Data
@Component
@ConfigurationProperties(prefix = "kml")
public class FileStorageProperties {
	private String uploadDir;
}
