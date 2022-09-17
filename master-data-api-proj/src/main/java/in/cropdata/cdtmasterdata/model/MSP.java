package in.cropdata.cdtmasterdata.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author RinkeshKM
 * @Date 31/07/20
 */
@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MSP implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int ID;

    @Transient
    private Integer CommodityID;

    @Transient
    private Integer SubRegionID;

    @Transient
    private Integer RegionID;

    @Transient
    private Integer VarietyID;

    @Transient
    private Object StateCode;

    @Transient
    private String StateCodes;

    @Transient
    private String Commodity;

    @Transient
    private String States;

    @Transient
    private String StateName;

    @Transient
    private String RegionName;

    @Transient
    private String Status;

    @Transient
    private double Msp;
}
