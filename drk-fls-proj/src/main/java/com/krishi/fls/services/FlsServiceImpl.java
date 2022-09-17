package com.krishi.fls.services;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.krishi.fls.entity.*;
import com.krishi.fls.model.*;
import com.krishi.fls.model.Error;
import com.krishi.fls.repository.*;
import com.krishi.fls.utility.FileUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.config.Task;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.krishi.fls.dao.FlsDao;
import com.krishi.fls.dao.FlsDayEndSynchDao;
import com.krishi.fls.dao.MasterDataDao;
import com.krishi.fls.dao.UserDao;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author janardhan
 *
 */
@Service
@Component
public class FlsServiceImpl implements FlsService {

	private static final Logger logger = LoggerFactory.getLogger(FlsServiceImpl.class);
	final static SimpleDateFormat SYNC_FILE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-SSS");

	@Autowired
	private FlsDao flsDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private FlsDayEndSynchDao flsEndsynchDao;

	@Autowired
	private MasterDataDao masterDataDao;

	@Autowired
	private PanchayatRepository panchayatRepo;

	@Autowired
	private TehsilRepository tehsilRepo;

	@Autowired
	private DistrictRepository districtRepo;
	
	@Autowired
	private StaticDataRepository staticDataRepo;
	
	@Autowired
	private JobSyncDetailsRepository jobSyncDetailsRepo;

	@Autowired
	private FarmerFarmRepository farmerFarmRepo;

	@Autowired
	private FarmCaseRepository farmerCaseRepo;
	
	@Autowired
	private RightsRepository rightsRepo;
	
	@Autowired
	private ViewRightRepository viewRightRepo;
	
	@Autowired
	private TaskFutureDatesRepository taskFutureDatesRepo;
	
	@Autowired
	private FarmerCropInfoRepository cropInfoRepository;
	
	@Autowired
	private FarmerRepository farrmerRepo;


