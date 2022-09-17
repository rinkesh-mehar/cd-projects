package com.krishi.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishi.dao.RightDao;
import com.krishi.entity.*;
import com.krishi.model.*;
import com.krishi.repository.*;
import com.krishi.vo.ViewFieldMonitoringDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.Column;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
//import com.krishi.entity.Season;
//import com.krishi.repository.SeasonRepository;


@Service
public class TeleCallerServiceImpl implements TeleCallerService
{

	@Autowired
	private ViewFieldMonitoringInfoRepository viewFieldMonitoringInfoRepository;

	@Autowired
	private HarvestMonitoringRepository harvestMonitoringRepository;

	@Autowired
	private ViewLeadCallingDetailInfoRepository viewLeadCallingDetailInfoRepository;

	@Autowired
	private CommodityRepository commodityRepository;

	@Autowired
	private LanguageRepository languageRepository;

	@Autowired
	private MobileTypeRepository mobileTypeRepository;

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskHistoryRepository taskHistoryRepository;

	@Autowired
	private VipStatusRepository vipStatusRepository;

	@Autowired
	private VipRepository vipRepository;

	@Autowired
	private FarmerRepository farmerRepository;

	@Autowired
	private VipDesignationRepository vipDesignationRepository;

	@Autowired
	private VehicleScheduleRepository vehicleScheduleRepository;

	@Autowired
	private IrrigationSourceRepository irrigationSourceRepository;

	@Autowired
	private SeedTreatmentAgentRepository seedTreatmentAgentRepository;

	@Autowired
	private IrrigationMethodRepository irrigationMethodRepository;

	@Autowired
	private ViewFieldMonitoringDetailRepository viewFieldMonitoringDetailRepository;

	@Autowired
	private FarmCaseRepository farmCaseRepository;

	@Autowired
	private TaskFutureDatesRepository taskFutureDatesRepository;

	@Autowired
	private ViewNonTechnicalCallingListForwardRepository viewNonTechnicalCallingListForwardRepository;

	@Autowired
	private ViewNonTechnicalCallingListSpotRepository viewNonTechnicalCallingListSpotRepository;

	@Autowired
	private CallingStatusRepository callingStatusRepository;


    @Autowired
    private VarietyRepository varietyRepository;

    @Autowired
    private ViewFarmerCropInfoRepository viewFarmerCropInfoRepo;

	@Autowired
	private RightsRepository rightsRepository;

	@Autowired
	private RightDao rightDao;

    @Autowired
    private ProxyRelationTypeRepository proxyRelationTypeRepository;

	@Autowired
	private AgriLandHoldingSizeRepository agriLandHoldingSizeRepository;

//	@Autowired
//	private SeasonRepository seasonRepository;
	
	@Autowired
	private FarmerCropInfoRepository farmerCropInfoRepo;

	private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat FORMAT2 = new SimpleDateFormat("dd-MM-yyyy");

