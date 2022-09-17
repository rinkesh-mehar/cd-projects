package in.cropdata.cdtmasterdata.model;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @Date 12/11/20
 */
@Data
@Entity
@Table(name = "pricing_bc_slope_range", schema = "cdt_master_data")
public class PricingBcSlopeRange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "SlopeMin")
    private BigDecimal slopeMin;

    @Column(name = "SlopeMax")
    private BigDecimal slopeMax;

    @Column(name = "BuyerConstant")
    private BigDecimal buyerConstant;

    @Column(name = "CreatedAt")
    private Timestamp createdAt;

    @Column(name = "UpdatedAt")
    private Timestamp updatedAt;

    @Column(name = "Status")
    private String status;

}
