package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author RinkeshKM
 */

@Entity
@Table(name = "proxy_relation_type")
public class ProxyRelationType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;

	private String name;

	@Column(name = "status")
	private Integer status;

	public ProxyRelationType() {
		super();
	}

	public ProxyRelationType(String name, Integer status) {
		super();
		this.name = name;
		this.status = status;
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
		return "ProxyRelationType{" +
				"id=" + id +
				", name='" + name + '\'' +
				", status=" + status +
				'}';
	}
}
