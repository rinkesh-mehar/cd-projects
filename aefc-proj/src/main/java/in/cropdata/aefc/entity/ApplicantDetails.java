package in.cropdata.aefc.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.aefc.entity
 * @date 05/12/21
 * @time 5:33 PM
 */
@Data
@Entity
@Table(name = "alpplicant_details", schema = "cdt_website")
public class ApplicantDetails
{
    @Id
    @Column(name = "ID", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column (name = "FirstName")
    private String firstName;

    @Column (name = "LastName")
    private String lastName;

    @Column (name = "MiddleName")
    private String middleName;

    @Column (name = "DesignationID")
    private Integer designationID;

    @Column (name = "OtherDesignationName")
    private String otherDesignationName;

    @Column (name = "TelephoneNumber")
    private String telephoneNumber;

    @JsonProperty("loginMobileNo")
    @Column (name = "MobileNumber")
    private String mobileNumber;

    @Column (name = "EmailAddress")
    private String emailAddress;

//    @Column (name = "RegisteredAddress")
//    private String registeredAddress;

    @Column (name = "Nationality")
    private String nationality;

    @Column (name = "LocationOfInterest")
    private String locationOfInterest;

    @Column (name = "ApplicantTypeID")
    private Integer applicantTypeID;

    @Column (name = "Website")
    private String website;

    @Column (name = "AuthToken")
    private String authToken;

    @Column(name = "IsMobileVerified")
    private String isMobileVerified;

    @Column(name = "IsEmailVerified")
    private String isEmailVerified;

    @Column (name = "EmailVerificationLink")
    private String emailVerificationLink;

    @JsonProperty("loginOtp")
    @Column (name = "Otp")
    private String otp;

    @Column (name = "AadharCardNo")
    private String aadharCardNo;

    @Column(name = "OtpGenerationTime")
    private Timestamp timeout;

}
