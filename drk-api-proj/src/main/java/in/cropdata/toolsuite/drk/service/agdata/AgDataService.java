package in.cropdata.toolsuite.drk.service.agdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.toolsuite.drk.dao.agdata.AgDataDao;

@Service
public class AgDataService {

	@Autowired
	private AgDataDao agDataDao;

	public String getData(long unixTimestamp) {

		if (unixTimestamp > 0) {

			return agDataDao.getData(unixTimestamp);
		} else {

			return agDataDao.getData();
		}
	}
}// AgDataService
