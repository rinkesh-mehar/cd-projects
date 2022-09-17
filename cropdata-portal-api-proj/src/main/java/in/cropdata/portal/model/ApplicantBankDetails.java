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
@Table(name = "applicant_bank_details", schema = "cdt_website")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicantBankDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ApplicantID")
    private Integer applicantID;

    @Column(name = "ApplicantApplicationID")
    private Integer applicantApplicationID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Branch")
    private String branch;

    @Column(name = "AccountType")
    private String accountType;

    @Column(name = "AccountNumber")
    private String accountNumber;

    @Column(name = "IsOperational")
    private Integer isOperational;

    @Column(name = "IFSCCode")
    private String ifscCode;





    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;

    @Transient
    private String status;



}
