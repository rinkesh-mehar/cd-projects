package com.drkrishi.rlt.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Table(name = "subtask")
@Entity
public class Subtask {

    @Id
    private String id;

    @Column(name = "task_id")
    private String taskId;

    @Column(name = "kyc_is_verified")
    private Boolean kycIsVerified;

    @Column(name = "kml_is_verified")
    private Boolean kmlIsVerified;

    @Column(name = "image_is_verified")
    private Boolean imageIsVerified;

    @Column(name = "bank_is_verified")
    private Boolean bankIsVerified;

    @Column(name = "rlm_approval_is_verified")
    private Boolean rlmApprovalIsVerified;

    @Column(name = "rlt_sample_is_verified")
    private Boolean rltSampleIsVerified;

    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createAt) {
        this.createdAt = createAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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

    public Boolean getKycIsVerified() {
        return kycIsVerified;
    }

    public void setKycIsVerified(Boolean kycIsVerified) {
        this.kycIsVerified = kycIsVerified;
    }

    public Boolean getKmlIsVerified() {
        return kmlIsVerified;
    }

    public void setKmlIsVerified(Boolean kmlIsVerified) {
        this.kmlIsVerified = kmlIsVerified;
    }

    public Boolean getImageIsVerified() {
        return imageIsVerified;
    }

    public void setImageIsVerified(Boolean imageIsVerified) {
        this.imageIsVerified = imageIsVerified;
    }

    public Boolean getBankIsVerified() {
        return bankIsVerified;
    }

    public void setBankIsVerified(Boolean bankIsVerified) {
        this.bankIsVerified = bankIsVerified;
    }

    public Boolean getRlmApprovalIsVerified() {
        return rlmApprovalIsVerified;
    }

    public void setRlmApprovalIsVerified(Boolean rlmApprovalIsVerified) {
        this.rlmApprovalIsVerified = rlmApprovalIsVerified;
    }

    public Boolean getRltSampleIsVerified() {
        return rltSampleIsVerified;
    }

    public void setRltSampleIsVerified(Boolean rltSampleIsVerified) {
        this.rltSampleIsVerified = rltSampleIsVerified;
    }
}
