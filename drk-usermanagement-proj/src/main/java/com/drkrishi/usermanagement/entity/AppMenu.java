package com.drkrishi.usermanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "app_menu")
public class AppMenu  implements Serializable {

	 /** Primary key. */
	 protected static final String PK = "id";
	 
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=10)
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="display_name")
	private String display_name;
	
	@Column(name="url")
	private String url;
	
	@Column(name="platform")
	private int platform;
	
	@Column(name="parent_menu_id")
	private int parent_menu_id;
	
	@Column(name="icon")
	private String icon;
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	@Column(name="sequence")
	private int sequence;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplay_name() {
		return display_name;
	}

	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public int getParent_menu_id() {
		return parent_menu_id;
	}

	public void setParent_menu_id(int parent_menu_id) {
		this.parent_menu_id = parent_menu_id;
	}

	public AppMenu() {}
	
	public AppMenu(int id, String name, String display_name, String url, int platform, int parent_menu_id, String icon, int sequence) {
		this.id = id;
		this.name = name;
		this.display_name = display_name;
		this.url = url;
		this.platform = platform;
		this.parent_menu_id = parent_menu_id;
		this.icon=icon;
		this.sequence=sequence;
	}
	
	
	
	
	
}
