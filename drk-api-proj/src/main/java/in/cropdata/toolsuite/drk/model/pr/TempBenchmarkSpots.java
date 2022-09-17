package in.cropdata.toolsuite.drk.model.pr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "temp_benchmark_spots_zl20")
@Data
public class TempBenchmarkSpots {

	@Id
	@Column(name = "TILEID")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long spotId;

	@Column(name = "CaseId")
	private int caseId;

	@Column(name = "RegionId")
	@Transient
	private int regionId;

	@Column(name = "SubregionId")
	private int subregionId;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	@Column(name = "CreatedAt")
//	private Date createdAt;
//
//	@Transient
//	@Column(name = "UpdatedAt")
//	private Date updatedAt;

}
