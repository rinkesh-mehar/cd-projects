package in.cropdata.toolsuite.drk.model.lh;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.toolsuite.drk.model.lh
 * @date 18/02/21
 * @time 1:00 PM
 */
@Data
@Entity(name = "lh_warehouse")
public class LhWarehouse
{
    @Id
    @Column(name = "ID")
    private Integer id;

    @Column(name = "RegionID")
    private Integer regionId;

    @Column(name = "Name")
    private String name;

    @Column(name = "AddressID")
    private Integer addressId;

    @Transient
    private Integer stateCode;

    @Transient
    private Integer districtCode;

    @Transient
    private Integer tehsilCode;

    @Transient
    private double latitude;

    @Transient
    private double longitude;

    @Transient
    private String address;
}
