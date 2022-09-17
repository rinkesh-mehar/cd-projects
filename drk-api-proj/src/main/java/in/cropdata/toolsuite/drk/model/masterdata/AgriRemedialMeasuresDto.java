package in.cropdata.toolsuite.drk.model.masterdata;

import java.util.List;

public class AgriRemedialMeasuresDto {

	private Integer id;

	public Integer stateCode;

	public Integer commodityId;

	public Integer companyId;

	public Integer stressTypeId;

	public Integer agrochemicalTypeId;

	public String brandName;

	public String status;

	public String remedialStatus;;

	List<AgriBioticStress> stressList;

	List<AgriAgrochemicalMaster> agrochemicalList;

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

	public Integer getStressTypeId() {
		return stressTypeId;
	}

	public void setStressTypeId(Integer stressTypeId) {
		this.stressTypeId = stressTypeId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public List<AgriBioticStress> getBioticStressList() {
		return stressList;
	}

	public void setBioticStressList(List<AgriBioticStress> bioticStressList) {
		this.stressList = bioticStressList;
	}

	public List<AgriAgrochemicalMaster> getAgrochemicalList() {
		return agrochemicalList;
	}

	public void setAgrochemicalList(List<AgriAgrochemicalMaster> agrochemicalList) {
		this.agrochemicalList = agrochemicalList;
	}

	public Integer getAgrochemicalTypeId() {
		return agrochemicalTypeId;
	}

	public void setAgrochemicalTypeId(Integer agrochemicalTypeId) {
		this.agrochemicalTypeId = agrochemicalTypeId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemedialStatus() {
		return remedialStatus;
	}

	public void setRemedialStatus(String remedialStatus) {
		this.remedialStatus = remedialStatus;
	}

	public List<AgriBioticStress> getStressList() {
		return stressList;
	}

	public void setStressList(List<AgriBioticStress> stressList) {
		this.stressList = stressList;
	}

}
