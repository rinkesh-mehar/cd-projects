package com.krishi.fls.model;

import java.util.List;

import com.krishi.fls.entity.HealthQuestionnaire;
import com.krishi.fls.entity.MbepEntity;
import com.krishi.fls.entity.Phenophase;
import com.krishi.fls.entity.PhenophaseDuration;
import com.krishi.fls.entity.PlantPart;
import com.krishi.fls.entity.StaticData;
import com.krishi.fls.entity.Stress;
import com.krishi.fls.entity.StressControlMeasure;
import com.krishi.fls.entity.StressSeverity;
import com.krishi.fls.entity.StressSeverityControlMeasures;
import com.krishi.fls.entity.StressSymptoms;
import com.krishi.fls.entity.StressType;
import com.krishi.fls.entity.TileZL11;
import com.krishi.fls.entity.VarietyQuality;

public class MasterData {
	
	private List<Phenophase> phenophaseList;
	
	private List<Stress> stressList;
	
	private List<StressType> stressTypeList;
	
	private List<PlantPart> plantPartList;

	private List<StressSymptoms> stressSymptomsList;
	
	private List<StressSeverity> stressSeverity;
	
	private List<HealthQuestionnaire> healthQuestionarie;
	
	private List<PhenophaseDuration> phenophaseDurationList;
	
	private List<StressControlMeasure>  stressControlMeasure;
	
	private List<StressSeverityControlMeasures> stressSeverityControlMeasure;
	
	private List<StaticData> staticData;
	
	private List<VarietyQuality> varietyQualities;
	
	private List<MbepEntity> mbep;
	
	/**added newlist for fls day start -CDT-Ujwal */
	private List<TileZL11> tileZL11;

	public List<Phenophase> getPhenophaseList() {
		return phenophaseList;
	}

	public void setPhenophaseList(List<Phenophase> phenophaseList) {
		this.phenophaseList = phenophaseList;
	}

	public List<Stress> getStressList() {
		return stressList;
	}

	public void setStressList(List<Stress> stressList) {
		this.stressList = stressList;
	}

	public List<StressType> getStressTypeList() {
		return stressTypeList;
	}

	public void setStressTypeList(List<StressType> stressTypeList) {
		this.stressTypeList = stressTypeList;
	}

	public List<PlantPart> getPlantPartList() {
		return plantPartList;
	}

	public void setPlantPartList(List<PlantPart> plantPartList) {
		this.plantPartList = plantPartList;
	}

	public List<StressSymptoms> getStressSymptomsList() {
		return stressSymptomsList;
	}

	public void setStressSymptomsList(List<StressSymptoms> stressSymptomsList) {
		this.stressSymptomsList = stressSymptomsList;
	}

	public List<StressSeverity> getStressSeverity() {
		return stressSeverity;
	}

	public void setStressSeverity(List<StressSeverity> stressSeverity) {
		this.stressSeverity = stressSeverity;
	}

	public List<HealthQuestionnaire> getHealthQuestionarie() {
		return healthQuestionarie;
	}

	public void setHealthQuestionarie(List<HealthQuestionnaire> healthQuestionarie) {
		this.healthQuestionarie = healthQuestionarie;
	}

//	public List<PhenophaseDuration> getPhenophaseDurationList() {
//		return phenophaseDurationList;
//	}
//
//	public void setPhenophaseDurationList(List<PhenophaseDuration> phenophaseDurationList) {
//		this.phenophaseDurationList = phenophaseDurationList;
//	}

	public List<StressControlMeasure> getStressControlMeasure() {
		return stressControlMeasure;
	}

	public void setStressControlMeasure(List<StressControlMeasure> stressControlMeasure) {
		this.stressControlMeasure = stressControlMeasure;
	}

	public List<StressSeverityControlMeasures> getStressSeverityControlMeasure() {
		return stressSeverityControlMeasure;
	}

	public void setStressSeverityControlMeasure(List<StressSeverityControlMeasures> stressSeverityControlMeasure) {
		this.stressSeverityControlMeasure = stressSeverityControlMeasure;
	}

	public List<StaticData> getStaticData() {
		return staticData;
	}

	public void setStaticData(List<StaticData> staticData) {
		this.staticData = staticData;
	}

	public List<VarietyQuality> getVarietyQualities() {
		return varietyQualities;
	}

	public void setVarietyQualities(List<VarietyQuality> varietyQualities) {
		this.varietyQualities = varietyQualities;
	}

	public List<MbepEntity> getMbep() {
		return mbep;
	}

	public void setMbep(List<MbepEntity> mbep) {
		this.mbep = mbep;
	}

	public List<TileZL11> getTileZL11() {
		return tileZL11;
	}

	public void setTileZL11(List<TileZL11> tileZL11) {
		this.tileZL11 = tileZL11;
	}
	
	
}
