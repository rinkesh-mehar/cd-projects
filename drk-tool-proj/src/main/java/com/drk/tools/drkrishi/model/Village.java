package com.drk.tools.drkrishi.model;

import lombok.Data;

import javax.persistence.*;

/**
 * @author RinkeshKM
 * @Date 09/09/20
 */

@Data
@Entity
@Table(name = "village", schema = "drkrishi_ts")
public class Village {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "panchayat_id")
    private Integer panchayatId;

    @Column(name = "code")
    private Integer code;

    @Column(name = "region_id")
    private Integer regionId;

    @Column(name = "subregion_id")
    private Integer subregionId;

    @Column(name = "name")
    private String name;

    @Column(name = "pincode")
    private Integer pincode;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "comment")
    private String comment;

    @Column(name = "status")
    private Integer status;

}
