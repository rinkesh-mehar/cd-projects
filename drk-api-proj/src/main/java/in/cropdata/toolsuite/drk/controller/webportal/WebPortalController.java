package in.cropdata.toolsuite.drk.controller.webportal;

import in.cropdata.toolsuite.drk.DrkApplication;
import in.cropdata.toolsuite.drk.constants.ErrorConstants;
import in.cropdata.toolsuite.drk.dao.masterdata.ResourceDao;
import in.cropdata.toolsuite.drk.exceptions.tileassignment.InvalidApiKeyException;
import in.cropdata.toolsuite.drk.model.masterdata.ParameterBandDetails;
import in.cropdata.toolsuite.drk.service.tileassignment.WebPortalService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author RinkeshKM
 * @project DRK
 * @created 16/02/2021 - 3:37 PM
 */

@RequestMapping("/" + DrkApplication.apiVersion + "/portal")
@RestController
public class WebPortalController {

    @Autowired
    WebPortalService webPortalService;

    @Autowired
    private ResourceDao resourceDao;

//    @GetMapping("/get-parameter/{commodityID}")
//    List<Object> getParameterDetails(@PathVariable Integer commodityID) {
//        webPortalService.getParameterDetails(commodityID);
//        return null;
//    }

//    @PostMapping("validate-band")
//    public void validateQuality(@RequestBody List<Map<String, Object>> request) {
//        System.out.println(request);
//    }


    @PostMapping("validate-band")
    public ResponseEntity<?> validateQuality(@RequestBody ParameterBandDetails parameterBandDetails,
                                             @RequestParam(required = false) String apiKey) throws NotFoundException
    {
        if (apiKey == null) {
            throw new InvalidApiKeyException(ErrorConstants.REQUIRED_API_KEY,
                    "Api Key is required to access this Resource");
        }
        if (!resourceDao.isApiKeyExists(apiKey).isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(webPortalService.validateQualityBand(parameterBandDetails));
        } else {
            throw new InvalidApiKeyException(ErrorConstants.INVALID_API_KEY, "Invalid API Key");
        }
    }
}
