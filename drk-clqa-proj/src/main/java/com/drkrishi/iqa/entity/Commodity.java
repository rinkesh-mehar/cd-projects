// Generated with g9.

package com.drkrishi.iqa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="commodity")
public class Commodity  {
  
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "name" )
    private String name;
    
    @Column(name="scientific_name")
    private String scientificName;
    
    @Column(name = "comment")
    private String comment;
    
    @Column(name = "status")
    private int status;

   
    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }

    public String getName() {
        return name;
    }

    public void setName(String aName) {
        name = aName;
    }

    public String getScientificName() {
        return scientificName;
    }

    public void setScientificName(String aScientificName) {
        scientificName = aScientificName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String aComment) {
        comment = aComment;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int aStatus) {
        status = aStatus;
    }
}
