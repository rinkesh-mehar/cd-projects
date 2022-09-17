package in.cropdata.farmers.app.gstmTransitory.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 23/03/2021 - 4:06 PM
 */

@Data
@Entity
@Table(name = "ground_truth_zl20",schema = "gstm_transitory")
public class GroundTruthZL20 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID",nullable = false)
    @JsonProperty("ID")
    private Integer id;

    @Column(name = "StateCode")
    private Integer stateCode;

    @Column(name = "TileID")
    private String tileID;

    @Column(name = "VillageCode")
    private Integer villageCode;

    @Column(name = "CaseID")
    private String caseID;

    @Column(name = "StressID")
    private Integer StressID;

    @Column(name = "ServerityID")
    private Integer ServerityID;

    @Column(name = "SampleID")
    private String SampleID;

    @Column(name = "WeekNo")
    private Integer weekNo;

    @Column(name = "Year")
    private Integer year;

    @Column(name = "SymptomID")
    private Integer symptomID;

    @Transient
    private Timestamp createdAt;

    @Transient
    private Timestamp updatedAt;

}