	@Autowired
	private FileUtility fileUtility;
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krishi.fls.services.FlsService#getFlsTaskData(int)
	 */
	@Override
	public ScheduledTask getFlsTask(int userId){
		ScheduledTask scheduledTask = new ScheduledTask();
		Users drkrishiUsers = userDao.findById(userId);
		if (drkrishiUsers != null) {
			List<TaskAllocation> taskAllocations = flsDao.getTask(userId, drkrishiUsers.getRegionId());
//			taskAllocations.forEach(item -> {
//				item.setAssigneeId(userId);
//				item.setStatus(1);
//			});
//			flsDao.updateTaskAllocation(taskAllocations);
			// put prioritation conditions
			if(taskAllocations != null && taskAllocations.size() > 0) {
				List<String> taskIds = taskAllocations.stream().map(TaskAllocation::getId).collect(Collectors.toList());
				Set<TaskFutureDates> taskFutureDates  = taskFutureDatesRepo.findAllFutureDatesByTaskId(taskIds);
				scheduledTask.setTaskFutureDates(taskFutureDates);
			} else {
				scheduledTask.setTaskFutureDates(Set.of());
			}
			Set<Farmer> farmers = new HashSet<>();
			Set<Village> villages = new HashSet<>();
			Set<Region> regions = new HashSet<>();
			Set<State> states = new HashSet<>();
			Set<Panchayat> panchayat = new HashSet<>();
			Set<Tehsil> tehsil = new HashSet<>();
			Set<District> district = new HashSet<>();
			List<TaskType> tasktype = flsDao.tasktype();
			List<CaseType> casetype = flsDao.casetype();
			scheduledTask.setTaskAllocations(taskAllocations);
			scheduledTask.setTaskType(tasktype);
			scheduledTask.setCaseType(casetype);

			String timeDuration = flsDao.findStaticValueByKey("timeDuration");
			scheduledTask.setTimeDuration(timeDuration);
//			MasterData masterData = masterDataDao.getMasterData(userId, drkrishiUsers.getStateId(), drkrishiUsers.getRegionId());
			/*TODO : remove master data*/
//			scheduledTask.setMasterData(masterData);
			setScheduledTask(scheduledTask, taskAllocations, farmers, villages, regions, states, tehsil, district,
					panchayat);
			if (scheduledTask.getVillage().size() > 0) {
				List<Integer> villageIds = scheduledTask.getVillage().stream().map(i -> i.getId())
						.collect(Collectors.toList());
				Set<Poi> poiList = flsDao.findPoiByVillageIds(villageIds);
				scheduledTask.setPoi(poiList);
			} else {
				scheduledTask.setPoi(Set.of());
			}

		}
		return scheduledTask;
	}
	@Override
	public ScheduledTask getFlsTaskData(int userId) {
		ScheduledTask scheduledTask = new ScheduledTask();
		Users drkrishiUsers = userDao.findById(userId);
		if (drkrishiUsers != null) {
			List<TaskAllocation> taskAllocations = flsDao.getTask(userId, drkrishiUsers.getRegionId());
//			taskAllocations.forEach(item -> {
//				item.setAssigneeId(userId);
//				item.setStatus(1);
//			});
//			flsDao.updateTaskAllocation(taskAllocations);
			// put prioritation conditions
			if(taskAllocations != null && taskAllocations.size() > 0) {
				List<String> taskIds = taskAllocations.stream().map(t -> t.getId()).collect(Collectors.toList());
				Set<TaskFutureDates> taskFutureDates  = taskFutureDatesRepo.findAllFutureDatesByTaskId(taskIds);
				scheduledTask.setTaskFutureDates(taskFutureDates);
			} else {
				scheduledTask.setTaskFutureDates(Set.of());
			}
			Set<Farmer> farmers = new HashSet<Farmer>();
			Set<Village> villages = new HashSet<Village>();
			Set<Region> regions = new HashSet<Region>();
			Set<State> states = new HashSet<State>();
			Set<Panchayat> panchayat = new HashSet<Panchayat>();
			Set<Tehsil> tehsil = new HashSet<Tehsil>();
			Set<District> district = new HashSet<District>();
			List<TaskType> tasktype = flsDao.tasktype();
			List<CaseType> casetype = flsDao.casetype();
			scheduledTask.setTaskAllocations(taskAllocations);
			scheduledTask.setTaskType(tasktype);
			scheduledTask.setCaseType(casetype);

			String timeDuration = flsDao.findStaticValueByKey("timeDuration");
			scheduledTask.setTimeDuration(timeDuration);
			MasterData masterData = masterDataDao.getMasterData(userId, drkrishiUsers.getStateId(), drkrishiUsers.getRegionId());
			/*TODO : remove master data*/
			scheduledTask.setMasterData(masterData);
			setScheduledTask(scheduledTask, taskAllocations, farmers, villages, regions, states, tehsil, district,
					panchayat);
			if (scheduledTask.getVillage().size() > 0) {
				List<Integer> villageIds = scheduledTask.getVillage().stream().map(i -> i.getId())
						.collect(Collectors.toList());
				Set<Poi> poiList = flsDao.findPoiByVillageIds(villageIds);
				scheduledTask.setPoi(poiList);
			} else {
				scheduledTask.setPoi(Set.of());
			}

		}
			return scheduledTask;
	}

	/**
	 * @param scheduledTask
	 * @param taskAllocations
	 * @param farmers
	 * @param villages
	 * @param regions
	 * @param states
	 */
	private void setScheduledTask(ScheduledTask scheduledTask, List<TaskAllocation> taskAllocations,
			Set<Farmer> farmers, Set<Village> villages, Set<Region> regions, Set<State> states, Set<Tehsil> tehsil,
			Set<District> district, Set<Panchayat> panchayat) {
		getFarmers(taskAllocations, farmers);
		getVillages(farmers, villages);
		getRegion(villages, regions); 
		getStates(regions, states);
		getPanchayat(villages, panchayat);
		getTehsil(panchayat, tehsil);
		getDistrict(tehsil, district);
		scheduledTask.setFarmer(farmers);
		scheduledTask.setVillage(villages);
		scheduledTask.setRegion(regions);
		scheduledTask.setState(states);
		scheduledTask.setTehsil(tehsil);
		scheduledTask.setPanchayat(panchayat);
		scheduledTask.setDistricts(district);
		// scheduledTask.setTaskType(tasktype);
	}

