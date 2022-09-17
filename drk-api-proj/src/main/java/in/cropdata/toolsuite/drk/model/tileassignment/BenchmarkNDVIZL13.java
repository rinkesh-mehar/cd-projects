package in.cropdata.toolsuite.drk.model.tileassignment;

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

@Entity(name = "benchmark_ndvi_zl13")
@Data
public class BenchmarkNDVIZL13 {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private long id;

	@Column(name = "RegionID")
	private int regionId;

	@Column(name = "SubRegionID")
	private int subRegionId;

	@Column(name = "CommodityID")
	private int commodityId;

	@Column(name = "PhenophaseID")
	private int phenophaseId;

	@Column(name = "BenchmarkNDVI")
	private float benchmarkNDVI;

	@Transient
	@Column(name = "UpdatedAt")
	private Date updatedAt;

	@Transient
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedAt")
	private Date createdAt;

}
