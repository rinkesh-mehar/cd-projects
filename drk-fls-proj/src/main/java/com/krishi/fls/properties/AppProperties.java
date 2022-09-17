package com.krishi.fls.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Ujwal-cropdata
 * @Date 09/07/21
 */
@Data
@Component
public class AppProperties {

    @Value("${user.data.path}")
    private String userDirPath;

    @Value("${extract-all-path}")
    private String zipExtract;
}
