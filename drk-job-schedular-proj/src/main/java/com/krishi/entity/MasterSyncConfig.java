package com.krishi.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author cropdata-Aniket Naik
 *
 */

@Entity
@Table(name = "master_data_sync_config")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MasterSyncConfig implements Serializable {

	

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private Integer id;

	@Column(name = "screen")
	private Integer screen;

	@Column(name = "role")
	private Integer role;

	@Column(name = "label")
	private String label;

	@Column(name = "url")
	private String url;

	@Column(name = "table")
	private String table;

	@Column(name = "field_mapping")
	private String fieldMapping;

	@Column(name = "zipping_level")
	private String zippingLevel;

	@Column(name = "sync_frequency")
	private Integer syncFrequency;

	@Column(name = "select_query")
	private String query;

	@Column(name = "download_in_android")
	private String downloadInAndroid;
	
	@Column(name = "entity_name")
	private String entityName;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getScreen() {
		return screen;
	}

	public void setScreen(Integer screen) {
		this.screen = screen;
	}

	public Integer getRole() {
		return role;
	}

	public void setRole(Integer role) {
		this.role = role;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getFieldMapping() {
		return fieldMapping;
	}

	public void setFieldMapping(String fieldMapping) {
		this.fieldMapping = fieldMapping;
	}

	public String getZippingLevel() {
		return zippingLevel;
	}

	public void setZippingLevel(String zippingLevel) {
		this.zippingLevel = zippingLevel;
	}

	public Integer getSyncFrequency() {
		return syncFrequency;
	}

	public void setSyncFrequency(Integer syncFrequency) {
		this.syncFrequency = syncFrequency;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getDownloadInAndroid() {
		return downloadInAndroid;
	}

	public void setDownloadInAndroid(String downloadInAndroid) {
		this.downloadInAndroid = downloadInAndroid;
	}
	
	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public MasterSyncConfig() {
		super();
	}
	
	

}
