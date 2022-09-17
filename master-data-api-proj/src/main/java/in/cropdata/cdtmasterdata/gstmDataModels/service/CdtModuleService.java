package in.cropdata.cdtmasterdata.gstmDataModels.service;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.gstmDataModels.dto.CdtModuleDto;
import in.cropdata.cdtmasterdata.gstmDataModels.model.CdtManage;
import in.cropdata.cdtmasterdata.gstmDataModels.model.Model;
import in.cropdata.cdtmasterdata.gstmDataModels.repository.CdtManageRepository;
import in.cropdata.cdtmasterdata.gstmDataModels.repository.ModelRepository;
import in.cropdata.cdtmasterdata.gstmDataModels.vo.CdtManageVo;
import in.cropdata.cdtmasterdata.properties.AppProperties;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.toolsuite.sdk.FileManagerUtil;
import in.cropdata.toolsuite.sdk.dto.DirectoryResponseDTO;
import in.cropdata.toolsuite.sdk.dto.FileUploadResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

/**
 * @author cropdata-ujwal
 * @package in.cropdata.cdtmasterdata.service
 * @date 29/07/20
 * @time 7:43 PM
 * To change this template use File | Settings | File and Code Templates
 */
@Service
public class CdtModuleService
{
    private static final Logger logger = LoggerFactory.getLogger(CdtModuleService.class);
	private static final String SERVER_ERROR_MSG = "Server Error : ";


    @Autowired
    AppProperties appProperties;

    @Autowired
    CdtManageRepository cdtModuleRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    private FileManagerUtil fileManagerUtil;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    /**
     * @return It is return model list
     */
    public Map<String, Object> getModels()
    {
        try
        {
            Map<String, Object> responseMap = new HashMap<>();

            responseMap.put("Models", cdtModuleRepository.listOfModels());

            return responseMap;

        } catch (Exception ex)
        {
            throw ex;
        }
    }

    public List<Model> getAllModel(){


        return modelRepository.findAll();
    }
    /**
     * @return It is return total records
     */
    public List<CdtModuleDto> getListOfManage()
    {

        return cdtModuleRepository.getManageList();
    }
    

	public Page<Model> getModelListByPagenation(Integer page, Integer size, String searchText){
		try {
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return modelRepository.getModelListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			logger.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Data Found For Searched Text -> " + searchText);
		}
	}

    public Page<CdtModuleDto> getPageableList(int page, int size, String searchText){

        try
        {
            searchText = "%" + searchText + "%";
            Pageable sortByIdAsc = PageRequest.of(page,size, Sort.by("ID").descending());
            return cdtModuleRepository.getPageableList(sortByIdAsc, searchText);

        }catch (Exception e){
            throw e;
        }
    }

    public Optional<CdtManage> getRecordById(Integer id)
    {
        return cdtModuleRepository.findById(id);
    }

    public ResponseMessage updateRecord(Integer id, CdtManageVo cdtModuleManageVo) throws IOException
    {
        try
        {
            if (id != 0)
            {
                ObjectMapper objectMapper = new ObjectMapper();
                CdtManage cdtManage = objectMapper.readValue(cdtModuleManageVo.getData(), CdtManage.class);
                Optional<CdtManage> checkRecord = cdtModuleRepository.findById(cdtManage.getId());
                if (checkRecord.isPresent())
                {
                    if (checkRecord.get().getStatus().equals("Failed"))
                    {

                        FileUploadResponseDTO manageFile = this.getManageFile(cdtModuleManageVo.getCsvFile(),"Models", "Models");
                        logger.info("updating records info...");
                        cdtModuleRepository.updateRecord(cdtManage.getId(), manageFile.getPublicUrl(), "Pending", null);
                    } else
                    {
                        return responseMessageUtil.sendResponse(false, "", "ID: " + id + " is Already " + checkRecord.get().getStatus());

                    }
                }
                else {
                    return responseMessageUtil.sendResponse(false,  "", APIConstants.RESPONSE_NO_RECORD_FOUND + " " + id);

                }
            }
            return responseMessageUtil.sendResponse(true, "Manage Record" + APIConstants.RESPONSE_UPDATE_SUCCESS + " " + id, "");
        } catch (Exception e)
        {
            throw e;
        }
    }

    public ResponseMessage updateRecordById(int id)
    {
        try
        {
            if (id != 0)
            {
                Optional<CdtManage> exist = cdtModuleRepository.findById(id);
                if (exist.isPresent())
                {
                    logger.info("deleting records info...");

                    cdtModuleRepository.deleteRecords("Deleted", id);
                }
            }
            return responseMessageUtil.sendResponse(true, "Manage Record" + APIConstants.RESPONSE_DELETE_SUCCESS + " " + id, "");
        } catch (Exception e)
        {
            throw e;
        }
    }

