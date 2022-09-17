package in.cropdata.cdtmasterdata.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.acl.model.AclRole;
import in.cropdata.cdtmasterdata.constants.APIConstants;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.exceptions.DoesNotExistException;
import in.cropdata.cdtmasterdata.repository.AclRoleRepository;
import in.cropdata.cdtmasterdata.util.ResponseMessageUtil;

@Service
public class AclRoleService {

    @Autowired
    private AclRoleRepository aclRoleRepository;

    @Autowired
    private ResponseMessageUtil responseMessageUtil;

    public ResponseMessage addRole(AclRole role) {
	aclRoleRepository.save(role);

	return responseMessageUtil.sendResponse(true, "Role" + APIConstants.RESPONSE_ADD_SUCCESS, "");
    }// addRole

    public List<AclRole> getAllRole() {
	return aclRoleRepository.findAll(Sort.by("name"));
    }// getAllRole

    public ResponseMessage updateRoleById(int id, AclRole role) {

	try {
	    Optional<AclRole> foundRole = aclRoleRepository.findById(id);

	    if (foundRole.isPresent()) {
		AclRole roleupdate = foundRole.get();

		if (role.getName() != null) {
		    roleupdate.setName(role.getName());
		}

		if (role.getDescription() != null) {
		    roleupdate.setDescription(role.getDescription());
		}
		aclRoleRepository.save(roleupdate);

		return responseMessageUtil.sendResponse(true, "Role" + APIConstants.RESPONSE_UPDATE_SUCCESS + id, "");

	    } else {
		return responseMessageUtil.sendResponse(false, "", "Role" + APIConstants.RESPONSE_UPDATE_ERROR + id);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    return responseMessageUtil.sendResponse(false, "", "Server Error : " + e.getMessage());
	}
    }// updateRoleById

    public ResponseMessage deleteRoleById(int id) {

	try {
	    aclRoleRepository.deleteById(id);

	    return responseMessageUtil.sendResponse(true, "Role" + APIConstants.RESPONSE_DELETE_SUCCESS + id, "");

	} catch (Exception e) {
	    return responseMessageUtil.sendResponse(false, "", "Role" + APIConstants.RESPONSE_DELETE_ERROR + id);
	}
    }// deleteRoleById

    public AclRole findRoleById(int id) {
	try {
	    Optional<AclRole> foundRole = aclRoleRepository.findById(id);
	    if (foundRole.isPresent()) {
		return foundRole.get();
	    } else {
		throw new DoesNotExistException("Role" + APIConstants.RESPONSE_DOES_NOT_EXIST + id);
	    }
	} catch (Exception e) {
	    throw e;
	}
    }// findRoleById

}
