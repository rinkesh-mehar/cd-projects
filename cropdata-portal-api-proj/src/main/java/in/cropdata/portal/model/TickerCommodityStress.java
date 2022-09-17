package in.cropdata.portal.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@Entity
@Table(name = "ticker_commodity_stress", schema = "cdt_website")
public class TickerCommodityStress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "TickerID")
    private Integer tickerId;

    @Column(name = "StressID")
    private Integer stressId;

    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;

    @Column(name = "Status")
    private String status;

}
