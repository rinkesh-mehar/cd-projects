package com.drkrishi.rlt.service;

import java.util.List;

import com.drkrishi.rlt.entity.ViewFarmerCropInfo;
import com.drkrishi.rlt.model.*;
import com.drkrishi.rlt.model.vo.CaseCropInfoVo;
import org.springframework.http.ResponseEntity;

public interface DiagnoseService {
	
	public List<SymptomsModel> getSymptoms(Integer stressId);
	public CaseDetailsModel getCaseDetails(String caseId, int flag);
	
	public DiagnoseGACModel getGACInformation(String taskId);
	
	public DiagnoseModel getDiagnoseDetails(String taskId, int flag,  int userId);
//	public List<Comment> getComments(int taskId);
//	public MasterModel getMasterDetails(int flag);
	public RecommendationModel getRecommendations(String taskId, Integer userId);
	
	public IrrigationDetail getIrrigationDetail(String taskId);
	public Fertilizer getFertilizer(String taskId);
	public CropInformation getCropInformation(String taskId);
	public SeedTreatment getSeedTreatment(String taskId);
	public ResponseMessage saveSpotDetails(SpotModel spot);
	public RemedialMeasure getRemedialMeasures(String taskId);
	
	public ResponseMessage saveDiagnoseDetails(RecommendationModel recommendationModel);
	
	public ResponseMessage rlmApprove( RecommendationModel recommendationModel);
	public ResponseMessage rlmReassign(ReassignModel reassignModel);
	
	public List<RltUserModel> getRlt(int rlmId);

	public List<QualityParameterRangeModel> getQualityParameter(String taskId);

	public ResponseEntity<?> getBand(QualityParameterRangeModel qualityParameterRangeModels);

	CaseCropInfoVo getFarmerCropInfo(String taskId);

//	List<StressDetailsVO> getStressDetails();
}
