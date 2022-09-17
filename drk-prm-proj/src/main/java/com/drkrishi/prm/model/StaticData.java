package com.drkrishi.prm.model;

public class StaticData {

	private int id;

	private String land_Measurement;

	private int system_Password_Expired_Period;

	private int user_Password_Expired_Period;

	public StaticData() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLand_Measurement() {
		return this.land_Measurement;
	}

	public void setLand_Measurement(String land_Measurement) {
		this.land_Measurement = land_Measurement;
	}

	public int getSystem_Password_Expired_Period() {
		return this.system_Password_Expired_Period;
	}

	public void setSystem_Password_Expired_Period(int system_Password_Expired_Period) {
		this.system_Password_Expired_Period = system_Password_Expired_Period;
	}

	public int getUser_Password_Expired_Period() {
		return this.user_Password_Expired_Period;
	}

	public void setUser_Password_Expired_Period(int user_Password_Expired_Period) {
		this.user_Password_Expired_Period = user_Password_Expired_Period;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((land_Measurement == null) ? 0 : land_Measurement.hashCode());
		result = prime * result + system_Password_Expired_Period;
		result = prime * result + user_Password_Expired_Period;
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
		StaticData other = (StaticData) obj;
		if (id != other.id)
			return false;
		if (land_Measurement == null) {
			if (other.land_Measurement != null)
				return false;
		} else if (!land_Measurement.equals(other.land_Measurement))
			return false;
		if (system_Password_Expired_Period != other.system_Password_Expired_Period)
			return false;
		if (user_Password_Expired_Period != other.user_Password_Expired_Period)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StaticData [id=" + id + ", land_Measurement=" + land_Measurement + ", system_Password_Expired_Period="
				+ system_Password_Expired_Period + ", user_Password_Expired_Period=" + user_Password_Expired_Period
				+ "]";
	}

	public StaticData(int id, String land_Measurement, int system_Password_Expired_Period,
			int user_Password_Expired_Period) {
		super();
		this.id = id;
		this.land_Measurement = land_Measurement;
		this.system_Password_Expired_Period = system_Password_Expired_Period;
		this.user_Password_Expired_Period = user_Password_Expired_Period;
	}

}