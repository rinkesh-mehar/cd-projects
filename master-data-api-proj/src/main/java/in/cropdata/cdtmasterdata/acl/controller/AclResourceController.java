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

import in.cropdata.cdtmasterdata.acl.model.AclResource;
import in.cropdata.cdtmasterdata.dto.ResponseMessage;
import in.cropdata.cdtmasterdata.service.AclResourceService;

@RestController
@RequestMapping("/acl/resource")
//@CrossOrigin("*")
public class AclResourceController {
	
	@Autowired
	private AclResourceService aclResourceService;
	
	@PostMapping("/add")
	public ResponseMessage addResource(@RequestBody AclResource aclResource) {
		return aclResourceService.addResource(aclResource);
	}//addResource
	
	@GetMapping("/list")
	public List<AclResource> getAllResource(){
		return aclResourceService.getAllResource();
	}//getAllResource
	
	@GetMapping("/parents")
	public List<AclResource> getAllParentResource(){
		return aclResourceService.getAllParentResource();
	}//getAllParentResource
	
	@GetMapping("/parents/{parentId}")
	public List<AclResource> getAllParentResource(@PathVariable int parentId){
		return aclResourceService.getAllSubResourceByParentId(parentId);
	}//getAllResource
	
	@PutMapping("/{id}/update")
	public ResponseMessage updateResourceById(@PathVariable int id, @RequestBody AclResource aclResource) {
		return aclResourceService.updateResourceById(id, aclResource);
	}//updateResourceById
	
	@DeleteMapping("/{id}/delete")
	public ResponseMessage deleteResourceById(@PathVariable int id){
		return aclResourceService.deleteResourceById(id);
	}//deleteResourceById
	
	@GetMapping("/{id}")
	public AclResource findResourceById(@PathVariable int id) {
		return aclResourceService.findResourceById(id);
	}//findResourceById

}//ResourceController
