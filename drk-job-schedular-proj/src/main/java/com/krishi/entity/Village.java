package com.krishi.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "village")
public class Village implements Serializable {


    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private Integer id;
    @Column(name="panchayat_id", precision=10)
    private Integer panchayatId;
    @Column(precision=10)
    private Integer code;
    @Column(name="region_id", precision=10)
    private Integer regionId;
    @Column(name="subregion_id", precision=10)
    private String subregionId;
    @Column(length=200)
    private String name;
    @Column(precision=10)
    private Integer pincode;
    @Column(precision=19, scale=8)
    private BigDecimal latitude;
    @Column(precision=19, scale=8)
    private BigDecimal longitude;
    @Column(length=255)
    private String comment;
    @Column(nullable=false, precision=10)
    private Integer status;

    @Column(name = "acz_id")
	private Integer aczId;

	public Integer getAczId() {
		return aczId;
	}

	public void setAczId(Integer aczId) {
		this.aczId = aczId;
	}

	/** added synch processes - Ujwal : Start */
	/**  Ring Number */
    @Column(name = "ring_number")
    private Integer ringNumber;
    
    public Integer getRingNumber() {
		return ringNumber;
	}

	public void setRingNumber(Integer ringNumber) {
		this.ringNumber = ringNumber;
	}
	/** added sync processes - Ujwal : End*/
	
    /** Default constructor. */
    public Village() {
        super();
    }

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPanchayatId() {
		return panchayatId;
	}

	public void setPanchayatId(Integer panchayatId) {
		this.panchayatId = panchayatId;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public String getSubregionId() {
		return subregionId;
	}

	public void setSubregionId(String subregionId) {
		this.subregionId = subregionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getPincode() {
		return pincode;
	}

	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public static String getPk() {
		return PK;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((latitude == null) ? 0 : latitude.hashCode());
		result = prime * result + ((longitude == null) ? 0 : longitude.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((panchayatId == null) ? 0 : panchayatId.hashCode());
		result = prime * result + ((pincode == null) ? 0 : pincode.hashCode());
		result = prime * result + ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((subregionId == null) ? 0 : subregionId.hashCode());
		result = prime * result + ((aczId == null) ? 0 : aczId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Village other = (Village) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (comment == null) {
			if (other.comment != null)
				return false;
		} else if (!comment.equals(other.comment))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (latitude == null) {
			if (other.latitude != null)
				return false;
		} else if (!latitude.equals(other.latitude))
			return false;
		if (longitude == null) {
			if (other.longitude != null)
				return false;
		} else if (!longitude.equals(other.longitude))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (panchayatId == null) {
			if (other.panchayatId != null)
				return false;
		} else if (!panchayatId.equals(other.panchayatId))
			return false;
		if (pincode == null) {
			if (other.pincode != null)
				return false;
		} else if (!pincode.equals(other.pincode))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (subregionId == null) {
			if (other.subregionId != null)
				return false;
		} else if (!subregionId.equals(other.subregionId))
			return false;
		if (aczId == null) {
			if (other.aczId != null)
				return false;
		} else if (!aczId.equals(other.aczId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Village{" +
				"id=" + id +
				", panchayatId=" + panchayatId +
				", code=" + code +
				", regionId=" + regionId +
				", subregionId=" + subregionId +
				", name='" + name + '\'' +
				", pincode=" + pincode +
				", latitude=" + latitude +
				", longitude=" + longitude +
				", comment='" + comment + '\'' +
				", status=" + status +
				", aczId=" + aczId +
				", ringNumber=" + ringNumber +
				'}';
	}
}
