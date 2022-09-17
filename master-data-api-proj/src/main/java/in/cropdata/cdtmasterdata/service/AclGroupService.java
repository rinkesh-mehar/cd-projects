package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.acl.model.AclGroup;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.repository.AclGroupRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AclGroupService {

	@Autowired
	private AclGroupRepository aclGroupRepository;

	@Autowired
	private ResponseMessageUtil responseMessageUtil;

	public ResponseMessage addGroup(AclGroup aclGroup) {
		try {
			aclGroupRepository.save(aclGroup);

			return responseMessageUtil.sendResponse(true, "Group" + APIConstants.RESPONSE_ADD_SUCCESS, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// addGroup

	public List<AclGroup> getAllGroup() {
		return aclGroupRepository.findAll();
	}// getAllGroup

	public ResponseMessage updateGroupById(int id, AclGroup aclGroup) {

		try {

			Optional<AclGroup> foundGroup = aclGroupRepository.findById(id);

			if (foundGroup.isPresent()) {

				aclGroup.setId(id);
				aclGroupRepository.save(aclGroup);

				return responseMessageUtil.sendResponse(true, "Group" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

			} else {

				return responseMessageUtil.sendResponse(false, "", "Group" + APIConstants.RESPONSE_UPDATE_ERROR + id);
			}
		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
		}
	}// updateGroupById

	public ResponseMessage deleteGroupById(int id) {

		try {
			aclGroupRepository.deleteById(id);
			return responseMessageUtil.sendResponse(true, "Group" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

		} catch (Exception e) {
			return responseMessageUtil.sendResponse(false, "", "Group" + APIConstants.RESPONSE_DELETE_ERROR + id);
		}
	}// deleteGroupById

	public AclGroup findGroupById(int id) {
		try {
			Optional<AclGroup> foundGroup = aclGroupRepository.findById(id);
			if (foundGroup.isPresent()) {
				return foundGroup.get();
			} else {
				throw new DoesNotExistException("Group" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findGroupById

}
