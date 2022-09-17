package in.cropdata.farmers.app.gstmCaseMap.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 23/03/2021 - 3:40 PM
 */

@Data
@Entity
@Table(name = "case_tile_mapping_zl20",schema = "gstm_case_map")
public class CaseTileMappingZL20 {

    @Id
    @JsonProperty("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "TileID")
    private String tileID;

    @Column(name = "TilePID")
    private String tilePID;

    @Column(name = "CaseID")
    private String caseID;

    @Column(name = "RegionID")
    private String regionID;

    @Column(name = "SubRegionID")
    private String subRegionID;

    @Transient
    private String CreatedAt;

    @Transient
    private String UpdatedAt;

    @Transient
    private String Status;

}