	private String generateKey(int userId, String entityName) {
		Properties properties = new Properties();
		try {
			InputStream resourceStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("entity-code.properties");
			properties.load(resourceStream);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Thread.sleep(5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int fixLenght = Integer.parseInt(properties.getProperty("FIX_LENGHT"));
		String entityValue = properties.getProperty(entityName);
		String id = String.valueOf(userId);
		int prefixZero = fixLenght - id.length();
		StringBuffer sb = new StringBuffer(entityValue);
		for( int i=0; i<prefixZero; i++) {
			sb.append("0");
		}
		sb.append(id);
		sb.append(System.currentTimeMillis());
		return sb.toString();
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseMessage getLeadCallingDetail(String taskId) {
		ResponseMessage message = new ResponseMessage();
		Optional<Task> task = taskRepository.findById(taskId);
		Optional<ViewLeadCallingDetailInfo> info = viewLeadCallingDetailInfoRepository.findById(taskId);
		if(info.isEmpty()) {
			message.setStatus(206);
			message.setMessage("Task not found!");
			return message;
		} 
		LeadCallingDetailModel model = new LeadCallingDetailModel(info.get());

		MasterData master = new MasterData();
		List<Commodity> commodities = null;
		/*try {

			commodities = commodityRepository.findByRegion(info.get().getFarmerId());
		}catch (Exception e){
			e.getMessage();
		}*/
//		master.setMajorcropsgrownlist(commodities);
		List<Language> languages = languageRepository.findByStatus(1);
		master.setSpeakinglanguagelist(languages);
		List<MobileType> mobileTypes = mobileTypeRepository.findByStatus(1);
		master.setMobiletypelist(mobileTypes);
		List<ProxyRelationType> proxyRelationType = proxyRelationTypeRepository.findByStatus(1);
		master.setProxyRelationType(proxyRelationType);
		List<VipStatus> vipStatus = vipStatusRepository.findByStatus(1);
		master.setStatuslist(vipStatus);
		List<VipDesignation> designationList = vipDesignationRepository.findByStatus(1);
		master.setDesignationlist(designationList);
		if(task.get().getEntityTypeId() == 4) {
			/** Added for POI : Start */
			List<PoiDataModel> meetingPointList = vipDesignationRepository.getMeetingPointListByTaskId(taskId);
			master.setMeetingpointlist(meetingPointList);
			/** Added for POI : End */
		} else {
			List<PoiDataModel> meetingPointList = vipDesignationRepository.getMeetingPointListByVillageId(info.get().getVillageId());
			master.setMeetingpointlist(meetingPointList);
		}
		
		/** added for farmer major crop-CDT-Ujwal: Start */		
//		List<Season> seasonList = seasonRepository.findByStatus(1);
//		master.setSeasonList(seasonList);
		List<ViewFarmerCropInfo> farmerCropInfoList = viewFarmerCropInfoRepo.findByTaskId(taskId, 0);
		master.setFarmerCropInfoList(farmerCropInfoList);
		/** added for farmer major crop-CDT-Ujwal: End */

		/** Fetching only not selected commodity RinkeshKM*/
/*		List<Integer> commodityIdList = farmerCropInfoList.stream().map(ViewFarmerCropInfo::getCommodityId).collect(Collectors.toList());
		List<Commodity> commodities;
		if (commodityIdList.size()>0){
			commodities = commodityRepository.findByRegionIdAndCommodityId(info.get().getRegionId(),  commodityIdList);
		} else {
			commodities = commodityRepository.findByRegionId(info.get().getRegionId());
		}
		master.setMajorcropsgrownlist(commodities);*/

		master.setLeadStatus(callingStatusRepository.findCallingStatusForCrop(1));

		model.setMasterData(master);
		
		model.setSellerType(viewNonTechnicalCallingListForwardRepository.findSellerTypeNameByTaskId(taskId));

		if(info.get().getMajorCropList() != null && !info.get().getMajorCropList().trim().isBlank()) {
			List<Integer> cropIds = Arrays.asList(info.get().getMajorCropList().split(","))
					.stream().map(i -> Integer.parseInt(i.trim()))
					.collect(Collectors.toList());

			if (commodities != null) {
				List<Commodity> crops = commodities.stream()

						.filter(c -> cropIds.contains(c.getId()))
						.collect(Collectors.toList());
				model.setMajorcropsgrown(crops);
			}
		} else {
			model.setMajorcropsgrown(new ArrayList<>());
		}
		if(info.get().getSpeakingLanguageList() != null && !info.get().getSpeakingLanguageList().trim().isBlank()) {
			List<Integer> speakLangIds = Arrays.asList(info.get().getSpeakingLanguageList().split(","))
					.stream().map(l -> Integer.parseInt(l.trim()))
					.collect(Collectors.toList());
			List<Language> langs = languages.stream().filter(l -> speakLangIds.contains(l.getId()))
					.collect(Collectors.toList());
			model.setSpeakinglanguage(langs);
		} else {
			model.setSpeakinglanguage(new ArrayList<>());
		}

		List<String> mlVisitList = new ArrayList<>();
		Calendar next30DaysCal = Calendar.getInstance();
		next30DaysCal.add(Calendar.DAY_OF_YEAR, 30);
		Calendar nextDayCal = Calendar.getInstance();
		nextDayCal.add(Calendar.DAY_OF_YEAR, 1);
		List<VehicleSchedule> visits = vehicleScheduleRepository.findByVillageIdAndVisitDateBetween(info.get().getVillageId(), new Date(nextDayCal.getTimeInMillis()), new Date(next30DaysCal.getTimeInMillis()));
		visits.forEach(v -> mlVisitList.add(FORMAT.format(v.getVisitDate())));
		model.setMlvisit(mlVisitList);
		message.setMessage("success");
		message.setStatus(200);
		message.setData(model);
		return message;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage updateTask(RejectTaskModel rejectTaskModel) {
		ResponseMessage response = new ResponseMessage();
		Optional<Task> taskOptional = taskRepository.findById(rejectTaskModel.getTaskid());
		if(taskOptional.isPresent() 
				&& taskOptional.get().getAssigneeId().equals(0) 
				&& taskOptional.get().getStatus().equals(0) 
				&& taskOptional.get().getTaskTypeId().equals(10)) {
			Task task = taskOptional.get();
			TaskHistory history = new TaskHistory();
			String id = generateKey(task.getAssigneeId(), "TASK_HISTORY");
			history.setId(id);
			history.setTaskId(task.getId());
			history.setTaskDate(task.getTaskDate());
			history.setStartTime(task.getStartTime());
			history.setEndTime(task.getEndTime());
			history.setTaskTypeId(task.getTaskTypeId());
			history.setAssigneeId(rejectTaskModel.getUserid());
			history.setEntityTypeId(task.getEntityTypeId());
			history.setEntityId(task.getEntityId());
			/** added for calling status list - Pranay */
			String comment = callingStatusRepository.findNameByCallingStatusId(rejectTaskModel.getCallingstatus());
			/*
			 * String comment = null; switch(rejectTaskModel.getCallingstatus()) { case
			 * 1:comment = "Farmer didn't receive call";break; case 2:comment =
			 * "Not Reachable";break; case 3:comment = "Farmer Rejected Call";break; case
			 * 4:comment = "Technical Glitch";break; case 5:comment =
			 * "Not Interested";break; case 6:comment = "Not a farmer";break;
			 * default:comment = "Unknown"; }
			 */
			if(rejectTaskModel.getCallingstatus() == 1 || rejectTaskModel.getCallingstatus()== 2 || rejectTaskModel.getCallingstatus() == 4) {
				/** 
				 * changes for setting farmer as not interested 
				 * if the count is repeated more than 2 times for the status 1, 2 and 4
				 */
				Integer count = 0;
				count = taskHistoryRepository.getCountForCommentByTaskId(rejectTaskModel.getTaskid());
				if (count > 2) {
					comment = "Not Interested";
					history.setComment(comment);
					history.setStatus(callingStatusRepository.findIdByName(comment));
					task.setAssigneeId(rejectTaskModel.getUserid());
					task.setStatus(3);
				} else {
					history.setComment(comment);
					history.setStatus(4);
					Calendar today = Calendar.getInstance();
					today.add(Calendar.DAY_OF_YEAR, 1);
					task.setTaskDate(new Date(today.getTimeInMillis()));
					task.setAssigneeId(0);
					task.setStatus(0);
				}
				/** changes end */
			} else {
				history.setComment(comment);
				history.setStatus(3);
				task.setStatus(3);
				task.setAssigneeId(rejectTaskModel.getUserid());
			}
			taskRepository.save(task);
			taskHistoryRepository.save(history);
			response.setStatus(200);
			response.setMessage("Successfully updated");
			return response;
		} else {
			response.setStatus(400);
			response.setMessage("This Task is already completed, take next task for completion.");
			return response;
		}
	}



	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage saveLeadCallingDetail(LeadCallingDetailRequestModel leadCallingDetailRequestModel) {
		ResponseMessage message = new ResponseMessage();
		try {
			Optional<Task> taskOptional = taskRepository.findById(leadCallingDetailRequestModel.getTaskid());
			if(taskOptional.isPresent()
					&& taskOptional.get().getAssigneeId().equals(0)
					&& taskOptional.get().getStatus().equals(0)
					&& taskOptional.get().getTaskTypeId().equals(10)) {
				Task task  = taskOptional.get();

				TaskHistory history = new TaskHistory();
				String id = generateKey(leadCallingDetailRequestModel.getUserid(), "TASK_HISTORY");
				history.setId(id);
				history.setTaskId(task.getId());
				history.setTaskDate(task.getTaskDate());
				history.setStartTime(task.getStartTime());
				history.setEndTime(task.getEndTime());
				history.setTaskTypeId(task.getTaskTypeId());
				history.setAssigneeId(leadCallingDetailRequestModel.getUserid());
				history.setEntityTypeId(task.getEntityTypeId());
				history.setEntityId(task.getEntityId());
				history.setStatus(2);
				history.setComment(leadCallingDetailRequestModel.getComments());

				List<Date> scheduleDates = leadCallingDetailRequestModel.getScheduledate().stream()
						.map(t -> {
							try {
								return FORMAT.parse(t).getTime();
							} catch (ParseException e) {
								throw new RuntimeException("Invalid Date");
							}
						})
						.sorted(Comparator.naturalOrder()).map(d -> new Date(d)).collect(Collectors.toList());

				// if no ml visit
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(scheduleDates.get(scheduleDates.size() -1).getTime());
				cal.set(Calendar.HOUR, 23);
				cal.set(Calendar.MINUTE, 59);
				cal.set(Calendar.SECOND, 59);
				Optional<Farmer> farmer;
				if(task.getEntityTypeId() == 4) {
					farmer = farmerRepository.findById(task.getEntityId());
				} else {
					farmer = farmerRepository.findByCaseId(task.getEntityId());
				}
				List<VehicleSchedule> visits = vehicleScheduleRepository.findByVillageIdAndVisitDateIn(farmer.get().getVillageId(), scheduleDates);
				//vehicleScheduleRepository.findByVillageIdAndVisitDateBetween(farmer.get().getVillageId(), new Date(scheduleDates.get(0)), new Date(cal.getTimeInMillis()));


				if(visits.size() == 0) {
					task.setTaskDate(scheduleDates.get(0));
					task.setAssigneeId(0);
					task.setStatus(0);
					TaskHistory taskHistory = taskHistoryRepository.findTop1ByTaskIdAndStatusAndTaskTypeIdInOrderByTaskTypeIdDesc(task.getId(), 2, List.of(1,2,3,4,5));
					if(taskHistory == null) {
						task.setTaskTypeId(1);
					} else if(taskHistory.getTaskTypeId() < 5) {
						task.setTaskTypeId(taskHistory.getTaskTypeId()+1);
					} else {
						throw new RuntimeException("Invalid Task History");
					}
//					scheduleDates.stream().skip(1).map(d -> new TaskFutureDates(task.getId(), d)).forEach(taskFutureDatesRepository::save);
				} else if(scheduleDates.size() == 1){
					task.setStatus(2);
					history.setComment("schedule with ML visit on date "+FORMAT.format(visits.get(0).getVisitDate())+" | "+history.getComment());
				} else if(scheduleDates.size() > 1 && visits.size() > 1){
					message.setStatus(400);
					message.setMessage("Please select only one ML visit date.");
					return message;
				} else {
					message.setStatus(400);
					message.setMessage("Please select either Regular or ML visit date.");
					return message;
				}

				VipDesignation vipDesignation = null;
				if(leadCallingDetailRequestModel.getDesignation() != null) {
					vipDesignation = vipDesignationRepository.findByName(leadCallingDetailRequestModel.getDesignation());
				}

				/**
				 * Based on farmer crops create task -CDT-Ujwal
				 * */
				List<Task> getTaskList = this.createTaskBaseOnMajorCrops(task, leadCallingDetailRequestModel.getCropInfoId(),
						leadCallingDetailRequestModel.getUserid());
				List<Task> filterTaskList = getTaskList.stream().filter(t-> (t.getFarmerCropInfoId() != null)).collect(Collectors.toList());
				for (Task cropTask : filterTaskList) {

					scheduleDates.stream().skip(1).map(d -> new TaskFutureDates(cropTask.getId(), d)).forEach(taskFutureDatesRepository::save);
				}

				List<TaskHistory> updateTaskHistory = updateTaskHistoryBaseOnMajorCrops(history, leadCallingDetailRequestModel.getCropInfoId(),
						leadCallingDetailRequestModel.getUserid(), getTaskList.stream().map(Task::getId).collect(Collectors.toList()));

				taskHistoryRepository.saveAll(updateTaskHistory);

				taskRepository.saveAll(getTaskList);

//				taskHistoryRepository.save(history);
//				taskRepository.save(task);

				Vip vip = vipRepository.findByFarmerId(task.getEntityId());
				if(vip == null && leadCallingDetailRequestModel.getVip() == true) {
					vip = new Vip();
					String vipId = generateKey(task.getAssigneeId(), "VIP");
					vip.setId(vipId);
					vip.setFarmerId(task.getEntityId());
					vip.setName(farmer.get().getFarmerName());
					vip.setPrimaryNumber(farmer.get().getPrimaryMobNumber());
					vip.setAlternateNumber(farmer.get().getPrimaryMobNumber());
					vip.setVillageId(farmer.get().getVillageId());
				}
				if(vip != null) {
					vip.setOtherDesignation(leadCallingDetailRequestModel.getOtherDesignation());
					vip.setStatus(leadCallingDetailRequestModel.getStatus());
					vip.setVipDesignation(vipDesignation != null ? vipDesignation.getId() : null);
					vipRepository.save(vip);
				}

				/**
				 * update lead status based on selected crops
				 * */
				if (leadCallingDetailRequestModel.getCropInfoId() != null){
					List<FarmerCropInfo> getExistingFarmerCropList = farmerCropInfoRepo.getFarmerCropInfoByCropInfoIds(leadCallingDetailRequestModel.getCropInfoId());
					if (getExistingFarmerCropList != null){
						for (FarmerCropInfo farmerCropInfo : getExistingFarmerCropList) {
							farmerCropInfo.setLeadCallingStatus(1);
						}
					}
					farmerCropInfoRepo.saveAll(getExistingFarmerCropList);
				}

				updateFarmer(leadCallingDetailRequestModel, task);


				message.setStatus(200);
				message.setMessage("Details have been saved successfully.");
			} else {
				message.setStatus(400);
				message.setMessage("This Task is already completed, take next task for completion.");
			}
			return message;
		} catch(Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
			return message;
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void updateFarmer(LeadCallingDetailRequestModel leadCallingDetailRequestModel, Task task) {
		/*String majorCrop = leadCallingDetailRequestModel.getMajorcropsgrown().stream()
				.map(c -> Integer.toString(c.getId())).collect(Collectors.joining(","));*/
		String speakingLang = leadCallingDetailRequestModel.getSpeakinglanguage().stream()
				.map(l -> Integer.toString(l.getId())).collect(Collectors.joining(","));
		Integer govtIdProof = leadCallingDetailRequestModel.getGovernmentidproof() != null && leadCallingDetailRequestModel.getGovernmentidproof() == true ? 1 : 0;
		Integer ownLand = leadCallingDetailRequestModel.getOwnland() != null && leadCallingDetailRequestModel.getOwnland() == true ? 1 : 0;
		Integer irrigationLand = leadCallingDetailRequestModel.getIrrigatedland() != null && leadCallingDetailRequestModel.getIrrigatedland() == true ? 1 : 0;
		Integer cdt = leadCallingDetailRequestModel.getWillingnessoffarmer() != null && leadCallingDetailRequestModel.getWillingnessoffarmer() == true ? 1 : 0;
		Integer vip = leadCallingDetailRequestModel.getVip() != null && leadCallingDetailRequestModel.getVip() == true ? 1 : 0;

		farmerRepository.updateDetail(leadCallingDetailRequestModel.getFathername(),
				leadCallingDetailRequestModel.getAlternatemobileno(),
				leadCallingDetailRequestModel.getReferenceperson(),
				leadCallingDetailRequestModel.getReferencepersonmobileno(),
				govtIdProof, ownLand, irrigationLand,
				leadCallingDetailRequestModel.getFarmsize(), /*majorCrop,*/
				leadCallingDetailRequestModel.getCroppingarea(),
				speakingLang, leadCallingDetailRequestModel.getMobiletype(),
				cdt, vip, leadCallingDetailRequestModel.getMeetingpoint(),
//				leadCallingDetailRequestModel.getSellerName(),// added for POI - Pranay
				task.getEntityId());
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage getFieldMonitoringList(Integer userId) {
		ResponseMessage response = new ResponseMessage();
		response.setStatus(200);
		response.setMessage("success");
		List<HarvestMonitoringTechnicalCalling> allTask = harvestMonitoringRepository.findAll();
		List<HarvestMonitoringModal> monitoringTaskList =  allTask.stream().map(HarvestMonitoringModal::new).collect(Collectors.toList());
		response.setData(monitoringTaskList);
		return response;
	}



	@Override
	@Transactional(readOnly = true)
	public ResponseMessage getFieldMonitoringDetail(String taskId) {
//		ResponseMessage response = new ResponseMessage();
//		FieldMonitoringDetailModel model = new FieldMonitoringDetailModel();
//		model.setTaskId(taskId);
//		TaskHistory taskHistory = taskHistoryRepository.findTop1ByTaskIdAndStatusAndTaskTypeIdInOrderByTaskTypeIdDesc(taskId, 2, List.of(3,4));
//		Optional<ViewFieldMonitoringDetailVO> detailOp = viewFieldMonitoringDetailRepository.findById(taskId);
//		if(detailOp.isEmpty()) {
//			response.setStatus(204);
//			response.setMessage("No data found");
//			return response;
//		}
//
//		model.setType(detailOp.get().getTaskTypeId().equals(21) ? "Verification" : detailOp.get().getTaskTypeId().equals(11)? "Monitoring" : taskHistory.getTaskTypeId().equals(3) ? "Incomplete Verification":"Incomplete Monitoring");
//		model.setCaseId(detailOp.get().getEntityId());
//
//		CropInformationModel crop = new CropInformationModel(detailOp.get());
//		model.setCropinformation(crop);
//
//		List<Integer> irrigationSourceIds = Arrays.asList(detailOp.get().getIrrigationSourceId().split(",")).stream()
//				.map(i -> Integer.parseInt(i.trim())).collect(Collectors.toList());
//		List<Integer> irrigationMethodIds = Arrays.asList(detailOp.get().getIrrigationMethodId().split(",")).stream()
//				.map(i -> Integer.parseInt(i.trim())).collect(Collectors.toList());
//
//		IrrigationdetailsModel iModel = new IrrigationdetailsModel(detailOp.get());
//
//		List<IrrigationSource> source = irrigationSourceRepository.findAllById(irrigationSourceIds);
//		List<IrrigationMethod> method = irrigationMethodRepository.findAllById(irrigationMethodIds);
//		iModel.setIrrigationsource(source);
//		iModel.setIrrigationmethod(method);
//		model.setIrrigationdetails(iModel);
//
//		FertilizerModel fModel = new FertilizerModel(detailOp.get());
//		model.setFertilizer(fModel);
//
//		SeedtreatmentModel sModel = new SeedtreatmentModel(detailOp.get());
//
//		Optional<SeedTreatmentAgent> agentOp = seedTreatmentAgentRepository.findById(detailOp.get().getSeedTreatmentAgentId());
//		if(agentOp.isPresent()) {
//			sModel.setAgent(agentOp.get().getName());
//		}
//		model.setSeedtreatment(sModel);
//
//		RemedialMeasureModel rModel = new RemedialMeasureModel(detailOp.get());
//		model.setRemedialmeasure(rModel);
//		Calendar cal = Calendar.getInstance();
//		cal.setFirstDayOfWeek(Calendar.MONDAY);
//		cal.setMinimalDaysInFirstWeek(4);
//		model.setCurrentYear(cal.get(Calendar.YEAR));
//		model.setCurrentWeek(cal.get(Calendar.WEEK_OF_YEAR));
//
//		ScheduleModel scModel = new ScheduleModel(detailOp.get());
//		model.setSchedule(scModel);
//
//		response.setData(model);
//		response.setMessage("Success");
//		response.setStatus(200);
//		return response;
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseMessage getHarvestMonitoringDetail(String taskId) {
		ResponseMessage response = new ResponseMessage();
		FieldMonitoringDetailModel model = new FieldMonitoringDetailModel();
		model.setTaskId(taskId);
		TaskHistory taskHistory = taskHistoryRepository.findTop1ByTaskIdAndStatusAndTaskTypeIdInOrderByTaskTypeIdDesc(taskId, 2, List.of(3,4));
		Optional<ViewFieldMonitoringDetailVO> detailOp = viewFieldMonitoringDetailRepository.getFieldMonitoringByTaskId(taskId);
		if(detailOp.isEmpty()) {
			response.setStatus(204);
			response.setMessage("No data found");
			return response;
		}

//		model.setType(detailOp.get().getTaskTypeId().equals(21) ? "Verification" : detailOp.get().getTaskTypeId().equals(11)? "Monitoring" : taskHistory.getTaskTypeId().equals(3) ? "Incomplete Verification":"Incomplete Monitoring");
		model.setCaseId(detailOp.get().getEntityId());

		CropInformationModel crop = new CropInformationModel(detailOp.get());
		model.setCropinformation(crop);

		List<Integer> irrigationSourceIds = Arrays.asList(detailOp.get().getIrrigationSourceId().split(",")).stream()
				.map(i -> Integer.parseInt(i.trim())).collect(Collectors.toList());
		List<Integer> irrigationMethodIds = Arrays.asList(detailOp.get().getIrrigationMethodId().split(",")).stream()
				.map(i -> Integer.parseInt(i.trim())).collect(Collectors.toList());

		IrrigationdetailsModel iModel = new IrrigationdetailsModel(detailOp.get());

		List<IrrigationSource> source = irrigationSourceRepository.findAllById(irrigationSourceIds);
		List<IrrigationMethod> method = irrigationMethodRepository.findAllById(irrigationMethodIds);
		iModel.setIrrigationsource(source);
		iModel.setIrrigationmethod(method);
		model.setIrrigationdetails(iModel);

		/** Contact information changes added Rinkesh KM*/
		model.setContactInformation(new ContactInformation(detailOp.get()));

		/** Right information changes added Rinkesh KM*/
		model.setRightInformation(new RightInformation(detailOp.get()));

		/** Right Id set Rinkesh KM*/
		model.setRightId(detailOp.get().getRightId());

		/** Setting min and max date in calender*/
		model.setCalender(new CalenderModel(detailOp.get()));
		FertilizerModel fModel = new FertilizerModel(detailOp.get());
		model.setFertilizer(fModel);

		SeedtreatmentModel sModel = new SeedtreatmentModel(detailOp.get());

		Optional<SeedTreatmentAgent> agentOp = seedTreatmentAgentRepository.findById(detailOp.get().getSeedTreatmentAgentId());
		if(agentOp.isPresent()) {
			sModel.setAgent(agentOp.get().getName());
		}
		model.setSeedtreatment(sModel);

		RemedialMeasureModel rModel = new RemedialMeasureModel(detailOp.get());
		model.setRemedialmeasure(rModel);
		Calendar cal = Calendar.getInstance();
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		cal.setMinimalDaysInFirstWeek(4);
		model.setCurrentYear(cal.get(Calendar.YEAR));
		model.setCurrentWeek(cal.get(Calendar.WEEK_OF_YEAR));

		ScheduleModel scModel = new ScheduleModel(detailOp.get());
		model.setSchedule(scModel);

		response.setData(model);
		response.setMessage("Success");
		response.setStatus(200);
		return response;
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public ResponseMessage updateMonitoringTask(FieldMonitoringRequestModel model) {
		ResponseMessage response = new ResponseMessage();
		try {

			Optional<Task> taskOp  = taskRepository.findById(model.getTaskid());
			if(taskOp.isEmpty() 
					|| !taskOp.get().getAssigneeId().equals(0) 
					|| !taskOp.get().getStatus().equals(0) 
					|| !(taskOp.get().getTaskTypeId().equals(22)
							|| taskOp.get().getTaskTypeId().equals(21)
							|| taskOp.get().getTaskTypeId().equals(10))) {
				response.setStatus(400);
				response.setMessage("This Task is already completed, take next task for completion.");
				return response;
			}

			Task task = taskOp.get();
			TaskHistory taskHistory = taskHistoryRepository.findTop1ByTaskIdAndStatusAndTaskTypeIdInOrderByTaskTypeIdDesc(task.getId(), 2, List.of(3,4));
			TaskHistory history = new TaskHistory();
			String id = generateKey(model.getUserid(), "TASK_HISTORY");
			history.setId(id);
			history.setTaskId(task.getId());
			history.setTaskDate(task.getTaskDate());
			history.setStartTime(task.getStartTime());
			history.setEndTime(task.getEndTime());
			history.setTaskTypeId(task.getTaskTypeId());
			history.setAssigneeId(model.getUserid());
			history.setEntityTypeId(task.getEntityTypeId());
			history.setEntityId(task.getEntityId());
			history.setStatus(2);


			if(model.getVisitrequired() != null && model.getVisitrequired()) {
				Optional<FarmCase> farmCaseOp = farmCaseRepository.findById(task.getEntityId());
				if(farmCaseOp.isEmpty()) {
					response.setStatus(400);
					response.setMessage("Data not found");
					return response;
				}
				Task newTask = new Task();
				Calendar cal = Calendar.getInstance();
				cal.setTime(farmCaseOp.get().getNmd());
				cal.add(Calendar.DAY_OF_YEAR, 30-6);
				String taskId = generateKey(model.getUserid(), "TASK");
				newTask.setId(taskId);
				newTask.setTaskDate(new Date(cal.getTimeInMillis()));
				newTask.setTaskTypeId(22);
				newTask.setAssigneeId(0);
				newTask.setStatus(0);
				newTask.setEntityTypeId(task.getEntityTypeId());
				newTask.setEntityId(task.getEntityId());
				newTask.setStartTime(task.getStartTime());
				newTask.setEndTime(task.getEndTime());
				newTask.setBarcode(task.getBarcode());
				taskRepository.save(newTask);
				cal.add(Calendar.DAY_OF_YEAR, 6);
				farmCaseOp.get().setNmd(new Date(cal.getTimeInMillis()));
				farmCaseRepository.save(farmCaseOp.get());

				history.setComment("Visit not required | "+ model.getComments());
				task.setStatus(2);
			} else {
				String comment = null;
				switch(model.getCallingstatus()) {
				case 1: comment = "Schedule Visit";break;
				case 2: comment = "Farmer didn't receive call";break;
				case 3: comment = "Not Reachable";break;
				case 4: comment = "Farmer Rejected Call";break;
				case 5: comment = "Technical Glitch"; break;
				case 6: comment = "Not Interested";break;
				default:comment = "Unknown";
				}

				history.setComment(comment+" | "+model.getComments());
				if(model.getCallingstatus() == 1) {

					List<Date> scheduleDates = model.getDateschedule().stream()
							.map(t -> {
								try {
									return FORMAT2.parse(t).getTime();
								} catch (ParseException e) {
									throw new RuntimeException("Invalid Date");
								}
							})
							.sorted(Comparator.naturalOrder()).map(d -> new Date(d)).collect(Collectors.toList());

					task.setTaskDate(scheduleDates.get(0));
					task.setAssigneeId(0);
					task.setStatus(1);
					if (taskHistory != null){
						if(task.getTaskTypeId().equals(21) || taskHistory.getTaskTypeId().equals(3)) {
							task.setTaskTypeId(4);
						} else {
							task.setTaskTypeId(5);
						}
					}
					scheduleDates.stream().skip(1).map(d -> new TaskFutureDates(task.getId(), d)).forEach(taskFutureDatesRepository::save);
				} else if(model.getCallingstatus() == 6){
					task.setStatus(3);
					task.setAssigneeId(model.getUserid());
					history.setStatus(3);
				} else {
					history.setStatus(4);
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.DAY_OF_YEAR, 1);
					task.setTaskDate(new Date(cal.getTimeInMillis()));
					task.setAssigneeId(0);
					task.setStatus(0);
				}


			}

			Map<String, Object> fetchedRightInfo = rightDao.getRights(model.getRightId());
			ObjectMapper mapper = new ObjectMapper();

			Rights rights = mapper.convertValue(fetchedRightInfo, Rights.class);

			if (rights != null){
				rights.setDeliverableQuantity(model.getDeliverableQuantity());
				rights.setHarvestedQuantity(model.getHarvestedQuantity());
				rights.setVersionNumber(rights.getVersionNumber() + 1);

				rightsRepository.save(rights);
			}

			taskRepository.save(task);
			taskHistoryRepository.save(history);
			response.setMessage("Details have been saved successfully.");
			response.setStatus(200);
			return response;
		} catch(Exception e) {
			e.printStackTrace();
			response.setStatus(500);
			response.setMessage("An Error Occurred, Please Try Again Later.");
			return response;
		}
	}

    @Override
    public ResponseMessage getForwardNonTechnicalCallingList(@Valid DataTablesInput input, Integer stateId, Integer regionId,
                                                             Integer districtId, Integer villageId, Integer commodityId, Integer areaId)
    {
        ResponseMessage response = new ResponseMessage();
        try
        {
            List<Column> columns = input.getColumns();
            for (Column c : columns)
            {
                if (c.getData().equalsIgnoreCase("serialNumber")
                        || c.getName().equalsIgnoreCase("serialNumber"))
                {
                    columns.remove(c);
                    break;
                }
            }

            Optional<AgriLandHoldingSize> agriLandHoldingSize = agriLandHoldingSizeRepository.findById(areaId);

            Specification<ViewNonTechnicalCallingListForward> geoSpecification = (Specification<ViewNonTechnicalCallingListForward>) (root, query, criteriaBuilder) -> {
                if (villageId != 0)
                {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("villageId"), villageId));
                } else if (districtId != 0)
                {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("districtId"), districtId));
                } else if (regionId != 0)
                {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("regionId"), regionId));
                } else if (stateId != 0)
                {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("stateId"), stateId));
                } else if (areaId != 0)
                {
                    return criteriaBuilder.and(criteriaBuilder.between(root.get("cropArea"), agriLandHoldingSize.get().getMinValue(), agriLandHoldingSize.get().getMaxValue()));
                } else if (commodityId != 0)
                {
                    return criteriaBuilder.and(criteriaBuilder.equal(root.get("commodityId"), commodityId));
                } else
                {
                    return null;
                }
            };
            DataTablesOutput<ViewNonTechnicalCallingListForward> data = viewNonTechnicalCallingListForwardRepository.findAll(input, geoSpecification);
            int count = input.getStart();
            for (ViewNonTechnicalCallingListForward d : data.getData())
            {
                d.setSerialNumber(++count);
            }
            response.setData(data);
            response.setMessage("success");
            response.setStatus(200);
        } catch (Exception e)
        {
            e.printStackTrace();
            response.setMessage("An Error Occurred, Please Try Again Later.");
            response.setStatus(500);
        }
        return response;
    }


