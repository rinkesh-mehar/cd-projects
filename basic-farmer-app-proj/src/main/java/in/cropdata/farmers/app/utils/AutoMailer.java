package in.cropdata.farmers.app.utils;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.cropdata.farmers.app.properties.AppProperties;


@Service
public class AutoMailer {

	@Autowired
	private RestTemplate rst;

	@Autowired
	private AppProperties appConfig;


	public String sendSMS(String sendTo, String message) throws IOException {

		String status = "failed";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		headers.add("apikey", appConfig.getSmsGatewayApiKey());
		String smsObject = "sender=CRPDTA&numbers=91" + sendTo + "+&message=" + message + "&messagetype=TXT";
		HttpEntity<String> request = new HttpEntity<String>(smsObject.toString(), headers);
		ResponseEntity<String> _sR = rst.exchange(appConfig.getSmsGatewayUrl(), HttpMethod.POST, request,
				String.class);
		ObjectMapper _map = new ObjectMapper();
		@SuppressWarnings("unchecked")
		Map<String, Object> _m = _map.readValue(_sR.getBody(), Map.class);
		System.out.println("reply from Sms Gateway" + _m);
		if (_m.get("status").equals("success"))
			status = "success";
		return status;
	}

}
