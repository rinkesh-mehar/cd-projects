/**
 * 
 */
package in.cropdata.farmers.app.gstmTransitory.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.Data;

/**
 * @author cropdata-Aniket Naik
 *
 */
@Data
@Entity
@Table(name = "phenophase_group",schema = "gstm_transitory")
public class PhenophaseGroupModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID",nullable = false)
	private Integer id;
	
	@Column(name = "SeasonID")
	private Integer seasonId;
	
	@Column(name = "DistrictCode")
	private Integer districtCode;
	
	@Column(name = "CommodityID")
	private Integer commodityId;
	
	@Column(name = "VarietyID")
	private Integer varietyId;
	
	@Column(name = "PhenophaseID")
	private Integer phenophaseId;
	
	@Column(name = "SowingWeek")
	private Integer sowingWeek;
	
	@Column(name = "SowingYear")
	private Integer sowingYear;
	
	@Column(name = "NextCalculationDate")
	private Date nextCalculationDate;

}
