package in.cropdata.cdtmasterdata.gstmDataModels.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model
 * @date 29/07/20
 * @time 7:27 PM
 * To change this template use File | Settings | File and Code Templates
 */

@Data
@Entity
@Table(name = "raw_data_files", schema = "gstm_data_models")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class CdtManage
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "ModelID")
    private Integer modelId;

    @Column(name = "FileUrl")
    private String fileUrl;

    @Column(name = "ErrMsg")
    private String errorMsg;

    @Column(name = "Status")
    private String status;

    @Transient
    private Date createdAt;

    @Transient
    private Date updatedAt;

}
