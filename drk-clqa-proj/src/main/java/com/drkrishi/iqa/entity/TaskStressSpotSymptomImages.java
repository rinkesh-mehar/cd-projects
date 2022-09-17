package com.drkrishi.iqa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "task_stress_spot_symptom_images")
public class TaskStressSpotSymptomImages {

    @Id

    @Column(name = "id")
    private String id;

    @Column(name = "task_spot_stress_symptom_id")
    private String taskSpotStressSymptomId;

    @Column(name = "side")
    private String side;

    @Column(name = "incidence")
    private String incidence;

    @Column(name = "severity")
    private Integer severity;

    @Column(name = "benchmark")
    private Integer benchmark;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status")
    private String status;

}
