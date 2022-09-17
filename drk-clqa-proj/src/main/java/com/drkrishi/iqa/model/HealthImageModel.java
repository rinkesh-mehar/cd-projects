package com.drkrishi.iqa.model;

public class HealthImageModel {
    private String id;
    private String taskId;
    private String leftPhoto;
    private String rightPhoto;
    private String frontPhoto;
    private String spotId;
    private Integer commodityId;

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

    public String getLeftPhoto() {
        return leftPhoto;
    }

    public void setLeftPhoto(String leftPhoto) {
        this.leftPhoto = leftPhoto;
    }

    public String getRightPhoto() {
        return rightPhoto;
    }

    public void setRightPhoto(String rightPhoto) {
        this.rightPhoto = rightPhoto;
    }

    public String getFrontPhoto() {
        return frontPhoto;
    }

    public void setFrontPhoto(String frontPhoto) {
        this.frontPhoto = frontPhoto;
    }

    public String getSpotId() {
        return spotId;
    }

    public void setSpotId(String spotId) {
        this.spotId = spotId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }
}
