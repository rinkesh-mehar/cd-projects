package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity
@Table(name = "agri_quality_commodity_parameter", schema = "cdt_master_data")
public class AgriQualityCommodityParameter {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CommodityID")
	private Integer CommodityId;
	
	@Column(name = "ParameterID")
	private Integer ParameterId;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String status;
	
	@Transient
	private Integer[] qualityParameterIds;

}
