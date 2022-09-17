package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name = "village_additional_info")
public class VillageAdditionalInfo implements EntityModel {
	@Id
	private String id;
	
	@Column(name = "village_id")
	private Integer villageId;
	
	@Column(name = "farmer_count")
	private Integer farmerCount;

	/** added new variable in sync Task Processor -CDT-Ujwal-Start */
	
	@Column(name = "apmc")
	private String apmc;
	
	@Column(name = "irrigation_percent")
	private Integer irrigationPercent;
	
	@Column(name = "net_sown_area")
	private Double netSownArea;
	
	@Column(name = "avg_land_holding_size")
	private Double avgLandHoldingSize;
	
	
	public String getApmc() {
		return apmc;
	}

	public void setApmc(String apmc) {
		this.apmc = apmc;
	}

	public Integer getIrrigationPercent() {
		return irrigationPercent;
	}

	public void setIrrigationPercent(Integer irrigationPercent) {
		this.irrigationPercent = irrigationPercent;
	}

	public Double getNetSownArea() {
		return netSownArea;
	}

	public void setNetSownArea(Double netSownArea) {
		this.netSownArea = netSownArea;
	}

	public Double getAvgLandHoldingSize() {
		return avgLandHoldingSize;
	}

	public void setAvgLandHoldingSize(Double avgLandHoldingSize) {
		this.avgLandHoldingSize = avgLandHoldingSize;
	}
	/** added new variable in sync Task Processor -CDT-Ujwal-End */
	
	@Override
	public String toString() {
		return "VillageAdditionalInfo [id=" + id + ", villageId=" + villageId + ", farmerCount=" + farmerCount
				+ ", apmc=" + apmc + ", irrigationPercent=" + irrigationPercent + ", netSownArea=" + netSownArea
				+ ", avgLandHoldingSize=" + avgLandHoldingSize + "]";
	}
	
	/** added new variable in prs sync Task Processor -CDT-Ujwal-End */
	
	public VillageAdditionalInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVillageId() {
		return villageId;
	}

	public void setVillageId(Integer villageId) {
		this.villageId = villageId;
	}

	public Integer getFarmerCount() {
		return farmerCount;
	}

	public void setFarmerCount(Integer farmerCount) {
		this.farmerCount = farmerCount;
	}
	
}
