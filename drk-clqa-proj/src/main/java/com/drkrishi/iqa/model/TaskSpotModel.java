package com.drkrishi.iqa.model;

import lombok.Data;

import java.util.List;

/**
 * @author cropdata-ujwal
 * @package com.drkrishi.iqa.model
 * @date 13/09/21
 * @time 12:31 PM
 */
@Data
public class TaskSpotModel
{

    private String taskId;

   private String caseId;

    private Integer count;
    private boolean isDisable;

    private List<String> spotIds;

    private List<SpotModel> spotModels;
//    private List<SpotStressModel> spotStressModels;

//    private List<SpotHealthModel> spotHealthModels;
}
