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
@Table(name = "task_stress_spot_symptoms")
public class TaskStressSpotSymptoms implements Serializable, EntityModel {

    protected static final String PK = "id";

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "task_spot_stress_id")
    private String taskSpotStressSymptomId;

    @Column(name = "symptom_id")
    private Integer symptomId;

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

    public Integer getSymptomId() {
        return symptomId;
    }

    public void setSymptomId(Integer symptomId) {
        this.symptomId = symptomId;
    }
}
