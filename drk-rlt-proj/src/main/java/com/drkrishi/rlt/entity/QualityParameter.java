package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="quality_parameter")
public class QualityParameter {

	@Id
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;

	private String name;

	private Integer status;

	public QualityParameter() {
		super();
	}

	public QualityParameter(Integer id, String name, Integer commodityId, String hscode, Integer status) {
		super();
		this.id = id;
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

}
