package in.cropdata.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for fetching/updating news data.
 * 
 * @author PranaySK
 * @since 1.0
 */

@Data
@Entity
@Table(name = "news", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
public class News {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "PlatformID")
	private Integer platformId;

	@Column(name = "Subject")
	private String title;

	@Column(name = "Description")
	private String description;

	@Column(name = "ExternalUrl")
	private String externalUrl;

	@Column(name = "ImageUrl")
	private String imageUrl;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
	@Column(name = "PublishedDate")
	private String publishedDate;

	@Column(name = "Source")
	private String source;

	@Column(name = "LatestNews")
	private String latestNews;
	
	@Column(name = "Priority")
	private Integer priority;

	@Transient
	private String platform;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	private String date;

	@Transient
	private String status;
}
