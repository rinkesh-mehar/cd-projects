package in.cropdata.portal.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ticker_commodity", schema = "cdt_website")
public class TickerCommodity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CommodityID")
    private Integer commodityId;

    @Column(name = "PhenophaseID")
    private Integer phenophaseId;

    @Transient
    private List<Integer> tickerCommodityStressList;

    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;

    @Column(name = "Status")
    private String status;
}
