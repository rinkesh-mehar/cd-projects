package com.krishi.step;

import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.krishi.entity.*;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.krishi.model.GstmSimpleNdviResponseModel;
import com.krishi.repository.FarmCaseRepository;
import com.krishi.repository.SimpleNdviRepository;
import com.krishi.repository.StaticDataRepository;

public class SimpleNdviSyncProcessor implements Tasklet{

	private static final Logger LOGGER = LogManager.getLogger(SimpleNdviSyncProcessor.class);
	
	@Autowired
	private StaticDataRepository staticDataRepository;
	
	@Autowired
	private FarmCaseRepository farmCaseRepository;
	
	@Autowired
	private SimpleNdviRepository simpleNdviRepository;
	
	/*Fetch simple ndvi image info and insert into database*/
	@Override
	public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
		StaticData baseUrlData = staticDataRepository.findByKey("gstmBasePath");
		StaticData apiKeyData = staticDataRepository.findByKey("masterDataApikey");
		try {
			List<FarmCase> farmCases = farmCaseRepository.findSimpleNdviToSync();
			Calendar cal = Calendar.getInstance();
			int week = cal.get(Calendar.WEEK_OF_YEAR);
			int year = cal.get(Calendar.YEAR);
			farmCases.forEach((farmCase) -> {
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrlData.getValue() + "/simple-ndvi")
						.queryParam("caseId", farmCase.getId())
						.queryParam("year", year)
						.queryParam("week", week)
						.queryParam("apiKey", apiKeyData.getValue());
				
				RestTemplate template = new RestTemplate();
				template.setRequestFactory(new HttpComponentsClientHttpRequestWithBodyFactory());
				
				ResponseEntity<SimpleNdviJsonData> response = template.exchange(builder.toUriString(),
						HttpMethod.GET, null, new ParameterizedTypeReference<SimpleNdviJsonData>() {});
				
				if (response.getBody().getData().size() > 0) {
					List<SimpleNdvi> ndviList = response.getBody().getData().stream().map(GstmSimpleNdviResponseModel::getSimpleNdvi)
							.collect(Collectors.toList());
					for (SimpleNdvi ndvi : ndviList) {
						
						ndvi.setCaseId(farmCase.getId());
						List<SimpleNdvi> existData = simpleNdviRepository.findByCaseIdAndWeekAndYear(ndvi.getCaseId(),
								ndvi.getWeek(), ndvi.getYear());
						if (existData.size() == 0) {
							
							simpleNdviRepository.saveAndFlush(ndvi);
						}
					}
				}
				farmCase.setSimpleNdviLastSync(new Date(System.currentTimeMillis()));
				farmCaseRepository.saveAndFlush(farmCase);
				
			});
			
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error(e);
		}
		return RepeatStatus.FINISHED;
	}
	
	private static final class HttpComponentsClientHttpRequestWithBodyFactory extends HttpComponentsClientHttpRequestFactory {
        @Override
        protected HttpUriRequest createHttpUriRequest(HttpMethod httpMethod, URI uri) {
            if (httpMethod == HttpMethod.GET) {
                return new HttpGetRequestWithEntity(uri);
            }
            return super.createHttpUriRequest(httpMethod, uri);
        }
    }
	
    private static final class HttpGetRequestWithEntity extends HttpEntityEnclosingRequestBase {
        public HttpGetRequestWithEntity(final URI uri) {
            super.setURI(uri);
        }

        @Override
        public String getMethod() {
            return HttpMethod.GET.name();
        }
    }

}