	/**
	 * @param regions
	 * @param states
	 */
	private void getStates(Set<Region> regions, Set<State> states) {
		for (Region region : regions) {
			State state = flsDao.findStateById(region.getStateId());
			states.add(state);
		}
	}

	/**
	 * @param regions
	 * @param states
	 */
	private void getPanchayat(Set<Village> village, Set<Panchayat> panchayat) {
		List<Integer> ids = village.stream().map(v -> v.getPanchayatId()).collect(Collectors.toList());
		List<Panchayat> panchayats = panchayatRepo.findAllById(ids);
		panchayat.addAll(panchayats);
	}

	/**
	 * @param regions
	 * @param states
	 */
	private void getTehsil(Set<Panchayat> panchayat, Set<Tehsil> tehsil) {
		List<Integer> ids = panchayat.stream().map(v -> v.getTehsilId()).collect(Collectors.toList());
		List<Tehsil> tehsils = tehsilRepo.findAllById(ids);
		tehsil.addAll(tehsils);
	}

	/**
	 * @param taskAllocations
	 * @param farmers
	 */
	private void getFarmers(List<TaskAllocation> taskAllocations, Set<Farmer> farmers) {
		for (TaskAllocation taskAllocation : taskAllocations) {
			Farmer farmer;
			if(taskAllocation.getEntityTypeId() == 4) {
				farmer = flsDao.getFarmer(taskAllocation.getEntityId());
				
			}
			/** handle farm condition*/
			else if(taskAllocation.getEntityTypeId() == 15) {
				FarmerFarm farmerFarm=farmerFarmRepo.findbyfarmId(taskAllocation.getEntityId());
				farmer = flsDao.getFarmer(farmerFarm.getFarmerId());
				farmer.setMajorCrop(cropInfoRepository.getMajorCommoditybyFarmerId(taskAllocation.getEntityId()));

				farmers.add(farmer);
			}
			else {
				FarmCase farmcase=farmerCaseRepo.getBycaseID(taskAllocation.getEntityId());
				/*if (farmcase != null){*/

					FarmerFarm farmerFarm=farmerFarmRepo.findbyfarmId(farmcase.getFarmId());
					farmer = flsDao.getFarmer(farmerFarm.getFarmerId());
					farmer.setMajorCrop(cropInfoRepository.getMajorCommoditybyFarmerId(taskAllocation.getEntityId()));

					farmers.add(farmer);
				/*}*/
			}
			//update major crop
		}
	}

	/**
	 * @param villages
	 * @param regions
	 */
	private void getRegion(Set<Village> villages, Set<Region> regions) {
		for (Village village : villages) {
			Region region = flsDao.findRegionById(village.getRegionId());
			regions.add(region);
		}
	}

	/**
	 * @param villages
	 * @param regions
	 */
	private void getDistrict(Set<Tehsil> tehsil, Set<District> district) {
		List<Integer> ids = tehsil.stream().map(v -> v.getDistrictId()).collect(Collectors.toList());
		List<District> districts = districtRepo.findAllById(ids);
		district.addAll(districts);
	}

