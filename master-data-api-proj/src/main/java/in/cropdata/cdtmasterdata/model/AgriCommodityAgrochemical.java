package in.cropdata.cdtmasterdata.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Data
@Entity(name = "agri_commodity_agrochemical")
public class AgriCommodityAgrochemical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "AgrochemicalTypeID")
    private int agrochemicalTypeId;

    @Transient
    private String agrochemicalType;

    @Column(name = "Name")
    private String name;

    @Column(name = "WaitingPeriod")
    private int waitingPeriod;

    @Column(name = "CommodityID")
    private int commodityId;

    @Transient
    private String commodity;

    @Column(name = "StressTypeID")
    private int stressTypeId;

    @Column(name = "CIBRCApproved")
    private String cibrcApproved;

    @Transient
    private Set<AgriStress> stressNameList = new HashSet<>();

    //	@Column(name = "UpdatedAt")
    @Transient
    private Date updatedAt;

    //	@Column(name = "CreatedAt")
    @Transient
    private Date createdAt;

    @Column(name = "Status")
    private String status;

}
