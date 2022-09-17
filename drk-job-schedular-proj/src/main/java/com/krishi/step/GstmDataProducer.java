package com.krishi.step;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishi.dao.GstmDataDao;
import com.krishi.entity.StaticData;
import com.krishi.repository.StaticDataRepository;

public class GstmDataProducer implements Tasklet {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private StaticDataRepository staticDataRepository;
	
	@Autowired
	private GstmDataDao gstmDataDao;

	/*send the benchmark spot data to gstm api*/
	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		
		StaticData baseUrlData = staticDataRepository.findByKey("masterMbepPath");
		StaticData apiKeyData = staticDataRepository.findByKey("masterDataApikey");
		List<Map<String, Object>> requestObject = new ArrayList<>();
		List<String> taskSpotIds = new ArrayList<>();
		List<Object[]> array = gstmDataDao.findAllGstmBenchmark();
		if(array != null && array.size() > 0) {
		ObjectMapper mapper = new ObjectMapper();
		
		array.forEach(o -> {	
			taskSpotIds.add((String) o[0]);
			Map<String, Object> map = new HashMap<>();
			map.put("spotID", o[1]);
			map.put("caseID", o[2]);
			map.put("regionID", o[3]);
			map.put("subRegionID", o[4]);
			requestObject.add(map);
		});
		String requestData = mapper.writeValueAsString(array);
		//System.out.println(requestData);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		HttpEntity<String> request = new HttpEntity<>(requestData, headers);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrlData.getValue() + "/add-benchmark-spots")
				.queryParam("apiKey", apiKeyData.getValue());
		try {
			ResponseEntity<Map> result = restTemplate.postForEntity(builder.toUriString(), request, Map.class);
			if(result.getBody().containsKey("success") && (Boolean) result.getBody().get("success")) {
				gstmDataDao.updateSyncStatus(taskSpotIds);
			}
			
		} catch(HttpStatusCodeException  e) {
			System.out.println(e.getResponseBodyAsString());
			//gstmDataDao.updateSyncStatus(taskSpotIds);
		}
		}
		return RepeatStatus.FINISHED;
	}

}
