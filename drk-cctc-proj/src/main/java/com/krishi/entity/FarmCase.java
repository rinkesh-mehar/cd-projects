// Generated with g9.

package com.krishi.entity;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="farm_case")
public class FarmCase {


    @Id
    private String id;

    private String farmId;
    
    private Date nmd;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFarmId() {
		return farmId;
	}

	public void setFarmId(String farmId) {
		this.farmId = farmId;
	}

	public Date getNmd() {
		return nmd;
	}

	public void setNmd(Date nmd) {
		this.nmd = nmd;
	}

}
