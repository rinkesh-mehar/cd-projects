package in.cropdata.cdtmasterdata.gstmstudio.controller;

import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.gstmstudio.model.FilterMaster;
import in.cropdata.cdtmasterdata.gstmstudio.dto.GstmEyeDto;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.gstmstudio.model.Parameter;
import in.cropdata.cdtmasterdata.gstmstudio.service.ParameterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author RinkeshKM
 * @Date 27/07/20
 */

@RestController
@RequestMapping("gstm-eye")
public class ParameterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterController.class);

    @Autowired
    ParameterService parameterService;

    @GetMapping("/parameter/list")
    public List<GstmEyeDto> getAllParametersList() {
        return parameterService.getAllParametersList();
    }

    @GetMapping("/parameter/Paginated/list")
    public Page<GstmEyeDto> getAllParametersListPaginated(@RequestParam("page") Integer page,
                                                          @RequestParam("size") Integer size,
                                                          @RequestParam(required = false, defaultValue = "") String searchText) {

        return parameterService.getAllParametersListPaginated(page, size, searchText);
    }

    @GetMapping("/parameter")
    public GstmEyeDto getParametersByParameterID(@RequestParam int parameterId) {
        return parameterService.getParametersByParameterID(parameterId);
    }

    @GetMapping("/platform")
    public List<GstmEyeDto> getAllPlatform() {
        return parameterService.getAllPlatform();
    }

    @GetMapping("/dataType")
    public List<GstmEyeDto> getDataTypeByPlatformId(@RequestParam("platformId") int platformId) {
        return parameterService.getDataTypeByPlatformId(platformId);
    }

    @PutMapping("/parameter/{id}")
    public ResponseEntity<ResponseMessage> updateParameter(@PathVariable(name = "id") Integer parameterId,
                                                           @RequestBody FilterMaster parameterDetail)
    {
        LOGGER.info("Updating Parameter detail for ID -> {}", parameterId);

        if (parameterDetail == null) {
            throw new InvalidDataException("Parameter data can not be null!");
        }

        return new ResponseEntity<>(parameterService.updateParameter(parameterId, parameterDetail), HttpStatus.OK);
    }

    @PutMapping("/parameter/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteParameter(@PathVariable(name = "id") Integer parameterId)
    {
        LOGGER.info("Updating Parameter detail for ID -> {}", parameterId);

        return new ResponseEntity<>(parameterService.deleteParameter(parameterId), HttpStatus.OK);
    }

    @GetMapping("/category")
    public List<GstmEyeDto> getCategoryByDataType(@RequestParam("dataTypeId") int dataTypeId) {
        return parameterService.getCategoryByDataType(dataTypeId);
    }

    @GetMapping("/subcategory")
    public List<GstmEyeDto> getSubcategoryByCategory(@RequestParam("categoryId") int categoryId) {
        return parameterService.getSubcategoryByCategory(categoryId);
    }

    @PostMapping("/parameter")
    public ResponseEntity<ResponseMessage> adParameterDetails(@RequestBody Parameter parameterDetails) {

        if (parameterDetails == null) {
            throw new InvalidDataException("Parameter data can not be null!");
        }

        LOGGER.info("Adding Parameter Details...");
        return new ResponseEntity<>(parameterService.storeParameterDetail(parameterDetails), HttpStatus.CREATED);
    }

}
