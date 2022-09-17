/**
 * 
 */
package in.cropdata.cdtmasterdata.website.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Used for holding bank account details of RL User.
 * 
 * @author PranaySK
 */

@Data
@Entity
@Table(name = "rl_bank_details", schema = "cdt_website")
@JsonInclude(value = Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RlUserBankDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "RlUserID")
	private Integer rlUserId;

	@Column(name = "BankName")
	private String bankName;

	@Column(name = "AccountNumber")
	private String accountNumber;

	@Column(name = "IFSCCode")
	private String ifscCode;

	@Column(name = "BankImageUrl")
	private String bankAccountImageUrl;

	@Transient
	private Date createdAt;

	@Transient
	private Date updatedAt;

	@Transient
	@Column(name = "Status")
	private String status;

}
