package com.drkrishi.rlt.model;

import java.util.List;

public class Question {
	private String formControlName;
	private String name;
	private String type;
	private List<String> values;
	private String selected;
	
	public String getFormControlName() {
		return formControlName;
	}
	public void setFormControlName(String formControlName) {
		this.formControlName = formControlName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getValues() {
		return values;
	}
	public void setValues(List<String> values) {
		this.values = values;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	
}
