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
@Table(name = "hs_code")
@JsonIgnoreProperties(ignoreUnknown = true)
public class HsCode implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "commodity_id")
	@JsonProperty("CommodityID")
	private Integer commodityID;

	@Column(name = "general_commodity_id")
	@JsonProperty("GeneralCommodityID")
	private Integer generalCommodityID;

	@Column(name = "commodity_class_id")
	@JsonProperty("CommodityClassID")
	private Integer commodityClassID;

	@Column(name = "hs_code")
	@JsonProperty("HSCode")
	private String hsCode;

	@Column(name = "uom_id")
	@JsonProperty("UomID")
	private Integer uomID;

	@Column(name = "description")
	@JsonProperty("Description")
	private String description;

	@Column(name = "status")
	@JsonProperty("Status")
	private Integer status;

	public HsCode() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommodityID() {
		return commodityID;
	}

	public void setCommodityID(Integer commodityID) {
		this.commodityID = commodityID;
	}

	public Integer getGeneralCommodityID() {
		return generalCommodityID;
	}

	public void setGeneralCommodityID(Integer generalCommodityID) {
		this.generalCommodityID = generalCommodityID;
	}

	public Integer getCommodityClassID() {
		return commodityClassID;
	}

	public void setCommodityClassID(Integer commodityClassID) {
		this.commodityClassID = commodityClassID;
	}

	public String getHsCode() {
		return hsCode;
	}

	public void setHsCode(String hsCode) {
		this.hsCode = hsCode;
	}

	public Integer getUomID() {
		return uomID;
	}

	public void setUomID(Integer uomID) {
		this.uomID = uomID;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
		return "HsCode [id=" + id + ", commodityID=" + commodityID + ", generalCommodityID=" + generalCommodityID
				+ ", commodityClassID=" + commodityClassID + ", hsCode=" + hsCode + ", uomID=" + uomID
				+ ", description=" + description + ", status=" + status + "]";
	}

	public HsCode(Integer id, Integer commodityID, Integer generalCommodityID, Integer commodityClassID, String hsCode,
			Integer uomID, String description, Integer status) {
		super();
		this.id = id;
		this.commodityID = commodityID;
		this.generalCommodityID = generalCommodityID;
		this.commodityClassID = commodityClassID;
		this.hsCode = hsCode;
		this.uomID = uomID;
		this.description = description;
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityClassID == null) ? 0 : commodityClassID.hashCode());
		result = prime * result + ((commodityID == null) ? 0 : commodityID.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((generalCommodityID == null) ? 0 : generalCommodityID.hashCode());
		result = prime * result + ((hsCode == null) ? 0 : hsCode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((uomID == null) ? 0 : uomID.hashCode());
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
		HsCode other = (HsCode) obj;
		if (commodityClassID == null) {
			if (other.commodityClassID != null)
				return false;
		} else if (!commodityClassID.equals(other.commodityClassID))
			return false;
		if (commodityID == null) {
			if (other.commodityID != null)
				return false;
		} else if (!commodityID.equals(other.commodityID))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (generalCommodityID == null) {
			if (other.generalCommodityID != null)
				return false;
		} else if (!generalCommodityID.equals(other.generalCommodityID))
			return false;
		if (hsCode == null) {
			if (other.hsCode != null)
				return false;
		} else if (!hsCode.equals(other.hsCode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (uomID == null) {
			if (other.uomID != null)
				return false;
		} else if (!uomID.equals(other.uomID))
			return false;
		return true;
	}

}
