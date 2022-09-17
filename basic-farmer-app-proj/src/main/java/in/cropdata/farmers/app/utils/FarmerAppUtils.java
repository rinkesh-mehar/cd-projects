/**
 * 
 */
package in.cropdata.farmers.app.utils;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import in.cropdata.farmers.app.masters.repository.AppVersionControlRepository;
import in.cropdata.farmers.app.model.FarmLocation;
import in.cropdata.farmers.app.properties.AppProperties;
import in.cropdata.farmers.app.repository.FarmerAppRepository;


/**
 * @author cropdata-Aniket Naik
 *
 */
@Component
public class FarmerAppUtils {
	
	@Autowired
	private AppVersionControlRepository versionControlrepository;
	
	@Autowired
    private FarmerAppRepository farmerAppRepository;
	
	 @Autowired
	 private RestTemplate restTemplate;
	 
	 @Autowired
	 private AppProperties appProperties;
	
	public boolean matchAppKey(String appKey) {
		try {
			String key = versionControlrepository.getAppKey(appKey);
			if (key != null && key.equals(appKey)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	
	
	public FarmLocation getDataByCordinates(Map<String, Object> latLongMap) {

//		final String url = "http://192.168.0.41:8004/get/farmdetail";// local
//		final String url = "http://143.10.0.11:8096/get/farmdetail";// public
//		final String url = "http://192.168.0.253:9141/get/farmdetail";// public
//		final String url = "https://api-dev.cropdata.in/gstm/get/farmdetail";// public
		final String url = appProperties.getGstmFarmDetailsApiUrl();
		

		ResponseEntity<FarmLocation> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			HttpEntity<Object> requestEntity = new HttpEntity<>(Arrays.asList(latLongMap), headers);
			
			System.out.println("----->" + Arrays.asList(latLongMap));
			
			if (latLongMap != null && !latLongMap.isEmpty()) {
				response = restTemplate.postForEntity(url, requestEntity, FarmLocation.class);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response.getBody();
	}
	
	public String getMonthNameByWeekNumber(Integer weekNumber) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
    	Calendar cal = Calendar.getInstance();
    	cal.set(Calendar.WEEK_OF_YEAR, weekNumber);        
    	return String.valueOf(sdf.format(cal.getTime()));
	}
	
	public String timestampToDate(String timstamp) {
		 Date date = new Date(Long.parseLong(timstamp));
		 return new SimpleDateFormat("yyyy-MM-dd").format(date);
		  
	}

}
