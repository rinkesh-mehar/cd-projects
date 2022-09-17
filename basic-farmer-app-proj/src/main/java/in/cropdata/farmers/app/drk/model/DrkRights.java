package in.cropdata.farmers.app.drk.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name = "rights", schema = "drkrishi_ts")
public class DrkRights {
    @Id
    @Column(name="id")
    private String id;

    @Column(name="version_number")
    private Integer versionNumber;

    @Column(name="case_id")
    private String caseId;

    @Column(name="current_quantity")
    private Float currentQuantity;

    @Column(name="estimated_quantity")
    private Float estimatedQuantity;

    @Column(name="standard_quantity")
    private Float standardQuantity;

    @Column(name="allowable_variance_qty_pos")
    private Float allowableVarianceQtyPos;

    @Column(name="allowable_variance_qty_neg")
    private Float allowableVarianceQtyNeg;

    @Column(name="allowable_variance_qty_pos_per")
    private Float allowableVarianceQtyPosPer;

    @Column(name="allowable_variance_qty_neg_per")
    private Float allowableVarianceQtyNegPer;

    @Column(name="estimated_quality")
    private String estimatedQuality;

    @Column(name="current_quality")
    private String currentQuality;

    @Column(name="allowable_variance_quality")
    private String allowableVarianceQuality;

    @Column(name="mbep")
    private Double mbep;

    @Column(name="domestic_restriction")
    private String domesticRestriction;

    @Column(name="international_restriction")
    private String internationalRestriction;

    @Column(name="delivery_date")
    private Date deliveryDate;

    @Column(name="logistic_hub_id")
    private String logisticHubId;

    @Column(name="logistic_hub_address")
    private String logisticHubAddress;

    @Column(name="farmer_default")
    private String farmerDefault;

}
