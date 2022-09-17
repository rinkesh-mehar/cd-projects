package in.cropdata.cdtmasterdata.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import lombok.Data;

@Entity(name = "regional_language")
@Data
public class RegionalLanguage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "StateCode")
	private int stateCode;
	
	@Transient
	private String state;

	@Column(name = "RegionID")
	private Integer regionId;
	
	@Transient
	private String region;
	
	@Column(name = "LanguageID")
	private int languageId;
	
	@Transient
	private String language;
	
//	@Column(name = "UpdatedAt")
	@Transient
	private Date updatedAt;
	
//	@Column(name = "CreatedAt")
	@Transient
	private Date createdAt;
	
	@Column(name = "Status")
	private String status;
}
