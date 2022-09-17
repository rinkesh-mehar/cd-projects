package in.cropdata.toolsuite.drk.apkversioning.model;

import lombok.Data;

@Data
public class ApkVersionControl {
	private boolean status;
	private String message;
	private VersionDetails versionDetails;
}
