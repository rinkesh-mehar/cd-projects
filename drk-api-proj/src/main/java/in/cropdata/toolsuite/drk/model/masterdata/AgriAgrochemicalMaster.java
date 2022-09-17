package in.cropdata.toolsuite.drk.model.masterdata;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_agrochemical")
public class AgriAgrochemicalMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "AgrochemicalTypeID")
	private int agrochemicalTypeId;

	@Transient
	private String agrochemicalType;

	@Column(name = "Name")
	private String name;

	@Column(name = "WaitingPeriod")
	private int waitingPeriod;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Column(name = "StressTypeID")
	private int stressTypeId;
	
	@Transient
	private List<AgriBioticStress> stressNameList;

	@Column(name = "UpdatedAt")
	private Date updatedAt;

	@Column(name = "CreatedAt")
	private Date createdAt;

	@Column(name = "Status")
	private String status;

}
