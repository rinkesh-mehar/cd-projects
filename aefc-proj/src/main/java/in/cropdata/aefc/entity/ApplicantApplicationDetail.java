package in.cropdata.aefc.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * @since 1.0
 * @author RinkeshKM
 */

@Table(name = "applicant_application_details")
@Entity
@Data
public class ApplicantApplicationDetail {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ApplicantID")
    private Integer applicantID;

    @Column(name = "ApplicationTypeID")
    private Integer applicationTypeID;

    @Column(name = "CompanyName")
    private String companyName;

//    @Column(name = "DateOfIncorporation")
//    private Date dateOfIncorporation;

    @Column(name = "PanNumber")
    private String panNumber;

    @Column(name = "CinNumber")
    private String cinNumber;

    @Column(name = "GstNumber")
    private String gstNumber;

    @Column(name = "Vat")
    private String vat;

    @Column(name = "CompanyRegistrationNumber")
    private String companyRegistrationNumber;

//    @Column(name = "BusinessAddress")
//    private String businessAddress;

    @Column(name = "Reference")
    private String reference;

    @Column(name = "ParentReference")
    private String parentReferralCode;
//    @Column(name = "BuildingName")
//    private String buildingName;

//    @Column(name = "StreetName")
//    private String streetName;

//    @Column(name = "PostalCode")
//    private String postalCode;

//    @Column(name = "Country")
//    private String country;

//    @Column(name = "State")
//    private String state;

//    @Column(name = "City")
//    private String city;

    @Column(name = "Nationality")
    private String nationality;
}
