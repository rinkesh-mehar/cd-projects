package in.cropdata.farmers.app.masters.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.farmers.app.masters.model
 * @date 05/02/21
 * @time 11:42 AM
 */
@Data
@Entity
@Table(name = "geo_panchayat", schema = "cdt_master_data")
public class GeoPanchayat
{
    @JsonProperty("ID")
    @Id
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "TehsilCode")
    private Integer tehsilCode;

    @Column(name = "PanchayatCode")
    private Integer panchayatCode;

    @Column(name = "RegionID")
    private Integer regionID;

    @Column(name = "Name")
    private String  name;
}
