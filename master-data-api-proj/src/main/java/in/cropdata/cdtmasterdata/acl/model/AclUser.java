package in.cropdata.cdtmasterdata.acl.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity(name = "acl_users")
public class AclUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "RoleID")
    private int roleId;

    @Column(name = "Name")
    private String name;

    @Column(name = "Email")
    @NotNull(message = "Email cannot be Empty")
    private String email;

    @Column(name = "Password")
//    @JsonIgnore
    private String password;

    @Transient
    private String role;

    @Column(name = "CreatedAt")
    @JsonIgnore
    private Date createdAt;

    @Column(name = "UpdatedAt")
    @JsonIgnore
    private Date updatedAt;

    @Column(name = "Status")
//    @JsonIgnore
    private String status;
    
    @Transient
    private String token;

}
