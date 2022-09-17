package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Entity(name = "regional_season")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegionalSeason {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "RegionID")
	private Integer regionId;
	
	@Transient
	private String region;
	
	@Column(name = "StateCode")
	private Integer stateCode;
	
	@Transient
	private String state;
	
	@Column(name = "SeasonID")
	private Integer seasonId;
	
	@Transient
	private String season;
	
	@Column(name = "SeasonStartWeek")
	private Integer startWeek;
	
	@Column(name = "SeasonEndWeek")
	private Integer endWeek;

	@Column(name = "SeasonSpan")
	private Integer seasonSpan;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}
