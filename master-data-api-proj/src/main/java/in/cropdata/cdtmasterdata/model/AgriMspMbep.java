package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "agri_MSP_MBEP")
@Data
public class AgriMspMbep {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@Column(name = "ID")
	private int id;
	
	@Column(name = "StateCode")
	private int stateCode;
	
	@Transient
	private String state;

	@Column(name = "RegionID")
	private int regionId;
	
	@Transient
	private String region;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "VarietyID")
	private int varietyId;
	
	@Column(name = "PriceTypeID")
	private int priceTypeId;
	
	@Transient
	private String priceType;
	
	@Transient
	private String variety;
	
	@Column(name = "Date")
	private Date date;
	
	@Column(name = "Year")
	private int year;
	
	@Column(name = "PriceValue")
	private float priceValue;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}
