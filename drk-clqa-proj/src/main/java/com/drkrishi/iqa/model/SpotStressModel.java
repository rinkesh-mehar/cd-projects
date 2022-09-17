package com.drkrishi.iqa.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotStressModel {

    private String spotId;
    private List<StressModel> stressModelList;
}
