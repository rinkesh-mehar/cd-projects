package com.drkrishi.usermanagement.entity;




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
@Table(name = "user_roles")
public class UserRoles implements Serializable {

    /** Primary key. */
    protected static final String PK = "userRolesId";

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id", unique=true, nullable=false, precision=10)
    private int userRolesId;
    @Column(name="user_id", precision=10)
    private int userId;
    @Column(name="role_id", precision=10)
    private int roleId;
    

    /** Default constructor. */
    public UserRoles() {
        super();
    }

    /**
     * Access method for userRolesId.
     *
     * @return the current value of userRolesId
     */
    public int getUserRolesId() {
        return userRolesId;
    }

    /**
     * Setter method for userRolesId.
     *
     * @param aUserRolesId the new value for userRolesId
     */
    public void setUserRolesId(int aUserRolesId) {
        userRolesId = aUserRolesId;
    }

    /**
     * Access method for userId.
     *
     * @return the current value of userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Setter method for userId.
     *
     * @param aUserId the new value for userId
     */
    public void setUserId(int aUserId) {
        userId = aUserId;
    }

    /**
     * Access method for roleId.
     *
     * @return the current value of roleId
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Setter method for roleId.
     *
     * @param aRoleId the new value for roleId
     */
    public void setRoleId(int aRoleId) {
        roleId = aRoleId;
    }

    /**
     * Access method for stateId.
     *
     * @return the current value of stateId
     */
   

    /**
     * Setter method for stateId.
     *
     * @param aStateId the new value for stateId
     */
   

    /**
     * Access method for roleName.
     *
     * @return the current value of roleName
     */
    

    /**
     * Setter method for roleName.
     *
     * @param aRoleName the new value for roleName
     */
   

    /**
     * Compares the key for this instance with another UserRoles.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class UserRoles and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof UserRoles)) {
            return false;
        }
        UserRoles that = (UserRoles) other;
        if (this.getUserRolesId() != that.getUserRolesId()) {
            return false;
        }
        return true;
    }

    /**
     * Compares this instance with another UserRoles.
     *
     * @param other The object to compare to
     * @return True if the objects are the same
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof UserRoles)) return false;
        return this.equalKeys(other) && ((UserRoles)other).equalKeys(this);
    }

    /**
     * Returns a hash code for this instance.
     *
     * @return Hash code
     */
    @Override
    public int hashCode() {
        int i;
        int result = 17;
        i = getUserRolesId();
        result = 37*result + i;
        return result;
    }

    /**
     * Returns a debug-friendly String representation of this instance.
     *
     * @return String representation of this instance
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("[UserRoles |");
        sb.append(" userRolesId=").append(getUserRolesId());
        sb.append("]");
        return sb.toString();
    }

    public UserRoles(int userRolesId, int userId, int roleId, int stateId, String roleName) {
		
		this.userRolesId = userRolesId;
		this.userId = userId;
		this.roleId = roleId;
	}

	/**
     * Return all elements of the primary key.
     *
     * @return Map of key names to values
     */
    public Map<String, Object> getPrimaryKey() {
        Map<String, Object> ret = new LinkedHashMap<String, Object>(6);
        ret.put("userRolesId", Integer.valueOf(getUserRolesId()));
        return ret;
    }

}