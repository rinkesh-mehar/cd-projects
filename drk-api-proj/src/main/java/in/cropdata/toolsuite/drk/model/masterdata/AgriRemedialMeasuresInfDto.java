package in.cropdata.toolsuite.drk.model.masterdata;

import java.util.Date;

public class AgriRemedialMeasuresInfDto {

	private Integer id;

	private Integer stateCode;

	private Integer commodityId;

	private Integer companyId;

	private Integer varietyId;

	private Integer stressTypeId;

	private Integer agrchemicalTypeId;

	private String commodity;

	private String variety;

	private String name;

	private String brandName;
	
	private Date updatedAt;

	private Date createdAt;

	private String status;

	private String remedialStatus;

	private String bioticStressId;

	private String stressCommodityId;

	private String bioticStressTypeId;

	private String bioticStressName;

	private String scientificName;

	private String startPhenophaseId;

	private String endPhenophaseId;

	private String symptoms;

	private String agrochemicalId;

	private String agrochemicalTypeId;

	private String agrochemicalName;

	private String waitingPeriod;

	private String commodityIdForAgrochemical;

	private String agroStressTypeId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateCode() {
		return stateCode;
	}

	public void setStateCode(Integer stateCode) {
		this.stateCode = stateCode;
	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getVarietyId() {
		return varietyId;
	}

	public void setVarietyId(Integer varietyId) {
		this.varietyId = varietyId;
	}

	public Integer getStressTypeId() {
		return stressTypeId;
	}

	public void setStressTypeId(Integer stressTypeId) {
		this.stressTypeId = stressTypeId;
	}

	public String getCommodity() {
		return commodity;
	}

	public void setCommodity(String commodity) {
		this.commodity = commodity;
	}

	public String getVariety() {
		return variety;
	}

	public void setVariety(String variety) {
		this.variety = variety;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getBioticStressId() {
		return bioticStressId;
	}

	public void setBioticStressId(String bioticStressId) {
		this.bioticStressId = bioticStressId;
	}

	public String getStressCommodityId() {
		return stressCommodityId;
	}

	public void setStressCommodityId(String stressCommodityId) {
		this.stressCommodityId = stressCommodityId;
	}

	public String getBioticStressTypeId() {
		return bioticStressTypeId;
	}

	public void setBioticStressTypeId(String bioticStressTypeId) {
		this.bioticStressTypeId = bioticStressTypeId;
	}

	public String getBioticStressName() {
		return bioticStressName;
	}

	public void setBioticStressName(String bioticStressName) {
		this.bioticStressName = bioticStressName;
	}

	public String getScientificName() {
		return scientificName;
	}

	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}

	public String getStartPhenophaseId() {
		return startPhenophaseId;
	}

	public void setStartPhenophaseId(String startPhenophaseId) {
		this.startPhenophaseId = startPhenophaseId;
	}

	public String getEndPhenophaseId() {
		return endPhenophaseId;
	}

	public void setEndPhenophaseId(String endPhenophaseId) {
		this.endPhenophaseId = endPhenophaseId;
	}

	public String getSymptoms() {
		return symptoms;
	}

	public void setSymptoms(String symptoms) {
		this.symptoms = symptoms;
	}

	public String getAgrochemicalId() {
		return agrochemicalId;
	}

	public void setAgrochemicalId(String agrochemicalId) {
		this.agrochemicalId = agrochemicalId;
	}

	public String getAgrochemicalTypeId() {
		return agrochemicalTypeId;
	}

	public void setAgrochemicalTypeId(String agrochemicalTypeId) {
		this.agrochemicalTypeId = agrochemicalTypeId;
	}

	public String getAgrochemicalName() {
		return agrochemicalName;
	}

	public void setAgrochemicalName(String agrochemicalName) {
		this.agrochemicalName = agrochemicalName;
	}

	public String getWaitingPeriod() {
		return waitingPeriod;
	}

	public void setWaitingPeriod(String waitingPeriod) {
		this.waitingPeriod = waitingPeriod;
	}

	public String getCommodityIdForAgrochemical() {
		return commodityIdForAgrochemical;
	}

	public void setCommodityIdForAgrochemical(String commodityIdForAgrochemical) {
		this.commodityIdForAgrochemical = commodityIdForAgrochemical;
	}

	public String getAgroStressTypeId() {
		return agroStressTypeId;
	}

	public void setAgroStressTypeId(String agroStressTypeId) {
		this.agroStressTypeId = agroStressTypeId;
	}

	public Integer getAgrchemicalTypeId() {
		return agrchemicalTypeId;
	}

	public void setAgrchemicalTypeId(Integer agrchemicalTypeId) {
		this.agrchemicalTypeId = agrchemicalTypeId;
	}

	public String getRemedialStatus() {
		return remedialStatus;
	}

	public void setRemedialStatus(String remedialStatus) {
		this.remedialStatus = remedialStatus;
	}

}
