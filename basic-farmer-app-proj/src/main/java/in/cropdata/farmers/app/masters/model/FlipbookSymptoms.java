package in.cropdata.farmers.app.masters.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 12:19 PM
 */

@Data
@Entity
@Table(name = "flipbook_symptoms", schema = "cdt_master_data")
public class FlipbookSymptoms {

    @Id
    @JsonProperty("ID")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "CommodityID")
    private Integer CommodityID;

    @Column(name = "PhenophaseID")
    private Integer PhenophaseID;

    @Column(name = "PlantPartID")
    private Integer plantPartID;

    @Column(name = "StressTypeID")
    private Integer stressTypeID;

    @Column(name = "StressID")
    private Integer StressID;

    @Column(name = "StressStageID")
    private Integer stressStageID;

    @Column(name = "Symptom")
    private String symptom;

    @Column(name = "GenericImage")
    private String genericImage;

    @Transient
    private Timestamp CreatedAt;

    @Transient
    private Integer UpdatedAt;

    @Transient
    private Integer Status;
}
