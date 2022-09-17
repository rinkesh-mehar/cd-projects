package com.krishi.fls.entity;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name="task_transaction")
public class TaskTransaction implements Serializable {

    /** Primary key. */
    protected static final String PK = "id";

    @Id
    private String id;
    @Column(name="task_id", nullable=false, precision=10)
    private String taskId;
    @Column(name="unit_price", nullable=false, precision=5, scale=2)
    private float unitPrice;
    @Column(name="payment_mode", nullable=false, precision=10)
    private int paymentMode;
    @Column(name="amount_paid", nullable=false, precision=8, scale=2)
    private float amountPaid;

    /** Default constructor. */
    public TaskTransaction() {
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
     * Access method for unitPrice.
     *
     * @return the current value of unitPrice
     */
    public float getUnitPrice() {
        return unitPrice;
    }

    /**
     * Setter method for unitPrice.
     *
     * @param aUnitPrice the new value for unitPrice
     */
    public void setUnitPrice(float aUnitPrice) {
        unitPrice = aUnitPrice;
    }

    /**
     * Access method for paymentMode.
     *
     * @return the current value of paymentMode
     */
    public int getPaymentMode() {
        return paymentMode;
    }

    /**
     * Setter method for paymentMode.
     *
     * @param aPaymentMode the new value for paymentMode
     */
    public void setPaymentMode(int aPaymentMode) {
        paymentMode = aPaymentMode;
    }

    /**
     * Access method for amountPaid.
     *
     * @return the current value of amountPaid
     */
    public float getAmountPaid() {
        return amountPaid;
    }

    /**
     * Setter method for amountPaid.
     *
     * @param aAmountPaid the new value for amountPaid
     */
    public void setAmountPaid(float aAmountPaid) {
        amountPaid = aAmountPaid;
    }

}
