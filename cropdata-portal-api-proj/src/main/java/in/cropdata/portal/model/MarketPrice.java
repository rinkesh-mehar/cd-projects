package in.cropdata.portal.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Entity
@Table(name = "ticker_market_price", schema = "cdt_website")
public class MarketPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CommodityID")
    private Integer commodityId;

    @Column(name = "VarietyID")
    private Integer varietyId;

    @Column(name = "MarketID")
    private Integer marketId;

    @Column(name = "MinPrice")
    private BigDecimal minPrice;

    @Column(name = "MaxPrice")
    private BigDecimal maxPrice;

    @Column(name = "ModalPrice")
    private BigDecimal modalPrice;

    @Transient
    private Integer districtId;

    @Transient
    private Integer stateId;

    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;

    @Column(name = "Status")
    private String status;
}
