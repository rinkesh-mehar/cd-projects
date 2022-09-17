package in.cropdata.farmers.app.masters.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder.In;

import lombok.Data;

@Data
@Entity
@Table(name="zonal_standard_quantity_chart",schema="cdt_master_data")
public class ZonalStandardQuantityChart {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer id; 
	
	@Column(name="ZonalVarietyID")
	private Integer zonalVarietyID; 
	
	@Column(name="StandardPositiveVariancePerAcre")
	private Double standardPositiveVariancePerAcre; 
	
	@Column(name="StandardPositiveVariancePercent")
	private Double standardPositiveVariancePercent; 
	
	@Column(name="StandardNegativeVariancePerAcre")
	private Double standardNegativeVariancePerAcre; 
	
	@Column(name="StandardNegativeVariancePercent")
	private Double standardNegativeVariancePercent; 
}
