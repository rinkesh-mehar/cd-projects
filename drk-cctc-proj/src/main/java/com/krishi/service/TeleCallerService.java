package com.krishi.service;

import javax.validation.Valid;

import com.krishi.entity.Commodity;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;

import com.krishi.entity.FarmerCropInfo;
import com.krishi.model.FieldMonitoringRequestModel;
import com.krishi.model.LeadCallingDetailRequestModel;
import com.krishi.model.RejectTaskModel;
import com.krishi.model.ResponseMessage;

import java.util.List;

public interface TeleCallerService {

	ResponseMessage getFieldMonitoringList(Integer userId);

	ResponseMessage getLeadCallingDetail(String taskId);

	ResponseMessage saveLeadCallingDetail(LeadCallingDetailRequestModel leadCallingDetailRequestModel);

	ResponseMessage updateTask(RejectTaskModel rejectTaskModel);

	ResponseMessage getFieldMonitoringDetail(String taskId);

	ResponseMessage updateMonitoringTask(FieldMonitoringRequestModel model);

	ResponseMessage getForwardNonTechnicalCallingList(@Valid DataTablesInput input, Integer stateId, Integer regionId, Integer districtId, Integer villageId, Integer commodityId, Integer areaId);

	ResponseMessage getLeadCallingStateList();

	ResponseMessage getLeadCallingCommodityList();

	ResponseMessage getLandHoldingSize();

	ResponseMessage getLeadCallingRegionList(Integer stateId);

	ResponseMessage getLeadCallingDistrictList(Integer regionId);

	ResponseMessage getLeadCallingVillageList(Integer districtId);

	/** added for calling status list - Pranay */
	ResponseMessage getCallingStatusList();
	
	/** added for farmer major crop- CDT-Ujwal */
	ResponseMessage getVarietyByCommodityId(Integer commodityId);
	
	ResponseMessage saveAndUpdateFarmerCropInfo(FarmerCropInfo farmerCropInfo);
	
	ResponseMessage getFarmerExistingCropById(String farmerCropId);
	
	ResponseMessage deleteFarmerCropInfo(String farmerCropId);
	
	ResponseMessage getFarmerMajorCropList(String taskId);

	ResponseMessage getHarvestMonitoringDetail(String taskId);

	List<Commodity> getCommodityListByCropType(Integer cropTypeId, Integer farmerId);
}
