package in.cropdata.farmers.app.drk.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@Entity
@Table(name = "farmer_login_otp", schema = "drkrishi_ts")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FarmerLoginOtp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "PrimaryMobileNumber")
	private String mobileNo;

	@Column(name = "Otp")
	private String otp;

	@Column(name = "Timeout")
	private Timestamp timeout;

	@Column(name = "CreatedAt")
	private Timestamp createdAt;
	
	@Transient
	private Timestamp updatedAt;

}


 