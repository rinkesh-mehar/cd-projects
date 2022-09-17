package in.cropdata.toolsuite.drk.model.gt;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "ground_truth_zl19")
public class GT_ZL19 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "RegionID")
	private int regionId;

	@Column(name = "VillageCode")
	private int villageCode;

//	@Column(name = "TileID")
//	private BigInteger fieldId;
	

	@Column(name = "SampleID")
	private String sampleId;

	@Column(name = "CaseID")
	private long caseId;

	@Column(name = "StressID")
	private int stressId;

	@Column(name = "ServerityID")
	private int severityId;

}
