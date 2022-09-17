package in.cropdata.cdtmasterdata.website.dto;


import lombok.Data;

@Data
public class BuyerPreApplicationDto {

	// Applicant Details
	
	private String name; //Contact Person Name
	private Integer designationID;
	private String otherDesignationName;
	private String telephoneNumber;
	private String mobileNumber;
	private String emailAddress;
//	private String registeredAddress;
	private String nationality; //Origin
//	private String locationOfInterest;
	private Integer applicantTypeID;
	private String website;
	
	// Applicant Application Details
	
	private Integer applicantApplicationId;
	private Integer applicationTypeID;
	private String companyName;
	private String dateOfIncorporation;
	private String panNumber;
	private String cinNumber;
	private String gstNumber;
	private String vat;
	private String companyRegistrationNumber;
	private String buildingName;
	private String streetName;
	private String postalCode;
	private String country;
	private String state;
	private String city;
//	private String businessAddress;
	private String reference;
	
	// Applicant Commodities Of Interest
	
	private Integer[] commoditiesId;
	
	// Applicant Contact Details
	
//	private String companyAuthSignName;
//	private String companyAuthContactNumber;
//	private String companyAuthSignEmail;
//	private String companyFaxNumber;
//	private String companyTelephoneNumber;
//	private String companyMobileNumber;
//	private String companyRepresentativeName;
//	private String companyRepresentativeContactNumber;
//	private String companyRepresentativeEmailID;
	
	// Applicant Office Space Details
	
//	private String officeSpace; //own/rent office space 
//	private BigDecimal usableArea;
//	private String officeAddress;
//	private String infrastructureFacilitiesInOffice;
	
	// Applicant Other Business Details
	
//	private String  firmName;
//	private String  product;
//	private String otherBusinessSamePremises;
//	private Integer  firmTypeID;
	private String  natureOfBusiness;
//	private String  yearOfEstablishment;
//	private String  presentNetWorth;
//	private String  city;
	private Integer  currencyID;
	private Integer businessTypeID;
	private String averageRevenue;
	private String nonAgriculturalBusinessName;
	
}
