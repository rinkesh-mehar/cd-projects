package in.cropdata.cdtmasterdata.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author RinkeshKM
 * @Date 30/07/20
 */

@Entity
@Table(name = "agri_band", schema = "cdt_master_data")
@Data
public class Band implements Serializable {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "Name")
    private String name;

    @Column(name = "Status")
    private String status;


    @Transient
    private Date updatedAt;

    @Transient
    private Date createdAt;
}
