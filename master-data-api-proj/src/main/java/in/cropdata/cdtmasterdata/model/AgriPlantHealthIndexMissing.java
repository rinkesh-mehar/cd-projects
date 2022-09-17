package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Entity(name = "agri_plant_health_index_missing")
@Data
@JsonInclude(value = Include.NON_NULL)
public class AgriPlantHealthIndexMissing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "Name")
	private String name;
	
	@Column(name = "StateCode")
	private int stateCode;
	
	@Column(name = "CommodityID")
	private int commodityID;
	
	@Column(name = "VarietyID")
	private int varietyID;
	
	@Column(name = "PhenophaseID")
	private int phenophaseID;
	
//	@Column(name = "HealthParameter")
//	private String healthParameter;
	
	@Column(name = "NormalValue")
	private float normalValue;
	
	@Column(name = "IdealValue")
	private float idealValue;
	
//	@Column(name = "Value")
//	private float value;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}
