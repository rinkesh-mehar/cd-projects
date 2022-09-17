package in.cropdata.aefc.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @date 05/12/21
 */

@Table(name = "applicant_other_business_details")
@Entity
@Data
public class ApplicantOtherBusinessDetails {

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ApplicantID")
    private Integer applicantID;

    @Column(name = "ApplicationAplicantID")
    private Integer applicationAplicantID;

    @Column(name = "FirmName")
    private String firmName;

    @Column(name = "Product")
    private String product;

    @Column(name = "OtherBusinessSamePremises")
    private String otherBusinessSamePremises;

    @Column(name = "FirmTypeID")
    private Integer firmTypeID;

    @Column(name = "NatureOfBusiness")
    private String natureOfBusiness;

    @Column(name = "YearOfEstablishment")
    private String yearOfEstablishment;

    @Column(name = "PresentNetWorth")
    private String presentNetWorth;

    @Column(name = "City")
    private String city;

    @Column(name = "CurrencyID")
    private Integer currencyID;

    @Column(name = "BusinessTypeID")
    private Integer businessTypeID;

    @Column(name = "AverageRevenue")
    private String averageRevenue;

    @Column(name = "NonAgriculturalBusinessName")
    private String nonAgriculturalBusinessName;

}