	/**
	 * @param farmers
	 * @param villages
	 */
	private void getVillages(Set<Farmer> farmers, Set<Village> villages) {
		for (Farmer farmer : farmers) {
			Village village = flsDao.findVillageById(farmer.getVillageId());
			villages.add(village);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krishi.fls.services.FlsService#getFLSsynch()
	 */
	@Override
	public void syschFlsTaskData(Integer userId) {

	}

	@Override
	public Response syschFlsCollectedData(FlsDataSynch dataSynch) {
		flsEndsynchDao.flsSynch(dataSynch);
		System.out.println(dataSynch);
		Response response = new Response();
		return response;
	}

	@Override
	public HistoricalData history(Integer userId) {
		return flsDao.getHistorydata(userId);
	}
	
	@Transactional
	@Override
	public Response saveOnFile(String dayEndSynch, Integer userId) {
		Response response=new Response();
		/*try {
			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			SyncDataModel  syncDataModel =	objectMapper.readValue(dayEndSynch, SyncDataModel.class);


			StaticData dirPath = staticDataRepo.findByDataKey("syncJsonPath");
			File dir = new File(dirPath.getDataValue(),userId.toString());
			logger.info("Create New folder.");
			if(!dir.exists()) {
				logger.info("directory not exist.");
				boolean isSuccess = dir.mkdir();
				if(!isSuccess) {
					logger.info("Unable to create folder.");
					System.out.println("Unable to create folder.");
				}
			}

			String filename = SYNC_FILE_DATE_FORMAT.format(new Date())+".json";
			JobSyncDetails jobDetails = new JobSyncDetails();
			jobDetails.setFileName(filename);
			jobDetails.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			jobDetails.setUserId(userId);
			jobSyncDetailsRepo.save(jobDetails);
			logger.info("save json in DB.");
			File file = new File(dir, filename);
			try(FileWriter writer = new FileWriter(file)) {
				logger.info("writing file.");
				writer.write(dayEndSynch);
			} catch (IOException e) {
				e.printStackTrace();
				Error error = new Error();
				error.setErrorMesg("An Error Occurred, Please Try Again Later.");
				response.setError(error);
				return response;
			}
		} catch(Exception e) {
			e.printStackTrace();
			Error error = new Error();
			error.setErrorMesg("An Error Occurred, Please Try Again Later.");
			response.setError(error);
			return response;
		}*/
 			response.setSuccess("Data Synced Successfully.");
		return response;
	}

	@Override
	public RightsData getRightsInfo(String caseId) {
		RightsData data = new RightsData();
		List<Rights> rights = rightsRepo.findByCaseIdIn(List.of(caseId));
		data.setRightsList(rights);
		List<ViewRight> viewRight = viewRightRepo.findByCaseId(caseId);
		data.setViewRightList(viewRight);
		return data;
	}
	
	/** Check farmer app Farmer already exists or not -CDT-Ujwal */ 
	@Override
	public Response checkFarmerExist(Farmer farmer) {
		Response response = new Response();
		try {
			if (farmer != null) {
				
				Optional<Farmer> checkExist = farrmerRepo.findByprimaryMobNumber(farmer.getPrimaryMobNumber());
				
				if (checkExist.isEmpty()) {
					farrmerRepo.save(farmer);
					response.setStatus(200);
					response.setSuccess("Farmer Save Successfully.");
					response.setData(null);
				} else {
					response.setData(checkExist);
					response.setStatus(409);
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			Error error = new Error();
			error.setErrorMesg("An Error Occurred, Please Try Again Later.");
			response.setError(error);
			response.setStatus(500);
			return response;
		}
		return response;
		
	}
	@Override
	public Map<String, Object> getScoutData(Integer userId) throws JsonProcessingException {
		Map<String, Object> getScoutUrls = new HashMap<>();
		String fileUrl = null;
		ScheduledTask scheduledTask =this.getFlsTask(userId);
		/*TODO : Temporally change */
//		ScheduledTask scheduledTask =this.getFlsTaskData(userId);
		HistoricalData historicalData = this.history(userId);

		if (scheduledTask!= null) {
			getScoutUrls.put("assignments", scheduledTask);
		} if (historicalData != null){
			getScoutUrls.put("historicals", historicalData);
		}
			/*ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(getScoutUrls);

		File file= fileUtility.createAndUploadJsonFile(json, userId);*/

		/*if (file !=null){

			fileUrl = fileUtility.uploadFileInABS("user_data", file);
			if (fileUrl != null){
				file.delete();
			}
		}*/
		return getScoutUrls;
	}

	@Override
	public Map<String, Object> unzipFile(MultipartFile file) throws IOException {
		return fileUtility.readZipFile(file);
	}

}
