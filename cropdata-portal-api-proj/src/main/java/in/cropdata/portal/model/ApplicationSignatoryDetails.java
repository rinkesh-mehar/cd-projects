package in.cropdata.portal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Vivek Gajbhiye
 */
@Data
@Entity
@Table(name = "application_signatory_details", schema = "cdt_website")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationSignatoryDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer  id;

    @Column(name = "ApplicantID")
    private Integer applicantID;

//    @Column(name = "ApplicationAplicantID")
//    private Integer applicationAplicantID;

    @Column(name = "CompanyAuthSignFirstName")
    private String companyAuthSignFirstName;

    @Column(name = "CompanyAuthSignMiddleName")
    private String companyAuthSignMiddleName;

    @Column(name = "CompanyAuthSignLastName")
    private String companyAuthSignLastName;

    @Column(name = "CompanyAuthContactNumber")
    private String companyAuthContactNumber;

    @Column(name = "CompanyAuthSignEmail")
    private String companyAuthSignEmail;

    @Column(name = "CompanyFaxNumber")
    private String companyFaxNumber;

    @Column(name = "CompanyTelephoneNumber")
    private String companyTelephoneNumber;

    @Column(name = "CompanyMobileNumber")
    private String companyMobileNumber;

    @Column(name = "CompanyRepresentativeName")
    private String companyRepresentativeName;

    @Column(name = "CompanyRepresentativeContactNumber")
    private String companyRepresentativeContactNumber;

    @Column(name = "CompanyRepresentativeEmailID")
    private String companyRepresentativeEmailID;

    @Column(name = "OtherDesignation")
    private String otherDesignation;


    @Column(name = "IsdCode")
    private String isdCode;

    @Column(name = "TelephoneIsdCode")
    private String telephoneIsdCode;


    @Column(name = "CompanyAuthSignDesignationID")
    private Integer companyAuthSignDesignationID;

    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;

    @Transient
    private String status;
}
