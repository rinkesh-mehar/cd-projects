package com.drkrishi.usermanagement.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MenuModel{

	private String name;
	private String display_name;
	private String url;
	private int parent_menu_id;
	private String icon;
	private int sequence;
	
	
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
	
	public int getParent_menu_id() {
		return parent_menu_id;
	}
	public void setParent_menu_id(int parent_menu_id) {
		this.parent_menu_id = parent_menu_id;
	}
	public MenuModel(String name, String display_name, String url, int parent_menu_id, String icon, int sequence) {
	
		this.name = name;
		this.display_name = display_name;
		this.url = url;
		this.parent_menu_id = parent_menu_id;
		this.icon = icon;
		this.sequence=sequence;
	}
}
