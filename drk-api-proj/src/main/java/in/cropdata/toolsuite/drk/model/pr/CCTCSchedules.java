package in.cropdata.toolsuite.drk.model.pr;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity(name = "village_date_schedules")
@JsonIgnoreProperties(ignoreUnknown = true)
public class CCTCSchedules implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "VillageCode")
	private int villageCode;

	@Column(name = "TotalCalls")
	private int totalCalls;

	@Column(name = "RejectedCalls")
	private int rejectedCalls;

	@Column(name = "WillingToJoinAgriota")
	private int willingToJoinAgriota;
	
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
	@Column(name = "Date")
	private Date date;

}
