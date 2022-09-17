package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "subtask")
public class SubTask{

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
    @Column(name = "rlm_approval_is_verified")
    private int rlmApprovalVerified;
    @Column(name = "rlt_sample_is_verified")
    private int rltSampleVerified;

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

    public int isKycIsVerified() {
        return kycIsVerified;
    }

    public void setKycIsVerified(int kycIsVerified) {
        this.kycIsVerified = kycIsVerified;
    }

    public int isKmlIsVerified() {
        return kmlIsVerified;
    }

    public void setKmlIsVerified(int kmlIsVerified) {
        this.kmlIsVerified = kmlIsVerified;
    }

    public int isImageIsVerified() {
        return imageIsVerified;
    }

    public void setImageIsVerified(int imageIsVerified) {
        this.imageIsVerified = imageIsVerified;
    }

    public int isBankIsVerified() {
        return bankIsVerified;
    }

    public void setBankIsVerified(int bankIsVerified) {
        this.bankIsVerified = bankIsVerified;
    }

    public int getRlmApprovalVerified()
    {
        return rlmApprovalVerified;
    }

    public void setRlmApprovalVerified(int rlmApprovalVerified)
    {
        this.rlmApprovalVerified = rlmApprovalVerified;
    }

    public int getRltSampleVerified()
    {
        return rltSampleVerified;
    }

    public void setRltSampleVerified(int rltSampleVerified)
    {
        this.rltSampleVerified = rltSampleVerified;
    }
}
