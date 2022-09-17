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

import in.cropdata.cdtmasterdata.acl.model.AclRole;
import in.cropdata.cdtmasterdata.acl.service.AclRestrictionsService;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.dto.interfaces.Restriction;
import in.cropdata.cdtmasterdata.service.AclRoleService;

@RestController
@RequestMapping("/acl/role")
//@CrossOrigin("*")
public class AclRoleController {

    @Autowired
    private AclRoleService roleService;

    @Autowired
    private AclRestrictionsService aclRestrictionsService;

    @PostMapping("/add")
    public ResponseMessage addRole(@RequestBody AclRole role) {
	return roleService.addRole(role);
    }// addRole

    @GetMapping("/list")
    public List<AclRole> getAllRole() {
	List<AclRole> roleList = roleService.getAllRole();
	for (AclRole aclRole : roleList) {
	    List<Restriction> restrictionList = aclRestrictionsService.getAllRestrictionByRoleId(aclRole.getId());
	    if (restrictionList != null) {
		aclRole.setRestrictions(restrictionList);
	    }
	}
	return roleList;
    }// getAllRole

    @PutMapping("/{id}/update")
    public ResponseMessage updateRoleById(@PathVariable int id, @RequestBody AclRole role) {
	return roleService.updateRoleById(id, role);
    }// updateRoleById

    @DeleteMapping("/{id}/delete")
    public ResponseMessage deleteRoleById(@PathVariable int id) {
	return roleService.deleteRoleById(id);
    }// deleteRoleById

    @GetMapping("/{id}")
    public AclRole findRoleById(@PathVariable int id) {
	return roleService.findRoleById(id);
    }// findRoleById

}// RoleController