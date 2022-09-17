package in.cropdata.farmers.app.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;


@Data
@Entity
@Table(name = "farmer", schema = "farmers_app")
@JsonInclude(value = Include.NON_NULL)
public class Farmer implements Serializable{
	
	private static final long serialVersionUID = 592556610256236803L;

	@Id
	@Column(name = "ID")
	private String id;
	
//	@JsonInclude(JsonInclude.Include.NON_NULL)
	@Column(name = "FarmerName")
	private String farmerName;

	@Column(name = "PrimaryMobNumber")
	private String primaryMobNumber;

	@Column(name = "VillageCode")
	private Integer villageCode;
	
	@Column(name = "AddressID")
	private Integer addressId;

	@Column(name = "AuthToken")
	private String authToken;
	
	@Transient
	private Integer stateCode;

	@Transient
	private Timestamp createdAt;

	@Transient
	private Timestamp updatedAt;

}
