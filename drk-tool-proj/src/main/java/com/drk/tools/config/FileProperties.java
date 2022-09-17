package com.drk.tools.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author Rinkesh Mehar - Cropdata
 * 29-DEC-2020
 */
@Data
@Component
public class FileProperties {

//    @Value("${extract-all-path}")
    private String zipExtract = "D:\\Toolsuite\\Flipbook_Temp\\extract-all";

}
