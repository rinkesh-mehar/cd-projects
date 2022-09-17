package com.krishi.model;

import com.krishi.vo.ViewFieldMonitoringDetailVO;

public class ContactInformation {

    private String stateName;
    private String districtName;
    private String tehsilName;
    private String villageName;
    private String regionName;
    private String farmerName;

    public ContactInformation() {
        super();
    }

    public ContactInformation(ViewFieldMonitoringDetailVO viewFieldMonitoringDetailVO) {
        this.stateName = viewFieldMonitoringDetailVO.getStateName();
        this.districtName = viewFieldMonitoringDetailVO.getDistrictName();
        this.tehsilName = viewFieldMonitoringDetailVO.getTehsilName();
        this.villageName = viewFieldMonitoringDetailVO.getVillageName();
        this.regionName = viewFieldMonitoringDetailVO.getRegionName();
        this.farmerName = viewFieldMonitoringDetailVO.getFarmerName();
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getTehsilName() {
        return tehsilName;
    }

    public void setTehsilName(String tehsilName) {
        this.tehsilName = tehsilName;
    }

    public String getVillageName() {
        return villageName;
    }

    public void setVillageName(String villageName) {
        this.villageName = villageName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getFarmerName() {
        return farmerName;
    }

    public void setFarmerName(String farmerName) {
        this.farmerName = farmerName;
    }
}
