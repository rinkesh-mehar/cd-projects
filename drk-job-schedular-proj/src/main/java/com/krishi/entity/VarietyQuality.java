package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "variety_quality")
public class VarietyQuality {

	@Id
	private Integer id;

	@Column(name = "acz_id")
    private String aczId;

	@Column(name = "variety_id")
	private Integer varietyId;
	
	@Column(name = "current_quality_id")
	private Integer currentQualityId;

	@Column(name = "current_quality")
	private String currentQuality;

	@Column(name = "estimated_quality")
	private String estimatedQuality;

	@Column(name = "allowable_variance_in_quality")
	private String allowableVarianceInQuality;

	@Column(name = "state_code")
	private Integer stateCode;

	@Column(name = "region_id")
	private Integer regionId;
	
	@Column(name = "commodity_id")
	private Integer commodityId;

	@Column(name = "sowing_start_week")
	private Integer sowingWeekStart;

	@Column(name = "sowing_end_week")
	private Integer sowingWeekEnd;
	
	@Column(name = "status")
	private Integer status;
	
	public Integer getId() {
        return id;
    }

	public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getSowingWeekStart() {
        return sowingWeekStart;
    }

	public void setSowingWeekStart(Integer sowingWeekStart) {
        this.sowingWeekStart = sowingWeekStart;
    }

    public Integer getSowingWeekEnd() {
        return sowingWeekEnd;
    }

    public void setSowingWeekEnd(Integer sowingWeekEnd) {
        this.sowingWeekEnd = sowingWeekEnd;
    }

    public Integer getVarietyId() {
        return varietyId;
    }

    public void setVarietyId(Integer varietyId) {
        this.varietyId = varietyId;
    }

    public String getCurrentQuality() {
        return currentQuality;
    }

    public void setCurrentQuality(String currentQuality) {
        this.currentQuality = currentQuality;
    }

    public String getEstimatedQuality() {
        return estimatedQuality;
    }

    public void setEstimatedQuality(String estimatedQuality) {
        this.estimatedQuality = estimatedQuality;
    }

    public String getAllowableVarianceInQuality() {
        return allowableVarianceInQuality;
    }

    public void setAllowableVarianceInQuality(String allowableVarianceInQuality) {
        this.allowableVarianceInQuality = allowableVarianceInQuality;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCurrentQualityId() {
        return currentQualityId;
    }

    public void setCurrentQualityId(Integer currentQualityId) {
        this.currentQualityId = currentQualityId;
    }

    public Integer getStateCode() {
        return stateCode;
    }

    public void setStateCode(Integer stateCode) {
        this.stateCode = stateCode;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Integer commodityId) {
        this.commodityId = commodityId;
    }

    public String getAczId() {
        return aczId;
    }

    public void setAczId(String aczId) {
        this.aczId = aczId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((allowableVarianceInQuality == null) ? 0 : allowableVarianceInQuality.hashCode());
        result = prime * result + ((currentQuality == null) ? 0 : currentQuality.hashCode());
        result = prime * result + ((estimatedQuality == null) ? 0 : estimatedQuality.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((varietyId == null) ? 0 : varietyId.hashCode());
        result = prime * result + ((aczId == null) ? 0 : aczId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        VarietyQuality other = (VarietyQuality) obj;
        if (allowableVarianceInQuality == null) {
            if (other.allowableVarianceInQuality != null)
                return false;
        } else if (!allowableVarianceInQuality.equals(other.allowableVarianceInQuality))
            return false;
        if (currentQuality == null) {
            if (other.currentQuality != null)
                return false;
        } else if (!currentQuality.equals(other.currentQuality))
            return false;
        if (estimatedQuality == null) {
            if (other.estimatedQuality != null)
                return false;
        } else if (!estimatedQuality.equals(other.estimatedQuality))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (status == null) {
            if (other.status != null)
                return false;
        } else if (!status.equals(other.status))
            return false;
        if (varietyId == null) {
            if (other.varietyId != null)
                return false;
        } else if (!varietyId.equals(other.varietyId))
            return false;
        if (aczId == null) {
            if (other.aczId != null)
                return false;
        } else if (!aczId.equals(other.aczId))
            return false;
        return true;
    }

}