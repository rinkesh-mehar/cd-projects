package com.drkrishi.rlt.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="task_health_details")
public class TaskHealthDetails implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    @Column(unique=true, nullable=false, precision=10)
    private String id;
    @Column(name="task_id", precision=10)
    private String taskId;
    @Column(name="question_id", precision=10)
    private int questionId;
    @Column(name="selected_answer", length=255)
    private String selectedAnswer;
//    @Column(length=100)
//    private String coordinate;
//    
    @Column(name="spot_id", precision=10)
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
  
    public String getSpotId() {
		return spotId;
	}

	public void setSpotId(String spotId) {
		this.spotId = spotId;
	}

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

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
     * @return True if other object is instance of class TaskHealthDetails and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
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
        if (!(other instanceof TaskHealthDetails)) return false;
        return this.equalKeys(other) && ((TaskHealthDetails)other).equalKeys(this);
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[TaskHealthDetails |");
        sb.append(" id=").append(getId());
        sb.append("]");
        return sb.toString();
    }

    /**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("id", Integer.valueOf(getId()));
        return ret;
    }

}
