package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_seed_treatment_missing")
public class AgriSeedTreatmentMissing {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "VarietyID")
	private int varietyId; 
	
	@Transient
	private String variety;
	
	@Column(name = "UomID")
	private int uomId;
	
	@Transient
	private String uom;
	
	@Column(name = "Name")
	private String name;
	
	private String dose;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
	

}
