package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author RinkeshKM
 * @Date 27/07/21
 */
@Embeddable
public class RightsCompositeKey  implements Serializable{

	private String id;

	private Integer versionNumber;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getVersionNumber() {
		return versionNumber;
	}

	public void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	public RightsCompositeKey() {
	}

	public RightsCompositeKey(String id, Integer versionNumber) {
		this.id = id;
		this.versionNumber = versionNumber;
	}
}
