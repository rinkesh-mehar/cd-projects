package com.krishi.service;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.krishi.dao.GstmDataDao;
import com.krishi.entity.*;
import com.krishi.repository.*;
import com.krishi.utility.FileUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishi.model.MasterDataSyncInfo;
import com.krishi.model.MasterDataTable;
import com.krishi.model.RequestStateModel;

@RestController
@RequestMapping("/test")
public class CDTMasterSync {
	private static final Logger LOGGER = LogManager.getLogger(MasterdataSyncImpl.class);
	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

	@Autowired
	private MasterSyncConfigRepository masterSyncConfigRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private GstmSyncRepository gstmSyncRepository;

	@Autowired
	private StaticDataRepository staticDataRepository;

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private ObjectMapper mapper;

	private String masterDataApi;

	private String masterDataKey;


	@Autowired
	private FileUtility fileUtility;

	@Autowired
	private RegionRepository regionRepository;

	@Autowired
	private StateRepository stateRepository;

	@Autowired
	private GstmDataDao gstmDataDao;



	List<MasterDataSyncInfo> logs = new ArrayList<>();

	@GetMapping("/run")
	public void run() {

		List<MasterSyncConfig> fetchedMasterSyncConfig = masterSyncConfigRepository.findAll();
		if (fetchedMasterSyncConfig != null) {
			for (MasterSyncConfig masterSyncConfig : fetchedMasterSyncConfig) {
				getCDTData(masterSyncConfig.getUrl(), masterSyncConfig.getFieldMapping(),
						masterSyncConfig.getEntityName());
			}
		}

	}

	private void getCDTData(String url, String fieldMapping, String entityName) {
		int pageNo = 1;
		int pageRecordSize = 0;
		do {
			try {

				List<String> responseList = null;
				StaticData apikeyData = staticDataRepository.findByKey("masterDataApikey");
				masterDataKey = apikeyData.getValue();
				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("unixTimestamp", 0)
						.queryParam("apiKey", masterDataKey).queryParam("page", pageNo);
				ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);
				responseList = mapper.readValue(response.getBody(), new TypeReference<List<?>>() {
				});
				pageRecordSize = responseList.size();
				String _response = replaceJsonKeys(response.getBody(), fieldMapping);
				convertAndStoreData(_response, entityName);

				++pageNo;
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (pageRecordSize > 0);

	}

	private String replaceJsonKeys(String response, String fieldMapping) {
		try {
			@SuppressWarnings("unchecked")
			HashMap<String, String> map = mapper.readValue(fieldMapping, HashMap.class);
			Set<Entry<String, String>> entrySet = map.entrySet();
			for (Entry<String, String> valuesMap : entrySet) {
				if (response.contains(valuesMap.getKey())) {
					response = response.replace(valuesMap.getKey(), valuesMap.getValue());
				}
			}
			System.out.println("response is " + response);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return response;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void convertAndStoreData(String _response, String entityName) {

		try {
			
			Object d = Class.forName("com.krishi.entity.".concat(entityName)).newInstance();
			Class cls = Class.forName("com.krishi.entity.".concat(entityName), true, d.getClass().getClassLoader());
			cls.getDeclaredConstructors();
			List<Object> object = mapper.readValue(_response, new TypeReference<List<Object>>() {});
			
			
			List<Object> outList = new ArrayList<>();
			Object newo = Class.forName("com.krishi.entity.".concat(entityName)).newInstance();
			for(Object o : object) {
				outList.add(mapper.convertValue(o, newo.getClass()));
			}
			
			
			/** Dynamic Repository*/

			Field genericField = cls.getDeclaredField("generic");
			Class clazz = genericField.getDeclaringClass();
			
//			System.out.println("interface are " + Arrays.toString(cls.getInterfaces()));
//			Object repo = Class.forName("com.krishi.service.Generic").newInstance();
//			Method m = EducationTypeRepository.class.getDeclaredMethod("abc", List.class);  
//			Object rv = m.invoke(outList);
			
//			System.out.println(rv);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// state
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void syncState() {

		MasterDataSyncInfo syncInfo = new MasterDataSyncInfo();
		syncInfo.setTableName(MasterDataTable.STATE.name());
		logs.add(syncInfo);

		Date startTime = new Date();

		LOGGER.info("INFO :: State API sync started At: " + FORMAT.format(startTime));

		String lastSyncTime = "0";

		GstmSync gstmSyn = gstmSyncRepository.findBySchemaName(MasterDataTable.STATE.name());
		if (gstmSyn != null && !gstmSyn.getLastSync().isBlank()) {
			lastSyncTime = gstmSyn.getLastSync();
		}

		try {
			List<RequestStateModel> response = null;
			int pageRecordsCount = 0;
			int totalRecordsCount = 0;
			int pageNo = 0;
			int totalNewRecords = 0;
			int totalUpdatedRecords = 0;
			do {
				++pageNo;
				LOGGER.info("INFO :: Page Number: " + pageNo);

				UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(masterDataApi + "/state")
						.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
						.queryParam("page", pageNo);

				ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
						String.class);

				response = mapper.readValue(apiResponse.getBody(), new TypeReference<List<RequestStateModel>>() {
				});

				pageRecordsCount = response.size();

				if (pageRecordsCount > 0) {
					List<StateEntity> list = response.stream().map(a -> a.getEntity()).collect(Collectors.toList());
					int i = 0;
					for (StateEntity m : list) {
						++i;
//							StateEntity existRecored = em.find(StateEntity.class, m.getStateId());
//							if (existRecored != null) {
//								if (!existRecored.equals(m)) {
//									em.merge(m);
//									em.flush();
//									++totalUpdatedRecords;
//								}
//							} else {
//								em.persist(m);
//								em.flush();
//								++totalNewRecords;
//							}
//							if (i >= 500) {
//								i = 0;
//								em.clear();
//							}
					}
				}

				totalRecordsCount += pageRecordsCount;
			} while (pageRecordsCount > 0);
			if (gstmSyn == null) {
				gstmSyn = new GstmSync();
				gstmSyn.setSchemaName(MasterDataTable.STATE.name());
			}
			if (totalRecordsCount > 0) {
				gstmSyn.setLastSync(Long.toString(System.currentTimeMillis() / 1000));
				gstmSyncRepository.saveAndFlush(gstmSyn);
			}
			Date endTime = new Date();
			syncInfo.setTotalRecordsCount(totalRecordsCount);
			syncInfo.setPageCount(pageNo - 1);
			syncInfo.setTotalNewRecords(totalNewRecords);
			syncInfo.setTotalUpdatedRecords(totalUpdatedRecords);
			syncInfo.setSuccess(true);

			LOGGER.info("INFO :: Sync Completed At: " + FORMAT.format(endTime));
			LOGGER.info("INFO :: State sync Successful");
			LOGGER.info("INFO :: Total records read from API : " + totalRecordsCount);
			LOGGER.info("INFO :: Total Page : " + (pageNo - 1));
			LOGGER.info("INFO :: Total records inserted : " + totalNewRecords);
			LOGGER.info("INFO :: Total records updated : " + totalUpdatedRecords);

		} catch (Exception e) {

			syncInfo.setErrorMessage(e.getMessage());
			LOGGER.error("ERROR :: State API error: " + e.getMessage());
			LOGGER.error(e);
			throw new RuntimeException(e);
		}
	}

}
