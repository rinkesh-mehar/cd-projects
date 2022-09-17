package in.cropdata.farmers.app.masters.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "agri_stress_control_recommendation")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class StressControlRecommendation {
    @JsonProperty("ID")
    @Id
    private Integer id;

    //    @Column(name = "StateCode")
    @Transient
    private Integer stateCode;

    //    @Column(name = "DistrictCode")
    @Transient
    private Integer districtCode;

    //    @Column(name = "CommodityID")
    @Transient
    private Integer commodityID;

    @Transient
    private Integer stressControlMeasureID;

    @Transient
    private String stressControlMeasureName;

    //    @Column(name = "StressID")
    @Transient
    private Integer stressID;

    @Column(name = "Instructions")
    private String instructions;

    @Transient
    private Integer recomendationID;

    @Transient
    private String recommendation;

    @Transient
    private Integer agroChemicalInstructions;

    @Transient
    private Integer agrochemicalInstructionID;

    @Transient
    private Integer agrochemApplicationTypeID;

    @Transient
    private Integer agrochemicalID;

    @Transient
    private Integer dosePerHectare;

    @Transient
    private Integer perHectareUomID;

    @Transient
    private Integer dosePerAcre;

    @Transient
    private Integer perAcreUomID;

    @Transient
    private Integer waterPerHectare;

    @Transient
    private Integer perHectareWaterUomID;

    @Transient
    private Integer waterPerAcre;

    @Transient
    private Integer perAcreWaterUomID;

}
