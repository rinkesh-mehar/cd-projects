package in.cropdata.cdtmasterdata.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "agri_agrochemical")
public class AgriAgrochemical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "Name")
    private String name;

    //	@Column(name = "UpdatedAt")
    @Transient
    private Date updatedAt;

    //	@Column(name = "CreatedAt")
    @Transient
    private Date createdAt;

    @Column(name = "Status")
    private String status;

}
