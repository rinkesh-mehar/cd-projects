package in.cropdata.toolsuite.drk.model.tileassignment;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity(name = "geo_region")
@Data
public class GeoRegion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RegionID")
	private int regionID;

//    @Column(name = "TileID")
//    private int tileID;

//	@Column(name = "ImageID")
//	private String imageID;

	@Column(name = "FileUrl")
	private String imageUrl;

	/*@Column(name = "MmpkID")
	private String mmpkId;*/

/*	@Column(name = "MmpkUrl")
	private String mmpkUrl;*/

	@Column(name = "StateCode")
	private int stateCode;

	@Column(name = "Latitude")
	private float latitude;

	@Column(name = "Longitude")
	private float longitude;

	@Column(name = "Name")
	private String Name;

	@Column(name = "Description")
	private String description;

	@Column(name = "Onboarded")
	private String onboarded;

	@Column(name = "MapRows")
	private Integer MapRows;

	@Column(name = "MapColumns")
	private Integer MapColumns;

	@Column(name = "UpdatedAt")
	private Date updatedAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CreatedAt")
	private Date createdAt;

	@Column(name = "Status")
	private String status;

}
