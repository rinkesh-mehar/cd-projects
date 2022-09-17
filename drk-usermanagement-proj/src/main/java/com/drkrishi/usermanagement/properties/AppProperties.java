package com.drkrishi.usermanagement.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Cropdata-Ujwal
 * @Date 08/07/21
 */
@Data
@Component
public class AppProperties {

    @Value("${azure.master.blob.url}")
    private String blobUrl;

    @Value("${azure.media.url}")
    private String plantPartAndSymptomsUrl;
    @Value("${azure.mmpk.url}")
    private String mmpkBlobUrl;

    @Value("${fls.url}")
    private String flsUrl;

    @Value("${prs.url}")
    private String prsUrl;
}
