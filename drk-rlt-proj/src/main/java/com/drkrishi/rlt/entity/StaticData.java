package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="static_data")
public class StaticData {

    @Id
    @Column(name="id")
    private Integer id;
    
    @Column(name="data_key")
    private String dataKey;
    
    @Column(name="data_value")
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
