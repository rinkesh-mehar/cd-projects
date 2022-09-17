package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "agri_quality_chart_missing")
@Data
public class AgriQualityChartMissing {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "ID")
	private int id;
	
//	@Column(name = "Name")
//	private String name;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "PhenophaseID")
	private int phenophaseId;
	
	@Transient
	private String phenophase;
	
	@Column(name = "HealthParameterID")
	private int healthParameterId;
	
	@Transient
	private String healthParameter;
	
	@Column(name = "GradeI")
	private String gradeI;
	
	@Column(name = "GradeII")
	private String gradeII;
	
	@Column(name = "GradeIII")
	private String gradeIII;
	
	@Column(name = "MingradeI")
	private float mingradeI;
	
	@Column(name = "MaxgradeI")
	private float maxgradeI;
	
	@Column(name = "MingradeII")
	private float mingradeII;
	
	@Column(name = "MaxgradeII")
	private float maxgradeII;
	
	@Column(name = "MingradeIII")
	private float mingradeIII;
	
	@Column(name = "MaxgradeIII")
	private float maxgradeIII;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
