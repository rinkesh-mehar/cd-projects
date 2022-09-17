// Generated with g9.

package com.drkrishi.iqa.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="case_kml")
public class CaseKml {

    @Id
   
    @Column(unique=true)
    private String id;
    @Column(name="kml_file_path")
    private String kmlFilePath;
    @Column(name="farm_case_id")
    private String farmCaseId;
    @Column(name="captured_on")
    private Timestamp capturedOn;
    @Column(name="captured_by")
    private int capturedBy;
    
    @Column(name="is_verified")
    private Integer isVerified;
    

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getKmlFilePath() {
        return kmlFilePath;
    }

    public void setKmlFilePath(String aKmlFilePath) {
        kmlFilePath = aKmlFilePath;
    }

    public String getFarmCaseId() {
		return farmCaseId;
	}

	public void setFarmCaseId(String farmCaseId) {
		this.farmCaseId = farmCaseId;
	}

	public Timestamp getCapturedOn() {
        return capturedOn;
    }
  
    public void setCapturedOn(Timestamp aCapturedOn) {
        capturedOn = aCapturedOn;
    }
    
    public int getCapturedBy() {
        return capturedBy;
    }
   
    public void setCapturedBy(int aCapturedBy) {
        capturedBy = aCapturedBy;
    }

	public Integer getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Integer isVerified) {
		this.isVerified = isVerified;
	}

    
}
