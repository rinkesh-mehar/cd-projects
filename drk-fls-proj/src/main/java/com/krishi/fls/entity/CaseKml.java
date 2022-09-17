// Generated with g9.

package com.krishi.fls.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="case_kml")
public class CaseKml {
	private static final SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    @Id
    @Column(name="id")
    private String id;
    @Column(name="kml_file_path")
    private String kmlFilePath;
    @Column(name="farm_case_id")
    private String farmCaseId;
    @Column(name="captured_on")
    private Date capturedOn;
    @Column(name="captured_by")
    private int capturedBy;

  
    public String getId() {
        return id;
    }

   
    public void setId(String aId) {
        id = aId;
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

    
    public Date getCapturedOn() {
        return capturedOn;
    }

    
    public void setCapturedOn(Date aCapturedOn) {
        capturedOn = aCapturedOn;
    }
    
    public void setCapturedOn(String aCapturedOn) {
    	try {
			this.capturedOn=new Date(sd.parse(aCapturedOn).getTime());
		} catch (ParseException e) {
		}
    }

    
    public int getCapturedBy() {
        return capturedBy;
    }

    
    public void setCapturedBy(int aCapturedBy) {
        capturedBy = aCapturedBy;
    }

}
