package in.cropdata.toolsuite.drk.model.pr;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "benchmark_spots_zl20")
@Data
public class BenchmarkSpots {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "RegionId")
	private int regionID;

	@Column(name = "SubregionId")
	private int subregionId;

	@Column(name = "CommodityID")
	private int commodityId;

	@Column(name = "PhenophaseId")
	private int phenophaseId;

	@Column(name = "SpotId")
	private Long spotId;

	@Column(name = "CaseId")
	private int caseId;

	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedAt")
	private Date createdAt;

	@Transient
	@Column(name = "UpdatedAt")
	private Date updatedAt;

}
