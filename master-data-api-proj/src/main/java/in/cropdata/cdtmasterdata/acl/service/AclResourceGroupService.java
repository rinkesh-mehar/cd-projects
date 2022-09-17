package in.cropdata.cdtmasterdata.acl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.acl.model.AclResourceGroup;
import in.cropdata.cdtmasterdata.acl.repository.AclResourceGroupRepository;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AclResourceGroupService {
	@Autowired
	private AclResourceGroupRepository aclResourceGroupRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public ResponseMessage addResourceGroup(AclResourceGroup AclResourceGroup) {
		try {
			aclResourceGroupRepository.save(AclResourceGroup);

			return responseMessageUtil.sendResponse(true, "Group" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addGroup

	public List<AclResourceGroup> getAllResourceGroup() {
		return aclResourceGroupRepository.findAll(Sort.by("resourceGroupName"));
	}// getAllGroup

	public ResponseMessage updateResourceGroupById(int id, AclResourceGroup AclResourceGroup) {

		try {

			Optional<AclResourceGroup> foundResourceGroup = aclResourceGroupRepository.findById(id);

			if (foundResourceGroup.isPresent()) {

				AclResourceGroup.setId(id);
				aclResourceGroupRepository.save(AclResourceGroup);

				return responseMessageUtil.sendResponse(true, "Group Updated Successfully with Resource Group Name : "+ AclResourceGroup.getResourceGroupName() , "");

			} else {

				return responseMessageUtil.sendResponse(false, "", "Group" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGroupById

	public ResponseMessage deleteResourceGroupById(int id) {

		try {
			aclResourceGroupRepository.deleteById(id);
			return responseMessageUtil.sendResponse(true, "Group" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Group" + APIConstants.RESPONSE_DELETE_ERROR + id);
		}
	}// deleteGroupById

	public AclResourceGroup findResourceGroupById(int id) {
		try {
			Optional<AclResourceGroup> foundResourceGroup = aclResourceGroupRepository.findById(id);
			if (foundResourceGroup.isPresent()) {
				return foundResourceGroup.get();
			} else {
				throw new DoesNotExistException("Group" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGroupById
}
