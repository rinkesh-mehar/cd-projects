package com.krishi.entity;

import com.krishi.model.EntityModel;
import com.microsoft.applicationinsights.core.dependencies.javaxannotation.RegEx;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author RinkeshKM
 * @project JobScheduler
 * @created 04/09/2021 - 4:55 PM
 */

@Entity
@Table(name = "task_spot_health")
public class TaskSpotHealth implements Serializable, EntityModel {

    /**
     * Primary key.
     */
    protected static final String PK = "id";

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "question_id", precision = 10)
    private int questionId;
    @Column(name = "input_values", length = 10)
    private String inputValues;
    @Column(name = "task_spot_id")
    private String taskSpotId;

    /**
     * Default constructor.
     */
    public TaskSpotHealth() {
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

    public String getTaskSpotId() {
        return taskSpotId;
    }

    public void setTaskSpotId(String spotId) {
        this.taskSpotId = spotId;
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
    public String getInputValues() {
        return inputValues;
    }

    /**
     * Setter method for selectedAnswer.
     *
     * @param aSelectedAnswer the new value for selectedAnswer
     */
    public void setInputValues(String aSelectedAnswer) {
        inputValues = aSelectedAnswer;
    }

    /**
     * Access method for coordinate.
     *
     * @return the current value of coordinate
     */

    /**
     * Compares the key for this instance with another TaskHealthDetails.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class TaskHealthDetails and the
     * key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TaskSpotHealth)) {
            return false;
        }
        TaskSpotHealth that = (TaskSpotHealth) other;
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
        if (!(other instanceof TaskSpotHealth))
            return false;
        return this.equalKeys(other) && ((TaskSpotHealth) other).equalKeys(this);
    }

}
