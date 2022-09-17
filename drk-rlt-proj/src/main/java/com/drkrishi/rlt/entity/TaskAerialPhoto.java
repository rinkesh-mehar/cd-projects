package com.drkrishi.rlt.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "task_aerial_photo")
public class TaskAerialPhoto {

    @Id
    @Column(unique = true, nullable = false, precision = 10)
    private String id;

    @Column(name = "task_id", nullable = false, precision = 10)
    private String taskId;

    @Column(name = "photos", nullable = false, precision = 10)
    private String photos;

    public TaskAerialPhoto() {
        super();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getPhotos() {
        return photos;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
    }

}
