package in.cropdata.cdtmasterdata.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name = "ground_truth_zl20")
public class ValidateStressGroundTruth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CaseID")
    private String caseid;

    @Column(name = "Status")
    private String status;
}
