// Generated with g9.

package com.krishi.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

@Entity
@Table(name="case_kml")
public class CaseKml implements EntityModel {

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
    
    /** added kml_url - CDT-Ujwal :Start */
    @Column(name = "kml_url")
    private String kmlUrl;
    
    

    public String getKmlUrl() {
		return kmlUrl;
	}

	public void setKmlUrl(String kmlUrl) {
		this.kmlUrl = kmlUrl;
	}
	/** added kml_url - CDT-Ujwal :End */
	
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

    public void setFarmCaseId(String aFarmCaseId) {
        farmCaseId = aFarmCaseId;
    }

    public Timestamp getCapturedOn() {
        return capturedOn;
    }
  
    public void setCapturedOn(Timestamp aCapturedOn) {
        capturedOn = aCapturedOn;
    }

    public void setCapturedOn(String aCapturedOn) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            capturedOn = new Timestamp(format.parse(aCapturedOn).getTime());
        } catch (Exception e){}
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
