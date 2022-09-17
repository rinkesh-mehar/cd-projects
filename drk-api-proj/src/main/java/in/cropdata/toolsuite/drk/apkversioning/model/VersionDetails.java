package in.cropdata.toolsuite.drk.apkversioning.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VersionDetails {
	private String appName;
	private int versionNumber;
	private String versionName;
	private Date releaseDate;
	private String apkUrl;
	private String versionLog;
}
