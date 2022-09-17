package in.cropdata.cdtmasterdata.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "pricing_master")
@Data
public class PricingMaster {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "ID")
    private int id;

    @Column(name = "CommodityID")
    private int commodityId;

    @Transient
    private String commodity;

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

    @Transient
    private List<Integer> stateId;

    @Transient
    private Integer stateCode;

    @Transient
    private List<AgriVariety> varietyId;

    @Transient
    private Integer regionId;

    @Column(name = "Msp")
    private Double msp;

    @Column(name = "Mfp")
    private Double mfp;

    @Column(name = "MarginConstant")
    private Double marginConstant;

    @Column(name = "BuyerConstant")
    private Double buyerConstant;

    @Column(name = "SellerConstant")
    private Double sellerConstant;

    @Column(name = "Mbep")
    private Double mbep;

    @Column(name = "Pmp")
    private Double pmp;

    @Column(name = "PriceSpread")
    private Double priceSpread;

    @Transient
    private String screen;
}
