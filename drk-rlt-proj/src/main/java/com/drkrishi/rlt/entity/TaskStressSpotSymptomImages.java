package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_stress_spot_symptom_images")
public class TaskStressSpotSymptomImages {

    @Id
    @Column(unique = true, nullable = false, precision = 10)
    private String id;

    @Column(name = "task_spot_stress_symptom_id", nullable = false, precision = 10)
    private String taskSpotStressSymptomId;

    @Column(name = "side", nullable = false, precision = 10)
    private String side;

    @Column(name = "incidence", nullable = false, precision = 10)
    private Integer incidence;

    @Column(name = "severity", nullable = false, precision = 10)
    private Integer severity;

    @Column(name = "benchmark", nullable = false, precision = 10)
    private Boolean benchmark;

    @Column(name = "image_url", nullable = false, precision = 10)
    private Integer imageURL;

    public String getId() {
        return id;
    }

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

    public Boolean getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(Boolean benchmark) {
        this.benchmark = benchmark;
    }

    public Integer getImageURL() {
        return imageURL;
    }

    public void setImageURL(Integer imageURL) {
        this.imageURL = imageURL;
    }
}
