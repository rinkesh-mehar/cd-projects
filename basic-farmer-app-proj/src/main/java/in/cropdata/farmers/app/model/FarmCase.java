/**
 * 
 */
package in.cropdata.farmers.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author RinkeshKM
 * @Date 05/02/21
 */
@Data
@Entity
@Table(name = "farm_case", schema = "farmers_app")
public class FarmCase {

	@Id
	@Column(name = "ID",nullable = false)
	@JsonProperty("ID")
	private String ID;

	@Column(name = "FarmID")
	private String farmID;

	@Column(name = "CropType")
	private Integer cropType;
	
	@Transient
	private Timestamp createdAt;
	
	@Transient
	private Timestamp updatedAt;
}
