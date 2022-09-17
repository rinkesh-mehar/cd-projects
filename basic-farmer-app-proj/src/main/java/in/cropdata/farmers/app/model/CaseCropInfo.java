/**
 * 
 */
package in.cropdata.farmers.app.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import in.cropdata.farmers.app.DTO.FarmerLatLongDTO;
//import in.cropdata.farmers.app.drk.model.FarmerCaseKMLPoint;
import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity
@Table(name = "case_crop_info", schema = "farmers_app")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CaseCropInfo implements Serializable {

	private static final long serialVersionUID = 592556610256236803L;

	@Id
	@JsonProperty("ID")
	@Column(name = "ID")
	private String ID;

	@Column(name = "CaseID")
	private String caseID;
	
	@Column(name = "VarietyID")
	private Integer varietyID;

	@Column(name = "SeasonID")
	private Integer seasonID;

	@Column(name = "IrrigationSourceID")
	private Integer irrigationSourceID;

	@Column(name = "CropArea")
	private Float cropArea;
	
	@Column(name = "SowingWeek")
	private Integer sowingWeek;

	@Column(name = "SowingYear")
	private Integer sowingYear;
	
	@Column(name = "HarvestWeek")
	private Integer harvestWeek;

	@Column(name = "FarmerID")
	private String farmerID;

	@Column(name = "CommodityID")
	private Integer commodityID;

	@Column(name = "Quantity")
	private Float quantity;
	
	@Transient
	private Timestamp createdAt;
	
	@Transient
	private Timestamp updatedAt;

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
	private List<FarmerLatLongDTO> caseKmlLatLongs;

	@Transient
	private List<Integer> stressIDs;
}
