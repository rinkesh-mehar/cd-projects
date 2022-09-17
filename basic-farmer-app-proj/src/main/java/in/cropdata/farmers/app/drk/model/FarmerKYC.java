package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author RinkeshKM
 * @Date 30/01/21
 */

@Data
@Entity
@Table(name = "farmer_kyc", schema = "drkrishi_ts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FarmerKYC {

	@Id
	@JsonProperty("ID")
	@Column(name = "ID")
	private String ID;

	@Column(name = "doc_type_id")
	private Integer docTypeID;

	@Column(name = "farmer_id")
	private String farmerID;

	@Column(name = "address_id")
	private Integer addressID;
	
	@Column(name = "permanent_address")
	private String permanentAddress;

	@Transient
	private String docURL;

	@Column(name = "farmer_photo")
	private String farmerPhoto;

	@Transient
	private Double latitude;

	@Transient
	private Double longitude;

	@Transient
	private List<String> liKYCDocs;

	@Transient
	private String addressLine1;
	
	@JsonProperty(value = "pincode")
	@Transient
	private Integer pinCode;

	@Transient
	private String farmerName;

	@Transient
	private String primaryMobNumber;

	@Transient
	private Double farmSize;

}