	@Override
	public ResponseMessage getLeadCallingStateList() {
		ResponseMessage message = new ResponseMessage();
		try {
			List<Object[]> stateList = viewNonTechnicalCallingListForwardRepository.findAllStateList();
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
			for(Object[] state:stateList) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("stateId", state[0]);
				data.put("stateName", state[1]);
				response.add(data);
			}

			message.setData(response);
			message.setStatus(200);
			message.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}

	@Override
	public ResponseMessage getLeadCallingCommodityList()
	{
		ResponseMessage message = new ResponseMessage();
		try
		{
			List<Object[]> commodityList = viewNonTechnicalCallingListForwardRepository.findAllCommodityList();
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
			for (Object[] commodity : commodityList)
			{
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("commodityId", commodity[0]);
				data.put("commodityName", commodity[1]);
				response.add(data);
			}

			message.setData(response);
			message.setStatus(200);
			message.setMessage("Success");
		} catch (Exception e)
		{
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}

    @Override
    public ResponseMessage getLandHoldingSize()
    {
        ResponseMessage message = new ResponseMessage();
        try
        {
            List<Object[]> landHoldingList = viewNonTechnicalCallingListForwardRepository.getLandHoldingSize();
            List<Map<String, Object>> response = new ArrayList<>();
            for (Object[] landHolding : landHoldingList)
            {
                Map<String, Object> data = new HashMap<String, Object>();
                data.put("landHoldingId", landHolding[0]);
                data.put("landHoldingName", landHolding[1]);
                response.add(data);
            }

            message.setData(response);
            message.setStatus(200);
            message.setMessage("Success");
        } catch (Exception e)
        {
            e.printStackTrace();
            message.setStatus(500);
            message.setMessage("An Error Occurred, Please Try Again Later.");
        }
        return message;
    }

	@Override
	public ResponseMessage getLeadCallingRegionList(Integer stateId) {
		ResponseMessage message = new ResponseMessage();
		try {
			List<Object[]> regionList = viewNonTechnicalCallingListForwardRepository.findAllRegionList(stateId);
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
			for(Object[] region:regionList) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("regionId", region[0]);
				data.put("regionName", region[1]);
				response.add(data);
			}
			message.setData(response);
			message.setStatus(200);
			message.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}

	@Override
	public ResponseMessage getLeadCallingDistrictList(Integer regionId) {
		ResponseMessage message = new ResponseMessage();
		try {
			List<Object[]> districtList = viewNonTechnicalCallingListForwardRepository.findAllDistrictList(regionId);
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
			for(Object[] district:districtList) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("districtId", district[0]);
				data.put("districtName", district[1]);
				response.add(data);
			}
			message.setData(response);
			message.setStatus(200);
			message.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}

	@Override
	public ResponseMessage getLeadCallingVillageList(Integer districtId) {
		ResponseMessage message = new ResponseMessage();
		try {
			List<Object[]> villageList = viewNonTechnicalCallingListForwardRepository.findAllVillageList(districtId);
			List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
			for(Object[] village:villageList) {
				Map<String, Object> data = new HashMap<String, Object>();
				data.put("villageId", village[0]);
				data.put("villageName", village[1]);
				response.add(data);
			}
			message.setData(response);
			message.setStatus(200);
			message.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}

	/** added for calling status list - Pranay : Start */
	@Override
	public ResponseMessage getCallingStatusList() {
		ResponseMessage message = new ResponseMessage();
		try {
			List<Object[]> statusList = callingStatusRepository.findAllCallingStatusList();
			List<Map<String, Object>> response = new ArrayList<>();
			for(Object[] status : statusList) {
				Map<String, Object> data = new HashMap<>();
				data.put("statusId", status[0]);
				data.put("status", status[1]);
				response.add(data);
			}

			message.setData(response);
			message.setStatus(200);
			message.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}
	/** added for calling status list - Pranay : End */


	/** added for farmer major crop-CDT-Ujwal: Start */
	@Override
	public ResponseMessage getVarietyByCommodityId(Integer commodityId) {
		ResponseMessage message = new ResponseMessage();
		List<Map<String, Object>> response = new ArrayList<>();
		try {
			List<Object[]> varietyList = varietyRepository.findVarietyByCommdityId(commodityId);
			for(Object[] variety : varietyList) {
				Map<String, Object> data = new HashMap<>();
				data.put("varietyId", variety[0]);
				data.put("name", variety[1]);
				response.add(data);
			}

			message.setData(response);
			message.setStatus(200);
			message.setMessage("Success");
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}

	@Override
	public ResponseMessage saveAndUpdateFarmerCropInfo(FarmerCropInfo farmerCropInfo) {
		ResponseMessage message = new ResponseMessage();
		FarmerCropInfo cropInfo = null;
		try {
			if (farmerCropInfo.getCropInfoId() != null) {
				Optional<FarmerCropInfo> existFarmerCropInfo = farmerCropInfoRepo
						.findById(farmerCropInfo.getCropInfoId());
				if (existFarmerCropInfo.isPresent()) {
//					FarmerCropInfo checkExistRecordForSepc = farmerCropInfoRepo.existRecordForSpec(
//							farmerCropInfo.getFarmerId(),  farmerCropInfo.getCommodityId(),
//							farmerCropInfo.getVarietyId());
					/*if (checkExistRecordForSepc != null) {*/
							cropInfo = this.getWeekAndYear(farmerCropInfo.getSowingDate(), farmerCropInfo.getHarvest());
							farmerCropInfo.setHarvestWeek(cropInfo.getHarvestWeek());
							farmerCropInfo.setHarvestYear(cropInfo.getHarvestYear());
							farmerCropInfo.setSowingWeek(cropInfo.getSowingWeek());
							farmerCropInfo.setSowingYear(cropInfo.getSowingYear());
							farmerCropInfo.setLeadCallingStatus(0);
							farmerCropInfoRepo.save(farmerCropInfo);
							message.setMessage("Details have been updated successfully.");
							message.setStatus(200);
					/*} else {
						message.setMessage("Given records already exist.");
						message.setStatus(500);
					}*/
				}
			} else {

				/*FarmerCropInfo checkExistRecordForSepc = farmerCropInfoRepo.existRecordForSpec(
						farmerCropInfo.getFarmerId(),  farmerCropInfo.getCommodityId(),
						farmerCropInfo.getVarietyId());*/
				/*if (checkExistRecordForSepc == null) {*/
					String id = generateKey(farmerCropInfo.getUserId(),"FARMER_CROP_INFO");
					farmerCropInfo.setCropInfoId(id);
					cropInfo = this.getWeekAndYear(farmerCropInfo.getSowingDate(), farmerCropInfo.getHarvest());
					farmerCropInfo.setHarvestWeek(cropInfo.getHarvestWeek());
					farmerCropInfo.setHarvestYear(cropInfo.getHarvestYear());
					farmerCropInfo.setSowingWeek(cropInfo.getSowingWeek());
					farmerCropInfo.setSowingYear(cropInfo.getSowingYear());
					farmerCropInfo.setLeadCallingStatus(0);
					farmerCropInfoRepo.save(farmerCropInfo);
					message.setMessage("Details have been saved successfully.");
					message.setStatus(200);
//				} else {
//					message.setMessage("Given records already exist.");
//					message.setStatus(500);
//				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}
	
	@Override
	public ResponseMessage getFarmerExistingCropById(String farmerCropId) {
		ResponseMessage message = new ResponseMessage();
		try {
			if (farmerCropId != null) {
				List<ViewFarmerCropInfo> existRecord = viewFarmerCropInfoRepo.findCropInfoByCropInfoId(farmerCropId);
				if(!existRecord.isEmpty()) {
					message.setData(existRecord);
					message.setStatus(200);
					message.setMessage("Success");
				} else {
					message.setMessage("Record Not Found.");
					message.setStatus(500);
				}
			} else {
				message.setMessage("Case Id Requred");
				message.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		return message;
	}

	@Override
	public ResponseMessage deleteFarmerCropInfo(String farmerCropId) {
		ResponseMessage message = new ResponseMessage();
		try {
			if (farmerCropId != null) {
				Optional<FarmerCropInfo> existRecord = farmerCropInfoRepo.findCropInfoByCropInfoId(farmerCropId);
				if(existRecord.isPresent()) {
					farmerCropInfoRepo.deleteById(farmerCropId);
					message.setStatus(200);
					message.setMessage("Major crop details have been successfully deleted");
				} else {
					message.setMessage("Record Not Found.");
					message.setStatus(500);
				}
			} else {
				message.setMessage("Case Id Requred");
				message.setStatus(500);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
		
		return message;
	}
	
	@Override
	public ResponseMessage getFarmerMajorCropList(String taskId) {
		ResponseMessage message = new ResponseMessage();
		try {
			List<ViewFarmerCropInfo> farmerCropInfoList = null;
			if (taskId != null) {
				farmerCropInfoList = viewFarmerCropInfoRepo.findByTaskId(taskId, 0);
			}
			message.setData(farmerCropInfoList);
			message.setStatus(200);
			
		} catch (Exception e) {
			e.printStackTrace();
			message.setStatus(500);
			message.setMessage("An Error Occurred, Please Try Again Later.");
		}
	
//		
		return message;
	}
	/** added for farmer major crop-CDT-Ujwal: End */

	private FarmerCropInfo getWeekAndYear(Date sowing, Date harvest) {
		FarmerCropInfo farmerCropInfo = new FarmerCropInfo();
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		Calendar calendar = null;
		if (sowing != null) {
			calendar = Calendar.getInstance(Locale.UK);
			calendar.setTime(sowing);
			System.out.println("get week" + calendar.get(Calendar.WEEK_OF_YEAR));
			System.out.println("get year" + calendar.get(Calendar.YEAR));
			
			farmerCropInfo.setSowingWeek(calendar.get(Calendar.WEEK_OF_YEAR));
			farmerCropInfo.setSowingYear(calendar.get(Calendar.YEAR));
		} 
		if (harvest != null) {

			calendar = Calendar.getInstance(Locale.UK);
			calendar.setTime(harvest);
			System.out.println("get week" + calendar.get(Calendar.WEEK_OF_YEAR));
			System.out.println("get year" + calendar.get(Calendar.YEAR));
			
			farmerCropInfo.setHarvestWeek(calendar.get(Calendar.WEEK_OF_YEAR));
			farmerCropInfo.setHarvestYear(calendar.get(Calendar.YEAR));
		}
	
		
		return farmerCropInfo;
		
	}

	public List<Task> createTaskBaseOnMajorCrops(Task task, List<String> majorCropIds, Integer userId) {
		List<Task> updateTaskList = null;
		int totalFarmerCropIdCount;
		if (majorCropIds != null) {
			updateTaskList = new ArrayList<>();

			Integer totalFarmerCopInfoCount = farmerCropInfoRepo.getFarmerCropCount(task.getEntityId());
			Integer totalTaskCount = taskRepository.getAllFarmerCropInfoIds(task.getEntityId());
			Date getTaskDate = task.getTaskDate();
			task.setTaskDate(new Date(System.currentTimeMillis()));
			task.setTaskTypeId(10);
			/*if (!totalFarmerCopInfoCount.equals(totalTaskCount)) {

				*//*Task newTask = new Task();
				if (!(totalTaskCount == 0)){
					newTask.setId(generateKey(userId, "TASK"));
					newTask.setAssigneeId(0);
					newTask.setTaskTypeId(10);
					newTask.setEntityId(task.getEntityId());
					newTask.setEntityTypeId(4);
					newTask.setFarmerCropInfoId(null);
					newTask.setTaskDate(new Date(System.currentTimeMillis()));
					newTask.setStatus(0);
					taskRepository.save(newTask);
				}*//*
			} else {
				for (int i = 0; i < majorCropIds.size(); i++) {
					int existFarmerCropInfoId = taskRepository.getNotAssignTaskData(majorCropIds.get(i));
					if (existFarmerCropInfoId == 0){
						task.setFarmerCropInfoId(majorCropIds.get(0));
						updateTaskList.add(task);
						break;
					}
				}
			}*/
			UpdateOrCreateTask(task, majorCropIds, userId, updateTaskList, getTaskDate);

			/**
			 * check task count of farmerCropInfoId and farmerCropInfo count are match
			 * then delete extra task of CCTC
			 * */
			if ((updateTaskList.size() + totalTaskCount) == totalFarmerCopInfoCount){
				int count = taskRepository.deleteExtraTask(task.getId());
			}


			/*task.setFarmerCropInfoId(majorCropIds.get(0));
			updateTaskList.add(task);
			if (majorCropIds.size() > 0) {
				for (int i = 1; i < majorCropIds.size(); i++) {
					Task newTask = new Task();
					newTask.setId(generateKey(userId, "TASK"));
					newTask.setAssigneeId(task.getAssigneeId());
					newTask.setTaskTypeId(task.getTaskTypeId());
					newTask.setEntityId(task.getEntityId());
					newTask.setEntityTypeId(task.getEntityTypeId());
					newTask.setFarmerCropInfoId(majorCropIds.get(i));
					newTask.setTaskDate(task.getTaskDate());
					newTask.setStatus(task.getStatus());
					updateTaskList.add(newTask);
				}
			}*/
		}
		return updateTaskList;
	}

	private void UpdateOrCreateTask(Task task, List<String> majorCropIds, Integer userId, List<Task> updateTaskList, Date taskDate) {
		for (String farmerCropInfoId : majorCropIds) {
			/**
			 * check farmerCropInfoId are present or not in task table with taskTypeId 10
			 **/
			Task data = this.checkTaskExist(farmerCropInfoId);
			/**
			 * If task present in task table again given farmerCropInfoId
			 * then update taskTypeId, assigneeId and taskDate
			 * */
			if (data != null){
				data.setAssigneeId(0);
				data.setTaskTypeId(1);
				data.setTaskDate(taskDate);

			}
			/**
			 * else create new task
			 * */
			else {
				data = this.createNewTask(task, farmerCropInfoId, userId, taskDate);

			}
			updateTaskList.add(data);
		}
	}

	public List<TaskHistory> updateTaskHistoryBaseOnMajorCrops(TaskHistory taskHistory, List<String> majorCropIds, Integer userId,
											   List<String> task) {

		List<TaskHistory> updateHistoryTaskList = null;
		if (majorCropIds != null) {
			updateHistoryTaskList = new ArrayList<>();
			taskHistory.setFarmerCropInfoId(majorCropIds.get(0));
			taskHistory.setTaskId(task.get(0));
			updateHistoryTaskList.add(taskHistory);
			if (majorCropIds.size() > 0) {
				for (int i = 1; i < task.size(); i++) {
					TaskHistory newTaskHistory = new TaskHistory();
					newTaskHistory.setId(generateKey(userId, "TASK_HISTORY"));
					newTaskHistory.setFarmerCropInfoId(majorCropIds.get(i));


					newTaskHistory.setTaskId(task.get(i));
					newTaskHistory.setTaskDate(taskHistory.getTaskDate());
					newTaskHistory.setStartTime(taskHistory.getStartTime());
					newTaskHistory.setEndTime(taskHistory.getEndTime());
					newTaskHistory.setTaskTypeId(taskHistory.getTaskTypeId());
					newTaskHistory.setAssigneeId(taskHistory.getAssigneeId());
					newTaskHistory.setEntityTypeId(taskHistory.getEntityTypeId());
					newTaskHistory.setEntityId(taskHistory.getEntityId());
					newTaskHistory.setStatus(taskHistory.getStatus());
					newTaskHistory.setComment(taskHistory.getComment());
					updateHistoryTaskList.add(newTaskHistory);
				}

			}
		}
		return updateHistoryTaskList;
	}

	@Override
	public List<Commodity> getCommodityListByCropType(Integer cropTypeId, Integer regionId){
		List<Commodity> commodityList = null;
		try {
			if (cropTypeId == 1) {
				commodityList = commodityRepository.farForwardCommodityList(regionId);
			} else {
				commodityList = commodityRepository.nearForwardCommodityList(regionId);
			}
		}catch (Exception e){
			e.getMessage();
		}
		return commodityList;
	}

	private Task checkTaskExist(String farmerCropInfoId){
		Task task = null;
		if (farmerCropInfoId != null){
			task = taskRepository.getTaskByFarmerCropInfoId(farmerCropInfoId);
		}
		return task;
	}

	private Task createNewTask(Task task,String farmerCropInfoId, Integer userId, Date taskDate){
		Task newTask = new Task();
		if (farmerCropInfoId != null){
			newTask.setId(generateKey(userId, "TASK"));
			newTask.setAssigneeId(task.getAssigneeId());
			newTask.setTaskTypeId(1);
			newTask.setEntityId(task.getEntityId());
			newTask.setEntityTypeId(task.getEntityTypeId());
			newTask.setFarmerCropInfoId(farmerCropInfoId);
			newTask.setTaskDate(taskDate);
			newTask.setStatus(task.getStatus());
		}
		return newTask;
	}
}
