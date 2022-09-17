package com.krishi.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="panchayat")
public class Panchayat {

    @Id
    private Integer id;

    private Integer tehsilId;

    private String name;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTehsilId() {
		return tehsilId;
	}

	public void setTehsilId(Integer tehsilId) {
		this.tehsilId = tehsilId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



}
