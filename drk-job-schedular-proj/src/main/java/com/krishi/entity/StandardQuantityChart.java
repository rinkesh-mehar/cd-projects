package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT - Rinkesh
 */

@Entity
@Table(name = "standard_quantity_chart")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StandardQuantityChart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "state_id")
	@JsonProperty("StateCode")
	private Integer stateCode;

	@Column(name = "commodity_id")
	@JsonProperty("CommodityID")
	private Integer commodityID;

	@Column(name = "variety_id")
	@JsonProperty("VarietyID")
	private Integer varietyID;

	@Column(name = "standard_quantity_per_acre")
	@JsonProperty("StandardQuantityPerAcre")
	private Double standardQuantityPerAcre;

	@Column(name = "standard_positive_variance_per_acre")
	@JsonProperty("StandardPositiveVariancePerAcre")
	private Double standardPositiveVariancePerAcre;

	@Column(name = "standard_positive_variance_percent")
	@JsonProperty("StandardPositiveVariancePercent")
	private Double standardPositiveVariancePercent;

	@Column(name = "standard_negative_variance_per_acre")
	@JsonProperty("StandardNegativeVariancePerAcre")
	private Double standardNegativeVariancePerAcre;

	@Column(name = "standard_negative_variance_percent")
	@JsonProperty("StandardNegativeVariancePercent")
	private Double standardNegativeVariancePercent;

	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

	public StandardQuantityChart() {
		super();
	}

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

	public Integer getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(Integer commodityID) {
		this.commodityID = commodityID;
	}

	public Integer getVarietyID() {
		return varietyID;
	}

	public void setVarietyID(Integer varietyID) {
		this.varietyID = varietyID;
	}

	public Double getStandardQuantityPerAcre() {
		return standardQuantityPerAcre;
	}

	public void setStandardQuantityPerAcre(Double standardQuantityPerAcre) {
		this.standardQuantityPerAcre = standardQuantityPerAcre;
	}

	public Double getStandardPositiveVariancePerAcre() {
		return standardPositiveVariancePerAcre;
	}

	public void setStandardPositiveVariancePerAcre(Double standardPositiveVariancePerAcre) {
		this.standardPositiveVariancePerAcre = standardPositiveVariancePerAcre;
	}

	public Double getStandardPositiveVariancePercent() {
		return standardPositiveVariancePercent;
	}

	public void setStandardPositiveVariancePercent(Double standardPositiveVariancePercent) {
		this.standardPositiveVariancePercent = standardPositiveVariancePercent;
	}

	public Double getStandardNegativeVariancePerAcre() {
		return standardNegativeVariancePerAcre;
	}

	public void setStandardNegativeVariancePerAcre(Double standardNegativeVariancePerAcre) {
		this.standardNegativeVariancePerAcre = standardNegativeVariancePerAcre;
	}

	public Double getStandardNegativeVariancePercent() {
		return standardNegativeVariancePercent;
	}

	public void setStandardNegativeVariancePercent(Double standardNegativeVariancePercent) {
		this.standardNegativeVariancePercent = standardNegativeVariancePercent;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatus(String status) {
		this.status = (status != null && status.equalsIgnoreCase("Active")) ? 1 : 0;
	}

	@Override
	public String toString() {
		return "StandardQuantityChart [id=" + id + ", stateCode=" + stateCode + ", commodityID=" + commodityID
				+ ", varietyID=" + varietyID + ", standardQuantityPerAcre=" + standardQuantityPerAcre
				+ ", standardPositiveVariancePerAcre=" + standardPositiveVariancePerAcre
				+ ", standardPositiveVariancePercent=" + standardPositiveVariancePercent
				+ ", standardNegativeVariancePerAcre=" + standardNegativeVariancePerAcre
				+ ", standardNegativeVariancePercent=" + standardNegativeVariancePercent + ", status=" + status + "]";
	}

	public StandardQuantityChart(Integer id, Integer stateCode, Integer commodityID, Integer varietyID,
			Double standardQuantityPerAcre, Double standardPositiveVariancePerAcre,
			Double standardPositiveVariancePercent, Double standardNegativeVariancePerAcre,
			Double standardNegativeVariancePercent, Integer status) {
		super();
		this.id = id;
		this.stateCode = stateCode;
		this.commodityID = commodityID;
		this.varietyID = varietyID;
		this.standardQuantityPerAcre = standardQuantityPerAcre;
		this.standardPositiveVariancePerAcre = standardPositiveVariancePerAcre;
		this.standardPositiveVariancePercent = standardPositiveVariancePercent;
		this.standardNegativeVariancePerAcre = standardNegativeVariancePerAcre;
		this.standardNegativeVariancePercent = standardNegativeVariancePercent;
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityID == null) ? 0 : commodityID.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((standardNegativeVariancePerAcre == null) ? 0 : standardNegativeVariancePerAcre.hashCode());
		result = prime * result
				+ ((standardNegativeVariancePercent == null) ? 0 : standardNegativeVariancePercent.hashCode());
		result = prime * result
				+ ((standardPositiveVariancePerAcre == null) ? 0 : standardPositiveVariancePerAcre.hashCode());
		result = prime * result
				+ ((standardPositiveVariancePercent == null) ? 0 : standardPositiveVariancePercent.hashCode());
		result = prime * result + ((standardQuantityPerAcre == null) ? 0 : standardQuantityPerAcre.hashCode());
		result = prime * result + ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((varietyID == null) ? 0 : varietyID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StandardQuantityChart other = (StandardQuantityChart) obj;
		if (commodityID == null) {
			if (other.commodityID != null)
				return false;
		} else if (!commodityID.equals(other.commodityID))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (standardNegativeVariancePerAcre == null) {
			if (other.standardNegativeVariancePerAcre != null)
				return false;
		} else if (!standardNegativeVariancePerAcre.equals(other.standardNegativeVariancePerAcre))
			return false;
		if (standardNegativeVariancePercent == null) {
			if (other.standardNegativeVariancePercent != null)
				return false;
		} else if (!standardNegativeVariancePercent.equals(other.standardNegativeVariancePercent))
			return false;
		if (standardPositiveVariancePerAcre == null) {
			if (other.standardPositiveVariancePerAcre != null)
				return false;
		} else if (!standardPositiveVariancePerAcre.equals(other.standardPositiveVariancePerAcre))
			return false;
		if (standardPositiveVariancePercent == null) {
			if (other.standardPositiveVariancePercent != null)
				return false;
		} else if (!standardPositiveVariancePercent.equals(other.standardPositiveVariancePercent))
			return false;
		if (standardQuantityPerAcre == null) {
			if (other.standardQuantityPerAcre != null)
				return false;
		} else if (!standardQuantityPerAcre.equals(other.standardQuantityPerAcre))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (varietyID == null) {
			if (other.varietyID != null)
				return false;
		} else if (!varietyID.equals(other.varietyID))
			return false;
		return true;
	}

}
