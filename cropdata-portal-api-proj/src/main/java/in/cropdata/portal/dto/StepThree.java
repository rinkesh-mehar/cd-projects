package in.cropdata.portal.dto;

import lombok.Data;

/**
 * @author Vivek Gajbhiye
 */
@Data
public class StepThree {

    private String name;
    private String branch;
    private String accountType;
    private String accountNumber;
    private Integer isOperational;
    private String ifscCode;
    private String membershipType;
    private String agreementAccepted;
}
