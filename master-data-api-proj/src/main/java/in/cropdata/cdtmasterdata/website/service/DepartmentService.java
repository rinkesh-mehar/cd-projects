package in.cropdata.cdtmasterdata.website.service;

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

import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.AlreadyExistException;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.exceptions.InvalidDataException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;
import in.cropdata.cdtmasterdata.website.dto.DepartmentDto;
import in.cropdata.cdtmasterdata.website.model.Department;
import in.cropdata.cdtmasterdata.website.repository.DepartmentRepository;

@Service
public class DepartmentService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DepartmentService.class);

	@Autowired
	private DepartmentRepository departmentRepository;

	private static final String SERVER_ERROR_MSG = "Server Error : ";
	
	@Autowired
	ResponseMessageUtil responseMessageUtil;

	public List<DepartmentDto> getDepartmentList() {
		try {
			LOGGER.info("getting all department info...");
			return departmentRepository.getDepartmentList();
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Department Data Found!");

		}

	}
	
	public Page<DepartmentDto> getDepartmentListByPagenation(Integer page, Integer size, String searchText){
		try {
			LOGGER.info("getting all departments from service....");
			searchText = "%" + searchText + "%";
			Pageable sortedByIdDesc = PageRequest.of(page, size, Sort.by("id").descending());
			return departmentRepository.getDepartmentListByPagenation(sortedByIdDesc, searchText);
		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("No Department Data Found For Searched Text -> " + searchText);
		}
	}
	
	public ResponseMessage addDepartment(Department department) {
		try {
			LOGGER.info("Add Department From Service...");
//			department.setStatus(APIConstants.STATUS_INACTIVE);
			
			LOGGER.info("Length of Department : " + department.getName().length());
			
			if(department.getName().length() > 100) {
				throw new InvalidDataException("Department Length Should Not Be Greater Than 100");
			}
			
			DepartmentDto alreadyExistDepartment = departmentRepository.findAlreadyExistDepartmentForAddMode(department.getName());
			LOGGER.info("alreadyExistDepartment " + alreadyExistDepartment);
			if(alreadyExistDepartment == null) {
				LOGGER.info("Department already not exist...{} ");
			departmentRepository.save(department);
		}else {
			LOGGER.info("Department already exist...{} ");
			throw new AlreadyExistException("'" + department.getName() + "' Department Is Already Exist");
		}
			return responseMessageUtil.sendResponse(true, "Department" + APIConstants.RESPONSE_ADD_SUCCESS, "");
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error: " + e.getMessage());
		}
	}
	
	public Department getDepartmentById(Integer id) {

		try {

			LOGGER.info("getting department by Id...");
			return departmentRepository.findById(id).orElse(null);

		} catch (Exception ex) {
			LOGGER.error(SERVER_ERROR_MSG, ex);
			throw new DoesNotExistException("Department Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
		}

	}

	public ResponseMessage updateDepartment(Integer id, Department department) {
		try {
			
			LOGGER.info("Length of Department : " + department.getName().length());
			
			if(department.getName().length() > 100) {
				throw new InvalidDataException("Department Length Should Not Be Greater Than 100");
			}
			
			Optional<Department> departmentDetail = departmentRepository.findById(id);

			if (departmentDetail.isPresent()) {
				
				DepartmentDto alreadyExistDepartment = departmentRepository.findAlreadyExistDepartmentForEditMode(id,department.getName());
				LOGGER.info("alreadyExistDepartment " + alreadyExistDepartment);
				if(alreadyExistDepartment == null) {
					LOGGER.info("Department already not exist...{} ");
					LOGGER.info("\"Updating Department From Service for ID -> {}\", id");
					department.setId(id);
					departmentRepository.save(department);
			}else {
				LOGGER.info("Department already exist...{} ");
				throw new AlreadyExistException("'" + department.getName() + "' Department Is Already Exist");
			}
				
				return responseMessageUtil.sendResponse(true, "Department" + APIConstants.RESPONSE_UPDATE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false,
						"Department Does" + APIConstants.RESPONSE_DOES_NOT_EXIST + id, "");
			}
		}catch (InvalidDataException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		} catch (AlreadyExistException e) {
			return responseMessageUtil.sendResponse(false, "",e.getMessage());
		}catch (Exception ex) {
			return responseMessageUtil.sendResponse(false, "", SERVER_ERROR_MSG + ex.getMessage());
		}
	}
	
	public ResponseMessage deactiveDepartment(int id) {
		try {
			Optional<Department> optionalDepartment = departmentRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				departmentRepository.deactiveDepartment(id);

				return responseMessageUtil.sendResponse(true, "Department" + APIConstants.RESPONSE_DEACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Department" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public ResponseMessage activeDepartment(int id) {
		try {
			Optional<Department> optionalDepartment = departmentRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				departmentRepository.activeDepartment(id);

				return responseMessageUtil.sendResponse(true, "Department" + APIConstants.RESPONSE_ACTIVE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Department" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}
	
	public Integer opportunityCountByDepartment(int departmentId) {
		
		return departmentRepository.opportunityCountByDepartment(departmentId);

	}
	
	public ResponseMessage deleteDepartment(int id) {
		try {
			Optional<Department> optionalDepartment = departmentRepository.findById(id);
			if (optionalDepartment.isPresent()) {

				departmentRepository.deleteDepartment(id);

				return responseMessageUtil.sendResponse(true, "Department" + APIConstants.RESPONSE_DELETE_SUCCESS + id,
						"");
			} else {
				return responseMessageUtil.sendResponse(false, "",
						"Department" + APIConstants.RESPONSE_NO_RECORD_FOUND + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}

	}

}
