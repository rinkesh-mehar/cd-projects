package com.drk.tools.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VersionDetails {
	private String AppName;
	private int VersionNumber;
	private String VersionName;
	private Date ReleaseDate;
	private String ApkUrl;
	private String VersionLog;
}
