package in.cropdata.farmers.app.masters.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @project BasicFarmersAppAPI
 * @created 22/03/2021 - 12:19 PM
 */

@Data
@Entity
@Table(name = "agri_field_activity", schema = "cdt_master_data")
public class FieldActivity {

    @Id
    @JsonProperty("ID")
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @Column(name = "SeasonID")
    private Integer seasonID;

    @Column(name = "CommodityID")
    private Integer CommodityID;

    @Column(name = "PhenophaseID")
    private Integer PhenophaseID;

    @Column(name = "Name")
    private String Name;

    @Column(name = "Description")
    private String Description;

    @Transient
    private Timestamp CreatedAt;

    @Transient
    private Integer UpdatedAt;

    @Transient
    private Integer Status;
}
