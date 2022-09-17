package in.cropdata.toolsuite.drk.model.cases;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity(name = "farmer_details")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@IdClass(FarmerCompositeKey.class)
public class Farmer implements Serializable {

	public Farmer() {
		super();
	}

	public Farmer(BigInteger farmerId) {
		super();
		this.farmerId = farmerId;
	}

	@Id
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

	@Column(name = "FarmerName")
	private String farmerName;

	@Column(name = "FarmerFatherHusbandName")
	private String farmerFatherHusbandName;

	@Column(name = "Age")
	private int age;

	@Column(name = "PrimaryMobileNumber")
//	@JsonProperty("primaryMobNumber")
	private long primaryMobileNumber;

	@Column(name = "AlternateMobileNumber")
//	@JsonProperty("alternateMobNumber")
	private long alternateMobileNumber;

	@Column(name = "MaritalStatusID")
	private int maritalStatusId;

	@Column(name = "MobileTypeID")
	private int mobileTypeId;

	@Column(name = "HasGovtIdProof")
	private String hasGovtIdProof;

//	@JsonAlias("govtIdProofId")
//	@Transient
//	private Integer[] govtIdProofId;
//
//	@Column(name = "govtIdProofId")
//	private String govtIdProofIdStr;
	
	@Column(name = "GovtIdProofID")
	private String govtIdProofId;

	@Column(name = "EducationID")
	@JsonProperty("educationTypeId")
	private int educationId;

	@Column(name = "FarmerLanguageID")
	@JsonProperty("speakingLanguageId")
	private int farmerLanguageId;

	@Column(name = "IsVIP")
	private String isVip;

	@Column(name = "VIPStatusID")
	private int vipStatusId;

	@Column(name = "NumberOfDependents")
	@JsonProperty("dependentsCount")
	private int numberOfDependents;

	@Column(name = "HasOwnLand")
	private String hasOwnLand;

	@Column(name = "TotalOwnLand")
	private float totalOwnLand;

	@Column(name = "HasLeasedLand")
	private String hasLeasedLand;

	@Column(name = "HasIrrigatedLand")
	private String hasIrrigatedLand;

	@Column(name = "FarmSize")
	private float farmSize;

	@Column(name = "CropArea")
	private float cropArea;

	@Column(name = "MajorCrop")
	private String majorCrop;
	
	@Column(name = "CroppingPattern")
	private String croppingPattern;

	@Column(name = "HasPolyhouse")
	private String hasPolyhouse;

	@Column(name = "HasCattles")
	private String hasCattles;

	@Column(name = "HasPonds")
	private String hasPonds;

	@Column(name = "HasSheds")
	private String hasSheds;

	@Column(name = "FarmMachineryID")
	private int farmMachineryId;

	@Column(name = "ActivityID")
	@JsonProperty("alliedActivityId")
	private int activityId;

	@Column(name = "AnnualIncome")
	private float annualIncome;

	@Column(name = "DueAmount")
	private float dueAmount;

	@Column(name = "BankID")
	@JsonProperty("bankAccountId")
	private int bankId;

	@Column(name = "BankBranchID")
	private int bankBranchId;

	@Column(name = "AccountName")
	private String accountName;

	@Column(name = "Ifsc")
	private String ifsc;

	@Column(name = "HasKisanCreditCard")
	private String hasKisanCreditCard;

	@Column(name = "HasLifeInsurance")
	private String hasLifeInsurance;

	@Column(name = "HasHealthInsurance")
	private String hasHealthInsurance;

	@Column(name = "HasCropInsurance")
	private String hasCropInsurance;

	@Column(name = "IsPennydropped")
	private String isPennydropped;

	@Column(name = "PennydropStatus")
	private String pennydropStatus;

	@Column(name = "PennydropDate")
	private String pennydropDate;

	@Column(name = "HasOutstandingLoan")
	private String hasOutstandingLoan;

	@Column(name = "LoanPurposeID")
	private int loanPurposeId;

	@Column(name = "SourceOfLoanID")
	private int sourceOfLoanId;

	@Column(name = "OutstandingLoanAmount")
	private float outstandingLoanAmount;

	@Column(name = "WillingnessForCdt")
	private boolean willingnessForCdt;

	@Column(name = "IsDrkCust")
	@JsonProperty("isDrkCust")
	private boolean isDrkCust;

	@Column(name = "IsAgriotaCust")
	@JsonProperty("isAgriotaCust")
	private boolean isAgriotaCust;

	@Column(name = "CreatedBy")
	private int createdBy;

	@Column(name = "CreatedDate")
	private Date createdDate;

	@Column(name = "ModifiedBy")
	private int modifiedBy;

	@Column(name = "ModifiedDate")
	private Date modifiedDate;

//	@Column(name = "IsLeasedLand")
//	private String isLeasedLand;

	@Column(name = "IsVerified")
	private String isVerified;

//	@Column(name = "CurrentRatio")
//	private int currentRatio;

	/**
	 * @return the govtIdProofIdStr
	 */
//	public String getGovtIdProofIdStr() {
//		return Arrays.toString(govtIdProofId);
//	}

	/**
	 * @param govtIdProofId the govtIdProofId to set
	 */
//	public void setGovtIdProofId(Integer[] govtIdProofId) {
//		this.govtIdProofId = govtIdProofId;
//		this.govtIdProofIdStr = Arrays.toString(govtIdProofId);
//		this.govtIdProofIdStr = this.govtIdProofIdStr.replaceAll("\\[", "").replaceAll("\\]", "");
//	}

// @Column(name = "CreatedAt")
// private Date createdAt;
//
// @Column(name = "UpdatedAt")
// private Date updatedAt;

// @Column(name = "Status")
// private String status;

}