package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@Entity
@Table(name = "agri_commodity_stress",schema = "cdt_master_data")
@JsonInclude(value = Include.NON_NULL)
public class AgriCommodityStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CommodityID")
	private Integer commodityId;
	
	@Column(name = "StressID")
	private Integer stressId;
	
	@Transient
	private Date createdAt;
	
	@Transient
	private Date updatedAt;
	
	@Transient
	private String status;
	
}
