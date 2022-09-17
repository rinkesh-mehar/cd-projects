package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_meteorological_weeks")
public class AgriMeteorologicalWeek {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int Id;
	
	@Column(name = "WeekNo")
	private int weekNo;
	
	@Column(name = "StartDay")
	private int startDay;
	
	@Column(name = "StartMonth")
	private int startMonth;
	
	@Column(name = "EndDay")
	private int endDay;
	
	@Column(name = "EndMonth")
	private int endMonth;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
