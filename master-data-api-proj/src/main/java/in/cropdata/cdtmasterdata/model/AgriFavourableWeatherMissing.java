package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "agri_favourable_weather_missing")
@Data
public class AgriFavourableWeatherMissing {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer Id;
	
	@Column(name = "CommodityID")
	private Integer commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "PhenophaseID")
	private Integer phenophaseId;
	
	@Transient
	private String phenophase;
	
	@Column(name = "WeatherParameterID")
	private Integer weatherParameterId;
	
	@Transient
	private String weatherParameter;
	
	@Column(name = "SpecificationAverage")
	private Float specificationAverage;
	
	@Column(name = "SpecificationLower")
	private Float specificationLower;
	
	@Column(name = "SpecificationUpper")
	private Float specificationUpper;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}

