package in.cropdata.aefc.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.aefc.entity
 * @date 23/12/21
 * @time 3:42 PM
 */
@Table
@Entity(name = "email_template")
@Data
public class EmailTemplate
{

    @Id
    @Column (name = "id")
    private Integer id;

    @Column (name = "name")
    private String name;

    @Column (name = "subject")
    private String subject;

    @Column (name = "body")
    private String body;

    @Column (name = "is_active")
    private Integer isActive;
}
