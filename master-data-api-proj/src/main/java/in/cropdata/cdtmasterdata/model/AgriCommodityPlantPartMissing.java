package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Entity(name = "agri_commodity_plant_part_missing")
@JsonIgnoreProperties
public class AgriCommodityPlantPartMissing {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "CommodityID")
	private int commodityId;
	
	@Transient
	private String commodity;
	
	@Column(name = "PhenophaseID")
	private int phenophaseId; 
	
	@Transient
	private String phenophase;
	
	@Column(name = "PlantPartID")
	private int plantPartId;
	
	@Transient
	private String plantPart;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "FileUrl")
	private String imageURL;
	
	@Column(name = "ImageID")
	private String imageId;
	

}
