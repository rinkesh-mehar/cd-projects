package com.drkrishi.usermanagement.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="static_data")
public class StaticData {

    @Id
    private Integer id;
    
    @Column(name="data_key")
    private String key;
    @Column(name="data_value")
    private String value;

    
    public StaticData() {
        super();
    }


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}
    
}
