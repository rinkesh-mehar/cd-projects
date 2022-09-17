package in.cropdata.portal.worldData.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.portal.worldData.model
 * @date 13/12/21
 * @time 6:15 PM
 */
@Table
@Data
@Entity(name = "countries")
public class Countries
{
    @Id
    @Column(name = "code")
    private String code;

    @Column(name = "name")
    private String name;

    @Column(name = "countrycode")
    private String countryCode;
}
