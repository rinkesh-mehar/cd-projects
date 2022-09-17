package com.drk.tools.model;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@Component
@JsonIgnoreProperties(ignoreUnknown = true)
public class MetaData {

	private long syncTime;
	private String moduleName;
	private String pathKey;
	private String dirPath;
	private String fileType;
	private String fileName;
	private String fileExtension;
	private String fileOrigionalName;

}