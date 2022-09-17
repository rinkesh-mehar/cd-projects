package in.cropdata.farmers.app.gstmTransitory.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 5:49 PM
 */

@Data
@Entity
@Table(name = "case_stress_occurrence", schema = "gstm_transitory")
public class CaseStressOccurrence {

    @Id
    @JsonProperty("ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Integer ID;

    @Column(name = "CaseID")
    private String caseID;

    @Column(name = "StressID")
    private Integer stressID;

    @Column(name = "WeekNumber")
    private Integer weekNumber;

    @Column(name = "Year")
    private Integer year;

    @Transient()
    private Timestamp createdAt;

    @Transient()
    private List<Integer> symptomIDs;

    @Transient()
    private Timestamp updatedAt;

}
