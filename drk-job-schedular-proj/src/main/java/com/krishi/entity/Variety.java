package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "variety")
public class Variety {

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "name")
	private String name;

	@Column(name = "commodity_id")
	private Integer commodityId;

	@Column(name = "hscode")
	private String hscode;

	@Column(name = "status")
	private Integer status;

	@Column(name = "uom")
	private String uom;
	
	@Column(name = "domestic_restrictions")
	private String domesticRestrictions;
	
	@Column(name = "international_restrictions")
	private String internationalRestrictions;
	
	@Column(name = "parent_variety_id")
	private Integer parentId;
	
	
	public Variety() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getCommodityId() {
		return commodityId;
	}


	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}


	public String getHscode() {
		return hscode;
	}


	public void setHscode(String hscode) {
		this.hscode = hscode;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getUom() {
		return uom;
	}


	public void setUom(String uom) {
		this.uom = uom;
	}


	public String getDomesticRestrictions() {
		return domesticRestrictions;
	}


	public void setDomesticRestrictions(String domesticRestrictions) {
		this.domesticRestrictions = domesticRestrictions;
	}


	public String getInternationalRestrictions() {
		return internationalRestrictions;
	}


	public void setInternationalRestrictions(String internationalRestrictions) {
		this.internationalRestrictions = internationalRestrictions;
	}
	
	


	


	/**
	 * @return the parentId
	 */
	public Integer getParentId() {
		return parentId;
	}


	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityId == null) ? 0 : commodityId.hashCode());
		result = prime * result + ((domesticRestrictions == null) ? 0 : domesticRestrictions.hashCode());
		result = prime * result + ((hscode == null) ? 0 : hscode.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((internationalRestrictions == null) ? 0 : internationalRestrictions.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((uom == null) ? 0 : uom.hashCode());
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
		Variety other = (Variety) obj;
		if (commodityId == null) {
			if (other.commodityId != null)
				return false;
		} else if (!commodityId.equals(other.commodityId))
			return false;
		if (domesticRestrictions == null) {
			if (other.domesticRestrictions != null)
				return false;
		} else if (!domesticRestrictions.equals(other.domesticRestrictions))
			return false;
		if (hscode == null) {
			if (other.hscode != null)
				return false;
		} else if (!hscode.equals(other.hscode))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (internationalRestrictions == null) {
			if (other.internationalRestrictions != null)
				return false;
		} else if (!internationalRestrictions.equals(other.internationalRestrictions))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (uom == null) {
			if (other.uom != null)
				return false;
		} else if (!uom.equals(other.uom))
			return false;
		return true;
	}

	

}
