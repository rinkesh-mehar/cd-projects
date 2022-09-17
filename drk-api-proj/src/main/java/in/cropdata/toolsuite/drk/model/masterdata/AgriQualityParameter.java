package in.cropdata.toolsuite.drk.model.masterdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @project DRK
 * @created 16/02/2021 - 4:55 PM
 */

@Entity
@Table(name = "agri_quality_parameter")
public class AgriQualityParameter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonProperty("ID")
    private Integer id;

    @Column(name = "Name")
    private String name;
}
