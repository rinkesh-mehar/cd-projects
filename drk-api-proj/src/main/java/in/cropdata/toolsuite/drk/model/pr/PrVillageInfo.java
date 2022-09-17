package in.cropdata.toolsuite.drk.model.pr;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity(name = "village_information")
public class PrVillageInfo implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "SubRegionID")
	private int subRegionId;

	@Column(name = "VillageCode")
	private int villageCode;

	@Column(name = "TotalFarmHolding")
	private int totalFarmHolding;

	@Column(name = "AverageYeilD")
	private float averageYeild;

	@Column(name = "TotalSownArea")
	private float totalSownArea;

	@Column(name = "TotalIrrigatedArea")
	private float totalIrrigatedArea;

	@Column(name = "TotalFarmers")
	private int totalFarmers;

//	@Column(name = "FarmerRegistered")
//	private int farmerRegistered;

	@OneToMany(mappedBy = "prVillageInfo", cascade = CascadeType.ALL)
	private List<PrVillageCropSeasonSowingHarvestInfo> prVillageCropSeasonSowingHarvestList;

}
