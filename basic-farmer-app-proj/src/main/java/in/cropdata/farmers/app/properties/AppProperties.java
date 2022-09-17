/**
 * 
 */
package in.cropdata.farmers.app.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author cropdata-pallavi
 *
 */
@Component
@Data
public class AppProperties {

	@Value("${jwt.secret.key}")
	private String jwtSecretKey;

	@Value("${notification.server.url}")
	private String notificationUrl;

	@Value("${farmer.notification.server.key}")
	private String notificationServerKey;
	
	@Value("${security.cdt.api-key}")
	private String apiKeyProperty;
	
	@Value("${file-manager.url}")
	private String fileManagerURL;

	@Value("${basic-farmer-app.smsgatewayapikey}") 
	private String smsGatewayApiKey;

	@Value("${basic-farmer-app.smsgatewayurl}")
	private String smsGatewayUrl;

	@Value("${drkrishi.url}")
	private String drkrishUrl;
	
	@Value("${gstm.farm-details.api-url}")
	private String gstmFarmDetailsApiUrl;
	
	@Value("${kml.file.path}")
	private String kmlFilePath;

}
