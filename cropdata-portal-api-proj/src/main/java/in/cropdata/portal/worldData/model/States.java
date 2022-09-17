package in.cropdata.portal.worldData.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.worldData.model
 * @date 14/12/21
 * @time 11:11 AM
 */
@Table
@Entity(name = "regions")
@Data
public class States
{
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "country")
    private String country;

    @JsonProperty("region")
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

}
