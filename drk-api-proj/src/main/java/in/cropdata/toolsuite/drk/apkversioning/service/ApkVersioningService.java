package in.cropdata.toolsuite.drk.apkversioning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.apkversioning.model.ApkVersionControl;
import in.cropdata.toolsuite.drk.apkversioning.repository.ApkVersioningDAO;

@Service
public class ApkVersioningService {

	@Autowired
	ApkVersioningDAO versioningDAO;

	public ApkVersionControl versionCheck(String apiKey) {
		return versioningDAO.getVersion(apiKey);
	}
}
