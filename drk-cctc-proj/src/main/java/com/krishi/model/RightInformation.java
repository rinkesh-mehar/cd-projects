package com.krishi.model;

import com.krishi.vo.ViewFieldMonitoringDetailVO;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RightInformation {

    private Integer currentQuantity;
    private String harvestDate;
    private Integer allowableVarianceQtyPos;
    private Integer allowableVarianceQtyNeg;
    private String harvestWeek;

    public RightInformation() {
        super();
    }

    public RightInformation(ViewFieldMonitoringDetailVO viewFieldMonitoringDetailVO) {
        this.currentQuantity = viewFieldMonitoringDetailVO.getCurrentQuantity();
        this.harvestDate = viewFieldMonitoringDetailVO.getHarvestDate();
        this.allowableVarianceQtyPos = viewFieldMonitoringDetailVO.getAllowableVarianceQtyPos();
        this.allowableVarianceQtyNeg = viewFieldMonitoringDetailVO.getAllowableVarianceQtyNeg();
        if(viewFieldMonitoringDetailVO.getHarvestWeek() != null && viewFieldMonitoringDetailVO.getHarvestYear() != null) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, viewFieldMonitoringDetailVO.getHarvestYear());
            c.set(Calendar.WEEK_OF_YEAR, viewFieldMonitoringDetailVO.getHarvestWeek()+1);
            int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
            c.add(Calendar.DATE, -i - 7);
            c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            Date start = c.getTime();
            c.add(Calendar.DATE, 6);
            Date end = c.getTime();
            SimpleDateFormat format = new SimpleDateFormat("dd MMM");
            this.harvestWeek = viewFieldMonitoringDetailVO.getHarvestWeek()+" ("+format.format(start) + " - " + format.format(end)+")";
        }
    }

    public String getHarvestWeek() {
        return harvestWeek;
    }

    public void setHarvestWeek(String harvestWeek) {
        this.harvestWeek = harvestWeek;
    }

    public Integer getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Integer currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

    public String getHarvestDate() {
        return harvestDate;
    }

    public void setHarvestDate(String harvestDate) {
        this.harvestDate = harvestDate;
    }

    public Integer getAllowableVarianceQtyPos() {
        return allowableVarianceQtyPos;
    }

    public void setAllowableVarianceQtyPos(Integer allowableVarianceQtyPos) {
        this.allowableVarianceQtyPos = allowableVarianceQtyPos;
    }

    public Integer getAllowableVarianceQtyNeg() {
        return allowableVarianceQtyNeg;
    }

    public void setAllowableVarianceQtyNeg(Integer allowableVarianceQtyNeg) {
        this.allowableVarianceQtyNeg = allowableVarianceQtyNeg;
    }
}
