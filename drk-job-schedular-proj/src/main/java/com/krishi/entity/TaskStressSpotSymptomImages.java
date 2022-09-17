package com.krishi.entity;

import com.krishi.model.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author RinkeshKM
 * @project JobScheduler
 * @created 04/09/2021 - 4:55 PM
 */

@Entity
@Table(name = "task_stress_spot_symptom_images")
public class TaskStressSpotSymptomImages implements Serializable, EntityModel {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "task_spot_stress_symptom_id")
    private String taskSpotStressSymptomId;

    @Column(name = "side")
    private String side;

    @Column(name = "incidence")
    private Integer incidence;

    @Column(name = "severity")
    private Integer severity;

    @Column(name = "benchmark")
    private Integer benchmark;

    @Column(name = "image_url")
    private String imageUrl;


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTaskSpotStressSymptomId() {
        return taskSpotStressSymptomId;
    }

    public void setTaskSpotStressSymptomId(String taskSpotStressSymptomId) {
        this.taskSpotStressSymptomId = taskSpotStressSymptomId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public Integer getIncidence() {
        return incidence;
    }

    public void setIncidence(Integer incidence) {
        this.incidence = incidence;
    }

    public Integer getSeverity() {
        return severity;
    }

    public void setSeverity(Integer severity) {
        this.severity = severity;
    }

    public Integer getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(Integer benchmark) {
        this.benchmark = benchmark;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
