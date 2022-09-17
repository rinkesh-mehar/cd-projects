package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "agri_recommendation_missing")
@Data
public class AgriRecommendationMissing {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;

	@Column(name = "StressID")
	private int stressId;
	
	@Transient
	private String stress;

	@Column(name = "StressSeverityID")
	private int stressSeverityId;
	
	@Transient
	private String stressSeverity;

	@Column(name = "StressControlMeasureID")
	private int stressControlMeasureId;
	
	@Transient
	private String stressControlMeasure;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
