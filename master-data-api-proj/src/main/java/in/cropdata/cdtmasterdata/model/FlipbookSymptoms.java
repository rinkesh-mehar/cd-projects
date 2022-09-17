package in.cropdata.cdtmasterdata.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;


@Data
@Entity(name = "flipbook_symptoms")
public class FlipbookSymptoms {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "CommodityID")
	private int commodityId;

	@Transient
	private String commodity;
	
	@Column(name = "PhenophaseID")
	private int phenophaseId;
	
	@Column(name = "PlantPartID")
	private int plantPartId;
	
	@Column(name = "StressTypeID")
	private int stressTypeId;

	@Transient
	private String stressType;
	
	@Column(name = "StressID")
	private int stressId;


	@Column(name = "StressStageID")
	private Integer stressStageId;

	
	@Column(name = "Symptom")
	private String symptom;
	

//	@Column(name = "Reference")
//	private String reference;
}