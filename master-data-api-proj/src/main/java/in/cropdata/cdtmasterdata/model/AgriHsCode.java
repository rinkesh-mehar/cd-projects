package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "agri_hs_code")
@Data
public class AgriHsCode {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "HSCode")
	private String hsCode;
	
	@Column(name = "CommodityID")
	private Integer commodityId;

	@Transient
	private String commodity;	

	@Column(name = "GeneralCommodityID")
	private Integer generalCommodityId;

	@Transient
	private String generalCommodity;	

	
	@Column(name = "CommodityClassID")
	private Integer commodityClassId;

	@Transient
	private String commodityClass;	
	
	@Column(name = "UomID")
	private Integer uomId;

	@Transient
	private String uom;	
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
