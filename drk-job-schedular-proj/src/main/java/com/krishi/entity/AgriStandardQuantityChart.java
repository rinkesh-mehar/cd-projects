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
@Table(name = "agri_standard_quantity_chart")
@JsonIgnoreProperties(ignoreUnknown = true)
public class AgriStandardQuantityChart implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "state_id")
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

	public AgriStandardQuantityChart() {
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

	public AgriStandardQuantityChart(Integer id, Integer commodityID, Integer generalCommodityID, Integer commodityClassID, String hsCode,
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

}
