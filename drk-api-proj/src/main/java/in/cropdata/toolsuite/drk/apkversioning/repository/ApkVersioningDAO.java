package in.cropdata.toolsuite.drk.apkversioning.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import in.cropdata.toolsuite.drk.apkversioning.model.ApkVersionControl;
import in.cropdata.toolsuite.drk.apkversioning.model.VersionDetails;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.exceptions.DbException;

@Component
public class ApkVersioningDAO {

	private static final Logger log = LoggerFactory.getLogger(ApkVersioningDAO.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ApkVersionControl getVersion(String appKey) {
		String apkVersionQuery = "select AppName,VersionNumber,VersionName,ReleaseDate,ApkUrl,VersionLog FROM cdt_master_data.app_version_control where AppKey='"
				+ appKey + "'";
		log.debug(apkVersionQuery);
		ApkVersionControl apkVersion = null;
		try {
			VersionDetails versionDetails = jdbcTemplate.queryForObject(apkVersionQuery,
					(rs, rowNum) -> new VersionDetails(rs.getString("AppName"), rs.getInt("VersionNumber"),
							rs.getString("VersionName"), rs.getDate("ReleaseDate"), rs.getString("ApkUrl"),
							rs.getString("VersionLog")));
			log.debug("Version Details {}", versionDetails);
			apkVersion = new ApkVersionControl();
			apkVersion.setStatus(true);
			apkVersion.setMessage("Version Details");
			apkVersion.setVersionDetails(versionDetails);
		} catch (Exception ex) {
			log.error("Error in getVersion -> {}", ex.getMessage());
			throw new DbException(ErrorConstants.NO_RECORD_FOUND, "Data Not Available!");
		}
		return apkVersion;
	}
}
