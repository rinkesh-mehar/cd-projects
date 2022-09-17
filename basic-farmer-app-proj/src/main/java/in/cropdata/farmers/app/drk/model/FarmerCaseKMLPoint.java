package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 12/03/2021 - 2:12 PM
 */

@Data
@Entity
@Table (name = "farmer_case_kmlpoints", schema = "drkrishi_ts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class FarmerCaseKMLPoint {

    @Id
    @JsonProperty("ID")
    @Column(name = "ID",nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer ID;

    @Column(name = "farm_id")
    private String farmId;

    @Column(name = "case_id")
    private String caseID;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;
}
