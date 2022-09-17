package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_stress_spot_symptoms")
public class TaskStressSpotSymptom {

    @Id
    @Column(unique = true, nullable = false, precision = 10)
    private String id;

    @Column(name = "task_spot_stress_id")
    private String taskSpotStressId;

    @Column(name = "symptom_id")
    private Integer  symptomId;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskSpotStressId() {
        return taskSpotStressId;
    }

    public void setTaskSpotStressId(String taskSpotStressId) {
        this.taskSpotStressId = taskSpotStressId;
    }

    public Integer getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Integer symptomId) {
        this.symptomId = symptomId;
    }
}
