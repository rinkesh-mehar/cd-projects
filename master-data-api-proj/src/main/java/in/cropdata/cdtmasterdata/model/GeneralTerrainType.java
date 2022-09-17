package in.cropdata.cdtmasterdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * @author RinkeshKM
 * @Date 09/11/20
 */
@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "general_terrain_type", schema = "cdt_master_data")
public class GeneralTerrainType {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "RegionID")
    private Integer regionId;

    @Column(name = "TerrainType")
    private String terrainType;

    @Column(name = "MinPerKm")
    private BigDecimal minPerKm;

    @Column(name = "Status")
    private String status;

}

