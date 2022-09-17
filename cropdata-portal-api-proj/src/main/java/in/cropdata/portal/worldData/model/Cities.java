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
 * @date 13/12/21
 * @time 5:17 PM
 */
@Table
@Data
@Entity(name = "cities")
public class Cities
{
    @Id
    @Column (name = "ID")
    private Integer id;

    @Column (name = "country")
    private String country;

    @Column (name = "region")
    private String region;

    @Column (name = "name")
    private String name;

}
