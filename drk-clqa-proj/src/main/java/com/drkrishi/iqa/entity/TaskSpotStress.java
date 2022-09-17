package com.drkrishi.iqa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "task_spot_stress")
public class TaskSpotStress {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "task_spot_id")
    private String taskSpotId;

    @Column(name = "stress_id")
    private Integer stressId;

}
