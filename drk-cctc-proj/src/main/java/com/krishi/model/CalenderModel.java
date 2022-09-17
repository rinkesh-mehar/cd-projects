package com.krishi.model;

import com.krishi.vo.ViewFieldMonitoringDetailVO;
import lombok.Data;

import java.sql.Date;

@Data
public class CalenderModel {

    private Date minDate;

    private Date maxDate;

    public CalenderModel(ViewFieldMonitoringDetailVO detail){
        this.minDate = detail.getMinDate();
        this.maxDate = detail.getMaxDate();
    }
}
