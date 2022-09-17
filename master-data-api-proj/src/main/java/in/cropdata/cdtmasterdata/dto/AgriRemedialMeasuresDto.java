package in.cropdata.cdtmasterdata.dto;

import java.util.List;

import in.cropdata.cdtmasterdata.model.AgriCommodityAgrochemical;
import in.cropdata.cdtmasterdata.model.AgriDistrictCommodityStress;

public class AgriRemedialMeasuresDto {

	private Integer id;

	public Integer stateCode;

	public Integer commodityId;

	public Integer companyId;

	public Integer stressTypeId;

	public String brandName;

	public Integer agrchemicalTypeId;

	public String remedialStatus;

	public String stressTypeName;

	public String agrochemicalTypeName;

	public String status;

	List<AgriDistrictCommodityStress> stressList;

	List<AgriCommodityAgrochemical> agrochemicalList;

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

	public List<AgriDistrictCommodityStress> getStressList() {
		return stressList;
	}

	public void setStressList(List<AgriDistrictCommodityStress> stressList) {
		this.stressList = stressList;
	}

	public List<AgriCommodityAgrochemical> getAgrochemicalList() {
		return agrochemicalList;
	}

	public void setAgrochemicalList(List<AgriCommodityAgrochemical> agrochemicalList) {
		this.agrochemicalList = agrochemicalList;
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

	public String getStressTypeName() {
		return stressTypeName;
	}

	public void setStressTypeName(String stressTypeName) {
		this.stressTypeName = stressTypeName;
	}

	public String getAgrochemicalTypeName() {
		return agrochemicalTypeName;
	}

	public void setAgrochemicalTypeName(String agrochemicalTypeName) {
		this.agrochemicalTypeName = agrochemicalTypeName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
