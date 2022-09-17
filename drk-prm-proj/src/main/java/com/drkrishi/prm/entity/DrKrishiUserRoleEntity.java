package com.drkrishi.prm.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Entity
@Table(name="user_roles")
public class DrKrishiUserRoleEntity implements Serializable {

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
    public DrKrishiUserRoleEntity() {
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
     * Compares the key for this instance with another UserRoles.
     *
     * @param other The object to compare to
     * @return True if other object is instance of class UserRoles and the key objects are equal
     */
    private boolean equalKeys(Object other) {
        if (this==other) {
            return true;
        }
        if (!(other instanceof DrKrishiUserRoleEntity)) {
            return false;
        }
        DrKrishiUserRoleEntity that = (DrKrishiUserRoleEntity) other;
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
        if (!(other instanceof DrKrishiUserRoleEntity)) return false;
        return this.equalKeys(other) && ((DrKrishiUserRoleEntity)other).equalKeys(this);
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

    
    @Override
	public String toString() {
		return "DrKrishiUserRoleEntity [userRolesId=" + userRolesId + ", userId=" + userId + ", roleId=" + roleId + "]";
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
