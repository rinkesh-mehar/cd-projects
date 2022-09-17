package com.krishi.fls.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.fls.entity.BankDetails;
import com.krishi.fls.entity.CaseCoordinates;
import com.krishi.fls.entity.CaseCropInfo;
import com.krishi.fls.entity.CaseFieldDetails;
import com.krishi.fls.entity.CaseKml;
import com.krishi.fls.entity.FarmCase;
import com.krishi.fls.entity.Farmer;
import com.krishi.fls.entity.FarmerCroppingPattern;
import com.krishi.fls.entity.FarmerFarm;
import com.krishi.fls.entity.FarmerGeneralInfo;
import com.krishi.fls.entity.FarmerInsuranceInfo;
import com.krishi.fls.entity.FarmerKyc;
import com.krishi.fls.entity.FarmerLandInfo;
import com.krishi.fls.entity.FarmerLoanInfo;
import com.krishi.fls.entity.FarmerMachineryInfo;
import com.krishi.fls.entity.FarmerPolyhouseInfo;
import com.krishi.fls.entity.Rights;
import com.krishi.fls.entity.TaskAerialPhotos;
import com.krishi.fls.entity.TaskAllocation;
import com.krishi.fls.entity.TaskHealthDetails;
import com.krishi.fls.entity.TaskHealthPhoto;
import com.krishi.fls.entity.TaskHistory;
import com.krishi.fls.entity.TaskSpot;
import com.krishi.fls.entity.TaskStressDetails;
import com.krishi.fls.entity.TaskTransaction;
import com.krishi.fls.entity.VillageAdditionalInfo;

/**
 * @author janardhan
 *
 */
public class FlsDataSynch {

	private List<Farmer> farmer;
	private List<FarmerCroppingPattern> farmerCroping;
	private List<FarmerGeneralInfo> farmerGenInfo;
	private List<FarmerInsuranceInfo> farmerinsuranceInfo;
	private List<FarmerKyc> farmerKyc;
	private List<FarmerLandInfo> farmerLandInfo;
	private List<FarmerLoanInfo> farmerLoanInfo;
	private List<FarmerPolyhouseInfo> farmerPolyhouse;
	private List<FarmerMachineryInfo> farmerMachinery;
	@JsonProperty
	private List<TaskAllocation> task;
	private List<CaseCropInfo> caseCropInfo;
	private List<CaseFieldDetails> casefieldDetails;
	private List<FarmerFarm> farmerFarm;
	@JsonProperty
	private List<FarmCase> farmCase;
	private List<TaskHistory> taskHistory;
	private List<CaseCoordinates> caseCoordinates;
	private List<TaskStressDetails> stressTaskDetails;
	private List<TaskHealthDetails> healthTaskDetails;
	private List<TaskSpot> taskSpot;
	private List<TaskHealthPhoto> healthPhotos;
	private List<Rights> rights;
	private List<BankDetails> bankDetails;
	private List<CaseKml> caseKml;
	private List<VillageAdditionalInfo> villageAdditionalInfo;
	private List<TaskTransaction> taskTransaction;
	private List<TaskAerialPhotos> taskAerialPhotos;

	public List<TaskTransaction> getTaskTransaction() {
		return taskTransaction;
	}

	public void setTaskTransaction(List<TaskTransaction> taskTransaction) {
		this.taskTransaction = taskTransaction;
	}

	public List<VillageAdditionalInfo> getVillageAdditionalInfo() {
		return villageAdditionalInfo;
	}

	public void setVillageAdditionalInfo(List<VillageAdditionalInfo> villageAdditionalInfo) {
		this.villageAdditionalInfo = villageAdditionalInfo;
	}

	public List<TaskSpot> getTaskSpot() {
		return taskSpot;
	}

	public void setTaskSpot(List<TaskSpot> taskSpot) {
		this.taskSpot = taskSpot;
	}

	

	public List<Farmer> getFarmer() {
		return farmer;
	}

	public void setFarmer(List<Farmer> farmer) {
		this.farmer = farmer;
	}

	public List<FarmerCroppingPattern> getFarmerCroping() {
		return farmerCroping;
	}

	public void setFarmerCroping(List<FarmerCroppingPattern> farmerCroping) {
		this.farmerCroping = farmerCroping;
	}

	public List<FarmerGeneralInfo> getFarmerGenInfo() {
		return farmerGenInfo;
	}

	public void setFarmerGenInfo(List<FarmerGeneralInfo> farmerGenInfo) {
		this.farmerGenInfo = farmerGenInfo;
	}

	public List<FarmerInsuranceInfo> getFarmerinsuranceInfo() {
		return farmerinsuranceInfo;
	}

