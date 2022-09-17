package com.krishi.service;

import com.krishi.repository.GenericRepository;

public abstract class Generic<T> implements GenericRepository<T> {

	Object className;

	public Generic(Object className) {
		super();
		this.className = className;
	}
	
	public Object getClassName() {
		return className;
	}

	public void setClassName(Object className) {
		this.className = className;
	}

	public Generic() {
		super();
	}	

}
