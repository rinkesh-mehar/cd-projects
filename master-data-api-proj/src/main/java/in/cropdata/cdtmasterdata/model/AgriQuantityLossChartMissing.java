/**
 * 
 */
package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity(name = "agri_quantity_loss_chart_missing")
public class AgriQuantityLossChartMissing {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CommodityID")
//	@Transient
	private Integer  commodityId;
	
	@Transient
	private String commodity;

	@Column(name = "StressID")
	private Integer stressId;
	
	@Transient
	private String stress;
	
	@Column(name = "PhenophaseID")
//	@Transient
	private Integer phenophaseId;
	
	@Transient
	private String phenophase;
	
	@Column(name = "MinQuantityCorrectionPercent")
	private Integer minQuantityCorrectionPercent;
	
	@Column(name = "MaxQuantityCorrectionPercent")
	private Integer maxQuantityCorrectionPercent;
	
//	@Column(name = "CommodityPhenophaseID")
//	private int commodityPhenophaseId;

//	@Column(name ="ActualQuantityCorrectionPercent")
//	private Integer actualQuantityCorrectionPercent;
	
	@Column(name = "MinBandValue")
	private Integer minBandValue;
	
	@Column(name = "MaxBandValue")
	private Integer maxBandValue;
	
	@Column(name = "BandName")
	private String bandName;
//	@Column(name = "CriteriaID")
//	private int criteriaId;
	
	@Transient
	private String criteria;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status = "Inactive";

	@Column(name = "StressSeverityID")
	private String stressSeverityID;
	
}
