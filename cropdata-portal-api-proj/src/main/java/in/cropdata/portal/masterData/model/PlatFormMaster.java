package in.cropdata.portal.masterData.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.model
 * @date 24/08/20
 * @time 6:42 PM
 * To change this template use File | Settings | File and Code Templates
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "general_platform_master")
@Data
public class PlatFormMaster
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int platformId;

    @Column(name = "Name")
    private String name;

    @Transient
    private Date updatedAt;

    @Transient
    private Date createdAt;

    @Transient
    private String status;
}
