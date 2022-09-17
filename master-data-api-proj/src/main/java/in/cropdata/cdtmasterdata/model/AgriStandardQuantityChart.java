package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.Data;
@Entity(name = "agri_standard_quantity_chart")
@Data
public class AgriStandardQuantityChart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "StateCode")
	private int stateCode;
	
	@Transient
	private String state;
	
	@Column(name = "CommodityID")
	private int  commodityId;
	
	@Transient
	private String commodity;

	@Column(name = "VarietyID")
	private int varietyId;
	
	@Transient
	private String variety;
	
	@Column(name = "StandardQuantityPerAcre")
	private Double standardQuantityPerAcre;
	
	@Column(name = "StandardPositiveVariancePercent")
	private Double standardPositiveVariancePercent;
	
	@Column(name = "StandardPositiveVariancePerAcre")
	private Double standardPositiveVariancePerAcre;

	@Column(name = "StandardNegativeVariancePercent")
	private Double standardNegativeVariancePercent;
	
	@Column(name = "StandardNegativeVariancePerAcre")
	private Double standardNegativeVariancePerAcre;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
	
	
}