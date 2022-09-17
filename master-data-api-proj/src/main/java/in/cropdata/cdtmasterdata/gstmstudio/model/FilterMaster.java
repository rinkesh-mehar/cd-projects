package in.cropdata.cdtmasterdata.gstmstudio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author RinkeshKM
 * @Date 28/07/20
 */

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "filter_master", schema = "gstm_studio")
public class FilterMaster implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer Id;

    @Column(name = "PlatformID")
    private Integer platform;

    @Column(name = "DataTypeID")
    private Integer dataType;

    @Column(name = "CategoryID")
    private Integer category;

    @Column(name = "SubCategoryID")
    private Integer subCategory;

    @Column(name = "ParameterID")
    private String parameter;

    @Column(name = "Status")
    private String status;
}
