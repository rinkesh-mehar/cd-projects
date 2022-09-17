package in.cropdata.toolsuite.drk.model.pr;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity(name = "village_information_summary")
public class PrVillageInformationSummary implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "SubRegionID")
	private int subRegion;

	@Column(name = "TotalTask")
	private int totalTask;

	@Column(name = "TaskCompletion")
	private BigDecimal taskCompletionPercent;

}
