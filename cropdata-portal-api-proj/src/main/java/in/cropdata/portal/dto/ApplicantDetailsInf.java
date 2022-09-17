package in.cropdata.portal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Vivek Gajbhiye
 */
public interface ApplicantDetailsInf {

    public String getEmail();

    public String getCompanyName();

    public String getMobileNo();

    public String getStep();

    public String getIsdCode();

    public String getCountryCode();

    public String getParentReference();
}
