package com.krishi.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="regional_commodity")
public class RegionalCommodity {
    @Id
    private Integer id;
    @Column(name = "state_id")
    private Integer stateId;
    @Column(name = "region_id")
    private Integer regionId;
    @Column(name = "commodity_id")
    private Integer commodityId;


}
