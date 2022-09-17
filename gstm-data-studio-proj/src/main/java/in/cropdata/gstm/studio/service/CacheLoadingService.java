package in.cropdata.gstm.studio.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.gstm.studio.constants.ErrorConstants;
import in.cropdata.gstm.studio.dao.CacheLoadingRepository;
import in.cropdata.gstm.studio.exceptions.DbException;

/**
 * Service to load data in cache from DB.
 * 
 * @since 1.0
 * @author PranaySK
 */

@Service
public class CacheLoadingService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CacheLoadingService.class);

	@Autowired
	private CacheLoadingRepository loadingRepository;

	/**
	 * This method is used to get map and analytics data for the given parameters.
	 * 
	 * @return void
	 * 
	 * @throws DbException
	 * 
	 * @since 1.0
	 * @author PranaySK
	 */
	public void loadGstmData() {
		try {
			loadingRepository.loadGstmData();
			LOGGER.info("Gstm data loaded in cache!");

		} catch (Exception ex) {
			throw new DbException(ErrorConstants.DATA_FETCH_ERROR,
					"Error while loading data in cache -> " + ex.getMessage());
		}
	}

}
