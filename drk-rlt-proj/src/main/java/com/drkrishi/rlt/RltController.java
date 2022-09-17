package com.drkrishi.rlt;

import java.util.List;

import com.drkrishi.rlt.entity.ViewFarmerCropInfo;
import com.drkrishi.rlt.model.*;
import com.drkrishi.rlt.model.vo.CaseCropInfoVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import com.drkrishi.rlt.entity.Task;
import com.drkrishi.rlt.service.BarcodeService;
import com.drkrishi.rlt.service.DiagnoseService;
import com.drkrishi.rlt.service.DiagnosisApprovalService;
import com.drkrishi.rlt.service.ManageHardwareService;

@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/drk-rlt")
@RestController
public class RltController {

	private static final Logger LOGGER = LogManager.getLogger(RltController.class);

	@Autowired
	private BarcodeService barcodeService;

	@Autowired
	private DiagnoseService diagnoseService;
	
	@Autowired
	private DiagnosisApprovalService diagnosisApprovalService;
	
	@Autowired
	private ManageHardwareService manageHardwareService;

	// get all sample details list for RLT
	@RequestMapping(path = "/getSampleList/{rltId}", method = RequestMethod.GET, produces = "application/json")
	public List<BarcodeModel> getSampleList(@PathVariable("rltId") int rltId) {
		return barcodeService.getSampleList(rltId);
	}

	// based on bar-code number fetching data
	@RequestMapping(path = "/getBarcodeDetails/{barcodeNumber}/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getBarcodeDetails(@PathVariable("barcodeNumber") String barcodeNumber,@PathVariable("userId") int userId) {
		LOGGER.info("API stated, request barcodeNumber : " + barcodeNumber);
		return barcodeService.getBarcodeDetails(barcodeNumber,userId);
	}

	// save bar-code details into table
	@RequestMapping(path = "/saveBarcodeDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage saveBarcodeDetails(@RequestBody BarcodeRequestModel barcodeRequestModel) {
		return barcodeService.saveBarcodeDetails(barcodeRequestModel);
	}


	@RequestMapping(path = "/needMoreSample/{taskId}/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage needMoreSample(@PathVariable("taskId") String taskId, @PathVariable("userId") int userId) {
		 return barcodeService.setNeedMoreSample(taskId, userId);
	}

	// General information, comments and aerial photo 
	@RequestMapping(path = "/getGeneralInformation/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public DiagnoseGACModel getGeneralInformation(@PathVariable("taskId") String taskId) {
		return diagnoseService.getGACInformation(taskId);
	}
	// Get Farmer Crop Info by Task ID 		-- Rinkesh KM --
	@RequestMapping(path = "/getCommodityInfo/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public CaseCropInfoVo getFarmerCropInfo(@PathVariable("taskId") String taskId) {
		return diagnoseService.getFarmerCropInfo(taskId);
	}

	@RequestMapping(path = "/getSymptoms/{stressId}", method = RequestMethod.GET, produces = "application/json")
	public List<SymptomsModel> getSymptoms(@PathVariable("stressId") Integer stressId) {
		return diagnoseService.getSymptoms(stressId);
	}

	
//	@RequestMapping(path = "/getMasterDetails/{flag}", method = RequestMethod.GET, produces = "application/json")
//	public MasterModel getMasterDetails(@PathVariable("flag") int flag) {
//		return diagnoseService.getMasterDetails(flag);
//	}
	
	@RequestMapping(path = "/getRecommendations/{taskId}/{userId}", method = RequestMethod.GET, produces = "application/json")
	public RecommendationModel getRecommendations(@PathVariable("taskId") String taskId, @PathVariable("userId") Integer userId) {
		return diagnoseService.getRecommendations(taskId, userId);
	}
	
	@RequestMapping(path = "/getDiagnoseDetails/{taskId}/{flag}/{userId}", method = RequestMethod.GET, produces = "application/json")
	public DiagnoseModel getDiagnoseDetails(@PathVariable("taskId") String taskId, @PathVariable("flag") int flag, @PathVariable("userId") int userId) {
		 return diagnoseService.getDiagnoseDetails(taskId, flag, userId);
	}
	

