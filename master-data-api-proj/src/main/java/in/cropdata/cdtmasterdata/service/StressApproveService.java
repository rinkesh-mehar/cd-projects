package in.cropdata.cdtmasterdata.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.drkrishi.model.TaskStressDetails;
import in.cropdata.cdtmasterdata.drkrishi.repository.TaskStressDetailsRespository;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.StressApproveDto;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.model.TaskStressModel;
import in.cropdata.cdtmasterdata.repository.StressApproveRepository;
import in.cropdata.cdtmasterdata.util.AclHistoryUtil;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;


@Service
public class StressApproveService {

    private static final Logger LOGGER = LoggerFactory.getLogger(StressApproveService.class);
    private static final String SERVER_ERROR_MSG = "Server Error : ";

    @Autowired
    private StressApproveRepository stressApproveRepository;
    
    
    @Autowired
    private TaskStressDetailsRespository taskStressDetailsRepository;

    @Autowired
    private AclHistoryUtil approvalUtil;


    public Page<StressApproveDto> getStressForApprovalByPagination(Integer page, Integer size, String searchText) {
        try {
            searchText = "%" + searchText + "%";
            Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("CreatedAt").descending());
            return stressApproveRepository.getValidateStressListByPagenation(sortedByIdDesc, searchText);
        } catch (Exception ex) {
            LOGGER.error(SERVER_ERROR_MSG, ex);
            throw new DoesNotExistException("No Data For Approval Of Stress Is Found For Searched Text -> " + searchText);
        }
    }

    public StressApproveDto getDetailsByTaskStressDetailID(String taskStressDetailID) {
        return stressApproveRepository.getDetailsByTaskStressDetailID(taskStressDetailID);
    }

    public StressApproveDto getStressDetailsById(String id, Integer stressId, Integer symptomId) {
        return stressApproveRepository.getStressDetailsById(id,stressId,symptomId);
    }

    public List<StressApproveDto> getSymptomBySpec(Integer commodityID, Integer varietyID, Integer phenophaseID, Integer districtID) {
        return stressApproveRepository.getSymptomBySpec(commodityID, varietyID, phenophaseID, districtID);
    }

    public StressApproveDto getSymptomDetailsBySymptom(Integer symptomID) {
        return stressApproveRepository.getSymptomDetailsBySymptom(symptomID);
    }
    
	public ResponseMessage approveSymptomsDetails(TaskStressModel data) {

		ResponseMessage response = new ResponseMessage();
		Optional<TaskStressDetails> taskStressDetails = taskStressDetailsRepository.findById(data.getId());
		try {
			if (taskStressDetails.isPresent()) {
				TaskStressDetails details = taskStressDetails.get();
				details.setSymptomID(data.getSymptomID());
                details.setIsVerified(data.getApprovalStatus().equals("true") ? "Approved" : "Not Found");
				taskStressDetailsRepository.save(details);
				response.setSuccess(true);
				response.setMessage(data.getApprovalStatus().equals("true") ? "Stress details approved successfully!!"
                        : "Your Entry Is Updated As Not Found");

			} else {
				response.setSuccess(false);
				response.setMessage("Stress details not found !!");
			}
		} catch (Exception e) {
			response.setSuccess(false);
			response.setMessage("Some error occured !!");
			e.printStackTrace();
		}

		return response;
	}
  
}
