package in.cropdata.cdtmasterdata.region.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.region.model
 * @date 18/01/21
 * @time 12:39 PM
 */
@Data
@Entity(name = "drkrishi_source")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SourceModel
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Name")
    private String name;

//    @Transient
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
    private String stateName;

    @Transient
    private String countryName;
}
