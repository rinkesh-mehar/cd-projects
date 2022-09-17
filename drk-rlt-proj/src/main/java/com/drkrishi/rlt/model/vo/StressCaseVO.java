package com.drkrishi.rlt.model.vo;

public interface StressCaseVO {

    Integer getLeftIncident();
    Integer getRightIncident();
    Integer getFrontIncident();
    String getLeftPhoto();
    String getRightPhoto();
    String getFrontPhoto();
    Integer getLeftSeverity();
    Integer getRightSeverity();
    Integer getFrontSeverity();
    Integer getIsBenchmarkedFront();
    Integer getIsBenchmarkedLeft();
    Integer getIsBenchmarkedRight();
    String getSpotId();
    Integer getSymptomDesc();
    Boolean getStressStatus();
    String getStressName();
    Integer getStressId();
    String getStressType();
    String getId();
}
