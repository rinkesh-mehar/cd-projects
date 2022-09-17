/**
 * 
 */
package in.cropdata.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * @author vivek-cropdata
 *
 */
@Data
@Entity
@Table(name = " rl_aggrement_document", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class RlAggrementDocument {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "DocumentUrl")
	private String documentUrl;

	/**
	 * @param documentUrl
	 */
	public RlAggrementDocument(String documentUrl) {
		this.documentUrl = documentUrl;
	}

}
