/**
 * 
 */
package in.cropdata.farmers.app.drk.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity
@Table(name = "farmer_address_book", schema = "drkrishi_ts")
@JsonInclude(value = Include.NON_NULL)
public class FarmerAddressBook  implements Serializable{

	private static final long serialVersionUID = -8272364182624828086L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false)
	private Integer id;

	@Column(name = "AddressLine1")
	private String addressLine1;

	@Column(name = "AddressLine2")
	private String addressLine2;

	@Column(name = "TehsilCode")
	private Integer tehsilCode;

	@Column(name = "DistrictCode")
	private Integer districtCode;

	@Column(name = "StateCode")
	private Integer stateCode;

	@Column(name = "PrimaryMobile")
	private String primaryMobile;

	@Column(name = "CityCode")
	private Integer cityCode;

	@Column(name = "PanchayatCode")
	private Integer panchayatCode;

	@Column(name = "VillageCode")
	private Integer villageCode;

	@Column(name = "Pincode")
	private Integer pinCode;

	@Column(name = "Latitude")
	private Double latitude;
	
	@Column(name = "Longitude")
	private Double longitude;
	
	@Transient
	private String secondaryMobile;

}
