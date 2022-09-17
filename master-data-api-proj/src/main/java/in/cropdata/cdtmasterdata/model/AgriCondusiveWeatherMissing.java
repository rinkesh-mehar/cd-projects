/**
 * 
 */
package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @author pallavi-waghmare
 *
 */

@Entity(name = "agri_conducive_weather_missing")
@Data
public class AgriCondusiveWeatherMissing {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "CommodityID")
	private Integer commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "StressTypeID")
	private Integer stressTypeId;
	
	@Transient
	private String stressType;
	
	@Column(name = "StressID")
	private Integer stressId;
	
	@Transient
	private String stress;

	@Column(name = "PrimaryWeatherParameterID")
	private Integer primaryWeatherParameterId;
	
	@Transient
	private String primaryWeatherParameter;
	
	@Column(name = "SecondaryWeatherParameterID")
	private Integer secondaryWeatherParameterId;
	
	@Transient
	private String secondaryWeatherParameter;	
	
	@Column(name = "PrimarySpecificationAverage")
	private Double primarySpecificationAverage;
	
	@Column(name = "PrimarySpecificationLower")
	private Double primarySpecificationLower;
	
	@Column(name = "PrimarySpecificationUpper")
	private Double primarySpecificationUpper;
	
//	@Column(name = "PrimaryStressDuration")
//	private int primaryStressDuration;
	
	@Column(name = "PrimaryStressDurationPast")
	private Integer primaryStressDurationPast;
	
	@Column(name = "PrimaryStressDurationFuture")
	private Integer primaryStressDurationFuture;
	
	@Column(name = "SecondarySpecificationAverage")
	private Double secondarySpecificationAverage;
	
	@Column(name = "SecondarySpecificationLower")
	private Double secondarySpecificationLower;
	
	@Column(name = "SecondarySpecificationUpper")
	private Double secondarySpecificationUpper;
	
//	@Column(name = "SecondaryStressDuration")
//	private int secondaryStressDuration;
	
	@Column(name = "SecondaryStressDurationPast")
	private Integer secondaryStressDurationPast;
	
	@Column(name = "SecondaryStressDurationFuture")
	private Integer secondaryStressDurationFuture;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;


}
