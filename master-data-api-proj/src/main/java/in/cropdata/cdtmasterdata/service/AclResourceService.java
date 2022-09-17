package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.acl.model.AclResource;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.repository.AclResourceRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AclResourceService {

    @Autowired
    private AclResourceRepository aclResourceRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;
    
//    @Autowired
//    private AclGroupRepository groupRepository;

    public ResponseMessage addResource(AclResource aclResource) {

	try {
	    aclResourceRepository.save(aclResource);

	    return responseMessageUtil.sendResponse(true, "Resource" + APIConstants.RESPONSE_ADD_SUCCESS, "");

	} catch (Exception e) {
	    return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
	}
    }// addResource

    public List<AclResource> getAllResource() {
	List<AclResource> list = aclResourceRepository.findAll();
//	for (AclResource aclResource : list) {
//	    Optional<AclGroup> foundGroup = groupRepository.findById(aclResource.getGroupId());
//	    if (foundGroup.isPresent()) {
//		AclGroup group = foundGroup.get();
//		aclResource.setGroupName(group.getName());
//	    }
//	}
	return aclResourceRepository.findAll();
    }// getAllResource

    public ResponseMessage updateResourceById(int id, AclResource aclResource) {

	Optional<AclResource> foundResource = aclResourceRepository.findById(id);

	if (foundResource.isPresent()) {
	    try {
		aclResource.setId(id);
		aclResourceRepository.save(aclResource);

		return responseMessageUtil.sendResponse(true, "Resource" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

	    } catch (Exception e) {
		return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
	    }
	} else {
	    return responseMessageUtil.sendResponse(false, "", "Resource" + APIConstants.RESPONSE_UPDATE_ERROR + id);
	}
    }// updateResourceById

    public ResponseMessage deleteResourceById(int id) {
	try {
	    aclResourceRepository.deleteById(id);

	    return responseMessageUtil.sendResponse(true, "Resource" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

	} catch (Exception e) {
	    return responseMessageUtil.sendResponse(false, "", "Resource" + APIConstants.RESPONSE_DELETE_ERROR + id);
	}
    }// deleteResourceById


	public AclResource findResourceById(int id) {
		try {
			Optional<AclResource> foundResource = aclResourceRepository.findById(id);
			if (foundResource.isPresent()) {
				return foundResource.get();
			} else {
				throw new DoesNotExistException("Resource" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
			}
		} catch (Exception e) {
			throw e;
		}
	}// findResourceById


    public List<AclResource> getAllParentResource() {
	return aclResourceRepository.getAllParentResource();
    }// getAllParentResource

    public List<AclResource> getAllSubResourceByParentId(int parentId) {
	return aclResourceRepository.getAllSubResourceByParentId(parentId);
    }// getAllSubResourceByParentId

}