	@RequestMapping(path = "/getIrrigationInformation/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public IrrigationDetail getIrrigationDetail(@PathVariable("taskId") String taskId) {
		 return diagnoseService.getIrrigationDetail(taskId);
	}
	
	@RequestMapping(path = "/getCropInformation/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public CropInformation getCropInformation(@PathVariable("taskId") String taskId) {
		 return diagnoseService.getCropInformation(taskId);
	}
	
	@RequestMapping(path = "/getFertilizer/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public Fertilizer getFertilizer(@PathVariable("taskId") String taskId) {
		 return diagnoseService.getFertilizer(taskId);
	}
	
	@RequestMapping(path = "/getSeedTreatment/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public SeedTreatment getSeedTreatment(@PathVariable("taskId") String taskId) {
		 return diagnoseService.getSeedTreatment(taskId);
	}
	
	@RequestMapping(path = "/getRemedialMeasures/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public RemedialMeasure getRemedialMeasures(@PathVariable("taskId") String taskId) {
		 return diagnoseService.getRemedialMeasures(taskId);
	}
	
	@RequestMapping(path = "/saveSpotDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage saveSpotDetails(@RequestBody SpotModel spot) {
		 return diagnoseService.saveSpotDetails(spot);
	}
	
	@RequestMapping(path = "/saveDiagoseDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage saveDiagnoseDetails(@RequestBody RecommendationModel recommendationModel) {
		return diagnoseService.saveDiagnoseDetails(recommendationModel);
	}
	
	
	//  RLM API's
	
	@RequestMapping(path = "/getDiagnosisApprovalList/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getDiagnosisApprovalList(@PathVariable("userId") Integer userId, 
			@RequestParam(name = "taskstatus", defaultValue = "pending") String taskStatus,
			@RequestParam(name = "startdate", required = false) String startDate,
			@RequestParam(name = "enddate", required = false) String endDate,
			@RequestParam(name="barcode", required = false) String barcode) {
		return diagnosisApprovalService.getDiagnosisApprovalList(userId, taskStatus, startDate, endDate, barcode);	
	}
	
	@RequestMapping(path = "/assignTask", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponseMessage updateAssignee(@RequestBody Task task) {
		return diagnosisApprovalService.updateAssignee(task);
	}
	
	
	@RequestMapping(path = "/rlmApprove", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage rlmApprove(@RequestBody RecommendationModel recommendationModel) {
		return diagnoseService.rlmApprove(recommendationModel);
	}
	
	@RequestMapping(path = "/rlmReassign", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage rlmReassign(@RequestBody ReassignModel reassignModel) {
		return diagnoseService.rlmReassign(reassignModel);
	}
	
	@RequestMapping(path = "/getRlt/{rlmId}", method = RequestMethod.GET, produces = "application/json")
	public List<RltUserModel> getRlt(@PathVariable("rlmId") int rlmId) {
		return diagnoseService.getRlt(rlmId);
	}
	
	@RequestMapping(path="/assignedHardwareList/{userId}", method=RequestMethod.GET, produces = "application/json")
	public ResponseMessage getAssignedHardwareList(@PathVariable("userId")Integer userId, @RequestParam(name = "searchKeyward", required = false) String searchKeyward) {
		return manageHardwareService.getAssignedHardwardList(userId, searchKeyward);
	}
	
	@RequestMapping(path="/unTagHardware", method=RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseMessage unTagHardware(@RequestBody ManageHardwareModel manageHardwareModel) {
		return manageHardwareService.unTagHardware(manageHardwareModel);
	}
	//added new
	@RequestMapping(path = "/getParameterList/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public List<QualityParameterRangeModel> getQualityParameterList(@PathVariable("taskId") String taskId) {
		return diagnoseService.getQualityParameter(taskId);
	}

	@RequestMapping(path = "/getBand", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<?> getBand(@RequestBody QualityParameterRangeModel qualityParameterRangeModels) {
		return diagnoseService.getBand(qualityParameterRangeModels);
	}
}
