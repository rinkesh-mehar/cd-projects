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

@Data
@Entity
@Table(name = "agm_applicant_type", schema = "cdt_master_data")
@JsonInclude(value = Include.NON_NULL)
public class AgmApplicantType {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "Name")
	private String name;
	
	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;
	
	
	@Transient
	private String status;

}
