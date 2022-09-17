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
@Table(name = "rl_user_aggrement", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class RlUserAggrement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "RlUserID")
	private Integer rlUserId;

	@Column(name = "AggrementDocumentID")
	private Integer agreementDocumentId;

	@Column(name = "Status")
	private String status;

}
