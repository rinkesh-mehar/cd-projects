package com.drkrishi.usermanagement.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_menu")
public class RoleMenu  implements Serializable  {

	 /** Primary key. */
	 protected static final String PK = "id";
	 
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(unique=true, nullable=false, precision=10)
	private int id;
	
	@Column(name="menu_id")
	private int menu_id;
	
	@Column(name="role_id")
	private int role_id;
	
	public RoleMenu() {}
	
	public RoleMenu(int id, int menu_id, int role_id) {
		this.id = id;
		this.menu_id = menu_id;
		this.role_id = role_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMenu_id() {
		return menu_id;
	}

	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	
	
	
}
