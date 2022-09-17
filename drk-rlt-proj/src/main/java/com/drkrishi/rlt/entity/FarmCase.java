// Generated with g9.

package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farm_case")
public class FarmCase {

    @Id
    private String id;
    @Column(name="farm_id")
    private String farmId;
    
    @Column(name="nmd")
    private Date nmd;
    
    @Column(name="case_sample_status")
    private String caseSampleStatus;

      
    public String getCaseSampleStatus() {
		return caseSampleStatus;
	}

	public void setCaseSampleStatus(String caseSampleStatus) {
		this.caseSampleStatus = caseSampleStatus;
	}

    public String getId() {
        return id;
    }

    public void setId(String aId) {
        id = aId;
    }

    public String getFarmId() {
        return farmId;
    }

    public void setFarmId(String aFarmId) {
        farmId = aFarmId;
    }

	public Date getNmd() {
		return nmd;
	}

	public void setNmd(Date nmd) {
		this.nmd = nmd;
	}

}
