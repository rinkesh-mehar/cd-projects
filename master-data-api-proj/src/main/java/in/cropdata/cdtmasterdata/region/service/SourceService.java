package in.cropdata.cdtmasterdata.region.service;

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.region.model.SourceModel;
import in.cropdata.cdtmasterdata.region.vo.SourceVO;
import in.cropdata.cdtmasterdata.repository.SourceRepository;
import in.cropdata.cdtmasterdata.util.CryptoUtils;
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
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.region.service
 * @date 18/01/21
 * @time 1:32 PM
 */
@Service
public class SourceService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(SourceService.class);

    @Autowired
    private SourceRepository sourceRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    @Autowired
    private CryptoUtils cryptoUtils;

    public Page<SourceVO> getSourceList(int page, int size, String searchText)
    {

        searchText = "%" + searchText + "%";

        Pageable sortByIdDesc = PageRequest.of(page, size, Sort.by("ID").ascending());

        return sourceRepository.getSourceList(sortByIdDesc, searchText);
    }

    public ResponseMessage storeSource(SourceModel sourceModel)
    {

        try
        {
            LOGGER.info("check user details --------> {}", sourceModel.toString());
            if (sourceModel != null)
            {
                sourceModel.setApiKey(this.cryptoUtils.randomString(32));
                sourceRepository.save(sourceModel);
                LOGGER.info("successfully save source details is -------> {}", true);
                return responseMessageUtil.sendResponse(true, APIConstants.RESPONSE_ADD_SUCCESS, "");
            } else
            {
                LOGGER.info("successfully save source details is -------> {}", false);
                return responseMessageUtil.sendResponse(false, "", "Please check Data");
            }
        } catch (Exception ex)
        {
            throw ex;
        }
    }

    public Optional<SourceModel> getSourceById(Integer id)  {
        try
        {
            LOGGER.info("check source id -------> {}", id);
            if (id !=null){
                return sourceRepository.findById(id);
            }
        } catch (Exception ex) {
            throw ex;
        }
        return Optional.empty();
    }

    public ResponseMessage updateSourceDetails(Integer id, SourceModel sourceModel){
        try
        {
            Optional<SourceModel> getSourceById = sourceRepository.findById(id);

            if (getSourceById.isPresent()){
                sourceModel.setId(getSourceById.get().getId());
                sourceModel.setApiKey(getSourceById.get().getApiKey());

                sourceRepository.save(sourceModel);
                return this.responseMessageUtil.sendResponse(true, APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");
            }
            return this.responseMessageUtil.sendResponse(false, "", APIConstants.RESPONSE_UPDATE_ERROR + id);
        }catch (Exception ex){
            throw ex;
        }
    }

    public List<SourceModel> getSourceList(){
        return sourceRepository.findAll(Sort.by("name"));
    }
}
