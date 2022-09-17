package com.drkrishi.rlt.service;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;
import com.drkrishi.rlt.entity.*;
import com.drkrishi.rlt.model.*;
import com.drkrishi.rlt.model.Fertilizer;
import com.drkrishi.rlt.model.Recommendation;
import com.drkrishi.rlt.model.Stress;
import com.drkrishi.rlt.model.vo.CaseCropInfoVo;
import com.drkrishi.rlt.model.vo.RecommendationVO;
import com.drkrishi.rlt.model.vo.StressCaseVO;
import com.drkrishi.rlt.model.vo.TaskSpotHealthImageVo;
import com.drkrishi.rlt.properties.AppProperties;
import com.drkrishi.rlt.repository.CaseCropInfoRepository;
import com.drkrishi.rlt.repository.RecommendationRepository;
import com.drkrishi.rlt.repository.TaskSpotHealthImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.drkrishi.rlt.dao.DiagnoseDao;
import com.drkrishi.rlt.dao.MasterDao;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DiagnoseServiceImple implements DiagnoseService{

	@Autowired
	DiagnoseDao diagnoseDao;

	@Autowired
	RecommendationRepository recommendationRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	MasterDao masterDao;

	@Autowired
	CaseCropInfoRepository caseCropInfoRepository;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private TaskSpotHealthImageRepository taskSpotHealthImageRepository;

	private static final String validateBandUrl = "/v1.0/portal/validate-band";

	@Override
	public DiagnoseGACModel getGACInformation(String taskId) {
		
		List<Task> tasks = diagnoseDao.getTask(taskId);
		if(tasks.size() > 0) {
			Task task = tasks.get(0);
			if(task.getTaskTime() == null) {
				task.setTaskTime(new Time(System.currentTimeMillis()));
				diagnoseDao.setStartDate(taskId, task);
			}
		}
		
		List<StaticData> staticData = diagnoseDao.getStaticDataByKey(List.of("basePath", "aerialImagePath"));
		Optional<String> pathOp = staticData.stream().filter(s -> s.getDataKey().equals("basePath")).map(s -> s.getDataValue()).findFirst();	
		Optional<String> aerialImagePathOp = staticData.stream().filter(s -> s.getDataKey().equals("aerialImagePath")).map(s -> s.getDataValue()).findFirst();
//		String aerialImagePath = pathOp.orElse("")+aerialImagePathOp.orElse("");
		
		DiagnoseGACModel diagnoseGACModel = new DiagnoseGACModel();
//		diagnoseGACModel.setRlAerialImagePath(aerialImagePath);
		diagnoseGACModel.setGeneralInformation(getGeneralInformation(taskId));
		
		List<Object> aerialPhotos = diagnoseDao.getAerialPhoto(taskId);
		if(aerialPhotos.size() > 0) {

			List<String> aerialPhotoTrimmed = Arrays.stream(aerialPhotos.stream()
												.map(Object::toString)
												.map(a->a.split(","))
												.collect(Collectors.toList())
												.get(0))
												.map(String::trim)
												.collect(Collectors.toList());

			List<String> aerialPhotoToSet = new ArrayList<>();
			for (String apt: aerialPhotoTrimmed) {
				if (!apt.contains(appProperties.getAerialPhotoPath())){
					aerialPhotoToSet.add(appProperties.getAerialPhotoPath() + apt);
				} else {
					aerialPhotoToSet.add(apt);
				}
			}
			diagnoseGACModel.setAriealPhotos(aerialPhotoToSet);
		} else {
			diagnoseGACModel.setAriealPhotos(new ArrayList<>());
		}
		
		diagnoseGACModel.setComments(getComments(taskId));
		return diagnoseGACModel;
	}
	
	@Override
	public DiagnoseModel getDiagnoseDetails(String taskId, int flag, int userId) {
		
		DiagnoseModel diagnoseModel = new DiagnoseModel();
//		List<StaticData> staticData = diagnoseDao.getStaticDataByKey(List.of("basePath", "stressImagePath", "healthImagePath"));
//		Optional<String> pathOp = staticData.stream().filter(s -> s.getDataKey().equals("basePath")).map(s -> s.getDataValue()).findFirst();
//		Optional<String> stressImagePathOp = staticData.stream().filter(s -> s.getDataKey().equals("stressImagePath")).map(s -> s.getDataValue()).findFirst();
//		Optional<String> healthImagePathOp = staticData.stream().filter(s -> s.getDataKey().equals("healthImagePath")).map(s -> s.getDataValue()).findFirst();
	 
//		String stressImagePath = pathOp.orElse("")+stressImagePathOp.orElse("");//diagnoseDao.getStaticData();
//		String healthImagePath = pathOp.orElse("")+healthImagePathOp.orElse("");
		
//		diagnoseModel.setRlStressImagePath(stressImagePath);
//		diagnoseModel.setRlHealthImagePath(healthImagePath);
		
//		diagnoseModel.setShapeFile("Not Available"); // getShapeFile(taskId) 
		diagnoseModel.setSpots(getSpotDetails(taskId, userId));
		Set<Stress> stress = new HashSet<Stress>();
		for(SpotModel spotModel : diagnoseModel.getSpots()) {
			for(Stress st :spotModel.getStresses()) {
				if(stress.size() == 0 || !stress.stream().filter(o -> o.getStressId() == st.getStressId()).findFirst().isPresent()) {
					stress.add(st);
				}	
			}
				
		}
		
		diagnoseModel.setMasterData(getMasterDetails(flag,stress, taskId));
		return diagnoseModel;
	}
	
	private String getShapeFile(String taskId) {
		List<Object> shapeFiles = diagnoseDao.getShapeFile(taskId);
		if(shapeFiles.size() > 0) {
			return shapeFiles.get(0).toString();
		}
		return null;
	}

	@Override
	public CaseDetailsModel getCaseDetails(String caseId, int flag) {
		CaseDetailsModel caseDetailsModel = new CaseDetailsModel();
		List<CaseDetails> caseResult = diagnoseDao.getCaseDetails(caseId);
		if(caseResult.size() > 0) {
			caseDetailsModel.setCaseDetails(caseResult.get(0));
		}
		caseDetailsModel.setMasterData(getMasterDetails(flag));
		return caseDetailsModel;
	}
	
	private List<SpotModel> getSpotDetails(String taskId, int userId) {
		
		 List<SpotModel> spots = new ArrayList<>();
		 List<TaskSpot> spotResult = diagnoseDao.getSpotId(taskId);
		 if( spotResult != null && spotResult.size() > 0) {
			 for ( int i=0; i<spotResult.size(); i++ ) {
				 String spotId = String.valueOf(spotResult.get(i).getId().toString());
				 Integer isSpotBenchmark = Integer.valueOf(spotResult.get(i).getIsBenchmark().toString());
				 SpotModel spot = new SpotModel();
				 spot.setUserId(userId);
				 spot.setName(spotId);
				 spot.setStresses(getStressDetails(spotId));
				 spot.setHealth(getHealthDetails(spotId));
				 spot.setIsSpotBenchmark(isSpotBenchmark == 0 ? false : true);
				 spots.add(spot);
			 }
		 } 
		 
		 return spots;
	}
	
	private List<Stress> getStressDetails(String spotId) {
		
		List<Stress> stressModel = new ArrayList<>();
//		List<StressCase> stressCases = diagnoseDao.getStressDetails(spotId);
//		if(stressCases.size() > 0) {
//			for(int i=0; i<stressCases.size(); i++ ) {
//				Stress stress = new Stress();
//				stress.setLeft(new ViewPosition());
//				stress.setRight(new ViewPosition());
//				stress.setFront(new ViewPosition());
//				StressCase stressCase = stressCases.get(i);
//
//				stress.setStressId(stressCase.getStressId());
//				stress.setId(stressCase.getId());
//				stress.setType(stressCase.getStressType());
//				stress.setStatus(stressCase.getStressStatus());
//				stress.setTaskSpotId(stressCase.getSpotId());
//
//				stress.setDesc(stressCase.getSymptomDesc());
//
//				stress.getLeft().setImage(stressCase.getLeftPhoto());
//				stress.getLeft().setIncidence(stressCase.getLeftIncident());
//				stress.getLeft().setSeverity(stressCase.getLeftSeverity());
//				stress.getLeft().setBenchmark(stressCase.getIsBenchmarkedLeft());
//
//				stress.getFront().setImage(stressCase.getFrontPhoto());
//				stress.getFront().setIncidence(stressCase.getFrontIncident());
//				stress.getFront().setSeverity(stressCase.getFrontSeverity());
//				stress.getFront().setBenchmark(stressCase.getIsBenchmarkedFront());
//
//				stress.getRight().setImage(stressCase.getRightPhoto());
//				stress.getRight().setIncidence(stressCase.getRightIncident());
//				stress.getRight().setSeverity(stressCase.getRightSeverity());
//				stress.getRight().setBenchmark(stressCase.getIsBenchmarkedRight());
//
//				stressModel.add(stress);
//
//			}
//		}


		List<StressCaseVO> fetchedStressCaseDetails = caseCropInfoRepository.getStressCaseDetailBySpotId(spotId);

		if (!fetchedStressCaseDetails.isEmpty()){

			Stress stress = new Stress();
			stress.setLeft(new ViewPosition());
			stress.setRight(new ViewPosition());
			stress.setFront(new ViewPosition());

			for (StressCaseVO stressCase : fetchedStressCaseDetails){

				stress.setStressId(stressCase.getStressId());
//				stress.setId(stressCase.getStressId());
				stress.setType(stressCase.getStressType());
				stress.setStatus(stressCase.getStressStatus());

				stress.setDesc(stressCase.getSymptomDesc());

				if (stress.getLeft().getImage() == null){
					stress.getLeft().setImage(stressCase.getLeftPhoto());
				}
				if (stressCase.getLeftIncident() != null) {
					stress.getLeft().setId(stressCase.getId());
				}

				if (stress.getLeft().getIncidence() == null){
					stress.getLeft().setIncidence(stressCase.getLeftIncident());
				}
				if (stress.getLeft().getSeverity() == null){
					stress.getLeft().setSeverity(stressCase.getLeftSeverity());
				}
				if (stress.getLeft().getBenchmark() == null){
					if (stressCase.getIsBenchmarkedLeft() == null || stressCase.getIsBenchmarkedLeft() == 0){
						stress.getLeft().setBenchmark(null);
					} else {
						stress.getLeft().setBenchmark(true);
					}
				}

				if(stress.getFront().getImage() == null ) {
					stress.getFront().setImage(stressCase.getFrontPhoto());
				}
				if (stressCase.getFrontIncident() != null) {
					stress.getFront().setId(stressCase.getId());
				}

				if (stress.getFront().getIncidence() == null) {
					stress.getFront().setIncidence(stressCase.getFrontIncident());
				}
				if (stress.getFront().getSeverity() == null) {
					stress.getFront().setSeverity(stressCase.getFrontSeverity());
				}
				if (stress.getFront().getBenchmark() == null){
					if (stressCase.getIsBenchmarkedFront() == null || stressCase.getIsBenchmarkedFront() == 0) {
						stress.getFront().setBenchmark(null);
					} else {
						stress.getFront().setBenchmark(true);
					}
				}

				if (stress.getRight().getImage() == null){
					stress.getRight().setImage(stressCase.getRightPhoto());
				}
				if (stressCase.getRightIncident() != null) {
					stress.getRight().setId(stressCase.getId());
				}

				if (stress.getRight().getIncidence() == null){
					stress.getRight().setIncidence(stressCase.getRightIncident());
				}
				if (stress.getRight().getSeverity() == null){
					stress.getRight().setSeverity(stressCase.getRightSeverity());
				}
				if (stress.getRight().getBenchmark() == null){
					if (stressCase.getIsBenchmarkedRight() == null || stressCase.getIsBenchmarkedRight() == 0) {
						stress.getRight().setBenchmark(null);
					} else {
						stress.getRight().setBenchmark(true);
					}
				}
			}
			stressModel.add(stress);
		}
		
		return stressModel;
	}
	
	private Health getHealthDetails(String spotId) {
		Health healthDetails = new Health();
		List<TaskSpotHealthImageVo> healthResult = taskSpotHealthImageRepository.getHealthDetails(spotId);
		if (healthResult.size() > 0) {
			TaskSpotHealthImageVo data = healthResult.get(0);
			healthDetails.setLeft(data.getLeftPhoto());
			healthDetails.setFront(data.getFrontPhoto());
			healthDetails.setRight(data.getRightPhoto());
		}
		List<Question> questions = getHealthQuestionnaire(spotId);
		if(questions != null && questions.size() > 0) {
			healthDetails.setQuestions(questions);
		} else {
			healthDetails.setQuestions(new ArrayList<Question>());
		}
		return healthDetails;
	}

	private List<Question> getHealthQuestionnaire(String spotId){
		
		List<Question> questionList = new ArrayList<>();
		List<Object> questionResult = diagnoseDao.getQuestionnaireDetails(spotId);
		if (questionResult.size() > 0) {
			for ( int i=0; i<questionResult.size(); i++ ) {
				Object[] data = (Object[]) questionResult.get(i);
				Question question = new Question();
				question.setFormControlName(data[0].toString());
				question.setName(data[1].toString());
				question.setType(data[2].toString().toLowerCase());
				question.setValues(Arrays.asList(data[3].toString()));
				question.setSelected(String.valueOf(data[4]));
				questionList.add(question);
			}
		}
		return questionList;
	}

	
	
	private MasterModel getMasterDetails(int flag) {
		if(flag == 1) {
			List<MasterStressModel> masterStressModels = new ArrayList<>();
			List<Object> stressDetails = masterDao.getMasterStressDetails();

			if(stressDetails.size() > 0) {
				for ( int i=0; i<stressDetails.size(); i++ ) {
					Object[] data = (Object[]) stressDetails.get(i);
					MasterStressModel masterStressModel = new MasterStressModel();
					masterStressModel.setStressId((int) data[0]);
					masterStressModel.setStressName(data[1].toString());
					masterStressModel.setStressTypeId((int)data[2]);
					masterStressModel.setStressTypeName(data[3].toString());
					masterStressModels.add(masterStressModel);
				}
			}
			List<StressSeverity> stressSeverity = masterDao.getMasterStressSeverity();
			
			List<StressSymptomsModel> stressSymptomsModels = new ArrayList<>();
			List<Object> stressIds = masterDao.getMasterStressId();
			List<Symptom> symptoms =  getStressId(stressIds);
			
			MasterModel masterModel = new MasterModel(masterStressModels,stressSeverity, symptoms);
			return masterModel;
			
		} else {
			return new MasterModel();
		}
	}
	
	private MasterModel getMasterDetails(int flag, Set<Stress> stress, String taskId) {
		if(flag == 1) {

			List<Integer> fetchedCommodityId = jdbcTemplate.queryForList("select v.commodity_id from task t inner join case_crop_info cci on cci.case_id = t.entity_id\n" +
										"inner join variety v on v.id = cci.variety_id\n" +
										"where t.id = "+taskId+"", Integer.class);
			Set<String> filteredStressType = stress.stream().map(Stress::getType).collect(Collectors.toSet());

			for(Stress st :stress) {
				if(stress.size() == 0 || !stress.stream().filter(o -> o.getType().equals(st.getType())).findFirst().isPresent()) {
					stress.add(st);
				}	
			}
			List<MasterStressModel> masterStressModels = new ArrayList<>();
			//List<Object> stressDetails = masterDao.getMasterStressDetails();

			List<Object> stressDetails = new ArrayList<Object>();
//					masterDao.getMasterStressDetails();
			if (filteredStressType.size()>0){
				String [] spFilteredStressType = filteredStressType.toString().replaceAll("(^\\[|\\]$)", "").split(",");
				StringBuilder sb = new StringBuilder();
				for (String s : spFilteredStressType){
					sb.append(s.strip().concat("', '"));
				}
				stressDetails.addAll(masterDao.getMasterStressDetailsByType(sb.substring(0, sb.toString().length()-4), fetchedCommodityId.size() > 0 ? fetchedCommodityId.toString().replaceAll("(^\\[|\\]$)", "") : null, filteredStressType));
			}

			if(stressDetails.size() > 0) {
				for ( int i=0; i<stressDetails.size(); i++ ) {
					Object[] data = (Object[]) stressDetails.get(i);
					MasterStressModel masterStressModel = new MasterStressModel();
					masterStressModel.setStressId((int) data[0]);
					masterStressModel.setStressName(data[1].toString());
					masterStressModel.setStressTypeId((int)data[2]);
					masterStressModel.setStressTypeName(data[3].toString());
					masterStressModels.add(masterStressModel);
				}
			} 
			List<StressSeverity> stressSeverity = masterDao.getMasterStressSeverity();
			
			List<Object> stressIds = new ArrayList<Object>();
			for(Stress st: stress) {
				stressIds.add(st.getStressId());
			}
			
			List<StressSymptomsModel> stressSymptomsModels = new ArrayList<>();
			List<Symptom> symptoms =  getStressId(stressIds.stream().distinct().collect(Collectors.toList()));

			return new MasterModel(masterStressModels,stressSeverity, symptoms);
			
		} else {
			return new MasterModel();
		}
	}

	private List<Symptom> getStressId(List<Object> stressIds) {
		List<Symptom> symptoms = new ArrayList<>();
		if(stressIds.size() > 0) {
			for(int i=0; i<stressIds.size(); i++) {
				List<StressSymptoms> stressSymptoms = masterDao.getMasterStressSymptoms((int)stressIds.get(i));
				for(StressSymptoms stressSymptom : stressSymptoms) {
					Symptom symptom = new Symptom();
					symptom.setSymptomsId(stressSymptom.getId());
					symptom.setSymptomsDesc(stressSymptom.getSymptomDesc());
					symptoms.add(symptom);
				}	
			}			
		}
		return symptoms;
	}
	
	public RecommendationModel getRecommendations(String taskId, Integer userId) {
		List<StressSeverity> stressSeveritys = diagnoseDao.getStressSeverity();
		List<UnitOfMeasurement> unitOfMeasurement = diagnoseDao.getUnitOfMeasurement();
		Map<Integer, String> unitOfMeasurements = new HashMap<>();
		for(UnitOfMeasurement data : unitOfMeasurement) {
			unitOfMeasurements.put(data.getId(), data.getName());
		}
				
		List<TaskSpot> spotResult = diagnoseDao.getSpotId(taskId); 
		List<String> spots = spotResult.stream().map(TaskSpot::getId).collect(Collectors.toList());
//		List<Object[]> stressDetails =  diagnoseDao.getRecommendationStressDetails(spots);
		List<String> taskStressSpotSymptomImages = caseCropInfoRepository.taskStressSpotSymptomImages(spots.size() == 0? null : spots);
		List<StressCaseVO> stressDetails = caseCropInfoRepository.getRecommendation(taskStressSpotSymptomImages.size() == 0 ? null : taskStressSpotSymptomImages);
		CaseCropInfo sowingWeekAndAczId = diagnoseDao.getSowingIdAndAczId(taskId);

		int commodityId = 0;
		List<Commodity> commoditys = diagnoseDao.getCommodity(taskId);
		if(commoditys.size() > 0) {
			Commodity commodity = commoditys.get(0);
			commodityId = commodity.getId();
		}
		
		Map<String, Double> stressSeveritySum = new HashMap<>();
		Map<String, Integer> stressSeverityCount = new HashMap<>();
		Map<String, Integer> stressSeverityDetails = new HashMap<>();
		
		if(stressDetails.size() > 0) {
			for(int i = 0; i < stressDetails.size(); i++) {
				StressCaseVO stressDetail = stressDetails.get(i);
				String stressName = stressDetail.getStressName();
				double sum = 0.0;
				for(StressSeverity stressSeverity :stressSeveritys) {
					if (stressDetail.getLeftSeverity() != null && stressSeverity.getId() != null){
						if (stressSeverity.getId() == (int) stressDetail.getLeftSeverity()) {
							sum += stressSeverity.getValue();
						}
					}
					if (stressSeverity.getId() != null && stressDetail.getFrontSeverity() != null){
						if (stressSeverity.getId() == (int) stressDetail.getFrontSeverity()) {
							sum += stressSeverity.getValue();
						}
					}
					if (stressSeverity.getId() != null && stressDetail.getRightSeverity() != null){
						if (stressSeverity.getId() == (int) stressDetail.getRightSeverity()) {
							sum += stressSeverity.getValue();
						}
					}
				}
				
				Double stressSeverityAvg = sum / 3.0;
								
				if(stressSeveritySum.containsKey(stressName)) {
					stressSeveritySum.put(stressName, stressSeveritySum.get(stressName) + stressSeverityAvg);
					stressSeverityCount.put(stressName, stressSeverityCount.get(stressName) + 1);
				} else {
					stressSeveritySum.put(stressName, stressSeverityAvg);
					stressSeverityCount.put(stressName, 1);
					stressSeverityDetails.put(stressName, (int) stressDetail.getStressId());
				}
			}
		}
		
		double allSeveritySum = 0.0;
		int allSeverityCount = 0;
		boolean fountOtherStress = false;
		List<RecommendationStress> recommendationStresses = new ArrayList<>();
		for(Map.Entry<String, Double> entry : stressSeveritySum.entrySet()) {
			
			double sumAvg = entry.getValue()/stressSeverityCount.get(entry.getKey());
			double ceilOutput = Math.ceil(sumAvg);
			int stressSeverityId = 0;
			List<Integer> stressSeverityControlMeasureId = new ArrayList<Integer>();
			int stressId = stressSeverityDetails.get(entry.getKey());
			String stressName = entry.getKey().trim();
			RecommendationStress recommendationStress = new RecommendationStress();
			List<Recommendation> recommendations = new ArrayList<>();
			if (!stressName.toLowerCase().equals("others")) {
				allSeveritySum += sumAvg;
				allSeverityCount++;

				for (StressSeverity stressSeverity : stressSeveritys) {
					if (stressSeverity.getValue() == ceilOutput) {
						stressSeverityId = stressSeverity.getId();
						break;
					}
				}
				if (stressSeverityId > 1) {
					List<StressSeverityControlMeasures> stressSeverityControlMeasures = diagnoseDao
							.getStressSeverityControlMeasures(stressId, stressSeverityId);
					if (stressSeverityControlMeasures.size() > 0) {
						for (StressSeverityControlMeasures severityControlMeasures : stressSeverityControlMeasures) {
							stressSeverityControlMeasureId.add(severityControlMeasures.getControlMeasureId());
						}
					}

/*
 					List<StressRecommendation> stressRecommendations = diagnoseDao.getStressRecommendation(commodityId,
							stressId, stressSeverityControlMeasureId);
					if (stressRecommendations.size() > 0) {
						for (int i = 0; i < stressRecommendations.size(); i++) {

							Recommendation recommendation = new Recommendation();
							StringBuffer instruction = new StringBuffer();
							StressRecommendation stressRecommendation = stressRecommendations.get(i);

							if(stressRecommendation.getInstruction() != null) {
								instruction.append(stressRecommendation.getInstruction() + ", ");
							}

							if(stressRecommendation.getAgrochemicalId() != null) {
								List<Object> agrochemicalName = diagnoseDao
										.getAgrochemical(stressRecommendation.getAgrochemicalId());
								if (agrochemicalName.size() > 0) {
									instruction.append(agrochemicalName.get(0).toString() + ", ");
								}
							}

							if(stressRecommendation.getAgrochemicalApplicationId() != null) {
								List<Object> fertilizerApplicationMethodName = diagnoseDao
										.getAgrochemicalApplication(stressRecommendation.getAgrochemicalApplicationId());
								if (fertilizerApplicationMethodName.size() > 0) {
									instruction.append(fertilizerApplicationMethodName.get(0).toString() + ", ");
								}
							}

							if (stressRecommendation.getDose() != null && stressRecommendation.getDoseUomId() != null) {
								instruction.append(stressRecommendation.getDose() + " "
										+ unitOfMeasurements.get(stressRecommendation.getDoseUomId()) + ", ");
							}

							if (stressRecommendation.getWater() != null
									&& stressRecommendation.getWaterUomId() != null) {
								instruction.append(stressRecommendation.getWater() + " "
										+ unitOfMeasurements.get(stressRecommendation.getWaterUomId()));
							}
							recommendation.setId(stressRecommendation.getId());
							recommendation.setInstruction(!instruction.toString().isEmpty() ? instruction.toString().substring(0, instruction.toString().trim().length()-1) : null);
							recommendations.add(recommendation);

						}
					}
*/
					try {
						List<RecommendationVO> fetchedRecommendation = recommendationRepository.getRecommendation(commodityId, stressId, stressSeverityControlMeasureId,
								sowingWeekAndAczId.getAczId(), sowingWeekAndAczId.getCorrectedSowingWeek());

						for (RecommendationVO recommendationStr : fetchedRecommendation) {
							Recommendation rec = new Recommendation();
							rec.setId(recommendationStr.getRecommendationId());
							rec.setInstruction(recommendationStr.getRecommendation());

							recommendations.add(rec);
						}
					}catch (DataAccessException e){
						e.printStackTrace();
					}
				}
			} else {
				fountOtherStress = true;
			}
			recommendationStress.setId(stressId);
			recommendationStress.setName(stressName);
			recommendationStress.setSeverity(sumAvg);

			List<Recommendation> uniqueRecommendation = recommendations.stream()
					.collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparingInt(Recommendation::getId))),
							ArrayList::new));

			recommendationStress.setRecommendations(uniqueRecommendation);
			
			List<Object> selectedRecommendations = diagnoseDao.getSelectedRecommendations(taskId,stressId);
			if(selectedRecommendations.size() > 0) {
				List<Integer> selectedRecommendationsInInt = new ArrayList<Integer>();
				selectedRecommendations.forEach(item ->{
					selectedRecommendationsInInt.add(Integer.valueOf(item.toString()));
				});
				 
				recommendationStress.setSelectedRecommendations(selectedRecommendationsInInt);
			} else {
				recommendationStress.setSelectedRecommendations(new ArrayList<>());
			}
			
			recommendationStresses.add(recommendationStress);
		}
		
		double allSumAvg = allSeveritySum / allSeverityCount;
		double ceilOutput = Math.ceil(allSumAvg);
		String stressSeverityName = "";
		RecommendationModel recommendationModel = new RecommendationModel();
		for(StressSeverity stressSeverity : stressSeveritys) {
			if(stressSeverity.getValue() == ceilOutput ) {
				stressSeverityName = stressSeverity.getName();
				break;
			}
		}
		recommendationModel.setUserId(userId);
		recommendationModel.setTaskId(taskId);
		recommendationModel.setSeverityValue(allSumAvg);
		recommendationModel.setSeverityType(stressSeverityName);
		recommendationModel.setStresses(recommendationStresses);
		List<StaticData> staticData =diagnoseDao.getStaticDataByKey(List.of("severityResultCutoff"));
		String severityResultCutoff = staticData.stream().map(s -> s.getDataValue()).findFirst()
				.orElseThrow(() -> new RuntimeException("severityResultCutoff not found"));
		String label = "";
		
		if(fountOtherStress) {
			label = "Bad Sample";
		} else if(allSumAvg > Double.valueOf(severityResultCutoff)) {
			label = "Bad Sample";
		} else {
			label = "Good Sample";
		}
		
		recommendationModel.setLabel(label);
		return recommendationModel;
	}

	private List<Comment> getComments(String taskId) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		List<Object[]> comments = diagnoseDao.getComments(taskId);
		List<Comment> commentModel = new ArrayList<>();
		if(comments.size() > 0) {
			for(int i=0; i<comments.size(); i++) {
				Object[] data = comments.get(i);
				Comment comment = new Comment();
				
				comment.setId(data[0].toString());
				comment.setDateTime((data[1] != null && data[2] != null ) ? sdf.format(data[1])+" "+ data[2].toString() : null);
				comment.setComment(data[3].toString());
				comment.setName(data[4].toString()+" "+data[5].toString());
				comment.setDesignation(data[6].toString());
				commentModel.add(comment);
			}
			 
		}
		return commentModel;
	}
	
	private GeneralInformation getGeneralInformation(String taskId){
		
		GeneralInformation generalInformation = new GeneralInformation();
		List<ViewGeneralInformation> generalInformations = diagnoseDao.getGeneralInformation(taskId);
		if(generalInformations.size() > 0) {
			ViewGeneralInformation data = generalInformations.get(0);
			 
			generalInformation.setFarmerName(data.getFarmerName());
			generalInformation.setFarmerMobileNumber(data.getPrimaryMobNumber());
			generalInformation.setVillageName(data.getVillageName());
			generalInformation.setTehsil(data.getTehsilName() == null ? "" : data.getTehsilName());
			generalInformation.setDistict(data.getDistrictName() == null ? "" : data.getDistrictName());
			generalInformation.setState(data.getStateName() == null ? "" :data.getStateName());
		}
		return generalInformation;
	}
	
	public IrrigationDetail getIrrigationDetail(String taskId) {
		IrrigationDetail irrigationDetail = new IrrigationDetail();
		List<Object[]> caseFieldDetails = diagnoseDao.getCaseFieldDetails(taskId);
		if(caseFieldDetails.size() > 0) {
			Object[] data = caseFieldDetails.get(0);
			
			List<Object> irrigationSource = diagnoseDao.getIrrigationSource(data[0].toString().isEmpty()?null:data[0].toString());
			if(irrigationSource.size()> 0) {
				List<String> irrigationSourceName = irrigationSource.stream()
														.map(Object::toString)
														.collect(Collectors.toList());
				irrigationDetail.setIrrigationSource(irrigationSourceName);
			}
			
			List<Object> irrigationMethods = diagnoseDao.getIrrigationMethod(data[1].toString().isEmpty()?null:data[1].toString());
			if(irrigationMethods.size()> 0) {
				List<String> irrigationMethodsName = irrigationMethods.stream()
														.map(Object::toString)
														.collect(Collectors.toList());
				irrigationDetail.setIrrigationMethod(irrigationMethodsName);
			}
			
			if ((int)data[2] != 0) {
				irrigationDetail.setNumberOfIrrigation((int) data[2]);
			}
			if ((int)data[3] != 0 && (int)data[4] != 0) {
				irrigationDetail.setWeekOfIrrigationl(getWeekRangeBasedOnWeekNumberYear((int)data[3], (int)data[4]));
			}
		} else {
			irrigationDetail.setIrrigationSource(new ArrayList<>());
			irrigationDetail.setIrrigationMethod(new ArrayList<>());
		}
		
		return irrigationDetail;
	}
	
	public Fertilizer getFertilizer(String taskId) {
		Fertilizer fertilizer = new Fertilizer();
		List<Object[]> fertilizers =  diagnoseDao.getFertilizer(taskId);
		if(fertilizers.size() > 0) {
			Object[] data = fertilizers.get(0);
			fertilizer.setApplicationType(data[0].toString());
			fertilizer.setFertilizerName(data[1].toString());
			fertilizer.setFertilizerUom(data[2].toString());
			fertilizer.setFertilizerDose((int)data[3]);
			fertilizer.setSplitDose(data[4].toString());
			fertilizer.setApplicationWeek(getWeekRangeBasedOnWeekNumberYear((int)data[5], (int)data[6]));
		}
		return fertilizer;
	}
	
	public CropInformation getCropInformation(String taskId) {
		CropInformation cropInformation = new CropInformation();
		List<Object[]> cropInformations = diagnoseDao.getCropInformation(taskId);
		if(cropInformations.size() > 0) {
			Object[] data = cropInformations.get(0);
			cropInformation.setCommodityName(data[0].toString());
			cropInformation.setVarietyName(data[1].toString());
			cropInformation.setCropArea((float)data[2]);
//			cropInformation.setSeasonName(data[3].toString());
			cropInformation.setSowingWeek(getWeekRangeBasedOnWeekNumberYear((int) data[3],(int)data[4]));
			cropInformation.setSowingYear((int)data[4]);
			cropInformation.setSeedSourceName(data[5].toString());
			cropInformation.setSeedsSampleReceived((int)data[6]);
			cropInformation.setSeedsRates((float)data[7]);
			cropInformation.setSpacingRowToRow((float)data[8]);
			cropInformation.setSpacingPlantToPlant((float)data[9]);
			cropInformation.setCropTypeName(data[10].toString());
			if (data[11] != null){
				cropInformation.setSellerGivenQtyTon((Float) data[11]);
			}

		}
		return cropInformation;
	}
	
	public SeedTreatment getSeedTreatment(String taskId) {
		SeedTreatment seedTreatment = new SeedTreatment();
		List<Object[]> seedTreatments = diagnoseDao.getSeedTreatment(taskId);
		if(seedTreatments.size() > 0) {
			Object[] data = seedTreatments.get(0);
			
			seedTreatment.setSeedTreatment(data[0].toString());
			
			List<Object> treatmentAgents = diagnoseDao.getTreatmentAgent(data[1].toString());
			if(treatmentAgents.size()> 0) {
				List<String> treatmentAgentName = treatmentAgents.stream()
														.map(Object::toString)
														.collect(Collectors.toList());
				seedTreatment.setSeedTreatmentAgent(treatmentAgentName);
			}
			
//			List<Object> pesticides = diagnoseDao.getPesticides(Integer.parseInt(data[2].toString()));
//			
//			if(pesticides.size()> 0) {
////				List<String> pesticideName = pesticides.stream()
////														.map(Object::toString)
////														.collect(Collectors.toList());
//				seedTreatment.setInsecticide(pesticides.get(0).toString());
//			}
			
			seedTreatment.setSeedTreatmentUom(data[3] != null ? String.valueOf(data[3]) : "");
			if ((int)data[4] != 0) {
				seedTreatment.setSeedTreatmentAgentDose(data[4].toString());
			}

		} else {
			seedTreatment.setSeedTreatmentAgent(new ArrayList<>());
//			seedTreatment.setInsecticide(new ArrayList<>());
		}
		return seedTreatment;
	}
	
	@Override
	public RemedialMeasure getRemedialMeasures(String taskId){
		
		RemedialMeasure remedialMeasure = new RemedialMeasure();
		List<Object[]> remedialMeasures = diagnoseDao.getRemedialMeasures(taskId);
		if(remedialMeasures.size() > 0) {
			
			Object[] data = remedialMeasures.get(0);
			remedialMeasure.setAgrochemicalName(data[0].toString());
			remedialMeasure.setRemedialBrand(data[1].toString());
			remedialMeasure.setRemedialUom(data[2].toString());
			remedialMeasure.setRemedialDose((int) data[3]);
			remedialMeasure.setRemedialWeek(getWeekRangeBasedOnWeekNumberYear((int) data[4], (int) data[5]));
		}
		return remedialMeasure;
	}

	@Override
	public ResponseMessage saveSpotDetails(SpotModel spot) {
				
		diagnoseDao.updateSpotDetails(spot);
		ResponseMessage responseMessage = new ResponseMessage();
		return responseMessage;
	}
	
	@Override
	public ResponseMessage saveDiagnoseDetails(RecommendationModel recommendationModel) {
		
		ResponseMessage responseMessage = new ResponseMessage();
		List<Task> tasks = diagnoseDao.getTask(recommendationModel.getTaskId());
		if(tasks.size() > 0) {
			Task task = tasks.get(0);
			TaskHistory taskHistory = makeTaskHistory(task);
			diagnoseDao.submitDiagnoseDetails(recommendationModel,taskHistory, task);
		} else {
			
		}
	
		return responseMessage;
	}
	
	private TaskHistory makeTaskHistory(Task oldTask) {
		TaskHistory taskHistory = new TaskHistory();
		taskHistory.setTaskId(oldTask.getId());
		taskHistory.setTaskDate(oldTask.getTaskDate());
		taskHistory.setStartTime(oldTask.getTaskTime());
		taskHistory.setEndTime(new Time(System.currentTimeMillis())); 
		taskHistory.setTaskTypeId(oldTask.getTaskTypeId());
		taskHistory.setAssigneeId(oldTask.getAssigneeId());
		taskHistory.setStatus(oldTask.getStatus());
		taskHistory.setEntityTypeId(oldTask.getEntityTypeId());
		taskHistory.setEntityId(oldTask.getEntityId());
		return taskHistory;
	}
	
	
	@Override
	public ResponseMessage rlmApprove(RecommendationModel recommendationModel) {
		
		List<Task> tasks = diagnoseDao.getTask(recommendationModel.getTaskId());
		if(tasks.size() > 0) {
			Task task = tasks.get(0);
			TaskHistory taskHistory = makeTaskHistory(task);
			
			List<FarmCase> farmCases = diagnoseDao.getFarmCaseById(task.getEntityId());
			
			List<StaticData> staticData = diagnoseDao.getStaticDataByKey(List.of("subtractDaysFromNmd"));
			Optional<String> pathOp = staticData.stream().filter(s -> s.getDataKey().equals("subtractDaysFromNmd")).map(s -> s.getDataValue()).findFirst();
			int subtractDays = Integer.parseInt(pathOp.orElse(""));
			
			if(farmCases.size() > 0) {
				Calendar cal = Calendar.getInstance();
				cal.setTimeInMillis(farmCases.get(0).getNmd().getTime());
				cal.add(Calendar.DATE, subtractDays);
				diagnoseDao.rlmApprove(recommendationModel,taskHistory, task, cal);
			}
		}
		
		ResponseMessage responseMessage = new ResponseMessage();
		return responseMessage;
	}
	
	@Override
	public ResponseMessage rlmReassign(ReassignModel reassignModel) {
		List<Task> tasks = diagnoseDao.getTask(reassignModel.getTaskId());
		if(tasks.size() > 0) {
			Task task = tasks.get(0);
			TaskHistory taskHistory = makeTaskHistory(task);
			diagnoseDao.rlmReassign(taskHistory, task, reassignModel);
		}
		ResponseMessage responseMessage = new ResponseMessage();
		return responseMessage;
	}
	
	@Override
	public List<RltUserModel> getRlt(int rlmId){
		List<Object[]> rltUsers = diagnoseDao.getRlt(rlmId);
		List<RltUserModel> rltUserModels = new ArrayList<RltUserModel>();
		if(rltUsers.size() > 0) {
			for(int i=0; i < rltUsers.size() ; i++) {
				Object data[] = rltUsers.get(i);
				RltUserModel rltUserModel = new RltUserModel();
				rltUserModel.setRltId((int) data[0]);
				String name ="";
				name = data[1].toString();
				name = name +(data[2] !=null && data[2] !=""?" "+data[2].toString():"");
				name = name +" "+data[3].toString();
				
				rltUserModel.setRltName(name);
				rltUserModels.add(rltUserModel);
			}
		}
		return rltUserModels;		
	}
	
	private String getWeekRangeBasedOnWeekNumberYear(Integer week, Integer year) {
		Integer calculatedWeek = checkLastWeekOfYear(week,year);
		Calendar c = Calendar.getInstance();
	    c.set(Calendar.YEAR, year);
	    c.set(Calendar.WEEK_OF_YEAR, calculatedWeek+1);
	    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
	    c.add(Calendar.DATE, -i - 7);
	    c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
	    Date start = c.getTime();
	    c.add(Calendar.DATE, 6);
	    Date end = c.getTime();
	    SimpleDateFormat format = new SimpleDateFormat("dd MMM");
	    return "Week "+week+"-"+year +" ("+format.format(start) + "-" + format.format(end)+")";
	}
	
	private int checkLastWeekOfYear(Integer week, Integer year) {
		Calendar c = Calendar.getInstance();
		c.set(year-1,Calendar.DECEMBER,31);
		
		Calendar c1 = Calendar.getInstance();
		c1.set(year,Calendar.JANUARY,1);
		
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
		int dayOfWeek1 = c1.get(Calendar.DAY_OF_WEEK);
		
		if(dayOfWeek > 3 && dayOfWeek1 > 3 && dayOfWeek1 <= 7) {
			return week+1;
		}
		return week;
	}

	@Override
	public List<SymptomsModel> getSymptoms(Integer stressId) {
		List<SymptomsModel> symptoms = new ArrayList<SymptomsModel>();
		List<StressSymptoms> stressSymptomsList = diagnoseDao.getStressSymptoms(stressId);
		if(stressSymptomsList.size() > 0) {
			for(StressSymptoms stressSymptoms : stressSymptomsList ) {
				SymptomsModel symptomsModel = new SymptomsModel();
				symptomsModel.setSymptomsId(stressSymptoms.getId());
				symptomsModel.setSymptomsDesc(stressSymptoms.getSymptomDesc());
				symptoms.add(symptomsModel);
			}
		} 
		return symptoms;
	}

	@Override
	public List<QualityParameterRangeModel> getQualityParameter(String taskId) {
		List<QualityParameterRangeModel> prameterRangeModels = new ArrayList<QualityParameterRangeModel>();

		List<Object> list = diagnoseDao.getQualityParameter(taskId);
		if(list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				Object[] obj = (Object[]) list.get(i);
				QualityParameterRangeModel model = new QualityParameterRangeModel();
				model.setParameterId((Integer) obj[0]);
				model.setCommodityId((Integer)obj[4]);
				model.setName(obj[3].toString());
				model.setMaxValue(obj[1].toString());
				model.setMinValue(obj[2].toString());
//			model.setValue("");
				prameterRangeModels.add(model);
			}

		}
		return prameterRangeModels;
	}

	@Override
	public ResponseEntity<?> getBand(QualityParameterRangeModel qualityParameterRangeModels) {

		String url = appProperties.getDrkUrl() + validateBandUrl + "?apiKey=" + appProperties.getDrkApiKey();

		ResponseEntity<?> apiResponse = restTemplate.postForEntity(url, qualityParameterRangeModels, String.class);

		System.out.println("api response " + apiResponse);
		return apiResponse;
	}

	@Override
	public CaseCropInfoVo getFarmerCropInfo(String taskId) {
		return caseCropInfoRepository.findCommodityByTaskId(taskId);
	}

//	@Override
//	public Integer getBand(List<QualityParameterRangeModel> qualityParameterRangeModels) {
//		List<StaticData> staticData = diagnoseDao.getStaticDataByKey(List.of("drkPortalBasePath"));
//		Optional<String> drkPortalBasePath = staticData.stream().filter(s -> s.getDataKey().equals("drkPortalBasePath")).map(StaticData::getDataValue).findFirst();
//
//
//		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(drkPortalBasePath + "/validate-band")
//				.queryParam("unixTimestamp", lastSyncTime).queryParam("apiKey", masterDataKey)
//				.queryParam("page", pageNo);
//
//		ResponseEntity<String> apiResponse = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, null,
//				String.class);
//
//		return null;
//	}
}
