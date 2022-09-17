/**
 * 
 */
package in.cropdata.gateway.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * @author Vivek Gajbhiye
 *
 */
@Entity
@Table(name = "cropdata_ms_routes", schema = "route_gateway")
@Data
public class Zuul {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(name = "id")
	private Integer id;

	@Column(name = "path")
	private String path;

	@Column(name = "serviceId")
	private String serviceId;

	@Column(name = "url")
	private String url;
}
