package com.krishi.controller;

import javax.validation.Valid;

import com.krishi.entity.Commodity;
import com.krishi.service.SpotListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.krishi.entity.FarmerCropInfo;
import com.krishi.model.FieldMonitoringRequestModel;
import com.krishi.model.LeadCallingDetailRequestModel;
import com.krishi.model.RejectTaskModel;
import com.krishi.model.ResponseMessage;
import com.krishi.service.TeleCallerService;

import java.util.List;

//@CrossOrigin(origins = "*", allowCredentials = "true", allowedHeaders = "*")
@RestController
public class TeleCallerController {

	@Autowired
	private TeleCallerService teleCallerService;

	@Autowired
	private SpotListService spotListService;
	
	@RequestMapping(path="/getLeadCallingStateList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingStateList() {
		return teleCallerService.getLeadCallingStateList();
	}

	@RequestMapping(path="/getLeadCallingCommodityList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingCommodityList() {
		return teleCallerService.getLeadCallingCommodityList();
	}

	@RequestMapping(path="/getLandHoldingSize", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLandHoldingSize() {
		return teleCallerService.getLandHoldingSize();
	}
	
	@RequestMapping(path="/getLeadCallingRegionListByStateId/{stateId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingStateList(@PathVariable("stateId") Integer stateId) {
		return teleCallerService.getLeadCallingRegionList(stateId);
	}
	
	@RequestMapping(path="/getLeadCallingDistrictListByRegionId/{regionId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingDistrictList(@PathVariable("regionId") Integer regionId) {
		return teleCallerService.getLeadCallingDistrictList(regionId);
	}
	
	@RequestMapping(path="/getLeadCallingVillageListByDistrictId/{districtId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingVillageList(@PathVariable("districtId") Integer districtId) {
		return teleCallerService.getLeadCallingVillageList(districtId);
	}
	
	@RequestMapping(path="/getLeadCallingListForward", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingListForward(@RequestBody @Valid DataTablesInput input,
			@RequestParam(name = "stateId", required = false, defaultValue = "0") Integer stateId, @RequestParam(name="regionId", required = false, defaultValue = "0") Integer regionId,
			@RequestParam(name="districtId", required = false, defaultValue = "0") Integer districtId, @RequestParam(name="villageId", required=false, defaultValue = "0") Integer villageId,
			@RequestParam(name="commodityId", required=false, defaultValue = "0") Integer commodityId, @RequestParam(name="areaId", required=false, defaultValue = "0") Integer areaId) {
		return teleCallerService.getForwardNonTechnicalCallingList(input, stateId, regionId, districtId, villageId, commodityId, areaId);
	}

	@RequestMapping(path="/getLeadCallingListSpot", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingListSpot(@RequestBody @Valid DataTablesInput input,
			@RequestParam(name = "stateId", required = false, defaultValue = "0") Integer stateId, @RequestParam(name="regionId", required = false, defaultValue = "0") Integer regionId,
			@RequestParam(name="districtId", required = false, defaultValue = "0") Integer districtId, @RequestParam(name="villageId", required=false, defaultValue = "0") Integer villageId,
			@RequestParam(name="commodityId", required=false, defaultValue = "0") Integer commodityId, @RequestParam(name="areaId", required=false, defaultValue = "0") Integer areaId) {
		return spotListService.getSpotNonTechnicalCallingList(input, stateId, regionId, districtId, villageId, commodityId, areaId);
	}
	
	/** added for calling status list - Pranay : Start */
	@RequestMapping(path="/getCallingStatusList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getCallingStatusList() {
		return teleCallerService.getCallingStatusList();
	}
	/** added for calling status list - Pranay : End */
	
	@RequestMapping(path="/rejectTask", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage rejectTask(@RequestBody RejectTaskModel rejectTaskModel) {
		return teleCallerService.updateTask(rejectTaskModel);
	}
	
	@RequestMapping(path="/leadCallingDetail/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getLeadCallingDetail(@PathVariable("taskId") String taskId) {
		return teleCallerService.getLeadCallingDetail(taskId);
	}
	
	@RequestMapping(path="/leadCallingDetail", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE, produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage saveLeadCallingDetail(@RequestBody LeadCallingDetailRequestModel leadCallingDetailRequestModel) {
		return teleCallerService.saveLeadCallingDetail(leadCallingDetailRequestModel);
	}
	

	

	@RequestMapping(path = "/fieldMonitoringList/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getFieldMonitoringList(@PathVariable("userId") Integer userId) {
		return teleCallerService.getFieldMonitoringList(userId);
	}
	

	@RequestMapping(path="/fieldMonitoringDetail/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getFieldMonitoringDetail(@PathVariable("taskId") String taskId) {
		return teleCallerService.getFieldMonitoringDetail(taskId);
	}

	@RequestMapping(path="/harvestMonitoring/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getHarvestMonitoringDetail(@PathVariable("taskId") String taskId) {
		return teleCallerService.getHarvestMonitoringDetail(taskId);
	}
	
	@RequestMapping(path="/fieldMonitoringDetail", method=RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage saveFieldMonitoringDetail(@RequestBody FieldMonitoringRequestModel model) {
		return teleCallerService.updateMonitoringTask(model);
	}
	
	
	/** added for farmer major crop - CDT-Ujwal: Start */
	@RequestMapping(path="/varietyList/{commodityId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getVarietyList(@PathVariable("commodityId") Integer commodityId) {
		return teleCallerService.getVarietyByCommodityId(commodityId);
	}
	
	
	@RequestMapping(path = "/updateFarmerCropInfo", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage updateFarmerCropInfo(@RequestBody FarmerCropInfo farmerCropInfo) {
		return teleCallerService.saveAndUpdateFarmerCropInfo(farmerCropInfo);
	}
	
	@RequestMapping(path = "/getExistingCropInfo/{farmerCropId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getFarmerExistingCropInfo(@PathVariable("farmerCropId") String farmerCropId) {
		return teleCallerService.getFarmerExistingCropById(farmerCropId);
	}
	
	@RequestMapping(path = "/deleteFarmerCropInfo/{farmerCropId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage deleteFarmerCropInfo(@PathVariable("farmerCropId") String farmerCropId) {
		return teleCallerService.deleteFarmerCropInfo(farmerCropId);
	}
	
	@RequestMapping(path="/getFarmerCropList/{taskId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseMessage getFarmerCropList(@PathVariable("taskId") String taskId) {
		return teleCallerService.getFarmerMajorCropList(taskId);
	}
	/** added for farmer major crop - CDT-Ujwal: End */

	@RequestMapping(path = "/getCommodityList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public List<Commodity> getCommodityListByCropType(@RequestParam("cropType") Integer cropTypeId, @RequestParam("regionId") Integer regionId){

		return teleCallerService.getCommodityListByCropType(cropTypeId,regionId);
	}
}
