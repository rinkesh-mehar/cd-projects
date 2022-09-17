package in.cropdata.toolsuite.drk.model.cases;

import java.io.Serializable;
import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity(name = "farm_details")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(FarmCompositeKey.class)
public class Farm implements Serializable {

	public Farm() {
		super();
	}

	public Farm(BigInteger farmId) {
		super();
		this.farmId = farmId;
	}

	@Id
	@Column(name = "FarmID")
	private BigInteger farmId;

	@Column(name = "FarmerID")
	private BigInteger farmerId;

	@Id
	@Column(name = "RegionID")
	private Integer regionId;

	@Column(name = "SubRegionID")
	private Integer subRegionId;

	@Column(name = "villageCode")
	@JsonProperty("villageId")
	private Integer villageCode;

	@Column(name = "FarmName")
	private String farmName;

	@Column(name = "HasOwnLand")
	private String hasOwnLand;

	@Column(name = "LandOwnershipID")
	private Integer landOwnershipId;

	@Column(name = "HasLandOwnershipDocumentPhoto")
	private String hasLandOwnershipDocumentPhoto;

	@Column(name = "OwnLand")
	private float ownLand;

	@Column(name = "FarmSize")
	private float farmSize;

	@Column(name = "IsIrrigatedLand")
	private String isIrrigatedLand;

	@Column(name = "CroppingArea")
	private float croppingArea;

	@Column(name = "HasLeasedLand")
	private String hasLeasedLand;

	@Column(name = "LeasedOutLand")
	private float leasedOutLand;

	@Column(name = "LeasedInLand")
	private float leasedInLand;

//    @Column(name = "Rating")
//    private float rating;
//
//    @Column(name = "CreatedAt")
//    private Date createdAt;
//
//    @Column(name = "UpdatedAt")
//    private Date updatedAt;
//
//    @Column(name = "Status")
//    private String status;

}
