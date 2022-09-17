package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="season")
public class Season {
	
	@Id
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
	
	private String name;
	
	private String comment;
	
	private Integer status;

	public Season() {
		super();
	}

	public Season(Integer id, String name, String comment, Integer status) {
		super();
		this.id = id;
		this.name = name;
		this.comment = comment;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
