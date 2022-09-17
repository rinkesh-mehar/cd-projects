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
@Entity(name = "agri_agrochemical_brand")
public class AgriAgrochemicalBrand {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "Name")
	private String brandName;

	@Column(name = "CompanyId")
	private int companyId;
	
	@Column(name = "AgrochemicalID")
	private int agrochemicalId;
	
	@Column(name = "AgrochemicalStatus")
	private String agrochemicalStatus;
	
	@Transient
	private String companyName;
	
	@Transient
	private String agrochemical;
	
	@Transient
	private String agrochemicalTypeName;
	
	@Transient
	private int agrochemicalTypeId;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
