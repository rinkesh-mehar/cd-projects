package in.cropdata.farmers.app.masters.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "agri_control_measures")
public class ControlMeasures {
    @Id
   private Integer id;

    @Column(name = "Name")
   private String name;
}
