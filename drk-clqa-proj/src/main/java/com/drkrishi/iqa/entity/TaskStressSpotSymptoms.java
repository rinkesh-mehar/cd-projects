package com.drkrishi.iqa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "task_stress_spot_symptoms")
public class TaskStressSpotSymptoms {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "task_spot_stress_id")
    private String taskSpotStressId;
    @Column(name = "symptom_id")
    private String symptomId;

}
