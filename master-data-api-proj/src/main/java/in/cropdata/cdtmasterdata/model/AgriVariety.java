package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Entity(name = "agri_variety")
@Data
public class AgriVariety {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;

	@Column(name = "CommodityID")
	private int commodityId;

	@Column(name = "Name")
	private String name;
	
	@Column(name = "VarietyCode")
	private String varietyCode;
	
//	@Transient
//	private String hsCode;
	
	@JsonAlias("hsCode")
	@Column(name = "HsCodeId")
	private int hsCodeId;
	
//	@Column(name = "MspGroupID")
//	private int mspGroupId;
//	
//	@Transient
//	private String mspGroup;
	
	@Transient
	private String commodity;
	
	@Column(name = "DomesticRestrictions")
	private String domesticRestrictions;
	
	@Column(name = "InternationalRestrictions")
	private String internationalRestrictions;

//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;

}
