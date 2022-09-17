package in.cropdata.toolsuite.drk.model.tileassignment;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "geo_subregion")
@Data
public class GeoSubRegion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SubRegionID")
    private long subRegionID;
    
    @Column(name = "RegionID")
    private int regionID;
    
    @Column(name = "Latitude")
    private float latitude;
    
    @Column(name = "Longitude")
    private float longitude;
    
//    @Column(name = "Name")
//    private String Name;
//    
//    @Column(name = "Description")
//    private String description;
    
    @Transient
    private int totalTask; 
    
    @Transient
    private BigDecimal taskCompleteness; 
    
}
