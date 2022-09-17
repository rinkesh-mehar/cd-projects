package in.cropdata.farmers.app.gstmTransitory.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "case_details", schema = "gstm_transitory")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CaseDetailsEntity implements Serializable {

	private static final long serialVersionUID = -4579219215169246136L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "CaseID")
	private String caseID;

	@Column(name = "RegionID")
	private Integer regionID;

	@Column(name = "VillageCode")
	private Integer villageCode;

	@Column(name = "FarmID")
	private String farmID;

	@Column(name = "FarmerID")
	private String farmerID;

	@Column(name = "RightID")
	private BigInteger rightID;

	@Column(name = "LotID")
	private Integer lotID;

	@Column(name = "ZonalCommodityID")
	private Integer zonalCommodityID;

	@Column(name = "CommodityID")
	private Integer commodityID;

	@Column(name = "ZonalVarietyID")
	private Integer zonalVarietyID;

	@Column(name = "VarietyID")
	private Integer varietyID;

	@Column(name = "FarmerGivenSowingWeek")
	private Integer farmerGivenSowingWeek;

	@Column(name = "FarmerGivenSowingYear")
	private Integer farmerGivenSowingYear;

	@Column(name = "CurrentSowingWeek")
	private Integer currentSowingWeek;

	@Column(name = "SowingWeek")
	private Integer sowingWeek;

	@Column(name = "CurrentSowingYear")
	private Integer currentSowingYear;

	@Column(name = "HarvestWeek")
	private Integer harvestWeek;

	@Column(name = "HarvestYear")
	private Integer harvestYear;

	@Column(name = "KmlUrl")
	private String kmlUrl;

	@Column(name = "CorrectedSowingDate")
	private Date correctedSowingDate;

	@Column(name = "CurrentPhenophaseID")
	private Integer currentPhenophaseID;

	@Column(name = "SubRegionID")
	private String subRegionID;

	@Column(name = "SeedSourceID")
	private Integer seedSourceID;

	@Column(name = "EstimatedQuantity")
	private Double estimatedQuantity;

	@Column(name = "CurrentQuantity")
	private Double currentQuantity;

	@Column(name = "StateCode")
	private Integer stateCode;

	@Column(name = "CroppingArea")
	private String croppingArea;

	@Column(name = "Expired")
	private String expired;

	@Column(name = "DistrictCode")
	private Integer districtCode;

	@Column(name = "CropTypeID")
	private Integer cropTypeID;

	@Column(name = "AllowableVarianceQtyNegative")
	private Double allowableVarianceQtyNegative;

	@Column(name = "AllowableVarianceQtyPositive")
	private Double allowableVarianceQtyPositive;

	@Column(name = "IrrigationSourceID")
	private Integer irrigationSourceID;

	@Column(name = "CropAreaMismatched")
	private String cropAreaMismatched;

	@Transient
	private Integer phenophaseID;

	@Transient
	private String phenophaseName;

	@Transient
	private String stateName;

	@Transient
	private String varietyName;

	@Transient
	private String seasonName;

	@Transient
	private String commodityName;
	
	@Transient
	private String sowingDate;

}
