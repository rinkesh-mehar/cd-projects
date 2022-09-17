package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="variety")
public class Variety {
	
	@Id
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
	
	private String name;
	
	@Column(name = "commodity_id")
	private Integer commodityId;
	
	private String hscode;
	
	private Integer status;	

	public Variety() {
		super();
	}

	public Variety(Integer id, String name, Integer commodityId, String hscode, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.commodityId = commodityId;
		this.hscode = hscode;
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

}
