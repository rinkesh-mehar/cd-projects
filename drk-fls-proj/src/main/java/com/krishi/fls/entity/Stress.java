package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="stress")
public class Stress {

	@Id
	@Column(unique = true, nullable = false, precision = 10)
	private int id;

	@Column(length = 255)
	private String name;

	@Column(name = "stress_id")
	private Integer stressId;
//	private String sciName;

	@Column(name = "start_das")
	private Integer startDas;

	@Column(name = "end_das")
	private Integer endDas;

	@Column(name = "commodity_id", precision = 10)
	private Integer commodityId;

	@Column(name = "stress_type_id", precision = 10)
	private Integer stressTypeId;

	private Integer status;
	
	/** after acz introduce -CDT-Ujwal*/
	@Column(name = "acz_id")
	private Integer aczId;
	
	@Column(name = "sowing_start_week")
	private Integer sowingWeekStart;
	
	@Column(name = "sowing_end_week")
	private Integer sowingWeekEnd;

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
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
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getSciName() {
//		return sciName;
//	}
//
//	public void setSciName(String sciName) {
//		this.sciName = sciName;
//	}

	public Integer getCommodityId() {
		return commodityId;
	}

	public Integer getStartDas() {
		return startDas;
	}

	public void setStartDas(Integer startDas) {
		this.startDas = startDas;
	}

	public Integer getEndDas() {
		return endDas;
	}

	public void setEndDas(Integer endDas) {
		this.endDas = endDas;
	}

	public void setCommodityId(Integer commodityId) {
		this.commodityId = commodityId;
	}

	public Integer getStressTypeId() {
		return stressTypeId;
	}

	public void setStressTypeId(Integer stressTypeId) {
		this.stressTypeId = stressTypeId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStressId() {
		return stressId;
	}

	public void setStressId(Integer stressId) {
		this.stressId = stressId;
	}
    
    
}
