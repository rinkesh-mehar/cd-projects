package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @Date 16/04/21
 */
@Data
@Entity
@Table(name = "village", schema = "drkrishi_ts")
public class Village {

	@Id
	@Column(name = "id",nullable = false)
	@JsonProperty("ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;

	@Column(name = "panchayat_id")
	private Integer panchayatID;

	@Column(name = "code")
	private Integer code;

	@Column(name = "region_id")
	private Integer regionID;

	@Column(name = "subregion_id")
	private Integer subregionID;

	@Column(name = "name")
	private String name;

	@Column(name = "pincode")
	private Integer pincode;

	@Column(name = "latitude")
	private Double latitude;

	@Column(name = "longitude")
	private Double longitude;

	@Column(name = "ring_number")
	private Integer ringNumber;

	@Column(name = "comment")
	private String comment;

}
