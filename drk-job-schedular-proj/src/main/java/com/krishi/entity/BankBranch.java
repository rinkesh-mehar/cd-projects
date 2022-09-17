package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author CDT - Pranay
 */

@Entity
@Table(name = "general_bank_branch")
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankBranch implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	@JsonProperty("ID")
	private Integer id;

	@Column(name = "district_id")
	@JsonProperty("DistrictCode")
	private Integer districtId;

	@Column(name = "bank_id")
	@JsonProperty("BankID")
	private Integer bankId;

	@JsonProperty("Name")
	private String name;

	@JsonProperty("IFSCCode")
	private String ifsc;

	@JsonProperty("Status")
	private Integer status;

	public BankBranch() {
		super();
	}

	public BankBranch(Integer id, Integer districtId, Integer bankId, String name, String ifsc, Integer status) {
		super();
		this.id = id;
		this.districtId = districtId;
		this.bankId = bankId;
		this.name = name;
		this.ifsc = ifsc;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Integer districtId) {
		this.districtId = districtId;
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
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
		return "BankBranch [id=" + id + ", districtId=" + districtId + ", bankId=" + bankId + ", name=" + name
				+ ", ifsc=" + ifsc + ", status=" + status + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bankId == null) ? 0 : bankId.hashCode());
		result = prime * result + ((districtId == null) ? 0 : districtId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ifsc == null) ? 0 : ifsc.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		BankBranch other = (BankBranch) obj;
		if (bankId == null) {
			if (other.bankId != null)
				return false;
		} else if (!bankId.equals(other.bankId))
			return false;
		if (districtId == null) {
			if (other.districtId != null)
				return false;
		} else if (!districtId.equals(other.districtId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ifsc == null) {
			if (other.ifsc != null)
				return false;
		} else if (!ifsc.equals(other.ifsc))
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
		return true;
	}
	
	

}
