package com.krishi.fls.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name = "task_aerial_photo")
public class TaskAerialPhotos {

	@Id
	@Column(name = "id")
	private String id;
	
	@Column(name = "taskId")
	private String taskId;
	
	@Column(name = "photos")
	private String photos;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String gettaskId() {
		return taskId;
	}

	public void settaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((photos == null) ? 0 : photos.hashCode());
		result = prime * result + ((taskId == null) ? 0 : taskId.hashCode());
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
		TaskAerialPhotos other = (TaskAerialPhotos) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (photos == null) {
			if (other.photos != null)
				return false;
		} else if (!photos.equals(other.photos))
			return false;
		if (taskId == null) {
			if (other.taskId != null)
				return false;
		} else if (!taskId.equals(other.taskId))
			return false;
		return true;
	}

	
	
	public TaskAerialPhotos() {
		super();
	}

	public TaskAerialPhotos(String id, String taskId, String photos) {
		super();
		this.id = id;
		this.taskId = taskId;
		this.photos = photos;
	}

	@Override
	public String toString() {
		return "TaskAerialPhotos [id=" + id + ", taskId=" + taskId + ", photos=" + photos + "]";
	}
	
	

	
}
