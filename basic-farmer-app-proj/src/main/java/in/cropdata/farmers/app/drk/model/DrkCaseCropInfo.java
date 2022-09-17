package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import in.cropdata.farmers.app.DTO.FarmerLatLongDTO;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name ="case_crop_info",schema= "drkrishi_ts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class DrkCaseCropInfo {
	
	@Id
	@JsonProperty("ID")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, unique = true)
	private String ID;

	@Column(name = "case_id")
	private String caseID;

	@Column(name = "seed_source_id")
	private Integer seedSourceId;

	@Column(name = "variety_id")
	private Integer varietyID;

	@Column(name = "crop_area")
	private Double cropArea;

	@Column(name = "seeds_sample_received")
	private Boolean seedsSampleReceived;

	@Column(name = "seeds_rates")
	private Double seedRate;

	@Column(name = "season_id")
	private Integer seasonID;

	@Column(name = "uom_id")
	private Integer uomId;

	@Column(name = "spacing_row")
	private Double spacingRow;

	@Column(name = "spacing_plant")
	private Double spacingPlant;

	@Column(name = "corrected_sowing_week")
	private Integer sowingWeek;

	@Column(name = "corrected_sowing_year")
	private Integer sowingYear;

	@Column(name = "harvest_week")
	private Integer harvestWeek;

	@Column(name = "harvest_year")
	private Integer harvestYear;

	@Column(name = "farmer_given_sowing_year")
	private Integer farmerGivenSowingYear;

	@Column(name = "farmer_given_sowing_week")
	private Integer farmerGivenSowingWeek;

	@Column(name = "seeds_sample_received_info")
	private String seedsSampleReceivedInfo;

	@Column(name = "advisory_type")
	private String advisoryType;
	
	@Column(name = "acz_id")
	private Integer aczId;

	@Transient
//	@Column(name = "CreatedAt")
	private Timestamp createdAt;

	@Transient
//	@Column(name = "UpdatedAt")
	private Timestamp updatedAt;

	@Column(name = "SourceID")
	private Integer sourceID;

	@Column(name = "RegionID")
	private Integer regionId;

	@Column(name = "farmer_given_yield")
	private Double farmerGivenYield;

	@Column(name = "irrigation_source_id")
	private Integer irrigationSourceID;

	@Column(name = "case_status")
	private Integer caseStatusID;
	
	@Column(name = "latitude")
	private Double latitude;
	
	@Column(name = "longitude")
	private Double longitude;
	
	@Transient
	private String caseStatus;

	@Column(name = "sowing_date")
	private String sowingDate;

	@Transient
	private String status;

	@Transient
	private List<String> ownershipDocs;

	@Transient
	private Integer ownershipTypeID;

	@Transient
	private String ownershipTypeName;

	@Transient
	private Integer cropTypeID;

	@Transient
	private String cropTypeName;

	@Transient
	private String farmID;

	@Transient
	private String varietyName;

	@Transient
	private String commodityName;

	@Transient
	private String irrigationSourceName;

	@Transient
	private String seasonName;

	@Transient
	private List<FarmerLatLongDTO> caseKmlLatLongs;

	@Transient
	private List<Integer> stressIDs;
	
	@Transient
	private String cropAreaMismatched;
	
	@Transient
	private Boolean isDrKrishiCase;
	
	@Transient
	private Map<String,Object> rightDetails;
	
//	@Transient
//	private List<FarmerLatLongDTO> FarmerLatLongs;



}
