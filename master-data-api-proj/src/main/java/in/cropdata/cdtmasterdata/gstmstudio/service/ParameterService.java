package in.cropdata.cdtmasterdata.gstmstudio.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.gstmstudio.model.FilterMaster;
import in.cropdata.cdtmasterdata.gstmstudio.dto.GstmEyeDto;
import in.cropdata.cdtmasterdata.gstmstudio.model.Parameter;
import in.cropdata.cdtmasterdata.gstmstudio.repository.MasterFilterRepository;
import in.cropdata.cdtmasterdata.gstmstudio.repository.ParameterFilterRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author RinkeshKM on 27/07/20
 * @apiNote This API use for GSTM EYE -> Parameter In MasterData UI
 */

@Service
public class ParameterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ParameterService.class);
    private static final String SERVER_ERROR_MSG = "Server Error : ";

    @Autowired
    ResponseMessageUtil responseMessageUtil;

    @Autowired
    ParameterFilterRepository parameterFilterRepository;

    @Autowired
    MasterFilterRepository masterFilterRepository;

    public List<GstmEyeDto> getAllParametersList() {
        return masterFilterRepository.getParametersList();
    }

    /**
     * This method used to get all parameter list in the form of pagination
     *
     * @param searchText
     * @param page
     * @param size
     * @return <code>List</code> the response in list of
     * <code>{@link GstmEyeDto}</code>
     * @throws DoesNotExistException
     */
    public Page<GstmEyeDto> getAllParametersListPaginated(Integer page, Integer size, String searchText) {
        try {
            LOGGER.info("getting all Parameter info - paginated...");
            searchText = "%" + searchText + "%";

            Pageable sortedByIdAsc = PageRequest.of(page, size, Sort.by("id").ascending());

            return masterFilterRepository.getAllParametersListPaginated(sortedByIdAsc, searchText);


        } catch (Exception ex) {
            LOGGER.error(SERVER_ERROR_MSG, ex);
            throw new DoesNotExistException("No Parameter Data Found For Searched Text -> " + searchText);
        }
    }

    public GstmEyeDto getParametersByParameterID(final int parameterId) {
        return masterFilterRepository.getParameterByParameterId(parameterId);
    }

    public List<GstmEyeDto> getAllPlatform() {
        return masterFilterRepository.getAllPlatform();
    }

    public List<GstmEyeDto> getDataTypeByPlatformId(final int platformId) {
        return masterFilterRepository.getDataTypeByPlatformId(platformId);
    }

    public ResponseMessage updateParameter(Integer parameterId, FilterMaster parameterDetails) {
        try {

            Optional<FilterMaster> dbParameterDetail = masterFilterRepository.findById(parameterId);

            if (dbParameterDetail.isPresent()) {

                LOGGER.info("updating parameter info...");
                if (parameterDetails.getStatus().equals("")) {
                    parameterDetails.setStatus("Inactive");
                }

                parameterDetails.setId(parameterId);

                Integer paramId = masterFilterRepository.getParamIdByMasterId(parameterId);

                parameterFilterRepository.updateParameter(parameterDetails.getParameter(), paramId, parameterDetails.getStatus());

                parameterDetails.setParameter(paramId.toString());

                masterFilterRepository.save(parameterDetails);


                return responseMessageUtil.sendResponse(true, "Parameter"
                        + APIConstants.RESPONSE_ADD_SUCCESS + " with parameter Id "+ parameterId, "");

            } else {
                return responseMessageUtil.sendResponse(false,
                        "Parameter Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + parameterId, "");
            }

        } catch (
                Exception ex) {
            return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
        }
    }

    public ResponseMessage deleteParameter(Integer parameterId) {
        try {

            Optional<FilterMaster> dbParameterDetail = masterFilterRepository.findById(parameterId);

            if (dbParameterDetail.isPresent()) {

                LOGGER.info("deleting parameter info...");

                String paramId = dbParameterDetail.get().getParameter();


                masterFilterRepository.changeStatusToDeleteById(parameterId);
                parameterFilterRepository.changeStatusToDeleteById(Integer.parseInt(paramId));

                return responseMessageUtil.sendResponse(true, "Parameter"
                        + APIConstants.RESPONSE_DELETE_SUCCESS + parameterId, "");

            } else {
                return responseMessageUtil.sendResponse(false,
                        "Parameter Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + parameterId, "");
            }

        } catch (
                Exception ex) {
            return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
        }
    }

    public List<GstmEyeDto> getCategoryByDataType(final int dataTypeId) {
        return masterFilterRepository.getCategoryByDataType(dataTypeId);
    }

    public List<GstmEyeDto> getSubcategoryByCategory(final int categoryId) {
        return masterFilterRepository.getSubcategoryByCategory(categoryId);
    }

    public ResponseMessage storeParameterDetail(Parameter parameterDetail) {
        LOGGER.info("saving parameter detail...");

        Parameter parameterDetails = parameterFilterRepository.save(parameterDetail);

        if (masterFilterRepository.saveParameterInMaster(parameterDetails.getPlatform(), parameterDetails.getDataType(),
                parameterDetails.getCategory(), parameterDetails.getSubCategory(), parameterDetails.getID()) == 1)
        {
            return responseMessageUtil.sendResponse(true, "Parameter" + APIConstants.RESPONSE_ADD_SUCCESS, "");
        } else {
            return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG);

        }
    }
}
