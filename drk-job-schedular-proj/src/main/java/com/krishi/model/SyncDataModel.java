package com.krishi.model;

import com.krishi.entity.*;

import java.util.List;

public class SyncDataModel {

    //common
    private List<Farmer> farmer;
    private List<VillageAdditionalInfo> villageAdditionalInfo;

    //fls
    private List<FarmerCroppingPattern> farmerCroping;
    private List<FarmerGeneralInfo> farmerGenInfo;
    private List<FarmerInsuranceInfo> farmerinsuranceInfo;
    private List<FarmerKyc> farmerKyc;
    private List<FarmerLandInfo> farmerLandInfo;
    private List<FarmerLoanInfo> farmerLoanInfo;
    private List<FarmerPolyhouseInfo> farmerPolyhouse;
    private List<FarmerMachineryInfo> farmerMachinery;
    private List<Task> task;
    private List<CaseCropInfo> caseCropInfo;
    private List<CaseFieldDetails> casefieldDetails;
    private List<FarmerFarm> farmerFarm;
    private List<FarmCase> farmCase;
    private List<TaskHistory> taskHistory;
    private List<CaseCoordinates> caseCoordinates;
    private List<TaskStressDetails> stressTaskDetails;
    private List<TaskSpotHealth> taskSpotHealth;
    private List<TaskHealthDetails> healthTaskDetails;
    private List<TaskSpot> taskSpot;
    private List<TaskHealthPhoto> healthPhotos;
    private List<Rights> rights;
    private List<FarmerBankAccount> farmerBankAccount;
    private List<CaseKml> caseKml;
    private List<TaskTransaction> taskTransaction;
    private List<TaskAerialPhotos> taskAerialPhotos;
    private List<TaskRecommendation> taskRecommendation;
    //!fls

    //prs
    private List<GovtOfficial> govtOfficial;
    private List<VillageInfo> village;
    private List<Vip> vip;
    private List<Poi> poi;
    private List<PrsTask> villageTask;
    
	/** added farmer crop info in prs task sync processor - CDT-Ujwal - Start */
    private List<FarmerCropInfo> farmerCropInfo;

    public List<FarmerCropInfo> getFarmerCropInfo() {
		return farmerCropInfo;
	}

	public void setFarmerCropInfo(List<FarmerCropInfo> farmerCropInfo) {
		this.farmerCropInfo = farmerCropInfo;
	}
	
	/** added farmer crop info in prs task sync processor - CDT-Ujwal - End */

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

    public List<Task> getTask() {
        return task;
    }

    public void setTask(List<Task> task) {
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

    public List<TaskSpotHealth> getTaskSpotHealth() {
        return taskSpotHealth;
    }

    public void setTaskSpotHealth(List<TaskSpotHealth> healthTaskDetails) {
        this.taskSpotHealth = healthTaskDetails;
    }


    public List<TaskSpot> getTaskSpot() {
        return taskSpot;
    }

    public void setTaskSpot(List<TaskSpot> taskSpot) {
        this.taskSpot = taskSpot;
    }

    public List<TaskHealthPhoto> getHealthPhotos() {
        return healthPhotos;
    }

    public void setHealthPhotos(List<TaskHealthPhoto> healthPhotos) {
        this.healthPhotos = healthPhotos;
    }

    public List<Rights> getRights() {
        return rights;
    }

    public void setRights(List<Rights> rights) {
        this.rights = rights;
    }

    public List<FarmerBankAccount> getFarmerBankAccount() {
        return farmerBankAccount;
    }

    public void setFarmerBankAccount(List<FarmerBankAccount> farmerBankAccount) {
        this.farmerBankAccount = farmerBankAccount;
    }

    public List<CaseKml> getCaseKml() {
        return caseKml;
    }

    public void setCaseKml(List<CaseKml> caseKml) {
        this.caseKml = caseKml;
    }

    public List<VillageAdditionalInfo> getVillageAdditionalInfo() {
        return villageAdditionalInfo;
    }

    public void setVillageAdditionalInfo(List<VillageAdditionalInfo> villageAdditionalInfo) {
        this.villageAdditionalInfo = villageAdditionalInfo;
    }

    public List<TaskTransaction> getTaskTransaction() {
        return taskTransaction;
    }

    public void setTaskTransaction(List<TaskTransaction> taskTransaction) {
        this.taskTransaction = taskTransaction;
    }

    public List<GovtOfficial> getGovtOfficial() {
        return govtOfficial;
    }

    public void setGovtOfficial(List<GovtOfficial> govtOfficial) {
        this.govtOfficial = govtOfficial;
    }

    public List<VillageInfo> getVillage() {
        return village;
    }

    public void setVillage(List<VillageInfo> village) {
        this.village = village;
    }

    public List<Vip> getVip() {
        return vip;
    }

    public void setVip(List<Vip> vip) {
        this.vip = vip;
    }

    public List<Poi> getPoi() {
        return poi;
    }

    public void setPoi(List<Poi> poi) {
        this.poi = poi;
    }

    public List<PrsTask> getVillageTask() {
        return villageTask;
    }

    public void setVillageTask(List<PrsTask> villageTask) {
        this.villageTask = villageTask;
    }

    public void setVillageAddintionalInfo(List<VillageAdditionalInfo> info) {
        this.villageAdditionalInfo = info;
    }

	public List<TaskAerialPhotos> getTaskAerialPhotos() {
		return taskAerialPhotos;
	}

	public void setTaskAerialPhotos(List<TaskAerialPhotos> taskAerialPhotos) {
		this.taskAerialPhotos = taskAerialPhotos;
	}

	public List<TaskRecommendation> getTaskRecommendation() {
		return taskRecommendation;
	}

	public void setTaskRecommendation(List<TaskRecommendation> taskRecommendation) {
		this.taskRecommendation = taskRecommendation;
	}
}
