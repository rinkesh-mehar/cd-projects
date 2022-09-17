package in.cropdata.portal.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class StepOne {

    private Integer applicationTypeID;
    private String companyName;
    private String natureOfBusiness;
    private String nonAgricultureBusinessName;
    private Integer applicantType;
    private String emailAddress;
    private String mobileNumber;
    private String website;
    private String country;

    // Registered Address
    private String registeredBuildingName;
    private String registeredStreetName;
    private String registeredPostalCode;
    private String registeredCountryCode;
    private String registeredState;
    private String registeredCity;

    private String sameAsRegisteredAddress;

    // Business Address
    private String bussinessBuildingName;
    private String bussinessStreetName;
    private String businessPostalCode;
    private String businessCountryCode;
    private String businessState;
    private String businessCountry;
    private String businessCity;
    private Integer businessTypeID;

    private String currentOrInterestedProducts;
    private Integer[] commodityInterest;
    private String reference;
}
