package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "gstm_sync")
public class GstmSync {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	private int id;

	@Column(name ="schema_name")
	private String schemaName;
	
	@Column(name ="last_sync")
	private String lastSync;

	public GstmSync() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GstmSync(int id, String schemaName, String lastSync) {
		super();
		this.id = id;
		this.schemaName = schemaName;
		this.lastSync = lastSync;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSchemaName() {
		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName = schemaName;
	}

	public String getLastSync() {
		return lastSync;
	}

	public void setLastSync(String lastSync) {
		this.lastSync = lastSync;
	}

}
