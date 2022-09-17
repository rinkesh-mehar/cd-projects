package in.cropdata.toolsuite.drk.model.tileassignment;

import java.math.BigInteger;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Entity(name = "flipbook_benchmark_images")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BenchmarkImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

//    @Column(name = "CaseID")
//    private BigInteger caseId;
   
    @Column(name = "CommodityID")
    private Integer commodityId;
   
    @Transient
    private String commodityName;
     
//    @Column(name = "PhenophaseID")
//    private Integer phenophaseId;
    
    @Transient
    private String phenophaseName;
    
    @Column(name = "PlantPartID")
    private Integer plantPartId;
    
    @Transient
    private String plantPartName;
   
    @Column(name = "StressTypeID")
    private Integer stressTypeId;
    
    @Transient
    private String stressTypeName;
    
    @Column(name = "StressID")
    private Integer stressId;
    
    @Transient
    private String stressName;
   
//    @Column(name = "StressStageID")
//    private Integer stressStageId;

    @Transient
    private String stressStageName;
   
    @Column(name = "ImageID")
    private String imageId;
    
    @Column(name = "ImageUrl")
    private String imageUrl;
    
    @Column(name = "IsTagged")
    private boolean isTagged;
    
}
