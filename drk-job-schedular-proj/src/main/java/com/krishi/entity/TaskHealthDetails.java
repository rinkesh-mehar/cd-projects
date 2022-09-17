package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.krishi.model.EntityModel;

import java.io.Serializable;

@Entity
@Table(name = "task_health_details")
public class TaskHealthDetails implements Serializable, EntityModel {

	/** Primary key. */
	protected static final String PK = "id";

	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "task_id", precision = 10)
	private String taskId;
	@Column(name = "question_id", precision = 10)
	private int questionId;
	@Column(name = "selected_answer", length = 255)
	private String selectedAnswer;
//    @Column(length=100)
//    private String coordinate;
//    
	@Column(name = "spot_id", precision = 10)
	private String spotId;

	/** Default constructor. */
	public TaskHealthDetails() {
		super();
	}

	/**
	 * Access method for id.
	 *
	 * @return the current value of id
	 */
	public String getId() {
		return id;
	}

	public String getSpotId() {
		return spotId;
	}

	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}

	/**
	 * Setter method for id.
	 *
	 * @param aId the new value for id
	 */
	public void setId(String aId) {
		id = aId;
	}

	/**
	 * Access method for taskId.
	 *
	 * @return the current value of taskId
	 */
	public String getTaskId() {
		return taskId;
	}

	/**
	 * Setter method for taskId.
	 *
	 * @param aTaskId the new value for taskId
	 */
	public void setTaskId(String aTaskId) {
		taskId = aTaskId;
	}

	/**
	 * Access method for questionId.
	 *
	 * @return the current value of questionId
	 */
	public int getQuestionId() {
		return questionId;
	}

	/**
	 * Setter method for questionId.
	 *
	 * @param aQuestionId the new value for questionId
	 */
	public void setQuestionId(int aQuestionId) {
		questionId = aQuestionId;
	}

	/**
	 * Access method for selectedAnswer.
	 *
	 * @return the current value of selectedAnswer
	 */
	public String getSelectedAnswer() {
		return selectedAnswer;
	}

	/**
	 * Setter method for selectedAnswer.
	 *
	 * @param aSelectedAnswer the new value for selectedAnswer
	 */
	public void setSelectedAnswer(String aSelectedAnswer) {
		selectedAnswer = aSelectedAnswer;
	}

	/**
	 * Access method for coordinate.
	 *
	 * @return the current value of coordinate
	 */
//    public String getCoordinate() {
//        return coordinate;
//    }
//
//    /**
//     * Setter method for coordinate.
//     *
//     * @param aCoordinate the new value for coordinate
//     */
//    public void setCoordinate(String aCoordinate) {
//        coordinate = aCoordinate;
//    }

	/**
	 * Compares the key for this instance with another TaskHealthDetails.
	 *
	 * @param other The object to compare to
	 * @return True if other object is instance of class TaskHealthDetails and the
	 *         key objects are equal
	 */
	private boolean equalKeys(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TaskHealthDetails)) {
			return false;
		}
		TaskHealthDetails that = (TaskHealthDetails) other;
		if (this.getId() != that.getId()) {
			return false;
		}
		return true;
	}

	/**
	 * Compares this instance with another TaskHealthDetails.
	 *
	 * @param other The object to compare to
	 * @return True if the objects are the same
	 */
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof TaskHealthDetails))
			return false;
		return this.equalKeys(other) && ((TaskHealthDetails) other).equalKeys(this);
	}

}
