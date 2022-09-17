package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="district")
public class District {

    @Id
    private Integer id;

    private String name;

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

}
