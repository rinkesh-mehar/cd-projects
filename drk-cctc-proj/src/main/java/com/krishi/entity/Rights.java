package com.krishi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @Date 27/07/21
 */

@Data
@Entity
@IdClass(RightsCompositeKey.class)
@Table(name = "rights")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Rights {

    @Id
    private String id;

    @Id
    private Integer versionNumber;

    private Double deliverableQuantity;

    private Double harvestedQuantity;


    private String caseId;


    private Integer currentQuantity;


    private Integer estimatedQuantity;


    private Integer standardQuantity;


    private Integer allowableVarianceQtyPos;


    private Integer allowableVarianceQtyNeg;


    private Double allowableVarianceQtyPosPer;


    private Double allowableVarianceQtyNegPer;

    private String estimatedQuality;

    private String currentQuality;

    private String allowableVarianceQuality;

    private Double mbep;

    private String domesticRestriction;

    private String internationalRestriction;

    private Timestamp deliveryDateTime;

    private String logisticHubId;

    private String logisticHubAddress;

    private String farmerDefault;

    private String splitField;

    private String geographicallyAdjustent;

    private String riskReport;

    private String rightCertificate;

    private Integer recordCreatedBy;

    private Timestamp recordDateTime;

    private String status;

    private Timestamp statusReceivingDate;

    private String comments;

    private String lotId;

    private Boolean isVerified;
    private String transactionId;
    private Integer phenophaseId;
    private String stage;
    private Double dueAmount;
    private Double amountCollected;
    private Double contractedPrice;
    private Date expectedDeliveryDate;
    private Double totalWeighbridgeWeight;
    private Double totalWeighmentWeight;
    private Integer numberOfBagsFarmer;
    private Integer numberOfBagsCounting;
    private Integer numberOfBagsUnloaded;
    private Integer numberOfVehicles;
    private String rightQuality;
    private String reportingPassQrCode;
    private String exitPassQrCode;
    private String sampleSealQrCode;
    private Integer stackNo;
    private String sealBagPhotoUrl;
    private String paymentStatus;

}
