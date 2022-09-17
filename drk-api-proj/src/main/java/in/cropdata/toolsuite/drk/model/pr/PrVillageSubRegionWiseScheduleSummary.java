package in.cropdata.toolsuite.drk.model.pr;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "village_commodity_schedule_summary")
public class PrVillageSubRegionWiseScheduleSummary implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "SubRegionID")
	private int subRegionId;

	@Column(name = "TotalSowingArea")
	private float totalSowingArea;

	@Column(name = "ExpectedYeild")
	private float expectedYeild;

}
