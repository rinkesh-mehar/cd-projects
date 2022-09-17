package in.cropdata.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for fetching/updating reports data.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@Entity
@Table(name = "reports", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class Reports {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "PlatformID")
	private Integer PlatformID;

	@Column(name = "Title")
	private String title;

	@Column(name = "FileUrl")
	private String fileUrl;
	
	@Column(name = "Authenticate")
	private String authenticate;

	@Transient
	private String platform;
	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String status;
}
