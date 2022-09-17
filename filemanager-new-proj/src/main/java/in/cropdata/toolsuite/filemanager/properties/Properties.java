/**
 * 
 */
package in.cropdata.toolsuite.filemanager.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author cropdata-user
 *
 */
@Data
@Component
public class Properties {
	
	@Value("${azure.storage.ConnectionString}")
	private String storageConnectionString;
	
//	@Value("${azure.storage.enviornment}")
//	private String env;
	
	@Value("${azure.storage.container}")
	private String container;
	
}
