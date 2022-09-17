package in.cropdata.portal.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.model
 * @date 15/12/21
 * @time 12:35 PM
 */
@Data
@Table
@Entity(name = "terms_and_conditions")
public class TermsAndConditions
{
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "PlatformID")
    private Integer platformId;

    @Column(name = "TAndCUrl")
    private String tAndCUrl;

    @Column(name = "PrivacyPolicyUrl")
    private String privacyPolicyUrl;

    @Column(name = "Version")
    private String version;

    @Column(name = "status")
    private String status;
}
