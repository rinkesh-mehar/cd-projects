package com.drkrishi.rlt.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.drkrishi.rlt.entity.*;
import com.drkrishi.rlt.repository.SubtaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.drkrishi.rlt.model.Question;
import com.drkrishi.rlt.model.ReassignModel;
import com.drkrishi.rlt.model.RecommendationModel;
import com.drkrishi.rlt.model.RecommendationStress;
import com.drkrishi.rlt.model.SpotModel;
import com.drkrishi.rlt.model.Stress;
@Repository
public class DiagnoseDaoImpl implements DiagnoseDao {

	@PersistenceContext
	EntityManager em;

	@Autowired
	SubtaskRepository subtaskRepository;

	@SuppressWarnings("unchecked")
	@Override
	public List<CaseDetails> getCaseDetails(String caseId) {
		String hqlQuery = "from CaseDetails as cd where cd.caseId = :caseId";
		Query query = em.createQuery(hqlQuery,CaseDetails.class).setParameter("caseId", caseId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAerialPhoto(String taskId) {
		Query query = em.createQuery("SELECT tap.photos FROM TaskAerialPhoto as tap inner join Task as t on" +
				  " tap.taskId = t.id WHERE t.id = :taskId")
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getShapeFile(String taskId){
		Query query = em.createQuery("SELECT ck.kmlFilePath FROM CaseKml as ck inner join Task as t on" +
				  " ck.farmCaseId = t.entityId WHERE t.id = :taskId")
				.setParameter("taskId", taskId);
		return query.getResultList();
	}

	public CaseCropInfo getSowingIdAndAczId(String taskId) {
		Query query = em.createQuery("SELECT cci FROM Task t INNER JOIN CaseCropInfo cci ON t.entityId = cci.caseId" +
				" WHERE t.id = :taskId", CaseCropInfo.class)
				.setParameter("taskId", taskId);
		return (CaseCropInfo) query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TaskSpot> getSpotId(String taskId){
		String hqlQuery = "select ts from Task as t inner join TaskSpot as ts on t.id = ts.taskId"
				+ " where t.id = :taskId";
		Query query = em.createQuery(hqlQuery, TaskSpot.class).setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StressCase> getStressDetails(String spotId){
		Query query = em.createQuery("from StressCase where spotId = :spotId ", StressCase.class)
				.setParameter("spotId", spotId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StressSymptoms> getStressSymptoms(Integer stressId){
		Query query = em.createQuery("from StressSymptoms where stressId = :stressId", StressSymptoms.class)
				.setParameter("stressId", stressId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getQuestionnaireDetails(String spotId){
		String hqlQuery = "select thd.id, hq.name, hq.inputType, hq.inputValues, thd.selectedAnswer"
				+ " from TaskHealthDetails as thd inner join HealthQuestionnaire as hq on thd.questionId = hq.id"
				+ " where thd.spotId = :spotId";
		Query query = em.createQuery(hqlQuery).setParameter("spotId", spotId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getRecommendationStressDetails(List<String> spots){
		
		String hqlQuery = "select stressName, leftSeverity, frontSeverity, rightSeverity, stressId from StressCase"
				+ " where spotId in ( :spots ) ";
		Query query = em.createQuery(hqlQuery).setParameter("spots", spots.size() > 0 ? spots : null);
		return query.getResultList();		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StressSeverity> getStressSeverity() {
		Query query = em.createQuery("from StressSeverity", StressSeverity.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StressSeverityControlMeasures> getStressSeverityControlMeasures(int stressId, int severityId) {
		Query query = em.createQuery("from StressSeverityControlMeasures where stressId = :stressId and severityId = :severityId", StressSeverityControlMeasures.class)
				.setParameter("stressId", stressId)
				.setParameter("severityId", severityId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Commodity> getCommodity(String taskId){
		String hqlQuery = "select c from Task as t"+ 
				" inner join CaseCropInfo as cci on t.entityId = cci.caseId" + 
	    		" inner join Variety as v on cci.varietyId = v.id" + 
	    		" inner join Commodity as c on v.commodityId = c.id" +
	    		" where t.id = :taskId";
		Query query = em.createQuery(hqlQuery, Commodity.class)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StressRecommendation> getStressRecommendation(int commodityId, int stressId, List<Integer> controlMeasureId){
		
		String hqlQuery = " from StressRecommendation"
	    		+" where commodityId = :commodityId and stressId = :stressId and controlMeasureId in (:controlMeasureId)";
		Query query = em.createQuery(hqlQuery , StressRecommendation.class)
				.setParameter("commodityId", commodityId)
				.setParameter("stressId", stressId)
				.setParameter("controlMeasureId", controlMeasureId.size()>0? controlMeasureId:null);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getComments(String taskId){
		
//		String hqlQuery = " select th.id, th.taskDate, th.endTime, th.comment, u.firstName, u.lastName,"
//				+ " r.name, u.middleName from TaskHistory as th "
//				+ " inner join Users as u on th.assigneeId = u.id"
//				+ " inner join UserRoles as ur on ur.userId = u.id"
//				+ " inner join Roles as r on ur.roleId = r.id"
//				+ " where th.taskId = :taskId";
				
		String hqlQuery = "select commentId, commentDate, commentTime, comment, firstName, lastName, roleName, middleName"
				+ " from ViewComment where status = 2 and taskId = :taskId and (taskTypeId = 6 or taskTypeId = 8) order by concat(commentDate, commentTime) desc";
		
		Query query = em.createQuery(hqlQuery)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<UnitOfMeasurement> getUnitOfMeasurement(){
		Query query = em.createQuery("from UnitOfMeasurement", UnitOfMeasurement.class);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAgrochemical(int id){
		Query query = em.createQuery("select name from Agrochemical where id = :id")
				.setParameter("id", id); 
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getAgrochemicalApplication(int id){
		Query query = em.createQuery("select name from FertilizerApplicationMethod where id = :id")
				.setParameter("id", id);
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ViewGeneralInformation> getGeneralInformation(String taskId) {

		String hqlQuery = " from ViewGeneralInformation where taskId = :taskId";
		Query query = em.createQuery(hqlQuery , ViewGeneralInformation.class)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCaseFieldDetails(String taskId){
		String hqlQuery = "select csd.irrigationSourceId, csd.irrigationMethodId, csd.numberOfIrrigation, csd.weekOfIrrigation, csd.yearOfIrrigation"
				+ " from Task as t "
				+ " inner join CaseFieldDetails as csd on t.entityId = csd.caseId"
				+ " where t.id = :taskId"
				+ " ";
		Query query = em.createQuery(hqlQuery)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getIrrigationMethod(String id){
		String hqlQuery = "select name from IrrigationMethod where id in ("+id+")";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getIrrigationSource(String id){
		String hqlQuery = "select name from IrrigationSource where id in ("+id+")";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getFertilizer(String taskId){
//		String hqlQuery = "select fam.name, f.name, uom.name, cfd.fertilizerDose, cfd.fertilizerSplitDose, cfd.fertilizerWeekOfApplcation"
//				+ " from Task as t "
//				+ " inner join CaseFieldDetails as cfd on t.caseId = cfd.caseId"
//				+ " inner join Fertilizer as f on cfd.fertilizerId = f.id"
//				+ " inner join FertilizerApplicationMethod as fam on cfd.applicationId = fam.id"
//				+ " inner join UnitOfMeasurement as uom on cfd.fertilizerUomId = uom.id"
//				+ " where t.id = :taskId"
//				+ " ";
		
		String hqlQuery = "select fertilizerApplicationMethodName, fertilizerName, unitOfMeasurementName, fertilizerDose, "
				+ " fertilizerSplitDose, fertilizerWeekOfApplcation, fertilizerYearOfApplcation"
				+ " from ViewFertilizer where taskId = :taskId";
		Query query = em.createQuery(hqlQuery)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getCropInformation(String taskId) {
//		String hqlQuery = "select c.name AS commodity_name, v.name AS variety_name, cci.cropArea AS crop_area, se.name AS seasonName,"
//				+ " cci.sowingWeek AS sowing_week, cci.sowingYear AS sowing_year, ss.name AS seed_source_name,"
//				+ " cci.seedsSampleReceived AS seeds_sample_received, cci.seedsRates AS seeds_rates," 
//				+ " uom.name , cci.spacingRow as spacingRow, cci.spacingPlant as spacingPlant"
//				+ " from Task as t "
//				+ " JOIN CaseCropInfo as cci ON t.caseId = cci.caseId"
//				+ " JOIN Season as se ON cci.seasonId = se.id"
//				+ " JOIN UnitOfMeasurement as uom ON cci.uomId = uom.id"
//				+ " JOIN SeedSource as ss ON cci.seedSourceId = ss.id"
//				+ " JOIN Variety as v ON cci.varietyId = v.id"
//				+ " JOIN Commodity as c ON v.commodityId = c.id"
//				+ " JOIN Stress as s ON s.commodityId = c.id"
//				+ " where t.id = :taskId";
				
		
		String hqlQuery = "select commodityName, varietyName, cropArea, sowingWeek, sowingYear, seedSourceName,"
				+ " seedsSampleReceived, seedsRates, spacingRow, spacingPlant, cropTypeName, sellerGivenQtyTon"
				+ " from ViewCropInformation where taskId = :taskId";
		
		Query query = em.createQuery(hqlQuery)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getSeedTreatment(String taskId){
		String hqlQuery = "select cfd.seedTreatment, cfd.seedTreatmentAgentId, cfd.agrochemicalBrandId, uom.name, cfd.seedDose"
				+ " from Task as t "
				+ " left join CaseFieldDetails as cfd on t.entityId = cfd.caseId"
				+ " left join UnitOfMeasurement as uom on cfd.seedUomId = uom.id"
				+ " where t.id = :taskId";
		Query query = em.createQuery(hqlQuery)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getTreatmentAgent(String ids){
		String hqlQuery = "select name from SeedTreatmentAgent where id in ("+ids+")";
		Query query = em.createQuery(hqlQuery);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getPesticides(Integer id){
		String hqlQuery = "select a.name from Agrochemical as a inner join AgrochemicalBrand as ab "
				+ " on ab.agrochemicalId = a.id where ab.id = :id";
		Query query = em.createQuery(hqlQuery).setParameter("id", id);
		return query.getResultList();
	}
	
//	@Override
//	public StaticData getStaticData(){
//		String hqlQuery = "from StaticData where id = 1";
//		Query query = em.createQuery(hqlQuery,StaticData.class);
//		return (StaticData) query.getResultList().get(0);
//	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<StaticData> getStaticDataByKey(List<String> keys) {
			String hqlQuery = "from StaticData where dataKey in (:dataKey)";
			Query query = em.createQuery(hqlQuery,StaticData.class);
			query.setParameter("dataKey", keys);
			return  query.getResultList();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getRemedialMeasures(String taskId){
//		String hqlQuery = "select a.name, ab.name, uom.name, cfd.agrochemicalDose, cfd.agrochemicalWeekOfApplcation"
//				+ " from Task as t "
//				+ " inner join CaseFieldDetails as cfd on t.caseId = cfd.caseId"
//				+ " inner join Agrochemical as a on cfd.agrochemicalId = a.id"
//				+ " inner join AgrochemicalBrand as ab on cfd.agrochemicalBrandId = ab.id"
//				+ " inner join UnitOfMeasurement as uom on cfd.agrochemicalUomId = uom.id" 
//				+ " where t.id = :taskId";
		
		String hqlQuery = "select agrochemicalName, agrochemicalBrandName, unitOfMeasurementName, agrochemicalDose, "
				+ " agrochemicalWeekOfApplcation, agrochemicalYearOfApplcation"
				+ " from ViewRemedialMeasures where taskId = :taskId";
		Query query = em.createQuery(hqlQuery)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void updateSpotDetails(SpotModel spot) {
		
		List<Stress> stresses = spot.getStresses();
		List<Question> healthQuestionnaire = spot.getHealth().getQuestions();

		
		for(Stress stress : stresses) {
			boolean updateStatus = stress.getStatus();
			
			if (updateStatus) {
//				String hqlQuery = "update TaskStressDetails set symptomId = :symptomId, "
//						+ " leftIncident = :leftIncident, leftSeverity = :leftSeverity, isBenchmarkedLeft = :isBenchmarkedLeft,"
//						+ " frontIncident = :frontIncident, frontSeverity = :frontSeverity, isBenchmarkedFront = :isBenchmarkedFront,"
//						+ " rightIncident = :rightIncident, rightSeverity = :rightSeverity, isBenchmarkedRight = :isBenchmarkedRight"
//						+ " where id = :id";


				String hqlQuery = "update TaskStressSpotSymptomImages set incidence = :incidence, " +
						"severity = :severity, benchmark = :benchmark where id = :id";

				Query queryLeft = em.createQuery(hqlQuery);
				Query queryFront = em.createQuery(hqlQuery);
				Query queryRight = em.createQuery(hqlQuery);
//				query.setParameter("symptomId", stress.getDesc());

				queryLeft.setParameter("benchmark", stress.getLeft().getBenchmark());
				queryLeft.setParameter("severity", stress.getLeft().getSeverity());
				queryLeft.setParameter("incidence", stress.getLeft().getIncidence());
				queryLeft.setParameter("id", stress.getLeft().getId());
				queryLeft.executeUpdate();

				queryFront.setParameter("benchmark", stress.getFront().getBenchmark());
				queryFront.setParameter("severity", stress.getFront().getSeverity());
				queryFront.setParameter("incidence", stress.getFront().getIncidence());
				queryFront.setParameter("id", stress.getFront().getId());
				queryFront.executeUpdate();

				queryRight.setParameter("benchmark", stress.getRight().getBenchmark());
				queryRight.setParameter("severity", stress.getRight().getSeverity());
				queryRight.setParameter("incidence", stress.getRight().getIncidence());
				queryRight.setParameter("id", stress.getRight().getId());
				queryRight.executeUpdate();


//				query.setParameter("leftIncident", stress.getLeft().getIncidence());
//				query.setParameter("leftSeverity", stress.getLeft().getSeverity());
//				query.setParameter("isBenchmarkedLeft", stress.getLeft().getBenchmark());

//				query.setParameter("frontIncident", stress.getFront().getIncidence());
//				query.setParameter("frontSeverity", stress.getFront().getSeverity());
//				query.setParameter("isBenchmarkedFront", stress.getFront().getBenchmark());

//				query.setParameter("rightIncident", stress.getRight().getIncidence());
//				query.setParameter("rightSeverity", stress.getRight().getSeverity());
//				query.setParameter("isBenchmarkedRight", stress.getRight().getBenchmark());

//				query.setParameter("id", stress.getId());
//				query.executeUpdate();

				if (stress.getLeft().getBenchmark() != null) {
					if (stress.getLeft().getBenchmark()) {
						createTaskForBenchmarkImages(stress.getLeft().getId(), spot.getUserId(), stress.getDesc(), stress.getLeft().getImage());
					}
				}

				if (stress.getFront().getBenchmark() != null) {
					if (stress.getFront().getBenchmark()) {
						createTaskForBenchmarkImages(stress.getFront().getId(), spot.getUserId(), stress.getDesc(), stress.getFront().getImage());
					}
				}

				if (stress.getRight().getBenchmark() != null) {
					if (stress.getRight().getBenchmark()) {
						createTaskForBenchmarkImages(stress.getRight().getId(), spot.getUserId(), stress.getDesc(), stress.getRight().getImage());
					}
				}
				
			} else {
				String hqlQuery = "delete from TaskStressDetails where id = :id";
				Query query = em.createQuery(hqlQuery);
				query.setParameter("id", stress.getId());
				query.executeUpdate();
			}
		}
		
		for(Question question :healthQuestionnaire) {
//			String hqlQuery = "update TaskHealthDetails set selectedAnswer = :selectedAnswer where id = :questionId" ;
			String hqlQuery = "update TaskSpotHealth set inputValues = :inputValues where id = :questionId";
			Query query = em.createQuery(hqlQuery);
			query.setParameter("inputValues", question.getSelected());
			query.setParameter("questionId", question.getFormControlName());
			query.executeUpdate();
		}
		
		String hqlQuery = "update TaskSpot set isBenchmark = :isSpotBenchmark where id = :spotId" ;
		Query query = em.createQuery(hqlQuery);
		query.setParameter("isSpotBenchmark",spot.getIsSpotBenchmark() == true ? 1 : 0);
		query.setParameter("spotId",  spot.getName());
		query.executeUpdate();
	}
	
	private void createTaskForBenchmarkImages(String tsssiId, Integer userId, Integer symptomId, String imageName) {
		String generatedId = generateKey(userId, "BENCHMARKED_IMAGES");

//		String hqlQuery2 = "from StaticData where id = 1";
//		Query query2 = em.createQuery(hqlQuery2, StaticData.class);
//		StaticData staticData = (StaticData) query2.getResultList().get(0);
		List<StaticData> staticData = getStaticDataByKey(List.of("docBasePath", "benchmarkedImagePath","stressImagePath"));
		Optional<String> path = staticData.stream().filter(s -> s.getDataKey().equals("docBasePath")).map(s -> s.getDataValue()).findFirst();
		Optional<String> benchmarkedImagePath = staticData.stream().filter(s -> s.getDataKey().equals("benchmarkedImagePath")).map(s -> s.getDataValue()).findFirst();
		Optional<String> stressImagePath = staticData.stream().filter(s -> s.getDataKey().equals("stressImagePath")).map(s -> s.getDataValue()).findFirst();
		
		String benchmarkedImage = path.orElse("")+benchmarkedImagePath.orElse(""); //staticData.getBaseImagePath();
		String stressImage = path.orElse("")+stressImagePath.orElse("");
		
		File sourcePath = new File(
				stressImage + File.separator + imageName);
		
		String hqlQuery2 = " from BenchmarkedImages where imageName = :imageName";
		Query query2 = em.createQuery(hqlQuery2, BenchmarkedImages.class).setParameter("imageName", imageName);
		List<BenchmarkedImages> benchmarkedImagesCount = query2.getResultList();
		
//		if (sourcePath.exists() && sourcePath.isFile() && benchmarkedImagesCount.size() == 0) {
			
//			File destinationPath = new File(
//					benchmarkedImage + File.separator + imageName);
			
//			try {
//				Files.copy(Paths.get(sourcePath.getAbsolutePath()), Paths.get(destinationPath.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

//			String hqlQuery = "select cci.varietyId from TaskStressDetails as tsd "					// TODO need to change task stress deatils table
//					+ " inner join Task as t on tsd.taskId = t.id"
//					+ " inner join CaseCropInfo cci on t.entityId = cci.caseId " + " where tsd.id = :tsdId";

			String hqlQuery = "select cci.varietyId from TaskStressSpotSymptomImages as tsssi\n" +
					"inner join TaskStressSpotSymptom  as tsss on tsssi.taskSpotStressSymptomId = tsss.id\n" +
					"inner join TaskSpotStress tss on tsss.taskSpotStressId = tss.id\n" +
					"inner join TaskSpot ts on tss.taskSpotId = ts.id\n" +
					"inner join Task t on ts.taskId = t.id\n" +
					"inner join CaseCropInfo cci on t.entityId = cci.caseId where tsssi.id = :tsssiId";
			Query query = em.createQuery(hqlQuery).setParameter("tsssiId", tsssiId);
			int varietyId = Integer.parseInt(query.getResultList().get(0).toString());

			String hqlQuery1 = " select regionId from Users" + " where id = :userId";
			Query query1 = em.createQuery(hqlQuery1).setParameter("userId", userId);
			int regionId = Integer.parseInt(query1.getResultList().get(0).toString());

			BenchmarkedImages benchmarkedImages = new BenchmarkedImages();
			benchmarkedImages.setId(generatedId);
			benchmarkedImages.setCreatedBy(userId);
			benchmarkedImages.setCreatedDate(new Date(System.currentTimeMillis()));
			benchmarkedImages.setImageName(imageName);
			benchmarkedImages.setStatus(0);
			benchmarkedImages.setRegionId(regionId);
			benchmarkedImages.setStressSymptomId(symptomId);
			benchmarkedImages.setVarietyId(varietyId);
			em.persist(benchmarkedImages);

			Task task = new Task();
			String generatedTaskId = generateKey(userId, "TASK");
			task.setId(generatedTaskId);
			task.setEntityId(generatedId);
			task.setEntityTypeId(5);
			task.setStatus(0);
			task.setTaskTypeId(14);
			em.persist(task);
//		}
	}

	@Transactional
	@Override
	public void submitDiagnoseDetails(RecommendationModel recommendationModel, TaskHistory taskHistory, Task task) {
		String taskId = recommendationModel.getTaskId();
		List<RecommendationStress> recommendationStresses =  recommendationModel.getStresses();
		for(RecommendationStress recommendationStress :recommendationStresses) {
			List<Integer> selectedRecommendations = recommendationStress.getSelectedRecommendations();
			for(int selectedRecommendation :selectedRecommendations) {
				TaskRecommendation taskRecommendation = new TaskRecommendation();
				String generatedTaskRecommendationId = generateKey(recommendationModel.getUserId(),"TASK_RECOMMENDATION");
				taskRecommendation.setTaskId(taskId);
				taskRecommendation.setId(generatedTaskRecommendationId);
				taskRecommendation.setRecommendationId(selectedRecommendation);
				em.persist(taskRecommendation);
			}
		}
		
		String generatedTaskHistoryId = generateKey(recommendationModel.getUserId(),"TASK_HISTORY");
		taskHistory.setId(generatedTaskHistoryId);
//		taskHistory.setStatus(2);
		taskHistory.setTaskDate(new Date(System.currentTimeMillis()));
		taskHistory.setEndTime(new Time(System.currentTimeMillis()));
		taskHistory.setComment( recommendationModel.getComment()+" | "+recommendationModel.getLabel());
		em.persist(taskHistory);
		
		task.setAssigneeId(0);
		task.setStatus(0);
//		task.setTaskTypeId(9);
		em.persist(task);

		Optional<Subtask> fetchedSubtask = subtaskRepository.findFirstByTaskIdOrderByCreatedAtDesc(taskId);
		if (fetchedSubtask.isPresent()){
			fetchedSubtask.get().setRltSampleIsVerified(true);

			em.persist(fetchedSubtask.get());
		}
		String hqlQuery = "update FarmCase set caseSampleStatus = :caseSampleStatus where id = :caseId" ;
		Query query = em.createQuery(hqlQuery);
		query.setParameter("caseSampleStatus", recommendationModel.getLabel());
		query.setParameter("caseId", task.getEntityId());
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Task> getTask(String taskId) {
		Query query = em.createQuery(" from Task where id = :taskId",Task.class)
				.setParameter("taskId", taskId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getSelectedRecommendations(String taskId, int stressId){
		String hqlQuery = "select tr.recommendationId "
				+ " from TaskRecommendation as tr inner join StressRecommendation as sr on tr.recommendationId = sr.id"
				+ " where tr.taskId = :taskId and sr.stressId = :stressId";
		Query query = em.createQuery(hqlQuery)
				.setParameter("taskId", taskId)
				.setParameter("stressId", stressId);
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void rlmReassign(TaskHistory taskHistory, Task task, ReassignModel reassignModel) {
		
		String generatedTaskHistoryId = generateKey(reassignModel.getRlmUserId(),"TASK_HISTORY");
		taskHistory.setId(generatedTaskHistoryId);
//		taskHistory.setStatus(2);
		taskHistory.setTaskDate(new Date(System.currentTimeMillis()));
		taskHistory.setEndTime(new Time(System.currentTimeMillis()));
		taskHistory.setComment(reassignModel.getComment() + " | Reassigned");
		em.persist(taskHistory);
		
		task.setTaskDate(new Date(System.currentTimeMillis()));
		task.setTaskTime(null);
		task.setEndTime(null);
		task.setTaskTypeId(7);
		task.setAssigneeId(reassignModel.getRltUserId());
		task.setStatus(1);
		em.persist(task);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> getRlt(int rlmId){
		Query query = em.createQuery("select u.id, u.firstName, u.middleName, u.lastName from Users as u inner join UserRoles as ur on u.id = ur.userId where u.reportingTo = :rlmId and ur.roleId = 11")
				.setParameter("rlmId", rlmId);
		return query.getResultList();
	}
	
	
	@Transactional
	@Override
	public void rlmApprove(RecommendationModel recommendationModel, TaskHistory taskHistory, Task task, Calendar cal) {
		
		String taskId = recommendationModel.getTaskId();
		Query query = em.createQuery("delete from TaskRecommendation where taskId = :taskId")
				.setParameter("taskId", taskId);
		query.executeUpdate();
		
		List<RecommendationStress> recommendationStresses =  recommendationModel.getStresses();
		for(RecommendationStress recommendationStress :recommendationStresses) {
			List<Integer> selectedRecommendations = recommendationStress.getSelectedRecommendations();
			for(int selectedRecommendation :selectedRecommendations) {
				String generatedId = generateKey(recommendationModel.getUserId(),"TASK_RECOMMENDATION");
				TaskRecommendation taskRecommendation = new TaskRecommendation();
				taskRecommendation.setId(generatedId);
				taskRecommendation.setTaskId(taskId);
				taskRecommendation.setRecommendationId(selectedRecommendation);
				em.persist(taskRecommendation);
			}
		}
		
		String generatedTaskHistoryId = generateKey(recommendationModel.getUserId(),"TASK_HISTORY");
		taskHistory.setId(generatedTaskHistoryId);
//		taskHistory.setStatus(2);
		taskHistory.setTaskDate(new Date(System.currentTimeMillis()));
		taskHistory.setEndTime(new Time(System.currentTimeMillis()));
		taskHistory.setComment(recommendationModel.getComment()+  " | Approved");
		em.persist(taskHistory);
		
		int maxVersion = 0;
		List<Integer> maxVersions = getRightsByCaseId(task.getEntityId());
		if(maxVersions.size() > 0) {
			maxVersion = maxVersions.get(0);
		}
		/*TODO: temporary remove*/
//		if(recommendationModel.getLabel().trim().equals("Good Sample") || maxVersion > 1) {
		if(maxVersion >= 1) {
			task.setTaskDate(null);
			task.setTaskTime(null);
			task.setEndTime(null);
			task.setAssigneeId(0);
			task.setStatus(0);

			Optional<Subtask> fetchedSubtask = subtaskRepository.findFirstByTaskIdOrderByCreatedAtDesc(task.getId());

			if (fetchedSubtask.isPresent()) {
				Subtask subtask = fetchedSubtask.get();
				subtask.setRlmApprovalIsVerified(true);
				subtaskRepository.save(subtask);

				if (subtask.getKycIsVerified() && subtask.getKmlIsVerified() && subtask.getBankIsVerified()
						&& subtask.getRltSampleIsVerified()) {
					task.setTaskTypeId(8);
				}
			}
			em.persist(task);
			
			/*Task newTask = new Task();
			newTask.setId(generateKey(recommendationModel.getUserId(),"TASK"));
			newTask.setTaskDate(new Date(cal.getTime().getTime()));
			newTask.setTaskTime(null);
			newTask.setEndTime(null);
			newTask.setAssigneeId(0);
			newTask.setStatus(0);
			newTask.setTaskTypeId(11);
			newTask.setEntityId(task.getEntityId());
			newTask.setEntityTypeId(task.getEntityTypeId());
			em.persist(newTask);*/


			if(recommendationModel.getLabel().trim().equals("Good Sample")) {
				updateRights(task.getEntityId());
			}
		} else {
			task.setEndTime(new Time(System.currentTimeMillis()));
//			task.setStatus(2);
			em.persist(task);
		}

	}
	
	private void updateRights(String caseId) {
		String hqlQuery1 = " select id from Rights where caseId = :caseId and versionNumber = "
				+ " (select max(versionNumber) from Rights where caseId = :caseId)";
		
		Query query1 = em.createQuery(hqlQuery1)
				.setParameter("caseId", caseId);
		List<String> rightIds = query1.getResultList();
		String rightId = "";
		if(rightIds.size() > 0) {
			rightId = rightIds.get(0);
		}
		
		String hqlQuery = "update Rights set isVerified = 1 where id = :rightId";
		Query query = em.createQuery(hqlQuery).setParameter("rightId", rightId);
		query.executeUpdate();

	}
	
	@SuppressWarnings("unchecked")
	public List<Integer> getRightsByCaseId(String caseId){
		Query query = em.createQuery("select max(versionNumber)from Rights where caseId = :caseId")
				.setParameter("caseId", caseId);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<FarmCase> getFarmCaseById(String caseId){
		Query query = em.createQuery("from FarmCase where id = :caseId", FarmCase.class)
				.setParameter("caseId", caseId);
		return query.getResultList();
	}
	
	@Transactional
	@Override
	public void setStartDate(String taskId, Task task) {
		em.persist(task);
	}
	
	private String generateKey(Integer userId, String entityName) {
		
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> getQualityParameter(String taskId) {
		Query query = em.createQuery("select qcp.parameterId, qcp.maxValue,qcp.minValue,qp.name, qcp.commodityId FROM Task t \n"
				+ " inner join CaseCropInfo cci on cci.caseId = t.entityId \n"
				+ " inner join Variety v on v.id = cci.varietyId \n"
				+ " inner join QualityCommodityParameter qcp on qcp.commodityId = v.commodityId \n"
				+ " inner join QualityParameter qp on qp.id = qcp.parameterId \n"
				+ " where t.id = :taskId order by qp.id").
				setParameter("taskId", taskId);
		return query.getResultList();
	}
}
