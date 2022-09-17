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

import in.cropdata.cdtmasterdata.acl.model.AclResourceGroup;
import in.cropdata.cdtmasterdata.acl.service.AclResourceGroupService;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;



@RestController
@RequestMapping("/acl/resource-group")
//@CrossOrigin("*")
public class AclResourceGroupController {
	

	@Autowired
	private AclResourceGroupService aclResourceGroupService;

	@GetMapping("/list")
	public List<AclResourceGroup> getAllResourceGroup() {
		return aclResourceGroupService.getAllResourceGroup();
	}// getAllResourceGroup

	@PostMapping("/add")
	public ResponseMessage addResourceGroup(@RequestBody AclResourceGroup ResourceGroup) {
		return aclResourceGroupService.addResourceGroup(ResourceGroup);
	}// addAllResourceGroup

	@PutMapping("/{id}/update")
	public ResponseMessage updateResourceGroupById(@PathVariable int id, @RequestBody AclResourceGroup ResourceGroup) {
		return aclResourceGroupService.updateResourceGroupById(id, ResourceGroup);
	}// updateResourceGroupById

	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteResourceGroupById(@PathVariable int id) {
		return aclResourceGroupService.deleteResourceGroupById(id);
	}// deleteResourceGroupById

	@GetMapping("/{id}")
	public AclResourceGroup findResourceGroupById(@PathVariable int id) {
		return aclResourceGroupService.findResourceGroupById(id);
	}// findResourceGroupById
}