package in.cropdata.toolsuite.drk.model.pr;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Data
@Entity(name = "village_crop_season_sowing_harvest")
public class PrVillageCropSeasonSowingHarvestInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "VillageCode")
	private int villageCode;

	@Column(name = "SeasonID")
	private int seasonId;

	@Column(name = "CommodityID")
	private int commodityId;

	@Column(name = "SownArea")
	private float sownArea;

	@Column(name = "SowingWeek")
	private int sowingWeek;

	@Column(name = "HarvestingWeek")
	private int harvestingWeek;

	@ManyToOne
	@JoinColumn(name = "VillageCode", referencedColumnName = "VillageCode", insertable = false, updatable = false)
	private PrVillageInfo prVillageInfo;

}
