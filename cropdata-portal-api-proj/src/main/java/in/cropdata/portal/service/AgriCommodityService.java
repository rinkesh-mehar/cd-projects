package in.cropdata.portal.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.portal.exceptions.DoesNotExistException;
import in.cropdata.portal.repository.AgriCommodityRepository;
import in.cropdata.portal.vo.AgriCommodityVo;

@Service
public class AgriCommodityService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AgriCommodityService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	private AgriCommodityRepository agriCommodityRepository;


	public List<AgriCommodityVo> getActiveCommodityList() {
		try {
			LOGGER.info("getting active commodity list info...");

			return agriCommodityRepository.getActiveCommodityList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Active Commodity Data Found!");
		}
	}

	public List<AgriCommodityVo> getCommodityList() {
		try {
			LOGGER.info("getting active commodity list info...");

			return agriCommodityRepository.getCommodityList();

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Active Commodity Data Found!");
		}
	}
}
