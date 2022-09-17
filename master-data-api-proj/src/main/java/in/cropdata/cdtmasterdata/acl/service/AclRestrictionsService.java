package in.cropdata.cdtmasterdata.acl.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.cropdata.cdtmasterdata.acl.model.AclRestriction;
import in.cropdata.cdtmasterdata.acl.repository.AclRestrictionsRepository;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.Restriction;

@Service
public class AclRestrictionsService {

    @Autowired
    private AclRestrictionsRepository aclRestrictionsRepository;

    public ResponseMessage addRestriction(AclRestriction restriction) {

	ResponseMessage responseMessage = new ResponseMessage();

	aclRestrictionsRepository.save(restriction);

	responseMessage.setSuccess(true);
	responseMessage.setMessage("Restrictions added Successfully");
	return responseMessage;
    }// addRestriction

    public List<Restriction> getAllRestriction() {
	List<Restriction> data = aclRestrictionsRepository.getAllData();
	return data;
    }// getAllRestriction

    public ResponseMessage updateRestrictionById(int id, AclRestriction restriction) {
	ResponseMessage responseMessage = new ResponseMessage();
	Optional<AclRestriction> foundVareity = aclRestrictionsRepository.findById(id);
	if (foundVareity.isPresent()) {
	    restriction.setId(id);
	    aclRestrictionsRepository.save(restriction);

	    responseMessage.setSuccess(true);
	    responseMessage.setMessage("Restriction Updated Successfully.");
	} else {
	    responseMessage.setSuccess(false);
	    responseMessage.setError("Update Error.");
	}
	return responseMessage;
    }// updateRestrictionById

    public ResponseMessage deleteRestrictionById(int id) {
	aclRestrictionsRepository.deleteById(id);

	ResponseMessage responseMessage = new ResponseMessage();
	responseMessage.setSuccess(true);
	responseMessage.setMessage("Restriction Deleted Successfully.");
	return responseMessage;
    }// deleteRestrictionById

    public AclRestriction findRestrictionById(int id) {
	Optional<AclRestriction> foundActivity = aclRestrictionsRepository.findById(id);
	if (foundActivity.isPresent()) {
	    return foundActivity.get();
	}
	return null;
    }// findRestrictionById

    
    //  changes 
    public List<AclRestriction> findAllByRoleId(int roleId) {
	
	List<AclRestriction> list = aclRestrictionsRepository.findAllByRoleId(roleId);
	for (AclRestriction aclRestriction : list) {
//	    aclRestriction.setGroup(aclRestrictionsRepository.getGroupNameByGroupId(aclRestriction.getGroupId()));
	    aclRestriction.setResourceURI(aclRestrictionsRepository.getResourceURIByResourceId(aclRestriction.getResourceId()));
	}
	return list;
    }//findAllByRoleId(int roleId)

    public ResponseMessage addAllRestrictions(List<AclRestriction> restrictionList) {
	ResponseMessage responseMessage = new ResponseMessage();

	aclRestrictionsRepository.saveAll(restrictionList);

	responseMessage.setSuccess(true);
	responseMessage.setMessage("Restrictions added Successfully");
	return responseMessage;
    }

    public List<Restriction> getAllRestrictionByRoleId(int roleId) {
	List<Restriction> data = aclRestrictionsRepository.getAllDataByRoleId(roleId);
	return data;
    }//getAllRestrictionByRoleId

}