    /**
     * @param cdtModuleManageVo It is hold records
     *                          It is validate record and storing record in database
     * @return message
     * @throws IOException
     */
    public ResponseMessage storeManage(CdtManageVo cdtModuleManageVo) throws IOException
    {

        if (cdtModuleManageVo != null)
        {
            logger.info("manage Data ------> {}", cdtModuleManageVo);

            ObjectMapper objectMapper = new ObjectMapper();
            CdtManage cdtManage = objectMapper.readValue(cdtModuleManageVo.getData(), CdtManage.class);

            FileUploadResponseDTO manageFile = this.getManageFile(cdtModuleManageVo.getCsvFile(),"Models", "Models");

            if (manageFile != null)
            {
                if (manageFile.getPublicUrl() != null)
                {

                    int count = cdtModuleRepository.addModels(cdtManage.getModelId(), manageFile.getPublicUrl());

                }
            } else
            {
                return responseMessageUtil.sendResponse(false, "", "Given File Format Not supported!");
            }
        } else
        {
            return responseMessageUtil.sendResponse(false, "", "Models Manage " + APIConstants.RESPONSE_NO_RECORD_FOUND);
        }
        return responseMessageUtil.sendResponse(true, "Models Manage" + APIConstants.RESPONSE_ADD_SUCCESS, "");
    }

    public ResponseMessage storeModel(CdtManageVo cdtModelVo) throws IOException
    {
        if (cdtModelVo != null){
            logger.info("model Data ------> {}", cdtModelVo);
            ObjectMapper objectMapper = new ObjectMapper();
            Model modelData = objectMapper.readValue(cdtModelVo.getData(), Model.class);
            FileUploadResponseDTO manageFile;

            if (cdtModelVo.getCsvFile() != null){
                manageFile = this.getManageFile(cdtModelVo.getCsvFile(),"templates","Models");
                if (manageFile != null){
                    if (manageFile.getPublicUrl() != null){
                        modelData.setModelTemplates(manageFile.getPublicUrl());
                    }
                } else {
                    return responseMessageUtil.sendResponse(false, "", "Given File Format Not supported!");
                }
            }
            modelRepository.save(modelData);
            return responseMessageUtil.sendResponse(true, "Models Manage" + APIConstants.RESPONSE_ADD_SUCCESS, "");

        } else {
            return responseMessageUtil.sendResponse(false, "", "Models Manage " + APIConstants.RESPONSE_NO_RECORD_FOUND);
        }
    }


    public Optional<Model> getTemplateByModelId(Integer id){
        return modelRepository.findById(id);

    }
    /**
     * @param file It is hold csv file
     * @return It is return file url
     * @throws IOException
     */
    private FileUploadResponseDTO getManageFile(MultipartFile file,String pathKey,String dirPath) throws IOException
    {
        FileUploadResponseDTO fileUploadResponse = null;
        String[] SUPPORTED_EXTN = {".csv", ".xlsx", ".xlx", ".zip"};
        String fileExtension = Objects.requireNonNull(file.getOriginalFilename());
        logger.info(" file :: " + file.getOriginalFilename());
//        String pathKey = "Models";
        if (Arrays.stream(SUPPORTED_EXTN).anyMatch(fileExtension::contains))
        {
            Map<String, Object> pathKeyResponse = fileManagerUtil.isPathKeyExist(pathKey);
            logger.info("pathKey ::  " + pathKeyResponse.get("exist"));
            if (pathKeyResponse.get("exist") != null)
            {
                logger.info("pathKeyResponse ::  " + pathKeyResponse);
                Object exist = pathKeyResponse.get("exist");
                logger.info("result ::  " + exist);

                if (exist.toString().equals("true"))
                {
                    logger.info("inside if condition result is true ::  ");
                    fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false,
                            file.getOriginalFilename(), true, file, null);
                } else
                {
                    String moduleName = "master-data";
//                    String dirPath = pathKey;
                    logger.info("inside else condition result is false ::  ");
                    DirectoryResponseDTO createDirResp = this.fileManagerUtil.createDirectory(moduleName,
                            pathKey, dirPath, null);
                    if (createDirResp != null && createDirResp.isSuccess())
                    {
                        fileUploadResponse = this.fileManagerUtil.uploadFile(pathKey, false,
                                file.getOriginalFilename(), true, file, null);
                        logger.info("Create directory");
                    }
                }
            }
        }
        return fileUploadResponse;
    }
}
