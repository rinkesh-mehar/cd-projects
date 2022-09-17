/**
 * 
 */
package in.cropdata.farmers.app.drk.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @Date 05/02/21
 */
@Data
@Entity
@Table(name = "image_store", schema = "drkrishi_ts")
public class ImageStore {

	@Id
	@Column(name = "ID",nullable = false)
	@JsonProperty("ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer ID;

	@Column(name = "MetaID")
	private Integer metaID;

	@Column(name = "SourceID")
	private String sourceID;

	@Column(name = "ImageUrl")
	private String imageUrl;
	
	@Column(name= "FarmerID")
	private String farmerID;

}
