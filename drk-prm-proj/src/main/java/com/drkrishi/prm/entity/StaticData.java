package com.drkrishi.prm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="static_data")
public class StaticData {

    @Id
    private Integer id;
    
    private String dataKey;
    
    private String dataValue;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDataKey() {
		return dataKey;
	}

	public void setDataKey(String dataKey) {
		this.dataKey = dataKey;
	}

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}
}
