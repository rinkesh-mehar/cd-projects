package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity(name = "agri_stress_control_recommendation")
@JsonIgnoreProperties
public class AgriStressControlRecommendation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "StateCode")
	private Integer stateCode;
	
	@Column(name = "DistrictCode")
	private Integer districtCode;

	@Column(name = "CommodityID")
	private Integer commodityId;

	@Transient
	private String commodity;

	@Column(name = "StressControlMeasureID")
	private Integer stressControlMeasureId;

//	@Column(name = "StressTypeID")
//	private Integer stressTypeId;

	@Column(name = "StressID")
	private Integer stressId;
	
	@Column(name = "RecomendationID")
	private Integer recomendationID;

//	@Column(name = "Instructions")
//	private String instructions;
	
	@Column(name = "AgrochemicalInstructionID")
	private Integer agrochemicalInstructionID;
	
	@Column(name = "AgroChemicalInstructions")
	private String agroChemicalInstructions;

	@Column(name = "AgrochemicalID")
	private Integer agrochemicalId;

	@Column(name = "DosePerHectare")
	private String dosePerHectare;

	@Column(name = "PerHectareUomID")
	private Integer  perHectareUomId;

	@Column(name = "DosePerAcre")
	private String dosePerAcre;

	@Column(name = "PerAcreUomID")
	private Integer perAcreUomId;

	@Column(name = "WaterPerHectare")
	private String waterPerHectare;

	@Column(name = "PerHectareWaterUomID")
	private Integer perHectareWaterUomId;

	@Column(name = "WaterPerAcre")
	private String waterPerAcre;

	@Column(name = "PerAcreWaterUomID")
	private Integer perAcreWaterUomId;

	@Column(name = "AgrochemApplicationID")
	private Integer agrochemApplicationId;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
