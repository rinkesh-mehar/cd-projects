// Generated with g9.

package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="state")
public class State{

    @Id
    @Column(name="id", unique=true, nullable=false, precision=10)
    private int id;
    @Column(length=255)
    private String comment;
    @Column(name="name", length=255)
    private String name;
    @Column(precision=10)
    private int status;
    @Column(name="state_code", precision=10)
    private int stateCode;
    @Column(name="country_code", precision=10)
    private int countryCode;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getStateCode() {
		return stateCode;
	}
	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}
	public int getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(int countryCode) {
		this.countryCode = countryCode;
	}

}
