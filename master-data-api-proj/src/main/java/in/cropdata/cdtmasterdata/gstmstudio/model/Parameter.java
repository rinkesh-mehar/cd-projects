package in.cropdata.cdtmasterdata.gstmstudio.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Used for fetching DropDown data from DB to show filters in view page.
 *
 * @author RinkeshKM
 * @since 1.0
 */

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "filter_parameters", schema = "gstm_studio")
public class Parameter implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer ID;

    @Transient
    private Integer platform;

    @Transient
    private Integer dataType;

    @Transient
    private Integer category;

    @Transient
    private Integer subCategory;

    @Column(name = "Name")
    private String parameter;

    @Column(name = "ColumnName")
    private String columnName;

    @Column(name = "IsSeasonDependent")
    private String isSeasonDependent;

    @Column(name = "AggregationMethod")
    private String aggregationMethod;

    @Column(name = "OrderingMethod")
    private String orderingMethod;

    @Column(name = "Template")
    private String template;
}


