/**
 * 
 */
package in.cropdata.toolsuite.drk.service.ndvi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dao.ndvi.CaseNdviDao;
import in.cropdata.toolsuite.drk.dto.ndvi.CaseNdvi;

/**
 * @author admin-pc
 *
 */
@Service
public class CaseNdviService {

	@Autowired
	CaseNdviDao caseNdviDao;

	public List<CaseNdvi> getBI(CaseNdvi caseNdvi) {

		

		return caseNdviDao.getBI(caseNdvi);
	}

	public List<CaseNdvi> getndviList(CaseNdvi caseNdvi) {
		return this.caseNdviDao.getndviList(caseNdvi);
	}
}
