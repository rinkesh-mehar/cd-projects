package in.cropdata.cdtmasterdata.gstmDataModels.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.gstmDataModels.model
 * @date 05/09/20
 * @time 12:50 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Data
@Entity
@Table(name = "raw_model", schema = "gstm_data_models")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Model
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ModelName")
    private String modelName;

    @Column(name = "ModelDescription")
    private String modelDescription;

    @Column(name = "ModelTemplates")
    private String modelTemplates;

    /*@Column(name = "Status")*/
    @Transient
    private String status;

    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;
}
