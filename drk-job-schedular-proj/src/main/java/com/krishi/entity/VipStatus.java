package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "vip_status")
public class VipStatus {

	@Id
	@Column(unique = true, nullable = false, precision = 11)
	private Integer id;

	@Column(name = "description", length = 225)
	private String vipStatus;

	@Column(name = "status")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVipStatus() {
		return vipStatus;
	}

	public void setVipStatus(String vipStatus) {
		this.vipStatus = vipStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((vipStatus == null) ? 0 : vipStatus.hashCode());
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
		VipStatus other = (VipStatus) obj;
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
		if (vipStatus == null) {
			if (other.vipStatus != null)
				return false;
		} else if (!vipStatus.equals(other.vipStatus))
			return false;
		return true;
	}

}
