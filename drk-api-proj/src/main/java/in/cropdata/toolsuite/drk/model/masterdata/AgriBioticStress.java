package in.cropdata.toolsuite.drk.model.masterdata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_biotic_stress")
public class AgriBioticStress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "StateCode")
	private int stateCode;

	@Transient
	private String state;

	@Column(name = "CommodityID")
	private int commodityId;

	@Transient
	private String commodity;

//	@Column(name = "PhenophaseID")
//	private int phenophaseId;

//	@Transient
//	private String phenophase;

	@Column(name = "StressTypeID")
	private int stressTypeId;

	@Transient
	private String stressType;

	@Column(name = "Name")
	private String name;

	@Column(name = "ScientificName")
	private String scientificName;

	@Column(name = "Start")
	private int start;

	@Column(name = "End")
	private int end;

	@Column(name = "Symptoms")
	private String symptoms;

	@Column(name = "UpdatedAt")
	private Date updatedAt;

	@Column(name = "CreatedAt")
	private Date createdAt;

	@Column(name = "Status")
	private String status;

}
