package com.drkrishi.iqa;

import java.util.List;

import com.drkrishi.iqa.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.drkrishi.iqa.service.IqaService;
import com.drkrishi.iqa.service.KmlqaService;
import com.drkrishi.iqa.service.KycqaService;

//@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
//@RequestMapping("/clqa")
public class IqaController {
	@Autowired
	IqaService iqaService;
	
	@Autowired
	KmlqaService KmlqaService;
	
	@Autowired
	KycqaService kycqaService;

	// IQA

	@RequestMapping(path = "/getIqaList/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getIqaList(@PathVariable("userId") int userId) {
		return iqaService.getIqaTaskList(userId);
	}
	
//	@RequestMapping(path = "/getImageDetails", method = RequestMethod.POST, produces = "application/json")
//	public BenchmarkedImageDetailsModel getImageDetails(@RequestBody IqaTaskListModel iqaTaskListModel) {
//		return iqaService.getImageDetails(iqaTaskListModel);
//	}
	
	
	@RequestMapping(path = "/getImageDetails/{qaId}/{commodityId}/{stateId}/{regionId}/{status}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getImageDetails(@PathVariable("qaId") int qaId, @PathVariable("commodityId") Integer commodityId,
										   @PathVariable("stateId") Integer stateId,
										   @PathVariable("regionId") Integer regionId,
										   @PathVariable("status") boolean status) {
		
		IqaTaskListModel iqaTaskListModel = new IqaTaskListModel();
		iqaTaskListModel.setQaId(qaId);
		/*iqaTaskListModel.setCommodityName(commodityName);
		iqaTaskListModel.setStateName(stateName);
		iqaTaskListModel.setRegionName(regionName);*/
		iqaTaskListModel.setCommodityId(commodityId);
		iqaTaskListModel.setStateId(stateId);
		iqaTaskListModel.setRegionId(regionId);
		return iqaService.getStressImageDetails(iqaTaskListModel, status);
	}

	@RequestMapping(path = "/getSpotStressDetails/{spotId}/{commodityId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getSpotStressDetails(@PathVariable("spotId") String spotId, @PathVariable("commodityId") Integer commodityId){

		return iqaService.getSpotStressDetails(spotId,commodityId);
	}

	@RequestMapping(path = "/getSpotHealthDetails/{spotId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getHealthDetails(@PathVariable("spotId") String spotId) {

		/*IqaTaskListModel iqaTaskListModel = new IqaTaskListModel();
		iqaTaskListModel.setQaId(qaId);*/
		/*iqaTaskListModel.setCommodityName(commodityName);
		iqaTaskListModel.setStateName(stateName);
		iqaTaskListModel.setRegionName(regionName);*/
		/*iqaTaskListModel.setCommodityId(commodityId);
		iqaTaskListModel.setStateId(stateId);
		iqaTaskListModel.setRegionId(regionId);*/
		return iqaService.getHealthImageDetails(spotId);
	}

	@RequestMapping(path = "/getTaskSpot/{commodityId}/{stateId}/{regionId}", method = RequestMethod.GET, produces = "application/json")
		public ResponseMessage getTaskSpotList(@PathVariable("commodityId") Integer commodityId, @PathVariable("stateId") Integer stateId, @PathVariable("regionId") Integer regionId){
		IqaTaskListModel iqaTaskListModel = new IqaTaskListModel();
//		iqaTaskListModel.setQaId(qaId);
		/*iqaTaskListModel.setCommodityName(commodityName);
		iqaTaskListModel.setStateName(stateName);
		iqaTaskListModel.setRegionName(regionName);*/
		iqaTaskListModel.setCommodityId(commodityId);
		iqaTaskListModel.setStateId(stateId);
		iqaTaskListModel.setRegionId(regionId);
		return iqaService.getTaskSpotIds(iqaTaskListModel);
		}

	@RequestMapping(path = "/uploadBenchmarkedImage", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage uploadBenchmarkedImage(@RequestBody ImageUploadRequest imageUploadRequest) throws Exception
	{
		
		return iqaService.uploadEditedBenchmarkedImage(imageUploadRequest.getImageName(), imageUploadRequest.getImage(), imageUploadRequest.getId(), imageUploadRequest.getStatus(), imageUploadRequest.getUserId());
	}
	
	@RequestMapping(path = "/rejectBenchmarkedImage", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage benchmarkedImageRejection(@RequestBody BenchmarkedImageRejectionModel benchmarkedImageRejectionModel ) {
		return iqaService.imageRejection(benchmarkedImageRejectionModel);
	}
	
	
	// KML
	
	@RequestMapping(path = "/getKmlqaList/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getSampleList(@PathVariable("id") int id) {
		return KmlqaService.getKmlqaList(id);
	}
	
	@RequestMapping(path = "/uploadFile", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public ResponseMessage uploadKmlFile(@RequestParam("kmlFile") MultipartFile kmlFile, @RequestParam("taskId") String taskId) {
		return KmlqaService.uploadKmlFile(kmlFile,taskId);
	}
	
	@RequestMapping(path = "/uploadFiles", method = RequestMethod.POST, consumes = {"multipart/form-data"})
	public ResponseMessage uploadFile(@RequestParam("file") MultipartFile file) {
		return kycqaService.uploadFile(file);
	}
	@RequestMapping(path = "/downloadFile/{fileName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity downloadFile(@PathVariable("fileName") String fileName) {
		return KmlqaService.downloadKmlFile(fileName);
	}
	
	@RequestMapping(path = "/fileDetails/{taskId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage fileDetails(@PathVariable("taskId") String taskId) {
		return KmlqaService.fileDetails(taskId);
	}
	
	@RequestMapping(path = "/submitdetails/{taskId}/{area}/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage submitDetails(@PathVariable("taskId") String taskId,@PathVariable("area") Double area, @PathVariable("userId") int userId) {
		return KmlqaService.submitDetails(taskId,area, userId);
	}
	
	//KYC
	
	@RequestMapping(path = "/getKycqaList/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getKycqaList(@PathVariable("id") int id) {
		return kycqaService.getKycqaList(id);
	}

	@RequestMapping(path = "/getKycDetails/{farmerId}/{taskId}/{userId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseMessage getKycDetails(@PathVariable("farmerId") String farmerId,@PathVariable("taskId") String taskId,@PathVariable("userId") int userId ){
		return kycqaService.getKycDetails(farmerId, taskId,userId);
	}

	@RequestMapping(path = "/saveKycDetails", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage saveKycDetails(@RequestBody KycDetailsModel kycDetailsModel) {
		return kycqaService.saveKycDetails(kycDetailsModel);
	}
	
	@RequestMapping(path = "/kycCorrectionCalling", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage kycCorrection(@RequestBody KycDetailsModel kycDetailsModel) {
		return kycqaService.kycCorrection(kycDetailsModel);
	}
	@RequestMapping(path = "/completeTask", method = RequestMethod.POST, produces = "application/json")
	public ResponseMessage completeTask(@RequestBody CompleteTaskModel completeTaskModel){
//		System.out.println("completed task Id is -> " + taskId);
		return iqaService.completedTask(completeTaskModel.getTaskId(),completeTaskModel.getUserId());

	}
}
