package com.drkrishi.iqa.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotModel {

    private String spotId;
    private int isHealthImage;
//    private List<SpotStressModel> spotStressModels;
    private List<StressModel> stressModelList;

    private List<SpotHealthModel> spotHealthModels;
}
