/**
 * 
 */
package in.cropdata.gateway.dto;

import java.util.Map;

import lombok.Data;

/**
 * @author Vivek Gajbhiye
 *
 */
@Data
public class ServiceInstanceDTO {
	
	private String host;
	private String serviceId;
	private String instanceId;
	private int port;
	private String uri;
	private String serviceUrl;
	private Map<String, String> metadata;
	
	

}
