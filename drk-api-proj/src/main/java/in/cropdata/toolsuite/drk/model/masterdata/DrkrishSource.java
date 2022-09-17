package in.cropdata.toolsuite.drk.model.masterdata;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.model.masterdata
 * @date 18/02/21
 * @time 10:30 AM
 */
@Data
@Entity(name = "drkrishi_source")
public class DrkrishSource
{
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Description")
    private String description;

    @Column(name = "Address")
    private String address;

    @Column(name = "CountryCode")
    private Integer countryCode;

    @Column(name = "StateCode")
    private Integer stateCode;

    @Column(name = "Headquarter")
    private String headquarter;

    @Column(name = "ApiKey")
    private String apiKey;

    @Transient
    private String status;
}
