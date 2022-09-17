package com.krishi.fls.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krishi.fls.entity.BankDetails;
import com.krishi.fls.entity.CaseCoordinates;
import com.krishi.fls.entity.CaseCropInfo;
import com.krishi.fls.entity.CaseFieldDetails;
import com.krishi.fls.entity.CaseKml;
import com.krishi.fls.entity.FarmCase;
import com.krishi.fls.entity.Farmer;
import com.krishi.fls.entity.FarmerFarm;
import com.krishi.fls.entity.FarmerGeneralInfo;
import com.krishi.fls.entity.GovtOfficial;
import com.krishi.fls.entity.Poi;
import com.krishi.fls.entity.Rights;
import com.krishi.fls.entity.VillageAdditionalInfo;
import com.krishi.fls.entity.VillageInfo;
import com.krishi.fls.entity.Vip;

public class HistoricalData {
	
	private List<VillageInfo> villageInfos;
	private List<Farmer> farmer;
	private List<Vip> vip;
	private Set<GovtOfficial> govtOffical;
	private Set<Poi> poi;
	private List<FarmerFarm> farmerFarm;
	private List<FarmCase> farmCase;
	private List<CaseCropInfo> caseCropInfo;
	private List<CaseFieldDetails> caseFieldDetails;
	private List<CaseKml> caseKml;
	private List<CaseCoordinates> caseCoordinates;
	private String zipFilePath;
	private List<Rights> rights;
//	private List<BankDetails> farmerBankAccount;
	private List<Map<String, Object>> farmerBankDetails;
	private List<FarmerGeneralInfo> farmerGeneralInfo;
	private List<VillageAdditionalInfo> villageAdditionalInfo;
	
	public List<VillageInfo> getVillageInfos() {
		return villageInfos;
	}
	public void setVillageInfos(List<VillageInfo> villageInfos) {
		this.villageInfos = villageInfos;
	}
	public List<Farmer> getFarmer() {
		return farmer;
	}
	public void setFarmer(List<Farmer> farmer) {
		this.farmer = farmer;
	}
	public List<Vip> getVip() {
		return vip;
	}
	public void setVip(List<Vip> vip) {
		this.vip = vip;
	}
	public Set<GovtOfficial> getGovtOffical() {
		return govtOffical;
	}
	public void setGovtOffical(Set<GovtOfficial> govtOffical) {
		this.govtOffical = govtOffical;
	}
	public Set<Poi> getPoi() {
		return poi;
	}
	public void setPoi(Set<Poi> poi) {
		this.poi = poi;
	}
	public List<FarmerFarm> getFarmerFarm() {
		return farmerFarm;
	}
	public List<FarmerGeneralInfo> getFarmerGeneralInfo() {
		return farmerGeneralInfo;
	}
	public void setFarmerGeneralInfo(List<FarmerGeneralInfo> farmerGeneralInfo) {
		this.farmerGeneralInfo = farmerGeneralInfo;
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
	public List<CaseCropInfo> getCaseCropInfo() {
		return caseCropInfo;
	}
	public void setCaseCropInfo(List<CaseCropInfo> caseCropInfo) {
		this.caseCropInfo = caseCropInfo;
	}
	public List<CaseFieldDetails> getCaseFieldDetails() {
		return caseFieldDetails;
	}
	public void setCaseFieldDetails(List<CaseFieldDetails> caseFieldDetails) {
		this.caseFieldDetails = caseFieldDetails;
	}
	public List<CaseKml> getCaseKml() {
		return caseKml;
	}
	public void setCaseKml(List<CaseKml> caseKml) {
		this.caseKml = caseKml;
	}
	public List<CaseCoordinates> getCaseCoordinates() {
		return caseCoordinates;
	}
	public void setCaseCoordinates(List<CaseCoordinates> caseCoordinates) {
		this.caseCoordinates = caseCoordinates;
	}
	public String getZipFilePath() {
		return zipFilePath;
	}
	public void setZipFilePath(String zipFilePath) {
		this.zipFilePath = zipFilePath;
	}
	public List<Rights> getRights() {
		return rights;
	}
	public void setRights(List<Rights> rights) {
		this.rights = rights;
	}
//	public List<BankDetails> getFarmerBankAccount() {
//		return farmerBankAccount;
//	}
//	public void setFarmerBankAccount(List<BankDetails> farmerBankAccount) {
//		this.farmerBankAccount = farmerBankAccount;
//	}

	public List<Map<String, Object>> getFarmerBankDetails() {
		return farmerBankDetails;
	}

	public void setFarmerBankDetails(List<Map<String, Object>> farmerBankDetails) {
		this.farmerBankDetails = farmerBankDetails;
	}

	public List<VillageAdditionalInfo> getVillageAdditionalInfo() {
		return villageAdditionalInfo;
	}
	public void setVillageAdditionalInfo(List<VillageAdditionalInfo> villageAdditionalInfo) {
		this.villageAdditionalInfo = villageAdditionalInfo;
	}
	@Override
	public String toString() {
		return "HistoricalData [villageInfos=" + villageInfos + ", farmer=" + farmer + ", vip=" + vip + ", govtOfficial="
				+ govtOffical + ", poi=" + poi + "]";
	}
	

}
