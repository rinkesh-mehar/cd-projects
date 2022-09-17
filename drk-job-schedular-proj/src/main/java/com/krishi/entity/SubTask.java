package com.krishi.entity;

import com.krishi.model.EntityModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subtask")
public class SubTask implements EntityModel {

    @Id
    private String id;

    @Column(name = "task_id")
    private String taskId;
    @Column(name = "kyc_is_verified")
    private int kycIsVerified;
    @Column(name = "kml_is_verified")
    private int kmlIsVerified;
    @Column(name = "image_is_verified")
    private int imageIsVerified;
    @Column(name = "bank_is_verified")
    private int bankIsVerified;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public int getKycIsVerified() {
        return kycIsVerified;
    }

    public void setKycIsVerified(int kycIsVerified) {
        this.kycIsVerified = kycIsVerified;
    }

    public int getKmlIsVerified() {
        return kmlIsVerified;
    }

    public void setKmlIsVerified(int kmlIsVerified) {
        this.kmlIsVerified = kmlIsVerified;
    }

    public int getImageIsVerified() {
        return imageIsVerified;
    }

    public void setImageIsVerified(int imageIsVerified) {
        this.imageIsVerified = imageIsVerified;
    }

    public int getBankIsVerified() {
        return bankIsVerified;
    }

    public void setBankIsVerified(int bankIsVerified) {
        this.bankIsVerified = bankIsVerified;
    }
}
