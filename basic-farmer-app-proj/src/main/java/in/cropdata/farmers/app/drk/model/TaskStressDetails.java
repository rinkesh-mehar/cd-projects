package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Data
@Entity
@Table(name = "task_stress_details", schema = "drkrishi_ts")
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class TaskStressDetails {

    @Id
    @JsonProperty("ID")
    @Column(name = "ID", nullable = false, unique = true)
    private String id;

    @Column(name = "task_id")
    private String taskID;

    @Column(name = "symptom_id")
    private Integer symptomID;

    @Column(name = "front_photo")
    private String frontPhoto;

    @Column(name = "spot_id")
    private String spotID;

    @Column(name = "RegionID")
    private Integer RegionID;

    @Column(name = "case_id")
    private String caseID;

    @Column(name = "front_severity_value")
    private Integer frontSeverityValue;

    @Transient
    private List<Map<String, String>> symptomIDStressImage;

    @Transient
    private String advisoryType;

    @Transient
    private Timestamp createdAt;

    @Transient
    private Timestamp updatedAt;
    
    @Transient
    private List<Integer> symptomIDs;

}