	public void setFarmerinsuranceInfo(List<FarmerInsuranceInfo> farmerinsuranceInfo) {
		this.farmerinsuranceInfo = farmerinsuranceInfo;
	}

	public List<FarmerKyc> getFarmerKyc() {
		return farmerKyc;
	}

	public void setFarmerKyc(List<FarmerKyc> farmerKyc) {
		this.farmerKyc = farmerKyc;
	}

	public List<FarmerLandInfo> getFarmerLandInfo() {
		return farmerLandInfo;
	}

	public void setFarmerLandInfo(List<FarmerLandInfo> farmerLandInfo) {
		this.farmerLandInfo = farmerLandInfo;
	}

	public List<FarmerLoanInfo> getFarmerLoanInfo() {
		return farmerLoanInfo;
	}

	public List<Rights> getRights() {
		return rights;
	}

	public void setRights(List<Rights> rights) {
		this.rights = rights;
	}

	public void setFarmerLoanInfo(List<FarmerLoanInfo> farmerLoanInfo) {
		this.farmerLoanInfo = farmerLoanInfo;
	}

	public List<FarmerPolyhouseInfo> getFarmerPolyhouse() {
		return farmerPolyhouse;
	}

	public void setFarmerPolyhouse(List<FarmerPolyhouseInfo> farmerPolyhouse) {
		this.farmerPolyhouse = farmerPolyhouse;
	}

	public List<FarmerMachineryInfo> getFarmerMachinery() {
		return farmerMachinery;
	}

	public void setFarmerMachinery(List<FarmerMachineryInfo> farmerMachinery) {
		this.farmerMachinery = farmerMachinery;
	}

	public List<TaskAllocation> getTask() {
		return task;
	}

	public void setTask(List<TaskAllocation> task) {
		this.task = task;
	}

	public List<CaseCropInfo> getCaseCropInfo() {
		return caseCropInfo;
	}

	public void setCaseCropInfo(List<CaseCropInfo> caseCropInfo) {
		this.caseCropInfo = caseCropInfo;
	}

	public List<CaseFieldDetails> getCasefieldDetails() {
		return casefieldDetails;
	}

	public void setCasefieldDetails(List<CaseFieldDetails> casefieldDetails) {
		this.casefieldDetails = casefieldDetails;
	}

	public List<FarmerFarm> getFarmerFarm() {
		return farmerFarm;
	}

	public void setFarmerFarm(List<FarmerFarm> farmerFarm) {
		this.farmerFarm = farmerFarm;
	}

	public List<FarmCase> getFarmCase() {
		return farmCase;
	}

	public void setFarmCase(List<FarmCase> farmCase) {
		this.farmCase = farmCase;
	}


	public List<TaskHistory> getTaskHistory() {
		return taskHistory;
	}

	public void setTaskHistory(List<TaskHistory> taskHistory) {
		this.taskHistory = taskHistory;
	}

	public List<CaseCoordinates> getCaseCoordinates() {
		return caseCoordinates;
	}

	public void setCaseCoordinates(List<CaseCoordinates> caseCoordinates) {
		this.caseCoordinates = caseCoordinates;
	}

	public List<TaskStressDetails> getStressTaskDetails() {
		return stressTaskDetails;
	}

	public void setStressTaskDetails(List<TaskStressDetails> stressTaskDetails) {
		this.stressTaskDetails = stressTaskDetails;
	}

	public List<TaskHealthDetails> getHealthTaskDetails() {
		return healthTaskDetails;
	}

	public void setHealthTaskDetails(List<TaskHealthDetails> healthTaskDetails) {
		this.healthTaskDetails = healthTaskDetails;
	}

	public List<TaskHealthPhoto> getHealthPhotos() {
		return healthPhotos;
	}

	public void setHealthPhotos(List<TaskHealthPhoto> healthPhotos) {
		this.healthPhotos = healthPhotos;
	}

	public List<BankDetails> getBankDetails() {
		return bankDetails;
	}

	public void setBankDetails(List<BankDetails> bankDetails) {
		this.bankDetails = bankDetails;
	}

	public List<CaseKml> getCaseKml() {
		return caseKml;
	}

	public void setCaseKml(List<CaseKml> caseKml) {
		this.caseKml = caseKml;
	}

	public List<TaskAerialPhotos> getTaskAerialPhotos() {
		return taskAerialPhotos;
	}

	public void setTaskAerialPhotos(List<TaskAerialPhotos> taskAerialPhotos) {
		this.taskAerialPhotos = taskAerialPhotos;
	}


}
