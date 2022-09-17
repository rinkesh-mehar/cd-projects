package in.cropdata.farmers.app.masters.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 12/02/2021 - 3:06 PM
 */
@Data
@Entity
@Table(name = "farmer_id_proof", schema = "cdt_master_data")
public class FarmerIdProof {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    @JsonProperty("ID")
    private Integer ID;

    @Column(name = "Name")
    private String name;

    @Column(name = "Status")
    private String status;
}
