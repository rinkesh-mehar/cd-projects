package in.cropdata.portal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import in.cropdata.portal.model.ApplicationSignatoryDetails;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author Vivek Gajbhiye
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StepTwo {

    // Document Upload
    private List<MultipartFile> panFile;
    private List<MultipartFile> tanFile;
    private List<MultipartFile> cinFile;
    private List<MultipartFile> udyogAadharFile;
    private List<MultipartFile> gstCertificateFile;
//    private MultipartFile vatDocument;
//    private MultipartFile incomeTaxDocument;
//    private MultipartFile companyRegistrationDocument;
    private List<MultipartFile> vatDocument;
    private List<MultipartFile> incomeTaxDocument;
    private List<MultipartFile> companyRegistrationDocument;
    private List<MultipartFile> marketLicenseFile;
    private String cinNo;
    private String udyogAadharNo;
    private String gstNo;
    private String tanNo;
    private String panNo;
    private String incomeTaxNo;
    private String vatNo;
    private String isdCode;
    private String companyRegistrationNo;
    private String marketLicenseNo;
    private Integer directorCount;

    private List<String> directorsProprietorPartnerFirstName;
    private List<String> directorsProprietorPartnerMiddleName;
    private List<String> directorsProprietorPartnerLastName;
    //    privatList<String> directorsProprietorPartnerName;
    private List<Integer> directorsProprietorPartnerDesignation;
    private List<String> directorsProprietorPartnerOtherDesignation;
    private List<String> directorsProprietorPartnerTelephoneNo;
    private List<String> directorsProprietorPartnerMobileNo;
    private List<String> directorsProprietorPartnerEmail;
    private List<String> directorsProprietorPartnerTelephoneNoPrefix;
    private List<String> directorsProprietorPartnerMobileNoPrefix;


    private String adminFirstName;
    private String adminMiddleName;
    private String adminLastName;
    private String adminEmail;
    private String adminMobileNo;

    private String adminMobileNoPrefix;
    private String dateOfIncorporationOrLicense;
    private String dateOfIncorporationOrRegistration;
    private String licenseExpiryDate;
    private String cinNoExpiryDate;
    private String adminTelephoneNoPrefix;
    private String adminTelephoneNo;
    private String parentReference;

    private List<ApplicationSignatoryDetails> applicationSignatoryDetails;



}
