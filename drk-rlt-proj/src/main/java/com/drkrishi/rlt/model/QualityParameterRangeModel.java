package com.drkrishi.rlt.model;

import java.util.List;

public class QualityParameterRangeModel {

    private Integer parameterId;
    private Integer CommodityId;
    private String Name;
    private String MaxValue;
    private String MinValue;
    private Integer value;
    private Integer zonalCommodityID;
    private List<AgriQualityParameterModel> agriQualityParameterList;
    private Integer sowingWeek;

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getZonalCommodityID() {
        return zonalCommodityID;
    }

    public void setZonalCommodityID(Integer zonalCommodityID) {
        this.zonalCommodityID = zonalCommodityID;
    }

    public List<AgriQualityParameterModel> getAgriQualityParameterList() {
        return agriQualityParameterList;
    }

    public void setAgriQualityParameterList(List<AgriQualityParameterModel> agriQualityParameterList) {
        this.agriQualityParameterList = agriQualityParameterList;
    }

    public Integer getSowingWeek() {
        return sowingWeek;
    }

    public void setSowingWeek(Integer sowingWeek) {
        this.sowingWeek = sowingWeek;
    }

    public Integer getParameterId() {
        return parameterId;
    }

    public void setParameterId(Integer parameterId) {
        this.parameterId = parameterId;
    }

    public Integer getCommodityId() {
        return CommodityId;
    }

    public void setCommodityId(Integer commodityId) {
        CommodityId = commodityId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(String maxValue) {
        MaxValue = maxValue;
    }

    public String getMinValue() {
        return MinValue;
    }

    public void setMinValue(String minValue) {
        MinValue = minValue;
    }
}
