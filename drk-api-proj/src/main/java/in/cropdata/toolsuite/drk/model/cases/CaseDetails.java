/**
 * 
 */
package in.cropdata.toolsuite.drk.model.cases;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Vivek Gajbhiiye
 *
 */
@Data
@AllArgsConstructor
@Entity(name = "case_details")
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(CaseCompositeKey.class)
public class CaseDetails implements Serializable {
	
	/**
	 * 
	 */
	public CaseDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param caseId
	 * @param harvestWeek
	 * @param correctedSowingDate
	 */
	public CaseDetails(BigInteger caseId, int harvestWeek, int currentSowingWeek) {
		super();
		this.caseId = caseId;
		this.harvestWeek = harvestWeek;
		this.currentSowingWeek = currentSowingWeek;
	}

//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "ID")
//	private Integer id;

	@Id
	@Column(name = "CaseID")
	private BigInteger caseId;

	@Id
	@Column(name = "RegionID")
	private Integer regionID;

	@Column(name = "VillageCode")
	private Integer villageCode;

	@Column(name = "FarmID")
	private BigInteger farmId;

	@Column(name = "FarmerID")
	private BigInteger farmerId;
	
	@Column(name = "RightID")
	private BigInteger rightId;

	@Column(name = "LotID")
	private Integer lotId;
	
	@Column(name = "CommodityID")
	private Integer commodityId;

	@Column(name = "VarietyID")
	private Integer varietyId;

	@Column(name = "FarmerGivenSowingWeek")
	private Integer farmerGivenSowingWeek;

	@Column(name = "FarmerGivenSowingYear")
	private Integer farmerGivenSowingYear;
	
	@Column(name = "CurrentSowingWeek")
	private Integer currentSowingWeek;
	
//	@Column(name = "SowingWeek")
//	private Integer sowingWeek;

	@Column(name = "CurrentSowingYear")
	private Integer currentSowingYear;
	
	@Column(name = "HarvestWeek")
	private Integer harvestWeek;

	@Column(name = "HarvestYear")
	private Integer harvestYear;

	@Column(name = "KmlProcessingStatus")
	private String kmlProcessingStatus;

//	@Column(name = "ErrorMessage")
//	private String errorMessage;
//
//	@Column(name = "BoundaryBox")
//	private String boundaryBox;

	@Column(name = "KmlName")
	private String kmlName;

	@Column(name = "KmlID")
	private String kmlId;

	@Column(name = "SpotGuidenceKmlID")
	private String spotGuidenceKmlID;

	@Column(name = "KmlUrl")
	private String kmlUrl;
	
//	@Column(name = "SpotGuidenceKmlUrl")
//	private String spotGuidenceKmlUrl;
	
//	@Column(name = "updatedAt")
//	private Date updatedAt;

//	@Column(name = "CorrectedSowingDate")
//	@JsonFormat(pattern = "yyyy-MM-dd")
//	private Date correctedSowingDate;

	@Column(name = "CurrentPhenophaseID")
	@JsonAlias("currentPhenophaseId")
	private Integer currentPhenophaseID;

	@Column(name = "SubRegionID")
	private Integer subRegionID;

	@Column(name = "SeasonID")
	private Integer seasonID;

//	@Column(name = "Status")
//	private String status;

	@Column(name = "DeathReason")
	private String deathReason;

	@Column(name = "SeedSourceID")
	private Integer seedSourceId;

	@JsonProperty("seedTreatmentAgentId")
	@Column(name = "SeedTreatmentID")
	private Integer seedTreatmentId;

	@Column(name = "SeedTreatment")
	private Boolean seedTreatment;

	@Column(name = "SeedRate")
	private Double seedRate;

	@Column(name = "IrrigationSourceID")
	private Integer irrigationSourceId;

	@Column(name = "StressSeverityID")
	private Integer stressSeverityId;

	@Column(name = "PaymentStatus")
	private String paymentStatus;

	@Column(name = "RightStatus")
	private String rightStatus;

	@Column(name = "PlantHealth")
	private String plantHealth;

	@Column(name = "LandAreaMisMatchPercent")
	private String landAreaMisMatchPercent;

	@Column(name = "SeedSample")
	private String seedSample;

	@Column(name = "EstimatedQuantity")
	private Double estimatedQuantity;

	@Column(name = "CurrentQuantity")
	private Double currentQuantity;

	@Column(name = "YieldPercent")
	private Double yieldPercent;

	@Column(name = "StateCode")
	private Integer stateCode;

	@Column(name = "SeedSampleReceived")
	private String seedSampleReceived;

	@Column(name = "DueAmount")
	private Double dueAmount;

	@Column(name = "CroppingArea")
	private String croppingArea;

	@Column(name = "SpacingPlant")
	private Double spacingPlant;

	@JsonAlias("irrigationMethodId")
	@Column(name = "IrrigationMethodID")
	private Integer irrigationMethodID;

	@Column(name = "NumberOfIrrigations")
	private Integer numberOfIrrigations;

	@Column(name = "WeekOfIrrigation")
	private Integer weekOfIrrigation;
	
	@Column(name = "YearOfIrrigation")
	private Integer yearOfIrrigation;

	@JsonProperty("applicationId")
	@Column(name = "AgrochemicalApplicationTypeID")
	private Integer agrochemicalApplicationTypeID; // not na in mail

	@JsonAlias("fertilizerId")
	@Column(name = "FertilizerID")
	private Integer fertilizerID;

	@Column(name = "FertilizerDose")
	private Double fertilizerDose;

	@Column(name = "FertilizerSplitDose")
	private String fertilizerSplitDose;

	@JsonAlias("fertilizerUomId")
	@Column(name = "FertilizerUomID")
	private Integer fertilizerUomID;

	@Column(name = "FertilizerWeekOfApplication")
	private Integer fertilizerWeekOfApplication;

	@Column(name = "FertilizerYearOfApplication")
	private Integer fertilizerYearOfApplication;

	@JsonAlias("seedDose")
	@Column(name = "SeedTreatmentDose")
	private Double seedTreatmentDose;

	@JsonAlias("seedUomId")
	@Column(name = "SeedTreatmentUomID")
	private Integer seedTreatmentUomID;

	@JsonAlias("agrochemicalId")
	@Column(name = "AgrochemicalID")
	private Integer agrochemicalID;

	@Column(name = "AgrochemicalBrandID")
	private Integer agrochemicalBrandID;

	@Column(name = "AgrochemicalDose")
	private Double agrochemicalDose;

	@JsonAlias("agrochemicalUomId")
	@Column(name = "AgrochemicalUomID")
	private Integer agrochemicalUomID;

	@Column(name = "AgrochemicalWeekOfApplication")
	private Integer agrochemicalWeekOfApplication;

	@Column(name = "AgrochemicalYearOfApplication")
	private Integer agrochemicalYearOfApplication;

	@Column(name = "MobileTypeID")
	private Integer mobileTypeID;

	@Column(name = "SpacingRow")
	private Double spacingRow;

//	@Column(name = "NDVIRating")
//	private Double nDVIRating;

//	@Column(name = "Rating")
//	private Integer rating;
	

}
