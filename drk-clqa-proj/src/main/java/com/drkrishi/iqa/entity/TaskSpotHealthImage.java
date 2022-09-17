package com.drkrishi.iqa.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "task_spot_health_image")
public class TaskSpotHealthImage {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "task_spot_id")
    private String taskSpotId;

    @Column(name = "side")
    private String side;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "status")
    private String status;


}
