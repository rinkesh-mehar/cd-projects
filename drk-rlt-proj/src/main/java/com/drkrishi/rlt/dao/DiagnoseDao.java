package com.drkrishi.rlt.dao;

import java.util.Calendar;
import java.util.List;

import com.drkrishi.rlt.entity.*;
import com.drkrishi.rlt.model.ReassignModel;
import com.drkrishi.rlt.model.RecommendationModel;
import com.drkrishi.rlt.model.SpotModel;

public interface DiagnoseDao {

	public List<FarmCase> getFarmCaseById(String caseId);
	public void setStartDate(String taskId, Task task);
	public List<CaseDetails> getCaseDetails(String taskId);
	public List<Object> getAerialPhoto(String taskId);
	public List<Object> getShapeFile(String taskId);
	public List<StressCase> getStressDetails(String spotId);
	public List<Object> getQuestionnaireDetails(String spotId);
	public List<TaskSpot> getSpotId(String taskId);
	public List<Object[]> getRecommendationStressDetails(List<String> spots);
	public List<StressSeverity> getStressSeverity();
	public List<StressSeverityControlMeasures> getStressSeverityControlMeasures(int stressId, int severityId);
	public List<Commodity> getCommodity(String taskId);
	public List<StressRecommendation> getStressRecommendation(int commodityId, int stressId, List<Integer> controlMeasureId);
	
	public List<Object[]> getComments(String taskId);
	public List<UnitOfMeasurement> getUnitOfMeasurement();
	public List<Object> getAgrochemical(int id);
	public List<Object> getAgrochemicalApplication(int id);
	
	public List<ViewGeneralInformation> getGeneralInformation(String taskId);
	public List<Object[]> getCaseFieldDetails(String taskId);
	public List<Object> getIrrigationMethod(String id);
	public List<Object> getIrrigationSource(String id);
	public List<Object[]> getFertilizer(String taskId);
	public List<Object[]> getCropInformation(String taskId);
	public List<Object[]> getSeedTreatment(String taskId);
	public List<Object> getTreatmentAgent(String ids);
	public List<Object> getPesticides(Integer ids);
	//public StaticData getStaticData();
	public List<StaticData> getStaticDataByKey(List<String> keys);
	public List<Object[]> getRemedialMeasures(String taskId);
	
	public void updateSpotDetails(SpotModel spot);
	public void submitDiagnoseDetails(RecommendationModel recommendationModel, TaskHistory taskHistory, Task task);
	
	public List<Task> getTask(String taskId);
	
	public List<Object> getSelectedRecommendations(String taskId, int stressId);
	
	public void rlmReassign(TaskHistory taskHistory, Task task, ReassignModel reassignModel);
	
	public List<Object[]> getRlt(int rlmId);
	public void rlmApprove(RecommendationModel recommendationModel, TaskHistory taskHistory, Task task, Calendar cal);
	public List<StressSymptoms> getStressSymptoms(Integer stressId);

	public List<Object> getQualityParameter(String taskId);

    CaseCropInfo getSowingIdAndAczId(String taskId);
}
