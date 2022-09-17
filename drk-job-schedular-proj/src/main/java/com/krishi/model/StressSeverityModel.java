package com.krishi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.krishi.entity.StressSeverity;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StressSeverityModel {

  @JsonProperty
  private Integer ID;

  @JsonProperty
  private String Name;

  @JsonProperty
  private Double Value;

  @JsonProperty
  private String Status;

  public Integer getID() {
    return ID;
  }

  public void setID(Integer iD) {
    ID = iD;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public Double getValue() {
    return Value;
  }

  public void setValue(Double value) {
    Value = value;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }

  public StressSeverity getEntity() {

    StressSeverity entity = new StressSeverity();
    entity.setId(ID);
    entity.setName(Name);
    entity
        .setStatus(Status != null && Status.equalsIgnoreCase("Active") ? 1 : 0);
    entity.setValue(Value);

    return entity;

  }
}