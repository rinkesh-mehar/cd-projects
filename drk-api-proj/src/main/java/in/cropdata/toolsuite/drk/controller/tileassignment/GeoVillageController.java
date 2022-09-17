package in.cropdata.toolsuite.drk.controller.tileassignment;

import java.util.List;

import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.controller.util.ControllerUtil;
import in.cropdata.toolsuite.drk.dto.tileassignment.VillageDTOInf;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.properties.AppProperties;
import in.cropdata.toolsuite.drk.service.tileassignment.GeoVillageService;

@RestController
@RequestMapping("/" + DrkApplication.apiVersion)
public class GeoVillageController {
    Logger logger = LoggerFactory.getLogger(GeoVillageController.class);

    @Autowired
    private GeoVillageService geoVillageService;

	@Autowired
	private ResourceDao resourceDao;

    @Autowired
    AppProperties appProperties;

    @GetMapping("/")
    public void index() {
	final String userIpAddress = ControllerUtil.getCurrentRequest().getRemoteAddr();
	final String userAgent = ControllerUtil.getCurrentRequest().getHeader("user-agent");
	String query = "Accessed index without API Key";
	final String userDisplay = String.format("Query:%s,IP:%s Browser:%s", query, userIpAddress, userAgent);
	logger.error(userDisplay);
	throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY, "Api Key is required to access this Resource");
    }

    /**
     * Get All Villages of Sub-Region
     * 
     * @param subRegionId
     * @return List<VillageDTO>
     */
    @GetMapping("/get-villages-by-sub-region-id/{subRegionId}")
    public List<VillageDTOInf> getAllVillagesBySubRegionId(@RequestParam(required = false) String apiKey, @PathVariable String subRegionId) {
	if (apiKey == null) {
	    throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY, "Api Key is required to access this Resource");
	}

	if (apiKey != null && !resourceDao.isApiKeyExists(apiKey).isEmpty()) {
	    return geoVillageService.getAllGeoVillage(subRegionId);
	} else {
	    throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
	}
    }// getAllVillagesBySubRegionId
}
