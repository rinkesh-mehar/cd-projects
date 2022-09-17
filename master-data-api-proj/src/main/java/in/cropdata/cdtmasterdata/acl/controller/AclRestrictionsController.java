package in.cropdata.cdtmasterdata.acl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.cropdata.cdtmasterdata.acl.model.AclRestriction;
import in.cropdata.cdtmasterdata.acl.service.AclRestrictionsService;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.Restriction;

@RestController
@RequestMapping("/acl/restriction")
//@CrossOrigin("*")
public class AclRestrictionsController {

    @Autowired
    private AclRestrictionsService aclRestrictionsService;

    @PostMapping("/add")
    public ResponseMessage addRestriction(@RequestBody AclRestriction restriction) {
	return aclRestrictionsService.addRestriction(restriction);
    }// addRestriction
    
    @PostMapping("/add-all")
    public ResponseMessage addAllRestriction(@RequestBody List<AclRestriction> restrictionList) {
	return aclRestrictionsService.addAllRestrictions(restrictionList);
    }// addRestriction

    @GetMapping("/list")
    public List<Restriction> getAllRestriction() {
	return aclRestrictionsService.getAllRestriction();
    }// getAllRestriction
    
    @GetMapping("/{roleId}/role")
    public List<Restriction> getAllRestrictionByRoleId(@PathVariable int roleId) {
	return aclRestrictionsService.getAllRestrictionByRoleId(roleId);
    }// getAllRestriction

    @PutMapping("/{id}/update")
    public ResponseMessage updateRestrictionById(@PathVariable int id, @RequestBody AclRestriction restriction) {
	return aclRestrictionsService.updateRestrictionById(id, restriction);
    }// updateRestrictionById

    @DeleteMapping("/{id}/delete")
    public ResponseMessage deleteRestrictionById(@PathVariable int id) {
	return aclRestrictionsService.deleteRestrictionById(id);
    }// deleteRestrictionById

    @GetMapping("/{id}")
    public AclRestriction findRestrictionById(@PathVariable int id) {
	return aclRestrictionsService.findRestrictionById(id);
    }// findRestrictionById

}// RestrictionController