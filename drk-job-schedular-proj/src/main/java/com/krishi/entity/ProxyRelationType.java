package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "proxy_relation_type")
public class ProxyRelationType {
	@Id
	@Column(unique = true, nullable = false, precision = 11)
	private Integer id;
	@Column(name = "name", length = 255)
	private String proxyRelationType;
	@Column(name = "status", length = 11)
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProxyRelationType() {
		return proxyRelationType;
	}

	public void setProxyRelationType(String proxyRelationType) {
		this.proxyRelationType = proxyRelationType;
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
		result = prime * result + ((proxyRelationType == null) ? 0 : proxyRelationType.hashCode());
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
		ProxyRelationType other = (ProxyRelationType) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (proxyRelationType == null) {
			if (other.proxyRelationType != null)
				return false;
		} else if (!proxyRelationType.equals(other.proxyRelationType))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}

}
