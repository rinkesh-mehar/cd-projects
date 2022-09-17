package com.drk.tools.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Rinkesh Mehar
 * @date 29/12/2020
 */
@Data
public class ZipFileDTO {
    private MultipartFile zipFile;
}
