/**
 * 
 */
package in.cropdata.cdtmasterdata.python.ui;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import in.cropdata.cdtmasterdata.properties.AppProperties;

/**
 * @author Vivek Gajbhiye
 *
 */
@RestController
@RequestMapping("/python-ui")
public class MasterDataPythonUIController {

	@Autowired
	AppProperties appProperties;

	@GetMapping("/fls-visit")
	public ResponseEntity<?> masterUI(@RequestParam Integer region_id) {

		RestTemplate rs = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.TEXT_HTML);
		headers.setAccept(Collections.singletonList(MediaType.TEXT_HTML));
		HttpEntity<String> entity = new HttpEntity<>("body", headers);
		ResponseEntity<String> exchange = rs.exchange(appProperties.getPythonUIUrl() + region_id, HttpMethod.GET,
				entity, String.class);
		String responseBody = exchange.getBody();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);

	}

}
