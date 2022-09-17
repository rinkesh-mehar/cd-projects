package in.cropdata.toolsuite.drk.model.masterdata;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @project DRK
 * @created 16/02/2021 - 5:47 PM
 */


@Entity
@Table(name = "zonal_quality_parameter_range ")
public class AgriQualityParameterRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    @JsonProperty("ID")
    private Integer id;

    @Column(name = "StateCode")
    private Integer stateCode;

    @Column(name = "ZonalCommodityID")
    private Integer commodityID;

    @Column(name = "ParamerterID")
    private Integer paramerterID;

    @Column(name = "BandID")
    private String bandID;

    @Column(name = "QualityCode")
    private String qualityCode;

    @Column(name = "Min")
    private Double min;

    @Column(name = "Max")
    private Double max;
}
