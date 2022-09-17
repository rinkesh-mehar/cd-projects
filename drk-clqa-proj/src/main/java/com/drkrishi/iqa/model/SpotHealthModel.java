package com.drkrishi.iqa.model;

import lombok.Data;

import java.util.List;

@Data
public class SpotHealthModel {
     private String spotId;
     private List<BenchmarkedImageModel> healthImageModelList;
}
