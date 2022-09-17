package com.drkrishi.prm.model;

import java.util.Arrays;

public class EmailTemplate {
	private int id;

	private byte[] content;

	private String title;

	public EmailTemplate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte[] getContent() {
		return this.content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(content);
		result = prime * result + id;
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		EmailTemplate other = (EmailTemplate) obj;
		if (!Arrays.equals(content, other.content))
			return false;
		if (id != other.id)
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "EmailTemplate [id=" + id + ", content=" + Arrays.toString(content) + ", title=" + title + "]";
	}

	public EmailTemplate(int id, byte[] content, String title) {
		super();
		this.id = id;
		this.content = content;
		this.title = title;
	}

}