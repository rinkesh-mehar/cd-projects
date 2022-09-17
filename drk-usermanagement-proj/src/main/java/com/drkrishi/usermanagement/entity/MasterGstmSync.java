package com.drkrishi.usermanagement.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "gstm_sync")
@Data
public class MasterGstmSync {

    @Id
    @Column(name="id", unique=true, nullable=false, precision=10)
    private Integer id;

    @Column(name = "schema_name")
    private String schemaName;

    @Column(name = "last_sync")
    private String lastSync;

    @Column(name = "screen_id")
    private String screenId;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "label_name")
    private String labelName;

    @Column(name = "url")
    private String url;

    @Column(name = "field_mapping")
    private String fieldMapping;

    @Column(name = "zipping_level")
    private String zippingLevel;

    @Column(name = "sync_frequency")
    private Integer syncFrequency;

    @Column(name = "select_query")
    private String selectQuery;

    @Column(name = "insert_query")
    private String insertQuery;
    @Column(name = "download_in_android")
    private String downloadInAndroid;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "last_sync_time")
    private Date lastSyncTime;

    @Column(name = "status")
    private String status;

    @Column(name = "conversion_function")
    private String conversionFunction;

    @Column(name = "sync_from_ts")
    private String syncFromTs;

}
