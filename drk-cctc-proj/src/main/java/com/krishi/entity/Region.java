package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="region")
public class Region {

    @Id
    private Integer id;

    private Integer stateId;
    
    private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
